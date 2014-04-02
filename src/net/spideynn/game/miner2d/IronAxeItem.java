package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;

public class IronAxeItem
  extends Item
  implements Serializable
{
  private static final long serialVersionUID = 398976108545392277L;
  
  public IronAxeItem()
  {
    super(1, 1, 32, false, "Iron Axe", false, 0);
    this.tool = true;
    this.usage = 8192;
  }
  
  public Tile getPlace(List<String> args)
  {
    return null;
  }
  
  public boolean use()
  {
    this.usage -= 30;
    if (this.usage < 1) {
      return false;
    }
    return true;
  }
  
  public Item copyitem()
  {
    IronAxeItem copy = new IronAxeItem();
    copy.downplace = this.downplace;
    copy.img = this.img;
    copy.leftplace = this.leftplace;
    copy.maxstack = this.maxstack;
    copy.name = this.name;
    copy.placeable = this.placeable;
    copy.rightplace = this.rightplace;
    copy.smeltable = this.smeltable;
    copy.smeltlast = this.smeltlast;
    copy.stack = this.stack;
    copy.torchplace = this.torchplace;
    copy.upplace = this.upplace;
    copy.tool = this.tool;
    copy.usage = this.usage;
    return copy;
  }
  
  public String getTypeOf()
  {
    return "IronAxeItem";
  }
  
  public boolean getIsSmeltable()
  {
    return false;
  }
  
  public Item getSmelted()
  {
    return null;
  }
}
