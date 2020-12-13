@bugs
Feature: bugs that need to be tested against

  @bug
  Scenario: BUG Winner doesn't get proper points, winner isn't the proper winner
    Given New game is started with 2 players
    When Player 1 has "(1 yellow),(2 blue),(3 red)" in their hand
    And Player 2 has "(7 yellow),(11 blue),(5 red)" in their hand
    And The deck has 1 Tile
    And Player 1 sends a command for ending current turn
    And Player 2 sends a command for ending current turn
    Then Player 1 finishes the round with 23 points
    And Player 2 finishes the round with -23 points

  @bug2
  Scenario: BUG Winner flip flops when ending a turn
    Given New game is started with 2 players
    When Player 1 has "(1 yellow),(2 blue),(3 red)" in their hand
    And Player 2 has "(7 yellow),(11 blue),(5 red)" in their hand
    And The deck has 1 Tile
    And Player 1 sends a command for ending current turn
    And Player 2 sends a command for ending current turn
    And Player 1 has won the game
    And Player 1 sends a command for ending current turn but fails
    And Player 2 sends a command for ending current turn but fails
    Then Player 1 finishes the round with 23 points
    And Player 2 finishes the round with -23 points

  @bug3
  Scenario: BUG When player places valid tiles but hasn't done first placement and <30 points of tiles, 3 tiles aren't picked up
    Given New game is started
    When Player has "(1 yellow),(2 yellow),(3 yellow),(9 black)" in their hand
    And Player has NOT done First Placement
    And Player sends a command for placing tiles of "(1 yellow),(2 yellow),(3 yellow)" on board
    And Player sends a command for ending current turn
    Then Player has NOT done First Placement
    And Player has 7 tiles
    And There are 2 total turns

  @bug3
  Scenario: BUG when sorting a give if the give tile indexes are backwards
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(10 black),(11 black),(12 black)" on board
    And Player has "(1 yellow),(2 blue),(3 red),(13 black),(12 black),(1 yellow),(2 blue),(3 red),(1 yellow),(2 blue),(3 red),(8 black),(9 black),(3 red)" in their hand
    When Player sends a command for giving tiles of "(9 black),(8 black)" to row 1
    And Board is valid
    And Player sends a command for ending current turn
    Then Board is valid
    And There are 2 total turns

  @bug4
  Scenario: Bug - move tiles with backwards indices
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(6 red),(7 red),(8 red)" on board
    When Player sends a command for moving row 1 indices "1 3 2" to row 2
    And Board is valid
    And Player sends a command for ending current turn
    Then Board is valid

  @bug5
  Scenario: Bug - Player should be able to give joker to groups on board
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(9 blue),(10 blue),(11 blue)" on board
    And There already exists tiles of "(12 blue),(12 yellow),(12 red)" on board
    And There already exists tiles of "(4 red),(4 yellow),(4 blue)" on board
    And There already exists tiles of "(7 blue),(7 red),(7 yellow)" on board
    And There already exists tiles of "(5 blue),(5 red),(5 black)" on board
    And Player has "(0 joker),(1 red),(5 black),(5 red),(5 blue),(6 black),(6 yellow),(8 red),(8 blue),(9 black),(9 blue),(10 red),(11 yellow),(13 blue)" in their hand
    And Board is valid
    When Player sends a command for displaying hand
    And Player sends a command for giving tiles of "(0 joker)" to row 2
    Then Board is valid

  @bug6
  Scenario: Bug - Player should be able to get first placement done with exactly 30 points
    Given New game is started
    And Player has "(2 blue),(3 red),(4 red),(5 yellow),(7 black),(7 blue),(8 black),(8 yellow),(9 red),(9 blue),(10 blue),(10 yellow),(11 blue),(12 black)" in their hand
    When Player sends a command for displaying hand
    And Player sends a command for placing tiles of "(9 blue),(10 blue),(11 blue)" on board
    And Player sends a command for ending current turn
    Then Player has done First Placement

  @bug-moveOutOfBound1
  Scenario: @bug-moveOutOfBound1 - Player types out of bound row index above
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And Player has "(5 red),(7 red),(9 red)" in their hand
    And Board is valid
    When Player sends the command "m 14 1 1" with no out of bounds exception
    And Player sends a command for ending current turn
    Then Board is valid
    And There are 2 total turns

  @bug-moveOutOfBound2
  Scenario: @bug-moveOutOfBound2 - Player types out of bound row index below, -1
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And Player has "(5 red),(7 red),(9 red)" in their hand
    And Board is valid
    When Player sends the command "m -1 1 1" with no out of bounds exception
    And Player sends a command for ending current turn
    Then Board is valid
    And There are 2 total turns

  @bug-moveOutOfBound3
  Scenario: @bug-moveOutOfBound3 - Player types out of bound row index below, 0
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And Player has "(5 red),(7 red),(9 red)" in their hand
    And Board is valid
    When Player sends the command "m 0 1 1" with no out of bounds exception
    And Player sends a command for ending current turn
    Then Board is valid
    And There are 2 total turns

  @bug-moveOutOfBound4
  Scenario: @bug-moveOutOfBound4 - Player types out of bound tile index below, -1
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And Player has "(5 red),(7 red),(9 red)" in their hand
    And Board is valid
    Then Player sends the command "m 1 1 -1" with no out of bounds exception
    And Player sends a command for ending current turn
    And Board is valid
    And There are 2 total turns

  @bug-moveOutOfBound5
  Scenario: @bug-moveOutOfBound5 - Player types out of bound tile index below, 0
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And Player has "(5 red),(7 red),(9 red)" in their hand
    And Board is valid
    Then Player sends the command "m 1 1 0" with no out of bounds exception
    And Player sends a command for ending current turn
    And Board is valid
    And There are 2 total turns

  @bug-moveOutOfBound6
  Scenario: @bug-moveOutOfBound6 - Player types out of bound tile index above
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And Player has "(5 red),(7 red),(9 red)" in their hand
    And Board is valid
    Then Player sends the command "m 1 1 999" with no out of bounds exception
    And Player sends a command for ending current turn
    And Board is valid
    And There are 2 total turns

  @bug-moveNumberException1
  Scenario: @bug-moveNumberException1 - Player types asdf in all arguments of move
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And Player has "(5 red),(7 red),(9 red)" in their hand
    And Board is valid
    Then Player sends the command "m asdf asdf asdf" with no number exception
    And Player sends a command for ending current turn
    And Board is valid
    And There are 2 total turns

  @bug-moveNumberException2
  Scenario: @bug-moveNumberException2 - Player types asdf in 1st arg
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And Player has "(5 red),(7 red),(9 red)" in their hand
    And Board is valid
    Then Player sends the command "m asdf 1 1" with no number exception
    And Player sends a command for ending current turn
    And Board is valid
    And There are 2 total turns

  @bug-moveNumberException3
  Scenario: @bug-moveNumberException3 - Player types asdf in 2nd arg
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And Player has "(5 red),(7 red),(9 red)" in their hand
    And Board is valid
    Then Player sends the command "m 1 asdf 1" with no number exception
    And Player sends a command for ending current turn
    And Board is valid
    And There are 2 total turns

  @bug-moveNumberException4
  Scenario: @bug-moveNumberException4 - Player types asdf in 3rd arg
    Given New game is started
    And Player's first placement is done
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And There already exists tiles of "(3 red),(4 red),(5 red),(6 red),(7 red),(8 red)" on board
    And Player has "(5 red),(7 red),(9 red)" in their hand
    And Board is valid
    Then Player sends the command "m 1 1 asdf" with no number exception
    And Player sends a command for ending current turn
    And Board is valid
    And There are 2 total turns

