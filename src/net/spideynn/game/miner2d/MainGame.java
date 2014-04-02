package net.spideynn.game.miner2d;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.imageout.ImageOut;

public class MainGame
  extends BasicGame
{
  public AppGameContainer container;
  public boolean physics = true;
  public boolean instantmine = false;
  public boolean mobspawn = true;
  public boolean dbgrend = false;
  public boolean esmenu = false;
  public GameSave s = new GameSave();
  public Image logo;
  public int reach = 12;
  public List<MenuButton> esm = new ArrayList();
  public List<Sound> soundBin = new ArrayList();
  public int deflight = 0;
  public int sunlight = 275 + this.s.grassHeight;
  
  public List<String> argReturn(String data, String sep)
  {
    List<String> args = new ArrayList();
    int pos = 0;
    args.add("");
    for (int i = 0; i < data.length(); i++)
    {
      String charz = data.substring(i, i + 1);
      if (charz.contains(sep))
      {
        args.add("");
        pos++;
      }
      else
      {
        args.set(pos, (String)args.get(pos) + charz);
      }
    }
    return args;
  }
  
  public boolean frozen = false;
  public boolean inCommand;
  public String currentCommand = "";
  public int lx;
  public int ly;
  public boolean airjump = false;
  public String acommand = "";
  public boolean renderinven = true;
  public String[] commands = { "/toolbox", "/stuck", "/treefarm", 
    "/sunlight", "/torch", "/deflight", "/zombierain", "/tp", 
    "/killall", "/pork", "/furnace", "/godmode", "/heal", "/walled", 
    "/house", "/mobspawn", "/instantmine", "/airjump", "/frozen", 
    "/explosion", "/tnt", "/tntdirt", "/tntpole", "/worldtnt", 
    "/reach", "/spawnzombie", "/physics", "/build", "/tree", 
    "/tntsurface" };
  public String[] commandargs = { " <diamond/iron/stone/wooden>", "", "", 
    " <(number)intensity/default>", " - spawnes 64 torches", 
    " <(number)intensity/default>", "", " <x> <y>", "", "", "", "", "", 
    "", "", "", "", "", "", " <radius> <x> <y>", "", "", "", "", 
    " <(number)distance/default>", " <x> <y> <amount>", "", "", "", "" };
  public float healthforce = 0.0F;
  public float healthbright = 0.0F;
  public List<Image> textureBin = new ArrayList();
  public List<Image> breakingBin = new ArrayList();
  public JFrame generateWorld = null;
  public int tileWidth = 32;
  public int tileHeight = 32;
  public boolean inventory = false;
  public Gui currentGui;
  public PlayerGui playerGui;
  public int blockbreak = 8192;
  public boolean godmode = false;
  public int[][] light;
  public int[][] light_r;
  public int[][] light_g;
  public int[][] light_b;
  public List<Runnable> tasks = new ArrayList();
  public boolean genWorldNow = false;
  public long milliLas = 0L;
  
  public void initPlayer()
  {
    System.out.println("Initializing Player...");
    Player p = new Player(MD.game.s.mapWidth * MD.game.tileWidth / 2, 0.0F, 
      24.0F, 24.0F, 100);
    p.lasty = 0.0F;
    while (!p.isColliding()) {
      p.y += 1.0F;
    }
    p.y -= 1.0F;
    p.lasty = p.y;
    MD.game.s.mp = p;
  }
  
  public MainGame(String title)
  {
    super(title);
  }
  
  public boolean drawselected = true;
  public int selectedx = 0;
  public int selectedy = 0;
  public int lw = 0;
  public int lh = 0;
  
  public void renderTile(Graphics g, int ix, int iy, int tx, int ty)
  {
    if (this.s.map[ix][iy] != null)
    {
      if (this.s.map[ix][iy].hasalpha) {
        if (iy < this.s.grassHeight + 5)
        {
          g.setColor(new Color(0, 192, 255, 255));
          g.fillRect(tx * this.tileWidth - this.s.camX, ty * this.tileHeight - 
            this.s.camY, this.tileWidth, this.tileHeight);
        }
        else
        {
          g.setColor(new Color(this.light_r[ix][iy] / 2, 
            this.light_g[ix][iy] / 5, this.light_b[ix][iy] / 100, 
            this.light[ix][iy] / 4));
          g.fillRect(tx * this.tileWidth - this.s.camX, ty * this.tileHeight - 
            this.s.camY, this.tileWidth, this.tileHeight);
        }
      }
      this.s.map[ix][iy].render(g, ix, iy, tx, ty);
      if ((ix == this.selectedx) && (iy == this.selectedy) && (this.s.canreach) && 
        (Mouse.isButtonDown(0))) {
        g.drawImage((Image)this.breakingBin.get(7 - this.blockbreak / 1170), ix * 
          this.tileWidth - this.s.camX, iy * this.tileHeight - this.s.camY);
      }
    }
    else if (iy < this.s.grassHeight + 5)
    {
      g.setColor(new Color(0, 192, 255, 255));
      g.fillRect(tx * this.tileWidth - this.s.camX, ty * this.tileHeight - this.s.camY, 
        this.tileWidth, this.tileHeight);
    }
    else
    {
      g.setColor(new Color(this.light_r[ix][iy] / 2, this.light_g[ix][iy] / 5, 
        this.light_b[ix][iy] / 100, this.light[ix][iy] / 4));
      g.fillRect(tx * this.tileWidth - this.s.camX, ty * this.tileHeight - this.s.camY, 
        this.tileWidth, this.tileHeight);
    }
  }
  
  public void renderGame(Graphics g, int WidthX, int HeightX)
  {
    g.resetTransform();
    g.setLineWidth(1.0F);
    int ya;
    int slot;
    int ix;
    String d;
    if (this.s.map != null)
    {
      g.setBackground(new Color(0, 0, 0, 255));
      for (int ix = (int)(this.s.camX / this.tileWidth); ix < this.s.camX / this.tileWidth + WidthX / this.tileWidth + 1.0F; ix++) {
        for (int iy = (int)(this.s.camY / this.tileHeight); iy < this.s.camY / this.tileHeight + HeightX / this.tileHeight + 2.0F; iy++) {
          if ((ix > -1) && (iy > -1) && (ix < this.s.mapWidth) && 
            (iy < this.s.mapHeight)) {
            renderTile(g, ix, iy, ix, iy);
          }
        }
      }
      int xa = (int)this.s.mp.x / this.tileWidth;
      ya = (int)this.s.mp.y / this.tileHeight;
      if (this.renderinven)
      {
        g.setColor(new Color(this.light[xa][ya], 0, 0, 255));
        g.fillRect(this.s.mp.x - this.s.camX, this.s.mp.y - this.s.camY, this.s.mp.w, this.s.mp.h);
        if ((this.drawselected) && (this.s.canreach))
        {
          g.setColor(Color.black);
          g.drawRect(this.selectedx * this.tileWidth - this.s.camX, this.selectedy * 
            this.tileHeight - this.s.camY, this.tileWidth, this.tileHeight);
        }
      }
      for (LivingEntity le : this.s.entities) {
        le.render(g);
      }
      for (PickableItem pi : this.s.items) {
        pi.render(
          g, 
          this.light[((int)(pi.x / this.tileWidth))][((int)(pi.y / this.tileHeight))]);
      }
      if (this.renderinven)
      {
        g.setColor(new Color(0, 96, 255, 128));
        g.fillRoundRect(WidthX / 2 - 235.0F, HeightX - 50.0F, 470.0F, 
          50.0F, 10);
        g.setColor(Color.black);
        g.setLineWidth(1.0F);
        g.drawRoundRect(WidthX / 2 - 235.0F, HeightX - 50.0F, 470.0F, 
          50.0F, 10);
        float ix_ = WidthX / 2 - 235.0F + 10.0F;
        g.setColor(new Color(0, 96, 255, 240));
        slot = 30;
        for (ix = 0; ix < 10; ix++)
        {
          if (this.s.mp.invsel == slot)
          {
            g.setColor(new Color(0, 255, 96, 240));
            g.setLineWidth(3.0F);
          }
          else
          {
            g.setColor(new Color(0, 96, 255, 240));
            g.setLineWidth(1.0F);
          }
          g.drawRect(ix_, HeightX - 45.0F, 40.0F, 40.0F);
          if (this.s.mp.inven.i[slot] != null)
          {
            g.drawImage(
              (Image)MD.game.textureBin.get(this.s.mp.inven.i[slot].img), 
              ix_ + 4.0F, HeightX - 45.0F + 4.0F);
            if (this.s.mp.inven.i[slot].stack > 1)
            {
              d = String.valueOf(this.s.mp.inven.i[slot].stack);
              g.setColor(new Color(0, 0, 0, 192));
              g.fillRect(
                ix_ + 40.0F - g.getFont().getWidth(d) - 2.0F, 
                HeightX - 45.0F + 40.0F - 
                g.getFont().getHeight(d) - 2.0F, g
                .getFont().getWidth(d), g.getFont()
                .getHeight(d));
              g.setColor(new Color(0, 128, 255, 255));
              g.drawString(d, ix_ + 40.0F - g.getFont().getWidth(d) - 
                2.0F, HeightX - 45.0F + 40.0F - 
                g.getFont().getHeight(d) - 2.0F);
            }
            if (this.s.mp.inven.i[slot].tool)
            {
              g.setColor(new Color(0, 0, 0, 255));
              g.fillRect(ix_, HeightX - 12.0F, 40.0F, 4.0F);
              g.setColor(new Color(0, 255, 0, 255));
              g.fillRect(ix_, HeightX - 12.0F, 
                (int)(this.s.mp.inven.i[slot].usage / 204.0F), 4.0F);
            }
          }
          ix_ += 45.0F;
          slot++;
        }
        g.setColor(new Color((int)this.healthbright, (int)this.healthbright, 
          (int)this.healthbright, 255));
        g.fillRect(WidthX / 2 - 235.0F, HeightX - 75.0F, 
          this.s.mp.maxhealth * 2, 20.0F);
        g.setColor(new Color(0, 255, 0, 255));
        g.fillRect(WidthX / 2 - 235.0F + 2.0F, HeightX - 75.0F + 2.0F, 
          this.s.mp.health * 2 - 4, 16.0F);
      }
      if (this.inventory) {
        this.currentGui.render(g, this.container);
      }
    }
    else
    {
      g.setBackground(new Color(255, 255, 255, 255));
      for (MenuButton mbz : this.mb)
      {
        g.setColor(new Color(0, 96, 255, 128));
        g.fillRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
        g.setColor(new Color(0, 0, 0, 255));
        g.drawRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
        g.setColor(new Color(255, 255, 255, 255));
        g.drawString(mbz.text, mbz.x + mbz.w / 2 - 
          g.getFont().getWidth(mbz.text) / 2, mbz.y + mbz.h / 2 - 
          g.getFont().getHeight(mbz.text) / 2);
        if (mbz.alpha > 0)
        {
          g.setColor(new Color(255, 255, 255, mbz.alpha));
          g.fillRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
        }
      }
      g.drawImage(this.logo, WidthX / 2 - 400.0F, 10.0F);
    }
    int ind;
    if (this.inCommand)
    {
      g.setColor(new Color(127, 127, 127, 200));
      g.fillRect(0.0F, HeightX - 23, WidthX, 23.0F);
      g.setColor(new Color(255, 255, 255, 255));
      g.drawString(this.currentCommand + "_", 0.0F, HeightX - 23);
      if (this.acommand.startsWith("/"))
      {
        int y = HeightX - g.getFont().getHeight(this.currentCommand + "_") - 
          10;
        ind = 0;
        for (String s : this.commands)
        {
          if (s.startsWith(this.acommand))
          {
            y -= g.getFont().getHeight(s) - 1;
            g.setColor(new Color(0, 255, 0, 255));
            int x = 0;
            String sub = s.substring(0, this.acommand.length());
            g.drawString(sub, 0.0F, y);
            x += g.getFont().getWidth(sub);
            g.setColor(new Color(255, 255, 255, 255));
            g.drawString(
              s.substring(this.acommand.length(), s.length()), 
              x - 2, y);
            
            x = x + g.getFont().getWidth(s.substring(this.acommand.length(), s.length()));
            g.setColor(new Color(255, 255, 0, 255));
            g.drawString(this.commandargs[ind], x, y);
          }
          ind++;
        }
      }
    }
    if (this.esmenu)
    {
      g.setColor(new Color(127, 127, 127, 200));
      g.fillRect(0.0F, 0.0F, WidthX, HeightX);
      for (MenuButton mbz : this.esm)
      {
        g.setColor(new Color(0, 96, 255, 128));
        g.fillRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
        g.setColor(new Color(0, 0, 0, 255));
        g.drawRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
        g.setColor(new Color(255, 255, 255, 255));
        g.drawString(mbz.text, mbz.x + mbz.w / 2 - 
          g.getFont().getWidth(mbz.text) / 2, mbz.y + mbz.h / 2 - 
          g.getFont().getHeight(mbz.text) / 2);
        if (mbz.alpha > 0)
        {
          g.setColor(new Color(255, 255, 255, mbz.alpha));
          g.fillRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
        }
      }
    }
    if (this.dbgrend)
    {
      g.setColor(Color.orange);
      g.drawString("FPS: " + this.container.getFPS() + "\noffsX: " + 
        this.s.camOffsX + "\noffsY: " + this.s.camOffsY + "\nX: " + 
        (int)(this.s.mp.x / this.tileWidth) + "\nY: " + 
        (int)(this.s.mp.y / this.tileHeight), 50.0F, 50.0F);
    }
  }
  
  public void createExplosion(int x, int y, int radius, boolean inflate, boolean soundeffects, Tile xploder)
  {
    float lx = x * this.tileWidth;
    float ly = y * this.tileHeight;
    for (LivingEntity le : this.s.entities)
    {
      float dx = le.x - lx;
      float dy = le.y - ly;
      float ox = dx;
      float oy = dy;
      if (dx < 0.0F) {
        dx *= -1.0F;
      }
      if (dy < 0.0F) {
        dy *= -1.0F;
      }
      if (dx + dy < 320.0F)
      {
        float px = 320.0F;
        if (ox < 0.0F) {
          px = -320.0F;
        }
        float py = 320.0F;
        if (oy < 0.0F) {
          py = -320.0F;
        }
        float fxx = px - ox;
        float fyy = py - oy;
        fxx /= 300.0F;
        fyy /= 300.0F;
        
        le.fx += fxx;
        le.fy += fyy;
        le.hit((int)(320.0F - dx + dy) / 12);
      }
    }
    float dx = this.s.mp.x - lx;
    float dy = this.s.mp.y - ly;
    float ox = dx;
    float oy = dy;
    if (dx < 0.0F) {
      dx *= -1.0F;
    }
    if (dy < 0.0F) {
      dy *= -1.0F;
    }
    if (dx + dy < 320.0F)
    {
      float px = 320.0F;
      if (ox < 0.0F) {
        px = -320.0F;
      }
      float py = 320.0F;
      if (oy < 0.0F) {
        py = -320.0F;
      }
      float fxx = px - ox;
      float fyy = py - oy;
      fxx /= 300.0F;
      fyy /= 300.0F;
      
      this.s.mp.fx += fxx;
      this.s.mp.fy += fyy;
      this.s.mp.takeHealth((int)(320.0F - dx + dy) / 12);
    }
    for (int ix = radius * -1; ix < radius + 1; ix++) {
      for (int iy = radius * -1; iy < radius + 1; iy++) {
        if ((x + ix > -1) && (y + iy > -1) && (x + ix < this.s.mapWidth) && 
          (y + iy < this.s.mapHeight) && 
          (this.s.map[(x + ix)][(y + iy)] != null))
        {
          boolean brk = true;
          if ((this.s.map[(x + ix)][(y + iy)] != xploder) && (inflate) && 
            (this.s.map[(x + ix)][(y + iy)].isInflatable))
          {
            TntTile d = (TntTile)this.s.map[(x + ix)][(y + iy)];
            d.explodeX = true;
            this.s.map[(x + ix)][(y + iy)].RightClick();
            brk = false;
          }
          if (brk) {
            if (this.s.map[(x + ix)][(y + iy)] != xploder)
            {
              if (this.rand.nextInt(800) > 600)
              {
                breakTile(x + ix, y + iy, false);
              }
              else
              {
                this.s.map[(x + ix)][(y + iy)].breakTile();
                this.s.map[(x + ix)][(y + iy)] = null;
              }
            }
            else
            {
              this.s.map[(x + ix)][(y + iy)].breakTile();
              this.s.map[(x + ix)][(y + iy)] = null;
            }
          }
        }
      }
    }
    if (soundeffects) {
      ((Sound)this.soundBin.get(21)).play();
    }
    reloadLights(-1);
  }
  
  public void SaveWorld()
  {
    try
    {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutput out = null;
      out = new ObjectOutputStream(bos);
      for (int ix = 0; ix < this.s.mapWidth; ix++) {
        for (int iy = 0; iy < this.s.mapHeight; iy++) {
          if (this.s.map[ix][iy] != null) {
            this.s.map[ix][iy].Serialized();
          }
        }
      }
      out.writeObject(this.s);
      for (int ix = 0; ix < this.s.mapWidth; ix++) {
        for (int iy = 0; iy < this.s.mapHeight; iy++) {
          if (this.s.map[ix][iy] != null) {
            this.s.map[ix][iy].Deserialized();
          }
        }
      }
      byte[] yourBytes = bos.toByteArray();
      JFileChooser fc = new JFileChooser();
      if (fc.showSaveDialog(null) == 0)
      {
        String filename = fc.getSelectedFile().getPath();
        BufferedOutputStream bosx = null;
        FileOutputStream fos = new FileOutputStream(new File(filename));
        bosx = new BufferedOutputStream(fos);
        bosx.write(yourBytes);
        out.close();
        bosx.close();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void LoadWorld()
  {
    try
    {
      JFileChooser fc = new JFileChooser();
      if (fc.showOpenDialog(null) == 0)
      {
        File file = fc.getSelectedFile();
        byte[] fileData = new byte[(int)file.length()];
        
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        dis.readFully(fileData);
        dis.close();
        
        ByteArrayInputStream bis = new ByteArrayInputStream(fileData);
        ObjectInput in = null;
        in = new ObjectInputStream(bis);
        Object o = in.readObject();
        GameSave sdd = (GameSave)o;
        this.s = sdd;
        
        this.tasks.clear();
        int iy;
        for (int ix = 0; ix < this.s.mapWidth; ix++) {
          for (iy = 0; iy < this.s.mapHeight; iy++) {
            if (this.s.map[ix][iy] != null) {
              this.s.map[ix][iy].Deserialized();
            }
          }
        }
        for (InvenSlot is : this.playerGui.guis_s) {
          is.hook = this.s.mp.inven;
        }
        if (this.light == null) {
          this.light = new int[this.s.mapWidth][this.s.mapHeight];
        }
        if (this.light_r == null) {
          this.light_r = new int[this.s.mapWidth][this.s.mapHeight];
        }
        if (this.light_g == null) {
          this.light_g = new int[this.s.mapWidth][this.s.mapHeight];
        }
        if (this.light_b == null) {
          this.light_b = new int[this.s.mapWidth][this.s.mapHeight];
        }
        reloadLights(-1);
        bis.close();
        in.close();
      }
    }
    catch (IOException|ClassNotFoundException e2)
    {
      e2.printStackTrace();
    }
  }
  
  public void adder(List<MenuButton> me)
  {
    me.clear();
    me.add(new MenuButton("Generate New World!", 
      this.container.getWidth() / 2 - 128, this.container.getHeight() / 2 - 
      32 - 96 + 100, 256, 64, new Runnable()
      {
        public void run()
        {
          try
          {
            MainGame.this.s.mapWidth = 512;
            MainGame.this.s.mapHeight = 4096;
            MainGame.this.randomGen();
            MainGame.this.initPlayer();
          }
          catch (SlickException e)
          {
            e.printStackTrace();
          }
        }
      }));
    me.add(new MenuButton("Go Fullscreen (Broken)", 
      this.container.getWidth() / 2 - 128, this.container.getHeight() / 2 - 
      32 + 100, 256, 64, new Runnable()
      {
        public void run()
        {
          try
          {
            int fullscreen = 0;
            AppGameContainer appgc = new AppGameContainer(
              MD.game);
            if (fullscreen == 0)
            {
              appgc.setFullscreen(true);
              fullscreen = 1;
            }
            else
            {
              appgc.setFullscreen(false);
              fullscreen = 0;
            }
          }
          catch (SlickException e)
          {
            e.printStackTrace();
          }
        }
      }));
    me.add(new MenuButton("Load World!", 
      this.container.getWidth() / 2 - 128, this.container.getHeight() / 2 - 
      32 + 96 + 100, 256, 64, new Runnable()
      {
        public void run()
        {
          MainGame.this.LoadWorld();
        }
      }));
  }
  
  public void windowResize(GameContainer gc)
  {
    adder(this.mb);
    this.esm.clear();
    this.esm.add(new MenuButton("Generate New World!", 
      this.container.getWidth() / 2 - 128, this.container.getHeight() / 2 - 
      32 - 192 + 100, 256, 64, new Runnable()
      {
        public void run()
        {
          try
          {
            MainGame.this.s.mapWidth = 512;
            MainGame.this.s.mapHeight = 4096;
            MainGame.this.randomGen();
            MainGame.this.initPlayer();
          }
          catch (SlickException e)
          {
            e.printStackTrace();
          }
        }
      }));
    this.esm.add(new MenuButton("Save World!", 
      this.container.getWidth() / 2 - 128, this.container.getHeight() / 2 - 
      32 - 96 + 100, 256, 64, new Runnable()
      {
        public void run()
        {
          MainGame.this.SaveWorld();
        }
      }));
    this.esm.add(new MenuButton("Load World!", 
      this.container.getWidth() / 2 - 128, this.container.getHeight() / 2 - 
      32 + 100, 256, 64, new Runnable()
      {
        public void run()
        {
          MainGame.this.LoadWorld();
        }
      }));
    this.esm.add(new MenuButton("Exit World (Without saving)", this.container
      .getWidth() / 2 - 128, this.container.getHeight() / 2 - 32 + 
      192, 256, 64, new Runnable()
      {
        public void run()
        {
          MainGame.this.s.map = null;
          MainGame.this.s.entities.clear();
          MainGame.this.s.items.clear();
          MainGame.this.s.mp = new Player(256.0F, 16000.0F, 24.0F, 24.0F, 100);
          MainGame.this.esmenu = false;
        }
      }));
    this.esm.add(new MenuButton("Back To Game", 
      this.container.getWidth() / 2 - 128, this.container.getHeight() / 2 - 
      32 - 192, 256, 64, new Runnable()
      {
        public void run()
        {
          MainGame.this.esmenu = false;
        }
      }));
    System.out.println("Window Resized!");
    if (this.currentGui != null) {
      this.currentGui.resized(gc);
    }
  }
  
  public List<MenuButton> mb = new ArrayList();
  
  public void render(GameContainer gc, Graphics g)
    throws SlickException
  {
    renderGame(g, gc.getWidth(), gc.getHeight());
  }
  
  public Random rand = new Random();
  
  public void craft()
  {
    for (int ii = 40; ii < 44; ii++) {
      if (this.s.mp.inven.i[ii] != null)
      {
        this.s.mp.inven.i[ii].stack -= 1;
        if (this.s.mp.inven.i[ii].stack < 1) {
          this.s.mp.inven.i[ii] = null;
        }
      }
    }
  }
  
  public void breakTile(int xtile, int ytile, boolean sound)
  {
    if (this.s.map[xtile][ytile] != null)
    {
      Item drop = this.s.map[xtile][ytile].getDrop();
      if (drop != null) {
        this.s.items.add(new PickableItem(drop, xtile * this.tileWidth + 8.0F, 
          ytile * this.tileHeight + 8.0F, this.rand.nextFloat() * 5.0F - 2.5F, 
          this.rand.nextFloat() * -2.0F, 16.0F, 16.0F));
      }
      if ((ytile - 1 > -1) && 
        (this.s.map[xtile][(ytile - 1)] != null) && 
        (!this.s.map[xtile][(ytile - 1)].canfloat))
      {
        Item dropx = this.s.map[xtile][(ytile - 1)].getDrop();
        if (dropx != null) {
          this.s.items.add(new PickableItem(dropx, xtile * 
            this.tileWidth + 8.0F, ytile * this.tileHeight + 8.0F, 
            this.rand.nextFloat() * 5.0F - 2.5F, this.rand
            .nextFloat() * -2.0F, 16.0F, 16.0F));
        }
        this.s.map[xtile][(ytile - 1)].breakTile();
        this.s.map[xtile][(ytile - 1)] = null;
      }
      if ((xtile - 1 > -1) && 
        (this.s.map[(xtile - 1)][ytile] != null) && 
        (!this.s.map[(xtile - 1)][ytile].canfloat))
      {
        Item dropx = this.s.map[(xtile - 1)][ytile].getDrop();
        if (dropx != null) {
          this.s.items.add(new PickableItem(dropx, xtile * 
            this.tileWidth + 8.0F, ytile * this.tileHeight + 8.0F, 
            this.rand.nextFloat() * 5.0F - 2.5F, this.rand
            .nextFloat() * -2.0F, 16.0F, 16.0F));
        }
        this.s.map[(xtile - 1)][ytile].breakTile();
        this.s.map[(xtile - 1)][ytile] = null;
      }
      if ((xtile + 1 < this.s.mapWidth) && 
        (this.s.map[(xtile + 1)][ytile] != null) && 
        (!this.s.map[(xtile + 1)][ytile].canfloat))
      {
        Item dropx = this.s.map[(xtile + 1)][ytile].getDrop();
        if (dropx != null) {
          this.s.items.add(new PickableItem(dropx, xtile * 
            this.tileWidth + 8.0F, ytile * this.tileHeight + 8.0F, 
            this.rand.nextFloat() * 5.0F - 2.5F, this.rand
            .nextFloat() * -2.0F, 16.0F, 16.0F));
        }
        this.s.map[(xtile + 1)][ytile].breakTile();
        this.s.map[(xtile + 1)][ytile] = null;
      }
      this.s.map[xtile][ytile].breakTile();
      this.s.map[xtile][ytile] = null;
      for (int ix = -1; ix < 2; ix++) {
        for (int iy = -1; iy < 2; iy++) {
          if ((xtile + ix > -1) && (xtile + ix < this.s.mapWidth) && 
            (ytile + iy > -1) && (ytile + iy < this.s.mapHeight) && 
            (this.s.map[(xtile + ix)][(ytile + iy)] != null) && 
            (!this.s.map[(xtile + ix)][(ytile + iy)].tileUpdate())) {
            breakTile(xtile + ix, ytile + iy, false);
          }
        }
      }
      if (sound) {
        ((Sound)this.soundBin.get(20)).play();
      }
      reloadLights(xtile);
    }
  }
  
  public void mousePressed(int button, int x, int y)
  {
    if (this.s.map == null) {
      for (MenuButton mi : this.mb) {
        if ((Mouse.getX() > mi.x) && (Mouse.getX() < mi.x + mi.w) && 
          (this.container.getHeight() - Mouse.getY() > mi.y) && 
          (this.container.getHeight() - Mouse.getY() < mi.y + mi.h))
        {
          mi.clickAction.run();
          break;
        }
      }
    } else if (this.esmenu) {
      for (MenuButton mi : this.esm) {
        if ((Mouse.getX() > mi.x) && 
          (Mouse.getX() < mi.x + mi.w) && 
          (this.container.getHeight() - Mouse.getY() > mi.y)) {
          if (this.container.getHeight() - Mouse.getY() < mi.y + mi.h)
          {
            mi.clickAction.run();
            break;
          }
        }
      }
    } else if (this.inventory) {
      this.currentGui.mousePressed(button, x, y);
    } else if (this.s.canreach) {
      if (button == 0) {
        for (LivingEntity le : this.s.entities) {
          if ((x + this.s.camX > le.x) && 
            (x + this.s.camX < le.x + le.w) && 
            (y + this.s.camY > le.y) && 
            (y + this.s.camY < le.y + le.h))
          {
            le.hit(-1);
            if ((this.s.mp.inven.i[this.s.mp.invsel] == null) || 
              (!this.s.mp.inven.i[this.s.mp.invsel].tool) || 
              (this.s.mp.inven.i[this.s.mp.invsel].use())) {
              break;
            }
            this.s.mp.inven.i[this.s.mp.invsel] = null;
            
            break;
          }
        }
      } else if (button == 1) {
        if (this.s.map[this.selectedx][this.selectedy] != null)
        {
          if ((this.s.map[this.selectedx][this.selectedy].RightClick()) && 
            (this.s.mp.inven.i[this.s.mp.invsel] != null) && 
            (!this.s.mp.inven.i[this.s.mp.invsel].use())) {
            this.s.mp.inven.i[this.s.mp.invsel] = null;
          }
        }
        else if (this.drawselected) {
          if ((this.s.mp.inven.i[this.s.mp.invsel] != null) && 
            (this.s.mp.inven.i[this.s.mp.invsel].food))
          {
            this.s.mp.takeHealth(this.s.mp.inven.i[this.s.mp.invsel].healthrep * 
              -1);
            if (this.s.mp.health > 100) {
              this.s.mp.health = 100;
            }
            if (!this.s.mp.inven.i[this.s.mp.invsel].use()) {
              this.s.mp.inven.i[this.s.mp.invsel] = null;
            }
          }
          else if ((this.s.mp.inven.i[this.s.mp.invsel] != null) && 
            (this.s.mp.inven.i[this.s.mp.invsel].placeable))
          {
            List<String> args = new ArrayList();
            args.add(String.valueOf(this.selectedx));
            args.add(String.valueOf(this.selectedy));
            this.s.map[this.selectedx][this.selectedy] = this.s.mp.inven.i[this.s.mp.invsel]
              .getPlace(args);
            this.s.map[this.selectedx][this.selectedy]
              .placeTile();
            ((Sound)this.soundBin.get(19)).play();
            if (!this.s.mp.inven.i[this.s.mp.invsel].use()) {
              this.s.mp.inven.i[this.s.mp.invsel] = null;
            }
            reloadLights(this.selectedx);
            for (int ix = -1; ix < 2; ix++) {
              for (int iy = -1; iy < 2; iy++) {
                if ((this.selectedx + ix > -1) && 
                  (this.selectedx + ix < this.s.mapWidth) && 
                  (this.selectedy + iy > -1) && 
                  (this.selectedy + iy < this.s.mapHeight) && 
                  (this.s.map[
                  (this.selectedx + ix)][(this.selectedy + 
                  iy)] != null)) {
                  if (!this.s.map[(this.selectedx + ix)][(this.selectedy + iy)].tileUpdate()) {
                    breakTile(
                      this.selectedx + 
                      ix, 
                      this.selectedy + 
                      iy, 
                      false);
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  public List<CraftingRecipe> crafts = new ArrayList();
  
  public void reloadCrafting()
  {
    this.s.mp.inven.i[44] = null;
    String[][] current = new String[3][3];
    int iy;
    for (int ix = 0; ix < 3; ix++) {
      for (iy = 0; iy < 3; iy++) {
        current[ix][iy] = "";
      }
    }
    if (this.s.mp.inven.i[40] != null) {
      current[0][0] = this.s.mp.inven.i[40].name;
    }
    if (this.s.mp.inven.i[41] != null) {
      current[1][0] = this.s.mp.inven.i[41].name;
    }
    if (this.s.mp.inven.i[42] != null) {
      current[0][1] = this.s.mp.inven.i[42].name;
    }
    if (this.s.mp.inven.i[43] != null) {
      current[1][1] = this.s.mp.inven.i[43].name;
    }
    for (CraftingRecipe cp : this.crafts)
    {
      boolean brk1 = false;
      for (int ix_ = 0; ix_ < cp.misx.length; ix_++)
      {
        int ax = cp.misx[ix_];
        int ay = cp.misy[ix_];
        String[][] currentcompare = new String[3][3];
        for (int ixx = 0; ixx < 3; ixx++) {
          for (int iyx = 0; iyx < 3; iyx++)
          {
            int tx = ixx + ax;
            int ty = iyx + ay;
            if ((tx > -1) && (tx < 3) && (ty > -1) && (ty < 3)) {
              currentcompare[tx][ty] = cp.crafting[ixx][iyx];
            }
          }
        }
        for (int ixx = 0; ixx < 3; ixx++) {
          for (int iyx = 0; iyx < 3; iyx++) {
            if (currentcompare[ixx][iyx] == null) {
              currentcompare[ixx][iyx] = "";
            }
          }
        }
        if ((current[0][0].equals(currentcompare[0][0])) && 
          (current[1][0].equals(currentcompare[1][0])) && 
          (current[2][0].equals(currentcompare[2][0])) && 
          (current[0][1].equals(currentcompare[0][1])) && 
          (current[1][1].equals(currentcompare[1][1]))) {
          if (current[2][1].equals(currentcompare[2][1])) {
            if (current[0][2].equals(currentcompare[0][2])) {
              if (current[1][2].equals(currentcompare[1][2])) {
                if (current[2][2].equals(currentcompare[2][2]))
                {
                  this.s.mp.inven.i[44] = cp
                    .getItem();
                  brk1 = true;
                  break;
                }
              }
            }
          }
        }
      }
      if (brk1) {
        break;
      }
    }
  }
  
  public void keyPressed(int key, char c)
  {
    if (!this.inCommand)
    {
      if (!this.esmenu)
      {
        if (key == 28) {
          this.inCommand = true;
        }
        if (key == 59) {
          this.renderinven = (!this.renderinven);
        } else if (key == 61) {
          this.dbgrend = (!this.dbgrend);
        }
        if ((c == 'p') || (c == 'P')) {
          try
          {
            int width = 16384;
            int height = 16384;
            float camXl = this.s.camX;
            float camYl = this.s.camY;
            MD.game.s.camX = (this.s.mp.x - width / 2 + this.s.mp.w / 2.0F);
            MD.game.s.camY = (this.s.mp.y - height / 2 + this.s.mp.h / 2.0F);
            if (MD.game.s.camX < 0.0F) {
              MD.game.s.camX = 0.0F;
            }
            if (MD.game.s.camY < 0.0F) {
              MD.game.s.camY = 0.0F;
            }
            if (MD.game.s.camX > MD.game.s.mapWidth * MD.game.tileWidth - width) {
              MD.game.s.camX = 
                (MD.game.s.mapWidth * MD.game.tileWidth - width);
            }
            if (MD.game.s.camY > MD.game.s.mapHeight * MD.game.tileHeight - height) {
              MD.game.s.camY = 
                (MD.game.s.mapHeight * MD.game.tileHeight - height);
            }
            Image im = new Image(width, height);
            Graphics img_g = im.getGraphics();
            img_g.setBackground(new Color(0, 0, 0, 255));
            img_g.clear();
            renderGame(img_g, width, height);
            img_g.flush();
            ImageOut.write(im, "img.png");
            this.s.camX = camXl;
            this.s.camY = camYl;
          }
          catch (SlickException e)
          {
            e.printStackTrace();
          }
        }
        if ((c == 'c') || (c == 'C'))
        {
          this.s.map = null;
          this.s.entities.clear();
          this.s.items.clear();
          this.s.mp = new Player(256.0F, 16000.0F, 24.0F, 24.0F, 100);
        }
        if ((c == 'g') || (c == 'G'))
        {
          if (this.generateWorld != null) {
            this.generateWorld.dispose();
          }
          this.generateWorld = new GenerateWorldJFrame();
          this.generateWorld.setVisible(true);
        }
        if ((c == 'k') || (c == 'K')) {
          this.s.mp.y += 100 * this.tileHeight;
        }
        if ((c == 'e') || (c == 'E'))
        {
          this.currentGui = this.playerGui;
          this.currentGui.resized(this.container);
          this.inventory = (!this.inventory);
        }
        if ((key > 1) && (key < 12)) {
          this.s.mp.invsel = (key + 28);
        }
        if ((c == 't') || (c == 'T')) {
          this.inCommand = true;
        }
        if ((key == 1) && 
          (this.s.map != null)) {
          if (this.inventory) {
            this.inventory = false;
          } else {
            this.esmenu = true;
          }
        }
      }
      else if (key == 1)
      {
        this.esmenu = false;
      }
    }
    else
    {
      if (key == 14)
      {
        if (this.currentCommand.length() > 0) {
          this.currentCommand = this.currentCommand.substring(0, 
            this.currentCommand.length() - 1);
        }
      }
      else if (key == 28)
      {
        submitCommand(this.currentCommand);
        this.currentCommand = "";
        this.inCommand = false;
      }
      else if (key == 1)
      {
        this.currentCommand = "";
        this.inCommand = false;
      }
      else
      {
        this.currentCommand += c;
      }
      this.acommand = "";
      for (int i = 0; i < this.currentCommand.length(); i++)
      {
        String charx = this.currentCommand.substring(i, i + 1);
        if (charx.equals(" ")) {
          break;
        }
        this.acommand += charx;
      }
    }
    try
    {
      if (key == 87) {
        if (this.container.isFullscreen())
        {
          this.container.setDisplayMode(800, 600, false);
        }
        else
        {
          Dimension d = Toolkit.getDefaultToolkit()
            .getScreenSize();
          this.container.setDisplayMode(d.width, d.height, true);
        }
      }
    }
    catch (SlickException e)
    {
      e.printStackTrace();
    }
  }
  
  public void submitCommand(String cc)
  {
    try
    {
      int mx = (int)(this.s.mp.x / this.tileWidth);
      int my = (int)(this.s.mp.y / this.tileWidth);
      boolean playSound = true;
      if (cc.startsWith("/"))
      {
        String cmd = cc.substring(1, cc.length());
        List<String> args = argReturn(cmd, " ");
        String str1;
        switch ((str1 = (String)args.get(0)).hashCode())
        {
        case -2086047308: 
          if (str1.equals("instantmine")) {}
          break;
        case -1724348854: 
          if (str1.equals("sunlight")) {}
          break;
        case -1266085216: 
          if (str1.equals("frozen")) {}
          break;
        case -1164336703: 
          if (str1.equals("tntdirt")) {}
          break;
        case -1163973646: 
          if (str1.equals("tntpole")) {}
          break;
        case -1140093645: 
          if (str1.equals("toolbox")) {
            break;
          }
          break;
        case -991840136: 
          if (str1.equals("airjump")) {}
          break;
        case -874274463: 
          if (str1.equals("spawnzombie")) {}
          break;
        case -795192343: 
          if (str1.equals("walled")) {}
          break;
        case -712238717: 
          if (str1.equals("killall")) {}
          break;
        case -663245701: 
          if (str1.equals("mobspawn")) {}
          break;
        case -586095033: 
          if (str1.equals("physics")) {}
          break;
        case -505639592: 
          if (str1.equals("furnace")) {}
          break;
        case -392151366: 
          if (str1.equals("zombierain")) {}
          break;
        case 3708: 
          if (str1.equals("tp")) {}
          break;
        case 115002: 
          if (str1.equals("tnt")) {}
          break;
        case 3198440: 
          if (str1.equals("heal")) {}
          break;
        case 3446904: 
          if (str1.equals("pork")) {}
          break;
        case 3568542: 
          if (str1.equals("tree")) {}
          break;
        case 36250728: 
          if (str1.equals("worldtnt")) {}
          break;
        case 94094958: 
          if (str1.equals("build")) {}
          break;
        case 99469088: 
          if (str1.equals("house")) {}
          break;
        case 108386675: 
          if (str1.equals("reach")) {}
          break;
        case 109776284: 
          if (str1.equals("stuck")) {}
          break;
        case 110547964: 
          if (str1.equals("torch")) {}
          break;
        case 172045875: 
          if (str1.equals("tntsurface")) {}
          break;
        case 197143583: 
          if (str1.equals("godmode")) {}
          break;
        case 333722389: 
          if (str1.equals("explosion")) {}
          break;
        case 654076977: 
          if (str1.equals("deflight")) {}
          break;
        case 1386695892: 
          if (!str1.equals("treefarm"))
          {
            break label4894;
            String str2;
            switch ((str2 = (String)args.get(1)).hashCode())
            {
            case -782181354: 
              if (str2.equals("wooden")) {}
              break;
            case 3241160: 
              if (str2.equals("iron")) {
                break;
              }
              break;
            case 109770853: 
              if (str2.equals("stone")) {}
            case 1655054676: 
              if ((goto 4894) || (!str2.equals("diamond"))) {
                break label4894;
              }
              this.s.items.add(new PickableItem(new DiamondAxeItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, -1.0F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new DiamondSwordItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, -0.5F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new DiamondShovelItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, 0.5F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new DiamondPickaxeItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, 1.0F, 0.0F, 16.0F, 16.0F));
              break label4894;
              this.s.items.add(new PickableItem(new IronAxeItem(), this.s.mp.x, 
                this.s.mp.y - 128.0F, -1.0F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new IronSwordItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, -0.5F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new IronShovelItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, 0.5F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new IronPickaxeItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, 1.0F, 0.0F, 16.0F, 16.0F));
              break label4894;
              this.s.items.add(new PickableItem(new StoneAxeItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, -1.0F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new StoneSwordItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, -0.5F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new StoneShovelItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, 0.5F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new StonePickaxeItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, 1.0F, 0.0F, 16.0F, 16.0F));
              break label4894;
              this.s.items.add(new PickableItem(new WoodenAxeItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, -1.0F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new WoodenSwordItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, -0.5F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new WoodenShovelItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, 0.5F, 0.0F, 16.0F, 16.0F));
              this.s.items.add(new PickableItem(new WoodenPickaxeItem(), 
                this.s.mp.x, this.s.mp.y - 128.0F, 1.0F, 0.0F, 16.0F, 16.0F));
            }
            break label4894;
            this.s.items.add(new PickableItem(new LadderItem(64), this.s.mp.x, 
              this.s.mp.y - 128.0F, 0.0F, -2.0F, 16.0F, 16.0F));
          }
          else
          {
            this.s.items.add(new PickableItem(new SaplingItem(64), this.s.mp.x, 
              this.s.mp.y - 128.0F, -1.0F, -2.0F, 16.0F, 16.0F));
            this.s.items.add(new PickableItem(new BoneMealItem(64), this.s.mp.x, 
              this.s.mp.y - 128.0F, 1.0F, -2.0F, 16.0F, 16.0F));
            break label4894;
            if (((String)args.get(1)).equals("default")) {
              this.sunlight = (275 + this.s.grassHeight);
            } else {
              this.sunlight = Integer.valueOf((String)args.get(1)).intValue();
            }
            reloadLights(-1);
            break label4894;
            this.s.items.add(new PickableItem(new TorchItem(64), this.s.mp.x, 
              this.s.mp.y - 128.0F, 0.0F, -2.0F, 16.0F, 16.0F));
            break label4894;
            if (((String)args.get(1)).equals("default")) {
              this.deflight = 0;
            } else {
              this.deflight = Integer.valueOf((String)args.get(1)).intValue();
            }
            reloadLights(-1);
            break label4894;
            for (int i = 0; i < this.s.mapWidth - 1; i++) {
              this.s.entities.add(new EntityZombie(i * this.tileWidth, this.rand
                .nextFloat() * 15000.0F, 50));
            }
            playSound = false;
            ((Sound)this.soundBin.get(11)).play();
            break label4894;
            this.s.mp.x = Float.valueOf(Integer.valueOf((String)args.get(1)).intValue() * 
              this.tileWidth).floatValue();
            this.s.mp.y = Float.valueOf(Integer.valueOf((String)args.get(2)).intValue() * 
              this.tileHeight).floatValue();
            break label4894;
            this.frozen = (!this.frozen);
            playSound = false;
            ((Sound)this.soundBin.get(13)).play();
            break label4894;
            this.s.entities.clear();
            break label4894;
            for (int i = 0; i < 10; i++) {
              this.s.entities.add(new EntityPig(this.s.mp.x - this.rand.nextFloat() * 
                8.0F - 4.0F, this.s.mp.y - 100.0F, 25));
            }
            break label4894;
            this.s.items.add(new PickableItem(new CoalItem(64), this.s.mp.x, 
              this.s.mp.y - 128.0F, 0.0F, -2.0F, 16.0F, 16.0F));
            break label4894;
            this.godmode = (!this.godmode);
            playSound = false;
            if (this.godmode)
            {
              ((Sound)this.soundBin.get(15)).play();
            }
            else
            {
              ((Sound)this.soundBin.get(16)).play();
              break label4894;
              int j = 100 - this.s.mp.health;
              this.s.mp.takeHealth(j * -1);
              playSound = false;
              break label4894;
              for (int ix = -4; ix < 5; ix++) {
                for (int iy = -4; iy < 5; iy++) {
                  if ((mx + ix > -1) && (my + iy > -1) && 
                    (mx + ix < this.s.mapWidth) && 
                    (my + iy < this.s.mapHeight) && 
                    (this.s.map[(mx + ix)][(my + iy)] != null))
                  {
                    this.s.map[(mx + ix)][(my + iy)].breakTile();
                    this.s.map[(mx + ix)][(my + iy)] = null;
                  }
                }
              }
              playSound = false;
              ((Sound)this.soundBin.get(14)).play();
              reloadLights(-1);
              break label4894;
              for (int ix = -4; ix < 5; ix++) {
                for (int iy = -4; iy < 5; iy++) {
                  if ((mx + ix > -1) && (my + iy > -1) && 
                    (mx + ix < this.s.mapWidth) && 
                    (my + iy < this.s.mapHeight) && 
                    (this.s.map[(mx + ix)][(my + iy)] != null))
                  {
                    this.s.map[(mx + ix)][(my + iy)].breakTile();
                    this.s.map[(mx + ix)][(my + iy)] = null;
                  }
                }
              }
              for (int ix = -4; ix < 5; ix++)
              {
                this.s.map[(mx + ix)][(my - 4)] = new NormalTile(11, true, 
                  "Wooden Planks");
                this.s.map[(mx + ix)][(my + 4)] = new NormalTile(11, true, 
                  "Wooden Planks");
              }
              for (int iy = -4; iy < 5; iy++)
              {
                this.s.map[(mx - 4)][(my - iy)] = new NormalTile(11, true, 
                  "Wooden Planks");
                this.s.map[(mx + 4)][(my + iy)] = new NormalTile(11, true, 
                  "Wooden Planks");
              }
              this.s.map[(mx + 3)][(my + 3)].placeTile();
              this.s.map[(mx + 2)][(my + 3)] = new ChestTile();
              this.s.map[(mx + 2)][(my + 3)].placeTile();
              this.s.map[(mx + 1)][(my + 3)] = new ChestTile();
              this.s.map[(mx + 1)][(my + 3)].placeTile();
              this.s.map[(mx - 3)][(my + 3)] = new CraftingTableTile(mx - 3, 
                my + 3);
              this.s.map[(mx - 3)][(my + 3)].placeTile();
              this.s.map[(mx - 3)][my] = new TorchTile(mx - 3, my);
              this.s.map[(mx - 3)][my].placeTile();
              this.s.map[(mx - 3)][my].tileUpdate();
              this.s.map[(mx + 3)][my] = new TorchTile(mx + 3, my);
              this.s.map[(mx + 3)][my].placeTile();
              this.s.map[(mx + 3)][my].tileUpdate();
              this.s.map[(mx + 4)][(my + 1)].breakTile();
              this.s.map[(mx + 4)][(my + 1)] = null;
              this.s.map[(mx + 4)][(my + 2)].breakTile();
              this.s.map[(mx + 4)][(my + 2)] = null;
              
              playSound = false;
              ((Sound)this.soundBin.get(17)).play();
              reloadLights(-1);
              break label4894;
              this.mobspawn = (!this.mobspawn);
              break label4894;
              this.instantmine = (!this.instantmine);
              break label4894;
              this.airjump = (!this.airjump);
              break label4894;
              createExplosion(Integer.valueOf((String)args.get(2)).intValue(), 
                Integer.valueOf((String)args.get(3)).intValue(), 
                Integer.valueOf((String)args.get(1)).intValue(), true, true, null);
              playSound = false;
              reloadLights(-1);
              break label4894;
              this.s.items.add(new PickableItem(new TntItem(64), this.s.mp.x, 
                this.s.mp.y - 128.0F, 0.0F, -2.0F, 16.0F, 16.0F));
              break label4894;
              for (int ix = 0; ix < this.s.mapWidth; ix++) {
                for (int iy = 0; iy < this.s.mapHeight; iy++) {
                  if ((this.s.map[ix][iy] != null) && 
                    (this.s.map[ix][iy].getType().equals("Dirt"))) {
                    this.s.map[ix][iy] = new TntTile(ix, iy);
                  }
                }
              }
              break label4894;
              for (int iy = 0; iy < this.s.mapHeight; iy++)
              {
                if (this.s.map[mx][iy] != null)
                {
                  this.s.map[mx][iy].breakTile();
                  this.s.map[mx][iy] = null;
                }
                this.s.map[mx][iy] = new TntTile(mx, iy);
              }
              this.s.mp.x -= this.tileWidth;
              break label4894;
              for (int ix = 0; ix < this.s.mapWidth; ix++) {
                for (int iy = 0; iy < this.s.mapHeight; iy++) {
                  if (this.s.map[ix][iy] != null)
                  {
                    this.s.map[ix][iy].breakTile();
                    this.s.map[ix][iy] = null;
                    this.s.map[ix][iy] = new TntTile(ix, iy);
                  }
                }
              }
              reloadLights(-1);
              break label4894;
              if (((String)args.get(1)).equals("default"))
              {
                this.reach = 12;
              }
              else
              {
                this.reach = Integer.valueOf((String)args.get(1)).intValue();
                break label4894;
                for (int i = 0; i < Integer.valueOf((String)args.get(3)).intValue(); i++)
                {
                  EntityZombie ez = new EntityZombie(Integer.valueOf(
                    (String)args.get(1)).intValue() * this.tileWidth, Integer.valueOf(
                    (String)args.get(2)).intValue() * this.tileHeight, 50);
                  ez.fx = (this.rand.nextFloat() * 10.0F - 5.0F);
                  ez.fy = (this.rand.nextFloat() * 10.0F - 5.0F);
                  this.s.entities.add(ez);
                }
                break label4894;
                this.physics = (!this.physics);
                this.s.mp.fx = 0.0F;
                this.s.mp.fy = 0.0F;
                break label4894;
                this.s.items.add(new PickableItem(new StoneItem(9999, 9999, 43, 
                  "Bricks"), this.s.mp.x, this.s.mp.y - 128.0F, 0.0F, -2.0F, 16.0F, 16.0F));
                this.s.items.add(new PickableItem(new StoneItem(9999, 9999, 44, 
                  "Stone Bricks"), this.s.mp.x, this.s.mp.y - 128.0F, 0.0F, -2.0F, 
                  16.0F, 16.0F));
                break label4894;
                int height = my;
                int ix = mx;
                for (int i = 0; i < 3 + this.rand.nextInt(4); i++)
                {
                  if ((height > -1) && (height < this.s.mapHeight) && (ix > -1) && 
                    (ix < this.s.mapWidth)) {
                    this.s.map[ix][height] = new WoodTile();
                  }
                  height--;
                }
                for (int ixz = -1 + this.rand.nextInt(2) * -1; ixz < 1 + this.rand.nextInt(4) + 2; ixz++) {
                  for (int iyz = -1 + this.rand.nextInt(2) * -1; iyz < 1 + this.rand.nextInt(4); iyz++) {
                    if ((ix + ixz > -1) && (ix + ixz < this.s.mapWidth) && 
                      (height + iyz > -1) && 
                      (height + iyz < this.s.mapHeight) && 
                      (this.s.map[(ix + ixz)][(height + iyz)] == null)) {
                      this.s.map[(ix + ixz)][(height + iyz)] = new LeafTile();
                    }
                  }
                }
                while (this.s.mp.isColliding()) {
                  this.s.mp.x -= 1.0F;
                }
                break label4894;
                for (int ixx = 0; ixx < this.s.mapWidth; ixx++) {
                  for (int iy = 0; iy < this.s.mapHeight; iy++) {
                    if (this.s.map[ixx][iy] != null)
                    {
                      if (this.s.map[ixx][(iy - 1)] != null)
                      {
                        this.s.map[ixx][(iy - 1)].breakTile();
                        this.s.map[ixx][(iy - 1)] = null;
                      }
                      this.s.map[ixx][(iy - 1)] = new TntTile(ixx, iy - 1);
                      break;
                    }
                  }
                }
                while (this.s.mp.isColliding()) {
                  this.s.mp.y -= 1.0F;
                }
              }
            }
          }
          break;
        }
      }
      label4894:
      if (playSound) {
        ((Sound)this.soundBin.get(9)).play();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public int pull(int original, int puller)
  {
    if (original > puller)
    {
      int o = original - puller;
      o /= 2;
      return original - o;
    }
    if (original < puller)
    {
      int o = puller - original;
      o /= 2;
      return original + o;
    }
    return original;
  }
  
  public void reloadLights(int row)
  {
    if (row == -1) {
      for (int ix = 0; ix < this.s.mapWidth; ix++) {
        for (iy = 0; iy < this.s.mapHeight; iy++)
        {
          this.light[ix][iy] = this.deflight;
          this.light_r[ix][iy] = 'ÿ';
          this.light_g[ix][iy] = 'ÿ';
          this.light_b[ix][iy] = 'ÿ';
        }
      }
    } else {
      for (int iy = 0; iy < this.s.mapHeight; iy++)
      {
        this.light[row][iy] = this.deflight;
        this.light_r[row][iy] = 'ÿ';
        this.light_g[row][iy] = 'ÿ';
        this.light_b[row][iy] = 'ÿ';
      }
    }
    Light li;
    int ix;
    for (int iy = this.s.lightoutset.iterator(); iy.hasNext(); ix < li.rad + 1)
    {
      li = (Light)iy.next();
      int tileX = li.x;
      int tileY = li.y;
      ix = li.rad * -1; continue;
      for (int iy = li.rad * -1; iy < li.rad + 1; iy++) {
        if ((tileX + ix > -1) && (tileY + iy > -1) && 
          (tileX + ix < this.s.mapWidth) && 
          (tileY + iy < this.s.map[(tileX + ix)].length))
        {
          int k = ix;
          if (k < 0) {
            k *= -1;
          }
          k *= li.light / 10;
          int l = iy;
          if (l < 0) {
            l *= -1;
          }
          l *= li.light / 10;
          if ((row == -1) || (row == tileX + ix))
          {
            this.light[(tileX + ix)][(tileY + iy)] += li.light - k - l;
            this.light_r[(tileX + ix)][(tileY + iy)] = pull(
              this.light_r[(tileX + ix)][(tileY + iy)], li.r);
            this.light_g[(tileX + ix)][(tileY + iy)] = pull(
              this.light_g[(tileX + ix)][(tileY + iy)], li.g);
            this.light_b[(tileX + ix)][(tileY + iy)] = pull(
              this.light_b[(tileX + ix)][(tileY + iy)], li.b);
          }
        }
      }
      ix++;
    }
    if (row == -1)
    {
      for (int ix = 0; ix < this.s.mapWidth; ix++)
      {
        int ray = this.sunlight;
        for (int iy = 0; iy < this.s.mapHeight; iy++) {
          if ((this.s.map[ix][iy] != null) && (this.s.map[ix][iy].declight))
          {
            ray -= 48;
            if (ray < 1) {
              break;
            }
          }
          else
          {
            this.light[ix][iy] += ray;
            ray--;
            if (ray < 1) {
              break;
            }
          }
        }
      }
    }
    else
    {
      int ray = this.sunlight;
      for (int iy = 0; iy < this.s.mapHeight; iy++) {
        if ((this.s.map[row][iy] != null) && (this.s.map[row][iy].declight))
        {
          ray -= 48;
          if (ray < 1) {
            break;
          }
        }
        else
        {
          this.light[row][iy] += ray;
          ray--;
          if (ray < 1) {
            break;
          }
        }
      }
    }
  }
  
  public void createOre(int ore, Graphics g, int x, int y, int m, int max_i)
  {
    g.setColor(new Color(ore, 0, 0, 255));
    for (int i = 0; i < max_i; i++) {
      g.fillRect(x + this.rand.nextInt(m), y + this.rand.nextInt(m), 
        1 + this.rand.nextInt(max_i), 1 + this.rand.nextInt(max_i));
    }
    g.setColor(new Color(1, 0, 0, 255));
  }
  
  public void seedOre(int ore, Graphics g, int loops, int m, int max_i, int l)
  {
    for (int i = 0; i < loops; i++)
    {
      int ix = this.rand.nextInt(this.s.mapWidth);
      int iy = this.rand.nextInt(this.s.mapHeight - this.s.grassHeight);
      System.out.println(ore + " " + ix + " " + iy + " " + i + " / " + 
        loops);
      if (g.getPixel(ix, iy).getRed() == 1) {
        createOre(ore, g, ix, iy, m, max_i);
      }
    }
  }
  
  public void randomGen()
    throws SlickException
  {
    System.out.println("Initializing Map...");
    this.s.map = new Tile[this.s.mapWidth][this.s.mapHeight];
    this.light = new int[this.s.mapWidth][this.s.mapHeight];
    this.light_r = new int[this.s.mapWidth][this.s.mapHeight];
    this.light_g = new int[this.s.mapWidth][this.s.mapHeight];
    this.light_b = new int[this.s.mapWidth][this.s.mapHeight];
    this.s.lightoutset.clear();
    for (int ix = 0; ix < this.s.mapWidth; ix++) {
      for (int iy = 0; iy < this.s.mapHeight; iy++)
      {
        if (iy > this.s.grassHeight + 5) {
          this.s.map[ix][iy] = new NormalTile(1, true, "Stone");
        } else {
          this.s.map[ix][iy] = new NormalTile(0, true, "Dirt");
        }
        this.light[ix][iy] = 0;
      }
    }
    int gg = this.s.grassHeight + 5;
    Image mapgen = new Image(this.s.mapWidth, this.s.mapHeight - gg);
    


    Graphics g = mapgen.getGraphics();
    g.setBackground(new Color(0, 0, 0, 0));
    g.clear();
    g.setColor(new Color(1, 0, 0, 255));
    System.out.println("Generating Caves...");
    for (int k = 0; k < this.s.mapWidth * this.s.mapHeight / 1024; k++)
    {
      int averagebulk = 6 + this.rand.nextInt(15);
      int sx = this.rand.nextInt(mapgen.getWidth());
      int sy = this.rand.nextInt(mapgen.getHeight());
      g.fillOval(sx, sy, this.rand.nextInt(averagebulk), 
        this.rand.nextInt(averagebulk));
      boolean left = false;
      boolean right = false;
      boolean up = false;
      boolean down = false;
      int rx = this.rand.nextInt(4);
      switch (rx)
      {
      case 0: 
        left = !left;
        break;
      case 1: 
        right = !right;
        break;
      case 2: 
        up = !up;
        break;
      case 3: 
        down = !down;
      }
      for (int l = 0; l < 25; l++)
      {
        if (left) {
          sx--;
        }
        if (right) {
          sx++;
        }
        if (up) {
          sy--;
        }
        if (down) {
          sy++;
        }
        if (sx > this.s.mapWidth) {
          sx = 0;
        }
        if (sy > this.s.mapHeight) {
          sy = 0;
        }
        if (sx < 0) {
          sx = this.s.mapWidth;
        }
        if (sy < 0) {
          sy = this.s.mapHeight;
        }
        g.fillOval(sx, sy, this.rand.nextInt(averagebulk), 
          this.rand.nextInt(averagebulk));
        if (this.rand.nextInt(100) > 90)
        {
          g.setColor(new Color(2, 0, 0, 255));
          g.fillOval(sx, sy, this.rand.nextInt(averagebulk), 
            this.rand.nextInt(averagebulk));
          g.setColor(new Color(1, 0, 0, 255));
        }
        if (this.rand.nextInt(50) > 40)
        {
          int o = this.rand.nextInt(4);
          switch (o)
          {
          case 0: 
            left = !left;
            break;
          case 1: 
            right = !right;
            break;
          case 2: 
            up = !up;
            break;
          case 3: 
            down = !down;
          }
        }
      }
    }
    System.out.println("Seeding Coal");
    seedOre(3, g, 20000, 3, 3, 15);
    System.out.println("Seeding Iron");
    seedOre(5, g, this.s.mapWidth * this.s.mapHeight / 1700, 2, 2, 200);
    System.out.println("Seeding Diamond");
    seedOre(4, g, this.s.mapWidth * this.s.mapHeight / 9000, 1, 1, 512);
    g.flush();
    for (int ix = 0; ix < this.s.mapWidth; ix++) {
      for (int iy = this.s.grassHeight + 5; iy < this.s.mapHeight; iy++)
      {
        int cu = mapgen.getColor(ix, iy - gg).getRed();
        if (cu == 1) {
          this.s.map[ix][iy] = null;
        } else if (cu == 2) {
          this.s.map[ix][iy] = new NormalTile(0, true, "Dirt");
        } else if (cu == 3) {
          this.s.map[ix][iy] = new CoalOre();
        } else if (cu == 4) {
          this.s.map[ix][iy] = new DiamondOre();
        } else if (cu == 5) {
          this.s.map[ix][iy] = new IronTile();
        }
      }
    }
    System.out.println("Putting Bedrock...");
    for (int ix = 0; ix < this.s.mapWidth; ix++) {
      for (int iy = this.s.mapHeight - 5; iy < this.s.mapHeight; iy++) {
        this.s.map[ix][iy] = new NormalTile(2, true, "Bedrock");
      }
    }
    System.out.println("Making Overworld Space...");
    for (int ix = 0; ix < this.s.mapWidth; ix++) {
      for (int iy = 0; iy < this.s.grassHeight; iy++) {
        this.s.map[ix][iy] = null;
      }
    }
    int gheight = this.s.grassHeight;
    System.out.println("Putting Grass...");
    int dir = 0;
    int alt = 0;
    for (int ix = 0; ix < this.s.mapWidth; ix++)
    {
      if (alt < 1)
      {
        if (dir == 1)
        {
          if (gheight < this.s.grassHeight) {
            gheight++;
          }
        }
        else if (dir == 2) {
          gheight--;
        }
        alt = this.rand.nextInt(3);
      }
      else
      {
        alt--;
      }
      if (this.rand.nextInt(500) > 400) {
        dir = this.rand.nextInt(3);
      }
      this.s.map[ix][gheight] = new NormalTile(3, true, "Grass");
      if (gheight < this.s.grassHeight) {
        for (int iy = gheight + 1; iy < this.s.grassHeight; iy++) {
          this.s.map[ix][iy] = new NormalTile(0, true, "Dirt");
        }
      }
    }
    System.out.println("Putting Trees...");
    for (int ix = 0; ix < this.s.mapWidth; ix++) {
      if (this.rand.nextInt(512) > 490)
      {
        int height = 0;
        for (int iy = 0; iy < this.s.mapHeight; iy++) {
          if ((this.s.map[ix][iy] != null) && 
            (this.s.map[ix][iy].img == 3))
          {
            height = iy - 1;
            break;
          }
        }
        for (int i = 0; i < 3 + this.rand.nextInt(4); i++)
        {
          if ((height > -1) && (height < this.s.mapHeight) && (ix > -1) && 
            (ix < this.s.mapWidth)) {
            this.s.map[ix][height] = new WoodTile();
          }
          height--;
        }
        for (int ixz = -1 + this.rand.nextInt(2) * -1; ixz < 1 + this.rand.nextInt(4) + 2; ixz++) {
          for (int iyz = -1 + this.rand.nextInt(2) * -1; iyz < 1 + this.rand.nextInt(4); iyz++) {
            if ((ix + ixz > -1) && (ix + ixz < this.s.mapWidth) && 
              (height + iyz > -1) && 
              (height + iyz < this.s.mapHeight) && 
              (this.s.map[(ix + ixz)][(height + iyz)] == null)) {
              this.s.map[(ix + ixz)][(height + iyz)] = new LeafTile();
            }
          }
        }
      }
    }
    System.out.println("Reloading Lights...");
    reloadLights(-1);
    System.out.println("Updating Tiles...");
    for (int ix = 0; ix < this.s.mapWidth; ix++) {
      for (int iy = 0; iy < this.s.mapHeight; iy++) {
        if (this.s.map[ix][iy] != null) {
          this.s.map[ix][iy].tileUpdate();
        }
      }
    }
  }
  
  public void init(GameContainer gc)
    throws SlickException
  {
    this.logo = new Image("res/logo.png");
    gc.setShowFPS(false);
    this.playerGui = new PlayerGui(gc);
    this.container = ((AppGameContainer)gc);
    this.soundBin.add(new Sound("res/sound/hurt.wav"));
    this.soundBin.add(new Sound("res/sound/hurt2.wav"));
    this.soundBin.add(new Sound("res/sound/hurt3.wav"));
    this.soundBin.add(new Sound("res/sound/hurt4.wav"));
    this.soundBin.add(new Sound("res/sound/hurt5.wav"));
    this.soundBin.add(new Sound("res/sound/hurt6.wav"));
    this.soundBin.add(new Sound("res/sound/hurt7.wav"));
    this.soundBin.add(new Sound("res/sound/hurt8.wav"));
    this.soundBin.add(new Sound("res/sound/heal.wav"));
    this.soundBin.add(new Sound("res/sound/command.wav"));
    this.soundBin.add(new Sound("res/sound/die.wav"));
    this.soundBin.add(new Sound("res/sound/zombierain.wav"));
    this.soundBin.add(new Sound("res/sound/item.wav"));
    this.soundBin.add(new Sound("res/sound/frozen.wav"));
    this.soundBin.add(new Sound("res/sound/wall.wav"));
    this.soundBin.add(new Sound("res/sound/godmode.wav"));
    this.soundBin.add(new Sound("res/sound/godmodeoff.wav"));
    this.soundBin.add(new Sound("res/sound/house.wav"));
    this.soundBin.add(new Sound("res/sound/jump.wav"));
    this.soundBin.add(new Sound("res/sound/place.wav"));
    this.soundBin.add(new Sound("res/sound/blockbreak.wav"));
    this.soundBin.add(new Sound("res/sound/tnt.wav"));
    
    this.textureBin.add(new Image("res/dirt.png"));
    this.textureBin.add(new Image("res/stone.png"));
    this.textureBin.add(new Image("res/bedrock.png"));
    this.textureBin.add(new Image("res/grass.png"));
    this.textureBin.add(new Image("res/coal.png"));
    this.textureBin.add(new Image("res/coalitem.png"));
    this.textureBin.add(new Image("res/torchdown.png"));
    this.textureBin.add(new Image("res/torchleft.png"));
    this.textureBin.add(new Image("res/torchright.png"));
    this.textureBin.add(new Image("res/wood.png"));
    this.textureBin.add(new Image("res/leaves.png"));
    this.textureBin.add(new Image("res/planks.png"));
    this.textureBin.add(new Image("res/stick.png"));
    this.textureBin.add(new Image("res/sapling.png"));
    this.textureBin.add(new Image("res/diamond.png"));
    this.textureBin.add(new Image("res/diamonditem.png"));
    this.textureBin.add(new Image("res/chest.png"));
    this.textureBin.add(new Image("res/craftingtable.png"));
    this.textureBin.add(new Image("res/woodenpickaxe.png"));
    this.textureBin.add(new Image("res/stonepickaxe.png"));
    this.textureBin.add(new Image("res/cobblestone.png"));
    this.textureBin.add(new Image("res/iron.png"));
    this.textureBin.add(new Image("res/furnace.png"));
    this.textureBin.add(new Image("res/ironitem.png"));
    this.textureBin.add(new Image("res/ironpickaxe.png"));
    this.textureBin.add(new Image("res/diamondpickaxe.png"));
    this.textureBin.add(new Image("res/woodenshovel.png"));
    this.textureBin.add(new Image("res/stoneshovel.png"));
    this.textureBin.add(new Image("res/ironshovel.png"));
    this.textureBin.add(new Image("res/diamondshovel.png"));
    this.textureBin.add(new Image("res/woodenaxe.png"));
    this.textureBin.add(new Image("res/stoneaxe.png"));
    this.textureBin.add(new Image("res/ironaxe.png"));
    this.textureBin.add(new Image("res/diamondaxe.png"));
    this.textureBin.add(new Image("res/woodensword.png"));
    this.textureBin.add(new Image("res/stonesword.png"));
    this.textureBin.add(new Image("res/ironsword.png"));
    this.textureBin.add(new Image("res/diamondsword.png"));
    this.textureBin.add(new Image("res/rawpork.png"));
    this.textureBin.add(new Image("res/pork.png"));
    this.textureBin.add(new Image("res/ladder.png"));
    this.textureBin.add(new Image("res/tnt.png"));
    this.textureBin.add(new Image("res/bonemeal.png"));
    this.textureBin.add(new Image("res/bricks.png"));
    this.textureBin.add(new Image("res/stonebricks.png"));
    this.breakingBin.add(new Image("res/breaking/breaking1.png"));
    this.breakingBin.add(new Image("res/breaking/breaking2.png"));
    this.breakingBin.add(new Image("res/breaking/breaking3.png"));
    this.breakingBin.add(new Image("res/breaking/breaking4.png"));
    this.breakingBin.add(new Image("res/breaking/breaking5.png"));
    this.breakingBin.add(new Image("res/breaking/breaking6.png"));
    this.breakingBin.add(new Image("res/breaking/breaking7.png"));
    this.breakingBin.add(new Image("res/breaking/breaking8.png"));
    this.s.entities.add(new EntityPig(192.0F, 15000.0F, 26));
    CraftingRecipes.addCrafts();
  }
  
  public List<Runnable> remtask = null;
  public boolean reactm = true;
  public boolean reactz = true;
  
  public void update(GameContainer gc, int delta)
    throws SlickException
  {
    if ((MD.vsync == -1) || (this.milliLas + MD.vsync < System.currentTimeMillis()))
    {
      if ((this.lw != gc.getWidth()) || (this.lh != gc.getHeight()))
      {
        this.lw = gc.getWidth();
        this.lh = gc.getHeight();
        windowResize(gc);
      }
      List<PickableItem> remi;
      if ((this.s.map != null) && (!this.esmenu))
      {
        float miny;
        float maxy;
        if ((this.mobspawn) && 
          (this.rand.nextInt(1024) > 1010))
        {
          float minx = this.s.mp.x - 1700.0F;
          miny = this.s.mp.y - 1700.0F;
          float maxx = this.s.mp.x + 1700.0F;
          maxy = this.s.mp.y + 1700.0F;
          if (minx < 0.0F) {
            minx = 0.0F;
          }
          if (miny < 0.0F) {
            miny = 0.0F;
          }
          if (maxx > this.s.mapWidth * this.tileWidth) {
            maxx = this.s.mapWidth * this.tileWidth;
          }
          if (maxy > this.s.mapHeight * this.tileHeight) {
            maxy = this.s.mapHeight * this.tileHeight;
          }
          float sx = minx + this.rand.nextFloat() * maxx - minx;
          float sy = miny + this.rand.nextFloat() * maxy - miny;
          EntityZombie ez = new EntityZombie(sx, sy, 50);
          boolean spawnation = false;
          int zx = (int)(ez.x / this.tileWidth);
          int zy = (int)(ez.y / this.tileHeight);
          for (int ix = -5; ix < 6; ix++)
          {
            boolean brk = false;
            for (int iy = -5; iy < 6; iy++) {
              if ((zx + ix > -1) && (zy + iy > -1) && 
                (zx + ix < this.s.mapWidth) && 
                (zy + iy < this.s.mapHeight) && 
                (this.s.map[(zx + ix)][(zy + iy)] != null))
              {
                spawnation = true;
                brk = true;
                break;
              }
            }
            if (brk) {
              break;
            }
          }
          if ((spawnation) && 
            (this.light[((int)(ez.x / this.tileWidth))][((int)(ez.y / this.tileHeight))] < 200) && 
            (!ez.isColliding()))
          {
            System.out.println("zombie spawned at " + sx / 
              this.tileWidth + " " + sy / this.tileHeight);
            this.s.entities.add(ez);
          }
        }
        if ((this.selectedx > -1) && (this.selectedx < this.s.mapWidth) && (this.selectedy > -1) && 
          (this.selectedy < this.s.mapHeight)) {
          if ((this.s.canreach) && (this.s.map[this.selectedx][this.selectedy] != null) && 
            (Mouse.isButtonDown(0)))
          {
            if (this.instantmine) {
              this.blockbreak -= 8192;
            } else {
              this.blockbreak = (this.blockbreak - this.s.map[this.selectedx][this.selectedy].getHardness());
            }
            if (this.blockbreak < 1)
            {
              this.blockbreak = 8192;
              if (this.s.canreach)
              {
                breakTile(this.selectedx, this.selectedy, true);
                if ((this.s.mp.inven.i[this.s.mp.invsel] != null) && 
                  (this.s.mp.inven.i[this.s.mp.invsel].tool) && 
                  (!this.s.mp.inven.i[this.s.mp.invsel].use())) {
                  this.s.mp.inven.i[this.s.mp.invsel] = null;
                }
              }
            }
            if (this.reactm)
            {
              this.blockbreak = 8192;
              this.reactm = false;
            }
          }
          else
          {
            this.reactm = true;
          }
        }
        this.remtask = new ArrayList();
        for (Runnable r : this.tasks) {
          r.run();
        }
        for (Runnable r : this.remtask) {
          this.tasks.remove(r);
        }
        this.healthforce *= 0.99F;
        float hb = this.healthbright;
        this.healthbright += this.healthforce;
        this.healthforce -= 0.4F;
        if (this.healthbright > 255.0F)
        {
          this.healthbright = hb;
          this.healthforce *= -0.5F;
        }
        if (this.healthbright < 0.0F)
        {
          this.healthbright = hb;
          this.healthforce *= -0.5F;
        }
        this.s.mp.update(gc);
        if (!this.frozen)
        {
          List<LivingEntity> rementit = new ArrayList();
          for (int i = 0; i < this.s.entities.size(); i++) {
            if (!((LivingEntity)this.s.entities.get(i)).update()) {
              rementit.add((LivingEntity)this.s.entities.get(i));
            }
          }
          for (LivingEntity le : rementit) {
            this.s.entities.remove(le);
          }
        }
        this.milliLas = System.currentTimeMillis();
        remi = new ArrayList();
        for (PickableItem pi : this.s.items)
        {
          if ((!pi.decenting) && 
            (GameTools.GetCollision(this.s.mp.x, this.s.mp.y, this.s.mp.w, 
            this.s.mp.h, pi.x, pi.y, pi.w, pi.h)))
          {
            int stk = pi.pick.stack;
            for (int ii = 0; ii < this.s.mp.inven.i.length - 5; ii++)
            {
              int iii = this.s.mp.inven.i.length - ii - 1 - 5;
              if ((this.s.mp.inven.i[iii] != null) && 
                (this.s.mp.inven.i[iii].name == pi.pick.name) && 
                (this.s.mp.inven.i[iii].stack < this.s.mp.inven.i[iii].maxstack))
              {
                for (int i = 0; i < stk; i++)
                {
                  if (this.s.mp.inven.i[iii].stack >= this.s.mp.inven.i[iii].maxstack) {
                    break;
                  }
                  this.s.mp.inven.i[iii].stack += 1;
                  stk--;
                }
                if (stk < 1) {
                  break;
                }
              }
            }
            pi.pick.stack = stk;
            if (stk > 0) {
              for (int ii = 0; ii < this.s.mp.inven.i.length - 5; ii++)
              {
                int iii = this.s.mp.inven.i.length - 5 - ii - 1;
                if (this.s.mp.inven.i[iii] == null)
                {
                  this.s.mp.inven.i[iii] = pi.pick;
                  stk = 0;
                  break;
                }
              }
            }
            if (stk < 1)
            {
              pi.decenting = true;
              ((Sound)MD.game.soundBin.get(12)).play();
            }
          }
          if ((!this.frozen) && 
            (!pi.update())) {
            remi.add(pi);
          }
        }
        for (PickableItem pi : remi) {
          this.s.items.remove(pi);
        }
      }
      else if (this.esmenu)
      {
        for (MenuButton mi : this.esm) {
          if ((Mouse.getX() > mi.x) && (Mouse.getX() < mi.x + mi.w) && 
            (gc.getHeight() - Mouse.getY() > mi.y) && 
            (gc.getHeight() - Mouse.getY() < mi.y + mi.h))
          {
            if (mi.alpha < 201) {
              mi.alpha += 5;
            }
            if (mi.alpha > 200) {
              mi.alpha = 200;
            }
          }
          else
          {
            if (mi.alpha > 0) {
              mi.alpha -= 5;
            }
            if (mi.alpha < 0) {
              mi.alpha = 0;
            }
          }
        }
      }
      else
      {
        for (MenuButton mi : this.mb) {
          if ((Mouse.getX() > mi.x) && (Mouse.getX() < mi.x + mi.w) && 
            (gc.getHeight() - Mouse.getY() > mi.y) && 
            (gc.getHeight() - Mouse.getY() < mi.y + mi.h))
          {
            if (mi.alpha < 201) {
              mi.alpha += 5;
            }
            if (mi.alpha > 200) {
              mi.alpha = 200;
            }
          }
          else
          {
            if (mi.alpha > 0) {
              mi.alpha -= 5;
            }
            if (mi.alpha < 0) {
              mi.alpha = 0;
            }
          }
        }
      }
    }
  }
  
  public static void main(String[] args)
    throws SlickException
  {
    System.out.println("[About] This game is made by Spideynn, and has put a total of 30+ hours into this!");
    System.out
      .println("[About] Please do *not* copy and say it is your game. He has put lots of work into this, and hopes you enjoy this game.");
    if (args.length > 0) {
      MD.vsync = Integer.valueOf(args[0]).intValue();
    }
    AppGameContainer appgc = new AppGameContainer(MD.game);
    appgc.setAlwaysRender(true);
    appgc.setDisplayMode(1024, 600, false);
    appgc.setIcon("res/diamondpickaxe.png");
    appgc.start();
  }
}
