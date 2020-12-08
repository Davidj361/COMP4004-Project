package Rummikub;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class StepDefinitions {
    Game game = new Game(true);
    ArrayList<Tile> tiles = new ArrayList<Tile>();

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
        assertFalse(game.getCurPlayer().getDoneFirstPlacement());
    }

    @Given("Player has {string} in hand")
    public void player_has_in_hand(String string) {
        // Write code here that turns the phrase above into concrete actions
        tiles_are(string);
        Hand hand = new Hand(tiles);
        game.setCurHand(hand);
    }

    @When("Player sends a command for placing {string} tiles on board")
    public void player_sends_a_command_for_placing_tiles_on_board(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        String tiles [] = string.split(",");
        String command = "p";
        for (int i=0; i<tiles.length; i++)
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
        assertTrue(game.getCurPlayer().getDoneFirstPlacement());
    }

    @Then("First placement is NOT successful")
    public void first_placement_is_not_successful() {
        // Write code here that turns the phrase above into concrete actions
        assertFalse(game.getCurPlayer().getDoneFirstPlacement());
    }

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
        game.setBoardState(board);
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
        game.setBoardState(board);
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
        game.setBoardState(board);
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
        assertTrue(game.isRun(game.getBoard().board.get(0)));
    }

    @Then("Player has {int} tiles")
    public void player_has_tiles(Integer int1) {
        game.println(game.curPlayerHand().printHand().toString());
        assertEquals(int1.intValue(), game.curPlayerHand().getSize());
    }

    @When("Player sends a command for undoing the previous action")
    public void player_sends_a_command_for_undoing_the_previous_action() throws IOException {
        game.command(0, "u");
    }

    @When("Secondly placed tiles form a group")
    public void secondly_placed_tiles_form_a_group() {
        assertTrue(game.isGroup(game.getBoard().board.get(0)));
    }

    @When("Player sends a command for placing another group of {string} on board")
    public void player_sends_a_command_for_placing_another_group_of_on_board(String string) throws IOException {
        String command = "p";
        for (int i=0; i<parseTiles(string).size(); i++)
            command = command + " " + (i+4);
        System.out.println(command);
        game.command(0, command);
    }

    @When("Player sends a command for placing a tile of {string} but fails")
    public void player_sends_a_command_for_placing_a_tile_of_but_fails(String string) throws IOException {
        game.command(0, "g 0 1");
        game.println(game.getBoard().printBoard());
    }

    @When("Player sends a command for placing another tile of {string} on board")
    public void player_sends_a_command_for_placing_another_tile_of_on_board(String string) throws IOException {
        game.command(0, "g 0 2");
        game.println(game.getBoard().printBoard());
    }

    @When("Secondly placed tile form a run")
    public void secondly_placed_tile_form_a_run() {
        assertTrue(game.isRun(game.getBoard().board.get(0)));
    }

    @Then("Player has {int} tile")
    public void player_has_tile(Integer int1) {
        game.println(game.curPlayerHand().printHand().toString());
        assertEquals(int1.intValue(), game.curPlayerHand().getSize());
    }

    @When("Secondly placed tile form a group")
    public void secondly_placed_tile_form_a_group() {
        assertTrue(game.isGroup(game.getBoard().board.get(0)));
    }

    @When("Player sends a command for splitting tiles of {string} into a new row")
    public void player_sends_a_command_for_splitting_tiles_of_into_a_new_row(String string) throws IOException {
        game.println("before " + game.getBoard().printBoard());
        game.command(0, "s 0 3");
        game.println("after " + game.getBoard().printBoard());
    }

    @When("Player sends a command for placing a tile of {string} together with splitted tiles")
    public void player_sends_a_command_for_placing_a_tile_of_together_with_splitted_tiles(String string) throws IOException {
        //game.command(0, "g 1 1");
        //game.println(game.getBoard().printBoard());
    }

    @When("There has a new run on board")
    public void there_has_a_new_run_on_board() {

    }
}
