package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class PlayerGui
  extends Gui
  implements Serializable
{
  private static final long serialVersionUID = -710287558232234474L;
  public Item holding = null;
  public int ix = 0;
  public int iy = 0;
  public int sw = 0;
  public int sh = 0;
  
  public PlayerGui(GameContainer gc)
  {
    this.sw = gc.getWidth();
    this.sh = gc.getHeight();
  }
  
  public PlayerGui()
  {
    this.sw = 0;
    this.sh = 0;
  }
  
  public void render(Graphics g, GameContainer gc)
  {
    g.setLineWidth(1.0F);
    g.setColor(new Color(0, 96, 255, 128));
    g.fillRoundRect(gc.getWidth() / 2 - 325.0F, 
      gc.getHeight() / 2 - 250.0F, 650.0F, 500.0F, 10);
    g.setColor(Color.black);
    g.drawRoundRect(gc.getWidth() / 2 - 325.0F, 
      gc.getHeight() / 2 - 250.0F, 650.0F, 500.0F, 10);
    g.setColor(new Color(212, 212, 212, 255));
    g.fillRoundRect(gc.getWidth() / 2 - 315.0F, 
      gc.getHeight() / 2 - 240.0F, 630.0F, 480.0F, 10);
    g.setColor(new Color(127, 127, 127, 255));
    for (InvenSlot ic : this.guis_s) {
      ic.render(g, gc);
    }
    for (InvenSlot ic : this.guis_s) {
      ic.renderTop(g, gc);
    }
    for (InvenLabel ic : this.guis_l) {
      ic.render(g, gc);
    }
    if (this.holding != null)
    {
      g.drawImage((Image)MD.game.textureBin.get(this.holding.img), Mouse.getX() - this.ix + 
        5, gc.getHeight() - Mouse.getY() - this.iy + 5);
      if (this.holding.stack > 1)
      {
        String d = String.valueOf(this.holding.stack);
        g.setColor(new Color(0, 0, 0, 192));
        g.fillRect(Mouse.getX() - this.ix + 5 + 40 - g.getFont().getWidth(d) - 
          2, gc.getHeight() - Mouse.getY() - this.iy + 40 - 
          g.getFont().getHeight(d) - 2, 
          g.getFont().getWidth(d), g.getFont().getHeight(d));
        g.setColor(new Color(0, 128, 255, 255));
        g.drawString(d, Mouse.getX() - this.ix + 5 + 40 - 
          g.getFont().getWidth(d) - 2, 
          gc.getHeight() - Mouse.getY() - this.iy + 40 - 
          g.getFont().getHeight(d) - 2);
      }
    }
  }
  
  public void mousePressed(int button, int x, int y)
  {
    if (button == 0) {
      for (InvenSlot c : this.guis_s) {
        if ((x > c.x) && (x < c.x + c.w) && (y > c.y) && (y < c.y + c.h))
        {
          if (this.holding != null)
          {
            boolean dox = true;
            if ((c.hook == MD.game.s.mp.inven) && (c.slot == 44)) {
              dox = false;
            }
            if (dox)
            {
              if (c.hook.i[c.slot] == null)
              {
                c.hook.i[c.slot] = this.holding;
                this.holding = null; break;
              }
              if (c.hook.i[c.slot].name.equals(this.holding.name))
              {
                int added = 0;
                for (int i = 0; i < this.holding.stack; i++)
                {
                  if (c.hook.i[c.slot].stack >= c.hook.i[c.slot].maxstack) {
                    break;
                  }
                  c.hook.i[c.slot].stack += 1;
                  added++;
                }
                this.holding.stack -= added;
                if (this.holding.stack >= 1) {
                  break;
                }
                this.holding = null; break;
              }
              Item slotx = c.hook.i[c.slot];
              c.hook.i[c.slot] = this.holding;
              this.holding = slotx;
              this.ix = (Mouse.getX() - c.x);
              this.iy = (this.sh - Mouse.getY() - c.y); break;
            }
            if ((c.hook.i[c.slot] == null) || 
              (!this.holding.name.equals(c.hook.i[c.slot].name))) {
              break;
            }
            int takeaway = 0;
            for (int i = 0; i < c.hook.i[c.slot].stack; i++) {
              if (this.holding.stack < this.holding.maxstack)
              {
                this.holding.stack += 1;
                takeaway++;
              }
            }
            c.hook.i[c.slot].stack -= takeaway;
            if (c.hook.i[c.slot].stack >= 1) {
              break;
            }
            c.hook.i[c.slot] = null;
            if (c.slot != 44) {
              break;
            }
            MD.game.craft(); break;
          }
          if (c.hook.i[c.slot] == null) {
            break;
          }
          this.holding = c.hook.i[c.slot];
          this.ix = (Mouse.getX() - c.x);
          this.iy = (this.sh - Mouse.getY() - c.y);
          c.hook.i[c.slot] = null;
          if (c.slot != 44) {
            break;
          }
          MD.game.craft();
          


          break;
        }
      }
    } else if (button == 1) {
      for (InvenSlot c : this.guis_s) {
        if ((x > c.x) && (x < c.x + c.w) && (y > c.y) && (y < c.y + c.h))
        {
          if (this.holding != null)
          {
            if (c.hook.i[c.slot] == null)
            {
              Item cop = this.holding.copyitem();
              cop.stack = 1;
              c.hook.i[c.slot] = cop;
              this.holding.stack -= 1;
              if (this.holding.stack >= 1) {
                break;
              }
              this.holding = null; break;
            }
            if (c.hook.i[c.slot].name == this.holding.name)
            {
              if (c.hook.i[c.slot].stack >= c.hook.i[c.slot].maxstack) {
                break;
              }
              c.hook.i[c.slot].stack += 1;
              this.holding.stack -= 1;
              if (this.holding.stack >= 1) {
                break;
              }
              this.holding = null; break;
            }
            Item slotx = c.hook.i[c.slot];
            c.hook.i[c.slot] = this.holding;
            this.holding = slotx;
            this.ix = (Mouse.getX() - c.x);
            this.iy = (this.sh - Mouse.getY() - c.y); break;
          }
          if (c.hook.i[c.slot] == null) {
            break;
          }
          if (c.hook.i[c.slot].stack == 1)
          {
            this.holding = c.hook.i[c.slot];
            c.hook.i[c.slot] = null; break;
          }
          int half = c.hook.i[c.slot].stack / 2;
          this.holding = new NormalItem(half, 
            c.hook.i[c.slot].maxstack, 
            c.hook.i[c.slot].img, 
            c.hook.i[c.slot].name, 
            c.hook.i[c.slot].smeltable, 
            c.hook.i[c.slot].smeltlast);
          this.ix = (Mouse.getX() - c.x);
          this.iy = (this.sh - Mouse.getY() - c.y);
          c.hook.i[c.slot].stack -= half;
          if (c.hook.i[c.slot].stack >= 1) {
            break;
          }
          c.hook.i[c.slot] = null;
          



          break;
        }
      }
    }
    MD.game.reloadCrafting();
  }
  
  public void resized(GameContainer gc)
  {
    this.sw = gc.getWidth();
    this.sh = gc.getHeight();
    this.guis_s.clear();
    this.guis_l.clear();
    float tranx = gc.getWidth() / 2 - 315.0F;
    float trany = gc.getHeight() / 2 - 240.0F;
    float x = 90.0F;
    float y = 280.0F;
    int slot = 0;
    for (int iy = 0; iy < 4; iy++)
    {
      for (int ix = 0; ix < 10; ix++)
      {
        this.guis_s.add(new InvenSlot((int)(tranx + x + ix * 45), 
          (int)(trany + y), 40, 40, slot, MD.game.s.mp.inven));
        slot++;
      }
      x = 90.0F;
      y += 45.0F;
    }
    this.guis_s.add(new InvenSlot((int)(tranx + 320.0F), (int)(trany + 120.0F), 
      40, 40, 40, MD.game.s.mp.inven));
    this.guis_s.add(new InvenSlot((int)(tranx + 365.0F), (int)(trany + 120.0F), 
      40, 40, 41, MD.game.s.mp.inven));
    this.guis_s.add(new InvenSlot((int)(tranx + 365.0F), (int)(trany + 165.0F), 
      40, 40, 43, MD.game.s.mp.inven));
    this.guis_s.add(new InvenSlot((int)(tranx + 320.0F), (int)(trany + 165.0F), 
      40, 40, 42, MD.game.s.mp.inven));
    this.guis_s.add(new InvenSlot((int)(tranx + 410.0F), 
      (int)(trany + 120.0F + 23.0F), 40, 40, 44, MD.game.s.mp.inven));
    this.guis_l.add(new InvenLabel((int)(tranx + 315.0F - 225.0F), 
      (int)(trany + 260.0F), "Inventory"));
    this.guis_l.add(new InvenLabel((int)(tranx + 320.0F), 
      (int)(trany + 100.0F), "Crafting"));
  }
}
