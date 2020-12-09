@drawTileFeature
Feature: Testing user draws a tile scenario

  Scenario: Draw Tile at the end of turn
    Given New game is started
    And Player starts round 1
    When Player finishes turn by sending end turn command
    Then Tile is given to player from the deck
    And Game goes to turn 2

  Scenario: Place a tile but it is an invalid placement, tile returns to rack, then draw a tile and finishes turn
    Given New game is started
    And Player starts round 1
    And There exists a run of "(3 blue),(4 blue),(5 blue)" on board
    And Player has "(6 red)" in their hand
    When Player sends a command for placing a tile of "(6 red)" on board but fails
    And Player finishes turn by sending end turn command
    Then Tile is given to player from the deck so player has 2 tiles
    And Game goes to turn 2

  Scenario: Play Turn - places multiple tiles but it is an invalid placement, tiles return to rack, then finishes turn
    Given New game is started
    And Player starts round 1
    And Player has "(3 red),(5 blue),(7 black)" in their hand
    When Player sends a command for placing tiles of "(3 red),(5 blue),(7 black)" on board but fails
    And Player finishes turn by sending end turn command
    Then Tile is given to player from the deck so player has 4 tiles
    And Game goes to turn 2