package net.spideynn.game.miner2d;

import java.io.Serializable;

public abstract class Item
  implements ItemMethods, Serializable
{
  private static final long serialVersionUID = 102987113695455609L;
  public String name;
  public int stack;
  public int maxstack;
  public int img;
  public boolean placeable;
  public boolean smeltable;
  public int smeltlast;
  public boolean torchplace;
  public boolean leftplace;
  public boolean rightplace;
  public boolean upplace;
  public boolean downplace;
  public boolean tool;
  public int usage;
  public boolean food;
  public int healthrep;
  
  public Item(int stack, int maxstack, int img, boolean placeable, String name, boolean smeltable, int smeltlast)
  {
    this.stack = stack;
    this.maxstack = maxstack;
    this.img = img;
    this.placeable = placeable;
    this.name = name;
    this.smeltable = smeltable;
    this.smeltlast = smeltlast;
  }
  
  public Item()
  {
    this.stack = 0;
    this.maxstack = 0;
    this.img = 0;
    this.placeable = false;
    this.name = null;
  }
  
  public Tile getPlace()
  {
    return null;
  }
}
