
package Rummykub;

import java.util.ArrayList;

public class Player {
    private String name;
    private boolean firstPlacement = false;
    private Hand hand;
    public Player (String n) {
        this.name = n;
    }

    public String getName () {
        return name;
    }

    public boolean getFirstPlacement() {
        return firstPlacement;
    }

    public void setFirstPlacement() {
        firstPlacement = true;
    }

}

