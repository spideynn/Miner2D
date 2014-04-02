package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;

public class EntityZombie
  extends LivingEntity
  implements Serializable
{
  private static final long serialVersionUID = -5399647792297169536L;
  public float redness = 0.0F;
  public float forceredness = 0.0F;
  public int del = 0;
  public int jum = 0;
  public boolean playerCollision = true;
  
  public EntityZombie(float x, float y, int health)
  {
    super(x, y, 24.0F, 24.0F, health);
  }
  
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
  
  public EntityZombie() {}
  
  public void render(Graphics g)
  {
    if ((this.x + this.w > MD.game.s.camX) && 
      (this.x < MD.game.s.camX + MD.game.container.getWidth()) && 
      (this.y + this.h > MD.game.s.camY) && 
      (this.y < MD.game.s.camY + MD.game.container.getHeight()))
    {
      g.setColor(new Color(
        0, 
        MD.game.light[((int)(this.x / MD.game.tileWidth))][((int)(this.y / MD.game.tileHeight))], 
        (int)(MD.game.light[((int)(this.x / MD.game.tileWidth))][((int)(this.y / MD.game.tileHeight))] / 2.56D), 
        255));
      g.fillRect(this.x - MD.game.s.camX, this.y - MD.game.s.camY, 
        this.w, this.h);
      g.setColor(new Color((int)this.redness, 0, 0, 192));
      g.fillRect(this.x - MD.game.s.camX, this.y - MD.game.s.camY, 
        this.w, this.h);
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
    this.forceredness *= 0.99F;
    this.forceredness -= 0.04F;
    float lr = this.redness;
    this.redness += this.forceredness;
    if ((this.redness < 0.0F) || (this.redness > 255.0F))
    {
      this.redness = lr;
      this.forceredness *= -0.5F;
    }
    float paway = 0.0F;
    if (MD.game.s.mp.x > this.x) {
      paway += MD.game.s.mp.x - this.x;
    }
    if (MD.game.s.mp.x < this.x) {
      paway += this.x - MD.game.s.mp.x;
    }
    if (MD.game.s.mp.y > this.y) {
      paway += MD.game.s.mp.y - this.y;
    }
    if (MD.game.s.mp.y < this.y) {
      paway += this.y - MD.game.s.mp.y;
    }
    paway /= MD.game.tileWidth;
    if (paway < 100.0F)
    {
      float lxx = this.x;
      if (this.x > MD.game.s.mp.x)
      {
        this.x -= 1.0F;
        if (isColliding())
        {
          this.x = lxx;
          if (this.jum < 1)
          {
            this.fy -= 3.0F;
            this.jum = 40;
          }
          else
          {
            this.jum -= 1;
          }
        }
      }
      if (this.x < MD.game.s.mp.x)
      {
        this.x += 1.0F;
        if (isColliding())
        {
          this.x = lxx;
          if (this.jum < 1)
          {
            this.fy -= 3.0F;
            this.jum = 40;
          }
          else
          {
            this.jum -= 1;
          }
        }
      }
      if (GameTools.GetCollision(this.x, this.y, this.w, this.h, MD.game.s.mp.x, 
        MD.game.s.mp.y, MD.game.s.mp.w, MD.game.s.mp.h)) {
        if (this.playerCollision) {
          if (this.del < 1)
          {
            if (this.x < MD.game.s.mp.x) {
              MD.game.s.mp.fx += 1.2F;
            }
            if (this.x > MD.game.s.mp.x) {
              MD.game.s.mp.fx -= 1.2F;
            }
            MD.game.s.mp.fy += 1.5F;
            MD.game.s.mp.takeHealth(1);
            this.del = 25;
          }
          else
          {
            this.del -= 1;
          }
        }
      }
    }
    if (this.health < 1)
    {
      ((Sound)MD.game.soundBin.get(10)).play();
      return false;
    }
    return true;
  }
  
  public void hit(int amount)
  {
    ((Sound)MD.game.soundBin.get(Runtime.rand.nextInt(8))).play();
    if (amount == -1)
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
            break label243;
            this.health -= 4;
            break label280;
          }
          else
          {
            this.health -= 12;
            break label280;
            this.health -= 15;
            break label280;
            this.health -= 25;
          }
          break;
        }
        label243:
        this.health -= 100;
      }
      else
      {
        this.health -= 2;
      }
    }
    else {
      this.health -= amount;
    }
    label280:
    this.forceredness += 8.0F;
    if (amount == -1)
    {
      this.fy -= 3.0F;
      if (this.x > MD.game.s.mp.x) {
        this.fx += 2.0F;
      } else {
        this.fx -= 2.0F;
      }
    }
  }
  
  public String getTypeEntity()
  {
    return "Zombie";
  }
}
