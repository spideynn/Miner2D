package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class PickableItem
  implements Serializable
{
  private static final long serialVersionUID = -7280476475468706820L;
  public Item pick;
  public float x;
  public float y;
  public float fx;
  public float fy;
  public float w;
  public float h;
  public int alpha = 200;
  public boolean decenting = false;
  public int tickX = 0;
  
  public PickableItem(Item pick, float x, float y, float fx, float fy, float w, float h)
  {
    this.pick = pick;
    this.x = x;
    this.y = y;
    this.fx = fx;
    this.fy = fy;
    this.w = w;
    this.h = h;
  }
  
  public PickableItem()
  {
    this.pick = null;
    this.x = 0.0F;
    this.y = 0.0F;
    this.fx = 0.0F;
    this.fy = 0.0F;
    this.w = 0.0F;
    this.h = 0.0F;
  }
  
  public boolean isColliding()
  {
    int sx = (int)this.x / MD.game.tileWidth;
    int sy = (int)this.y / MD.game.tileHeight;
    for (int ix = -2; ix < 3; ix++)
    {
      boolean brk = false;
      for (int iy = -2; iy < 3; iy++)
      {
        int tx = sx + ix;
        int ty = sy + iy;
        if ((tx > -1) && (ty > -1) && (tx < MD.game.s.mapWidth) && 
          (ty < MD.game.s.mapHeight) && 
          (MD.game.s.map[tx][ty] != null) && 
          (GameTools.GetCollision(this.x, this.y, this.w, this.h, tx * 
          MD.game.tileWidth, ty * 
          MD.game.tileHeight, MD.game.tileWidth, 
          MD.game.tileHeight))) {
          if (MD.game.s.map[tx][ty].collision) {
            return true;
          }
        }
      }
      if (brk) {
        break;
      }
    }
    return false;
  }
  
  public void render(Graphics g, int light)
  {
    g.drawImage((Image)MD.game.textureBin.get(this.pick.img), this.x - MD.game.s.camX, this.y - 
      MD.game.s.camY, this.x + this.w - MD.game.s.camX, this.y + this.h - 
      MD.game.s.camY, 0.0F, 0.0F, MD.game.tileWidth, MD.game.tileHeight, 
      new Color(light, light, light, this.alpha));
  }
  
  public boolean update()
  {
    this.fx *= 0.99F;
    this.fy *= 0.99F;
    this.fy += 0.1F;
    float lx = this.x;
    this.x += this.fx;
    if (isColliding())
    {
      this.x = lx;
      this.fx *= -0.5F;
    }
    float ly = this.y;
    this.y += this.fy;
    if (isColliding())
    {
      this.y = ly;
      this.fy *= -0.5F;
    }
    if (this.y < 1.0F)
    {
      this.y = 2.0F;
      this.fy *= -0.5F;
    }
    if (this.x < 1.0F)
    {
      this.x = 2.0F;
      this.fx *= -0.5F;
    }
    if (this.x > MD.game.s.mapWidth * MD.game.tileWidth)
    {
      this.x = (MD.game.s.mapWidth * MD.game.tileWidth - 1.0F);
      this.fx *= -0.5F;
    }
    if (this.y > MD.game.s.mapHeight * MD.game.tileHeight)
    {
      this.y = (MD.game.s.mapHeight * MD.game.tileHeight - 1.0F);
      this.fy *= -0.5F;
    }
    if (this.decenting)
    {
      if (MD.game.s.mp.x + MD.game.s.mp.w / 2.0F > this.x + this.w / 2.0F) {
        this.fx += 0.05F;
      }
      if (MD.game.s.mp.x + MD.game.s.mp.w / 2.0F < this.x + this.w / 2.0F) {
        this.fx -= 0.05F;
      }
      if (MD.game.s.mp.y + MD.game.s.mp.h / 2.0F < this.y + this.h / 2.0F) {
        this.fy -= 0.2F;
      }
      if (this.tickX < 0)
      {
        this.x += 1.0F;
        this.y += 1.0F;
        this.w -= 2.0F;
        this.h -= 2.0F;
        this.tickX = 10;
      }
      else
      {
        this.tickX -= 1;
      }
      this.alpha -= 5;
      if (this.alpha < 0) {
        return false;
      }
      return true;
    }
    return true;
  }
}
