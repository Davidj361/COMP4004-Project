@gameFeature
Feature: Testing for functions in Game Class


  Scenario: checking get winner and points functionality
    Given New game is started
    And New game is started with 3 players
    And Player 1 has placed all tiles
    And Player 2 has "(7 yellow),(11 blue),(5 red)" on hand
    And Player 3 has "(6 red),(9 blue),(0 Joker)" on hand
    Then Player 1 wins the round with score 68 points
    And Player 2 finishes the round with -23 points
    And Player 3 finishes the round with -45 points


  Scenario: Using joker to complete a run
    Given New game is started
    And Player starts turn (not first placement)
    And Player 1 has "(7 yellow),(8 yellow),(0 Joker)" on hand
    When Player sends a command for placing a run of "(7 yellow),(8 yellow),(0 Joker)" on board
    And Player sends a command to end turn
    Then Tiles placed on board successfully

  Scenario: Using joker to complete a group
    Given New game is started
    And Player starts turn (not first placement)
    And Player 1 has "(9 blue),(9 green),(0 Joker)" on hand
    When Player sends a command for placing a group of "(9 blue),(9 green),(0 Joker)" on board
    And Player sends a command to end turn
    Then Tiles placed on board successfully

  Scenario: Using a joker for First Placement
    Given New game is started
    And First tile has not been placed
    And Player 1 has "(10 red),(11 red),(0 joker)" on hand
    When Player sends a command for placing "(10 red),(11 red),(0 joker)" tiles on board
    And Player sends a command to end turn
    Then First placement is successful

  Scenario: Player retrieves joker from board
    Given New game is started
    And Player starts turn (not first placement)
    And Player 1 has "(8 blue),(7 black),(7 blue)" on hand
    And There already exists a run of "(8 red),(8 black),(0 Joker)" on board
    When Player sends a command for splitting tiles at index 3
    And Player sends a command for placing a group of "(8 blue)" on board
    And Player sends a command for placing a group of "(7 black),(7 blue)" on board
    And Player sends a command to end turn
    Then Tiles placed on board successfully