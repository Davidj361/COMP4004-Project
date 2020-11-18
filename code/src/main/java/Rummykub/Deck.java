import java.util.ArrayList;
import java.util.Collections;
import static mypackage.Tile.Colors.*;

public class deck{
    private int size;
    private ArrayList<Tile> totalDeck;
    public static enum colors {GR, RE, BK, BL};


    //Create full deck of all tiles
    public deck() {
        totalDeck = new ArrayList<Tile>();
        for (int j = 0; j < 4; j++) {
            for (int x = 0; x < 2; x++){
                for (int i = 1; i < 14; i++) {
                    Tile Tile = new Tile(i, Colors.values()[j]);
                    totalDeck.add(Tile);

                }
            }


        }
        //Shuffle all tiles
        this.shuffleTiles();
    }

    //get the full deck
    public ArrayList<Tile> gettotalDeck(){
        return totalDeck;
    }

    //get the tiles left in the deck
    public int getTotalTilesInDeck() {
        size = totalDeck.size();
        return size;
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
