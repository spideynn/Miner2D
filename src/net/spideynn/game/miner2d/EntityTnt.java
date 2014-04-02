package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class EntityTnt
  extends LivingEntity
  implements Serializable
{
  private static final long serialVersionUID = -5599792152206583907L;
  public int alpha = 0;
  public boolean d = true;
  public Tile parent;
  public int xplode = 0;
  
  public EntityTnt() {}
  
  public boolean isColliding()
  {
    if (this.x < 0.0F) {
      return true;
    }
    if (this.y < 0.0F) {
      return true;
    }
    if (this.x > MD.game.s.mapWidth * MD.game.tileWidth) {
      return true;
    }
    if (this.y > MD.game.s.mapHeight * MD.game.tileHeight) {
      return true;
    }
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
  
  public EntityTnt(float x, float y, int health, Tile parent)
  {
    super(x, y, MD.game.tileWidth, MD.game.tileHeight, 1);
    this.parent = parent;
    this.xplode = health;
  }
  
  public void render(Graphics g)
  {
    if ((this.x + this.w > MD.game.s.camX) && 
      (this.x < MD.game.s.camX + MD.game.container.getWidth()) && 
      (this.y + this.h > MD.game.s.camY) && 
      (this.y < MD.game.s.camY + MD.game.container.getHeight()))
    {
      int ix = (int)(this.x / MD.game.tileWidth);
      int iy = (int)(this.y / MD.game.tileHeight);
      g.drawImage((Image)MD.game.textureBin.get(41), this.x - MD.game.s.camX, this.y - 
        MD.game.s.camY, this.x - MD.game.s.camX + MD.game.tileWidth, this.y - 
        MD.game.s.camY + MD.game.tileHeight, 0.0F, 0.0F, 
        MD.game.tileWidth, MD.game.tileHeight, new Color(
        MD.game.light_r[ix][iy], MD.game.light_g[ix][iy], 
        MD.game.light_b[ix][iy], MD.game.light[ix][iy]));
      g.setColor(new Color(255, 255, 255, this.alpha));
      g.fillRect(this.x - MD.game.s.camX, this.y - MD.game.s.camY, this.w, this.h);
    }
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
    if (this.d)
    {
      this.alpha += 1;
      if (this.alpha > 199) {
        this.d = false;
      }
    }
    else if (this.alpha > 0)
    {
      this.alpha -= 1;
    }
    else
    {
      this.d = true;
    }
    this.xplode -= 1;
    if (this.xplode < 1)
    {
      MD.game.createExplosion((int)(this.x / MD.game.tileWidth), 
        (int)(this.y / MD.game.tileHeight), 4, true, true, this.parent);
      return false;
    }
    return true;
  }
  
  public void hit(int amount) {}
  
  public String getTypeEntity()
  {
    return "Tnt";
  }
}
