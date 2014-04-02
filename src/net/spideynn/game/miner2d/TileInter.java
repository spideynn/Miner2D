package net.spideynn.game.miner2d;

import org.newdawn.slick.Graphics;

public abstract interface TileInter
{
  public abstract String getType();
  
  public abstract void render(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract Item getDrop();
  
  public abstract void breakTile();
  
  public abstract void placeTile();
  
  public abstract void Serialized();
  
  public abstract void Deserialized();
  
  public abstract boolean RightClick();
  
  public abstract int getHardness();
  
  public abstract boolean tileUpdate();
}
