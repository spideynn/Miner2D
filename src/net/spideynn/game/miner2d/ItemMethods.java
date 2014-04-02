package net.spideynn.game.miner2d;

import java.util.List;

public abstract interface ItemMethods
{
  public abstract Tile getPlace(List<String> paramList);
  
  public abstract boolean use();
  
  public abstract Item copyitem();
  
  public abstract String getTypeOf();
  
  public abstract boolean getIsSmeltable();
  
  public abstract Item getSmelted();
}
