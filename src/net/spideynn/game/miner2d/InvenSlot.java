package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class InvenSlot
  implements InvenMethods, Serializable
{
  private static final long serialVersionUID = -2361169259713072225L;
  public int x;
  public int y;
  public int w;
  public int h;
  public int slot;
  public Inventory hook;
  
  public InvenSlot(int x, int y, int w, int h, int slot, Inventory hook)
  {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.slot = slot;
    this.hook = hook;
  }
  
  public InvenSlot()
  {
    this.x = 0;
    this.y = 0;
    this.w = 40;
    this.h = 40;
    this.slot = 0;
    this.hook = null;
  }
  
  public void render(Graphics g, GameContainer gc)
  {
    g.setLineWidth(1.0F);
    g.setColor(new Color(127, 127, 127, 255));
    g.drawRect(this.x, this.y, this.w, this.h);
    if (this.hook.i[this.slot] != null)
    {
      g.drawImage((Image)MD.game.textureBin.get(this.hook.i[this.slot].img), 
        this.x + 4, this.y + 4);
      if (this.hook.i[this.slot].stack > 1)
      {
        String d = String.valueOf(this.hook.i[this.slot].stack);
        g.setColor(new Color(0, 0, 0, 192));
        g.fillRect(this.x + 40 - g.getFont().getWidth(d) - 2, this.y + 40 - 
          g.getFont().getHeight(d) - 2, 
          g.getFont().getWidth(d), g.getFont().getHeight(d));
        g.setColor(new Color(0, 128, 255, 255));
        g.drawString(d, this.x + 40 - g.getFont().getWidth(d) - 2, this.y + 40 - 
          g.getFont().getHeight(d) - 2);
      }
      if (this.hook.i[this.slot].tool)
      {
        g.setColor(new Color(0, 0, 0, 255));
        g.fillRect(this.x, this.y + 40.0F - 5.0F, 40.0F, 4.0F);
        g.setColor(new Color(0, 255, 0, 255));
        g.fillRect(this.x, this.y + 40.0F - 5.0F, 
          (int)(this.hook.i[this.slot].usage / 204.0F), 4.0F);
      }
    }
  }
  
  public void renderTop(Graphics g, GameContainer gc)
  {
    if ((Mouse.getX() > this.x) && (Mouse.getX() < this.x + this.w) && 
      (gc.getHeight() - Mouse.getY() > this.y) && 
      (gc.getHeight() - Mouse.getY() < this.y + this.h))
    {
      g.setColor(new Color(255, 255, 255, 96));
      g.fillRect(this.x, this.y, this.w, this.h);
      if (this.hook.i[this.slot] != null)
      {
        String s = this.hook.i[this.slot].name + "\nIs Fuel: " + 
          String.valueOf(this.hook.i[this.slot].smeltable) + 
          "\nSmelt Last: " + this.hook.i[this.slot].smeltlast;
        g.setColor(new Color(0, 92, 192, 128));
        g.fillRect(Mouse.getX() + 10, gc.getHeight() - Mouse.getY() + 
          10, g.getFont().getWidth(s) + 1, g.getFont()
          .getHeight(s) + 1);
        g.setColor(new Color(255, 255, 255, 255));
        g.drawString(s, Mouse.getX() + 10, 
          gc.getHeight() - Mouse.getY() + 10);
      }
    }
  }
}
