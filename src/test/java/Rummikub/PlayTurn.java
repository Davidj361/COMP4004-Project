package Rummikub;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayTurn {
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

    @Given("Player starts round \\(not first placement)")
    public void player_starts_round_not_first_placement() {
        assertEquals(1, game.getTurn());
        assertTrue(game.playerTurn(0));
        game.getCurPlayer().setFirstPlacement();
    }

    @Given("There already exists a run of {string} on board")
    public void there_already_exists_a_run_of_on_board(String string) {
        Board board = new Board();
        board.addSet(parseTiles(string));
        game.println(board.printBoard());
        game.setBoard(board);
    }

    @Given("Player has {string} on hand")
    public void player_has_on_hand(String string) {
        Hand hand = new Hand(parseTiles(string));
        game.println(hand.printHand().toString());
        game.setCurHand(hand);
    }

    @When("Player sends a command for placing a tile of {string} on board")
    public void player_sends_a_command_for_placing_a_tile_of_on_board(String string) throws IOException {
        game.command(0, "g 0 1");
    }

    @When("Placed tile form a run")
    public void placed_tile_form_a_run() {
        assertTrue(game.isRun(game.getBoard().board.get(0)));
    }

    @When("Player sends a command for ending current turn")
    public void player_sends_a_command_for_ending_current_turn() throws IOException {
        game.command(0, "e");
    }

    @Then("Tiles placed on board successfully")
    public void tiles_placed_on_board_successfully() {
        assertTrue(game.getBoard().checkBoard());
    }

    @Then("Player ends a turn")
    public void player_ends_a_turn() {
        assertEquals(2, game.getTurn());
    }

    @Given("There already exists a group of {string} on board")
    public void there_already_exists_a_group_of_on_board(String string) {
        Board board = new Board();
        board.addSet(parseTiles(string));
        game.println(board.printBoard());
        game.setBoard(board);
    }

    @When("Placed tile form a group")
    public void placed_tile_form_a_group() {
        assertTrue(game.isGroup(game.getBoard().board.get(0)));
    }

    @When("Player sends a command for placing tiles of {string} on board")
    public void player_sends_a_command_for_placing_tiles_of_on_board(String string) throws IOException {
        String command = "g 0";
        for (int i=0; i<parseTiles(string).size(); i++)
            command = command + " " + (i+1);
        System.out.println(command);
        game.command(0, command);
    }

    @When("Placed tiles form a run")
    public void placed_tiles_form_a_run() {
        assertTrue(game.isRun(game.getBoard().board.get(0)));
    }

    @When("Player sends a command for placing a run of {string} on board")
    public void player_sends_a_command_for_placing_a_run_of_on_board(String string) throws IOException {
        System.out.println("hand: " + game.curPlayerHand().printHand());
        String command = "p";
        for (int i=0; i<parseTiles(string).size(); i++)
            command = command + " " + (i+1);
        System.out.println(command);
        game.command(0, command);
    }

    @When("Player sends a command for placing a group of {string} on board")
    public void player_sends_a_command_for_placing_a_group_of_on_board(String string) throws IOException {
        String command = "p";
        for (int i=0; i<parseTiles(string).size(); i++)
            command = command + " " + (i+1);
        System.out.println(command);
        game.command(0, command);
    }

    @When("Placed tiles form a group")
    public void placed_tiles_form_a_group() {
        assertTrue(game.isGroup(game.getBoard().board.get(0)));
    }

    @When("Placed tiles form another run")
    public void placed_tiles_form_another_run() {
        assertTrue(game.isRun(game.getBoard().board.get(1)));
    }

    @When("Placed tiles form another group")
    public void placed_tiles_form_another_group() {
        assertTrue(game.isGroup(game.getBoard().board.get(1)));
    }

    @When("Player sends a command for placing tiles of {string} but fails")
    public void player_sends_a_command_for_placing_tiles_of_but_fails(String string) throws IOException {
        String command = "p";
        for (int i=0; i<parseTiles(string).size(); i++)
            command = command + " " + (i+1);
        System.out.println(command);
        game.command(0, command);
        game.println(game.getBoard().printBoard());
    }

    @When("Player sends a command for placing another run of {string} on board")
    public void player_sends_a_command_for_placing_another_run_of_on_board(String string) throws IOException {
        String command = "p";
        for (int i=0; i<parseTiles(string).size(); i++)
            command = command + " " + (i+4);
        System.out.println(command);
        game.command(0, command);
    }

    @When("Secondly placed tiles form a run")
    public void secondly_placed_tiles_form_a_run() {
        assertTrue(game.isRun(game.getBoard().board.get(1)));
    }

    @Then("Player has {int} tiles")
    public void player_has_tiles(Integer int1) {
        game.println(game.curPlayerHand().printHand().toString());
        assertEquals(int1.intValue(), game.curPlayerHand().getSize());
    }

    @When("Player sends a command for undoing the previous action")
    public void player_sends_a_command_for_undoing_the_previous_action() throws IOException {
        game.command(0, "u");
        game.println("hand: " + game.curPlayerHand().printHand().toString());
        game.println("board" + game.getBoard().printBoard());
    }
}
