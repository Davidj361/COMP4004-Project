package Rummikub;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DrawTile {
    Game game = new Game(true);

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
}
