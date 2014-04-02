package net.spideynn.game.miner2d;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract interface GuiMethods
{
  public abstract void render(Graphics paramGraphics, GameContainer paramGameContainer);
  
  public abstract void mousePressed(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void resized(GameContainer paramGameContainer);
}
