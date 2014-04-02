package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;

public class PorkchopItem
  extends Item
  implements Serializable
{
  private static final long serialVersionUID = -8564013217112861152L;
  
  public PorkchopItem(int stack)
  {
    super(stack, 64, 39, false, "Porkchop", false, 0);
    this.food = true;
    this.healthrep = 15;
  }
  
  public PorkchopItem() {}
  
  public Tile getPlace(List<String> args)
  {
    return null;
  }
  
  public boolean use()
  {
    this.stack -= 1;
    if (this.stack < 1) {
      return false;
    }
    return true;
  }
  
  public Item copyitem()
  {
    PorkchopItem copy = new PorkchopItem(0);
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
    return "PorkchopItem";
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
