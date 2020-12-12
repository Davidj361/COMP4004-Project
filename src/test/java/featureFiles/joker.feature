@jokerFeature
Feature: jokerFeature, The joker can be a wild tile that be used to create runs and groups on the board

  @joker_1
  Scenario: Using joker to complete a run
    Given New game is started
    And Player's first placement is done
    And Player 1 has "(7 yellow),(8 yellow),(0 Joker)" in their hand
    When Player sends a command for placing tiles of "(7 yellow),(8 yellow),(0 Joker)" on board
    And Player sends a command for ending current turn
    Then Board is valid

  @joker_2
  Scenario: Using joker to complete a group
    Given New game is started
    And Player's first placement is done
    And Player 1 has "(9 blue),(9 green),(0 Joker)" in their hand
    When Player sends a command for placing tiles of "(9 blue),(9 green),(0 Joker)" on board
    And Player sends a command for ending current turn
    Then Board is valid

  @joker_3
  Scenario: Using a joker for First Placement
    Given New game is started
    And Player has NOT done First Placement
    And Player 1 has "(10 red),(11 red),(0 joker)" in their hand
    When Player sends a command for placing tiles of "(10 red),(11 red),(0 joker)" on board
    And Player sends a command for ending current turn
    Then Player has done First Placement

  @joker_4
  Scenario: Player retrieves joker from board
    Given New game is started
    And Player's first placement is done
    And Player 1 has "(8 blue),(7 black),(7 blue)" in their hand
    And There already exists tiles of "(8 red),(8 black),(0 Joker)" on board
    When Player sends a command for splitting row 1 at index 2
    And Player sends a command for giving tiles of "(8 blue)" to row 1
    And Player sends a command for giving tiles of "(7 black),(7 blue)" to row 2
    And Player sends a command for ending current turn
    Then Board is valid

  @joker_5
  Scenario: Using joker to complete a run where highest value is 13
    Given New game is started
    And Player's first placement is done
    And Player 1 has "(12 blue),(13 blue),(0 Joker)" in their hand
    When Player sends a command for placing tiles of "(12 blue),(13 blue),(0 Joker)" on board
    And Player sends a command for ending current turn
    Then Board is valid


  @joker_6
  Scenario: Using joker to complete a run with joker in the middle
    Given New game is started
    And Player's first placement is done
    And Player 1 has "(10 blue),(12 blue),(0 Joker)" in their hand
    When Player sends a command for placing tiles of "(10 blue),(12 blue),(0 Joker)" on board
    And Player sends a command for ending current turn
    Then Board is valid

  @joker_7
  Scenario: Using joker to complete a group with joker in the middle
    Given New game is started
    And Player's first placement is done
    And Player 1 has "(9 blue),(0 Joker),(9 Green)" in their hand
    When Player sends a command for placing tiles of "(9 blue),(0 Joker),(9 Green)" on board
    And Player sends a command for ending current turn
    Then Board is valid

  @joker_8
  Scenario: Using joker as only tile
    Given New game is started
    And Player's first placement is done
    And Player 1 has "(9 blue),(0 Joker),(9 Green)" in their hand
    When Player sends a command for placing tiles of "(0 Joker)" on board
    And Player sends a command for ending current turn
    Then Board is valid




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
    And Player sends a command for ending current turn
    Then Board is valid
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