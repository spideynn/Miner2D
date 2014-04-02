package net.spideynn.game.miner2d;

import org.newdawn.slick.Graphics;

public abstract interface LivingEntityMethods
{
  public abstract void render(Graphics paramGraphics);
  
  public abstract boolean update();
  
  public abstract void hit(int paramInt);
  
  public abstract String getTypeEntity();
}
