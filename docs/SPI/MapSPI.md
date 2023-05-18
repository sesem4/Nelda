<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>MapSPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
boolean isPositionPassable(Vector2 worldPosition)
```
</td>
</tr>
<tr>
<td><Strong>Description</strong></td>
<td>Check if a world position is a passable location.</td>
</tr>
<tr>
<td><Strong>Parameters</strong></td>
<td>

`Vector2 worldPosition` - The world position formatted as a `Vector2` that includes the x and y coordinate</td>
</tr>
<tr>
<td><Strong>PreConditions</strong></td>
<td>A map is loaded into the world.
</td>
</tr>
<tr>
<td><Strong>PostConditions</strong></td>
<td>The passability of the world position is returned.
</td>
</tr>
</tbody>
</table>

<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>MapSPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
boolean isTilePassable(Vector2 tileCoordinate)
```
</td>
</tr>
<tr>
<td><Strong>Description</strong></td>
<td>Check if a tile coordinate is a passable location.</td>
</tr>
<tr>
<td><Strong>Parameters</strong></td>
<td>

`Vector2 tileCoordinate` - The tile position is formatted as a `Vector2` that includes the x and y coordinates. This position is related to a whole number tile location</td>
</tr>
<tr>
<td><Strong>PreConditions</strong></td>
<td>A map is loaded into the world.
</td>
</tr>
<tr>
<td><Strong>PostConditions</strong></td>
<td>The passability of the tile position is returned.
</td>
</tr>
</tbody>
</table>

<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>MapSPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
boolean isTilePassable(int x, int y)
```
</td>
</tr>
<tr>
<td><Strong>Description</strong></td>
<td>Check if a tile coordinate is a passable location.</td>
</tr>
<tr>
<td><Strong>Parameters</strong></td>
<td>

`int x` - The tile x position

`int y` - The tile y position</td>
</tr>
<tr>
<td><Strong>PreConditions</strong></td>
<td>A map is loaded into the world.
</td>
</tr>
<tr>
<td><Strong>PostConditions</strong></td>
<td>The passability of the tile position is returned.
</td>
</tr>
</tbody>
</table>

<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>MapSPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
Vector2 getRandomPassableTile()
```
</td>
</tr>
<tr>
<td><Strong>Description</strong></td>
<td>Get a random passable tile coordinate.</td>
</tr>
<tr>
<td><Strong>Parameters</strong></td>
<td>-</td>
</tr>
<tr>
<td><Strong>PreConditions</strong></td>
<td>A map is loaded into the world.
</td>
</tr>
<tr>
<td><Strong>PostConditions</strong></td>
<td>A Vector2 coordinate representing a passable tile coordinate is returned.
</td>
</tr>
</tbody>
</table>

<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>MapSPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
boolean[][] getNavGrid()
```
</td>
</tr>
<tr>
<td><Strong>Description</strong></td>
<td>Get a grid of booleans representing the passable states of all tiles currently in the world.</td>
</tr>
<tr>
<td><Strong>Parameters</strong></td>
<td>-</td>
</tr>
<tr>
<td><Strong>PreConditions</strong></td>
<td>A map is loaded into the world.
</td>
</tr>
<tr>
<td><Strong>PostConditions</strong></td>
<td>A 2D boolean array is returned, which represents the passable states of all tiles currently in the world. The origin is bottom left, with the x being represented by the first index and the y being represented by the second index.
</td>
</tr>
</tbody>
</table>