package Rummikub;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DrawTile {
    Game game = new Game(true);

    public ArrayList<Tile> parseTiles (String string) {
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        // Express the Regexp above with the code you wish you had
        String a [] = string.split(",");
        for (int i = 0; i < a.length; i++) {
            String tileIs = a[i].replace("(", "");
            tileIs = tileIs.replace(")", "");
            String b [] = tileIs.split(" ");
            int value  = Integer.parseInt(b[0]);
            String color = b[1];
            Tile tile;
            if(color.compareToIgnoreCase("blue") == 0) {
                tile  = new Tile (value, Tile.Colors.BL);
            }
            else if(color.compareToIgnoreCase("red") == 0) {
                tile  = new Tile (value, Tile.Colors.RE);
            }
            else if(color.compareToIgnoreCase("yellow") == 0) {
                tile  = new Tile (value, Tile.Colors.YE);
            }
            else {
                tile  = new Tile (value, Tile.Colors.BK);
            }
            tiles.add(tile);
        }
        return tiles;
    }

    @Given("Player starts round")
    public void player_starts_round() {
        assertEquals(1, game.getTurn());
        assertTrue(game.playerTurn(0));
    }

    @When("Player finishes turn by sending end turn command")
    public void player_finishes_turn_by_sending_end_turn_command() throws IOException {
        game.command(0, "e");
    }

    @Then("Tile is given to player from the deck")
    public void tile_is_given_to_player_from_the_deck() {
        assertEquals(15, game.curPlayerHand().getSize());
    }

    @Then("Players turn ends")
    public void players_turn_ends() {
        assertEquals(2, game.getTurn());
    }

    @Given("Player has {string} on rack")
    public void player_has_on_rack(String string) {
        Hand hand = new Hand(parseTiles(string));
        game.println(hand.printHand().toString());
        game.setCurHand(hand);
    }

    @Given("There exists a run of {string} on board")
    public void there_exists_a_run_of_on_board(String string) {
        Board board = new Board();
        board.addSet(parseTiles(string));
        game.println(board.printBoard());
        game.setBoard(board);
    }

    @When("Player sends a command for placing a tile of {string} on board but fails")
    public void player_sends_a_command_for_placing_a_tile_of_on_board_but_fails(String string) throws IOException {
        game.command(0, "g 0 1");
        game.println(game.getBoard().printBoard());
    }

    @Then("Tile is given to player from the deck so player has {int} tiles")
    public void tile_is_given_to_player_from_the_deck_so_player_has_tiles(Integer int1) {
        game.println(game.curPlayerHand().printHand().toString());
        assertEquals(int1.intValue(), game.curPlayerHand().getSize());
    }

    @When("Player sends a command for placing tiles of {string} on board but fails")
    public void player_sends_a_command_for_placing_tiles_of_on_board_but_fails(String string) throws IOException {
        String command = "p";
        for (int i=0; i<parseTiles(string).size(); i++)
            command = command + " " + (i+1);
        System.out.println(command);
        game.command(0, command);
        game.println(game.getBoard().printBoard());
    }
}
