package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;

public class SaplingItem
  extends Item
  implements Serializable
{
  private static final long serialVersionUID = -718973732332694428L;
  
  public SaplingItem(int stack)
  {
    super(stack, 64, 13, true, "Sapling", true, 50);
    this.torchplace = true;
    this.downplace = true;
  }
  
  public SaplingItem() {}
  
  public Tile getPlace(List<String> args)
  {
    return new SaplingTile(Integer.valueOf((String)args.get(0)).intValue(), 
      Integer.valueOf((String)args.get(1)).intValue());
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
    return "SaplingItem";
  }
  
  public Item copyitem()
  {
    SaplingItem copy = new SaplingItem(0);
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
