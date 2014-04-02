package net.spideynn.game.miner2d;

import org.newdawn.slick.Image;

public class Particle
{
  public float x;
  public float y;
  public float fx;
  public float fy;
  public float rot;
  public Image img;
  
  public Particle(float x, float y, float fx, float fy, float rot, Image img)
  {
    this.x = x;
    this.y = y;
    this.fx = fx;
    this.fy = fy;
    this.rot = rot;
    this.img = img;
  }
  
  public Particle()
  {
    this.x = 0.0F;
    this.y = 0.0F;
    this.fx = 0.0F;
    this.fy = 0.0F;
    this.rot = 0.0F;
    this.img = null;
  }
}
