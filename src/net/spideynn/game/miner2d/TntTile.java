package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class TntTile
  extends Tile
  implements Serializable
{
  private static final long serialVersionUID = -7022327364872606356L;
  public int x = 0;
  public int y = 0;
  public boolean explodeX = false;
  
  public TntTile(int x, int y)
  {
    super(41, true, false, true, true);
    this.x = x;
    this.y = y;
    this.isInflatable = true;
  }
  
  public TntTile() {}
  
  public String getType()
  {
    return "TNT";
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
    return new TntItem(1);
  }
  
  public void breakTile() {}
  
  public void placeTile() {}
  
  public void Serialized() {}
  
  public void Deserialized() {}
  
  public boolean RightClick()
  {
    MD.game.s.map[this.x][this.y] = null;
    if (this.explodeX)
    {
      MD.game.s.entities.add(new EntityTnt(this.x * MD.game.tileWidth, this.y * 
        MD.game.tileHeight, 50 + Runtime.rand
        .nextInt(100), this));
      this.explodeX = false;
    }
    else
    {
      MD.game.s.entities.add(new EntityTnt(this.x * MD.game.tileWidth, this.y * 
        MD.game.tileHeight, 200 + Runtime.rand
        .nextInt(200), this));
    }
    return false;
  }
  
  public int getHardness()
  {
    return 8192;
  }
  
  public boolean tileUpdate()
  {
    return true;
  }
}
