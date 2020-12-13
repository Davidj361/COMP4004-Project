package Rummikub;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
	private Server server;
    private int turn, totalTurns = 0;
    boolean won = false;
    private int round = 0;
    private int gameEndingScore = 0;
    private boolean endRound = false;
    public Deck deck;
    private Board board = new Board();
    private Board origBoard = new Board();
	// players and clients indices should match
	// i.e. client[0] -> player[0]
	private final int numPlayers; // Needed for testing both offline and online
	private ArrayList<Player> players = new ArrayList<>();
    private final boolean testing; // A useful flag for code when testing


	// Constructors
	// Single player
	public Game(boolean test) {
		testing = test;
		numPlayers = 1;
		reset();
	}

	// Multiplayer with dummy players
	public Game(int i, boolean test) {
		testing = test;
		numPlayers = i;
		reset();
	}
	public Game(Server s) {
		this(s, false);
	}
	// Multiplayer and networked
	public Game(Server s, boolean test) {
		testing = test;
		server = s;
		server.game = this;
		numPlayers = server.getNumClients()+1; // Add the host
		reset();
	}

	// Useful for testing and restarting the game
	public void reset() {
		deck = new Deck();
		board = new Board();
		setOrigBoard();
		players = new ArrayList<>(); // Also reset the player list and re-add them
		turn = 1;
		totalTurns = 1;
		round = 1;
		won = false;

		if (server != null) { // Multiplayer mode
			for (String name : server.getNames())
				createPlayer(name);
		} else { // Single player mode
			for (int i=0; i<numPlayers; i++)
				createPlayer("player"+i);
		}
	}

	public void resetRound() {
		deck = new Deck();
		board = new Board();
		setOrigBoard();
		turn = 1;
		totalTurns = 1;
		round++;
		endRound = false;
	}

	// Helper function for Game.reset(..)
	public void createPlayer(String name) {
		Player p = new Player(name);
		Hand hand = new Hand(deck); // Generate a hand, deal tiles from Deck
		p.setHand(hand);
		players.add(p);
		p.sortHand();
	}

	public Player getWinner() {
		int highScore = -1;
		Player p = null;
		if (deck.size() != 0) {
			for (Player player : players) {
				if (player.handSize() == 0) {
					p = player;
				}
			}
		} else {
			int sum;
			for(Player ply: players) {
				sum = getSum(ply);
				if (highScore == -1 || sum < highScore) {
					p = ply;
					highScore = sum;
				}
			}
		}
		return p;
	}

	private int getSum(Player p) {
		int sum;
		sum = p.sumOfTiles();
		if (p.hasJoker())
			sum += 30;
		return sum;
	}

	public Player getFinalWinner() {
		Player p = null;
		int totalScore = 0;
		for (int i = 0; i < players.size(); i ++ ) {
			int scr = players.get(i).getTotalScore();
			if (scr >= gameEndingScore && scr > totalScore) {
				p = getPlayer(i);
				totalScore = scr;
			}
		}
		if (p != null) {
			println("Winner: " + p.getName());
			println("Score: " + p.getScore());
			won = true;
			if (server != null) {
				try {
					println("Server shutting down");
					server.close();
					server = null;
				} catch (IOException e) {
					System.out.println("Unable to close server in Game.getFinalWinner(..).");
					e.printStackTrace();
				}
			}
		}
		return p;
	}

	// Is the game won? Change header
	public void printTotalScores(boolean won) {
		StringBuilder output = new StringBuilder();
		if (won)
			output.append("=========FINAL SCORES=========\n");
		else
			output.append("=========CURRENT SCORES=========\n");
		for (Player player : players)
			output.append(player.getName()).append(": ").append(player.getTotalScore()).append("\n");
		println(output.toString());
	}

	public void scorePoints() {
		Player winner = getWinner();
		println(winner.getName() + " won the round");
		int scoreForWinner = 0;
		scoreForWinner = getScoreForWinner(winner, scoreForWinner);
		for (Player player : players) {
			if (player == winner) {
				winner.setScore(scoreForWinner);
				println(player.getName() + ": " + player.getScore());
				winner.updateTotalScore(scoreForWinner);
			}
		}
		Player finalWinner = getFinalWinner();
		printTotalScores(finalWinner != null);
	}

	private int getScoreForWinner(Player winner, int scoreForWinner) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) != winner) {
				int score = getScore(i);
				scoreForWinner += -score;
				players.get(i).setScore(score);
				println(players.get(i).getName() + ": " + players.get(i).getScore());
				players.get(i).updateTotalScore(score);
			}
		}
		return scoreForWinner;
	}

	private int getScore(int i) {
		int score = -players.get(i).sumOfTiles();
		if (players.get(i).hasJoker())
			score -= 30;
		return score;
	}

	// returns true if player's hand is empty
    public boolean isGameOver() {
    	if (players.get(getCurPlayerIdx()).handSize() == 0) {
			println("Round is over, and because a player emptied their hand");
			println(players.get(getCurPlayerIdx()).getName() + " won the round");
			return true;
		}
    	return false;
	}

	// Get who's the current player this turn for indexing purposes
	public int getCurPlayerIdx() {  return (turn-1) % players.size(); }
	public String getCurPlayerName() { return players.get(getCurPlayerIdx()).getName(); }
	public Player getCurPlayer() { return players.get(getCurPlayerIdx()); }

	// Is it this player's turn?
	public boolean playerTurn(int player) {
		return (getCurPlayerIdx() == player);
	}

	// Prints the same string to all players
	public void print(String str) {
		if (server != null) {
			for (int i = 0; i < players.size(); i++)
				print(str, i);
		} else
			print(str, 0);
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
	public boolean command(int playerIdx, String input) {
		if (won) // Needed for non-networked tests and as a safeguard
			return false;
		if (!playerTurn(playerIdx)) {
			if (!input.equals("h") && !input.equals("db") && !input.equals("dh")) {
				println("It is not your turn yet.", playerIdx);
				return false;
			}
		}
		Player curPlayer = getCurPlayer();

		String[] sArr = input.split(" ");
		boolean invalidCmd = false;
		if (sArr.length > 1) { // Commands with input arguments
			String[] args = Arrays.copyOfRange(sArr, 1, sArr.length);
			switch(sArr[0]) {
				case "p": // placing tiles from hand onto the board
					return placeTiles(args, curPlayer);
				case "g": // giving tiles to a row on the board
					return giveTiles(args, curPlayer);
				case "m": // moving tiles from one row to another on the board
					return moveTiles(args);
				case "s": // splitting rows on the board
					return splitRow(args);
				default:
					invalidCmd = true;
					invalidCommandMessage();
					break;
			}
		} else { // No arguments to commands
			switch(sArr[0]) {
				case "h": // display help message
					help(playerIdx);
					break;
				case "db": // display the board
					println(board.printHelper(),playerIdx);
					break;
				case "dh": // display player's hand
					if (!playerTurn(playerIdx))
						printPlayersHand(playerIdx);
					else
						printCurPlayerHand();
					break;
				case "u": // undo
					undo(curPlayer);
					break;
				case "e": // end turn
					return endTurn();
				default:
					invalidCmd = true;
					invalidCommandMessage();
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
	public void drawTile(Player p) {
		if(deck.size() > 0) {
			p.drawTile(deck);
			println("Tile drawn is a " + p.getHand().getTile(p.getHand().size()-1),getCurPlayerIdx());
		}
		else
			println("No more tiles left in the deck. Could not draw a tile.", getCurPlayerIdx());
	}


	// Print from the help from a file
	private void help(int index) {
		try {
			File fHelp = new File("resources/help.txt");
			BufferedReader br = new BufferedReader(new FileReader(fHelp));
			String str;
			while ((str = br.readLine()) != null) {
				print(str + "\n", index);
			}
		} catch (IOException e) {
			System.out.println("Unable to open help.txt in Game.help(..)");
			e.printStackTrace();
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
			int sum = sumOfTilesPlaced();
			boolean firstPlacement = player.getDoneFirstPlacement();
			if (!firstPlacement && sum<30) {
				//print error
				undo(player);
				println("Sorry you can't place those Tiles! Your First Placement must add up to 30 points", getCurPlayerIdx());
				println("3 tiles have been added to your hand from deck", getCurPlayerIdx());
				messageToOtherPlayers(getCurPlayerName() + "'s moves are invalid");
				messageToOtherPlayers("3 tiles have been added to " + getCurPlayerName() + "'s hand");
				if (deck.getTiles().size() > 0) {
					// Game rules says to pickup 3 tiles if tried to modify board but didn't end up successfully modifying
					for (int i=0; i<3; i++)
						drawTile(player);
				} else {
					endRound = true;
					println("This is the last turn because the deck is empty!");
				}
				println("There are currently: " + deck.getTiles().size() + " tiles left in the deck");
			}
			else if (!firstPlacement && sum > 30) {
				println("You have successfully completed your First placement", getCurPlayerIdx());
				messageToOtherPlayers(getCurPlayerName() + " successfully completed First placement round");
				player.setFirstPlacement();
				setOrigBoard();  //update original board to finalize
				player.updateHand();  //update original hand to finalize
			}
			else {
				println("Your moves are valid!", getCurPlayerIdx());
				messageToOtherPlayers(getCurPlayerName() + " made valid moves, board has been updated");
				setOrigBoard();  //update original board to finalize
				player.updateHand();  //update original hand to finalize
				player.sortHand(); //sort the updated hand
			}
		} else {
			if (player.getHand().compare(player.getOrigHand())) {
				undo(player);
				println("Your moves are not valid", getCurPlayerIdx());
				println("3 tiles have been added to your hand from deck", getCurPlayerIdx());
				messageToOtherPlayers(getCurPlayerName() + "'s moves are invalid");
				messageToOtherPlayers("3 tiles have been added to " + getCurPlayerName() + "'s hand");
				if (deck.getTiles().size() > 0) {
					// Game rules says to pickup 3 tiles if tried to modify board but didn't end up successfully modifying
					for (int i=0; i<3; i++)
						drawTile(player);
				} else {
					endRound = true;
					println("This is the last turn because the deck is empty!");
				}
				println("There are currently: " + deck.getTiles().size() + " tiles left in the deck");
			} else {
				println("You ended your turn with out making any moves", getCurPlayerIdx());
				if(board.checkBoard()) {
					println("A tile has been added to your hand from deck", getCurPlayerIdx());
					messageToOtherPlayers(getCurPlayerName() + " ended their turn with out making any moves");
					messageToOtherPlayers("A tile has been added to " + getCurPlayerName() + "'s hand");
					if (deck.getTiles().size() > 0) {
						drawTile(player);
					} else {
						endRound = true;
						println("This is the last turn because the deck is empty!");
					}
				} else {
					println("The board is not correct, 3 tiles have been added to your hand from deck", getCurPlayerIdx());
					messageToOtherPlayers(getCurPlayerName() + "ended their turn with out making any moves");
					messageToOtherPlayers("3 tiles has been added to " + getCurPlayerName() + "'s hand");
					if (deck.getTiles().size() > 0) {
						// Game rules says to pickup 3 tiles if tried to modify board but didn't end up successfully modifying
						for (int i=0; i<3; i++)
							drawTile(player);
					} else {
						endRound = true;
						println("This is the last turn because the deck is empty!");
					}
				}
				println("There are currently: " + deck.getTiles().size() + " tiles left in the deck");
			}
		}
		player.updateHand();  //update original hand to finalize
		player.sortHand(); //sort the updated hand
		println("Your turn has ended", getCurPlayerIdx());
		messageToOtherPlayers(getCurPlayerName() + "'s turn has ended");
		if(isGameOver() || endRound){
			if(endRound){
				println("The round is over because the deck was emptied");
			}
			println("Round is over, get ready for next Round!");
			scorePoints();
			resetRound();
		} else {
			turn++;
			players.get(getCurPlayerIdx()).nextTurn();
		}
		totalTurns++;
		announcePlayersTurn(); // Will announce who's turn it is now
		return true;
	}

	// Reverts the player's hand and the board to the original state
	// as when the turn started
	private void undo(Player curPlayer) {
		curPlayer.resetHand();
		curPlayer.sortHand();
		setBoard();
		println("Your hand has been reset to its original state", getCurPlayerIdx());
		messageToOtherPlayers(getCurPlayerName() + " undid changes made to board");
		println(board.printHelper());

	}

	// Places tiles from active player's hand to the board
	// “p 1 3 4 7”
	public boolean placeTiles(String[] sArr, Player player) {

		// Integer index of tiles on hand
		int[] tilesIdx = new int[sArr.length];
		for (int i=0; i<sArr.length; i++)
			tilesIdx[i] = Integer.parseInt(sArr[i]);
		Arrays.sort(tilesIdx);
		if (player.hasTiles(tilesIdx)) {
			ArrayList<Tile> playerTiles = player.placeTiles(tilesIdx);
			board.addSet(playerTiles);
			commandReceivedMessage("p");
			messageToOtherPlayers(getCurPlayerName() + " placed tile(s)");
			println(board.printHelper());
			return true;
		}
		noSuchTileExistErrorMessage();
		return false;
	}

	// Give tiles from your hand onto a row on the board
	// “g 1 2 3”
	private boolean giveTiles(String[] sArr, Player player) {
		int dstRow = Integer.parseInt(sArr[0])-1;
		int[] tilesIdx = new int[sArr.length-1];
		for (int i=1; i<sArr.length; i++)
			tilesIdx[i - 1] = Integer.parseInt(sArr[i]);
		Arrays.sort(tilesIdx);
		if (player.hasTiles(tilesIdx)) {
			ArrayList<Tile> playerTiles = player.placeTiles(tilesIdx);
			board.addToCurrent(playerTiles,dstRow);
			commandReceivedMessage("g");
			messageToOtherPlayers(getCurPlayerName() + " added a tile to row on the board");
			println(board.printHelper());
			return true;
		}
		noSuchTileExistErrorMessage();
		return false;
	}

	// Move tiles from one row on the board to another row on the board
	// “m 1 2 5 6”
	private boolean moveTiles(String[] sArr) {
		int srcRow = Integer.parseInt(sArr[0])-1;
		int dstRow = Integer.parseInt(sArr[1])-1;
		int[] tilesIdx = new int[sArr.length-2];
		for (int i=2; i<sArr.length; i++)
			tilesIdx[i-2] = Integer.parseInt(sArr[i]);
		Arrays.sort(tilesIdx);
		if (board.hasTiles(srcRow, tilesIdx)) {
			ArrayList<Integer> index = new ArrayList<>();
			for(int num:tilesIdx){
				index.add(num);
			}
			board.combineCurrent(srcRow,dstRow,index);
			commandReceivedMessage("m");
			messageToOtherPlayers(getCurPlayerName() + " moved a tile from one row to another on the board");
			println(board.printHelper());

			return true;
		}
		noSuchTileExistErrorMessage();
		return false;
	}

	// Split a row on the board into 2 rows
	// “s 1 4”
	private boolean splitRow(String[] sArr) {
		int srcRow = Integer.parseInt(sArr[0])-1;
		int splitIdx = Integer.parseInt(sArr[1]);
		board.separateSet(srcRow,splitIdx);
		commandReceivedMessage("s");
		messageToOtherPlayers(getCurPlayerName() + " split a row on the board");
		println(board.printHelper());
		return true;
	}

	public void printCurPlayerHand() {
		println(getCurPlayer().getHandStr(), getCurPlayerIdx());
	}

	public void printPlayersHand(int Index) {
		println(getPlayer(Index).getHandStr(), Index);
	}

	// Functions used by command(..)
	//////////////////////////////////////////////////////////////////////

	// Return current player's hand
	public Hand curPlayerHand() {
		return players.get(getCurPlayerIdx()).getHand();
	}

	// Return current turn number
	public int getTotalTurns() {  return totalTurns; }

	public int getTurn() {  return turn; }

	public int getRound() {  return round; }

	// Update current board with the given board
	public void setOrigBoard() {
		origBoard = new Board();
		for (ArrayList<Tile> row: board.board) {
			ArrayList<Tile> temp = new ArrayList<>();
			temp.addAll(row);
			origBoard.addSet(temp);
		}
	}
	public void setBoard() {
		board = new Board();
		for (ArrayList<Tile> row: origBoard.board) {
			ArrayList<Tile> temp = new ArrayList<>();
			temp.addAll(row);
			board.addSet(temp);
		}
	}

	// return board
	public Board getBoard() {
		return board;
	}

	// return players
	public ArrayList<Player> getPlayers() { return players; }

	public Player getPlayer(int i) { return players.get(i); }

	public int getGameEndingScore() { return gameEndingScore; }
	public void setGameEndingScore(int i) { gameEndingScore = i; }


	public void startText() {
		println("Game has started!");
		println("Total points needed to win is: " + getGameEndingScore());
		println("Type h to get help on playing the game");
		announcePlayersTurn();
	}

	public void announcePlayersTurn() {
		print("It is ");
		print(getCurPlayerName());
		println("'s turn!");
		// Show their hand
		printCurPlayerHand();
	}

	public void commandReceivedMessage (String message) {
		switch (message) {
			case "g" -> println("Tile(s) added to row on the board", getCurPlayerIdx());
			case "p" -> println("Tile(s) placed in row on the board", getCurPlayerIdx());
			case "s" -> println("Row on board was split", getCurPlayerIdx());
			case "m" -> println("Moved tile(s) from one row to another", getCurPlayerIdx());
		}
		println("You can make other moves or end your turn", getCurPlayerIdx());
	}

	public void invalidCommandMessage() {
		println("COMMAND INVALID: press h to get help on how to execute commands", getCurPlayerIdx());
	}

	public void noSuchTileExistErrorMessage() {
		println("UNABLE TO EXECUTE COMMAND: no such tiles exist", getCurPlayerIdx());
	}

	public void messageToOtherPlayers(String message) {
		if (server == null)
			return;
		for (int i = 0; i< players.size(); i ++) {
			if (i != getCurPlayerIdx()) {
				println(message, i);
			}
		}
	}

	public int sumOfTilesPlaced () {
		int origBoardSize = origBoard.getBoardSize();
		int currentBoardSize = board.getBoardSize();
		ArrayList<Tile> tilesPlaced = new ArrayList<>();
		ArrayList<Tile> tilesThereAlready = new ArrayList<>();
		for (int i = 0; i < origBoardSize; i++ ) {
			tilesThereAlready.addAll(origBoard.getRow(i));
		}
		for (int i = 0; i < currentBoardSize; i++) {
			tilesPlaced.addAll(board.getRow(i));
		}
		for (Tile value : tilesThereAlready) {
			tilesPlaced.remove(value);
		}
		int sum = 0;
		for (Tile tile : tilesPlaced)
			sum += tile.getValue();
		return sum;
	}

	// Used in testing code
	public int getNumPlayers() {
		if (numPlayers != players.size())
			throw new RuntimeException("Game's numPlayers != players.size()");
		return numPlayers;
	}
}
