package Rummikub;

public class Tile{
    public static enum Colors {BL, RE, YE, BK, JOKER};
    Colors color;
    private int value;
    private String state = " ";

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

    // Check all tiles colours and values
    @Override
    public String toString() {
        return String.format(this.state+ "  " + this.value + "   " + this.color.name() + "}");
    }

}

