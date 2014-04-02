package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class LadderTile
  extends Tile
  implements Serializable
{
  private static final long serialVersionUID = -8859203524737862308L;
  
  public LadderTile()
  {
    super(40, false, true, true, false);
    this.isLadder = true;
  }
  
  public String getType()
  {
    return "Ladder";
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
    return new LadderItem(1);
  }
  
  public void breakTile() {}
  
  public void placeTile() {}
  
  public void Serialized() {}
  
  public void Deserialized() {}
  
  public boolean RightClick()
  {
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
          break label169;
          return 40;
          
          return 80;
        }
        else
        {
          return 120;
          
          return 240;
        }
        break;
      }
    }
    label169:
    return 20;
  }
  
  public boolean tileUpdate()
  {
    return true;
  }
}
