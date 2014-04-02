package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;

public class EntityPig
  extends LivingEntity
  implements Serializable
{
  private static final long serialVersionUID = 563225355819558856L;
  public float redness = 0.0F;
  public float forceredness = 0.0F;
  
  public EntityPig(float x, float y, int health)
  {
    super(x, y, 24.0F, 24.0F, health);
  }
  
  public EntityPig() {}
  
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
  
  public void render(Graphics g)
  {
    if ((this.x + this.w > MD.game.s.camX) && 
      (this.x < MD.game.s.camX + MD.game.container.getWidth()) && 
      (this.y + this.h > MD.game.s.camY) && 
      (this.y < MD.game.s.camY + MD.game.container.getHeight()))
    {
      g.setColor(new Color(
        MD.game.light[((int)(this.x / MD.game.tileWidth))][((int)(this.y / MD.game.tileHeight))], 
        0, 
        (int)(MD.game.light[((int)(this.x / MD.game.tileWidth))][((int)(this.y / MD.game.tileHeight))] / 2.56D), 
        255));
      g.fillRect(this.x - MD.game.s.camX, this.y - MD.game.s.camY, 
        this.w, this.h);
      g.setColor(new Color(255, 0, 0, (int)this.redness));
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
    if (this.health < 1)
    {
      for (int i = 0; i < Runtime.rand.nextInt(3); i++) {
        MD.game.s.items.add(new PickableItem(new RawPorkchopItem(1), 
          this.x, this.y, Runtime.rand.nextFloat() * 5.0F - 2.5F, 
          Runtime.rand.nextFloat() * -2.0F, 16.0F, 16.0F));
      }
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
            break label279;
          }
          else
          {
            this.health -= 12;
            break label279;
            this.health -= 15;
            break label279;
            this.health -= 25;
          }
          break;
        }
        label243:
        this.health -= 2;
      }
      else
      {
        this.health -= 2;
      }
    }
    else {
      this.health -= amount;
    }
    label279:
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
    return "Pig";
  }
}
