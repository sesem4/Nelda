# EnemySpawnerSPI

## Description

Spawn enemy into the map.

## Operation

```java
void spawnEnemy(GameData gameData, World world, float x, float y)
```

## Parameters

`GameData gameData` - The game data

`World world` - The world of the game

`Float x` - The x coordinate the enemy should spawn at

`Float y` - The y coordinate the enemy should spawn at

## Preconditions

A enemy spawn is requested, and that the game has started and a suitable map and region is loaded.

## Postconditions

Enemy has been spawned at x and y.
