<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>PlginServiceSPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
void start(GameData gameData, World world)
void stop(GameData gameData, World world)
```
</td>
</tr>
<tr>
<td><Strong>Description</strong></td>
<td>Start and Stop the game plugin</td>
</tr>
<tr>
<td><Strong>Parameters</strong></td>
<td>

`GameData gameData` - The game data

`World world` - The world of the game
</td>
</tr>
<tr>
<td><Strong>PreConditions</strong></td>
<td>
<h3>Start</h3>  

The game has started and the plugin has not yet been started

<h3>Stop</h3> 

The game plugin is to be stopped and the game plugin has been started.

</td>
</tr>
<tr>
<td><Strong>PostConditions</strong></td>
<td>
<h3>Start</h3>  

The plugin has handled the start

<h3>Stop</h3> 

The plugin has handled the stop

</td>
</tr>
</tbody>
</table>






