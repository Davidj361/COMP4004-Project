@networkFeature
Feature: networkFeature, The game is networked and can have up to 4 players playing across the network

  @networkFeature-1
  Scenario: Networked game of 4 players start, 1 player wins the entire game in 1 round
    Given The host hosts a game
    And The clients are connected to host
    And A new networked game is started
    And Set game ending score to 5
    And Player 1 has "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red),(9 red),(10 red),(3 black),(4 black),(5 black),(6 black),(7 black),(8 black)" in their hand
    And Player 2 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    And Player 3 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    And Player 4 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    When Player 1 sends a command for placing tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red),(9 red),(10 red)" on board
    And Player 1 sends a command for ending current turn
    And Player 1 sends a command for ending current turn but fails
    And Player 1 sends a command for displaying hand
    And Player 2 sends a command for ending current turn and receives "(9 red)"
    And Player 2 sends a command for ending current turn but fails
    And Player 3 sends a command for ending current turn and receives "(9 red)"
    And Player 3 sends a command for ending current turn but fails
    And Player 4 sends a command for ending current turn and receives "(9 red)"
    And Player 4 sends a command for ending current turn but fails
    And Player 1 sends a command for placing tiles of "(3 black),(4 black),(5 black),(6 black),(7 black),(8 black)" on board
    And Player 1 sends a command for ending current turn
    And Everyone closes their connections
    Then Player 1 has won the game
    And Player 1 has 225 points from this round
    And Player 1 has 225 total points from the entire game
    And All other players but player 1 have -75 points from this round
    And All other players but player 1 have -75 total points from the entire game
    And There are 2 total rounds

  @networkFeature-2
  Scenario: Networked game of 4 players start, a player wins the game on the 2nd round
    Given The host hosts a game
    And The clients are connected to host
    And A new networked game is started
    And Set game ending score to 450
    And Player 1 has "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red),(9 red),(10 red),(3 black),(4 black),(5 black),(6 black),(7 black),(8 black)" in their hand
    And Player 2 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    And Player 3 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    And Player 4 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    When Player 1 sends a command for placing tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red),(9 red),(10 red)" on board
    And Player 1 sends a command for ending current turn
    And Player 1 sends a command for ending current turn but fails
    And Player 1 sends a command for displaying hand
    And Player 2 sends a command for ending current turn and receives "(9 red)"
    And Player 2 sends a command for ending current turn but fails
    And Player 3 sends a command for ending current turn and receives "(9 red)"
    And Player 3 sends a command for ending current turn but fails
    And Player 4 sends a command for ending current turn and receives "(9 red)"
    And Player 4 sends a command for ending current turn but fails
    And Player 1 sends a command for placing tiles of "(3 black),(4 black),(5 black),(6 black),(7 black),(8 black)" on board
    And Player 1 sends a command for ending current turn
    And Player 1 has won the round
    And Game is not won
    And Player 1 has 225 points from this round
    And All other players but player 1 have -75 points from this round
    And There are 2 total rounds
    And Player 1 has "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red),(9 red),(10 red),(3 black),(4 black),(5 black),(6 black),(7 black),(8 black)" in their hand
    And Player 2 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    And Player 3 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    And Player 4 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    And Player 1 sends a command for placing tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red),(9 red),(10 red)" on board
    And Player 1 sends a command for ending current turn
    And Player 1 sends a command for ending current turn but fails
    And Player 1 sends a command for displaying hand
    And Player 2 sends a command for ending current turn and receives "(9 red)"
    And Player 2 sends a command for ending current turn but fails
    And Player 3 sends a command for ending current turn and receives "(9 red)"
    And Player 3 sends a command for ending current turn but fails
    And Player 4 sends a command for ending current turn and receives "(9 red)"
    And Player 4 sends a command for ending current turn but fails
    And Player 1 sends a command for placing tiles of "(3 black),(4 black),(5 black),(6 black),(7 black),(8 black)" on board
    And Player 1 sends a command for ending current turn
    And Everyone closes their connections
    Then Player 1 has won the game
    And Player 1 has 225 points from this round
    And Player 1 has 450 total points from the entire game
    And All other players but player 1 have -75 points from this round
    And All other players but player 1 have -150 total points from the entire game
    And There are 3 total rounds

  @networkFeature-3
  Scenario: Networked game of 4 players start, a player wins the game and all players try to send commands and interact with the game but fail
    Given The host hosts a game
    And The clients are connected to host
    And A new networked game is started
    And Set game ending score to 5
    And Player 1 has "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red),(9 red),(10 red),(3 black),(4 black),(5 black),(6 black),(7 black),(8 black)" in their hand
    And Player 2 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    And Player 3 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    And Player 4 has "(1 red),(3 red),(5 red),(7 red),(9 red),(1 black),(3 black),(5 black),(7 black),(9 black),(1 yellow),(3 yellow),(5 yellow),(7 yellow)" in their hand
    When Player 1 sends a command for placing tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red),(9 red),(10 red)" on board
    And Player 1 sends a command for ending current turn
    And Player 1 sends a command for ending current turn but fails
    And Player 1 sends a command for displaying hand
    And Player 2 sends a command for ending current turn and receives "(9 red)"
    And Player 2 sends a command for ending current turn but fails
    And Player 3 sends a command for ending current turn and receives "(9 red)"
    And Player 3 sends a command for ending current turn but fails
    And Player 4 sends a command for ending current turn and receives "(9 red)"
    And Player 4 sends a command for ending current turn but fails
    And Player 1 sends a command for placing tiles of "(3 black),(4 black),(5 black),(6 black),(7 black),(8 black)" on board
    And Player 1 sends a command for ending current turn
    Then Player 1 has won the game
    And Player 1 has 225 points from this round
    And Player 1 has 225 total points from the entire game
    And All other players but player 1 have -75 points from this round
    And All other players but player 1 have -75 total points from the entire game
    And There are 2 total rounds
    And Player 1 sends a command for ending current turn but fails
    And Player 2 sends a command for ending current turn but fails
    And Player 3 sends a command for ending current turn but fails
    And Player 4 sends a command for ending current turn but fails
    And Everyone closes their connections