package Rummykub;

import java.util.ArrayList;
import java.util.Collections;
import static Rummykub.Tile.Colors;

public class Deck{
    private ArrayList<Tile> totalDeck;

    //Create full deck of all tiles
    public Deck() {
        totalDeck = new ArrayList<Tile>();
        for (int j = 0; j < 4; j++) {
            for (int x = 0; x < 2; x++){
                for (int i = 1; i < 14; i++) {
                    Tile tile = new Tile(i, Colors.values()[j]);
                    totalDeck.add(tile);
                }
            }
        }
        //Shuffle all tiles
        this.shuffleTiles();
    }

    //get the full deck
    public ArrayList<Tile> getTotalDeck(){
        return totalDeck;
    }

    public int size() {
        return totalDeck.size();
    }

    //remove tile from deck
    public Tile dealTile() {
        Tile dealtTile = totalDeck.remove(0);
        return dealtTile;
    }

    //shuffle tiles in the deck
    public void shuffleTiles() {
        Collections.shuffle(totalDeck);
    }
}
