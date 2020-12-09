package Rummikub;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Rummikub.Tile.Colors;

public class Game {
	private Server server;
    private boolean gameRunning = false;
    private int turn = 0;
    private int gameEndingScore = 0;
    private boolean endRound = false;
    public Deck deck;
    private Board board = new Board();
    private Board origBoard = new Board();
    private Scanner scanner = new Scanner(System.in);
	// players and clients indices should match
	// i.e. client[0] -> player[0]
	private int numPlayers; // Needed for testing both offline and online
	private ArrayList<Player> players = new ArrayList<Player>();
    private boolean testing; // A useful flag for code when testing


	// Constructors
	//For testing
	public Game() {
		this(false);
	}
	// Single player
	public Game(boolean b) {
		testing = b;
		numPlayers = 1;
		reset();
	}

	public Game(int i) {
		this(i, false);
	}
	// Multiplayer with dummy players
	public Game(int i, boolean b) {
		testing = b;
		numPlayers = i;
		reset();
	}
	public Game(Server s) {
		this(s, false);
	}
	// Multiplayer and networked
	public Game(Server s, boolean b) {
		testing = b;
		server = s;
		server.game = this;
		numPlayers = server.getNumClients();
		reset();
	}

	// Useful for testing and restarting the game
	public void reset() {
		gameRunning = true;
		deck = new Deck();
		board = new Board();
		setOrigBoard();
		players = new ArrayList<Player>(); // Also reset the player list and re-add them
		turn = 1;

		if (server != null) { // Multiplayer mode
			for (String name : server.getNames())
				createPlayer(name);
		} else { // Single player mode
			for (int i=0; i<numPlayers; i++)
				createPlayer("player"+i);
		}
	}

	public void resetRound() {
		gameRunning = true;
		deck = new Deck();
		board = new Board();
		setOrigBoard();
		turn = 1;
	}

	// Helper function for Game.reset(..)
	public void createPlayer(String name) {
		Player p = new Player(name);
		Hand hand = new Hand(deck); // Generate a hand, deal tiles from Deck
		p.setHand(hand);
		players.add(p);
		p.sortHand();
	}

    public boolean isRun(ArrayList<Tile> run) {
    	Colors color = run.get(0).getColor();
    	int value = run.get(0).getValue() - 1;

    	for (Tile t : run) {
    		if (t.getColor() != color)
    			return false;

    		if (t.getValue() == value + 1)
    			value++;
    		else
    			return false;
    	}
    	return true;
    }

    public boolean isGroup(ArrayList<Tile> group) {
    	boolean red = false;
    	boolean blue = false;
    	boolean yellow = false;
    	boolean black = false;
    	int value = group.get(0).getValue();

    	for (Tile t : group) {
    		if (t.getValue() != value)
    			return false;

    		if (t.getColor() == Colors.RE) {
    			if (!red)
    				red = true;
    			else
    				return false;
    		}
    		else if (t.getColor() == Colors.BL) {
    			if (!blue)
    				blue = true;
    			else
    				return false;
    		}
    		else if (t.getColor() == Colors.YE) {
    			if (!yellow)
    				yellow = true;
    			else
    				return false;
    		}
    		else {
    			if (!black)
    				black = true;
    			else
    				return false;
    		}
    	}
    	return true;
    }

    public Player getWinner() {
		int highscore = 10000;
		Player p = null;
		if(deck.getTiles().size() == 0){
			for (int i = 0; i < players.size(); i ++ ) {
				if (players.get(i).getTileNumber() == 0) {
					p = players.get(i);
				}
			}
		}
		else{
			for(int i = 0; i< players.size(); i++) {
				if (players.get(i).getTotalScore() < highscore) {
					p = players.get(i);
					highscore = players.get(i).getScore();
				}
			}
		}
		if (p != null) {
			println("Winner: " + p.getName());
			println("Score: " + p.getScore());
		}
		return p;
	}

	public Player getFinalWinner() {
		Player p = null;
		int totalscore = 0;
		for (int i = 0; i < players.size(); i ++ ) {
			if (players.get(i).getTotalScore() > totalscore) {
				p = players.get(i);
				totalscore = players.get(i).getTotalScore();
			}
		}
		if (p != null) {
			println("Winner: " + p.getName());
			println("Score: " + p.getScore());
		}
		return p;
	}

	public String printFinalScores(){
		String output;
		output = "=========FINAL SCORES=========";
		for(int i = 0; i < players.size(); i++){
			output += players.get(i).getName() + ": "+ players.get(i).getTotalScore() + "\n";
		}
		return output;
	}

