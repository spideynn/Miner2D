package net.spideynn.game.miner2d;

import java.io.Serializable;

public abstract class LivingEntity
  implements Serializable, LivingEntityMethods
{
  private static final long serialVersionUID = -5279682885461103877L;
  public float x;
  public float y;
  public float w;
  public float h;
  public int health;
  public float fx;
  public float fy;
  
  public LivingEntity(float x, float y, float w, float h, int health)
  {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.health = health;
    this.fx = 0.0F;
    this.fy = 0.0F;
  }
  
  public LivingEntity()
  {
    this.x = 0.0F;
    this.y = 0.0F;
    this.w = 24.0F;
    this.h = 24.0F;
    this.health = 10;
    this.fx = 0.0F;
    this.fy = 0.0F;
  }
}
