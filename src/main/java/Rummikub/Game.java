package Rummikub;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import Rummikub.Tile.Colors;

public class Game {
	private Server server;
    private boolean gameRunning = false;
    private int turn = 0;
    public Deck deck;
    private Board board = new Board();
    private Board origBoard = new Board();
    private Scanner scanner = new Scanner(System.in);
	//private static enum Actions {display, pick, finalize, undo, take, split};
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

	public ArrayList<Player> getPlayers () {
		return players;
	}
	// Useful for testing and restarting the game
	public void reset() {
		gameRunning = true;
		deck = new Deck();
		board = new Board();
		origBoard = board;
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

	// Helper function for Game.reset(..)
	private void createPlayer(String name) {
		Player p = new Player(name);
		Hand hand = new Hand(deck); // Generate a hand, deal tiles from Deck
		p.setHand(hand);
		players.add(p);
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
		Player p = null;
		for (int i = 0; i < players.size(); i ++ ) {
			if (players.get(i).getTileNumber() == 0) {
				p = players.get(i);
			}
		}
		if (p != null) {
			print("Winner: " + p.getName());
			print("Score: " + p.getScore());
		}
		return p;
	}

	public void scorePoints(ArrayList<Player> players) {
		Player winner = getWinner();
		int scoreForWinner = 0;
		for (int i = 0; i < players.size(); i ++) {
			if(players.get(i) != winner) {
				int score = -players.get(i).sumOfTiles();
				if (players.get(i).hasJoker()) {
					score -= 30;
				}
				scoreForWinner += -score;
				players.get(i).setScore(score);
			}
		}
		for (int i = 0; i < players.size(); i ++) {
			if(players.get(i) == winner) {
				winner.setScore(scoreForWinner);
			}
		}
	}

    // returns true if player's hand is empty
    public boolean isGameOver() {
    	if (players.get(curPlayer()).getTileNumber() == 0)
    		return true;
    	return false;
	}

	// Get who's the current player this turn for indexing purposes
	public int curPlayer() {
		return ((turn-1) % players.size());
	}

	public Player curPlayerObj() {
		return players.get(curPlayer());
	}
	public String curPlayerName() {
		return players.get(curPlayer()).getName();
	}

	// Is it this player's turn?
	public boolean playerTurn(int player) {
		return (curPlayer() == player);
	}

	// Prints the same string to all players
	public void print(String str) {
		if (server != null) // Multiplayer mode
			server.print(str);
		else // Single player mode
			System.out.print(str);
	}
	public void println(String str) {
		String out = str+'\n';
		print(out);
	}

	// The command handler
	// Parses text given by a client to Server
	public boolean command(int player, String input) throws IOException {
		if (!playerTurn(player))
			return false;
		Player curPlayer = players.get(curPlayer());

		String[] sArr = input.split(" ");
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
					ArrayList<String> tmp = curPlayer.printHand();
					// Will print index line then player hand line
					for (String s: tmp)
						println(s);
					break;
				case "u": // undo
					undo(curPlayer);
					break;
				case "e": // end turn
					// TODO Need to implement ending turn and validating board & current player's hand
					endTurn();
					break;
			}
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
		Player currPlayer = players.get(curPlayer());
		if (currPlayer.getHand().compare(currPlayer.getOrigHand()) && board.checkBoard()) {
			int sum = currPlayer.sumOfTilesPlaced();
			boolean firstPlacement = currPlayer.getFirstPlacement();
			if (!firstPlacement && sum<30) {
				//print error
				undo(players.get(curPlayer()));
				println("Sorry you can't place those Tiles! Your First Placement must add up to 30 points");
				println("Try again");
				players.get(curPlayer()).nextTurn();
				turn++;
				return false;
			}
			else if (!firstPlacement && sum > 30) {
				currPlayer.setFirstPlacement();
				origBoard = board;  //update original board to finalize
				players.get(curPlayer()).updateHand();  //update original hand to finalize
			}
			else {
				origBoard = board;  //update original board to finalize
				players.get(curPlayer()).updateHand();  //update original hand to finalize
				players.get(curPlayer()).sortHand(); //sort the updated hand
			}
			players.get(curPlayer()).nextTurn();
			turn++;
			return true;
		} else {
			drawTile(players.get(curPlayer()));
		}
		players.get(curPlayer()).nextTurn();
		turn++;
		return false;
	}

	// Reverts the player's hand and the board to the original state
	// as when the turn started
	// TODO Get origHand and origBoard initialized at every start of a turn
	private boolean undo(Player curPlayer) {
		curPlayer.resetHand();
		board = origBoard;
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
		if (player.hasTiles(tilesIdx)) {
			ArrayList<Tile> playerTiles = player.putTiles(tilesIdx);
			board.addSet(playerTiles);
			if (!board.checkBoard()) {
				println("Invalid placement!");
				board = origBoard;
				player.resetHand();
				return false;
			}
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
			if (!board.checkBoard()) {
				println("Invalid placement!");
				board = origBoard;
				player.resetHand();
				return false;
			}
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
			tilesIdx[i] = Integer.parseInt(sArr[i]);
		if (player.hasTiles(tilesIdx)) {
			ArrayList<Integer> index = new ArrayList<Integer>();
			for(int num:tilesIdx){
				index.add(num);
			}
			board.combineCurrent(srcRow,dstRow,index);
			if (!board.checkBoard()) {
				//TODO: error message printing: invalid board
				board = origBoard;
				player.resetHand();
				return false;
			}
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
			if (!board.checkBoard()) {
				//TODO: error message printing: invalid split
				board = origBoard;
				player.resetHand();
				return false;
			}
		return true;
	}

	// Functions used by command(..)
	//////////////////////////////////////////////////////////////////////


	//////////////////////////////////////////////////////////////////////
	// Functions for debugging purposes

	// Return current player's hand
	public Hand curPlayerHand() {
		return players.get(curPlayer()).getHand();
	}

	// Return current turn number
	public int getTurn() {
		return turn;
	}

	// Update current board with the given board
	public void setBoard(Board newBoard) {
		board = newBoard;
	}

	// return board
	public Board getBoard() {
		return board;
	}

	// Set current player's hand
	public void setCurHand(Hand hand) {
		players.get(curPlayer()).setHand(hand);
	}

	// return players
	public ArrayList<Player> getPlayers() {
		return players;
	}
}
