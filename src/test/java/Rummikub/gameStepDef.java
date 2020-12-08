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
        if(color.equalsIgnoreCase("blue"))
            defaultColor = Tile.Colors.BL;

        if(color.equalsIgnoreCase("red"))
            defaultColor = Tile.Colors.RE;

        if(color.equalsIgnoreCase("yellow"))
            defaultColor = Tile.Colors.YE;

        if(color.equalsIgnoreCase("black"))
            defaultColor = Tile.Colors.BK;

        if(color.equalsIgnoreCase("Joker"))
            defaultColor = Tile.Colors.JOKER;
        return defaultColor;
    }

    @Given("Game has {int} players")
    public void game_has_players(Integer int1) {
        for(int i = 1; i <= int1; i ++) {
            game.createPlayer("player" + i);
        }
    }

    @Given("Player1 has placed all tiles")
    public void player1_has_placed_all_tiles() {
        tiles = new ArrayList<Tile>();
        Hand hand = new Hand(tiles);
        game.getPlayers().get(0).setHand(hand);
        System.out.println(game.getPlayers().get(0).getTileNumber());
    }

    @Given("Player2 has tiles {string}")
    public void player2_has_tiles(String string) {
        tiles_are(string);
        Hand hand = new Hand(tiles);
        game.getPlayers().get(1).setHand(hand);
    }

    @Given("Player3 has tiles {string}")
    public void player3_has_tiles(String string) {
        tiles = new ArrayList<Tile>();
        tiles_are(string);
        Hand hand = new Hand(tiles);
        game.getPlayers().get(2).setHand(hand);
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("Player1 wins the round with score {int} points")
    public void player1_wins_the_round_with_score_points(Integer int1) {
        assertEquals(game.getWinner(), game.getPlayers().get(0));
        System.out.println("score is " +game.getPlayers().get(0).getTotalScore());
        game.scorePoints();
        System.out.println(game.getPlayers().get(0).getTotalScore());
        assertTrue(game.getPlayers().get(0).getScore() == int1);
    }

    @Then("Player2 finishes the round with {int} points")
    public void player2_finishes_the_round_with_points(Integer int1) {
        System.out.println("player2's score is " +game.getPlayers().get(0).getScore());
        assertTrue(game.getPlayers().get(1).getScore() == int1);
    }

    @Then("Player3 finishes the round with {int} points")
    public void player3_finishes_the_round_with_points(Integer int1) {
        assertTrue(game.getPlayers().get(2).getScore() == int1);
    }
}
