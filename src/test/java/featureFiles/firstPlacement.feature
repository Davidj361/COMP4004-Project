# First placement scenarios
Feature: Testing for use cases
  Testing all use cases so far

  Scenario: First placement - success
    Given First tile has not been placed
    And Player has "(8 red),(9 red),(10 red),(3 red),(3 black),(3 blue)" in hand
    When Player sends a command for placing "(8 red),(9 red),(10 red)" tiles on board
    And Player sends a command to end turn
    Then First placement is successful
  Scenario: First placement - fail
    Given First tile has not been placed
    And Player puts tiles "(8 red),(9 red),(10 red)" on board
    And Player ends a turn
    Then First placement is NOT successful