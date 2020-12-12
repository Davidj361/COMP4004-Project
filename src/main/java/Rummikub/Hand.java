package Rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Hand{
    protected ArrayList<Tile> tiles;

    // Constructor for generating a hand
    public Hand(Deck deck) {
        tiles = new ArrayList<Tile>();
        //Deal 14 tiles to each player
        for (int j = 0; j < 14; j++)
            addTile(deck.dealTile());
    }
    public Hand(ArrayList<Tile> tiles) {
        this.tiles = new ArrayList<Tile>(tiles);
    }
    public Hand(Hand h) {
        this.tiles = new ArrayList<Tile>(h.tiles);
    }

    //get number of tiles in hand
    public int size() {
        return tiles.size();
    }

    public ArrayList<Tile> getTiles () {
        return tiles;
    }

    public Tile getTile(int i) { return tiles.get(i); }

    public void setTile(int i, Tile t) { tiles.set(i, t); }

    //add Tile to players hand
    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    //check to see if players hand is empty
    public boolean isEmpty() {
        if (tiles.size() < 1) {
            return true;
        }
        return false;
    }

    //sort tiles in players hand
    public void sort() {
        Collections.sort(getTiles(), new Comparator<Tile>() {
            @Override
            public int compare(Tile tile1, Tile tile2){return  Integer.compare(tile1.getValue(), tile2.getValue());}
        });
    }

    public int sumOfTiles() {
        int count = 0;
        for (Tile t: tiles) {
            count += t.getValue();
        }
        return count;
    }

    public boolean hasTile(int index) {
        if (tiles.size() >= index && tiles.get(index - 1) != null)
            return true;
        return false;
    }

    public boolean hasJoker () {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getColor().equals(Tile.Colors.JOKER))
                return true;
        }
        return false;
    }
    
    public Tile putTile(int t) {
        return tiles.remove(t);
    }

    public ArrayList<Tile> putTiles(int[] tilesIndex) {
        ArrayList<Tile> tileSet = new ArrayList<Tile>();
        for (int i=tilesIndex.length-1; i>=0; i--) {
            tileSet.add(putTile(tilesIndex[i] - 1));
        }
        return tileSet;
    }

    // Good for debugging output
    public String toString() {
            String string = "";
            int size = 0;
            for (int i = 0; i < tiles.size(); i++) {
                string = getString(string, i);
                int sz = string.length() - size;
                size = string.length();
            }
            String ret = "";
            ret += string;
            return ret;
    }

    // Used by the game
    public String printHelper() {
        String string = "";
        String index = ""; // A string having aligned indexes
        int size = 0;
        for (int i = 0; i < tiles.size(); i++) {
            string = getString(string, i);
            int sz = string.length() - size;
            index += String.format("%-"+sz+"s", "["+ (i+1) +"]");
            size = string.length();
        }
        String ret = "";
        ret += index+'\n';
        ret += string;
        return ret;
    }

    private String getString(String string, int i) {
        if (tiles.get(i).getColor() != Tile.Colors.JOKER)
            string += tiles.get(i).getValue() + "  ";
        string += tiles.get(i).getColor() + "}  ";
        return string;
    }

    public boolean compare(Hand origHand){
        if(tiles.size() < origHand.size()){
            return true;
        }else {
            return false;
        }
    }
}


