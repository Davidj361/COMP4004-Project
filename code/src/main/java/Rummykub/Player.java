
package Rummykub;

import java.util.ArrayList;

public class Player {
    private int round;
    private String name;
    private boolean firstPlacement = false;
    private Hand hand;
    public Player (String n) {
        name = n;
        round = 1;
    }

    public String getName () {
        return name;
    }

    public void printHand() { hand.printHand(); }

    public boolean getFirstPlacement() {
        return firstPlacement;
    }

    public void setFirstPlacement() {
        firstPlacement = true;
    }

    public int getTileNumber() {
        return hand.getSize();
    }

    public void nextRound() {
        round++;
    }

    public void drawTile(Deck d) {
        hand.addTile(d.dealTile());
    }

    /**
     * This function checks if player has the tiles
     * @param tiles to be found on hand
     * @return true if player has all the tiles
     */
    public boolean hasTiles(ArrayList<Tile> tiles) {
        for (Tile t: tiles) {
            if (!hand.hasTile(t))
                return false;
        }
        return true;
    }

    // player loses tiles from hand
    public void putTiles(ArrayList<Tile> tiles) {
        for (Tile t: tiles) {
            hand.putTile(t);
        }
    }
}

