package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class DiamondOre
  extends Tile
  implements Serializable
{
  private static final long serialVersionUID = -1553315633535305952L;
  
  public String getType()
  {
    return "Diamond Ore";
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
    if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null)
    {
      String str;
      switch ((str = MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name).hashCode())
      {
      case -689540031: 
        if (str.equals("Diamond Pickaxe")) {
          break;
        }
      case 1571401717: 
        if ((goto 134) && (str.equals("Iron Pickaxe")))
        {
          return new DiamondItem(1);
          
          return new DiamondItem(1);
        }
        break;
      }
    }
    return null;
  }
  
  public void breakTile() {}
  
  public void placeTile() {}
  
  public void Serialized() {}
  
  public void Deserialized() {}
  
  public boolean RightClick()
  {
    return false;
  }
  
  public DiamondOre()
  {
    super(14, true, false, true, true);
  }
  
  public int getHardness()
  {
    if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null)
    {
      String str;
      switch ((str = MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name).hashCode())
      {
      case -689540031: 
        if (str.equals("Diamond Pickaxe")) {}
        break;
      case -2662077: 
        if (str.equals("Wooden Pickaxe")) {
          break;
        }
        break;
      case 328999506: 
        if (str.equals("Stone Pickaxe")) {}
        break;
      case 1571401717: 
        if (!str.equals("Iron Pickaxe"))
        {
          break label168;
          return 20;
          
          return 20;
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
