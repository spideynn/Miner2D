package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;

public class DiamondShovelItem
  extends Item
  implements Serializable
{
  private static final long serialVersionUID = -2649771670038663679L;
  
  public DiamondShovelItem()
  {
    super(1, 1, 29, false, "Diamond Shovel", false, 0);
    this.tool = true;
    this.usage = 8192;
  }
  
  public Tile getPlace(List<String> args)
  {
    return null;
  }
  
  public boolean use()
  {
    this.usage -= 5;
    if (this.usage < 1) {
      return false;
    }
    return true;
  }
  
  public Item copyitem()
  {
    DiamondShovelItem copy = new DiamondShovelItem();
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
    return "DiamondShovelItem";
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
