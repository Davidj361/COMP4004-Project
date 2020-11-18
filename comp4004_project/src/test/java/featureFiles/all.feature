# First placement scenarios

Scenario First placement - success
Given First tile has not been placed
and Player puts tiles on table
When Tiles add up to 30 or more
Then First tiles can be placed
Scenario: First placement - fail
Given First tile has not been placed
and Player puts tiles on table
When Tiles add up to less than 30
Then First tiles cannot be placed
Draw Tile scenarios
Scenario: Draw Tile - Draw Tile
Given Round is in effect
When Player selects ‘Draw Tile’
Then Tile is given to player from the deck
And Player’s rack is updated
And Players turn ends
Scenario: Draw Tile - End Turn
Given Round is in effect
When Player selects ‘End Turn’
And Player has not placed any tiles on the board
Then Tile is given to player from the deck
And Player’s rack is updated
And Players turn ends
End Game scenarios

Scenario: End Game
Given Round is in effect
When Player places game winning tile(s) on table
Then Game Calculates scores
And Scores are printed to all players
And Server Closes




# Play Turn scenarios

Scenario: Play Turn - places a tile and form a run, then finishes turn
Given Round is still in effect
When Player starts their turn
And User chooses a single tile from rack
And User transfers the chosen tiles onto board
And Placed tiles form a run
Then Tiles placed on board successfully
And User clicks "Finish Turn"
And User ends a turn

Scenario: Play Turn - places a tile and form a group, then finishes turn
Given Round is still in effect
When Player starts their turn
And User chooses a single tile from rack
And User transfers the chosen tiles onto board
And Placed tiles form a group
Then Tiles placed on board successfully
And User clicks "Finish Turn"
And User ends a turn

Scenario: Play Turn - places multiple tiles and form a run, then finishes turn
Given Round is still in effect
When Player starts their turn
And User chooses multiple tiles from rack
And User transfers the chosen tiles onto board
And Placed tiles form a run
Then Tiles placed on board successfully
And User clicks "Finish Turn"
And User ends a turn

Scenario: Play Turn - places multiple tiles and form a group, then finishes turn
Given Round is still in effect
When Player starts their turn
And User chooses multiple tiles from rack
And User transfers the chosen tiles onto board
And Placed tiles form a group
Then Tiles placed on board successfully
And User clicks "Finish Turn"
And User ends a turn

Scenario: Play Turn - places multiple tiles and form a run, and places multiple tiles and form another run, then finishes turn
Given Round is still in effect
When Player starts their turn
And User chooses multiple tiles from rack
And User transfers the chosen tiles onto board
And Placed tiles form a run
And Tiles placed on board successfully
And User chooses multiple tiles from rack
And User transfers the chosen tiles onto board
And Placed tiles form a run
Then Tiles placed on board successfully
And User clicks "Finish Turn"
And User ends a turn

Scenario: Play Turn - places multiple tiles and form a group, and places multiple tiles and form another group, then finishes turn
Given Round is still in effect
When Player starts their turn
And User chooses multiple tiles from rack
And User transfers the chosen tiles onto board
And Placed tiles form a group
And Tiles placed on board successfully
And User chooses multiple tiles from rack
And User transfers the chosen tiles onto board
And Placed tiles form a group
Then Tiles placed on board successfully
And User clicks "Finish Turn"
And User ends a turn

Scenario: Play Turn - places a tile but it is an invalid placement, tile returns to rack, then finishes turn
Given Round is still in effect
When Player starts their turn
And User chooses a single tile from rack
And User transfers the chosen tiles onto board
Then Invalid placement
And Tiles return to rack
And User clicks "Finish Turn"
And User ends a turn

Scenario: Play Turn - places multiple tiles but it is an invalid placement, tiles return to rack, then finishes turn
Given: Round is still in effect
When Player starts their turn
And User chooses multiple tiles from rack
And User transfers the chosen tiles onto board
Then Invalid placement
And Tiles return to rack
And User clicks "Finish Turn"
And User ends a turn

Scenario: Play Turn - places multiple tiles but it is an invalid placement, tiles return to rack, places other tiles and forms a run, then finishes turn
Given: Round is still in effect
When Player starts their turn
And User chooses multiple tiles from rack
And User transfers the chosen tiles onto board
And Invalid placement
And Tiles return to rack
And User chooses multiple tiles from rack
And User transfers the chosen tiles onto board
And Placed tiles form a run
Then Tiles placed on board successfully
And User clicks "Finish Turn"
And User ends a turn

Scenario: Play Turn - places multiple tiles but it is an invalid placement, tiles return to rack, places other tiles and forms a group, then finishes turn
Given: Round is still in effect
When Player starts their turn
And User chooses multiple tiles from rack
And User transfers the chosen tiles onto board
And Invalid placement
And Tiles return to rack
And User chooses multiple tiles from rack
And User transfers the chosen tiles onto board
And Placed tiles form a group
Then Tiles placed on board successfully
And User clicks "Finish Turn"
And User ends a turn



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
