@networkFeature
Feature: networkFeature, The game is networked and can have up to 4 players playing across the network

  @gamePlay
  Scenario: game starts, players play turns where Player 1 wins in 1 round

    Given The host hosts a game
    And The other players connect to host
    And A new networked game is started
    # And All players see the game's start text # Might add this back in later
    And Set game ending score to 5
    # Give 14 tiles to all players
    And Player 1 has "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red),(9 red),(10 red),(3 black),(4 black),(5 black),(6 black),(7 black),(8 black)" in their hand
    And Player 2 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    And Player 3 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    And Player 4 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand

    When Player 1 sends a command for placing a run of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red),(9 red),(10 red)" on board
    And Player 1 sends a command for ending current turn
    # Added duplicate end turns for each player for testing
    And Player 1 sends a command for ending current turn but fails
    And Player 2 sends a command for ending current turn and receives "(9 red)"
    And Player 2 sends a command for ending current turn but fails
    And Player 3 sends a command for ending current turn and receives "(9 red)"
    And Player 3 sends a command for ending current turn but fails
    And Player 4 sends a command for ending current turn and receives "(9 red)"
    And Player 4 sends a command for ending current turn but fails
    And Player 1 sends a command for placing a run of "(3 black),(4 black),(5 black),(6 black),(7 black),(8 black)" on board
    # Player should have no more tiles so he wins the round and the game since 5 points
    And Player 1 sends a command for ending current turn
    And Everyone closes their connections

    Then Player 1 has won the game
    # And The game is finished # Previous line does this but might add this back in
    # TODO Change points
    And Player 1 has 225 points
    And All other players but player 1 have -75 points
    And There are 2 total rounds