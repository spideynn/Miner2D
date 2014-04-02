package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;

public class DiamondItem
  extends Item
  implements Serializable
{
  private static final long serialVersionUID = 8678190831630588395L;
  
  public Tile getPlace(List args)
  {
    return null;
  }
  
  public boolean use()
  {
    return true;
  }
  
  public Item copyitem()
  {
    DiamondItem copy = new DiamondItem(0);
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
    return null;
  }
  
  public DiamondItem(int stack)
  {
    super(stack, 64, 15, false, "Diamond", false, 0);
  }
  
  public DiamondItem()
  {
    super(0, 0, 0, false, "", false, 0);
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
