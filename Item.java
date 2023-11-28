public class Item 
{
  private String name; 
  private int weight;
  private String itemDesc;
  private String isCollectable;

  public Item(String name, int weight)
  {
    this.name = name;
    this.weight = weight;
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

  public void setCollectable(String iscollectable)
  {   
      this.isCollectable = iscollectable;
  }

  public String getCollectable()
  {
      return isCollectable;
  }

  public boolean isCollectableItem()
  {
      if (isCollectable.equalsIgnoreCase("Storybook is not a collectable item.")) 
      {
          return false;
      }

      return true;
  }
}
