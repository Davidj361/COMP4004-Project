@gameFeature
Feature: Testing for functions in Game Class

  Scenario: Run - success
    Given Tiles are "(10 green),(11 green),(12 green)"
    Then Tile is a run

  Scenario: Run - fail
    Given Tiles are "(3 green),(4 green),(5 blue)"
    Then Tile is not a run


  Scenario: Group - success
    Given Tiles are "(3 green),(3 blue),(3 black)"
    Then Tile is a group

  Scenario: Group - fail
    Given Tiles are "(3 green),(3 blue),(3 blue)"
    Then Tiles is not a group


  Scenario: First Placement - success
    Given Tiles are "(10 green),(11 green),(12 green)"
    When player tries placing their first placement
    Then First placement is successful

  Scenario: First Placement - fail
    Given Tiles are "(8 green),(9 green),(10 green)"
    When player tries placing their first placement
    Then First placement is NOT successful