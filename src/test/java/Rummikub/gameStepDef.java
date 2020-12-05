package Rummikub;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

// Glue code for feature files
public class gameStepDef {
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private Game game = new Game(true); // set testing to true
//    private Player player = new Player("joe");

    public void tiles_are(String arg1) {
        System.out.println("sequence is " + arg1);
        String a [] = arg1.split(",");
        for (int i = 0; i < a.length; i++) {
            String tileIs = a[i].replace("(", "");
            tileIs = tileIs.replace(")", "");
            String b [] = tileIs.split(" ");
            int value  = Integer.parseInt(b[0]);
            String color = b[1];
            Tile tile  = new Tile (value, parseColor(color));
            tiles.add(tile);
        }
    }

    public Tile.Colors parseColor (String color) {
        Tile.Colors defaultColor = Tile.Colors.BL;
        if(color.compareToIgnoreCase("blue") == 0) {
            defaultColor = Tile.Colors.BL;
        }
        if(color.compareToIgnoreCase("red") == 0) {
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

    @Given("First tile has not been placed")
    public void first_tile_has_not_been_placed() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(game.curPlayerObj().getFirstPlacement());
        assertFalse(game.curPlayerObj().getFirstPlacement());
    }

    @Given("Player has {string} in hand")
    public void player_has_in_hand(String string) {
        // Write code here that turns the phrase above into concrete actions
        tiles_are(string);
        Hand hand = new Hand(tiles);
        game.setCurHand(hand);
        System.out.println("hand " + game.curPlayerObj().getName());
        System.out.println("original hand " +game.curPlayerObj().getOrigHand().getSize());
    }

    @When("Player sends a command for placing {string} tiles on board")
    public void player_sends_a_command_for_placing_tiles_on_board(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        String command = "p";
        for (int i=0; i<3; i++)
            command = command + " " + (i+1);
        System.out.println(command);
        game.command(0, command);
    }
    @When("Player sends a command to end turn")
    public void player_sends_a_command_to_end_turn() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        game.command(0, "e");
    }

    @Then("First placement is successful")
    public void first_placement_is_successful() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(game.curPlayerObj().getFirstPlacement());
        assertTrue(game.curPlayerObj().getFirstPlacement());
    }

    @Then("First placement is NOT successful")
    public void first_placement_is_not_successful() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(game.curPlayerObj().getFirstPlacement());
        assertFalse(game.curPlayerObj().getFirstPlacement());
    }
}
