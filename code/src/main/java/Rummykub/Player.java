
package Rummykub;

import java.util.ArrayList;

public class Player {
    private int turn;
    private String name;
    private int score; //score for current round
    private int totalScore;
    private boolean firstPlacement = false;
    private Hand hand;
    public Player (String n) {
        name = n;
        turn = 1;
    }

    public String getName () {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score ) {
        this.score = score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void updateTotalScore ( int scoreForRound) {
        totalScore += scoreForRound;
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

    public void setHand(Hand h) { hand = h; }

    public int sumOfTiles () {
        return hand.sumOfTiles();
    }
    // player loses tiles from hand
    public void putTiles(int[] tilesIndex) {
        for (int t: tilesIndex) {
            hand.putTile(t);
        }
    }
}

