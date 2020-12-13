package Rummikub;

public class Tile{
    public static enum Colors {BL, RE, YE, BK, JOKER}

    Colors color;
    private int value;

    public Tile(int value, Colors color) {
        this.value = value;
        this.color = color;
    }

    //Get tile value
    public int getValue() { return this.value; }

    //get tile color
    public Colors getColor() {
        return this.color;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isJoker() { return getColor().equals(Tile.Colors.JOKER); }

    // Check all tiles colours and values
    @Override
    public String toString() {
        String state = " ";
        return state + "  " + this.value + "   " + this.color.name() + "}";
    }

}

