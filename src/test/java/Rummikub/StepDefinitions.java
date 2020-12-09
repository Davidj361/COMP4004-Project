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

    public Tile.Colors parseColor(String color) {
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

    // Helper function for parseTilesForIndexes
    // Gets the index of the first tile matching the same value and color
    // off the current player
    private int findTile(int val, Tile.Colors color) {
        Tile ret = null;
        ArrayList<Tile> tiles = game.getCurPlayer().getHand().getTiles();
        for (int i=0; i<tiles.size(); i++) {
            Tile t = tiles.get(i);
            if (t.getValue() == val && t.getColor() == color)
                return i+1; // Because players start indexes at 1, not 0
        }
        // We didn't find a tile so throw an error
        throw new RuntimeException("Did not find a tile index in PlayTurn.findTile(..)");
    }

    // Helper function for parsing
    private Tile parseTile(String str) {
        String tileIs = str.replace("(", "");
        tileIs = tileIs.replace(")", "");
        String b [] = tileIs.split(" ");
        int value  = Integer.parseInt(b[0]);
        String c = b[1];
        Tile.Colors color;
        if(c.compareToIgnoreCase("blue") == 0) {
            color = Tile.Colors.BL;
        } else if(c.compareToIgnoreCase("red") == 0) {
            color = Tile.Colors.RE;
        } else if(c.compareToIgnoreCase("yellow") == 0) {
            color = Tile.Colors.YE;
        } else {
            color = Tile.Colors.BK;
        }
        return new Tile(value, color);
    }

    // Parses the string to get indexes of tiles in the current player's hand
    private ArrayList<Integer> getIndexes(String string) {
        ArrayList<Integer> idx = new ArrayList<Integer>();
        // Express the Regexp above with the code you wish you had
        String a [] = string.split(",");
        for (int i = 0; i < a.length; i++) {
            Tile t = parseTile(a[i]);
            idx.add(findTile(t.getValue(), t.getColor()));
        }
        return idx;
    }

    // Parsing a string to create a list of tiles
    private ArrayList<Tile> createTiles(String string) {
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        // Express the Regexp above with the code you wish you had
        String a [] = string.split(",");
        for (int i = 0; i < a.length; i++) {
            Tile t = parseTile(a[i]);
            tiles.add(t);
        }
        return tiles;
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
        game.setCurHand(hand);
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
        Hand hand = new Hand(createTiles(string));
        game.println(hand.printHand().toString());
        game.setCurHand(hand);
    }

    @Given("There exists a run of {string} on board")
    public void there_exists_a_run_of_on_board(String string) {
        Board board = new Board();
        board.addSet(createTiles(string));
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
        for (int i = 0; i< createTiles(string).size(); i++)
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
        board.addSet(createTiles(string));
        game.println(board.printBoard());
        game.setBoardState(board);
    }

    @Given("Player has {string} on hand")
    public void player_has_on_hand(String string) {
        Hand hand = new Hand(createTiles(string));
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
        board.addSet(createTiles(string));
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
        for (int i = 0; i< createTiles(string).size(); i++)
            command = command + " " + (i+1);
        System.out.println(command);
        game.command(0, command);
    }

    @When("Placed tiles form a run")
    public void placed_tiles_form_a_run() {
        assertTrue(game.isRun(game.getBoard().board.get(0)));
    }

    @When("Player sends a command for placing a run of {string} on board")
    public boolean player_sends_a_command_for_placing_a_run_of_on_board(String string) throws IOException {
        if (!game.isRun(createTiles(string)))
            return false;
        StringBuilder command = new StringBuilder("p");
        ArrayList<Integer> idx = getIndexes(string);
        for (int i: idx)
            command.append(" ").append(i);
        System.out.println(command);
        game.command(0, command.toString());
        return true;
    }

    @When("Player sends a command for placing a group of {string} on board")
    public boolean player_sends_a_command_for_placing_a_group_of_on_board(String string) throws IOException {
        if (!game.isGroup(createTiles(string)))
            return false;
        StringBuilder command = new StringBuilder("p");
        ArrayList<Integer> idx = getIndexes(string);
        for (int i: idx)
            command.append(" ").append(i);
        System.out.println(command);
        game.command(0, command.toString());
        return true;
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
        for (int i = 0; i< createTiles(string).size(); i++)
            command = command + " " + (i+1);
        System.out.println(command);
        game.command(0, command);
        game.println(game.getBoard().printBoard());
    }

    @When("Player sends a command for placing another run of {string} on board")
    public void player_sends_a_command_for_placing_another_run_of_on_board(String string) throws IOException {
        String command = "p";
        for (int i = 0; i< createTiles(string).size(); i++)
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
        for (int i = 0; i< createTiles(string).size(); i++)
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
