@playTurnFeature
Feature: Testing user play a turn in various scenarios

  Scenario: Play Turn - places a tile and form a run, then finishes turn
    Given Player starts round (not first placement)
    And There already exists a run of "(3 red),(4 red),(5 red)" on board
    And Player has "(6 red)" on hand
    When Player sends a command for placing a tile of "(6 red)" on board
    And Placed tile form a run
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places a tile and form a group, then finishes turn
    Given Player starts round (not first placement)
    And There already exists a group of "(3 red),(3 blue),(3 black)" on board
    And Player has "(3 yellow)" on hand
    When Player sends a command for placing a tile of "(3 yellow)" on board
    And Placed tile form a group
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places multiple tiles and form a run together with existing tiles on board, then finishes turn
    Given Player starts round (not first placement)
    And There already exists a run of "(3 red),(4 red),(5 red)" on board
    And Player has "(6 red),(7 red)" on hand
    When Player sends a command for placing tiles of "(6 red),(7 red)" on board
    And Placed tiles form a run
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places multiple tiles that form a run, then finishes turn
    Given Player starts round (not first placement)
    And Player has "(3 red),(4 red),(5 red),(6 red)" on hand
    When Player sends a command for placing a run of "(3 red),(4 red),(5 red),(6 red)" on board
    And Placed tiles form a run
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places multiple tiles that form a group, then finishes turn
    Given Player starts round (not first placement)
    And Player has "(3 red),(3 blue),(3 black),(3 yellow)" on hand
    When Player sends a command for placing a group of "(3 red),(3 blue),(3 black),(3 yellow)" on board
    And Placed tiles form a group
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places multiple tiles that form a run, and places multiple tiles that form another run, then finishes turn
    Given Player starts round (not first placement)
    And Player has "(3 red),(4 red),(5 red),(8 blue),(9 blue),(10 blue)" on hand
    When Player sends a command for placing a run of "(3 red),(4 red),(5 red)" on board
    And Placed tiles form a run
    And Player sends a command for placing a run of "(8 blue),(9 blue),(10 blue)" on board
    And Placed tiles form another run
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places multiple tiles that form a group, and places multiple tiles that form another group, then finishes turn
    Given Player starts round (not first placement)
    And Player has "(3 red),(3 blue),(3 black),(8 blue),(8 black),(8 yellow)" on hand
    When Player sends a command for placing a group of "(3 red),(3 blue),(3 black)" on board
    And Placed tiles form a group
    And Player sends a command for placing a group of "(8 blue),(8 black),(8 yellow)" on board
    And Placed tiles form another group
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places multiple tiles that form a group, and places multiple tiles that form a run, then finishes turn
    Given Player starts round (not first placement)
    And Player has "(3 red),(3 blue),(3 black),(8 blue),(9 blue),(10 blue)" on hand
    When Player sends a command for placing a group of "(3 red),(3 blue),(3 black)" on board
    And Placed tiles form a group
    And Player sends a command for placing a run of "(8 blue),(9 blue),(10 blue)" on board
    And Placed tiles form another run
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places multiple tiles but it is an invalid placement, undo and places other tiles that form a run, then finishes turn
    Given Player starts round (not first placement)
    And Player has "(3 red),(5 blue),(7 black),(8 blue),(9 blue),(10 blue)" on hand
    When Player sends a command for placing tiles of "(3 red),(5 blue),(7 black)" but fails
    And Player sends a command for undoing the previous action
    And Player sends a command for placing another run of "(8 blue),(9 blue),(10 blue)" on board
    And Secondly placed tiles form a run
    And Player sends a command for ending current turn
    Then Player has 3 tiles
    And Players turn ends

  Scenario: Play Turn - places multiple tiles but it is an invalid placement, undo and places other tiles that form a group, then finishes turn
    Given Player starts round (not first placement)
    And Player has "(3 red),(5 blue),(7 black),(8 blue),(8 red),(8 yellow)" on hand
    When Player sends a command for placing tiles of "(3 red),(5 blue),(7 black)" but fails
    And Player sends a command for undoing the previous action
    And Player sends a command for placing another group of "(8 blue),(8 red),(8 yellow)" on board
    And Secondly placed tiles form a group
    And Player sends a command for ending current turn
    Then Player has 3 tiles
    And Players turn ends

  Scenario: Play Turn - places a tile but it is an invalid placement, undo and places another tile that form a run, then finishes turn
    Given Player starts round (not first placement)
    And There already exists a run of "(3 red),(4 red),(5 red)" on board
    And Player has "(1 black),(6 red)" on hand
    When Player sends a command for placing a tile of "(1 black)" but fails
    And Player sends a command for undoing the previous action
    And Player sends a command for placing another tile of "(6 red)" on board
    And Secondly placed tile form a run
    And Player sends a command for ending current turn
    Then Player has 1 tile
    And Players turn ends

  Scenario: Play Turn - places a tile but it is an invalid placement, undo and places another tile that form a group, then finishes turn
    Given Player starts round (not first placement)
    And There already exists a group of "(8 blue),(8 red),(8 yellow)" on board
    And Player has "(1 black),(8 black)" on hand
    When Player sends a command for placing a tile of "(1 black)" but fails
    And Player sends a command for undoing the previous action
    And Player sends a command for placing another tile of "(8 black)" on board
    And Secondly placed tile form a group
    And Player sends a command for ending current turn
    Then Player has 1 tile
    And Players turn ends