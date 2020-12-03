package Rummikub;

import java.util.ArrayList;
import java.util.Collections;

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

    //get number of tiles in hand
    public int getSize() {
        return tiles.size();
    }

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
    public void sortTiles() {
        Collections.sort(tiles);
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

    public Tile putTile(int t) {
        return tiles.remove(t);
    }

    //send players hand to string *used for output of players hand*
    public String printHand() {
            String string = "";
            for (int i = 0; i < tiles.size(); i++) {
                if (tiles.get(i).getColor() == Tile.Colors.JOKER) {
                    string += tiles.get(i).getColor() + "}  ";
                } else {
                    string += tiles.get(i).getValue() + "  ";
                    string += tiles.get(i).getColor() + "}  ";
                }
            }
            return string;
    }

    public void moveTiles(int tileIndex, int newTileIndex){
        tiles.add(newTileIndex, tiles.remove(tileIndex));
    }

    public boolean compare(Hand origHand){
        if(tiles.size() < origHand.getSize()){
            return true;
        }else {
            return false;
        }
    }
}


