@startGameFeature
Feature: startGameFeature, Players can host or connect to a host
                            the game starts when everyone is connected

  @startGame-1
  Scenario: Players chooses a name, 1 player is a host and rest are clients, they enter the correct IP and connect and the game starts
    Given 2 people startup Rummikub
    And Player 1 is named "HostPlayer"
    And Player 2 is named "Client1"
    And Player 1 chooses port 1337
    And Player 2 chooses port 1337
    When Player 1 chooses to be host
    And Player 1 wants 2 players for their game
    And Player 1 wants the game to end at 50 points
    And Player 2 chooses a destination IP of "127.0.0.1"
    And Player 2 chooses to be a client
    And Player 1 starts hosting and clients connect
    Then Everyone is connected
    And The game has 2 players
    And The game has a game ending score at 50

  @startGame-2
  Scenario: Player 1 chooses to be a client then tries to connects to an IP not hosting
    Given 1 people startup Rummikub
    And Player 1 is named "Client1"
    And Player 1 chooses port 1338
    And Player 1 chooses a destination IP of "127.0.0.2"
    And Player 1 chooses to be a client
    Then Player 1 tries to connect but fails