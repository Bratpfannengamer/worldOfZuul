package de.szut.zuul;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west, up and down.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room {
    private String description;
    private HashMap<String, Room>exitMap=new HashMap<String, Room>();
    private HashMap<String, Item>itemMap=new HashMap<String, Item>();

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     *
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     *
     * @param direction direction of the next Room
     * @param nextRoom next Room
     *
     */
    public void setExit(String direction, Room nextRoom) {
        exitMap.put(direction,nextRoom);
    }

    /**
     *
     * Places an item in the Room
     * @param item new Item
     */
    public void dropItem(Item item){
        itemMap.put(item.getName(),item);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }
    public String getLongDescription(){
        String output="You are "+description+"\nExits: "+existsToString();
        if (!itemMap.isEmpty()){
            output = output+"\nItems in this room:\n"+itemList();
        }
        return output;
    }

    public Room getExit(String direction) {
        Room Exit=null;
        Exit=exitMap.get(direction);

        return Exit;
    }

    public String existsToString() {
        StringBuilder existsList = new StringBuilder("");
        for (String direction:exitMap.keySet()) {
            if (existsList.length() > 0) {
                existsList.append(", ");
            }
            existsList.append(direction);
        }
        return existsList.toString();
    }

    /**
     *
     */
    public String itemList(){
        StringBuilder itemList = new StringBuilder("");
        for (String name:itemMap.keySet()) {
            if (itemList.length() > 0) {
                itemList.append("\n");
            }
            itemList.append(itemMap.get(name).toString());
        }
        return itemList.toString();
    }

}

