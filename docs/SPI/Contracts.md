# Plugin

## SPI

### GamePluginSPI

## Description
```
Start and stop the plugins
```

## Operation
```
plugin()
```
## Parameters
```
*Unknown*
```
## Preconditions
```
*Unknown*
```
## Postconditions
```
*Unknown*
```
# Processing service

## SPI

IEntityProcessingServiceSPI

## Description
```
Updates the game loop for the specific entity.
```
## Operation
```
process(gameData, world)
```
## Parameters
```
* gameData - The game data object.
* world - The world object.
```
## Preconditions
```
A game frame has passed since last process call. A world and game has been created and started, as well as a entity has been created.
```
## Postconditions
```
The entity has been updated, with a new frame drawn.
```
# Post Processing service

## SPI

GamePluginSPI

## Description
```
*Unknown*
```

## Operation
```
*Unknown*
```
## Parameters
```
*Unknown*
```
## Preconditions
```
*Unknown*
```
## Postconditions
```
*Unknown*
```
