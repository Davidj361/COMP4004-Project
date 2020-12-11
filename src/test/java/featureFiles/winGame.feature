@winGameFeature
Feature: winGameFeature, Players can win the entire game if they pass the gameEndingScore.

   Copy pasted from winRound.feature
  @checkWinnerAndPoints
  Scenario: checking get winner and points functionality
    Given New game is started
    And New game is started with 3 players
    And Player 1 has placed all tiles
    And Player 2 has "(7 yellow),(11 blue),(5 red)" in their hand
    And Player 3 has "(6 red),(9 blue),(0 Joker)" in their hand
    Then Player 1 wins the round with score 68 points
    And Player 2 finishes the round with -23 points
    And Player 3 finishes the round with -45 points
    And Score reaches winning threshold
    And Winner and final scores are printed
    And Game closes

  Scenario: End round and go to next round
    Given New game is started
    And New game is started with 2 players
    And Player 1 has placed all tiles
    And Player 2 has "(7 yellow),(11 blue),(5 red)" in their hand
    Then Player 1 wins the round with score 23 points
    And Player 2 finishes the round with -23 points
    And Score does not reach winning threshold
    And Game goes to next round


