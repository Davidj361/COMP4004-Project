package Rummikub;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class StepDefinitions {
    Game game;
    Server server;
    ArrayList<Client> clients;
    // TODO Remove tiles, should be handled via argument passing with functions and returns
    ArrayList<Tile> tiles = new ArrayList<Tile>();
    ArrayList<App> apps; // For testing startGame use case


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper functions

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

    private boolean placeCommand(int playerIdx, String str) throws IOException {
        StringBuilder command = new StringBuilder("p");
        ArrayList<Integer> idx = getIndexes(str);
        for (int i: idx)
            command.append(" ").append(i);
        System.out.println(command);
        return game.command(playerIdx, command.toString());
    }

    private App getApp(int i) { return apps.get(i-1); }

    private void reset() {
        tiles = new ArrayList<Tile>();
        apps = null;
        clients = null;
        game = null;
        server = null;
    }

    // Helper functions
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Glue Code

    @Given("New game is started")
    public void new_game_is_started() {
        reset();
        game = new Game(true); // setup the game in testing mode
        assertEquals(1, game.getTurn());
    }

    @Given("New game is started with {int} players")
    public void new_game_is_started_with_players(int int1) {
        reset();
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
        reset();
        // Server(String name, int port, int numPlayers, boolean b)
        server = new Server("HostPlayer", 27015, 4);
        // Setup network
        assertTrue(server.host());
        server.start();
    }

    @And("The clients are connected to host")
    public void The_other_players_connect_to_host() throws InterruptedException, IOException {
        clients = new ArrayList<Client>();
        for (int i = 0; i<server.getMaxClients(); i++) {
            Client c = new Client("Client"+(i+1));
            clients.add(c);
            assertTrue(c.connect());
            c.sendName();
        }
        while (server.getNamesSet().size() != server.getMaxClients()+1) // Add host's name
            Thread.sleep(10);
        assertEquals(server.getNumClients(), server.getMaxClients());
    }

    @And("Everyone closes their connections")
    public void Everyone_closes_their_connections() throws IOException {
        if (server != null) {
            server.close();
            assertFalse(server.isOpen());
            server = null;
        }
        if (clients != null && clients.size() > 0) {
            for (Client c: clients)
                c.disconnect();
            clients = null;
        }
    }

    // Network crud
    /////////////////

    ////////////////
    // startGame

    // startGame.feature
    @Given("{int} people startup Rummikub")
    public void peopleStartupRummikub(int arg0) {
        reset();
        apps = new ArrayList<App>();
        clients = new ArrayList<Client>();
        game = null;
        server = null;
        for (int i=0; i<arg0; i++)
            apps.add(new App(true));
    }

    // startGame.feature
    @Given("Player {int} is named {string}")
    public void playerIsNamed(int arg0, String arg1) {
        getApp(arg0).name = arg1;
    }

    @Given("Player {int} chooses port {int}")
    public void playerChoosesPort(int arg0, int arg1) {
        getApp(arg0).port = arg1;
    }

    @When("Player {int} chooses to be host")
    public void playerChoosesToBeHost(int arg0) {
        getApp(arg0).state = 1;
    }

    @When("Player {int} chooses to be a client")
    public void playerChoosesToBeAClient(int arg0) {
        getApp(arg0).state = 2;
    }

    @When("Player {int} wants {int} players for their game")
    public void playerWantsPlayersForTheirGame(int arg0, int arg1) {
        getApp(arg0).numberOfPlayers = arg1;
    }

    @When("Player {int} wants the game to end at {int} points")
    public void playerWantsTheGameToEndAtPoints(int arg0, int arg1) {
        getApp(arg0).gameEndingScore = arg1;
    }

    @When("Player {int} chooses a destination IP of {string}")
    public void playerChoosesADestinationIPOf(int arg0, String arg1) {
        getApp(arg0).ip = arg1;
    }

    @Then("Everyone is connected")
    public void everyoneIsConnected() throws InterruptedException {
        assertEquals(server.getNumClients(), server.getMaxClients());
        assertEquals(apps.size(), game.getNumPlayers());
    }

    @Then("Player {int} starts hosting and clients connect")
    public void playerStartsHostingAndClientsConnect(int arg0) throws IOException, InterruptedException {
        server = getApp(arg0).startHost();
        for (int i = 0; i<apps.size(); i++) {
            if (apps.get(i).state != 2) // Have they chose the client option?
                continue;
            assertTrue(apps.get(i).client());
            clients.add(apps.get(i).client);
        }
        assertTrue(getApp(arg0).host());
        game = getApp(arg0).game;
    }

    @Then("Player {int} tries to connect but fails")
    public void playerTriesToConnectButFails(int arg0) throws IOException, InterruptedException {
        assertFalse(getApp(arg0).client());
    }
    // startGame
    ////////////////

    @And("Player has done First Placement")
    public void playerHasDoneFirstPlacement() {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(game.getCurPlayer().getDoneFirstPlacement());
    }

    @And("Player has NOT done First Placement")
    public void playerHasNotDoneFirstPlacement() {
        assertFalse(game.getCurPlayer().getDoneFirstPlacement());
    }

    @Given("Player {int} has placed all tiles")
    public void player_has_placed_all_tiles(int int1) {
        // Write code here that turns the phrase above into concrete actions
        tiles = new ArrayList<Tile>();
        Hand hand = new Hand(tiles);
        game.getPlayer(int1 -1 ).setHand(hand);
        System.out.println(game.getPlayer(int1 - 1).handSize());
    }

    // TODO DUPLICATED TEST FUNCTION
    @Then("Player {int} wins the round with score {int} points")
    public void player_wins_the_round_with_score_points(int int1, int int2) {
        assertEquals(game.getWinner(), game.getPlayer(int1 - 1));
        System.out.println("score is " +game.getPlayer(int1 - 1).getTotalScore());
        game.scorePoints();
        System.out.println(game.getPlayer(int1 - 1).getTotalScore());
        assertEquals(int2, game.getPlayer(int1 - 1).getScore());
    }

    // TODO DUPLICATED TEST FUNCTION
    @Then("Player {int} finishes the round with {int} points")
    public void player_finishes_the_round_with_points(int int1, int int2) {
        System.out.println("player2's score is " +game.getPlayer(int1 - 1).getScore());
        assertEquals(int2, game.getPlayer(int1 - 1).getScore());
    }

    @And("It is player's turn")
    public void itIsPlayerSTurn() {
        assertTrue(game.playerTurn(0));
    }

    @When("The deck is empty")
    public void theDeckIsEmpty() {
        game.deck.tiles = new ArrayList<Tile>();
        assertEquals(0, game.deck.size());
    }

    // TODO Properly make dynamic
    @When("The deck has {int} Tile")
    public void theDeckHasTile(int arg0) {
        game.deck.tiles = new ArrayList<Tile>();
        game.deck.tiles.add(new Tile(1, Tile.Colors.RE));
        assertEquals(arg0, game.deck.size());
    }

    @Then("Player draws tile\\(s) from the deck so player has {int} tiles")
    public void playerDrawsTileSFromTheDeckSoPlayerHasTiles(int arg0) {
        assertEquals(arg0, game.curPlayerHand().size());
    }

    @Given("Player has {string} in their hand")
    public void playerHasInTheirHand(String string) {
        playerXHasInTheirHand(1, string);
    }

    @Given("Player {int} has {string} in their hand")
    public void playerXHasInTheirHand(int i, String str) {
        Hand hand = new Hand(createTiles(str));
        game.getPlayer(i-1).setHand(hand);
    }

    @Given("There already exists tiles of {string} on board")
    public void thereAlreadyExistsTilesOfXOnBoard(String string) {
        ArrayList<Tile> tiles = createTiles(string);
        game.getBoard().addSet(tiles);
        game.setOrigBoard();
    }

    @Given("Player's first placement is done")
    public void player_starts_round_not_first_placement() {
        game.getCurPlayer().setFirstPlacement();
        assertEquals(true, game.getCurPlayer().getDoneFirstPlacement());
    }

    @When("Board is valid")
    public void boardIsValid() {
        assertTrue(game.getBoard().checkBoard());
    }

    @When("Player sends a command for ending current turn")
    public void player_sends_a_command_for_ending_current_turn() throws IOException {
        playerSendsACommandForEndingCurrentTurn(1);
    }

    @When("Player {int} sends a command for ending current turn")
    public void playerSendsACommandForEndingCurrentTurn(int arg0) throws IOException {
        assertTrue(game.command(arg0-1, "e"));
    }


    @When("Player sends a command for ending current turn but fails")
    public void player_sends_a_command_for_ending_current_turn_but_fails() throws IOException {
        playerSendsACommandForEndingCurrentTurnButFails(1);
    }

    @When("Player {int} sends a command for ending current turn but fails")
    public void playerSendsACommandForEndingCurrentTurnButFails(int arg0) throws IOException {
        assertFalse(game.command(arg0-1, "e"));
    }

    @When("Player {int} sends a command for ending current turn and receives {string}")
    public void playerSendsACommandForEndingCurrentTurnAndReceives(int arg0, String arg1) throws IOException {
        int idx = arg0-1;
        Hand h1 = new Hand(game.getPlayer(idx).getHand());
        playerSendsACommandForEndingCurrentTurn(arg0);
        // Re-assign the random drawn tile to our hard coded tile given by arg1
        Hand h2 = game.getPlayer(idx).getHand();
        int i = findDifferentTile(h2, h1);
        Tile t = parseTile(arg1);
        h2.setTile(i, t);
    }

    @And("There are {int} total turns")
    public void thereAreTotalTurns(int arg0) {
        assertEquals(arg0, game.getTotalTurns());
    }


    @Then("There are {int} total rounds")
    public void thereAreTotalRounds(int arg0) {
        assertEquals(arg0, game.getRound());
    }

    @When("Player sends a command for giving tiles of {string} to row {int}")
    public void player_sends_a_command_for_giving_tiles_of_on_board(String arg0, int arg1) throws IOException {
        player_x_sends_a_command_for_giving_tiles_of_on_board(0, arg0, arg1);
    }

    @When("Player {int} sends a command for giving tiles of {string} to row {int}")
    public void player_x_sends_a_command_for_giving_tiles_of_on_board(int arg0, String arg1, int arg2) throws IOException {
        StringBuilder command = new StringBuilder("g ");
        command.append(arg2);
        ArrayList<Integer> idx = getIndexes(arg1);
        for (int i: idx)
            command.append(" ").append(i);
        System.out.println(command);
        assertTrue(game.command(arg0, command.toString()));
    }

    // Not duplicate
    // Doesn't check specifically if it's a run or group
    @When("Player sends a command for placing tiles of {string} on board")
    public void playerSendsACommandForPlacingTilesOfOnBoard(String arg0) throws IOException {
        assertTrue(placeCommand(0, arg0));
    }

    @When("Player {int} sends a command for placing tiles of {string} on board")
    public void playerSendsACommandForPlacingARunOfOnBoard(int arg0, String str) throws IOException {
        assertTrue(placeCommand(arg0-1, str));
        assertTrue(game.getBoard().checkBoard());
    }

    @Then("Player has {int} tiles")
    public void player_has_tiles(int int1) {
        game.println(game.curPlayerHand().toString());
        assertEquals(int1, game.curPlayerHand().size());
    }

    @When("Player sends a command for undoing the previous action")
    public void player_sends_a_command_for_undoing_the_previous_action() throws IOException {
        assertTrue(game.command(0, "u"));
        game.println(game.getBoard().printHelper());
        game.printCurPlayerHand();
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
        Player p = game.getPlayer(arg0-1);
        assertEquals(p, winner);
    }

    @And("Player {int} has won the round")
    public void playerHasWonTheRound(int arg0) {
        Player winner = game.getWinner();
        assertNotEquals(null, winner);
        Player p = game.getPlayer(arg0-1);
        assertEquals(p, winner);
    }

    @Then("Game is not won")
    public void gameIsNotWon() {
        Player winner = game.getFinalWinner();
        assertNull(winner);
    }

    @Then("Game is won")
    public void gameIsWon() {
        Player winner = game.getFinalWinner();
        assertNotNull(winner);
    }

    @Then("Player {int} has {int} points from this round")
    public void playerHasPoints(int arg0, int arg1) {
        int scr = game.getPlayer(arg0-1).getScore();
        assertEquals(arg1, scr);
    }

    @Then("Player {int} has {int} total points from the entire game")
    public void playerHasTotalPointsFromTheEntireGame(int arg0, int arg1) {
        int scr = game.getPlayer(arg0-1).getTotalScore();
        assertEquals(arg1, scr);
    }

    @Then("All other players but player {int} have {int} points from this round")
    public void allOtherPlayersButPlayerHavePoints(int arg0, int arg1) {
        for (int i=0; i<game.getPlayers().size(); i++) {
            if (i == arg0-1)
                continue;
            int scr = game.getPlayer(i).getScore();
            assertEquals(arg1, scr);
        }
    }

    @Then("All other players but player {int} have {int} total points from the entire game")
    public void allOtherPlayersButPlayerHaveTotalPointsFromTheEntireGame(int arg0, int arg1) {
        for (int i=0; i<game.getPlayers().size(); i++) {
            if (i == arg0-1)
                continue;
            int scr = game.getPlayer(i).getTotalScore();
            assertEquals(arg1, scr);
        }
    }


    @When("Player sends a command for splitting row {int} at index {int}")
    public void player_sends_a_command_for_splitting_row_at_index(int int1, int int2) throws IOException {
        String command = String.format("s %d %d", int1, int2);
        assertTrue(game.command(0, command));
        game.println(game.getBoard().printHelper());
    }

    @When("Player sends a command for moving row {int} indices {string} to row {int}")
    public void player_sends_a_command_for_moving_row_indices_to_row(int int1, String string, int int3) throws IOException  {
        String command = String.format("m %d %d %s", int1, int3, string);
        assertTrue(game.command(0, command));
        game.println(game.getBoard().printHelper());
    }

    @When("Player sends a command for giving tiles of index {int} to row {int} which doesn't exist")
    public void player_sends_a_command_for_giving_tiles_of_index_to_row_which_doesn_t_exist(int int0, int int1) throws IOException  {
        String command = String.format("g %d %s", int1, int0);
        assertFalse(game.command(0, command));
        game.println(game.getBoard().printHelper());
    }

    @And("Game will end when a player reaches a score of {int}")
    public void gameWillEndWhenAPlayerReachesAScoreOf(int arg0) {
        game.setGameEndingScore(arg0);
        assertEquals( arg0, game.getGameEndingScore());
    }

    @And("Player {int} wins the game")
    public void playerWinsTheGame(int arg0) {
        assertEquals(game.getPlayer(arg0 - 1), game.getFinalWinner());
    }

    @And("Game goes to round {int}")
    public void gameGoesToRound(int arg0) {
        assertEquals(arg0 - 1, game.getRound());
    }

    @Then("The game has {int} players")
    public void theGameHasPlayers(int arg0) {
        assertEquals(arg0, game.getNumPlayers());
    }

    @And("Player sends a command for moving row {int} indices {int} to row {int} but fails")
    public void playerSendsACommandForMovingRowIndicesToRowButFails(int int1, int int2, int int3) throws IOException {
        String command = String.format("m %d %d %d", int1, int3, int2 );
        assertFalse(game.command(0, command));
        game.println(game.getBoard().printHelper());
    }

    @When("Player sends a command for displaying board")
    public void player_sends_a_command_for_displaying_board() throws IOException {
        String command = String.format("db");
        assertTrue(game.command(0, command));
    }

    @And("Player sends a command for displaying hand")
    public void playerSendsACommandForDisplayingHand() throws IOException {
        String command = String.format("dh");
        assertTrue(game.command(0, command));
    }
    @And("Player sends a command for displaying help")
    public void playerSendsACommandForDisplayingHelp() throws IOException {
        String command = String.format("h");
        assertTrue(game.command(0, command));
    }

    @And("Player {int} sends a command for displaying hand")
    public void playerSendsACommandForDisplayingHand(int arg0) throws IOException {
        String command = String.format("dh");
        assertTrue(game.command(arg0 -1, command));
    }

    @When("Player sends the command {string}")
    public void playerSendsTheCommand(String arg0) {
        String command = String.format(arg0);
        assertTrue(game.command(0, command));
    }

    @When("Player sends the command {string} which fails")
    public void playerSendsTheCommandWhichFails(String arg0) throws IOException {
        String command = String.format(arg0);
        assertFalse(game.command(0, command));
    }

    @Then("Player sends the command {string} with no out of bounds exception")
    public void playerSendsTheCommandWithNoOutOfBoundsException(String arg0) {
        try {
            String command = String.format(arg0);
            assertTrue(game.command(0, command));
        } catch (IndexOutOfBoundsException e) {
            assertNull(e);
        }
    }

    @Then("Player sends the command {string} with no out of bounds exception but fails")
    public void playerSendsTheCommandWithNoOutOfBoundsExceptionButFails(String arg0) {
        try {
            String command = String.format(arg0);
            assertFalse(game.command(0, command));
        } catch (IndexOutOfBoundsException e) {
            assertNull(e);
        }
    }

    @Then("Player sends the command {string} with no number exception but fails")
    public void playerSendsTheCommandWithNoNumberExceptionButfails(String arg0) {
        try {
            String command = String.format(arg0);
            assertFalse(game.command(0, command));
        } catch (NumberFormatException e) {
            assertNull(e);
        }
    }
}