package Rummikub;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class StepDefinitions {
    Game game;
    Server server;
    ArrayList<Client> clients;
    ArrayList<Tile> tiles = new ArrayList<Tile>();


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper functions

    private void tiles_are(String arg1) {
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

    private Tile.Colors parseColor(String color) {
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
        if(c.equalsIgnoreCase("blue")) {
            color = Tile.Colors.BL;
        } else if(c.equalsIgnoreCase("red")) {
            color = Tile.Colors.RE;
        } else if(c.equalsIgnoreCase("yellow")) {
            color = Tile.Colors.YE;
        } else if (c.equalsIgnoreCase("joker")){
            color = Tile.Colors.JOKER;
        }else {
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

    // Gets the index of the first tile the is different
    private int findDifferentTile(Hand bigHand, Hand smallHand) {
        int ret = -1;
        smallHand.sort();
        for (int i=0; i<smallHand.size(); i++) {
            Tile t1 = bigHand.getTile(i);
            Tile t2 = smallHand.getTile(i);
            if (t1 != t2) {
                ret = i;
                break;
            }
        }
        // Must be the last element
        if (ret == -1)
            ret = bigHand.size()-1;
        return ret;
    }

    // Helper functions
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Glue Code

    @Given("New game is started")
    public void new_game_is_started() {
        game = new Game(true); // setup the game in testing mode
        assertEquals(1, game.getTurn());
    }

    @Given("New game is started with {int} players")
    public void new_game_is_started_with_players(Integer int1) {
        game = new Game(int1, true); // setup the game in testing mode
        assertEquals(1, game.getTurn());
    }
    

    @Given("Game has {int} players")
    public void game_has_players(int int1) {
        assertEquals(int1, game.getPlayers().size());
    }

    /////////////////
    // Network crud

    @Given("A new networked game is started")
    public void A_new_networked_game_is_started() {
        game = new Game(server, true);
        assertEquals(1, game.getTurn());
    }

    @Given("The host hosts a game")
    public void TheHostHostsAGame() throws IOException {
        // Server(String name, int port, int numPlayers, boolean b)
        server = new Server("HostPlayer", 27015, 4);
        // Setup network
        assertTrue(server.host());
        server.start();
    }

    @Given("The other players connect to host")
    public void The_other_players_connect_to_host() throws InterruptedException, IOException {
        clients = new ArrayList<Client>();
        for (int i = 0; i<server.getMaxClients(); i++) {
            Client c = new Client("Player "+(i+2));
            clients.add(c);
            assertTrue(c.connect());
            //c.start();
            c.sendName();
        }
        while (server.getNamesSet().size() != server.getMaxClients()+1) // Add host's name
            Thread.sleep(10);
        assertEquals(server.getNumClients(), server.getMaxClients());
    }

    @When("Everyone closes their connections")
    public void Everyone_closes_their_connections() throws IOException {
        server.close();
        assertTrue(!server.isOpen());
    }

    // Network crud
    /////////////////


    @Given("First tile has not been placed")
    public void first_tile_has_not_been_placed() {
        // Write code here that turns the phrase above into concrete actions
        assertFalse(game.getCurPlayer().getDoneFirstPlacement());
    }

    @When("Player sends a command for placing {string} tiles on board")
    public void player_sends_a_command_for_placing_tiles_on_board(String string) throws IOException {
        playerSendsACommandForPlacingARunOfOnBoard(1, string);
    }

    @When("Player {int} sends a command for placing a run of {string} on board")
    public void playerSendsACommandForPlacingARunOfOnBoard(int arg0, String str) throws IOException {
        String tiles [] = str.split(",");
        String command = "p";
        for (int i=0; i<tiles.length; i++)
            command = command + " " + (i+1);
        System.out.println(command);
        game.command(arg0-1, command);
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

    @Given("Player {int} has placed all tiles")
    public void player_has_placed_all_tiles(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        tiles = new ArrayList<Tile>();
        Hand hand = new Hand(tiles);
        game.getPlayer(int1 -1 ).setHand(hand);
        System.out.println(game.getPlayers().get(int1 - 1).getTileNumber());
    }

    @Then("Player {int} wins the round with score {int} points")
    public void player_wins_the_round_with_score_points(Integer int1, Integer int2) {
        assertEquals(game.getWinner(), game.getPlayers().get(int1 - 1));
        System.out.println("score is " +game.getPlayers().get(int1 - 1).getTotalScore());
        game.scorePoints();
        System.out.println(game.getPlayers().get(int1 - 1).getTotalScore());
        assertTrue(game.getPlayers().get(int1 - 1).getScore() == int2);
    }

    @Then("Player {int} finishes the round with {int} points")
    public void player_finishes_the_round_with_points(Integer int1, Integer int2) {
        System.out.println("player2's score is " +game.getPlayers().get(int1 - 1).getScore());
        assertTrue(game.getPlayers().get(int1 - 1).getScore() == int2);
    }

    @Given("Player starts turn {int}")
    public void player_starts_round(int int1) {
        assertEquals(int1, game.getTurn());
        assertTrue(game.playerTurn(0));
    }

    @When("Player finishes turn by sending end turn command")
    public void player_finishes_turn_by_sending_end_turn_command() throws IOException {
        game.command(0, "e");
    }

    @Then("Tile is given to player from the deck")
    public void tile_is_given_to_player_from_the_deck() {
        assertEquals(15, game.curPlayerHand().size());
    }

    @Then("Players turn ends")
    public void players_turn_ends() {
        assertEquals(2, game.getTurn());
    }

    @Given("Player has {string} in their hand")
    public void player_has_in_their_hand(String string) {
        // tiles_are(string); // Might need to add back in later
        Hand hand = new Hand(createTiles(string));
        game.println(hand.toString());
        game.setCurHand(hand);
    }

    @Given("Player {int} has {string} on hand")
    public void playerHasOnHand(int i, String str) {
        Hand hand = new Hand(createTiles(str));
        game.println(hand.toString());
        game.getPlayers().get(i-1).setHand(hand);
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
    public void tile_is_given_to_player_from_the_deck_so_player_has_tiles(int int1) {
        game.println(game.curPlayerHand().toString());
        assertEquals(int1, game.curPlayerHand().size());
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

    @Given("Player starts turn \\(not first placement)")
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

    @When("Player sends a command for placing a tile of {string} on board")
    public void player_sends_a_command_for_placing_a_tile_of_on_board(String string) throws IOException {
        game.command(0, "g 0 1");
    }

    @When("Placed tiles form a run on row {int}")
    public void placed_tile_form_a_run_on_row(int int1) {
        assertTrue(game.isRun(game.getBoard().board.get(int1)));
    }

    @When("Player sends a command for ending current turn")
    public void player_sends_a_command_for_ending_current_turn() throws IOException {
        playerSendsACommandForEndingCurrentTurn(1);
    }

    @When("Player {int} sends a command for ending current turn")
    public void playerSendsACommandForEndingCurrentTurn(int arg0) throws IOException {
        game.command(arg0-1, "e");
    }

    @When("Player {int} sends a command for ending current turn and receives {string}")
    public void playerSendsACommandForEndingCurrentTurnAndReceives(int arg0, String arg1) throws IOException {
        int idx = arg0-1;
        Hand h1 = new Hand(game.getPlayer(idx).getHand());
        game.command(idx, "e");
        // Re-assign the random drawn tile to our hard coded tile given by arg1
        Hand h2 = game.getPlayer(idx).getHand();
        int i = findDifferentTile(h2, h1);
        Tile t = parseTile(arg1);
        h2.getTiles().set(i, t);
    }

    @Then("Tiles placed on board successfully")
    public void tiles_placed_on_board_successfully() {
        assertTrue(game.getBoard().checkBoard());
    }

    @Then("There are {int} total turns")
    public void thereAreTotalTurns(int arg0) {
        assertEquals(arg0, game.getTurn());
    }

    @Then("There are {int} total rounds")
    public void thereAreTotalRounds(int arg0) {
        assertEquals(arg0, game.getRound());
    }

    @Given("There already exists a group of {string} on board")
    public void there_already_exists_a_group_of_on_board(String string) {
        Board board = new Board();
        board.addSet(createTiles(string));
        game.println(board.printBoard());
        game.setBoardState(board);
    }

    @When("Placed tiles form a group on row {int}")
    public void placed_tile_form_a_group(int int1) {
        assertTrue(game.isGroup(game.getBoard().board.get(int1)));
    }

    @When("Player sends a command for placing tiles of {string} on board")
    public void player_sends_a_command_for_placing_tiles_of_on_board(String string) throws IOException {
        String command = "g 0";
        for (int i = 0; i< createTiles(string).size(); i++)
            command = command + " " + (i+1);
        System.out.println(command);
        game.command(0, command);
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

    @Then("Player has {int} tiles")
    public void player_has_tiles(int int1) {
        game.println(game.curPlayerHand().toString());
        assertEquals(int1, game.curPlayerHand().size());
    }

    @When("Player sends a command for undoing the previous action")
    public void player_sends_a_command_for_undoing_the_previous_action() throws IOException {
        game.command(0, "u");
        game.println(game.getBoard().printBoard());
        game.printCurPlayerHand();
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

    @Then("Player has {int} tile")
    public void player_has_tile(int int1) {
        game.println(game.curPlayerHand().toString());
        assertEquals(int1, game.curPlayerHand().size());
    }

    @When("Player sends a command for splitting tiles of {string} into a new row")
    public void player_sends_a_command_for_splitting_tiles_of_into_a_new_row(String string) throws IOException {
        game.command(0, "s 0 3");
    }

    @When("Player sends a command for splitting tiles at index {int}")
    public void player_sends_a_command_for_splitting_tiles_at_index(Integer int1) throws IOException {
        game.command(0,"s 0 "+ int1 );
    }

    @When("Player sends a command for placing a tile of {string} together with splitted tiles")
    public void player_sends_a_command_for_placing_a_tile_of_together_with_splitted_tiles(String string) throws IOException {
        game.command(0, "g 1 1");
        game.println(game.getBoard().printBoard());
    }

    @Given("There already exists another run of {string} on board")
    public void there_already_exists_another_run_of_on_board(String string) {
        Board board = game.getBoard();
        board.addSet(createTiles(string));
        game.println(board.printBoard());
        game.setBoardState(board);
    }

    @When("Player sends a command for splitting a tile of {string} into a new row")
    public void player_sends_a_command_for_splitting_a_tile_of_into_a_new_row(String string) throws IOException {
        game.command(0, "s 0 3");
        game.println(game.getBoard().printBoard());
    }

    @When("Player sends a command for splitting the second tile of {string} into a new row")
    public void player_sends_a_command_for_splitting_the_second_tile_of_into_a_new_row(String string) throws IOException {
        game.command(0, "s 1 3");
        game.println(game.getBoard().printBoard());
    }

    @When("Player sends a command for combining the second and third row and placing a tile of {string} together with the third row")
    public void player_sends_a_command_for_placing_a_tile_of_together_with_the_third_row(String string) throws IOException {
        game.command(0, "m 3 2 1");
        game.command(0, "g 2 1");
        game.println(game.getBoard().printBoard());
    }

    @When("Player sends a command for placing tiles of {string} together with splitted tiles")
    public void player_sends_a_command_for_placing_tiles_of_together_with_splitted_tiles(String string) throws IOException {
        game.command(0, "g 1 1 2");
        game.println(game.getBoard().printBoard());
    }

    @When("Player sends a command for placing a tile of {string} together with the second row")
    public void player_sends_a_command_for_placing_a_tile_of_together_with_the_second_row(String string) throws IOException {
        game.command(0, "g 1 1");
        game.println(game.getBoard().printBoard());
    }

    @Given("Player sends a command for moving the first row into the second row to combine them")
    public void player_sends_a_command_for_moving_the_first_row_into_the_second_row_to_combine_them() throws IOException {
        game.command(0, "m 1 0 1 2 3");
    }

    @Given("The game has a game ending score at {int}")
    public void theGameHasAGameEndingScoreAt(int arg0) {
        assertEquals(arg0, game.getGameEndingScore());
    }

    @Given("Set game ending score to {int}")
    public void setGameEndingScoreTo(int arg0) {
        game.setGameEndingScore(arg0);
    }

    @Then("Player {int} has won the game")
    public void playerHasWonTheGame(int arg0) {
        Player winner = game.getFinalWinner();
        assertNotEquals(null, winner);
        Player p = game.getPlayers().get(arg0-1);
        assertEquals(p, winner);
    }

    @Then("Player {int} has {int} points")
    public void playerHasPoints(int arg0, int arg1) {
        int scr = game.getPlayer(arg0-1).getScore();
        assertEquals(arg1, scr);
    }

    @Then("All other players but player {int} have {int} points")
    public void allOtherPlayersButPlayerHavePoints(int arg0, int arg1) {
        for (int i=0; i<game.getPlayers().size(); i++) {
            if (i == arg0-1)
                continue;
            int scr = game.getPlayer(i).getScore();
            assertEquals(arg1, scr);
        }
    }

}