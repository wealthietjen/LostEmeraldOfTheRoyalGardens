public class Character {

    private Room firstRoom;
    private String charType;
    private String charDesc;
    private Room secondRoom;

    public Character(String charType, Room firstRoom, Room secondRoom)
    {
        this.charType = charType;
        this.firstRoom = firstRoom;
        this.secondRoom = secondRoom;
    }

    public void setCharDesc(String charDesc)
    {
        this.charDesc = charDesc;
    }

    public Room getCurrentRoom()
    {
        return firstRoom;
    }

    public String getCharDesc()
    {
        return charDesc;
    }

    public void changeRoom()
    {
        // when the player moves, so do the characters
        // whenever the goRoom method is called, the moveAround method will be called too
        Room tempRoom = firstRoom;
        firstRoom = secondRoom;
        secondRoom = tempRoom;

        
    }
}
    

