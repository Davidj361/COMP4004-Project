package Rummikub;

import java.util.ArrayList;

public class Player {
    private int turn;
    private final String name;
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

    public int handSize() {
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

    public boolean hasTiles(ArrayList<Integer> tilesIndex) {
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
    public ArrayList<Tile> placeTiles(ArrayList<Integer> tilesIndex) {
        return hand.placeTiles(tilesIndex);
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

