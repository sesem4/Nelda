# PluginServiceSPI

## Description

Start and Stop the game plugin

## Operation

```java
void start(GameData gameData, World world)
void stop(GameData gameData, World world)
```

## Parameters

`GameData gameData` - The game data

`World world` - The world of the game

## Preconditions

### Start

The game has started and the plugin has not yet been started

### Stop

The game plugin is to be stopped and the game plugin has been started.

## Postconditions

### Start

The plugin has handled the start

### Stop

The plugin has handled the stop
