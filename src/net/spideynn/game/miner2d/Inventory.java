package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Inventory
  implements Serializable
{
  private static final long serialVersionUID = 1091823313571841697L;
  public Random rand = new Random();
  public Item[] i;
  
  public void drop(float x, float y)
  {
    for (Item it : this.i) {
      if (it != null) {
        MD.game.s.items.add(new PickableItem(it, x, y, 
          this.rand.nextFloat() * 5.0F - 2.5F, 
          this.rand.nextFloat() * 5.0F * -1.0F, 16.0F, 16.0F));
      }
    }
  }
  
  public Inventory(Item[] i)
  {
    this.i = i;
  }
  
  public Inventory() {}
}
