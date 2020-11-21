
package Rummykub;

import java.util.ArrayList;

public class Player {
    private String name;
    private boolean firstPlacement = false;
    private Board board;
    public Player (String n) {
        name = n;
    }

    public String getName () {
        return name;
    }

    public int sumOfTiles (ArrayList<Tile> tiles) {
        int sum = 0;
        for(Tile t: tiles) {
            sum += t.getValue();
        }
        return sum;
    }

    public void placeTile (ArrayList<Tile> tiles) {
        if (firstPlacement) {
            //board.putTiles(tiles)
        }
        else {
            if (sumOfTiles(tiles) >= 30) {
                firstPlacement = true;
                //board.putTiles(tiles);
            }
        }
    }

    public void splitTiles () {

    }
}

