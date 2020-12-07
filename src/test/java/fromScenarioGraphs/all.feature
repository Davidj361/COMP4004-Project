# This was written for a deliverable and goes through the same paths in the scenario graphs

# First placement scenarios
Feature: Testing for use cases
  Testing all use cases so far

Scenario: First placement - success
Given First tile has not been placed
And Player puts tiles on table
When Tiles add up to 30 or more
Then First tiles can be placed
Scenario: First placement - fail
Given First tile has not been placed
And Player puts tiles on table
When Tiles add up to less than 30
Then First tiles cannot be placed

#End Game scenarios

Scenario: End Game
Given Round is in effect
When Player places game winning tile(s) on table
Then Game Calculates scores
And Scores are printed to all players
And Server Closes



# Start Game scenarios

Scenario: Start Game - Players select start game but not all players connected yet
Given Host is started and running
When All players select ‘Start Game’
And Enough players for game ARE NOT reached
Then Error message about number of players

Scenario: Start Game - Players select start game and all players are connected
Given Host is started and running
When All players select ‘Start Game’
And Enough players ARE reached
Then Game’s deck of tiles is shuffled
And Players are given 14 tiles distributed amongst all players
And Players turns are chosen randomly
And Game shows each player their stack of tiles
And First player choses 'Play Turn'
