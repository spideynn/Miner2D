package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameSave
  implements Serializable
{
  private static final long serialVersionUID = 669254393427980214L;
  
  public GameSave()
  {
    this.canreach = true;
    this.lightoutset = new ArrayList();
    this.camX = 0.0F;
    this.camY = 0.0F;
    this.camOffsX = 0.0F;
    this.camOffsY = 0.0F;
    this.lcamX = 0.0F;
    this.lcamY = 0.0F;
    this.mapWidth = 512;
    this.mapHeight = 8192;
  }
  
  public boolean canreach = true;
  public List<LivingEntity> entities = new ArrayList();
  public List<Light> lightoutset = new ArrayList();
  public float camX = 0.0F;
  public float camY = 0.0F;
  public int grassHeight = 512;
  public float camOffsX = 0.0F;
  public float camOffsY = 0.0F;
  public float lcamX = 0.0F;
  public float lcamY = 0.0F;
  public List<PickableItem> items = new ArrayList();
  public Player mp;
  public int mapWidth = 512;
  public int mapHeight = 8192;
  public Tile[][] map;
}
