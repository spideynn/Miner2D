package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;

public class LadderItem
  extends Item
  implements Serializable
{
  private static final long serialVersionUID = -2798114608768794068L;
  
  public LadderItem(int stack)
  {
    super(stack, 64, 40, true, "Ladder", false, 0);
  }
  
  public LadderItem() {}
  
  public Tile getPlace(List<String> args)
  {
    return new LadderTile();
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
    LadderItem copy = new LadderItem(0);
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
    return "LadderItem";
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
