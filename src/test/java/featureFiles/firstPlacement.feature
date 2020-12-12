@firstPlacementFeature
Feature: firstPlacementFeature,
        The player cannot end their turn with tiles placed on the board unless the placement/modification is valid
        and they placed a sum of tiles of at least 30 points.

  @firstPlacement_1
  Scenario: First placement - success with one placement
    Given New game is started
    And Player has NOT done First Placement
    And Player has "(10 red),(12 red),(11 red),(3 red),(3 black),(3 blue)" in their hand
    When Player sends a command for placing tiles of "(10 red),(12 red),(11 red)" on board
    And Player sends a command for ending current turn
    Then Player has done First Placement

  @firstPlacement_2
  Scenario: First placement - success with two placements
    Given New game is started
    And Player has NOT done First Placement
    And Player has "(8 red),(9 red),(10 red),(3 red),(3 black),(3 blue)" in their hand
    When Player sends a command for placing tiles of "(8 red),(9 red),(10 red)" on board
    And Player sends a command for placing tiles of "(3 red),(3 black),(3 blue)" on board
    And Player sends a command for ending current turn
    Then Player has done First Placement

  @firstPlacement_3
  Scenario: First placement - fail with one placement
    Given New game is started
    And Player has NOT done First Placement
    And Player has "(6 red),(4 red),(5 red),(3 red),(3 black),(3 blue)" in their hand
    When Player sends a command for placing tiles of "(6 red),(4 red),(5 red)" on board
    And Player sends a command for ending current turn
    Then Player has NOT done First Placement

  @firstPlacement_4
  Scenario: First placement - fail with two placements
    Given New game is started
    And Player has NOT done First Placement
    And Player has "(6 red),(4 red),(5 red),(3 red),(3 black),(3 blue)" in their hand
    When Player sends a command for placing tiles of "(6 red),(4 red),(5 red)" on board
    And Player sends a command for placing tiles of "(3 red),(3 red),(3 red)" on board
    And Player sends a command for ending current turn
    Then Player has NOT done First Placement

  @firstPlacement_5
  Scenario: First placement - success with giving tiles
    Given New game is started
    And Player has NOT done First Placement
    And There already exists tiles of "(5 red),(6 red),(7 red)" on board
    And There already exists tiles of "(2 black),(3 black),(4 black)" on board
    And Player has "(5 black),(6 black),(7 black),(8 red),(9 red),(10 red)" in their hand
    When Player sends a command for giving tiles of "(8 red),(9 red),(10 red)" to row 1
    And Player sends a command for giving tiles of "(5 black),(6 black),(7 black)" to row 2
    And Player sends a command for ending current turn
    Then Player has done First Placement

  @firstPlacement_6
  Scenario: First placement - success with spliting tiles, giving tiles
    Given New game is started
    And Player has NOT done First Placement
    And There already exists tiles of "(3 black),(4 black),(5 black),(6 black),(7 black),(8 black)" on board
    And Player has "(6 black),(7 black),(8 black),(9 black),(10 black),(11 black)" in their hand
    When Player sends a command for splitting row 1 at index 3
    And Player sends a command for giving tiles of "(6 black),(7 black),(8 black)" to row 1
    And Player sends a command for giving tiles of "(9 black),(10 black),(11 black)" to row 2
    And Player sends a command for ending current turn
    Then Player has done First Placement

  @firstPlacement_7
  Scenario: First placement - success with spliting tiles, giving tiles
    Given New game is started
    And Player has NOT done First Placement
    And There already exists tiles of "(3 black),(4 black),(5 black)" on board
    And There already exists tiles of "(6 black),(7 black),(8 black)" on board
    And Player has "(1 black),(2 black),(9 black),(10 black),(11 black)" in their hand
    When Player sends a command for moving row 2 indices "1 2 3" to row 1
    And Player sends a command for giving tiles of "(1 black),(2 black),(9 black),(10 black),(11 black)" to row 1
    And Player sends a command for ending current turn
    Then Player has done First Placement
