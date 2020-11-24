#Test Cases for Functions in the Tile class
  Feature: Testing for functions in Game Class
    Testing functions in Game Class

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