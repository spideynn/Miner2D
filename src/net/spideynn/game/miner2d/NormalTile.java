package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class NormalTile
  extends Tile
  implements Serializable
{
  private static final long serialVersionUID = -6133637142665569193L;
  boolean drops = false;
  public String name;
  
  public NormalTile(int img, boolean drops, String name)
  {
    super(img, true, false, true, true);
    this.drops = drops;
    this.name = name;
  }
  
  public NormalTile() {}
  
  public String getType()
  {
    return this.name;
  }
  
  public void render(Graphics g, int ix, int iy, int tx, int ty)
  {
    if (MD.game.light[ix][iy] > 0)
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
    if (getType().equals("Stone"))
    {
      if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null)
      {
        String str;
        switch ((str = MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name).hashCode())
        {
        case -689540031: 
          if (str.equals("Diamond Pickaxe")) {}
          break;
        case -2662077: 
          if (str.equals("Wooden Pickaxe")) {
            break;
          }
          break;
        case 328999506: 
          if (str.equals("Stone Pickaxe")) {}
          break;
        case 1571401717: 
          if (!str.equals("Iron Pickaxe"))
          {
            break label204;
            return new CobbleStoneItem(1);
            
            return new CobbleStoneItem(1);
          }
          else
          {
            return new CobbleStoneItem(1);
            
            return new CobbleStoneItem(1);
          }
          break;
        }
        label204:
        return null;
      }
      return null;
    }
    if (this.drops) {
      return new NormalItem(1, 64, this.img, getType(), false, 0);
    }
    return null;
  }
  
  public void breakTile() {}
  
  public void placeTile() {}
  
  public void Serialized() {}
  
  public void Deserialized() {}
  
  public boolean RightClick()
  {
    return false;
  }
  
  public int getHardness()
  {
    String str1;
    switch ((str1 = getType()).hashCode())
    {
    case -1382845029: 
      if (str1.equals("Wooden Planks")) {}
      break;
    case 2130343: 
      if (str1.equals("Dirt")) {
        break;
      }
      break;
    case 69063062: 
      if (str1.equals("Grass")) {}
      break;
    case 80218181: 
      if (str1.equals("Stone")) {}
      break;
    case 1433103174: 
      if (!str1.equals("Bedrock"))
      {
        break label828;
        if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null)
        {
          String str2;
          switch ((str2 = MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name).hashCode())
          {
          case -2015121105: 
            if (str2.equals("Diamond Shovel")) {}
            break;
          case -1300227091: 
            if (str2.equals("Wooden Shovel")) {
              break;
            }
            break;
          case -1012433666: 
            if (str2.equals("Stone Shovel")) {}
            break;
          case 1521495803: 
            if (!str2.equals("Iron Shovel"))
            {
              break label292;
              return 130;
              
              return 150;
            }
            else
            {
              return 350;
              
              return 600;
            }
            break;
          }
        }
        label292:
        return 100;
        if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null)
        {
          String str3;
          switch ((str3 = MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name).hashCode())
          {
          case -689540031: 
            if (str3.equals("Diamond Pickaxe")) {}
            break;
          case -2662077: 
            if (str3.equals("Wooden Pickaxe")) {
              break;
            }
            break;
          case 328999506: 
            if (str3.equals("Stone Pickaxe")) {}
            break;
          case 1571401717: 
            if (!str3.equals("Iron Pickaxe"))
            {
              break label465;
              return 30;
              
              return 70;
            }
            else
            {
              return 100;
              
              return 180;
            }
            break;
          }
        }
        label465:
        return 10;
      }
      else
      {
        return 0;
        if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null)
        {
          String str4;
          switch ((str4 = MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name).hashCode())
          {
          case -2015121105: 
            if (str4.equals("Diamond Shovel")) {}
            break;
          case -1300227091: 
            if (str4.equals("Wooden Shovel")) {
              break;
            }
            break;
          case -1012433666: 
            if (str4.equals("Stone Shovel")) {}
            break;
          case 1521495803: 
            if (!str4.equals("Iron Shovel"))
            {
              break label648;
              return 130;
              
              return 150;
            }
            else
            {
              return 350;
              
              return 600;
            }
            break;
          }
        }
        label648:
        return 100;
        if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null)
        {
          String str5;
          switch ((str5 = MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name).hashCode())
          {
          case -1247130620: 
            if (str5.equals("Wooden Axe")) {
              break;
            }
            break;
          case -715133805: 
            if (str5.equals("Stone Axe")) {}
            break;
          case -658076158: 
            if (str5.equals("Diamond Axe")) {}
            break;
          case -247217226: 
            if (!str5.equals("Iron Axe"))
            {
              break label825;
              return 40;
              
              return 80;
            }
            else
            {
              return 120;
              
              return 240;
            }
            break;
          }
        }
        label825:
        return 20;
      }
      break;
    }
    label828:
    return 0;
  }
  
  public boolean tileUpdate()
  {
    return true;
  }
}