	public void scorePoints() {
		Player winner = getWinner();
		int scoreForWinner = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) != winner) {
				int score = -players.get(i).sumOfTiles();
				System.out.println("sum of tiles " + score);
				if (players.get(i).hasJoker()) {
					score -= 30;
				}
				scoreForWinner += -score;
				players.get(i).setScore(score);
				players.get(i).updateTotalScore(score);
			}
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) == winner) {
				winner.setScore(scoreForWinner);
				winner.updateTotalScore(scoreForWinner);
			}
		}
		// Final round ending for multiple rounds
		for (int i = 0; i < players.size(); i++) {
			if(players.get(i).getTotalScore() >= gameEndingScore){
				getFinalWinner();
				println(printFinalScores());
			}
		}
	}

    // returns true if player's hand is empty
    public boolean isGameOver() {
    	if (players.get(getCurPlayerIdx()).getTileNumber() == 0)
    		return true;
    	return false;
	}

	// Get who's the current player this turn for indexing purposes
	public int getCurPlayerIdx() {  return ((turn-1) % players.size()); }
	public String getCurPlayerName() { return players.get(getCurPlayerIdx()).getName(); }
	public Player getCurPlayer() { return players.get(getCurPlayerIdx()); }

	// Is it this player's turn?
	public boolean playerTurn(int player) {
		return (getCurPlayerIdx() == player);
	}

	// Prints the same string to all players
	public void print(String str) {
		for (int i = 0; i < players.size(); i++)
			print(str, i);
	}
	public void println(String str) {
		String out = str+'\n';
		print(out);
	}
	// These 2 functions is for private messaging/printing
	public void print(String str, int idx) {
		if (server != null) // Multiplayer mode
			server.print(str, idx);
		else // Single player mode
			System.out.print(str);
	}
	public void println(String str, int idx) {
		String out = str+'\n';
		print(out, idx);
	}

	// The command handler
	// Parses text given by a client to Server
	public boolean command(int playerIdx, String input) throws IOException {
		if (!playerTurn(playerIdx)) {
			println("It is not your turn yet.", playerIdx);
			return false;
		}
		Player curPlayer = getCurPlayer();

		String[] sArr = input.split(" ");
		boolean invalidCmd = false;
		if (sArr.length > 1) { // Commands with input arguments
			String[] args = Arrays.copyOfRange(sArr, 1, sArr.length);
			switch(sArr[0]) {
				case "p": // placing tiles from hand onto the board
					placeTiles(args, curPlayer);
					break;
				case "g": // giving tiles to a row on the board
					giveTiles(args, curPlayer);
					break;
				case "m": // moving tiles from one row to another on the board
					moveTiles(args, curPlayer);
					break;
				case "s": // splitting rows on the board
					splitRow(args, curPlayer);
					break;
				default:
					invalidCmd = true;
					break;
			}
		} else { // No arguments to commands
			switch(sArr[0]) {
				case "h": // display help message
					help();
					break;
				case "db": // display the board
					println(board.printBoard());
					break;
				case "dh": // display player's hand
					printCurPlayerHand();
					break;
				case "u": // undo
					undo(curPlayer);
					break;
				case "e": // end turn
					endTurn();
					break;
				default:
					invalidCmd = true;
					break;
			}
		}
		if (invalidCmd) {
			println("Bad command: "+input, playerIdx);
			return false;
		}
		return true;
	}

	//////////////////////////////////////////////////////////////////////
	// Functions used by command(..)

	// player draws a tile
	public void drawTile(Player p) { // DUPLICATE FUNCTION NAME
		p.drawTile(deck);
	}


	// Print from the help from a file
	private void help() throws IOException {
		File fHelp = new File("resources/help.txt");
		BufferedReader br = new BufferedReader(new FileReader(fHelp));
		String str;
		while ((str = br.readLine()) != null) {
			print(str+"\n");
		}
	}


	// Should be 3 states to validate
	// Invalid, unable to endTurn()
	// Valid, end turn with manipulations to board
	// Valid, no manipulates and drawTile
	public boolean endTurn() {
		//Nothing done on board in this turn, then draw
		Player player = getCurPlayer(); // The current player
		// Is the player's hand different and is the board valid?
		if (getCurPlayer().getHand().compare(player.getOrigHand()) && board.checkBoard()) {
			int sum = player.sumOfTilesPlaced();
			boolean firstPlacement = player.getDoneFirstPlacement();
			if (!firstPlacement && sum<30) {
				//print error
				undo(player);
				println("Sorry you can't place those Tiles! Your First Placement must add up to 30 points");
				println("Try again");
				return false;
			}
			else if (!firstPlacement && sum > 30) {
				player.setFirstPlacement();
				setOrigBoard();  //update original board to finalize
				player.updateHand();  //update original hand to finalize
			}
			else {
				setOrigBoard();  //update original board to finalize
				player.updateHand();  //update original hand to finalize
				player.sortHand(); //sort the updated hand
			}
		} else {
			undo(player);
			if(player.getHand().compare(player.getOrigHand())) {
				if (deck.getTiles().size() > 0) {
					drawTile(player);
					drawTile(player);
					drawTile(player);
				} else {
					endRound = true;
				}
			} else{
				if (deck.getTiles().size() > 0) {
					drawTile(player);
				} else {
					endRound = true;
				}
			}
			player.updateHand();  //update original hand to finalize
			player.sortHand(); //sort the updated hand
		}
		if(isGameOver() || endRound){
			scorePoints();
			resetRound();
		}
		players.get(getCurPlayerIdx()).nextTurn();
		turn++;
		announcePlayersTurn(); // Will announce who's turn it is now
		return true;
	}

	// Reverts the player's hand and the board to the original state
	// as when the turn started
	private boolean undo(Player curPlayer) {
		curPlayer.resetHand();
		curPlayer.sortHand();
		setBoard();
		return true;
	}

	// Places tiles from active player's hand to the board
	// “p 1 3 4 7”
	public boolean placeTiles(String[] sArr, Player player) {
		// Needs at least 1 tile to place
		if (sArr.length < 1)
			return false;
		// Integer index of tiles on hand
		int[] tilesIdx = new int[sArr.length];
		for (int i=0; i<sArr.length; i++)
			tilesIdx[i] = Integer.parseInt(sArr[i]);
			Arrays.sort(tilesIdx);
		if (player.hasTiles(tilesIdx)) {
			ArrayList<Tile> playerTiles = player.putTiles(tilesIdx);
			board.addSet(playerTiles);
			return true;
		}
		println("You cannot put those tiles!");
		return false;
	}

	// Give tiles from your hand onto a row on the board
	// “g 1 2 3”
	private boolean giveTiles(String[] sArr, Player player) {
		// Needs at least 1 tile to place and dstRow
		if (sArr.length < 2)
			return false;
		int dstRow = Integer.parseInt(sArr[0]);
		int[] tilesIdx = new int[sArr.length-1];
		for (int i=1; i<sArr.length; i++)
			tilesIdx[i - 1] = Integer.parseInt(sArr[i]);
		if (player.hasTiles(tilesIdx)) {
			ArrayList<Tile> playerTiles = player.putTiles(tilesIdx);
			board.addToCurrent(playerTiles,dstRow);
			return true;
		}
		println("You cannot put those tiles!");
		return false;
	}

	// Move tiles from one row on the board to another row on the board
	// “m 1 2 5 6”
	private boolean moveTiles(String[] sArr, Player player) {
		// Needs at least 1 tile to place and dstRow & srcRow
		if (sArr.length < 3)
			return false;
		int srcRow = Integer.parseInt(sArr[0]);
		int dstRow = Integer.parseInt(sArr[1]);
		int[] tilesIdx = new int[sArr.length-2];
		for (int i=2; i<sArr.length; i++)
			tilesIdx[i-2] = Integer.parseInt(sArr[i]);
		if (player.hasTiles(tilesIdx)) {
			ArrayList<Integer> index = new ArrayList<Integer>();
			for(int num:tilesIdx){
				index.add(num);
			}
			board.combineCurrent(srcRow,dstRow,index);
			return true;
		}
		//TODO: error message: no such tiles
		return false;
	}

	// Split a row on the board into 2 rows
	// “s 1 4”
	private boolean splitRow(String[] sArr, Player player) {
		// Needs a split index and srcRow
		if (sArr.length != 2)
			return false;
		int srcRow = Integer.parseInt(sArr[0]);
		int splitIdx = Integer.parseInt(sArr[1]);
		board.separateSet(srcRow,splitIdx);
		return true;
	}

	public void printCurPlayerHand() {
		ArrayList<String> tmp = getCurPlayer().printHand();
		// Will print index line then player hand line
		for (String s : tmp)
			println(s, getCurPlayerIdx());
	}

	// Functions used by command(..)
	//////////////////////////////////////////////////////////////////////


	//////////////////////////////////////////////////////////////////////
	// Functions for debugging purposes

	// initialize the board status for debugging
	public void setBoardState(Board b) {
		origBoard = new Board();
		board = new Board();
		for (ArrayList<Tile> row: b.board) {
			ArrayList<Tile> temp = new ArrayList<Tile>();
			temp.addAll(row);
			origBoard.addSet(temp);
		}
		setBoard();
	}

	// Return current player's hand
	public Hand curPlayerHand() {
		return players.get(getCurPlayerIdx()).getHand();
	}

	// Return current turn number
	public int getTurn() {
		return turn;
	}

	// Update current board with the given board
	public void setOrigBoard() {
		origBoard = new Board();
		for (ArrayList<Tile> row: board.board) {
			ArrayList<Tile> temp = new ArrayList<Tile>();
			temp.addAll(row);
			origBoard.addSet(temp);
		}
	}
	public void setBoard() {
		board = new Board();
		for (ArrayList<Tile> row: origBoard.board) {
			ArrayList<Tile> temp = new ArrayList<Tile>();
			temp.addAll(row);
			board.addSet(temp);
		}
	}

	// return board
	public Board getBoard() {
		return board;
	}

	// Set current player's hand
	public void setCurHand(Hand hand) {
		players.get(getCurPlayerIdx()).setHand(hand);
	}

	// return players
	public ArrayList<Player> getPlayers() {
		return players;
	}

	// Functions for debugging purposes
	//////////////////////////////////////////////////////////////////////


	public void startText() {
		println("Game has started!");
		announcePlayersTurn();
	}

	public void announcePlayersTurn() {
		print("It is ");
		print(getCurPlayerName());
		println("'s turn!");
		// Show their hand
		printCurPlayerHand();
	}

	public void setGameEndingScore(int i) { gameEndingScore = i; }
}
