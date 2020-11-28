
package Rummykub;

import java.util.ArrayList;

public class Player {
    private int turn;
    private String name;
    private boolean firstPlacement = false;
    private Hand hand;
    public Player (String n) {
        name = n;
        turn = 1;
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

    public void nextTurn() {
        turn++;
    }

    public void drawTile(Deck d) {
        hand.addTile(d.dealTile());
    }

    // player loses tiles from hand
    public void putTiles(int[] tilesIndex) {
        for (int t: tilesIndex) {
            hand.putTile(t);
        }
    }
}

