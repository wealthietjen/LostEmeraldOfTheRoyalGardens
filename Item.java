public class Item 
{
  private String name; 
  private int weight;
  private String itemDesc;
  private boolean isCollectableItem = false;

  public Item(String name, int weight, boolean isCollectableItem)
  {
    this.name = name;
    this.weight = weight;
    this.isCollectableItem = isCollectableItem;
  }

  public boolean isCollectableItem()
  {
    return isCollectableItem;
  }

  public String getName()
  {
    return name;
  }

  public int getWeight()
  {
  return weight;
  }

  public void setItemDesc(String itemDesc)
  {
    this.itemDesc = itemDesc;
  } 

  public String getItemDesc()
  {
    return itemDesc;
  }
}
