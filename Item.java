// Wealthie Tjendera K22046226

/**
 * This class is part of the "Lost Emerald of the Royal Gardens" application.
 * "Lost Emerald of the Royal Gardens" is a very simple, text based adventure game.
 * 
 * An "Item" is created and assigned to a Room and it can be interacted with in several ways. 
 * Some items are collectable and some are not. Each item has their own corresponding weight.
 * Collectable items can be taken and added to each player's respective inventories and thus, can also be 
 * removed from the inventory and placed at in a desired Room.
 */

public class Item 
{
  private String name; 
  private int weight;
  private String itemDesc;
  private boolean isCollectableItem = false;

  /**
   * each item can be defined with the following information
   * @param name name of the item
   * @param weight weight of the item 
   * @param isCollectableItem whether the item is collectable or not
   */
  public Item(String name, int weight, boolean isCollectableItem)
  {
    this.name = name;
    this.weight = weight;
    this.isCollectableItem = isCollectableItem;
  }

  /**
   * check whether the item is collectable or not
   * @return true if the item is collectable and false if not
   */
  public boolean isCollectableItem()
  {
    return isCollectableItem;
  }

  /**
   * get the item's name
   * @return item's name
   */
  public String getName()
  {
    return name;
  }

  /**
   * get the item's weight
   * @return item's weight
   */
  public int getWeight()
  {
  return weight;
  }

  /**
   * set the item description
   * @param itemDesc item description
   */
  public void setItemDesc(String itemDesc)
  {
    this.itemDesc = itemDesc;
  } 

  /**
   * get the item description
   * @return the item description
   */
  public String getItemDesc()
  {
    return itemDesc;
  }
}
