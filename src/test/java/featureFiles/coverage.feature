@coverageFeature
Feature: testing extra code to please code coverage

  @codecoverage_1
  Scenario: Tests printing the board with more than 10 rows
    Given New game is started
    And Player's first placement is done
    And Player has "(3 red),(4 red),(5 red),(8 blue),(9 blue),(10 blue),(10 blue),(9 red),(4 green),(1 red)" in their hand
    When Player sends a command for placing tiles of "(3 red)" on board
    And Player sends a command for placing tiles of "(4 red)" on board
    And Player sends a command for placing tiles of "(5 red)" on board
    And Player sends a command for placing tiles of "(8 blue)" on board
    And Player sends a command for placing tiles of "(9 blue)" on board
    And Player sends a command for placing tiles of "(10 blue)" on board
    And Player sends a command for placing tiles of "(10 blue)" on board
    And Player sends a command for placing tiles of "(9 red)" on board
    And Player sends a command for placing tiles of "(4 green)" on board
    And Player sends a command for placing tiles of "(1 red)" on board
    And Player sends a command for ending current turn
    And There are 2 total turns

  @codecoverage_2
  Scenario: Tests merging when not possible
    Given New game is started
    And Player's first placement is done
    And Player has "(3 red),(4 red),(5 red),(8 blue),(9 blue),(10 blue),(10 blue),(9 red),(4 green),(1 red)" in their hand
    When Player sends a command for placing tiles of "(3 red)" on board
    And Player sends a command for placing tiles of "(4 red)" on board
    And Player sends a command for moving row 1 indices 4 to row 2 but fails
    And Player sends a command for ending current turn
    And There are 2 total turns

  @codecoverage_db
  Scenario: Tests displaying board
    Given New game is started
    And Player's first placement is done
    And Player has "(3 red),(3 blue),(3 black),(8 blue),(8 black),(8 yellow) (9 yellow)" in their hand
    When Player sends a command for placing tiles of "(3 red),(3 blue),(3 black)" on board
    And Player sends a command for placing tiles of "(8 blue),(8 black),(8 yellow)" on board
    And Board is valid
    And Player sends a command for displaying board
    And Player sends a command for ending current turn
    Then Board is valid
    And There are 2 total turns

  @codecoverage_dh
  Scenario: Tests displaying hand
    Given New game is started
    And Player's first placement is done
    And Player has "(3 red),(3 blue),(3 black),(8 blue),(8 black),(8 yellow) (9 yellow)" in their hand
    And Player sends a command for displaying hand
    And Player sends a command for ending current turn
    And There are 2 total turns

  @codecoverage_h
  Scenario: Tests displaying help
    Given New game is started
    And Player's first placement is done
    When Player sends a command for displaying help
    And Player sends a command for ending current turn
    And There are 2 total turns

  @codecoverage_invalidcommand
  Scenario: Tests attempting invalid command
    Given New game is started
    And Player's first placement is done
    When Player sends the command "t" which fails
    And Player sends a command for ending current turn
    And There are 2 total turns

  @codecoverage_invalidplace
  Scenario: Tests attempting invalid command
    Given New game is started
    And Player's first placement is done
    When Player sends the command "p" which fails
    And Player sends a command for ending current turn
    And There are 2 total turns

  @codecoverage_invalidgive
  Scenario: Tests attempting invalid command
    Given New game is started
    And Player's first placement is done
    When Player sends the command "g" which fails
    And Player sends a command for ending current turn
    And There are 2 total turns

  @codecoverage_invalidmove
  Scenario: Tests attempting invalid command
    Given New game is started
    And Player's first placement is done
    When Player sends the command "m" which fails
    And Player sends a command for ending current turn
    And There are 2 total turns

  @codecoverage_invalidsplit
  Scenario: Tests attempting invalid command
    Given New game is started
    And Player's first placement is done
    When Player sends the command "s" which fails
    And Player sends a command for ending current turn
    And There are 2 total turns