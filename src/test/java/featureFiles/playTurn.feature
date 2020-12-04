# Play turn scenarios
Feature: Testing user play a turn in various scenarios

  Scenario: Play Turn - places a tile and form a run, then finishes turn
    Given Player starts round
    And There already exists a run of "(3 red),(4 red),(5 red)" on board
    And Player has "(6 red)" on hand
    When Player sends a command for placing a tile of "(6 red)" on board
    And Placed tile form a run
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places a tile and form a group, then finishes turn
    Given Player starts round
    And There already exists a group of "(3 red),(3 blue),(3 black)" on board
    And Player has "(3 yellow)" on hand
    When Player sends a command for placing a tile of "(3 yellow)" on board
    And Placed tile form a group
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places multiple tiles and form a run together with existing tiles on board, then finishes turn
    Given Player starts round
    And There already exists a run of "(3 red),(4 red),(5 red)" on board
    And Player has "(6 red),(7 red)" on hand
    When Player sends a command for placing tiles of "(6 red),(7 red)" on board
    And Placed tiles form a run
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places multiple tiles that form a run, then finishes turn
    Given Player starts round
    And There already exists a run of "(3 red),(4 red),(5 red)" on board
    And Player has "(3 red),(4 red),(5 red),(6 red)" on hand
    When Player sends a command for placing a run of "(3 red),(4 red),(5 red),(6 red)" on board
    And Placed tiles form a run
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn

  Scenario: Play Turn - places multiple tiles that form a group, then finishes turn
    Given Player starts round
    And Player has "(3 red),(3 blue),(3 black),(3 yellow)" on hand
    When Player sends a command for placing a group of "(3 red),(3 blue),(3 black),(3 yellow)" on board
    And Placed tiles form a group
    And Player sends a command for ending current turn
    Then Tiles placed on board successfully
    And Player ends a turn
