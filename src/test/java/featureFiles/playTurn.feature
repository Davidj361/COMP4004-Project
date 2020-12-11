@playTurnFeature
Feature: Testing user play a turn in various scenarios

  @playTurn_1
  Scenario: Play Turn - places a tile and form a run, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And There already exists tiles of "(3 red),(4 red),(5 red)" on board
    And Player has "(6 red)" in their hand
    When Player sends a command for giving tiles of "(6 red)" to row 0
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_2
  Scenario: Play Turn - places a tile and form a group, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And There already exists tiles of "(3 red),(3 blue),(3 black)" on board
    And Player has "(3 yellow)" in their hand
    When Player sends a command for giving tiles of "(3 yellow)" to row 0
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_3
  Scenario: Play Turn - places multiple tiles and form a run together with existing tiles on board, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And There already exists tiles of "(3 red),(4 red),(5 red)" on board
    And Player has "(6 red),(7 red)" in their hand
    When Player sends a command for placing tiles of "(6 red),(7 red)" on board
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_4
  Scenario: Play Turn - places multiple tiles that form a run, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And Player has "(3 red),(4 red),(5 red),(6 red)" in their hand
    When Player sends a command for placing tiles of "(3 red),(4 red),(5 red),(6 red)" on board
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_5
  Scenario: Play Turn - places multiple tiles that form a group, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And Player has "(3 red),(3 blue),(3 black),(3 yellow)" in their hand
    When Player sends a command for placing tiles of "(3 red),(3 blue),(3 black),(3 yellow)" on board
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_6
  Scenario: Play Turn - places multiple tiles that form a run, and places multiple tiles that form another run, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And Player has "(3 red),(4 red),(5 red),(8 blue),(9 blue),(10 blue)" in their hand
    When Player sends a command for placing tiles of "(3 red),(4 red),(5 red)" on board
    And Player sends a command for placing tiles of "(8 blue),(9 blue),(10 blue)" on board
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_7
  Scenario: Play Turn - places multiple tiles that form a group, and places multiple tiles that form another group, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And Player has "(3 red),(3 blue),(3 black),(8 blue),(8 black),(8 yellow)" in their hand
    When Player sends a command for placing tiles of "(3 red),(3 blue),(3 black)" on board
    And Player sends a command for placing tiles of "(8 blue),(8 black),(8 yellow)" on board
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_8
  Scenario: Play Turn - places multiple tiles that form a group, and places multiple tiles that form a run, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And Player has "(3 red),(3 blue),(3 black),(8 blue),(9 blue),(10 blue)" in their hand
    When Player sends a command for placing tiles of "(3 red),(3 blue),(3 black)" on board
    And Player sends a command for placing tiles of "(8 blue),(9 blue),(10 blue)" on board
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_9
  Scenario: Play Turn - places multiple tiles but it is an invalid placement, undo and places other tiles that form a run, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And Player has "(3 red),(5 blue),(7 black),(8 blue),(9 blue),(10 blue)" in their hand
    When Player sends a command for placing tiles of "(3 red),(5 blue),(7 black)" on board
    And Player sends a command for undoing the previous action
    And Player sends a command for placing tiles of "(8 blue),(9 blue),(10 blue)" on board
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Player has 3 tiles
    And There are 2 total turns

  @playTurn_10
  Scenario: Play Turn - places multiple tiles but it is an invalid placement, undo and places other tiles that form a group, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And Player has "(3 red),(5 blue),(7 black),(8 blue),(8 red),(8 yellow)" in their hand
    When Player sends a command for placing tiles of "(3 red),(5 blue),(7 black)" on board
    And Player sends a command for undoing the previous action
    And Player sends a command for placing tiles of "(8 blue),(8 red),(8 yellow)" on board
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Player has 3 tiles
    And There are 2 total turns

  @playTurn_11
  Scenario: Play Turn - places a tile but it is an invalid placement, undo and places another tile that form a run, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And There already exists tiles of "(3 red),(4 red),(5 red)" on board
    And Player has "(1 black),(6 red)" in their hand
    When Player sends a command for giving tiles of "(1 black)" to row 0
    And Player sends a command for undoing the previous action
    And Player sends a command for giving tiles of "(6 red)" to row 0
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Player has 1 tiles
    And There are 2 total turns

  @playTurn_12
  Scenario: Play Turn - places a tile but it is an invalid placement, undo and places another tile that form a group, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And There already exists tiles of "(8 blue),(8 red),(8 yellow)" on board
    And Player has "(1 black),(8 black)" in their hand
    When Player sends a command for giving tiles of "(1 black)" to row 0
    And Player sends a command for undoing the previous action
    And Player sends a command for giving tiles of "(8 black)" to row 0
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Player has 1 tiles
    And There are 2 total turns

  @playTurn_13
  Scenario: Play Turn - splits tiles and places a tile to form a run
    Given New game is started
    And Player starts turn (not first placement)
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red)" on board
    And Player has "(8 red)" in their hand
    When Player sends a command for splitting row 0 at index 3
    And Player sends a command for giving tiles of "(8 red)" to row 1
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_14
  Scenario: Play Turn - splits tiles and places multiple tiles to form a run
    Given New game is started
    And Player starts turn (not first placement)
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red)" on board
    And Player has "(7 red),(8 red)" in their hand
    When Player sends a command for splitting row 0 at index 3
    And Player sends a command for giving tiles of "(7 red),(8 red)" to row 1
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_15
  Scenario: Play Turn - splits tiles and places a tile with them to form a group
    Given New game is started
    And Player starts turn (not first placement)
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red)" on board
    And Player has "(8 red)" in their hand
    When Player sends a command for splitting row 0 at index 3
    And Player sends a command for giving tiles of "(8 red)" to row 1
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_16
  Scenario: Play Turn - splits tiles from two rows, combine splitted tiles and places a tile with them to form a group
    Given New game is started
    And Player starts turn (not first placement)
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red)" on board
    And There already exists additional tiles of "(3 blue),(4 blue),(5 blue),(6 blue)" on board
    And Player has "(6 black)" in their hand
    When Player sends a command for splitting row 0 at index 3
    And Player sends a command for splitting row 1 at index 3
    And Player sends a command for moving row 3 indices "1" to row 2
    And Player sends a command for giving tiles of "(6 black)" to row 2
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And There are 2 total turns

  @playTurn_17
  Scenario: Play Turn - move a run of tiles into another run to combine
    Given New game is started
    And Player starts turn (not first placement)
    And There already exists tiles of "(3 red),(4 red),(5 red)" on board
    And There already exists additional tiles of "(6 red),(7 red),(8 red)" on board
    And Player sends a command for moving row 1 indices "1 2 3" to row 0
    And Placed tiles form a run or a group on board
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
