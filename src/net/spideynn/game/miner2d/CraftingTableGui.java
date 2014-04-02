package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class CraftingTableGui
  extends Gui
  implements Serializable
{
  public Item holding = null;
  public int ix = 0;
  public int iy = 0;
  public Inventory hook;
  public int sw = 0;
  public int sh = 0;
  private static final long serialVersionUID = -7301460994349206324L;
  
  public CraftingTableGui(Inventory chest)
  {
    this.sw = 0;
    this.sh = 0;
    this.hook = chest;
  }
  
  public CraftingTableGui()
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
    boolean refreshInven = true;
    if (button == 0) {
      for (InvenSlot c : this.guis_s) {
        if ((x > c.x) && (x < c.x + c.w) && (y > c.y) && (y < c.y + c.h))
        {
          if (this.holding != null)
          {
            if ((c.hook == this.hook) && (c.slot == 9))
            {
              if ((c.hook.i[9] == null) || 
                (!c.hook.i[9].name.equals(this.holding.name))) {
                break;
              }
              int takenaway = 0;
              for (int i = 0; i < c.hook.i[9].stack; i++)
              {
                if (this.holding.stack >= this.holding.maxstack) {
                  break;
                }
                this.holding.stack += 1;
                takenaway++;
              }
              c.hook.i[9].stack -= takenaway;
              if (c.hook.i[9].stack < 1)
              {
                c.hook.i[9] = null;
                craft(); break;
              }
              refreshInven = false; break;
            }
            if (c.hook.i[c.slot] == null)
            {
              c.hook.i[c.slot] = this.holding;
              this.holding = null; break;
            }
            if (c.hook.i[c.slot].name == this.holding.name)
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
            if ((c.hook == this.hook) && (c.slot == 9)) {
              craft();
            }
            this.holding = slotx;
            this.ix = (Mouse.getX() - c.x);
            this.iy = (this.sh - Mouse.getY() - c.y); break;
          }
          if (c.hook.i[c.slot] == null) {
            break;
          }
          this.holding = c.hook.i[c.slot];
          if ((c.hook == this.hook) && (c.slot == 9)) {
            craft();
          }
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
          if ((c.hook == this.hook) && (c.slot == 9)) {
            break;
          }
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
            if ((c.hook == this.hook) && (c.slot == 9)) {
              craft();
            }
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
          if ((c.hook == this.hook) && (c.slot == 9)) {
            craft();
          }
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
    if (refreshInven) {
      reloadCrafting();
    }
  }
  
  public void craft()
  {
    for (int i = 0; i < 9; i++) {
      if (this.hook.i[i] != null)
      {
        this.hook.i[i].stack -= 1;
        if (this.hook.i[i].stack < 1) {
          this.hook.i[i] = null;
        }
      }
    }
  }
  
  public void reloadCrafting()
  {
    this.hook.i[9] = null;
    String[][] current = new String[3][3];
    int iy;
    for (int ix = 0; ix < 3; ix++) {
      for (iy = 0; iy < 3; iy++) {
        current[ix][iy] = "";
      }
    }
    if (this.hook.i[0] != null) {
      current[0][0] = this.hook.i[0].name;
    }
    if (this.hook.i[1] != null) {
      current[1][0] = this.hook.i[1].name;
    }
    if (this.hook.i[2] != null) {
      current[2][0] = this.hook.i[2].name;
    }
    if (this.hook.i[3] != null) {
      current[0][1] = this.hook.i[3].name;
    }
    if (this.hook.i[4] != null) {
      current[1][1] = this.hook.i[4].name;
    }
    if (this.hook.i[5] != null) {
      current[2][1] = this.hook.i[5].name;
    }
    if (this.hook.i[6] != null) {
      current[0][2] = this.hook.i[6].name;
    }
    if (this.hook.i[7] != null) {
      current[1][2] = this.hook.i[7].name;
    }
    if (this.hook.i[8] != null) {
      current[2][2] = this.hook.i[8].name;
    }
    for (CraftingRecipe cp : MD.game.crafts)
    {
      boolean brk1 = false;
      for (int ix_ = 0; ix_ < cp.misx.length; ix_++)
      {
        int ax = cp.misx[ix_];
        int ay = cp.misy[ix_];
        String[][] currentcompare = new String[3][3];
        for (int ixx = 0; ixx < 3; ixx++) {
          for (int iyx = 0; iyx < 3; iyx++)
          {
            int tx = ixx + ax;
            int ty = iyx + ay;
            if ((tx > -1) && (tx < 3) && (ty > -1) && (ty < 3)) {
              currentcompare[tx][ty] = cp.crafting[ixx][iyx];
            }
          }
        }
        for (int ixx = 0; ixx < 3; ixx++) {
          for (int iyx = 0; iyx < 3; iyx++) {
            if (currentcompare[ixx][iyx] == null) {
              currentcompare[ixx][iyx] = "";
            }
          }
        }
        if ((current[0][0].equals(currentcompare[0][0])) && 
          (current[1][0].equals(currentcompare[1][0])) && 
          (current[2][0].equals(currentcompare[2][0])) && 
          (current[0][1].equals(currentcompare[0][1])) && 
          (current[1][1].equals(currentcompare[1][1]))) {
          if (current[2][1].equals(currentcompare[2][1])) {
            if (current[0][2].equals(currentcompare[0][2])) {
              if (current[1][2].equals(currentcompare[1][2])) {
                if (current[2][2].equals(currentcompare[2][2]))
                {
                  this.hook.i[9] = cp.getItem();
                  brk1 = true;
                  break;
                }
              }
            }
          }
        }
      }
      if (brk1) {
        break;
      }
    }
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
    x = 90.0F;
    y = 80.0F;
    slot = 0;
    for (int iy = 0; iy < 3; iy++)
    {
      for (int ix = 0; ix < 3; ix++)
      {
        this.guis_s.add(new InvenSlot((int)(tranx + x + ix * 45), 
          (int)(trany + y), 40, 40, slot, this.hook));
        slot++;
      }
      x = 90.0F;
      y += 45.0F;
    }
    this.guis_s.add(new InvenSlot((int)(tranx + x + 135.0F), 
      (int)(trany + y - 90.0F), 40, 40, 9, this.hook));
    this.guis_l.add(new InvenLabel((int)(tranx + 315.0F - 225.0F), 
      (int)(trany + 260.0F), "Inventory"));
    this.guis_l.add(new InvenLabel((int)(tranx + 315.0F - 225.0F), 
      (int)(trany + 60.0F), "Crafting"));
  }
}
