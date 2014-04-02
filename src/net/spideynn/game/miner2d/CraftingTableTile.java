package net.spideynn.game.miner2d;

import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class CraftingTableTile
  extends Tile
{
  private static final long serialVersionUID = 9161704985136984017L;
  public Inventory crafting;
  public CraftingTableGui megui;
  public int x;
  public int y;
  
  public CraftingTableTile(int x, int y)
  {
    super(17, true, false, true, true);
    this.x = x;
    this.y = y;
    this.crafting = new Inventory(new Item[10]);
    this.megui = new CraftingTableGui(this.crafting);
  }
  
  public CraftingTableTile()
  {
    super(17, true, false, true, true);
    this.crafting = new Inventory(new Item[10]);
    this.megui = new CraftingTableGui(this.crafting);
  }
  
  public String getType()
  {
    return "Crafting Table";
  }
  
  public void render(Graphics g, int ix, int iy, int tx, int ty)
  {
    if (MD.game.light[ix][iy] > 0)
    {
      g.drawImage((Image)MD.game.textureBin.get(MD.game.s.map[ix][iy].img), tx * 
        MD.game.tileWidth - MD.game.s.camX, ty * 
        MD.game.tileHeight - MD.game.s.camY, new Color(
        MD.game.light_r[ix][iy], MD.game.light_g[ix][iy], 
        MD.game.light_b[ix][iy], MD.game.light[ix][iy]));
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
    return new CraftingTableItem(1);
  }
  
  public void breakTile()
  {
    this.crafting.drop(this.x * MD.game.tileWidth, this.y * MD.game.tileHeight);
  }
  
  public void placeTile() {}
  
  public void Serialized() {}
  
  public void Deserialized() {}
  
  public boolean RightClick()
  {
    MD.game.currentGui = this.megui;
    this.megui.resized(MD.game.container);
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
