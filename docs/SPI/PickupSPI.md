<table>
    <tbody>
        <tr>
            <td>
                <strong>SPI</strong>
            </td>
            <td>
                <h2>PickupSPI</h2>
            </td>
        </tr>
        <tr>
            <td>
                <Strong>Operation</strong>
            </td>
            <td>

                ```java
                Item pickup(GameData gameData, Entity item)
                ```

            </td>
        </tr>
        <tr>
            <td>
                <Strong>Description</strong>
            </td>
            <td>
                Player can pickup items
            </td>
        </tr>
        <tr>
            <td>
                <Strong>Parameters</strong>
            </td>
            <td>

                `GameData gameData` - The game data

                `Entity item` - Item to pickup

            </td>
        </tr>
        <tr>
            <td>
                <Strong>PreConditions</strong>
            </td>
            <td>
                Item and player have collided
            </td>
        </tr>
        <tr>
            <td>
                <Strong>PostConditions</strong>
            </td>
            <td>
                Item has been picked up by player
            </td>
        </tr>
    </tbody>
</table>