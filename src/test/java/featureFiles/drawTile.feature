@drawTileFeature
Feature: drawTileFeature,
          The player draws a tile when they end their turn without doing any previous actions,
          but draw 3 tiles if they modified the board and ended their turn when their placements were invalid.

  @drawTile_1
  Scenario: Draw Tile at the end of turn
    Given New game is started
    And Player starts turn (not first placement)
    When Player sends a command for ending current turn
    Then Tile is given to player from the deck
    And There are 2 total turns

  @drawTile_2
  Scenario: Place a tile but it is an invalid placement, tile returns to rack, then draw a tile and finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And There already exists tiles of "(3 blue),(4 blue),(5 blue)" on board
    And Player has "(6 red)" in their hand
    When Player sends a command for giving tiles of "(6 red)" to row 0
    And Player sends a command for ending current turn
    Then Player draws tile(s) from the deck so player has 4 tiles
    And There are 2 total turns

  @drawTile_3
  Scenario: Play Turn - places multiple tiles but it is an invalid placement, tiles return to rack, then finishes turn
    Given New game is started
    And Player starts turn (not first placement)
    And Player has "(5 red),(7 blue),(9 black)" in their hand
    When Player sends a command for placing tiles of "(5 red),(7 blue),(9 black)" on board
    And Player sends a command for ending current turn
    Then Player draws tile(s) from the deck so player has 6 tiles
    And There are 2 total turns