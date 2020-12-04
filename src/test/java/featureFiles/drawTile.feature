# Draw a tile scenario
Feature: Testing user draws a tile scenario

  Scenario: Draw Tile at the end of turn
    Given Player starts round
    When Player finishes turn by sending end turn command
    Then Tile is given to player from the deck
    And Players turn ends
