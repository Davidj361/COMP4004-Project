package Rummikub;

import java.util.ArrayList;
import java.util.Collections;
import static Rummikub.Tile.Colors;

public class Deck{
    private ArrayList<Tile> tiles;

    //Create full deck of all tiles
    public Deck() {
        tiles = new ArrayList<Tile>();
        for (int j = 0; j < 4; j++) {
            for (int x = 0; x < 2; x++){
                for (int i = 1; i < 14; i++) {
                    Tile tile = new Tile(i, Colors.values()[j]);
                    tiles.add(tile);
                }
            }
        }
        Tile joker1 = new Tile(0, Colors.values()[4]);
        Tile joker2 = new Tile(0, Colors.values()[4]);
        tiles.add(joker1);
        tiles.add(joker2);
        //Shuffle all tiles
        this.shuffleTiles();
    }

    //get the full deck
    public ArrayList<Tile> getTiles(){
        return tiles;
    }

    public int size() {
        return tiles.size();
    }

    //remove tile from deck
    public Tile dealTile() {
        Tile dealtTile = tiles.remove(0);
        return dealtTile;
    }

    //shuffle tiles in the deck
    public void shuffleTiles() {
        Collections.shuffle(tiles);
    }
}
