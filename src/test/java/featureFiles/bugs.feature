@bugs
Feature: bugs that need to be tested against

  @bug
  Scenario: BUG Winner doesn't get proper points, winner isn't the proper winner
    Given New game is started with 2 players
    When Player 1 has "(1 yellow),(2 blue),(3 red)" in their hand
    And Player 2 has "(7 yellow),(11 blue),(5 red)" in their hand
    And The deck has 1 Tile
    And Player 1 sends a command for ending current turn
    And Player 2 sends a command for ending current turn
    Then Player 1 finishes the round with 23 points
    And Player 2 finishes the round with -23 points

  @bug2
  Scenario: BUG Winner flip flops when ending a turn
    Given New game is started with 2 players
    When Player 1 has "(1 yellow),(2 blue),(3 red)" in their hand
    And Player 2 has "(7 yellow),(11 blue),(5 red)" in their hand
    And The deck has 1 Tile
    And Player 1 sends a command for ending current turn
    And Player 2 sends a command for ending current turn
    And Player 1 has won the game
    And Player 1 sends a command for ending current turn but fails
    And Player 2 sends a command for ending current turn but fails
    Then Player 1 finishes the round with 23 points
    And Player 2 finishes the round with -23 points

  @bug3
  Scenario: BUG When player places valid tiles but hasn't done first placement and <30 points of tiles, 3 tiles aren't picked up
    Given New game is started
    When Player has "(1 yellow),(2 yellow),(3 yellow),(9 black)" in their hand
    And Player has NOT done First Placement
    And Player sends a command for placing tiles of "(1 yellow),(2 yellow),(3 yellow)" on board
    And Player sends a command for ending current turn
    Then Player has NOT done First Placement
    And Player has 7 tiles
    And There are 2 total turns

  @bug3
  Scenario: BUG when sorting a give if the give tile indexes are backwards
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(10 black),(11 black),(12 black)" on board
    And Player has "(1 yellow),(2 blue),(3 red),(13 black),(12 black),(1 yellow),(2 blue),(3 red),(1 yellow),(2 blue),(3 red),(8 black),(9 black),(3 red)" in their hand
    When Player sends a command for giving tiles of "(9 black),(8 black)" to row 1
    And Board is valid
    And Player sends a command for ending current turn
    Then Board is valid
    And There are 2 total turns

  @bug4
  Scenario: Bug - move tiles with backwards indices
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(6 red),(7 red),(8 red)" on board
    When Player sends a command for moving row 1 indices "1 3 2" to row 2
    And Board is valid
    And Player sends a command for ending current turn
    Then Board is valid