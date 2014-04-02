package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;

public class IronSwordItem
  extends Item
  implements Serializable
{
  private static final long serialVersionUID = -2390601950259204992L;
  
  public IronSwordItem()
  {
    super(1, 1, 36, false, "Iron Sword", false, 0);
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
    IronSwordItem copy = new IronSwordItem();
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
    return "IronSwordItem";
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
