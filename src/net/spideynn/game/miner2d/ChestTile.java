package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ChestTile
  extends Tile
  implements Serializable
{
  private static final long serialVersionUID = -2276445059398829142L;
  public Inventory chest;
  public ChestGui chestgui;
  public int x;
  public int y;
  
  public ChestTile(int x, int y)
  {
    super(16, true, true, true, false);
    this.x = x;
    this.y = y;
    this.chest = new Inventory(new Item[40]);
    this.chestgui = new ChestGui(this.chest);
  }
  
  public ChestTile()
  {
    super(16, true, true, true, false);
    this.x = 0;
    this.y = 0;
    this.chest = new Inventory(new Item[40]);
    this.chestgui = new ChestGui(this.chest);
  }
  
  public String getType()
  {
    return "Chest";
  }
  
  public void render(Graphics g, int ix, int iy, int tx, int ty)
  {
    if (MD.game.light[ix][iy] > 0)
    {
      g.drawImage((Image)MD.game.textureBin.get(MD.game.s.map[ix][iy].img), tx * 
        MD.game.tileWidth - MD.game.s.camX, ty * 
        MD.game.tileHeight - MD.game.s.camY, new Color(
        MD.game.light[ix][iy], MD.game.light[ix][iy], 
        MD.game.light[ix][iy], 255));
    }
    else
    {
      g.setColor(new Color(0, 0, 0, 255));
      g.fillRect(ix * MD.game.tileWidth - MD.game.s.camX, iy * 
        MD.game.tileHeight - MD.game.s.camY, MD.game.tileWidth, 
        MD.game.tileWidth);
    }
  }
  
  public Item getDrop()
  {
    return new ChestItem(1);
  }
  
  public void breakTile()
  {
    this.chest.drop(this.x * MD.game.tileWidth, this.y * MD.game.tileHeight);
  }
  
  public void placeTile() {}
  
  public void Serialized() {}
  
  public void Deserialized() {}
  
  public boolean RightClick()
  {
    MD.game.currentGui = this.chestgui;
    this.chestgui.resized(MD.game.container);
    MD.game.inventory = true;
    return false;
  }
  
  public int getHardness()
  {
    if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null)
    {
      String str;
      switch ((str = MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name).hashCode())
      {
      case -1247130620: 
        if (str.equals("Wooden Axe")) {
          break;
        }
        break;
      case -715133805: 
        if (str.equals("Stone Axe")) {}
        break;
      case -658076158: 
        if (str.equals("Diamond Axe")) {}
        break;
      case -247217226: 
        if (!str.equals("Iron Axe"))
        {
          break label168;
          return 30;
          
          return 60;
        }
        else
        {
          return 80;
          
          return 120;
        }
        break;
      }
    }
    label168:
    return 10;
  }
  
  public boolean tileUpdate()
  {
    return true;
  }
}
