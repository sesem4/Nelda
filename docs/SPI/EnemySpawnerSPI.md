<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>SpawnableEnemySPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
void spawnEnemy(GameData gameData, Vector2 coordinate)
```
</td>
</tr>
<tr>
<td><Strong>Description</strong></td>
<td>Spawn enemy into the map with difficulty default.</td>
</tr>
<tr>
<td><Strong>Parameters</strong></td>
<td>

`GameData gameData` - The game data

`Vector2 coordinate` - The x and y coordinate the enemy should spawn at, formatted as a Vector2.
</td>
</tr>
<tr>
<td><Strong>PreConditions</strong></td>
<td>A enemy spawn is requested, and the game has started and a suitable map and region are loaded.
</td>
</tr>
<tr>
<td><Strong>PostConditions</strong></td>
<td>Enemy has been spawned at x and y with default difficulty.
</td>
</tr>
</tbody>
</table>

<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>SpawnableEnemySPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
void spawnEnemy(GameData gameData, Vector2 coordinate, int difficulty)
```
</td>
</tr>
<tr>
<td><Strong>Description</strong></td>
<td>Spawn enemy into the map with specified difficulty.</td>
</tr>
<tr>
<td><Strong>Parameters</strong></td>
<td>

`GameData gameData` - The game data

`Vector2 coordinate` - The x and y coordinate the enemy should spawn at, formatted as a Vector2.

`int difficulty` - The difficulty of the enemy
</td>
</tr>
<tr>
<td><Strong>PreConditions</strong></td>
<td>A enemy spawn is requested, and the game has started and a suitable map and region are loaded.
</td>
</tr>
<tr>
<td><Strong>PostConditions</strong></td>
<td>Enemy has been spawned at x and y with specified difficulty if the difficulty is possible.
</td>
</tr>
</tbody>
</table>

<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>SpawnableEnemySPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
int[] getDifficulties()
```
</td>
</tr>
<tr>
<td><Strong>Description</strong></td>
<td>Get the difficulties that are possible by the enemy.</td>
</tr>
<tr>
<td><Strong>Parameters</strong></td>
<td>
-
</td>
</tr>
<tr>
<td><Strong>PreConditions</strong></td>
<td>A game is running, and the component is available.
</td>
</tr>
<tr>
<td><Strong>PostConditions</strong></td>
<td>Difficulties for the enemy have been provided.
</td>
</tr>
</tbody>
</table>
