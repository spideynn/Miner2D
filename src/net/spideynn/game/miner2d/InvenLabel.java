package net.spideynn.game.miner2d;

import java.io.Serializable;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class InvenLabel
  implements InvenMethods, Serializable
{
  private static final long serialVersionUID = -870711211755592392L;
  public int x;
  public int y;
  public String text;
  
  public InvenLabel()
  {
    this.x = 0;
    this.y = 0;
    this.text = "Deserialized";
  }
  
  public InvenLabel(int x, int y, String text)
  {
    this.x = x;
    this.y = y;
    this.text = text;
  }
  
  public void render(Graphics g, GameContainer gc)
  {
    g.setColor(new Color(0, 0, 0, 255));
    g.drawString(this.text, this.x, this.y);
  }
  
  public void renderTop(Graphics g, GameContainer gc) {}
}
