package dk.sdu.sesem4.common.data.entity;
/**
 * Item class that represents an item in the game.
 */
public class Item {
    // The name of a specific item.
    private String name;
    // The description of a specific item.
    private String description;

    /**
     * The Constructor for the Item class.
     * @param name The name of the item.
     * @param description The description of the item.
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Get the name  of the item.
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }
    /**
     * Get the description of the item.
     * @return The description of the item.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Set the name of the item.
     * @param name The name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Set the description of the item.
     * @param description The description of the item.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the name and description of the item.
     * @return The name and description of the item.
     */
    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", description=" + description + '}';
    }
}
