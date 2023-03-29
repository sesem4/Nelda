<table>
    <tbody>
        <tr>
            <td>
                <strong>SPI</strong>
            </td>
            <td>
                <h2>ProcessingServiceSPI</h2>
            </td>
        </tr>
        <tr>
            <td>
                <Strong>Operation</strong>
            </td>
            <td>

                ```java
                void process(GameData gameData, Priority priority)
                ```

            </td>
        </tr>
        <tr>
            <td>
                <Strong>Description</strong>
            </td>
            <td>
                Main processing system
            </td>
        </tr>
        <tr>
            <td>
                <Strong>Parameters</strong>
            </td>
            <td>

                `GameData gameData` - The game data

                `Priority priority` - The priority, which is to be run for the current process round

            </td>
        </tr>
        <tr>
            <td>
                <Strong>PreConditions</strong>
            </td>
            <td>

                A complete game loop has been completed

            </td>
        </tr>
        <tr>
            <td>
                <Strong>PostConditions</strong>
            </td>
            <td>

                The system has been processed

            </td>
        </tr>
    </tbody>
</table>