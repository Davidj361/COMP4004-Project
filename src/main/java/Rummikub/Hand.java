package Rummikub;

import java.util.ArrayList;
import java.util.Comparator;

public class Hand{
    protected ArrayList<Tile> tiles;

    // Constructor for generating a hand
    public Hand(Deck deck) {
        tiles = new ArrayList<>();
        //Deal 14 tiles to each player
        for (int j = 0; j < 14; j++)
            addTile(deck.dealTile());
    }
    public Hand(ArrayList<Tile> tiles) {
        this.tiles = new ArrayList<>(tiles);
    }
    public Hand(Hand h) {
        this.tiles = new ArrayList<>(h.tiles);
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

    //sort tiles in players hand
    public void sort() {
        getTiles().sort(new Comparator<>() {
            @Override
            public int compare(Tile tile1, Tile tile2) {
                return Integer.compare(tile1.getValue(), tile2.getValue());
            }
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
        try {
            //noinspection ResultOfMethodCallIgnored
            tiles.get(index);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public boolean hasJoker () {
        for (Tile tile : tiles) {
            if (tile.isJoker())
                return true;
        }
        return false;
    }

    public Tile putTile(int t) {
        return tiles.remove(t);
    }

    public ArrayList<Tile> placeTiles(ArrayList<Integer> tilesIndex) {
        ArrayList<Tile> tileSet = new ArrayList<>();
        for (int i=tilesIndex.size()-1; i>=0; i--) {
            tileSet.add(putTile( tilesIndex.get(i) ));
        }
        return tileSet;
    }

    // Good for debugging output
    public String toString() {
        String string = "";
        for (int i = 0; i < tiles.size(); i++) {
            string = getString(string, i);
        }
        String ret = "";
        ret += string;
        return ret;
    }

    // Used by the game
    public String printHelper() {
        String string = "";
        StringBuilder index = new StringBuilder(); // A string having aligned indexes
        int size = 0;
        for (int i = 0; i < tiles.size(); i++) {
            string = getString(string, i);
            int sz = string.length() - size;
            index.append(String.format("%-" + sz + "s", "[" + (i + 1) + "]"));
            size = string.length();
        }
        String ret = "";
        ret += index.toString() +'\n';
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
        return tiles.size() < origHand.size();
    }
}


