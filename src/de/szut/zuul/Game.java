package de.szut.zuul;

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
     */
    private void createRooms()
    {
        Room marketsquare, templePyramid, tavern, sacrificialSite, hut, jungle, cellar, secretPassage, cave, beach, wizzardRoom;
      
        // create the rooms
        marketsquare = new Room("on the market square");
        templePyramid = new Room("in a temple pyramid");
        tavern = new Room("in the tavern at the market square");
        sacrificialSite = new Room("at a sacrificial site");
        hut = new Room("in a hut");
        jungle = new Room("in the jungle");
        cellar = new Room("in the wine cellar");
        secretPassage = new Room("in a secret passage");
        cave = new Room("in a cave");
        beach = new Room("on the beach");
        wizzardRoom = new Room("in the wizzards Room");

        // initialise room exits
        marketsquare.setExit("north",tavern);
        marketsquare.setExit("east",templePyramid);
        marketsquare.setExit("west",sacrificialSite);
        templePyramid.setExit("north",hut);
        templePyramid.setExit("west",marketsquare);
        templePyramid.setExit("up",wizzardRoom);
        templePyramid.setExit("down",cellar);
        tavern.setExit("south",marketsquare);
        tavern.setExit("east",hut);
        sacrificialSite.setExit("east",marketsquare);
        sacrificialSite.setExit("down",cave);
        hut.setExit("south",templePyramid);
        hut.setExit("east",jungle);
        hut.setExit("west",tavern);
        jungle.setExit( "west", hut);
        cellar.setExit("east",secretPassage);
        cellar.setExit("up",templePyramid);
        secretPassage.setExit("west",cellar);
        secretPassage.setExit("east",cave);
        cave.setExit("up",sacrificialSite);
        cave.setExit("south",beach);
        cave.setExit("east",secretPassage);
        beach.setExit("north",cave);
        wizzardRoom.setExit("down",templePyramid);

        //place initial items
        marketsquare.dropItem(new Item("bow","a wooden bow",.5));
        cave.dropItem(new Item("treasure","a small chest filled with coins",7.5));
        wizzardRoom.dropItem(new Item("arrows", "a quiver with different arrows",1));
        jungle.dropItem(new Item("herb","a healing herb",.5));
        jungle.dropItem(new Item("cocoa","a small cocoa tree", 5));
        sacrificialSite.dropItem(new Item("knife","a big sharp knife",1));
        hut.dropItem(new Item("spear","a spear and matching sling",5));
        tavern.dropItem(new Item("food","a hearty meal",.5));
        cellar.dropItem(new Item("jewellery", "a pretty headdress",1));

        currentRoom = marketsquare;  // start game on marketsquare
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
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printInfoRoom();
    }

    private void printInfoRoom() {
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
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")){
            look();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    private void look(){
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("through the jungle. At once there is a glade. On it there a buildings...");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   "+parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
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
            printInfoRoom();
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
