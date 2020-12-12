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
