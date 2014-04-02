package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;

public class WoodenAxeItem
  extends Item
  implements Serializable
{
  private static final long serialVersionUID = -7140381597825318937L;
  
  public WoodenAxeItem()
  {
    super(1, 1, 30, false, "Wooden Axe", false, 0);
    this.tool = true;
    this.usage = 8192;
  }
  
  public Tile getPlace(List<String> args)
  {
    return null;
  }
  
  public boolean use()
  {
    this.usage -= 100;
    if (this.usage < 1) {
      return false;
    }
    return true;
  }
  
  public Item copyitem()
  {
    WoodenAxeItem copy = new WoodenAxeItem();
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
    return "WoodenAxeItem";
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
