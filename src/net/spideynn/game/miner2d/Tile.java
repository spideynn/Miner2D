package net.spideynn.game.miner2d;

import java.io.Serializable;

public abstract class Tile
  implements TileInter, Serializable
{
  private static final long serialVersionUID = -5242563208121118185L;
  public int img;
  public boolean collision;
  public boolean hasalpha;
  public boolean canfloat;
  public boolean declight;
  public boolean isLadder;
  public boolean isInflatable;
  
  public Tile(int img, boolean collision, boolean hasalpha, boolean canfloat, boolean declight)
  {
    this.img = img;
    this.collision = collision;
    this.hasalpha = hasalpha;
    this.canfloat = canfloat;
    this.declight = declight;
  }
  
  public Tile()
  {
    this.img = 0;
    this.collision = false;
    this.hasalpha = false;
    this.canfloat = false;
    this.declight = false;
  }
}
