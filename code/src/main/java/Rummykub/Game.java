import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private boolean gameRunning = false;
    int turnIndex = 0;
    public deck Deck;
    private board Board;
    Scanner scanner = new Scanner(System.in);

    public Game() {
    }

    //private ArrayList<Player> players = new ArrayList<Player>();
    public void startGame() {
        gameRunning = true;
        Deck = new deck();
        Deck.shuffleTiles();
        Board = new board();


        // Add all players
        for (int i = 0; i < 3; i++) {
            ArrayList<tile> hand = new ArrayList<tile>();
            //Deal 14 tiles to each player
            for (int j = 0; j < 14; j++) {
                hand.add(Deck.dealTile());
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
    public boolean RoundOver() {
        for (int i = 0; i < 3; i++) {
            if (players.emptyHand()) {
                return true;
            }
        }
        return false;
    }
}