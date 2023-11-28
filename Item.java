public class Item 
{
  private String name; 
  private int weight;
  private String itemDesc;
  private String isCollectible;

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

  public void setCollectible(String isCollectible)
  {   
      this.isCollectible = isCollectible;
  }

  public String getCollectible()
  {
      return isCollectible;
  }

  public boolean isCollectibleItem()
  {
      if (isCollectible.equalsIgnoreCase("Storybook is not a collectible item.")) 
      {
          return false;
      }

      return true;
  }
}
