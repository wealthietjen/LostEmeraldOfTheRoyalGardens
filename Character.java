/**
 * the Character class determines the attributes each character created will have.
 * 
 * each character moves between two rooms that have been assigned to them. they cannot be 
 * interacted with; player will only know if they are currently in the same space as a character.
 */

public class Character 
{

    private Room firstRoom;
    private String charType;
    private String charDesc;
    private Room secondRoom;

    /**
     * each character will be defined with the following information
     * @param charType is the type of character they are (i.e. goblin, elf)
     * @param firstRoom is the room where they are initially spawned
     * @param secondRoom is the room where they will move towards when the 'go' command is called.
     * each character will alternate between these two rooms everytime the 'go' command is called. 
     */
    public Character(String charType, Room firstRoom, Room secondRoom)
    {
        this.charType = charType;
        this.firstRoom = firstRoom;
        this.secondRoom = secondRoom;
    }

    /**
     * describing the character created
     * @param charDesc is the character description
     */
    public void setCharDesc(String charDesc)
    {
        this.charDesc = charDesc;
    }

    /**
     * @return the room the character is currently in
     */
    public Room getCurrentRoom()
    {
        return firstRoom;
    }

    /**
     * @return the character description
     */
    public String getCharDesc()
    {
        return charDesc;
    }

    /**
     * when this method is called, the character will move to the other room that they were assigned to.
     * the aforementioned room will not be the current room the character is in.
     */
    public void changeRoom()
    {
        // when the player moves, so do the characters
        // whenever the goRoom method is called, the moveAround method will be called too
        Room tempRoom = firstRoom;
        firstRoom = secondRoom;
        secondRoom = tempRoom;

        
    }
}
    

