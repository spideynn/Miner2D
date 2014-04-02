package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class TorchTile
  extends Tile
  implements Serializable
{
  private static final long serialVersionUID = -4168139384034224464L;
  public Light myLight;
  
  public TorchTile(int x, int y)
  {
    super(6, false, true, false, false);
    this.myLight = new Light(x, y, 200, 5, 255, 255, 255);
  }
  
  public TorchTile() {}
  
  public String getType()
  {
    return "Torch";
  }
  
  public void render(Graphics g, int ix, int iy, int tx, int ty)
  {
    if ((MD.game.light[ix][iy] > 0) && (this.img != -1))
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
    return new TorchItem(1);
  }
  
  public void breakTile()
  {
    if (MD.game.s.lightoutset.contains(this.myLight))
    {
      MD.game.s.lightoutset.remove(this.myLight);
      MD.game.reloadLights(-1);
    }
  }
  
  public void placeTile()
  {
    if (!MD.game.s.lightoutset.contains(this.myLight))
    {
      MD.game.s.lightoutset.add(this.myLight);
      MD.game.reloadLights(-1);
    }
  }
  
  public void Serialized() {}
  
  public void Deserialized() {}
  
  public boolean RightClick()
  {
    return false;
  }
  
  public int getHardness()
  {
    return 8192;
  }
  
  public boolean tileUpdate()
  {
    this.img = -1;
    if ((this.myLight.x - 1 > -1) && 
      (MD.game.s.map[(this.myLight.x - 1)][this.myLight.y] != null) && 
      (MD.game.s.map[(this.myLight.x - 1)][this.myLight.y].getType() != 
      getType())) {
      this.img = 7;
    }
    if ((this.myLight.x + 1 < MD.game.s.mapWidth) && 
      (MD.game.s.map[(this.myLight.x + 1)][this.myLight.y] != null) && 
      (MD.game.s.map[(this.myLight.x + 1)][this.myLight.y].getType() != 
      getType())) {
      this.img = 8;
    }
    if ((this.myLight.y + 1 < MD.game.s.mapHeight) && 
      (MD.game.s.map[this.myLight.x][(this.myLight.y + 1)] != null) && 
      (MD.game.s.map[this.myLight.x][(this.myLight.y + 1)].getType() != 
      getType())) {
      this.img = 6;
    }
    if (this.img == -1) {
      return false;
    }
    return true;
  }
}
