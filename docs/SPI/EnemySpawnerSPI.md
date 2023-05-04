<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>EnemySpawnerSPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
void spawnEnemy(GameData gameData, float x, float y)
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

`Float x` - The x coordinate the enemy should spawn at

`Float y` - The y coordinate the enemy should spawn at
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
<td><h2>EnemySpawnerSPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
void spawnEnemy(GameData gameData, float x, float y, int difficulty)
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

`Float x` - The x coordinate the enemy should spawn at

`Float y` - The y coordinate the enemy should spawn at

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
<td><h2>EnemySpawnerSPI</h2></td>
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
