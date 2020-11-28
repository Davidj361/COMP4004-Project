package Rummykub;

import java.util.ArrayList;
import java.util.Scanner;

import Rummykub.Tile.Colors;

public class Game {
    private boolean gameRunning = false;
    int turn = 0;
    public Deck deck;
    private Board board;
    Scanner scanner = new Scanner(System.in);
	private static enum Actions {display, pick, finalize, undo, take, split};

    public Game() {
    }

    //private ArrayList<Player> players = new ArrayList<Player>();
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

    // returns true if player's hand is empty
    public boolean isGameOver(Player p) {
    	if (p.getTileNumber() == 0)
    		return true;
    	return false;
	}

	// player draws a tile
	public void drawTile(Player p) {
    	p.drawTile(deck);
	}

	public void placeTiles (Player player, ArrayList<Tile> tiles) {
		if (player.getFirstPlacement()) {
			//board.putTiles(tiles)
		}
		/**
		else {
			if (hand.sumOfTiles() >= 30) {
				player.setFirstPlacement();
				//board.putTiles(tiles);
			}
		}
		 **/
	}

	public Board placeTiles(Player p, Board b, ArrayList<Tile> tiles, int pos) {
    	if (p.hasTiles(tiles)) {
    		p.putTiles(tiles);
    		//TODO: need to implement locating tiles on board functionality
    		//b.locateTiles(tiles, pos);
		}
		return board;
	}

	public void finishTurn(Player p) {
		p.nextRound();
	}

	public void playTurn(Player p) {
    	Board temp = new Board();

    	// TODO: Game play based on user command

		//if (temp.validityCheck())
		//	board = temp;
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
}