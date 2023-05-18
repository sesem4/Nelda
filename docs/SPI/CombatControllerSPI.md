<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>CombatControllerSPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
boolean shouldAttack(GameData gameData,Entity entity)
```

</td>
</tr>
<tr>
<td><Strong>Description</strong></td>
<td>Checks if the entity should attack</td>
</tr>
<tr>
<td><Strong>Parameters</strong></td>
<td class="tg-0pky">

`GameData gameData` - The game data of the current game

`Entity entity` - The entity which is requesting combat

</td>
</tr>
<tr>
<td><Strong>PreConditions</strong></td>
<td>The game has been started, and the SPI has been implemented in a class.
</td>
</tr>
<tr>
<td><Strong>PostConditions</strong></td>
<td>Returns if a combat should be initiated
</td>
</tr>
</tbody>
</table>

<table>
<tbody>
<tr>
<td><strong>SPI</strong></td>
<td><h2>CombatControllerSPI</h2></td>
</tr>
<tr>
<td><Strong>Operation</strong></td>
    <td>

```java
Attack spawnAttack(GameData gameData,Entity entity)
```

</td>
</tr>
<tr>
<td><Strong>Description</strong></td>
<td>Spawn an attack from the provided entity</td>
</tr>
<tr>
<td><Strong>Parameters</strong></td>
<td class="tg-0pky">

`GameData gamedata` - The game data of the current game

`Entity entity` - The entity which is requesting combat

</td>
</tr>
<tr>
<td><Strong>PreConditions</strong></td>
<td>The game has been started, and the SPI has been implemented in a class.
</td>
</tr>
<tr>
<td><Strong>PostConditions</strong></td>
<td>Returns an Attack and spawns an attack in the world.
</td>
</tr>
</tbody>
</table>