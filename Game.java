import java.util.ArrayList;
import java.util.Random;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
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

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private int currentWeight = 0;
    private int maxWeight = 60;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private Item emerald;
    private Item bag;
    private Item storybook;
    private Character faerie;
    private Character goblin;
    private Character elf;
    private Room fieldOfGold;
    private Room crystallinePath;
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
     * Also set the detailed desciption of whether there are any items 
     * in the room to interact with
     */
    private void createRooms()
    {
        Room gardenEntrance, mellowMeadow, holyGrail, nightfallGarden, faerieLands, tritonsTrident, sunFields, riddledWonders, jadePalace;
      
        // create the rooms
        gardenEntrance = new Room("entrance to the Royal Gardens");
        fieldOfGold = new Room("in the Field of Gold");
        mellowMeadow = new Room("in the Mellow Meadow");
        holyGrail = new Room("in the Holy Grail");
        nightfallGarden = new Room("in the Nightfall Garden");
        crystallinePath = new Room("in the Crystalline Path");
        faerieLands = new Room("in the Faerie Lands");
        tritonsTrident = new Room("in Triton's Trident");
        sunFields = new Room("in the Sun Fields");
        riddledWonders = new Room("in the Riddled Wonders");
        jadePalace = new Room("in the Jade Palace");
        
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
        holyGrail.setDetailedDesc("There is a children's storybook in this room. \nType 'read storybook' to learn more.");

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
        jadePalace.setDetailedDesc("The precious stone you seek for lies in this room. \n Type 'take emerald' to claim.");


        // create itens
        emerald = new Item("Emerald", 30);
        bag = new Item("Bag", 10);
        storybook = new Item("Storybook", 20);

        // set item desc
        emerald.setItemDesc("You have successfully obtained the missing gem of the Royal Gardens! \nYour next and final mission is to return the gem to its rightful place \nin the Field of Gold.");
        bag.setItemDesc("A convenient tool that stores all your collectable items.");
        storybook.setItemDesc("Find out more about the Royal Gardens through this item...");

        // determine whether item is collectable or not 
        emerald.setCollectable("Is a collectable item.");
        bag.setCollectable("Is a collectable item.");
        storybook.setCollectable("Is not a collectable item.");

        // assign an item to each room
        jadePalace.assignItem(emerald);
        fieldOfGold.assignItem(bag);
        holyGrail.assignItem(storybook);

        // determine which room is the magic transporter room
        gardenEntrance.setTransporterRoom("is not the magic transporter room");
        fieldOfGold.setTransporterRoom("is not the magic transporter room");
        mellowMeadow.setTransporterRoom("is not the magic transporter room");
        holyGrail.setTransporterRoom("is not the magic transporter room");
        nightfallGarden.setTransporterRoom("is not the magic transporter room");
        crystallinePath.setTransporterRoom("is the magic transporter room"); // this is the magic transporter room
        faerieLands.setTransporterRoom("is not the magic transporter room");
        tritonsTrident.setTransporterRoom("is not the magic transporter room");
        sunFields.setTransporterRoom("is not the magic transporter room");
        riddledWonders.setTransporterRoom("is not the magic transporter room");
        jadePalace.setTransporterRoom("is not the magic transporter room");

        faerie = new Character("faerie", faerieLands, tritonsTrident);
        goblin = new Character("goblin", holyGrail, mellowMeadow);
        elf = new Character("elf", jadePalace, riddledWonders);

        faerie.setCharDesc("There is a small garden faerie in the room with you. \nIt is friendly and normally found within the Faerie Lands or Triton's Trident.");
        goblin.setCharDesc("The Royal Gardens have their own set of guards overlooking the rooms. \nThere is currently one in the room with you, our trusted goblin. \nHe patrols the Mellow Meadows and Holy Grail, keeping an eye out for any pests.");
        elf.setCharDesc("The Royal Gardens' very own knight elf accompanies you in this room. She guards over two rooms, the Jade Palace and the Riddled Wonders.");
        
        currentRoom = gardenEntrance;  // start game outside

    }

    

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        // setting the winning condition
        if (emerald.equals(fieldOfGold.getAssignedItem()) == true)
        {
            System.out.println("Congratulations! \nEmerald has been placed back to its rightful place. \nThank you for playing! See you in our next adventure. Goodbye!");
        }
        
        boolean finished = false;
        while (! finished) 
        {
        Command command = parser.getCommand();
        finished = processCommand(command);
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Royal Gardens!");
        System.out.println("Royal Gardens is a new, incredibly boring adventure game.");
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
            readStorybook(secondWord);
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

    // implementations of user commands:

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
        if (roomsVisited.size() == 0)
        {
            System.out.println("No more rooms to go back to");
            return;
        }

        ArrayList<Room> newList = new ArrayList<>();
        for (int i = 0; i < (roomsVisited.size() -1); i++)
        {
            newList.add(roomsVisited.get(i));
        }
        Room lastRoomVisited = roomsVisited.get(roomsVisited.size() - 1);
        roomsVisited = newList;
        currentRoom = lastRoomVisited;

        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        currentRoom.getDetailedDesc();
    }

    /**
    /**
    /**
     * print out the story and history behind the royal gardens
     * when the method is called
     */
    public void readStorybook(String itemName)
    {
        if(itemName == null)
        {
            // if there is no second word, we don't know what to read...
            System.out.println("Read what?");
            return;
        }

        if (!itemName.equalsIgnoreCase("storybook")) 
        {
            System.out.println("Item is not readable.");
            return;
        }

        if (currentRoom.hasAssignedItem() == false) 
        {
            System.out.println("There is no item in the room."); 
            return;   
        }
        
        System.out.println("the story will uh come prolly last");
    }

    /**
     * take the item in the room and add to inventory
     * when the method is called
     * @param item
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
            System.out.println("There is no item in the room.");
            return;
        }

        // get item from room
        Item assignedItem = currentRoom.getAssignedItem();
        int itemWeight = assignedItem.getWeight();
        
        // unable to pick up item if the total weight exceeds max weight that can be carried at a time
        if ((currentWeight + itemWeight) > maxWeight)
        {
            System.out.println("You have reached the maximum carry weight. \n Return back some items before proceeding.");
        }

        else
        {
            // Check that they are trying to pick up the assigned item
            if(!itemName.equalsIgnoreCase(assignedItem.getName()))
            {
                System.out.println("Item does not exist.");
                return;
            }

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
            System.out.println(assignedItem.getName() + " successfully added to inventory.");
            System.out.println();
            System.out.println(assignedItem.getItemDesc());
            currentRoom.assignItem(null);

            // add to inventory if successfully picked up
            inventory.add(assignedItem);
        }
    }

    public void placeItem(String itemName)
    {
        if(itemName == null)
        {
            // if there is no second word, we don't know what to place...
            System.out.println("place what?");
            return;
        }

        if (!(itemName.equals("bag") || itemName.equals("storybook") || itemName.equals("emerald")))
        {
            System.out.println("Invalid item inside world");
        }

        for (Item i: inventory)
        {
            if (i.getName().equalsIgnoreCase(itemName))
            {
                // return the item to the current room and remove from inventory
                System.out.println(itemName + " has been removed from your inventory.");

                // remove from inventory 
                inventory.remove(i);

                // add to the room
                currentRoom.assignItem(i);

                return;
            }
        }
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
                roomsVisited.clear(); // cannot go back to this room
            }

            else
            {
                roomsVisited.add(currentRoom); // add to rooms visited
            }

            // do for both
            currentRoom = nextRoom;
            System.out.println();
            System.out.println(currentRoom.getLongDescription());
            currentRoom.getDetailedDesc();

            // make the characters move between the rooms
            elf.changeRoom();
            goblin.changeRoom();
            faerie.changeRoom();

            // if the player and character are in the same room, let the player know
            if (currentRoom.equals(faerie.getCurrentRoom()))
            {
                System.out.println();
                System.out.println(faerie.getCharDesc());
            }
            else if (currentRoom.equals(elf.getCurrentRoom()))
            {
                System.out.println();
                System.out.println(elf.getCharDesc());
            }
            else if (currentRoom.equals(goblin.getCurrentRoom())) 
            {
                System.out.println();
                System.out.println(goblin.getCharDesc());    
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
            return true;  // signal that we want to quit
        }
    }
}