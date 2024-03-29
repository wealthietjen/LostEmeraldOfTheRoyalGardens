// Wealthie Tjendera K22046226

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Lost Emerald of the Royal Gardens" application. 
 * "Lost Emerald of the Royal Gardens" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room. 
 * 
 * The class contains methods to check if the room is the magic transporter room and 
 * if the room has any assigned items. There are also methods to add and remove items; 
 * get assigned item and get the number of items in the room; and methods to set and get the room description.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;

public class Room 
{
    public static ArrayList<Room> allRoomsCreated = new ArrayList<Room>();
    private String description;
    private String detailedDesc;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items = new ArrayList<Item>();
    private boolean isTransporterRoom;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, boolean isTransporterRoom)
    {
        this.description = description;
        this.isTransporterRoom = isTransporterRoom;
        exits = new HashMap<>();
        Room.allRoomsCreated.add(this);
    }

    /**
     * determine whether the room is a magic transporter room or not
     * @return true if the room is a magic transporter room. false if not.
     */
    public boolean isTransporterRoom()
    {
        return this.isTransporterRoom;
    }

    /**
     * add an item to a room
     * @param item item to be added
     */
    public void addItem(Item item)
    {
        items.add(item);
    }

    /**
     * get the item in a room
     * @return the item in the room
     */
    public Item getAssignedItem(int index)
    {
        return items.get(index);
    }

    /**
     * get the number of items in the room
     * @return the number of items in the room
     */
    public int getNumItemsInRoom()
    {
        return items.size();
    }

    /**
     * check whether the item is in the room
     * @param itemName item to check if is in the room
     * @return item if the item is in the room, null otherwise
     */
    public Item checkItemInRoom(String itemName)
    {
        for (Item item : items) 
        {
            if (item.getName().equalsIgnoreCase(itemName)) 
            {
                return item;    
            }
        }

        return null;
    }

    /**
     * remove an item from the room
     * @param item item to be removed
     */
    public void removeItemFromRoom(Item item)
    {
        items.remove(item);
    }

    /**
     * checks whether there are any items in the room
     * @return true if the room has item/s. false if not.
     */
    public boolean hasAssignedItem()
    {
        if (items.size() > 0) 
        {
            return true;
        }

        return false;
    }

    /**
     * set the description of an item
     * @param detailedDesc description of the item
     */
    public void setDetailedDesc(String detailedDesc)
    {
        this.detailedDesc = detailedDesc;
    }


    /**
     * get the description of the item when the method is called
     */
    public void getDetailedDesc()
    {
        System.out.println(detailedDesc);
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        int i = 0;
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) 
        /**
         * this is for structural purpose only. adding a comma after each exit in
         * the list except for after the last item
         */
        {
            if (i < exits.size() -1) 
            {
                returnString += " " + exit + ",";
                
            }
            else 
            {
                returnString += " " + exit;
            }
            i++;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}