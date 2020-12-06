package Rummikub;

import java.lang.reflect.Array;
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

    public ArrayList<String> printHand() { return hand.printHand(); }

    public boolean getDoneFirstPlacement() { return doneFirstPlacement; }

    public void setFirstPlacement() {
        doneFirstPlacement = true;
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
        for (int index: tilesIndex) {
            if (!hand.hasTile(index))
                return false;
        }
        return true;
    }

    public boolean hasJoker () {
        ArrayList<Tile> tiles = hand.getTiles();
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getColor().equals(Tile.Colors.JOKER))
                return true;
        }
        return false;
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
        hand = new Hand(origHand.getTiles());
    }

    public void updateHand() {
        origHand = new Hand(hand.getTiles());
    }

    public void sortHand() {
        Collections.sort(hand.getTiles(), new Comparator<Tile>() {
            @Override
            public int compare(Tile tile1, Tile tile2){return  Integer.compare(tile1.getValue(), tile2.getValue());}
        });
    }

    public void setHand(Hand h) {
        hand = h;
        origHand = new Hand(hand.getTiles());
    }

    public Hand getOrigHand () {
        return origHand;
    }

    public int sumOfTilesPlaced () {
        ArrayList<Tile> tilesPlaced = new ArrayList<Tile>(origHand.getTiles());
        tilesPlaced.removeAll(hand.getTiles());
        int sum = 0;
        for (int i = 0; i < tilesPlaced.size(); i++) {
            sum += tilesPlaced.get(i).getValue();
        }
        return sum;
    }
}

