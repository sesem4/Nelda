# Processing Service SPI

## Description

Main processing system

## Operation

```java
void process(GameData gameData, World world, Priority priority)
```

## Parameters

`GameData gameData` - The game data

`World world` - The world of the game

`Priority priority` - The priority, which is to be run for the current process round

## Preconditions

A complete game loop has been completed

## Postconditions

The system has been processed
