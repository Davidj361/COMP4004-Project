package Rummykub;

import java.util.ArrayList;
import java.util.Scanner;

import Rummykub.Tile.Colors;

public class Game {
    private boolean gameRunning = false;
    int turnIndex = 0;
    public Deck deck;
    private Board board;
    Scanner scanner = new Scanner(System.in);

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