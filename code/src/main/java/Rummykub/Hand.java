
import java.util.ArrayList;
import java.util.Collections;

public class hand{
    protected ArrayList<tile> Tiles;

    public hand(ArrayList<tile> Tiles) {
        this.Tiles = new ArrayList<tile>(Tiles);
    }

    //get number of tiles in hand
    public int getSize() {
        return Tiles.size();
    }

    //add Tile to players hand
    public void addTile(tile Tile) {
        this.Tiles.add(Tile);
    }

    //check to see if players hand is empty
    public boolean isEmpty() {
        if (Tiles.size() < 1) {
            return true;
        }
        return false;
    }

    //sort tiles in players hand
    public void sortTiles() {
        Collections.sort(Tiles);
    }

    //send players hand to string *used for output of players hand*
    @Override
    public String toString() {
            String string = "";
            for (int i = 0; i < Tiles.size(); i++) {
            string += Tiles.get(i).toString();
            }
            return string;
    }
}


