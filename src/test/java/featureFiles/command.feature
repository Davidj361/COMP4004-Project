# Test scenarios for Game.Command(..)

  Scenario: Game starts with 2 players, player places 10red, 10black, 10blue
    Given Game starts with 2 players
    

  Scenario: First Placement - fail
    Given Tiles are "(8 green),(9 green),(10 green)"
    When player tries placing their first placement
    Then First placement is NOT successful