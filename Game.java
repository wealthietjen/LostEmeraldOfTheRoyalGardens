// Wealthie Tjendera K22046226

/**
 *  This class is the main class of the "Lost Emerald of the Royal Gardens" application. 
 *  "Lost Emerald of the Royal Gardens" is a very simple, text based adventure game. Players 
 *  need to locate and return the lost Emerald to its rightful place in the Royal Gardens.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

import java.util.ArrayList;
import java.util.Random;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private int currentWeight = 0;
    private int maxWeight = 30;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private Item emerald;
    private Item bag;
    private Item fallenNote;
    private Character faerie;
    private Character goblin;
    private Character elf;
    private Room fieldOfGold;
    private ArrayList<Room> roomsVisited = new ArrayList<Room>();


    public static void main(String[] args) {
        Game game1 = new Game();
        game1.play();
    }
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();

    }

    /**
     * Create all the rooms and link their exits together.
     * Create and assign items to their respective rooms and set the detailed desciption of whether there are any items 
     * in the room to interact with. Also set whether the items are collectable or not.
     * Create and assign characters to their respective rooms. Set the rooms they are meant to alternate between and their character descriptions.
     */
    private void createRooms()
    {
        Room gardenEntrance, mellowMeadow, holyGrail, nightfallGarden, crystallinePath, faerieLands, tritonsTrident, sunFields, riddledWonders, jadePalace;
      
        // create the rooms
        gardenEntrance = new Room("entrance to the Royal Gardens", false);
        fieldOfGold = new Room("in the Field of Gold", false);
        mellowMeadow = new Room("in the Mellow Meadow", false);
        holyGrail = new Room("in the Holy Grail", false);
        nightfallGarden = new Room("in the Nightfall Garden", false);
        crystallinePath = new Room("in the Crystalline Path", true);
        faerieLands = new Room("in the Faerie Lands", false);
        tritonsTrident = new Room("in Triton's Trident", false);
        sunFields = new Room("in the Sun Fields", false);
        riddledWonders = new Room("in the Riddled Wonders", false);
        jadePalace = new Room("in the Jade Palace", false);
        
        // initialise room exits
        gardenEntrance.setExit("west", fieldOfGold);
        gardenEntrance.setExit("north", mellowMeadow);
        gardenEntrance.setDetailedDesc("There are no items in this room to interact with.");

        fieldOfGold.setExit("east", gardenEntrance);
        fieldOfGold.setDetailedDesc("There is an item in this room. \nType 'take bag' to collect.");
        
        mellowMeadow.setExit("north", holyGrail);
        mellowMeadow.setExit("south", gardenEntrance);
        mellowMeadow.setDetailedDesc("There are no items in this room to interact with.");
        
        holyGrail.setExit("west", nightfallGarden);
        holyGrail.setExit("east", faerieLands);
        holyGrail.setExit("south", gardenEntrance);
        holyGrail.setDetailedDesc("There is a fallen note in this room. \nType 'read fallen note' to learn more.");

        nightfallGarden.setExit("north", crystallinePath);
        nightfallGarden.setExit("east", holyGrail);
        nightfallGarden.setDetailedDesc("There are no items in this room to interact with.");

        crystallinePath.setExit("south", nightfallGarden);
        crystallinePath.setDetailedDesc("There are no items in this room to interact with.");

        faerieLands.setExit("north", tritonsTrident);
        faerieLands.setExit("west", holyGrail);
        faerieLands.setDetailedDesc("There are no items in this room to interact with.");

        tritonsTrident.setExit("east", sunFields);
        tritonsTrident.setExit("north", riddledWonders);
        tritonsTrident.setExit("south", faerieLands);
        tritonsTrident.setDetailedDesc("There are no items in this room to interact with.");

        sunFields.setExit("west", tritonsTrident);
        sunFields.setDetailedDesc("There are no items in this room to interact with.");

        riddledWonders.setExit("west", jadePalace);
        riddledWonders.setExit("south", tritonsTrident);
        riddledWonders.setDetailedDesc("There are no items in this room to interact with.");

        jadePalace.setExit("east", riddledWonders);
        jadePalace.setDetailedDesc("The precious stone you seek for lies in this room. \nType 'take emerald' to claim.");

        // create items and set their weight and whether they are collectable or not
        emerald = new Item("Emerald", 30, true);
        bag = new Item("Bag", 10, true);
        fallenNote = new Item("Fallen note", 5, false);

        // set item desc
        emerald.setItemDesc("You have successfully obtained the missing gem of the Royal Gardens! \nYour next and final mission is to return the gem to its rightful place \nin the Field of Gold. \n(Hint: In order to return/place an item in a room, type in the command 'place ' + item name.)");
        bag.setItemDesc("A convenient tool that stores all your collectable items.");
        fallenNote.setItemDesc("Find out more about the Royal Gardens through this item...");

        // assign an item to each room
        jadePalace.addItem(emerald);
        fieldOfGold.addItem(bag);
        holyGrail.addItem(fallenNote);

        // create characters and set their assigned rooms to alternate between
        faerie = new Character("faerie", tritonsTrident, faerieLands);
        goblin = new Character("goblin", holyGrail, mellowMeadow);
        elf = new Character("elf", jadePalace, riddledWonders);

        // set the character desciption
        faerie.setCharDesc("There is a small garden faerie in the room with you. \nIt is friendly and normally found within the Faerie Lands or Triton's Trident.");
        goblin.setCharDesc("The Royal Gardens have their own set of guards overlooking the rooms. \nThere is currently one in the room with you, our trusted goblin. \nHe patrols the Mellow Meadows and Holy Grail.");
        elf.setCharDesc("The Royal Gardens' very own knight elf accompanies you in this room. \nShe guards over two rooms, the Jade Palace and the Riddled Wonders.");
        
        currentRoom = gardenEntrance;  // start game at the garden entrance

    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        
        boolean finished = false;
        while (!finished) 
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
            
            // set the winning condition
            // once satisfied, game finishes and quits
            if (fieldOfGold.checkItemInRoom(emerald.getName()) != null)
            {
                System.out.println("Congratulations! \nEmerald has been placed back to its rightful place. \nThank you for playing! See you in our next adventure. Goodbye!");
                finished = true; 
            }
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Royal Gardens!");
        System.out.println("Royal Gardens is a new adventure game.");
        System.out.println("Your goal in this game is to locate the lost Emerald of the Royal Gardens and return it to its rightful place.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        String secondWord = command.getSecondWord();
        if (commandWord.equalsIgnoreCase("help")) {
            printHelp();
        }
        else if (commandWord.equalsIgnoreCase("go")) {
            goRoom(command);
        }
        else if (commandWord.equalsIgnoreCase("quit")) {
            wantToQuit = quit(command);
        }
        // establish a new command called "take"
        else if (commandWord.equalsIgnoreCase("take")) {
            takeItem(secondWord);
        }
        else if (commandWord.equalsIgnoreCase("read")) {
            readFallenNote(secondWord);
        }
        else if(commandWord.equalsIgnoreCase("place")) {
            placeItem(secondWord);
        }
        else if(commandWord.equalsIgnoreCase("back")) {
            goBack();
        }

        // else command not recognised.
        return wantToQuit;
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println();
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the gardens.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    // go back to the previous room visited
    private void goBack()
    {
        // if there are no more rooms to go back to or the player has just spawened into the game
        if (roomsVisited.size() == 0)
        {
            System.out.println("No more rooms to go back to");
            return;
        }

        // make a new list of rooms visited excluding the current room and set that as the new roomsVisited list.
        // get the last room visited by getting the room stored in the second last index of the original roomsVisited list.
        // set that last room visited as the current room and print out the room's desciption. 
        ArrayList<Room> newList = new ArrayList<>();
        for (int i = 0; i < (roomsVisited.size() -1); i++)
        {
            newList.add(roomsVisited.get(i));
        }
        Room lastRoomVisited = roomsVisited.get(roomsVisited.size() - 1);
        roomsVisited = newList;
        currentRoom = lastRoomVisited;

        System.out.println();
        System.out.println("------------------------------------------------");
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
        if (currentRoom.getNumItemsInRoom() > 0) 
        {
            currentRoom.getDetailedDesc();   
        }
    }

    /**
     * print out the small backstory of the royal gardens
     * when the method is called
     */
    public void readFallenNote(String itemName)
    {
        if(itemName == null)
        {
            // if there is no second word, we don't know what to read...
            System.out.println("Read what?");
            return;
        }

        if (currentRoom.hasAssignedItem() == false) 
        {
            // if there is no item in the current room then there is nothing to be read
            System.out.println("There is no item to read in the room."); 
            return;   
        }

        if (!itemName.equalsIgnoreCase("fallen note")) 
        {
            // if the second word is not a readable item, it cannot be read
            System.out.println("Item is not readable.");
            return;
        }

        // set and print the contents of the fallen note
        System.out.println();
        System.out.println("The note reads...");
        System.out.println();
        System.out.println("'The Royal Gardens is a place full of wonders and magic.");
        System.out.println("It is home to some of the most beautiful and exotic flora and fauna one could ever hope to see in their lifetime.");
        System.out.println("This is a place wherein every creature - big or small - lives in harmony. The royal family to which this garden belongs to still reigns to this day.");
        System.out.println("It took them many years to develop the gardens into the beauty it is today, and as such, the Royal Gardens became a symbol of determination");
        System.out.println("and strength unlike any other. Only members of the royal family and those appointed by them are allowed access to the gardens.");
        System.out.println("Otherwise, entry to the Royal Gardens is strictly prohibited.'\n");
        System.out.println("-- This is where the note ends. You may now proceed to the next desired room using the 'go' command. --\n");
    }

    /**
     * take the item in the room and add to inventory
     * when the method is called. this is only if the total weight of the player and items do not exceed the 
     * maximum weight the player can carry.
     * @param itemName is the name of the item being taken/picked up by the player
     */
    public void takeItem(String itemName) 
    {

        if(itemName == null)
        {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }

        // Check if the room even has an assigned item
        if (currentRoom.hasAssignedItem() == false)  
        {
            // No item in room
            System.out.println("There is no item to take in the room.");
            return;
        }

        // check if the item is in the room
        Item assignedItem = currentRoom.checkItemInRoom(itemName);
        if(assignedItem == null)
        {
            System.out.println("Item does not exist in this room.");
            return;
        }

        // get the item's weight
        int itemWeight = assignedItem.getWeight();
        
        // unable to pick up item if the total weight exceeds max weight that can be carried at a time
        if ((currentWeight + itemWeight) > maxWeight)
        {
            System.out.println("You have reached the maximum carry weight. \nPlace back some items before proceeding by typing the phrase 'place' + item.");
        }

        else
        {
            // check that they are trying to pick up a collectable item
            if(assignedItem.isCollectableItem() == false)
            {
                System.out.println();
                System.out.println("Item is not collectable.");
                return;
            }

            // add to current weight if they are
            currentWeight =+ assignedItem.getWeight();
            System.out.println();

            // add to inventory if item successfully picked up and print out its description
            inventory.add(assignedItem);
            System.out.println(assignedItem.getName() + " successfully added to inventory.\n");
            System.out.println(assignedItem.getItemDesc());
            System.out.println();
            currentRoom.removeItemFromRoom(assignedItem);
            
        }
    }

    /**
     * remove item in player's inventory and place it into the current room they are in.
     * @param itemName is the name of the item being placed back.
     */
    public void placeItem(String itemName)
    {
        if(itemName == null)
        {
            // if there is no second word, we don't know what to place...
            System.out.println("place what?");
            return;
        }

        // check if the item exists in this game
        if (!(itemName.equalsIgnoreCase("bag") || itemName.equalsIgnoreCase("fallen note") || itemName.equalsIgnoreCase("emerald")))
        {
            System.out.println("Invalid item inside game");
            return;
        }

        // removing from inventory and adding to the room
        for (Item i: inventory)
        {
            if (i.getName().equalsIgnoreCase(itemName))
            {
                // return the item to the current room and remove from inventory
                System.out.println();
                System.out.println(itemName + " has been removed from your inventory.");
                System.out.println();

                // remove from inventory 
                inventory.remove(i);

                // add to the room
                currentRoom.addItem(i);

                // subtract item weight from total weight
                currentWeight =- i.getWeight();

                return;
            }
        }

        // print if the item is not in player's inventory
        System.out.println("Item is not in your inventory.");

    }   

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {

            // setting up the magic transporter room
            if (nextRoom.isTransporterRoom() == true) 
            {   
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println("IMPORTANT NOTICE:");
                System.out.println("You have entered the magic teleporter room. \nYou will now be teleported to a random room in the Royal Gardens.");
                Random rand = new Random();
                int upperBound = 10;
                while (nextRoom.isTransporterRoom() == true)
                {
                    /*
                    * generate a random index according to the number of rooms 
                    * in the game. 
                    * current room is set to the transporter room. 
                    * if the room the player will be transported to is not 
                    * the current room then the player will the successfully transported. 
                    * otherwise, the program will continue generating indexes until it
                    * gets one which does not correspond to the magic transporter room.
                    */
                    int randomIndex = rand.nextInt(upperBound);
                    nextRoom = Room.allRoomsCreated.get(randomIndex);
                }
                roomsVisited.clear(); // cannot go back to magic transporter room
            }

            else
            {
                roomsVisited.add(currentRoom); // add to rooms visited
            }

            // do for both
            currentRoom = nextRoom;
            System.out.println();
            System.out.println("------------------------------------------------");
            System.out.println(currentRoom.getLongDescription());
            System.out.println();
            if (currentRoom.getNumItemsInRoom() > 0) 
            {
                currentRoom.getDetailedDesc();   
            }

            // make the characters move between their designated rooms
            elf.changeRoom();
            goblin.changeRoom();
            faerie.changeRoom();

            // if the player and character are in the same room, let the player know
            if (currentRoom.equals(faerie.getCurrentRoom()))
            {
                System.out.println();
                System.out.println(faerie.getCharDesc());
                System.out.println();
            }
            else if (currentRoom.equals(elf.getCurrentRoom()))
            {
                System.out.println();
                System.out.println(elf.getCharDesc());
                System.out.println();
            }
            else if (currentRoom.equals(goblin.getCurrentRoom())) 
            {
                System.out.println();
                System.out.println(goblin.getCharDesc());   
                System.out.println(); 
            }
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            System.out.println("\nThank you for playing the Lost Emerald of the Royal Gardens. \nGoodbye!\n");
            return true;  // signal that we want to quit
        }
    }
}