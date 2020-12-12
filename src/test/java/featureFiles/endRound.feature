@endRoundFeature
Feature: endRoundFeature, Players can win the entire game if they pass the gameEndingScore.

  @endRound_1
  Scenario: End round, then end game because player reaches the game ending score
    Given New game is started with 3 players
    And Player 1 has placed all tiles
    And Player 2 has "(7 yellow),(11 blue),(5 red)" in their hand
    And Player 3 has "(6 red),(9 blue),(0 Joker)" in their hand
    And Set game ending score to 50
    Then Player 1 wins the round with score 68 points
    And Player 2 finishes the round with -23 points
    And Player 3 finishes the round with -45 points
    And Game is won
    And Player 1 wins the game

  @endRound_2
  Scenario: End round and go to next round
    Given New game is started with 2 players
    And Player 1 has placed all tiles
    And Player 2 has "(7 yellow),(11 blue),(5 red)" in their hand
    And Set game ending score to 50
    Then Player 1 wins the round with score 23 points
    And Player 2 finishes the round with -23 points
    And Game is not won
    And Game goes to round 2

  @endRound_3
  Scenario: End round when the deck is empty and go to next round
    Given New game is started with 2 players
    And The deck is empty
    And Player 1 has "(7 yellow),(11 blue),(5 red)" in their hand
    And Player 2 has "(6 red),(9 blue),(5 red)" in their hand
    And Set game ending score to 50
    Then Player 2 wins the round with score 23 points
    And Player 1 finishes the round with -23 points
    And Game is not won
    And Game goes to round 2

  @endRound_4
  Scenario: End round when the deck is empty and end game because player reaches the game ending score
    Given New game is started with 2 players
    And The deck is empty
    And Player 1 has "(7 yellow),(11 blue),(5 red),(10 blue),(11 red),(11 blue)" in their hand
    And Player 2 has "(6 red),(9 blue),(5 red)" in their hand
    And Set game ending score to 50
    Then Player 2 wins the round with score 55 points
    And Player 1 finishes the round with -55 points
    And Game is won
    And Player 2 wins the game

