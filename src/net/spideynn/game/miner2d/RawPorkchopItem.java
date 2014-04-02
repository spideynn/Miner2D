package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;

public class RawPorkchopItem
  extends Item
  implements Serializable
{
  private static final long serialVersionUID = -4445676516371400539L;
  
  public RawPorkchopItem(int stack)
  {
    super(stack, 64, 38, false, "Raw Porkchop", false, 0);
    this.food = true;
    this.healthrep = 5;
  }
  
  public RawPorkchopItem() {}
  
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
    RawPorkchopItem copy = new RawPorkchopItem(0);
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
    return "RawPorkchopItem";
  }
  
  public boolean getIsSmeltable()
  {
    return true;
  }
  
  public Item getSmelted()
  {
    return new PorkchopItem(1);
  }
}
