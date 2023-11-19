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
        Room gardenEntrance, fieldOfGold, mellowMeadow, holyGrail, nightfallGarden, crystallinePath, faerieLands, tritonsTrident, sunFields, riddledWonders, jadePalace;
      
        // create the rooms
        gardenEntrance = new Room("entrance to the royal gardens");
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
        jadePalace.setDetailedDesc("Congratulations! \n You have made it to your destination. \nThe precious stone you seek for lies in this room. \n Type 'take emerald' to claim.");

        fieldOfGold.addItems(new String[] {"bag"});
        jadePalace.addItems(new String[] {"emerald"});
        holyGrail.addItems(new String[] {"storybook"});
        
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
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
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
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // establish a new command called "take"
        else if (commandWord.equals("take")) {
            takeItem(secondWord);
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
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the gardens.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
    /**
    /**
     * print out the story and history behind the royal gardens
     * when the method is called
     */
    public void readStorybook()
    {
        System.out.println("the story will uh come prolly last");
    }

    /**
     * take the item in the room and add to inventory
     * when the method is called
     * @param item
     */
    public void takeItem(String item) 
    {
        if(item == null) 
        {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }
    
        if (currentRoom.allItems.contains(item)) 
        {
            System.out.println(item + " successfully added to inventory.");
        }
        else 
        {
            System.out.println("item is not in room.");
        }
        
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
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            currentRoom.getDetailedDesc();
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