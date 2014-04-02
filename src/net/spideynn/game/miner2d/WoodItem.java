package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;

public class WoodItem
  extends Item
  implements Serializable
{
  private static final long serialVersionUID = -8024660163167111672L;
  
  public WoodItem(int stack)
  {
    super(stack, 64, 9, true, "Wood", true, 200);
  }
  
  public WoodItem() {}
  
  public Tile getPlace(List<String> args)
  {
    return new WoodTile();
  }
  
  public boolean use()
  {
    this.stack -= 1;
    if (this.stack < 1) {
      return false;
    }
    return true;
  }
  
  public String getTypeOf()
  {
    return "WoodItem";
  }
  
  public Item copyitem()
  {
    WoodItem copy = new WoodItem(0);
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
  
  public boolean getIsSmeltable()
  {
    return false;
  }
  
  public Item getSmelted()
  {
    return null;
  }
}
