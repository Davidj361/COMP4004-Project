public class Tile implements Comparable<Tile>{
    private int value;
    private String color;
    public static enum Colors {GR, RE, BK, BL};
    private String state = " ";

    //each tile
    public Tile(int value, Colors color) {
        this.value = value;
        this.color = Color.value();
    }

    //Get tile value
    public int getValue() {
        return this.value;
    }

    //get tile color
    public String getColor() {
        return this.color;
    }

//Check all tiles colours and values
    @Override
    public String toString() {
        String color = "";
        if (this.color.toString() == "RE"){
            color = "RE";
        }else if (this.color.toString() == "BL"){
            color = "BL";
        }else if (this.color.toString() == "GR"){
            color = "GR";
        }else{
            color = "BK";
        }

        return String.format(this.state+ "  " + this.value + "   " + color + "}");

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

