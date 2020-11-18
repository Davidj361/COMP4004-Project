package Rummykub;

import java.util.ArrayList;
import java.util.Scanner;

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
        deck.shuffleTiles();
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

    public void checkRun( ArrayList<Tile> run) {

    }

    public void checkGroup( ArrayList<Tile> group ) {

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