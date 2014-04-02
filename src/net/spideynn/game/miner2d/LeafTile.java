package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class LeafTile
  extends Tile
  implements Serializable
{
  private static final long serialVersionUID = 7112155102756621932L;
  Random rand = new Random();
  
  public LeafTile()
  {
    super(10, true, true, true, false);
  }
  
  public String getType()
  {
    return blockDta.getName(this.img);
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
    if (this.rand.nextInt(100) > 80) {
      return new SaplingItem(1);
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
  
  public int getHardness()
  {
    if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null)
    {
      String str;
      switch ((str = MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name).hashCode())
      {
      case -1335946075: 
        if (str.equals("Iron Sword")) {}
        break;
      case -1034388495: 
        if (str.equals("Diamond Sword")) {}
        break;
      case -180043405: 
        if (str.equals("Wooden Sword")) {
          break;
        }
        break;
      case -32212414: 
        if (!str.equals("Stone Sword"))
        {
          break label172;
          return 180;
        }
        else
        {
          return 240;
          
          return 360;
          
          return 480;
        }
        break;
      }
    }
    label172:
    return 120;
  }
  
  public boolean tileUpdate()
  {
    return true;
  }
}
