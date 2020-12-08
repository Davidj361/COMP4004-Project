@firstPlacementFeature
Feature: Testing for use cases
  Testing all use cases so far

  Scenario: First placement - success with one placement
    Given First tile has not been placed
    And Player has "(10 red),(12 red),(11 red),(3 red),(3 black),(3 blue)" in hand
    When Player sends a command for placing "(10 red),(12 red),(11 red)" tiles on board
    And Player sends a command to end turn
    Then First placement is successful

  Scenario: First placement - success with two placements
    Given First tile has not been placed
    And Player has "(8 red),(9 red),(10 red),(3 red),(3 black),(3 blue)" in hand
    When Player sends a command for placing "(8 red),(9 red),(10 red)" tiles on board
    And Player sends a command for placing "(3 red),(3 black),(3 blue)" tiles on board
    And Player sends a command to end turn
    Then First placement is successful

  Scenario: First placement - fail with one placement
    Given First tile has not been placed
    And Player has "(6 red),(4 red),(5 red),(3 red),(3 black),(3 blue)" in hand
    When Player sends a command for placing "(6 red),(4 red),(5 red)" tiles on board
    And Player sends a command to end turn
    Then First placement is NOT successful

  Scenario: First placement - fail with two placements
    Given First tile has not been placed
    And Player has "(6 red),(4 red),(5 red),(3 red),(3 black),(3 blue)" in hand
    When Player sends a command for placing "(6 red),(4 red),(5 red)" tiles on board
    When Player sends a command for placing "(3 red),(3 red),(3 red)" tiles on board
    And Player sends a command to end turn
    Then First placement is NOT successful