@endRoundFeature
Feature: winGameFeature, Players can win the entire game if they pass the gameEndingScore.

  @endRound_1
  Scenario: End round, then end game because player reaches the game ending score
    Given New game is started
    And New game is started with 3 players
    And Player 1 has placed all tiles
    And Player 2 has "(7 yellow),(11 blue),(5 red)" in their hand
    And Player 3 has "(6 red),(9 blue),(0 Joker)" in their hand
    And Game ends when a player reaches a score of 50
    Then Player 1 wins the round with score 68 points
    And Player 2 finishes the round with -23 points
    And Player 3 finishes the round with -45 points
    And Score reaches winning threshold
    And Player 1 wins the game
#   And Game closes

  @endRound_2
  Scenario: End round and go to next round
    Given New game is started
    And New game is started with 2 players
    And Player 1 has placed all tiles
    And Player 2 has "(7 yellow),(11 blue),(5 red)" in their hand
    And Game ends when a player reaches a score of 50
    Then Player 1 wins the round with score 23 points
    And Player 2 finishes the round with -23 points
    And Score does not reach winning threshold
    And Game goes to round 2

  @endRound_1
  Scenario: End round when the deck is empty and go to next round
    Given A game with 2 players is being played
    And The deck is empty
    And Player 1 has "(7 yellow),(11 blue),(5 red)" in their hand
    And Player 2 has "(6 red),(9 blue),(5 red)" in their hand
    And Game ends when a player reaches a score of 50
    Then Player 2 wins the round with score 23 points
    And Player 1 finishes the round with -23 points
    And Score does not reach winning threshold
    And Game goes to round 2

  @endRound_1
  Scenario: End round when the deck is empty and end game because player reaches the game ending score
    Given A game with 2 players is being played
    And The deck is empty
    And Player 1 has "(7 yellow),(11 blue),(5 red),(10 blue),(11 red),(11 blue)" in their hand
    And Player 2 has "(6 red),(9 blue),(5 red)" in their hand
    And Game ends when a player reaches a score of 50
    Then Player 2 wins the round with score 55 points
    And Player 1 finishes the round with -55 points
    And Score reaches winning threshold
    And Player 2 wins the game

