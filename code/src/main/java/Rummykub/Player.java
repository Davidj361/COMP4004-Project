
package Rummykub;

import java.util.ArrayList;

public class Player {
    private int turn;
    private String name;
    private int score; //score for current round
    private int totalScore;
    private boolean firstPlacement = false; // Has player made their first placement?
    private Hand hand, origHand;
    public Player (String n) {
        name = n;
        turn = 1;
    }

    public String getName () {
        return name;
    }

    public Hand getHand() { return hand; }

    public int getScore() { return score; }

    public void setScore(int score ) {
        this.score = score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void updateTotalScore ( int scoreForRound) {
        totalScore += scoreForRound;
    }

    public String printHand() { return hand.printHand(); }

    public boolean getFirstPlacement() { return firstPlacement; }

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

    public int sumOfTiles () {
        return hand.sumOfTiles();
    }

    public boolean hasTiles(int[] tilesIndex) {
        int sum = 0;
        for (int index: tilesIndex) {
            if (!hand.hasTile(index))
                return false;
            sum += hand.tiles.get(index - 1).getValue();
        }
        // if first placement, sum of tile values must be at least 30
        if (!firstPlacement && sum<30)
            return false;
        return true;
    }

    // player put tiles from hand
    public ArrayList<Tile> putTiles(int[] tilesIndex) {
        ArrayList<Tile> tileSet = new ArrayList<Tile>();
        for (int i=tilesIndex.length-1; i>=0; i--) {
            tileSet.add(hand.putTile(tilesIndex[i] - 1));
        }
        return tileSet;
    }

    public void resetHand() {
        hand = origHand;
    }

    public void updateHand() {
        origHand = hand;
    }

    public void setHand(Hand h) {
        origHand = hand = h;
    }
}

