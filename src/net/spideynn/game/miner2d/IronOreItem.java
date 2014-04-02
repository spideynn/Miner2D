package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;

public class IronOreItem
  extends Item
  implements Serializable
{
  private static final long serialVersionUID = 1298165374336035975L;
  
  public IronOreItem(int stack)
  {
    super(stack, 64, 21, true, "Iron Ore", false, 0);
  }
  
  public IronOreItem() {}
  
  public Tile getPlace(List<String> args)
  {
    return new IronTile();
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
    IronOreItem copy = new IronOreItem(0);
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
    return "IronOreItem";
  }
  
  public boolean getIsSmeltable()
  {
    return true;
  }
  
  public Item getSmelted()
  {
    return new IronIngotItem(1);
  }
}
