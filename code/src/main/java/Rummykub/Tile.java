package Rummykub;

public class Tile implements Comparable<Tile> {
    public static enum Colors {GR, RE, BK, BL};
    Colors color;
    private int value;
    private String state = " ";

    //each tile
    public Tile(int value, Colors color) {
        this.value = value;
        this.color = color;
    }

    //Get tile value
    public int getValue() {
        return this.value;
    }

    //get tile color
    public Colors getColor() {
        return this.color;
    }

//Check all tiles colours and values
    @Override
    public String toString() {
        return String.format(this.state+ "  " + this.value + "   " + this.color.name() + "}");
    }

    //Compare tiles with each other 8will be used for checking if tiles are allowed to be placed
    public int compareTo(Tile that) {
        // TODO Auto-generated method stub
        Integer thisValue = value;
        Integer thatValue = value;
        int compare = thisValue.compareTo(thatValue);
        if(compare == 0) {
            return this.color.toString().compareTo(that.color.toString());
        }
        return compare;
    }


}

