package Rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Player {
    private int turn;
    private String name;
    private int score; //score for current round
    private int totalScore;
    private boolean doneFirstPlacement = false; // Has player made their first placement?
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

    public String getHandStr() { return hand.printHelper(); }

    public boolean getDoneFirstPlacement() { return doneFirstPlacement; }

    public void setFirstPlacement() {
        doneFirstPlacement = true;
    }

    public int getTileNumber() {
        return hand.size();
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
        for (int index: tilesIndex) {
            if (!hand.hasTile(index))
                return false;
        }
        return true;
    }

    public boolean hasJoker () {
        return hand.hasJoker();
    }

    // player put tiles from hand
    public ArrayList<Tile> putTiles(int[] tilesIndex) {
        return hand.putTiles(tilesIndex);
    }

    public void resetHand() {
        hand = new Hand(origHand.getTiles());
    }

    public void updateHand() {
        origHand = new Hand(hand.getTiles());
    }

    public void sortHand() {
        hand.sort();
    }

    public void setHand(Hand h) {
        hand = h;
        origHand = new Hand(hand.getTiles());
    }

    public Hand getOrigHand () {
        return origHand;
    }

}

