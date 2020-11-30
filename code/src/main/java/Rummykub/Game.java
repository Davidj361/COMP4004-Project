package Rummykub;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Rummykub.Tile.Colors;

public class Game {
	private Server server;
    private boolean gameRunning = false;
    int turn = 0;
    public Deck deck;
    private Board board, origBoard;
    Scanner scanner = new Scanner(System.in);
	//private static enum Actions {display, pick, finalize, undo, take, split};
    private Hand origHand;
	// players and clients indices should match
	// i.e. client[0] -> player[0]
	int numPlayers; // Needed for testing both offline and online
	private ArrayList<Player> players = new ArrayList<Player>();
    private boolean testing; // A useful flag for code when testing


	// Constructors
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

	// TODO Reset all variables
	// Useful for testing and restarting the game
	public void reset() {
		// TODO Have players enter their name and assign that in Client class through networking
		for (int i = 0; i < numPlayers; i++)
			players.add(new Player(Integer.toString(i + 1)));
		gameRunning = true;
		deck = new Deck();
		board = new Board();
	}

    public void start() {
		// reset() Should already be called beforehand
        // Add all players
        for (int i = 0; i < 3; i++) {
            ArrayList<Tile> hand = new ArrayList<Tile>();
            //Deal 14 tiles to each player
            for (int j = 0; j < 14; j++) {
                hand.add(deck.dealTile());
            }
            // TODO: add player to players arraylist
            //Player p = new Player(hand, "NAME");
            //players.add(p);
        }
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

    public Player getWinner(ArrayList<Player> players) {
		Player temp = players.get(0);
		for (int i = 0; i < players.size(); i ++ ) {
			if (players.get(i).getTileNumber() == 0) {
				temp = players.get(i);
			}
		}
		return temp;
	}

	public void scorePoints(ArrayList<Player> players) {
		Player winner = getWinner(players);
		int scoreForWinner = 0;
		for (int i = 0; i < players.size(); i ++) {
			if(players.get(i) != winner) {
				scoreForWinner += players.get(i).sumOfTiles();
				int score = -1 * players.get(i).sumOfTiles();
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
    public boolean isGameOver(Player p) {
    	if (p.getTileNumber() == 0)
    		return true;
    	return false;
	}

	// player draws a tile
	public void drawTile(Player p) { // DUPLICATE FUNCTION NAME
    	p.drawTile(deck);
	}

	public void endTurn(Player p) {
		// Check if everything is valid
		p.nextTurn();
		turn++;
	}

	public void playTurn(Player p) {
		Board temp = new Board();

		// TODO: Game play based on user command

		//if (temp.validityCheck())
		//	board = temp;
	}

    //Check to see if any player has no tiles left in their hand
	// TODO Implement
    /* Commented out because players not yet implemented
    public boolean RoundOver() {
        for (int i = 0; i < 3; i++) {
            if (players.emptyHand()) {
                return true;
            }
        }
        return false;
    }
     */

	// Get who's the current player this turn for indexing purposes
	public int curPlayer() {
		return ((turn-1) % players.size());
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
		if (server != null)
			server.print(str);
		else
			System.out.print(str);
	}

	public boolean command(int player, String input) throws IOException {
		if (!playerTurn(player))
			return false;
		Player curPlayer = players.get(curPlayer());

		String[] sArr = input.split(" ");
		if (input.length() > 1) { // Commands with input arguments
			switch(sArr[0]) {
				case "h": // display player's hand
					help();
					break;
				case "db": // display the board
					board.printBoard();
					break;
				case "dh": // display player's hand
					curPlayer.printHand();
					break;
				case "u": // undo
					undo(curPlayer);
					break;
				case "e": // end turn
					// TODO Need to implement ending turn and validating board & current player's hand
					endTurn(curPlayer);
					break;
			}
		} else { // No arguments to commands
			String[] args = Arrays.copyOfRange(sArr, 1, sArr.length);
			switch(sArr[0]) {
				case "p": // placing tiles from hand onto the board
					placeTiles(args, curPlayer);
					break;
				case "g": // giving tiles to a row on the board
					// TODO Implement
					giveTiles(args);
					break;
				case "m": // moving tiles from one row to another on the board
					// TODO Implement
					moveTiles(args);
					break;
				case "s": // splitting rows on the board
					// TODO Implement
					splitRow(args);
					break;
			}
		}
		return true;
	}

	//////////////////////////////////////////////////////////////////////
	// Functions used by command(..)

	// Print from the help from a file
	// TODO Have a help file and read from it
	private void help() throws IOException {
		File fHelp = new File("resources/help.txt");
		BufferedReader br = new BufferedReader(new FileReader(fHelp));
		String str;
		while ((str = br.readLine()) != null) {
			print(str);
		}
	}

	// Reverts the player's hand and the board to the original state
	// as when the turn started
	// TODO Get origHand and origBoard initialized at every start of a turn
	private boolean undo(Player curPlayer) {
		curPlayer.setHand(origHand);
		board = origBoard;
		return true;
	}

	// Places tiles from active player's hand to the board
	// “p 1 3 4 7”
	private boolean placeTiles(String[] sArr, Player player) {
		// Needs at least 1 tile to place
		if (sArr.length < 1)
			return false;
		int[] tilesIdx = new int[sArr.length];
		for (int i=0; i<sArr.length; i++)
			tilesIdx[i] = Integer.parseInt(sArr[i]);
		/* TODO Implement hasTiles
		if (player.hasTiles(tilesIdx)) {
			player.putTiles(tilesIdx);
			//TODO: putting on board by index
		}
		 */
		// TODO Call place tiles from hand onto board
		return true;
	}

	// Give tiles from your hand onto a row on the board
	// “g 1 2 3”
	private boolean giveTiles(String[] sArr) {
		// Needs at least 1 tile to place and dstRow
		if (sArr.length < 2)
			return false;
		int dstRow = Integer.parseInt(sArr[0]);
		int[] tilesIdx = new int[sArr.length-1];
		for (int i=1; i<sArr.length; i++)
			tilesIdx[i] = Integer.parseInt(sArr[i]);
		// TODO Call give on board
		return true;
	}

	// Move tiles from one row on the board to another row on the board
	// “m 1 2 5 6”
	private boolean moveTiles(String[] sArr) {
		// Needs at least 1 tile to place and dstRow & srcRow
		if (sArr.length < 3)
			return false;
		int srcRow = Integer.parseInt(sArr[0]);
		int dstRow = Integer.parseInt(sArr[1]);
		int[] tilesIdx = new int[sArr.length-2];
		for (int i=2; i<sArr.length; i++)
			tilesIdx[i] = Integer.parseInt(sArr[i]);
		// TODO Call move on board
		return true;
	}

	// Split a row on the board into 2 rows
	// “s 1 4”
	private boolean splitRow(String[] sArr) {
		// Needs a split index and srcRow
		if (sArr.length != 2)
			return false;
		int srcRow = Integer.parseInt(sArr[0]);
		int splitIdx = Integer.parseInt(sArr[1]);
		// TODO Call split on board
		return true;
	}

	// Functions used by command(..)
	//////////////////////////////////////////////////////////////////////
}