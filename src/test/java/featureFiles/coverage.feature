@coverageFeature
Feature: coverageFeature, testing extra code to please code coverage

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
    When Player sends the command "t 1" which fails
    And Player sends a command for ending current turn
    And There are 2 total turns

  @codecoverage_invalidplacetiles
  Scenario: Tests attempting placing of tiles that do not exist
    Given New game is started
    And Player's first placement is done
    And Player has "(3 red),(3 blue),(3 black),(8 blue),(8 black),(8 yellow) (9 yellow)" in their hand
    When Player sends the command "p 6 7 8" which fails
    And Player sends a command for ending current turn
    And There are 2 total turns

  @codecoverage_jokerscore
  Scenario: End round and check points joker adds
    Given New game is started with 2 players
    And The deck is empty
    And Player 1 has "(7 yellow),(0 Joker),(5 red)" in their hand
    And Player 2 has "(6 red),(9 blue),(5 red)" in their hand
    And Set game ending score to 50
    Then Player 2 wins the round with score 42 points
    And Player 1 finishes the round with -42 points
    And Game is not won
    And Game goes to round 2

  @codecoverage_board
  Scenario: Player ends turn trying to place first placement without making 30 points and no tiles left in deck
    Given New game is started
    When Player has NOT done First Placement
    And The deck is empty
    And Player has "(5 blue),(5 red),(5 black)" in their hand
    And Player sends a command for placing tiles of "(5 blue),(5 red),(5 black)" on board
    And Player sends a command for ending current turn
    Then Player has 3 tiles
    And There are 2 total turns

  @codecoverage_board2
  Scenario: Player ends turn trying to place something not allowed with no tiles in deck
    Given New game is started
    When Player has NOT done First Placement
    And The deck is empty
    And Player has "(5 blue),(5 red),(6 black)" in their hand
    And Player sends a command for placing tiles of "(5 blue),(5 red),(6 black)" on board
    And Player sends a command for ending current turn
    Then Player has 3 tiles
    And There are 2 total turns

  @codecoverage_board3
  Scenario: Player ends turn while placing something not allowed on board with 1 tile left in deck
    Given New game is started
    When Player's first placement is done
    And The deck has 1 Tile
    And Player has "(5 blue),(5 red),(6 black)" in their hand
    And Player sends a command for placing tiles of "(5 blue),(5 red),(6 black)" on board
    And Player sends a command for ending current turn
    Then Player has 4 tiles
    And There are 2 total turns

  @codecoverage_board4
  Scenario: Player ends turn trying to place something not allowed with no tiles in deck
    Given New game is started
    When Player's first placement is done
    And The deck is empty
    And Player has "(5 blue),(5 red),(6 black)" in their hand
    And There already exists tiles of "(3 red),(3 blue),(3 black)" on board
    And Player sends a command for splitting row 1 at index 2
    And Player sends a command for ending current turn
    Then Player has 3 tiles
    And There are 2 total turns

  @winRound-1
  Scenario: checking get winner and points functionality
    Given New game is started
    And New game is started with 3 players
    When Player 1 has placed all tiles
    And Player 2 has "(7 yellow),(11 blue),(5 red)" in their hand
    And Player 3 has "(6 red),(9 blue),(0 Joker)" in their hand
    Then Player 1 wins the round with score 68 points
    And Player 2 finishes the round with -23 points
    And Player 3 finishes the round with -45 points