package de.szut.zuul;

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
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private Room upExit;
    private Room downExit;

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
     * @param north The north exit.
     * @param east  The east east.
     * @param south The south exit.
     * @param west  The west exit.
     * @param up    The up exit.
     * @param down  The down exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) {
        if (north != null) {
            northExit = north;
        }
        if (east != null) {
            eastExit = east;
        }
        if (south != null) {
            southExit = south;
        }
        if (west != null) {
            westExit = west;
        }
        if (up != null) {
            upExit = up;
        }
        if (down != null) {
            downExit = down;
        }
    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }

    public Room getExit(String direction) {
        Room Exit;
        switch (direction) {
            case "north":
                Exit = northExit;
                break;
            case "east":
                Exit = southExit;
                break;
            case "south":
                Exit = eastExit;
                break;
            case "west":
                Exit = westExit;
                break;
            case "up":
                Exit = upExit;
                break;
            case "down":
                Exit = downExit;
                break;
            default:
                Exit = null;
        }
        return Exit;
    }

    public String existsToString() {
        StringBuilder existsList = new StringBuilder("");
        if (northExit != null) {
            existsList.append("north");
        }
        if (southExit != null) {
            if (existsList.length() > 0) {
                existsList.append(", ");
            }
            existsList.append("south");
        }
        if (eastExit != null) {
            if (existsList.length() > 0) {
                existsList.append(", ");
            }
            existsList.append("east");
        }
        if (westExit != null) {
            if (existsList.length() > 0) {
                existsList.append(", ");
            }
            existsList.append("west");
        }
        if (upExit != null) {
            if (existsList.length() > 0) {
                existsList.append(", ");
            }
            existsList.append("up");
        }
        if (downExit != null) {
            if (existsList.length() > 0) {
                existsList.append(", ");
            }
            existsList.append("down");
        }
        return existsList.toString();
    }

}

