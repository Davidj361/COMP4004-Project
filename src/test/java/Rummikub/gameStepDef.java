package Rummikub;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.*;

// Glue code for feature files
public class gameStepDef {
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private Game game = new Game(true); // set testing to true
    private Player player = new Player("joe");

    @Given("^Tiles are \"([^\"]*)\"$")
    public void tiles_are(String arg1) {
        // Express the Regexp above with the code you wish you had
        System.out.println("sequence is " + arg1);
        String a [] = arg1.split(",");
        for (int i = 0; i < a.length; i++) {
            String tileIs = a[i].replace("(", "");
            tileIs = tileIs.replace(")", "");
//            System.out.println("tile is " +tileIs);
            String b [] = tileIs.split(" ");
//            System.out.println("2nd tile" + b[0] + " " + b[1]);
            int value  = Integer.parseInt(b[0]);
            String color = b[1];
//            System.out.println("value is " + value);
//            System.out.println("color is " + color);
            Tile tile  = new Tile (value, parseColor(color));
            tiles.add(tile);
        }
//        System.out.println(tiles);
//        System.out.println("array is " + a[1]);

    }

    public Tile.Colors parseColor (String color) {
        Tile.Colors defaultColor = Tile.Colors.BL;
        if(color.compareToIgnoreCase("blue") == 0) {
            defaultColor = Tile.Colors.BL;
        }
        if(color.compareToIgnoreCase("green") == 0) {
            defaultColor = Tile.Colors.RE;
        }
        if(color.compareToIgnoreCase("yellow") == 0) {
            defaultColor = Tile.Colors.YE;
        }
        if(color.compareToIgnoreCase("black") == 0) {
            defaultColor = Tile.Colors.BK;
        }
        return defaultColor;
    }

    @Then("Tile is a run")
    public void tile_is_a_run(){
        // Write code here that turns the phrase above into concrete actions
        assertTrue(game.isRun(tiles));
    }

    @Then("Tile is not a run")
    public void tile_is_not_a_run() {
        // Write code here that turns the phrase above into concrete actions
        assertFalse(game.isRun(tiles));
    }

    @Then("Tile is a group")
    public void tile_is_a_group(){
        // Write code here that turns the phrase above into concrete actions
        assertTrue(game.isGroup(tiles));
    }

    @Then("Tiles is not a group")
    public void tiles_is_not_a_group() {
        // Write code here that turns the phrase above into concrete actions
        assertFalse(game.isGroup(tiles));
    }

    @When("player tries placing their first placement")
    public void player_tries_placing_their_first_placement() {
        // Write code here that turns the phrase above into concrete actions
        String [] index = {"1","2","3"};
        Hand hand = new Hand(tiles);
        player.setHand(hand);
        game.placeTiles(index,player);
    }

    @Then("First placement is successful")
    public void first_placement_is_successful() {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(player.getFirstPlacement());
    }

    @Then("First placement is NOT successful")
    public void first_placement_is_not_successful() {
        // Write code here that turns the phrase above into concrete actions
        assertFalse(player.getFirstPlacement());
    }
}
