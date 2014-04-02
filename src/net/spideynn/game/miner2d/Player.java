package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Sound;

public class Player
  implements Serializable
{
  private static final long serialVersionUID = 1514632737086473050L;
  public int maxhealth;
  public boolean groundh = true;
  public int invsel;
  public boolean open;
  public float x;
  public float y;
  public float w;
  public float h;
  public float lasty = 16000.0F;
  public int health;
  public float fy;
  public float fx = 0.0F;
  public boolean react = true;
  public Inventory inven;
  public float groundhit = 0.0F;
  public int jumpgrav = 0;
  
  public void takeHealth(int amount)
  {
    if (amount < 0) {
      ((Sound)MD.game.soundBin.get(8)).play();
    } else if (amount > 0) {
      ((Sound)MD.game.soundBin.get(Runtime.rand.nextInt(8))).play();
    }
    if (!MD.game.godmode) {
      this.health -= amount;
    }
    MD.game.healthforce += 10.0F;
    if (this.health < 1)
    {
      this.inven.drop(this.x, this.y);
      ((Sound)MD.game.soundBin.get(10)).play();
      Player p = new Player(MD.game.s.mapWidth * MD.game.tileWidth / 2, 
        0.0F, 24.0F, 24.0F, 100);
      p.lasty = 0.0F;
      while (!p.isColliding()) {
        p.y += 1.0F;
      }
      p.y -= 1.0F;
      p.lasty = p.y;
      MD.game.s.mp = p;
    }
  }
  
  public boolean isCollidingLadder()
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
          if (MD.game.s.map[tx][ty].isLadder) {
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
  
  public boolean isColliding()
  {
    int sx = (int)this.x / MD.game.tileWidth;
    int sy = (int)this.y / MD.game.tileHeight;
    boolean brk;
    for (int ix = -2; ix < 3; ix++)
    {
      brk = false;
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
    for (LivingEntity le : MD.game.s.entities) {
      if ((le.getTypeEntity().equals("Tnt")) && 
        (GameTools.GetCollision(this.x, this.y, this.w, this.h, le.x, le.y, le.w, 
        le.h))) {
        return true;
      }
    }
    return false;
  }
  
  public void update(GameContainer gc)
  {
    if (!MD.game.inventory)
    {
      int curx = Mouse.getX();
      curx = (int)(curx + MD.game.s.camOffsX);
      int cx = curx / MD.game.tileWidth;
      int cury = gc.getHeight() - Mouse.getY();
      cury = (int)(cury + MD.game.s.camOffsY);
      int cy = cury / MD.game.tileHeight;
      MD.game.selectedx = ((int)(cx + MD.game.s.camX / MD.game.tileWidth));
      MD.game.selectedy = ((int)(cy + MD.game.s.camY / MD.game.tileHeight));
      if ((MD.game.lx != MD.game.selectedx) || 
        (MD.game.ly != MD.game.selectedy)) {
        MD.game.blockbreak = 8192;
      }
      MD.game.lx = MD.game.selectedx;
      MD.game.ly = MD.game.selectedy;
      if ((this.inven.i[this.invsel] != null) && (this.inven.i[this.invsel].torchplace))
      {
        boolean dox = false;
        if ((this.inven.i[this.invsel].rightplace) && 
          (MD.game.selectedx + 1 < MD.game.s.mapWidth) && 
          (MD.game.s.map[(MD.game.selectedx + 1)][MD.game.selectedy] != null) && 
          (this.inven.i[this.invsel] != null) && 
          (MD.game.s.map[(MD.game.selectedx + 1)][MD.game.selectedy]
          .getType() != this.inven.i[this.invsel].name)) {
          dox = true;
        }
        if ((this.inven.i[this.invsel].leftplace) && 
          (!dox) && 
          (MD.game.selectedx - 1 > -1) && 
          (MD.game.s.map[(MD.game.selectedx - 1)][MD.game.selectedy] != null) && 
          (this.inven.i[this.invsel] != null) && 
          (MD.game.s.map[(MD.game.selectedx - 1)][MD.game.selectedy]
          .getType() != this.inven.i[this.invsel].name)) {
          dox = true;
        }
        if ((this.inven.i[this.invsel].downplace) && 
          (!dox) && 
          (MD.game.selectedy + 1 < MD.game.s.mapHeight) && 
          (MD.game.s.map[MD.game.selectedx][(MD.game.selectedy + 1)] != null) && 
          (this.inven.i[this.invsel] != null) && 
          (MD.game.s.map[MD.game.selectedx][(MD.game.selectedy + 1)]
          .getType() != this.inven.i[this.invsel].name)) {
          dox = true;
        }
        if ((this.inven.i[this.invsel].upplace) && 
          (!dox) && 
          (MD.game.selectedy - 1 < MD.game.s.mapHeight) && 
          (MD.game.s.map[MD.game.selectedx][(MD.game.selectedy - 1)] != null) && 
          (this.inven.i[this.invsel] != null) && 
          (MD.game.s.map[MD.game.selectedx][(MD.game.selectedy - 1)]
          .getType() != this.inven.i[this.invsel].name)) {
          dox = true;
        }
        MD.game.drawselected = dox;
      }
      else
      {
        MD.game.drawselected = true;
      }
      MD.game.s.canreach = true;
      if ((MD.game.s.canreach) && (MD.game.drawselected))
      {
        int spx = (int)(this.x / MD.game.tileWidth);
        int spy = (int)(this.y / MD.game.tileHeight);
        spx -= MD.game.selectedx;
        spy -= MD.game.selectedy;
        if (spx < 0) {
          spx *= -1;
        }
        if (spy < 0) {
          spy *= -1;
        }
        if (spx + spy > MD.game.reach) {
          MD.game.s.canreach = false;
        }
      }
    }
    if ((Keyboard.isKeyDown(57)) || 
      (Keyboard.isKeyDown(45)))
    {
      if (this.react)
      {
        if (!MD.game.inCommand) {
          if (MD.game.physics)
          {
            this.y += 4.0F;
            if ((MD.game.airjump) || (isColliding()))
            {
              this.fy -= 5.0F;
              ((Sound)MD.game.soundBin.get(18)).play();
            }
            this.y -= 4.0F;
          }
          else
          {
            this.y += 4.0F;
            if ((MD.game.airjump) || (isColliding()))
            {
              this.jumpgrav = 40;
              ((Sound)MD.game.soundBin.get(18)).play();
            }
            this.y -= 4.0F;
          }
        }
        this.react = false;
      }
    }
    else {
      this.react = true;
    }
    MD.game.s.camX = (this.x - gc.getWidth() / 2 + this.w / 2.0F);
    MD.game.s.camY = (this.y - gc.getHeight() / 2 + this.h / 2.0F);
    if (MD.game.s.camX < 0.0F) {
      MD.game.s.camX = 0.0F;
    }
    if (MD.game.s.camY < 0.0F) {
      MD.game.s.camY = 0.0F;
    }
    if (MD.game.s.camX > MD.game.s.mapWidth * MD.game.tileWidth - gc.getWidth()) {
      MD.game.s.camX = 
        (MD.game.s.mapWidth * MD.game.tileWidth - gc.getWidth());
    }
    if (MD.game.s.camY > MD.game.s.mapHeight * MD.game.tileHeight - gc.getHeight()) {
      MD.game.s.camY = 
        (MD.game.s.mapHeight * MD.game.tileHeight - gc.getHeight());
    }
    MD.game.s.camOffsX += MD.game.s.camX - MD.game.s.lcamX;
    MD.game.s.camOffsY += MD.game.s.camY - MD.game.s.lcamY;
    do
    {
      if (MD.game.s.camOffsX > MD.game.tileWidth)
      {
        float lef = MD.game.s.camOffsX - MD.game.tileWidth;
        MD.game.s.camOffsX = lef;
      }
      if (MD.game.s.camOffsX < 0.0F)
      {
        float lef = MD.game.s.camOffsX * -1.0F;
        MD.game.s.camOffsX = (MD.game.tileWidth - lef);
      }
    } while ((MD.game.s.camOffsX > MD.game.tileWidth) || 
      (MD.game.s.camOffsX < 0.0F));
    do
    {
      if (MD.game.s.camOffsY > MD.game.tileHeight)
      {
        float lef = MD.game.s.camOffsY - MD.game.tileHeight;
        MD.game.s.camOffsY = lef;
      }
      if (MD.game.s.camOffsY < 0.0F)
      {
        float lef = MD.game.s.camOffsY * -1.0F;
        MD.game.s.camOffsY = (MD.game.tileHeight - lef);
      }
    } while ((MD.game.s.camOffsY > MD.game.tileHeight) || 
      (MD.game.s.camOffsY < 0.0F));
    MD.game.s.lcamX = MD.game.s.camX;
    MD.game.s.lcamY = MD.game.s.camY;
    if (MD.game.physics)
    {
      this.fy *= 0.99F;
      this.fx *= 0.99F;
      float lx = this.x;
      this.x += this.fx;
      if (!MD.game.inCommand)
      {
        if ((Keyboard.isKeyDown(32)) || 
          (Keyboard.isKeyDown(205)))
        {
          this.x += 2.5F;
          this.fx *= 0.88F;
        }
        if ((Keyboard.isKeyDown(30)) || 
          (Keyboard.isKeyDown(203)))
        {
          this.x -= 2.5F;
          this.fx *= 0.88F;
        }
      }
      if (isColliding())
      {
        this.x = lx;
        this.fx *= -0.5F;
      }
      float ly = this.y;
      if (!isCollidingLadder())
      {
        this.fy += 0.1F;
        this.y += this.fy;
      }
      if (isCollidingLadder())
      {
        this.fy = 0.0F;
        if (!MD.game.inCommand)
        {
          if (Keyboard.isKeyDown(200)) {
            this.y -= 1.5F;
          }
          if (Keyboard.isKeyDown(208)) {
            this.y += 1.5F;
          }
        }
      }
      if (isColliding())
      {
        this.y = ly;
        this.fy = 0.0F;
        if (this.groundh)
        {
          int xll = (int)(this.groundhit / MD.game.tileHeight);
          if (xll > 4) {
            takeHealth(xll - 4);
          }
          this.groundhit = 0.0F;
          this.groundh = false;
        }
      }
      else
      {
        this.groundh = true;
      }
      this.groundhit += (this.y - this.lasty > 0.0F ? this.y - this.lasty : 0.0F);
    }
    else
    {
      float lx = this.x;
      if ((Keyboard.isKeyDown(32)) || 
        (Keyboard.isKeyDown(205))) {
        this.x += 2.5F;
      }
      if ((Keyboard.isKeyDown(30)) || 
        (Keyboard.isKeyDown(203))) {
        this.x -= 2.5F;
      }
      if (isColliding()) {
        this.x = lx;
      }
      if (!isCollidingLadder())
      {
        float ly = this.y;
        if (this.jumpgrav > 0)
        {
          this.jumpgrav -= 1;
          this.y -= 7.0F;
        }
        this.y += 4.0F;
        if (isColliding()) {
          this.y = ly;
        }
      }
    }
    this.lasty = this.y;
    if (this.y < 0.0F) {
      this.y = 0.0F;
    }
    if (this.x < 0.0F) {
      this.x = 0.0F;
    }
    if (this.x > MD.game.s.mapWidth * MD.game.tileWidth - this.w) {
      this.x = (MD.game.s.mapWidth * MD.game.tileWidth - this.w);
    }
    if (this.y > MD.game.s.mapHeight * MD.game.tileHeight - this.h) {
      this.y = (MD.game.s.mapHeight * MD.game.tileHeight - this.h);
    }
  }
  
  public Player(float x, float y, float w, float h, int health)
  {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.maxhealth = health;
    this.health = this.maxhealth;
    this.inven = new Inventory(new Item[45]);
    this.open = true;
    this.invsel = 30;
  }
  
  public Player()
  {
    this.x = 0.0F;
    this.y = 0.0F;
    this.w = 0.0F;
    this.h = 0.0F;
    this.maxhealth = 0;
    this.health = 0;
    this.inven = new Inventory(new Item[45]);
    this.open = true;
    this.invsel = 30;
  }
}
