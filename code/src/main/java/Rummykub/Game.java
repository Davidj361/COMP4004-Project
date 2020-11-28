package Rummykub;

import java.util.ArrayList;
import java.util.Scanner;

import Rummykub.Tile.Colors;

public class Game {
    private boolean gameRunning = false;
    int turnIndex = 0;
    public Deck deck;
    private Board board;
    private Hand hand;
	// players and clients indices should match
	// i.e. client[0] -> player[0]
	private ArrayList<Player> players = new ArrayList<Player>();
    Scanner scanner = new Scanner(System.in);
    private boolean testing; // A useful flag for code


	// Constructors
	public Game() {
		this(false);
	}
	public Game(boolean b) {
		players.add(Player("local"));
		testing = b;
		reset();
	}
	public Game(int i) {
		this(i, false);
	}
	public Game(int i, boolean b) {
		testing = b;
		numPlayers = i;
		reset();
	}
	public Game(Server s) {
		this(s, false);
	}
	public Game(Server s, boolean b) {
		testing = b;
		server = s;
		server.game = this;
		//numPlayers = server.clientsConnected+1;
		reset();
	}


	// TODO Reset all variables
	// Useful for testing and restarting the game
	public void reset() {
	}

    public void startGame() {
        gameRunning = true;
        deck = new Deck();
        board = new Board();

        // Add all players
        for (int i = 0; i < 3; i++) {
            ArrayList<Tile> hand = new ArrayList<Tile>();
            //Deal 14 tiles to each player
            for (int j = 0; j < 14; j++) {
                hand.add(deck.dealTile());
            }
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

	public void placeTile ( Player player, ArrayList<Tile> tiles) {
		if (player.getFirstPlacement()) {
			//board.putTiles(tiles)
		}
		else {
			if (hand.sumOfTiles() >= 30) {
				player.setFirstPlacement();
				//board.putTiles(tiles);
			}
		}
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
    //Check to see if any player has no tiles left in their hand
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

	// Prints the same string to all players
	public void print(String str) {
		if (server != null)
			server.print(str);
		else
			System.out.print(str);
	}

	public boolean command(int player, String input) {
		if (!playerTurn(player))
			return false;

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
					players[i].hand.printHand();
					break;
				case "u": // undo
					undo();
					break;
				case "e": // end turn
					endTurn();
					break;
			}
		} else { // No arguments to commands
			switch(sArr[0]) {
				case "p": // placing tiles from hand onto the board
					placeTiles(sArr);
					break;
				case "g": // giving tiles to a row on the board
					giveTiles(sArr);
					break;
				case "m": // moving tiles from one row to another on the board
					moveTiles(sArr);
					break;
				case "s": // splitting rows on the board
					splitRow(sArr);
					break;
			}
		}
		return true;
	}

	// Print from the help from a file
	// TODO Have a help file and read from it
	private void help() {
		print("WIP help");
	}

	// Reverts the player's hand and the board to the original state
	// as when the turn started
	// TODO Get origHand and origBoard initialized at every start of a turn
	private boolean undo() {
		hand = origHand;
		board = origBoard;
		return true;
	}

	// Places tiles from active player's hand to the board
	private boolean placeTiles(String[] sArr) {
		return true;
	}
}