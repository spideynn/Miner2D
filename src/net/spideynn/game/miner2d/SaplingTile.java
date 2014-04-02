package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class SaplingTile
  extends Tile
  implements Serializable
{
  private static final long serialVersionUID = -7426700668462552715L;
  public Random rand = new Random();
  public int x;
  public int y;
  public Object growth = null;
  public int growleft = 8192 + this.rand.nextInt(8192);
  
  public SaplingTile(int x, int y)
  {
    super(13, false, true, false, false);
    this.x = x;
    this.y = y;
  }
  
  public SaplingTile() {}
  
  public String getType()
  {
    return "Sapling";
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
    return new SaplingItem(1);
  }
  
  public void breakTile()
  {
    if (MD.game.tasks.contains(this.growth)) {
      MD.game.tasks.remove(this.growth);
    }
  }
  
  public void placeTile()
  {
    setGrowth();
    if (!MD.game.tasks.contains(this.growth)) {
      MD.game.tasks.add((Runnable)this.growth);
    }
  }
  
  public void Serialized()
  {
    if (MD.game.tasks.contains(this.growth)) {
      MD.game.tasks.remove(this.growth);
    }
    this.growth = null;
  }
  
  private void setGrowth()
  {
    this.growth = new Runnable()
    {
      public void run()
      {
        if (MD.game.light[SaplingTile.this.x][SaplingTile.this.y] > 200) {
          SaplingTile.this.growleft -= 1;
        }
        if (SaplingTile.this.growleft < 1)
        {
          MD.game.s.map[SaplingTile.this.x][SaplingTile.this.y] = null;
          int height = SaplingTile.this.y;
          int ix = SaplingTile.this.x;
          for (int i = 0; i < 3 + SaplingTile.this.rand.nextInt(4); i++) {
            if ((height > -1) && (height < MD.game.s.mapHeight) && 
              (ix > -1) && (ix < MD.game.s.mapWidth))
            {
              MD.game.s.map[ix][height] = new WoodTile();
              height--;
            }
          }
          for (int ixz = -1 + SaplingTile.this.rand.nextInt(2) * -1; ixz < 1 + SaplingTile.this.rand.nextInt(4) + 2; ixz++) {
            for (int iyz = -1 + SaplingTile.this.rand.nextInt(2) * -1; iyz < 1 + SaplingTile.this.rand.nextInt(4); iyz++) {
              if ((ix + ixz > -1) && (ix + ixz < MD.game.s.mapWidth) && 
                (height + iyz > -1) && 
                (height + iyz < MD.game.s.mapHeight) && 
                (MD.game.s.map[(ix + ixz)][(height + iyz)] == null)) {
                MD.game.s.map[(ix + ixz)][(height + iyz)] = new LeafTile();
              }
            }
          }
          MD.game.remtask.add((Runnable)SaplingTile.this.growth);
        }
      }
    };
  }
  
  public void Deserialized()
  {
    setGrowth();
    if (!MD.game.tasks.contains(this.growth)) {
      MD.game.tasks.add((Runnable)this.growth);
    }
  }
  
  public boolean RightClick()
  {
    if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
      if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name.equals("Bone Meal"))
      {
        this.growleft = 0;
        return true;
      }
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
