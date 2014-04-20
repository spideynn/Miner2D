package net.spideynn.miner2d.main;

import net.spideynn.miner2d.blocks.ChestTile;
import net.spideynn.miner2d.blocks.CoalOre;
import net.spideynn.miner2d.blocks.DiamondOre;
import net.spideynn.miner2d.blocks.EmeraldOre;
import net.spideynn.miner2d.blocks.FurnaceItem;
import net.spideynn.miner2d.blocks.FurnaceTile;
import net.spideynn.miner2d.blocks.IronOre;
import net.spideynn.miner2d.blocks.LeafTile;
import net.spideynn.miner2d.blocks.NormalTile;
import net.spideynn.miner2d.blocks.RubyOre;
import net.spideynn.miner2d.blocks.SaplingItem;
import net.spideynn.miner2d.blocks.StoneItem;
import net.spideynn.miner2d.blocks.Tile;
import net.spideynn.miner2d.blocks.TntItem;
import net.spideynn.miner2d.blocks.TntTile;
import net.spideynn.miner2d.blocks.TorchItem;
import net.spideynn.miner2d.blocks.TorchTile;
import net.spideynn.miner2d.blocks.WoodTile;
import net.spideynn.miner2d.entities.EntityPig;
import net.spideynn.miner2d.entities.EntityZombie;
import net.spideynn.miner2d.entities.LivingEntity;
import net.spideynn.miner2d.gui.GenerateWorldJFrame;
import net.spideynn.miner2d.gui.Gui;
import net.spideynn.miner2d.gui.PlayerGui;
import net.spideynn.miner2d.items.BoneMealItem;
import net.spideynn.miner2d.items.CoalItem;
import net.spideynn.miner2d.items.DiamondAxeItem;
import net.spideynn.miner2d.items.DiamondPickaxeItem;
import net.spideynn.miner2d.items.DiamondShovelItem;
import net.spideynn.miner2d.items.DiamondSwordItem;
import net.spideynn.miner2d.items.EmeraldItem;
import net.spideynn.miner2d.items.IronAxeItem;
import net.spideynn.miner2d.items.IronPickaxeItem;
import net.spideynn.miner2d.items.IronShovelItem;
import net.spideynn.miner2d.items.IronSwordItem;
import net.spideynn.miner2d.items.Item;
import net.spideynn.miner2d.items.LadderItem;
import net.spideynn.miner2d.items.PickableItem;
import net.spideynn.miner2d.items.RubyItem;
import net.spideynn.miner2d.items.StoneAxeItem;
import net.spideynn.miner2d.items.StonePickaxeItem;
import net.spideynn.miner2d.items.StoneShovelItem;
import net.spideynn.miner2d.items.StoneSwordItem;
import net.spideynn.miner2d.items.WoodenAxeItem;
import net.spideynn.miner2d.items.WoodenPickaxeItem;
import net.spideynn.miner2d.items.WoodenShovelItem;
import net.spideynn.miner2d.items.WoodenSwordItem;
import net.spideynn.miner2d.other.CraftingRecipe;
import net.spideynn.miner2d.other.CraftingRecipes;
import net.spideynn.miner2d.other.CraftingTableTile;
import net.spideynn.miner2d.other.GameSave;
import net.spideynn.miner2d.other.GameTools;
import net.spideynn.miner2d.other.InvenSlot;
import net.spideynn.miner2d.other.Light;
import net.spideynn.miner2d.other.Player;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.util.*;

public class MainGame extends BasicGame {

	public AppGameContainer container;

	public boolean physics = true;
	public boolean instantmine = false;
	public boolean mobspawn = true;

	public boolean dbgrend = false;

	public boolean esmenu = false;

	public GameSave s = new GameSave();

	public Image logo;

	public int reach = 12;

	public List<MenuButton> esm = new ArrayList<MenuButton>();

	public List<Sound> soundBin = new ArrayList<Sound>();

	public int deflight = 0;
	public int sunlight = 275 + s.grassHeight;

	public List<String> argReturn(String data, String sep) {
		List<String> args = new ArrayList<String>();
		int pos = 0;
		args.add("");
		for (int i = 0; i < data.length(); i++) {
			String charz = data.substring(i, i + 1);
			if (charz.contains(sep)) {
				args.add("");
				pos++;
			} else {
				args.set(pos, args.get(pos) + charz);
			}
		}
		return args;
	}

	public boolean frozen = false;
	

	public boolean inCommand;
	public String currentCommand = "";

	public int lx, ly;

	public boolean airjump = false;

	public String acommand = "";

	public boolean renderinven = true;

	public String[] commands = { "/toolbox", "/stuck", "/treefarm",
			"/sunlight", "/torch", "/deflight", "/zombierain", "/tp",
			"/killall", "/pork", "/furnace", "/godmode", "/heal", "/walled",
			"/house", "/mobspawn", "/instantmine", "/airjump", "/frozen",
			"/explosion", "/tnt", "/tntdirt", "/tntpole", "/worldtnt",
			"/reach", "/spawnzombie", "/physics", "/build", "/tree",
			"/tntsurface", "/emeraldruby" };

	/*
	 * public String[] commandargs = {
	 * " - drops Diamond[(Pickaxe, Shovel, Axe, Sword)", " - Spawnes Ladders",
	 * "Gives 64 saplings and 64 bonemeal",
	 * " <(number)intensity/default> - regulates the sunlight amount (sunshine) [default: about 700]"
	 * , " - spawnes 64 torches",
	 * " <(number)intensity/default> - default light say 0: pitch black 255: all clear"
	 * , " - rains zombies from the sky", " <x> <y> - teleports player",
	 * " - kills all the mobs in the game (eg. zombie, pig, PrimedTNT)",
	 * " - spawns 10 pigs above the player",
	 * " - spawns 64 furnaces and 64 coal",
	 * " - player cant take damage (toggle)", " - heals player to 100 health",
	 * " - when you're stuck in a wall.. this destroys stuff in a 5 radius",
	 * " - makes a house", " - toggles mob spawning",
	 * " - no mining delay (toggle)",
	 * " - makes it so the player can jump in the air",
	 * " - freezes the items and the mobs", " <radius> <x> <y> - TNT explosion",
	 * " - gives 64 tnt", " - turns all dirt in the world to tnt",
	 * " - makes a pole of tnt [up and down directions]",
	 * " - changes the whole world to tnt",
	 * " <(number)distance/default> - changes the player reach distance",
	 * " <x> <y> <amount> - spawns zombies in the world",
	 * " - toggle player physics",
	 * " - gives player 64 bricks, stone bricks, and wooden planks",
	 * " - spawns a tree where the player is"};
	 */
	public String[] commandargs = { " <diamond/iron/stone/wooden>", "", "",
			" <(number)intensity/default>", " - spawnes 64 torches",
			" <(number)intensity/default>", "", " <x> <y>", "", "", "", "", "",
			"", "", "", "", "", "", " <radius> <x> <y>", "", "", "", "",
			" <(number)distance/default>", " <x> <y> <amount>", "", "", "", "", "" };

	public float healthforce = 0f;
	public float healthbright = 0f;

	public List<Image> textureBin = new ArrayList<Image>();
	public List<Image> breakingBin = new ArrayList<Image>();

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

	public List<Runnable> tasks = new ArrayList<Runnable>();

	public boolean genWorldNow = false;

	public long milliLas = 0;

	public void initPlayer() {
		System.out.println("Initializing Player...");
		Player p = new Player(MD.game.s.mapWidth * MD.game.tileWidth / 2, 0f,
				24, 24, 100);
		p.lasty = 0f;
		while (!p.isColliding())
			p.y++;
		p.y--;
		p.lasty = p.y;
		MD.game.s.mp = p;
	}

	public MainGame(String title) {
		super(title);
	}

	public boolean drawselected = true;
	public int selectedx = 0;
	public int selectedy = 0;

	public int lw = 0;
	public int lh = 0;

	public void renderTile(Graphics g, int ix, int iy, int tx, int ty) {
		if (s.map[ix][iy] != null) {
			if (s.map[ix][iy].hasalpha) {
				if (iy < s.grassHeight + 5) {
					g.setColor(new Color(0, 192, 255, (int) (255)));
					g.fillRect(tx * tileWidth - s.camX, ty * tileHeight
							- s.camY, tileWidth, tileHeight);
				} else {
					g.setColor(new Color(light_r[ix][iy] / 2,
							light_g[ix][iy] / 5, light_b[ix][iy] / 100,
							(int) (light[ix][iy] / 4)));
					g.fillRect(tx * tileWidth - s.camX, ty * tileHeight
							- s.camY, tileWidth, tileHeight);
				}

			}
			s.map[ix][iy].render(g, ix, iy, tx, ty);
			if (ix == selectedx && iy == selectedy && s.canreach
					&& Mouse.isButtonDown(0)) {
				g.drawImage(breakingBin.get(7 - (int) (blockbreak / 1170)), ix
						* tileWidth - s.camX, iy * tileHeight - s.camY);
			}
		} else {
			if (iy < s.grassHeight + 5) {
				g.setColor(new Color(0, 192, 255, (int) (255)));
				g.fillRect(tx * tileWidth - s.camX, ty * tileHeight - s.camY,
						tileWidth, tileHeight);
			} else {
				g.setColor(new Color(light_r[ix][iy] / 2, light_g[ix][iy] / 5,
						light_b[ix][iy] / 100, (int) (light[ix][iy] / 4)));
				g.fillRect(tx * tileWidth - s.camX, ty * tileHeight - s.camY,
						tileWidth, tileHeight);
			}
		}
	}

	public void renderGame(Graphics g, int WidthX, int HeightX) {
		g.resetTransform();
		g.setLineWidth(1f);
		if (s.map != null) {
			g.setBackground(new Color(0, 0, 0, 255));
			for (int ix = (int) (s.camX / tileWidth); ix < s.camX / tileWidth
					+ WidthX / tileWidth + 1; ix++) {
				for (int iy = (int) (s.camY / tileHeight); iy < s.camY
						/ tileHeight + HeightX / tileHeight + 2; iy++) {
					if (ix > -1 && iy > -1 && ix < s.mapWidth
							&& iy < s.mapHeight) {
						renderTile(g, ix, iy, ix, iy);
					}
				}
			}
			int xa = (int) s.mp.x / tileWidth;
			int ya = (int) s.mp.y / tileHeight;
			if (renderinven) {
				g.setColor(new Color(light[xa][ya], 0, 0, 255));
				g.fillRect(s.mp.x - s.camX, s.mp.y - s.camY, s.mp.w, s.mp.h);
				if (drawselected && s.canreach) {
					g.setColor(Color.black);
					g.drawRect(selectedx * tileWidth - s.camX, selectedy
							* tileHeight - s.camY, tileWidth, tileHeight);
				}
			}
			for (LivingEntity le : s.entities)
				le.render(g);
			for (PickableItem pi : s.items) {
				pi.render(
						g,
						light[(int) (pi.x / tileWidth)][(int) (pi.y / tileHeight)]);
			}
			if (renderinven) {
				g.setColor(new Color(0, 96, 255, 128));
				g.fillRoundRect(WidthX / 2 - 470f / 2, HeightX - 50f, 470f,
						50f, 10);
				g.setColor(Color.black);
				g.setLineWidth(1f);
				g.drawRoundRect(WidthX / 2 - 470f / 2, HeightX - 50f, 470f,
						50f, 10);
				float ix_ = WidthX / 2 - 470f / 2 + 10f;
				g.setColor(new Color(0, 96, 255, 240));
				int slot = 30;
				for (int ix = 0; ix < 10; ix++) {
					if (s.mp.invsel == slot) {
						g.setColor(new Color(0, 255, 96, 240));
						g.setLineWidth(3f);
					} else {
						g.setColor(new Color(0, 96, 255, 240));
						g.setLineWidth(1f);
					}
					g.drawRect(ix_, HeightX - 45f, 40f, 40f);
					if (s.mp.inven.i[slot] != null) {
						g.drawImage(
								MD.game.textureBin.get(s.mp.inven.i[slot].img),
								ix_ + 4, HeightX - 45f + 4);
						if (s.mp.inven.i[slot].stack > 1) {
							String d = String.valueOf(s.mp.inven.i[slot].stack);
							g.setColor(new Color(0, 0, 0, 192));
							g.fillRect(
									ix_ + 40 - g.getFont().getWidth(d) - 2,
									HeightX - 45f + 40
											- g.getFont().getHeight(d) - 2, g
											.getFont().getWidth(d), g.getFont()
											.getHeight(d));
							g.setColor(new Color(0, 128, 255, 255));
							g.drawString(d, ix_ + 40 - g.getFont().getWidth(d)
									- 2, HeightX - 45f + 40
									- g.getFont().getHeight(d) - 2);
						}
						if (s.mp.inven.i[slot].tool) {
							g.setColor(new Color(0, 0, 0, 255));
							g.fillRect(ix_, HeightX - 12f, 40f, 4f);
							g.setColor(new Color(0, 255, 0, 255));
							g.fillRect(ix_, HeightX - 12f,
									(int) (s.mp.inven.i[slot].usage / 204f), 4f);
						}
					}
					ix_ += 45;
					slot++;
				}
				g.setColor(new Color((int) healthbright, (int) healthbright,
						(int) healthbright, 255));
				g.fillRect(WidthX / 2 - 470f / 2, HeightX - 75f,
						s.mp.maxhealth * 2, 20);
				g.setColor(new Color(0, 255, 0, 255));
				g.fillRect(WidthX / 2 - 470f / 2 + 2, HeightX - 75f + 2,
						s.mp.health * 2 - 4, 20 - 4);
			}

			if (inventory) {
				currentGui.render(g, container);
			}
		} else {
			g.setBackground(new Color(255, 255, 255, 255));
			/*
			 * g.setColor(new Color(0, 96, 255, 192)); g.fillRoundRect(WidthX /
			 * 2 - 320f / 2, HeightX / 2 - 240f / 2, 320f, 240f, 10);
			 * g.setColor(new Color(0, 0, 0, 255)); g.drawRoundRect(WidthX / 2 -
			 * 320f / 2, HeightX / 2 - 240f / 2, 320f, 240f, 10);
			 * g.drawLine(WidthX / 2 - 320f / 2, HeightX / 2 - 240f / 2 + 30f,
			 * WidthX / 2 - 320f / 2 + 320f, HeightX / 2 - 240f / 2 + 30f);
			 * g.setColor(new Color(255, 255, 255, 255));
			 * g.drawString("World Not Loaded!", WidthX / 2 - 320f / 2 + 10f,
			 * HeightX / 2 - 240f / 2 + 5f);
			 * g.drawString("G - Generate New World", WidthX / 2 - 320f / 2 +
			 * 10f, HeightX / 2 - 240f / 2 + 50f);
			 * g.drawString("S - Save World", WidthX / 2 - 320f / 2 + 10f,
			 * HeightX / 2 - 240f / 2 + 80f); g.drawString("Q - Load World",
			 * WidthX / 2 - 320f / 2 + 10f, HeightX / 2 - 240f / 2 + 110f);
			 */
			for (MenuButton mbz : mb) {
				g.setColor(new Color(0, 96, 255, 128));
				g.fillRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
				g.setColor(new Color(0, 0, 0, 255));
				g.drawRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
				g.setColor(new Color(255, 255, 255, 255));
				g.drawString(mbz.text, mbz.x + mbz.w / 2
						- g.getFont().getWidth(mbz.text) / 2, mbz.y + mbz.h / 2
						- g.getFont().getHeight(mbz.text) / 2);
				if (mbz.alpha > 0) {
					g.setColor(new Color(255, 255, 255, mbz.alpha));
					g.fillRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
				}
			}
			g.drawImage(logo, WidthX / 2 - 800f / 2, 10f);
		}
		if (inCommand) {
			g.setColor(new Color(127, 127, 127, 200));
			g.fillRect(0, HeightX - 23, WidthX, 23);
			g.setColor(new Color(255, 255, 255, 255));
			g.drawString(currentCommand + "_", 0, HeightX - 23);
			if (acommand.startsWith("/")) {
				int y = HeightX - g.getFont().getHeight(currentCommand + "_")
						- 10;
				int ind = 0;
				for (String s : commands) {
					if (s.startsWith(acommand)) {
						y -= g.getFont().getHeight(s) - 1;
						g.setColor(new Color(0, 255, 0, 255));
						int x = 0;
						String sub = s.substring(0, acommand.length());
						g.drawString(sub, 0, y);
						x += g.getFont().getWidth(sub);
						g.setColor(new Color(255, 255, 255, 255));
						g.drawString(
								s.substring(acommand.length(), s.length()),
								x - 2, y);
						x += g.getFont().getWidth(
								s.substring(acommand.length(), s.length()));
						g.setColor(new Color(255, 255, 0, 255));
						g.drawString(commandargs[ind], x, y);
					}
					ind++;
				}
			}
		}
		if (esmenu) {
			g.setColor(new Color(127, 127, 127, 200));
			g.fillRect(0, 0, WidthX, HeightX);
			for (MenuButton mbz : esm) {
				g.setColor(new Color(0, 96, 255, 128));
				g.fillRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
				g.setColor(new Color(0, 0, 0, 255));
				g.drawRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
				g.setColor(new Color(255, 255, 255, 255));
				g.drawString(mbz.text, mbz.x + mbz.w / 2
						- g.getFont().getWidth(mbz.text) / 2, mbz.y + mbz.h / 2
						- g.getFont().getHeight(mbz.text) / 2);
				if (mbz.alpha > 0) {
					g.setColor(new Color(255, 255, 255, mbz.alpha));
					g.fillRoundRect(mbz.x, mbz.y, mbz.w, mbz.h, 10);
				}
			}
		}
		if (dbgrend) {
			g.setColor(Color.orange);
			g.drawString("FPS: " + container.getFPS() + "\noffsX: "
					+ s.camOffsX + "\noffsY: " + s.camOffsY + "\nX: "
					+ (int) (s.mp.x / tileWidth) + "\nY: "
					+ (int) (s.mp.y / tileHeight), 50, 50);
		}
	}

	public void createExplosion(int x, int y, int radius, boolean inflate,
			boolean soundeffects, Tile xploder) {
		float lx = x * tileWidth;
		float ly = y * tileHeight;
		for (LivingEntity le : s.entities) {
			float dx = le.x - lx;
			float dy = le.y - ly;
			float ox = dx;
			float oy = dy;
			if (dx < 0f)
				dx *= -1f;
			if (dy < 0f)
				dy *= -1f;
			if (dx + dy < 320f) {
				// s.mp.fx += ox;
				// s.mp.fy += oy;
				float px = 320f;
				if (ox < 0f)
					px = -320f;
				float py = 320f;
				if (oy < 0f)
					py = -320f;
				float fxx = px - ox;
				float fyy = py - oy;
				fxx /= 300f;
				fyy /= 300f;

				le.fx += fxx;
				le.fy += fyy;
				le.hit((int) (320f - dx + dy) / 12);
			}
		}
		float dx = s.mp.x - lx;
		float dy = s.mp.y - ly;
		float ox = dx;
		float oy = dy;
		if (dx < 0f)
			dx *= -1f;
		if (dy < 0f)
			dy *= -1f;
		if (dx + dy < 320f) {
			// s.mp.fx += ox;
			// s.mp.fy += oy;
			float px = 320f;
			if (ox < 0f)
				px = -320f;
			float py = 320f;
			if (oy < 0f)
				py = -320f;
			float fxx = px - ox;
			float fyy = py - oy;
			fxx /= 300f;
			fyy /= 300f;

			s.mp.fx += fxx;
			s.mp.fy += fyy;
			s.mp.takeHealth((int) (320f - dx + dy) / 12);
		}
		for (int ix = radius * -1; ix < radius + 1; ix++) {
			for (int iy = radius * -1; iy < radius + 1; iy++) {
				if (x + ix > -1 && y + iy > -1 && x + ix < s.mapWidth
						&& y + iy < s.mapHeight) {
					if (s.map[x + ix][y + iy] != null) {
						boolean brk = true;
						if (s.map[x + ix][y + iy] != xploder && inflate) {
							if (s.map[x + ix][y + iy].isInflatable) {
								TntTile d = (TntTile) s.map[x + ix][y + iy];
								d.explodeX = true;
								s.map[x + ix][y + iy].RightClick();
								brk = false;
							}
						}
						if (brk) {
							if (s.map[x + ix][y + iy] != xploder) {
								if (rand.nextInt(800) > 600) {
									breakTile(x + ix, y + iy, false);
								} else {
									s.map[x + ix][y + iy].breakTile();
									s.map[x + ix][y + iy] = null;
								}
							} else {
								s.map[x + ix][y + iy].breakTile();
								s.map[x + ix][y + iy] = null;
							}
						}
					}
				}
			}
		}
		if (soundeffects) {
			soundBin.get(21).play();
		}
		reloadLights(-1);
	}

	public void SaveWorld() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = null;
			out = new ObjectOutputStream(bos);
			for (int ix = 0; ix < s.mapWidth; ix++) {
				for (int iy = 0; iy < s.mapHeight; iy++) {
					if (s.map[ix][iy] != null) {
						s.map[ix][iy].Serialized();
					}
				}
			}
			out.writeObject(s);
			for (int ix = 0; ix < s.mapWidth; ix++) {
				for (int iy = 0; iy < s.mapHeight; iy++) {
					if (s.map[ix][iy] != null) {
						s.map[ix][iy].Deserialized();
					}
				}
			}
			byte[] yourBytes = bos.toByteArray();
			JFileChooser fc = new JFileChooser();
			if (fc.showSaveDialog(null) == 0) {
				String filename = fc.getSelectedFile().getPath();
				BufferedOutputStream bosx = null;
				FileOutputStream fos = new FileOutputStream(new File(filename));
				bosx = new BufferedOutputStream(fos);
				bosx.write(yourBytes);
				out.close();
				bosx.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadWorld() {
		try {
			JFileChooser fc = new JFileChooser();
			if (fc.showOpenDialog(null) == 0) {
				File file = fc.getSelectedFile();
				byte[] fileData = new byte[(int) file.length()];
				DataInputStream dis;
				dis = new DataInputStream(new FileInputStream(file));
				dis.readFully(fileData);
				dis.close();

				ByteArrayInputStream bis = new ByteArrayInputStream(fileData);
				ObjectInput in = null;
				in = new ObjectInputStream(bis);
				Object o = in.readObject();
				GameSave sdd = (GameSave) o;
				s = sdd;

				tasks.clear();
				for (int ix = 0; ix < s.mapWidth; ix++) {
					for (int iy = 0; iy < s.mapHeight; iy++) {
						if (s.map[ix][iy] != null) {
							s.map[ix][iy].Deserialized();
						}
					}
				}
				for (InvenSlot is : playerGui.guis_s) {
					is.hook = s.mp.inven;
				}
				if (light == null)
					light = new int[s.mapWidth][s.mapHeight];
				if (light_r == null)
					light_r = new int[s.mapWidth][s.mapHeight];
				if (light_g == null)
					light_g = new int[s.mapWidth][s.mapHeight];
				if (light_b == null)
					light_b = new int[s.mapWidth][s.mapHeight];

				reloadLights(-1);
				bis.close();
				in.close();

			}
		} catch (IOException | ClassNotFoundException e2) {
			e2.printStackTrace();
		}
	}

	public void adder(List<MenuButton> me) {
		me.clear();
		me.add(new MenuButton("Generate New World",
				container.getWidth() / 2 - 256 / 2, container.getHeight() / 2
						- 64 / 2 - 96 + 100, 256, 64, new Runnable() {
					@Override
					public void run() {
						/*
						  if (generateWorld != null) { generateWorld.dispose();
						  } generateWorld = new GenerateWorldJFrame();
						  generateWorld.setVisible(true);*/
						 
						try {
							s.mapWidth = 512;
							s.mapHeight = 1579;
							randomGen();
							initPlayer();
						} catch (SlickException e) {
							e.printStackTrace();
						}

					}
				}));
		me.add(new MenuButton("Load World",
				container.getWidth() / 2 - 256 / 2, container.getHeight() / 2
						- 64 / 2 + 96 + 100, 256, 64, new Runnable() {
					@Override
					public void run() {
						LoadWorld();
					}
				}));
	}

	public void windowResize(GameContainer gc) {
		adder(mb);
		esm.clear();
		esm.add(new MenuButton("Generate New World",
				container.getWidth() / 2 - 256 / 2, container.getHeight() / 2
						- 64 / 2 - 192 + 100, 256, 64, new Runnable() {
					@Override
					public void run() {
						
						 if (generateWorld != null) { generateWorld.dispose();
						 } generateWorld = new GenerateWorldJFrame();
						 generateWorld.setVisible(true);
						 
						//try {
							//s.mapWidth = 512;
							//s.mapHeight = 4096;
							//randomGen();
							//initPlayer();
						//} catch (SlickException e) {
						//	e.printStackTrace();
						}
					}
				));
		esm.add(new MenuButton("Save World",
				container.getWidth() / 2 - 256 / 2, container.getHeight() / 2
						- 64 / 2 - 96 + 100, 256, 64, new Runnable() {
					@Override
					public void run() {
						SaveWorld();
					}
				}));
		esm.add(new MenuButton("Load World!",
				container.getWidth() / 2 - 256 / 2, container.getHeight() / 2
						- 64 / 2 + 100, 256, 64, new Runnable() {
					@Override
					public void run() {
						LoadWorld();
					}
				}));
		esm.add(new MenuButton("Exit World (Without saving)", container
				.getWidth() / 2 - 256 / 2, container.getHeight() / 2 - 64 / 2
				+ 192, 256, 64, new Runnable() {
			@Override
			public void run() {
				s.map = null;
				s.entities.clear();
				s.items.clear();
				s.mp = new Player(256f, 16000f, 24, 24, 100);
				esmenu = false;
			}
		}));
		esm.add(new MenuButton("Back To Game",
				container.getWidth() / 2 - 256 / 2, container.getHeight() / 2
						- 64 / 2 - 192, 256, 64, new Runnable() {
					@Override
					public void run() {
						esmenu = false;
					}
				}));

		System.out.println("Window Resized!");
		if (currentGui != null) {
			currentGui.resized(gc);
		}
	}

	public List<MenuButton> mb = new ArrayList<MenuButton>();

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		renderGame(g, gc.getWidth(), gc.getHeight());
	}

	public Random rand = new Random();

	public void craft() {
		for (int ii = 40; ii < 44; ii++) {
			if (s.mp.inven.i[ii] != null) {
				s.mp.inven.i[ii].stack--;
				if (s.mp.inven.i[ii].stack < 1) {
					s.mp.inven.i[ii] = null;
				}
			}
		}
	}

	public void breakTile(int xtile, int ytile, boolean sound) {
		if (s.map[xtile][ytile] != null) {
			Item drop = s.map[xtile][ytile].getDrop();
			if (drop != null) {
				s.items.add(new PickableItem(drop, xtile * tileWidth + 8f,
						ytile * tileHeight + 8f, rand.nextFloat() * 5f - 2.5f,
						rand.nextFloat() * -2f, 16f, 16f));
			}
			if (ytile - 1 > -1) {
				if (s.map[xtile][ytile - 1] != null) {
					if (!s.map[xtile][ytile - 1].canfloat) {
						Item dropx = s.map[xtile][ytile - 1].getDrop();
						if (dropx != null) {
							s.items.add(new PickableItem(dropx, xtile
									* tileWidth + 8f, ytile * tileHeight + 8f,
									rand.nextFloat() * 5f - 2.5f, rand
											.nextFloat() * -2f, 16f, 16f));
						}
						s.map[xtile][ytile - 1].breakTile();
						s.map[xtile][ytile - 1] = null;
					}
				}
			}
			if (xtile - 1 > -1) {
				if (s.map[xtile - 1][ytile] != null) {
					if (!s.map[xtile - 1][ytile].canfloat) {
						Item dropx = s.map[xtile - 1][ytile].getDrop();
						if (dropx != null) {
							s.items.add(new PickableItem(dropx, xtile
									* tileWidth + 8f, ytile * tileHeight + 8f,
									rand.nextFloat() * 5f - 2.5f, rand
											.nextFloat() * -2f, 16f, 16f));
						}
						s.map[xtile - 1][ytile].breakTile();
						s.map[xtile - 1][ytile] = null;
					}
				}
			}
			if (xtile + 1 < s.mapWidth) {
				if (s.map[xtile + 1][ytile] != null) {
					if (!s.map[xtile + 1][ytile].canfloat) {
						Item dropx = s.map[xtile + 1][ytile].getDrop();
						if (dropx != null) {
							s.items.add(new PickableItem(dropx, xtile
									* tileWidth + 8f, ytile * tileHeight + 8f,
									rand.nextFloat() * 5f - 2.5f, rand
											.nextFloat() * -2f, 16f, 16f));
						}
						s.map[xtile + 1][ytile].breakTile();
						s.map[xtile + 1][ytile] = null;
					}
				}
			}
			s.map[xtile][ytile].breakTile();
			s.map[xtile][ytile] = null;
			for (int ix = -1; ix < 2; ix++) {
				for (int iy = -1; iy < 2; iy++) {
					if (xtile + ix > -1 && xtile + ix < s.mapWidth
							&& ytile + iy > -1 && ytile + iy < s.mapHeight
							&& s.map[xtile + ix][ytile + iy] != null) {
						if (!s.map[xtile + ix][ytile + iy].tileUpdate()) {
							breakTile(xtile + ix, ytile + iy, false);
						}
					}
				}
			}
			if (sound)
				soundBin.get(20).play();
			reloadLights(xtile);
		}
	}

	public void mousePressed(int button, int x, int y) {
		if (s.map == null) {
			for (MenuButton mi : mb) {
				if (Mouse.getX() > mi.x && Mouse.getX() < mi.x + mi.w
						&& container.getHeight() - Mouse.getY() > mi.y
						&& container.getHeight() - Mouse.getY() < mi.y + mi.h) {
					mi.clickAction.run();
					break;
				}
			}
		} else {
			if (esmenu) {
				for (MenuButton mi : esm) {
					if (Mouse.getX() > mi.x
							&& Mouse.getX() < mi.x + mi.w
							&& container.getHeight() - Mouse.getY() > mi.y
							&& container.getHeight() - Mouse.getY() < mi.y
									+ mi.h) {
						mi.clickAction.run();
						break;
					}
				}
			} else {
				if (inventory) {
					currentGui.mousePressed(button, x, y);
				} else {
					if (s.canreach) {
						if (button == 0) {
							for (LivingEntity le : s.entities) {
								if (x + s.camX > le.x
										&& x + s.camX < le.x + le.w
										&& y + s.camY > le.y
										&& y + s.camY < le.y + le.h) {
									le.hit(-1);
									if (s.mp.inven.i[s.mp.invsel] != null
											&& s.mp.inven.i[s.mp.invsel].tool) {
										if (!(s.mp.inven.i[s.mp.invsel].use()))
											s.mp.inven.i[s.mp.invsel] = null;
									}
									break;
								}
							}
						} else if (button == 1) {
							if (s.map[selectedx][selectedy] != null) {
								if (s.map[selectedx][selectedy].RightClick()) {
									if (s.mp.inven.i[s.mp.invsel] != null) {
										if (!s.mp.inven.i[s.mp.invsel].use()) {
											s.mp.inven.i[s.mp.invsel] = null;
										}
									}
								}
							} else {
								if (drawselected) {
									if (s.mp.inven.i[s.mp.invsel] != null
											&& s.mp.inven.i[s.mp.invsel].food) {
										s.mp.takeHealth(s.mp.inven.i[s.mp.invsel].healthrep
												* -1);
										if (s.mp.health > 100)
											s.mp.health = 100;
										if (!(s.mp.inven.i[s.mp.invsel].use()))
											s.mp.inven.i[s.mp.invsel] = null;
									} else {
										if (s.mp.inven.i[s.mp.invsel] != null
												&& s.mp.inven.i[s.mp.invsel].placeable == true) {
											List<String> args = new ArrayList<String>();
											args.add(String.valueOf(selectedx));
											args.add(String.valueOf(selectedy));
											s.map[selectedx][selectedy] = s.mp.inven.i[s.mp.invsel]
													.getPlace(args);
											s.map[selectedx][selectedy]
													.placeTile();
											soundBin.get(19).play();
											if (!s.mp.inven.i[s.mp.invsel]
													.use())
												s.mp.inven.i[s.mp.invsel] = null;
											reloadLights(selectedx);
											for (int ix = -1; ix < 2; ix++) {
												for (int iy = -1; iy < 2; iy++) {
													if (selectedx + ix > -1
															&& selectedx + ix < s.mapWidth
															&& selectedy + iy > -1
															&& selectedy + iy < s.mapHeight) {
														if (s.map[selectedx
																+ ix][selectedy
																+ iy] != null) {
															if (!s.map[selectedx
																	+ ix][selectedy
																	+ iy]
																	.tileUpdate()) {
																breakTile(
																		selectedx
																				+ ix,
																		selectedy
																				+ iy,
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
						}
					}
				}
			}
		}
	}

	public List<CraftingRecipe> crafts = new ArrayList<CraftingRecipe>();

	public void reloadCrafting() {
		s.mp.inven.i[44] = null;
		String[][] current = new String[3][3];
		for (int ix = 0; ix < 3; ix++) {
			for (int iy = 0; iy < 3; iy++) {
				current[ix][iy] = "";
			}
		}
		if (s.mp.inven.i[40] != null)
			current[0][0] = s.mp.inven.i[40].name;
		if (s.mp.inven.i[41] != null)
			current[1][0] = s.mp.inven.i[41].name;
		if (s.mp.inven.i[42] != null)
			current[0][1] = s.mp.inven.i[42].name;
		if (s.mp.inven.i[43] != null)
			current[1][1] = s.mp.inven.i[43].name;
		for (CraftingRecipe cp : crafts) {
			boolean brk1 = false;
			for (int ix_ = 0; ix_ < cp.misx.length; ix_++) {
				int ax = cp.misx[ix_];
				int ay = cp.misy[ix_];
				String[][] currentcompare = new String[3][3];
				for (int ixx = 0; ixx < 3; ixx++) {
					for (int iyx = 0; iyx < 3; iyx++) {
						int tx = ixx + ax;
						int ty = iyx + ay;
						if (tx > -1 && tx < 3 && ty > -1 && ty < 3) {
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
				if (current[0][0].equals(currentcompare[0][0])) {
					if (current[1][0].equals(currentcompare[1][0])) {
						if (current[2][0].equals(currentcompare[2][0])) {
							if (current[0][1].equals(currentcompare[0][1])) {
								if (current[1][1].equals(currentcompare[1][1])) {
									if (current[2][1]
											.equals(currentcompare[2][1])) {
										if (current[0][2]
												.equals(currentcompare[0][2])) {
											if (current[1][2]
													.equals(currentcompare[1][2])) {
												if (current[2][2]
														.equals(currentcompare[2][2])) {
													s.mp.inven.i[44] = cp
															.getItem();
													brk1 = true;
													break;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			if (brk1)
				break;
		}
	}

	public void keyPressed(int key, char c) {
		if (!inCommand) {
			if (!esmenu) {
				if (key == 28) {
					inCommand = true;
				}
				if (key == 59) {
					renderinven = !renderinven;
				} else if (key == 61) {
					dbgrend = !dbgrend;
				}
				if (c == 'p' || c == 'P') {
					try {
						int width = 16384;
						int height = 16384;
						float camXl = s.camX;
						float camYl = s.camY;
						MD.game.s.camX = s.mp.x - width / 2 + s.mp.w / 2;
						MD.game.s.camY = s.mp.y - height / 2 + s.mp.h / 2;
						if (MD.game.s.camX < 0)
							MD.game.s.camX = 0;
						if (MD.game.s.camY < 0)
							MD.game.s.camY = 0;
						if (MD.game.s.camX > MD.game.s.mapWidth
								* MD.game.tileWidth - width)
							MD.game.s.camX = MD.game.s.mapWidth
									* MD.game.tileWidth - width;
						if (MD.game.s.camY > MD.game.s.mapHeight
								* MD.game.tileHeight - height)
							MD.game.s.camY = MD.game.s.mapHeight
									* MD.game.tileHeight - height;
						Image im = new Image(width, height);
						Graphics img_g = im.getGraphics();
						img_g.setBackground(new Color(0, 0, 0, 255));
						img_g.clear();
						renderGame(img_g, width, height);
						img_g.flush();
						ImageOut.write(im, "img.png");
						s.camX = camXl;
						s.camY = camYl;
					} catch (SlickException e) {
						e.printStackTrace();
					}

				}
				if (c == 'c' || c == 'C') {
					s.map = null;
					s.entities.clear();
					s.items.clear();
					s.mp = new Player(256f, 16000f, 24, 24, 100);
				}
				if (c == 'g' || c == 'G') {
					/*
					 * try { randomGen(); } catch (SlickException e) {
					 * e.printStackTrace(); }
					 */
					if (generateWorld != null) {
						generateWorld.dispose();
					}
					generateWorld = new GenerateWorldJFrame();
					generateWorld.setVisible(true);
				}
				if (c == 'k' || c == 'K') {
					s.mp.y += 100 * tileHeight;
				}
				if (c == 'e' || c == 'E') {
					currentGui = playerGui;
					currentGui.resized((GameContainer) container);
					inventory = !inventory;
				}
				if (key > 1 && key < 12) {
					s.mp.invsel = key + 28;
				}
				if (c == 't' || c == 'T') {
					inCommand = true;
				}
				if (key == 1) {
					if (s.map != null) {
						if (inventory) {
							inventory = false;
						} else
							esmenu = true;
					}
				}
			} else {
				if (key == 1) {
					esmenu = false;
				}
			}

		} else {
			if (key == 14) {
				if (currentCommand.length() > 0)
					currentCommand = currentCommand.substring(0,
							currentCommand.length() - 1);
			} else if (key == 28) {
				submitCommand(currentCommand);
				currentCommand = "";
				inCommand = false;
			} else if (key == 1) {
				currentCommand = "";
				inCommand = false;
			} else {
				currentCommand += c;
			}
			acommand = "";
			for (int i = 0; i < currentCommand.length(); i++) {
				String charx = currentCommand.substring(i, i + 1);
				if (charx.equals(" "))
					break;
				acommand += charx;
			}
		}
		try {
			if (key == 87) {
				if (container.isFullscreen()) {
					container.setDisplayMode(800, 600, false);
				} else {
					java.awt.Dimension d = java.awt.Toolkit.getDefaultToolkit()
							.getScreenSize();
					container.setDisplayMode(d.width, d.height, true);
				}
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void submitCommand(String cc) {
		try {
			int mx = (int) (s.mp.x / tileWidth);
			int my = (int) (s.mp.y / tileWidth);
			boolean playSound = true;
			if (cc.startsWith("/")) {
				String cmd = cc.substring(1, cc.length());
				List<String> args = argReturn(cmd, " ");
				switch (args.get(0)) {
				case "toolbox":
					switch (args.get(1)) {
					case "diamond":
						s.items.add(new PickableItem(new DiamondAxeItem(),
								s.mp.x, s.mp.y - 128f, -1f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new DiamondSwordItem(),
								s.mp.x, s.mp.y - 128f, -0.5f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new DiamondShovelItem(),
								s.mp.x, s.mp.y - 128f, 0.5f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new DiamondPickaxeItem(),
								s.mp.x, s.mp.y - 128f, 1f, 0f, 16f, 16f));
						break;
					case "iron":
						s.items.add(new PickableItem(new IronAxeItem(), s.mp.x,
								s.mp.y - 128f, -1f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new IronSwordItem(),
								s.mp.x, s.mp.y - 128f, -0.5f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new IronShovelItem(),
								s.mp.x, s.mp.y - 128f, 0.5f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new IronPickaxeItem(),
								s.mp.x, s.mp.y - 128f, 1f, 0f, 16f, 16f));
						break;
					case "stone":
						s.items.add(new PickableItem(new StoneAxeItem(),
								s.mp.x, s.mp.y - 128f, -1f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new StoneSwordItem(),
								s.mp.x, s.mp.y - 128f, -0.5f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new StoneShovelItem(),
								s.mp.x, s.mp.y - 128f, 0.5f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new StonePickaxeItem(),
								s.mp.x, s.mp.y - 128f, 1f, 0f, 16f, 16f));
						break;
					case "wooden":
						s.items.add(new PickableItem(new WoodenAxeItem(),
								s.mp.x, s.mp.y - 128f, -1f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new WoodenSwordItem(),
								s.mp.x, s.mp.y - 128f, -0.5f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new WoodenShovelItem(),
								s.mp.x, s.mp.y - 128f, 0.5f, 0f, 16f, 16f));
						s.items.add(new PickableItem(new WoodenPickaxeItem(),
								s.mp.x, s.mp.y - 128f, 1f, 0f, 16f, 16f));
						break;
					}
					break;
				case "stuck":
					s.items.add(new PickableItem(new LadderItem(64), s.mp.x,
							s.mp.y - 128f, 0f, -2f, 16f, 16f));
					break;
				case "treefarm":
					s.items.add(new PickableItem(new SaplingItem(64), s.mp.x,
							s.mp.y - 128f, -1f, -2f, 16f, 16f));
					s.items.add(new PickableItem(new BoneMealItem(64), s.mp.x,
							s.mp.y - 128f, 1f, -2f, 16f, 16f));
					break;
				case "sunlight":
					if (args.get(1).equals("default")) {
						sunlight = 275 + s.grassHeight;
					} else {
						sunlight = Integer.valueOf(args.get(1));
					}
					reloadLights(-1);
					break;
				case "torch":
					s.items.add(new PickableItem(new TorchItem(64), s.mp.x,
							s.mp.y - 128f, 0f, -2f, 16f, 16f));
					break;
				case "deflight":
					if (args.get(1).equals("default")) {
						deflight = 0;
					} else {
						deflight = Integer.valueOf(args.get(1));
					}
					reloadLights(-1);
					break;
				case "zombierain":
					for (int i = 0; i < s.mapWidth - 1; i++) {
						s.entities.add(new EntityZombie(i * tileWidth, rand
								.nextFloat() * 15000F, 50));
					}
					playSound = false;
					soundBin.get(11).play();
					break;
				case "tp":
					s.mp.x = Float.valueOf(Integer.valueOf(args.get(1))
							* tileWidth);
					s.mp.y = Float.valueOf(Integer.valueOf(args.get(2))
							* tileHeight);
					break;
				case "frozen":
					frozen = !frozen;
					playSound = false;
					soundBin.get(13).play();
					break;
				case "killall":
					s.entities.clear();
					break;
				case "pork":
					for (int i = 0; i < 10; i++)
						s.entities.add(new EntityPig(s.mp.x - rand.nextFloat()
								* 8.0f - 4.0f, s.mp.y - 100f, 25));
					break;
				case "furnace":
					 s.items.add(new PickableItem(new FurnaceItem(64), s.mp.x,
					 s.mp.y - 128f, 0f, -2f, 16f, 16f));
					s.items.add(new PickableItem(new CoalItem(64), s.mp.x,
							s.mp.y - 128f, 0f, -2f, 16f, 16f));
					break;
				case "godmode":
					godmode = !godmode;
					playSound = false;
					if (godmode) {
						soundBin.get(15).play();
					} else {
						soundBin.get(16).play();
					}
					break;
				case "heal":
					int resetj = 100 - s.mp.health;
					s.mp.takeHealth(resetj * -1);
					playSound = false;
					break;
				case "walled":
					for (int ix = -4; ix < 5; ix++) {
						for (int iy = -4; iy < 5; iy++) {
							if (mx + ix > -1 && my + iy > -1
									&& mx + ix < s.mapWidth
									&& my + iy < s.mapHeight) {
								if (s.map[mx + ix][my + iy] != null) {
									s.map[mx + ix][my + iy].breakTile();
									s.map[mx + ix][my + iy] = null;
								}
							}
						}
					}
					playSound = false;
					soundBin.get(14).play();
					reloadLights(-1);
					break;
				case "house":
					for (int ix = -4; ix < 5; ix++) {
						for (int iy = -4; iy < 5; iy++) {
							if (mx + ix > -1 && my + iy > -1
									&& mx + ix < s.mapWidth
									&& my + iy < s.mapHeight) {
								if (s.map[mx + ix][my + iy] != null) {
									s.map[mx + ix][my + iy].breakTile();
									s.map[mx + ix][my + iy] = null;
								}
							}
						}
					}
					for (int ix = -4; ix < 5; ix++) {
						s.map[mx + ix][my - 4] = new NormalTile(11, true,
								"Wooden Planks");
						s.map[mx + ix][my + 4] = new NormalTile(11, true,
								"Wooden Planks");
					}
					for (int iy = -4; iy < 5; iy++) {
						s.map[mx - 4][my - iy] = new NormalTile(11, true,
								"Wooden Planks");
						s.map[mx + 4][my + iy] = new NormalTile(11, true,
								"Wooden Planks");
					}
					 s.map[mx + 3][my + 3] = new FurnaceTile();
					s.map[mx + 3][my + 3].placeTile();
					s.map[mx + 2][my + 3] = new ChestTile();
					s.map[mx + 2][my + 3].placeTile();
					s.map[mx + 1][my + 3] = new ChestTile();
					s.map[mx + 1][my + 3].placeTile();
					s.map[mx - 3][my + 3] = new CraftingTableTile(mx - 3,
							my + 3, my - 3 + 3);
					s.map[mx - 3][my + 3].placeTile();
					s.map[mx - 3][my] = new TorchTile(mx - 3, my);
					s.map[mx - 3][my].placeTile();
					s.map[mx - 3][my].tileUpdate();
					s.map[mx + 3][my] = new TorchTile(mx + 3, my);
					s.map[mx + 3][my].placeTile();
					s.map[mx + 3][my].tileUpdate();
					s.map[mx + 4][my + 1].breakTile();
					s.map[mx + 4][my + 1] = null;
					s.map[mx + 4][my + 2].breakTile();
					s.map[mx + 4][my + 2] = null;

					playSound = false;
					soundBin.get(17).play();
					reloadLights(-1);
					break;
				case "mobspawn":
					mobspawn = !mobspawn;
					break;
				case "instantmine":
					instantmine = !instantmine;
					break;
				case "airjump":
					airjump = !airjump;
					break;
				case "explosion":
					createExplosion(Integer.valueOf(args.get(2)),
							Integer.valueOf(args.get(3)),
							Integer.valueOf(args.get(1)), true, true, null);
					playSound = false;
					reloadLights(-1);
					break;
				case "tnt":
					s.items.add(new PickableItem(new TntItem(64), s.mp.x,
							s.mp.y - 128f, 0f, -2f, 16f, 16f));
					break;
				case "tntdirt":
					for (int ix = 0; ix < s.mapWidth; ix++) {
						for (int iy = 0; iy < s.mapHeight; iy++) {
							if (s.map[ix][iy] != null) {
								if (s.map[ix][iy].getType().equals("Dirt")) {
									s.map[ix][iy] = new TntTile(ix, iy);
								}
							}
						}
					}
					break;
				case "tntpole":
					for (int iy = 0; iy < s.mapHeight; iy++) {
						if (s.map[mx][iy] != null) {
							s.map[mx][iy].breakTile();
							s.map[mx][iy] = null;
						}
						s.map[mx][iy] = new TntTile(mx, iy);
					}
					s.mp.x -= tileWidth;
					break;
				case "worldtnt":
					for (int ix = 0; ix < s.mapWidth; ix++) {
						for (int iy = 0; iy < s.mapHeight; iy++) {
							if (s.map[ix][iy] != null) {
								s.map[ix][iy].breakTile();
								s.map[ix][iy] = null;
								s.map[ix][iy] = new TntTile(ix, iy);
							}
						}
					}
					reloadLights(-1);
					break;
				case "reach":
					if (args.get(1).equals("default")) {
						reach = 12;
					} else {
						reach = Integer.valueOf(args.get(1));
					}
					break;
				case "spawnzombie":
					for (int i = 0; i < Integer.valueOf(args.get(3)); i++) {
						EntityZombie ez = new EntityZombie(Integer.valueOf(args
								.get(1)) * tileWidth, Integer.valueOf(args
								.get(2)) * tileHeight, 50);
						ez.fx = rand.nextFloat() * 10f - 5f;
						ez.fy = rand.nextFloat() * 10f - 5f;
						s.entities.add(ez);
					}
					break;
				case "physics":
					physics = !physics;
					s.mp.fx = 0f;
					s.mp.fy = 0f;
					break;
				case "emeraldruby":
					s.items.add(new PickableItem(new EmeraldItem(64), s.mp.x,
							s.mp.y - 128f, 0f, -2f, 16f, 16f));
					s.items.add(new PickableItem(new RubyItem(64), s.mp.x,
							s.mp.y - 128f, 0f, -2f, 16f, 16f));
					break;
				case "build":
					s.items.add(new PickableItem(new StoneItem(9999, 9999, 43,
							"Bricks"), s.mp.x, s.mp.y - 128f, 0f, -2f, 16f, 16f));
					s.items.add(new PickableItem(new StoneItem(9999, 9999, 44,
							"Stone Bricks"), s.mp.x, s.mp.y - 128f, 0f, -2f,
							16f, 16f));
					break;
				case "tree":
					int height = my;
					int ix = mx;
					for (int i = 0; i < 3 + rand.nextInt(4); i++) {
						if (height > -1 && height < s.mapHeight && ix > -1
								&& ix < s.mapWidth) {
							s.map[ix][height] = new WoodTile();
						}
						height--;
					}
					for (int ixz = -1 + rand.nextInt(2) * -1; ixz < 1 + rand
							.nextInt(4) + 2; ixz++) {
						for (int iyz = -1 + rand.nextInt(2) * -1; iyz < 1 + rand
								.nextInt(4); iyz++) {
							if (ix + ixz > -1 && ix + ixz < s.mapWidth
									&& height + iyz > -1
									&& height + iyz < s.mapHeight) {
								if (s.map[ix + ixz][height + iyz] == null) {
									s.map[ix + ixz][height + iyz] = new LeafTile();
								}
							}
						}
					}
					while (s.mp.isColliding()) {
						s.mp.x--;
					}
					break;
				case "tntsurface":
					for (int ixx = 0; ixx < s.mapWidth; ixx++) {
						for (int iy = 0; iy < s.mapHeight; iy++) {
							if (s.map[ixx][iy] != null) {
								if (s.map[ixx][iy - 1] != null) {
									s.map[ixx][iy - 1].breakTile();
									s.map[ixx][iy - 1] = null;
								}
								s.map[ixx][iy - 1] = new TntTile(ixx, iy - 1);
								break;
							}
						}
					}
					while (s.mp.isColliding())
						s.mp.y--;
					break;
				}
			}
			if (playSound) {
				soundBin.get(9).play();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int pull(int original, int puller) {
		if (original > puller) {
			int o = (int) (original - puller);
			o = (int) (o / 2);
			return original - o;
		} else if (original < puller) {
			int o = (int) (puller - original);
			o = (int) (o / 2);
			return original + o;
		} else {
			return original;
		}
	}

	public void reloadLights(int row) {
		if (row == -1) {
			for (int ix = 0; ix < s.mapWidth; ix++) {
				for (int iy = 0; iy < s.mapHeight; iy++) {
					light[ix][iy] = deflight;
					light_r[ix][iy] = 255;
					light_g[ix][iy] = 255;
					light_b[ix][iy] = 255;
				}
			}
		} else {
			for (int iy = 0; iy < s.mapHeight; iy++) {
				light[row][iy] = deflight;
				light_r[row][iy] = 255;
				light_g[row][iy] = 255;
				light_b[row][iy] = 255;
			}
		}
		for (Light li : s.lightoutset) {
			int tileX = li.x;
			int tileY = li.y;
			for (int ix = li.rad * -1; ix < li.rad + 1; ix++) {
				for (int iy = li.rad * -1; iy < li.rad + 1; iy++) {
					if (tileX + ix > -1 && tileY + iy > -1
							&& tileX + ix < s.mapWidth
							&& tileY + iy < s.map[tileX + ix].length) {
						int k = ix;
						if (k < 0)
							k *= -1;
						k *= li.light / 10;
						int l = iy;
						if (l < 0)
							l *= -1;
						l *= li.light / 10;
						if (row == -1 || row == tileX + ix) {
							light[tileX + ix][tileY + iy] += li.light - k - l;
							light_r[tileX + ix][tileY + iy] = pull(
									light_r[tileX + ix][tileY + iy], li.r);
							light_g[tileX + ix][tileY + iy] = pull(
									light_g[tileX + ix][tileY + iy], li.g);
							light_b[tileX + ix][tileY + iy] = pull(
									light_b[tileX + ix][tileY + iy], li.b);
						}
					}
				}
			}
		}
		if (row == -1) {
			for (int ix = 0; ix < s.mapWidth; ix++) {
				int ray = sunlight;
				for (int iy = 0; iy < s.mapHeight; iy++) {
					// cast ray
					if (s.map[ix][iy] != null && s.map[ix][iy].declight) {
						ray -= 48;
						if (ray < 1) {
							break;
						}
					}
					light[ix][iy] += ray;
					ray--;
					if (ray < 1) {
						break;
					}
				}
			}
		} else {
			int ray = sunlight;
			for (int iy = 0; iy < s.mapHeight; iy++) {
				// cast ray
				if (s.map[row][iy] != null && s.map[row][iy].declight) {
					ray -= 48;
					if (ray < 1) {
						break;
					}
				}
				light[row][iy] += ray;
				ray--;
				if (ray < 1) {
					break;
				}
			}
		}
	}

	public void createOre(int ore, Graphics g, int x, int y, int m, int max_i) {
		g.setColor(new Color(ore, 0, 0, 255));
		for (int i = 0; i < max_i; i++)
			g.fillRect(x + rand.nextInt(m), y + rand.nextInt(m),
					1 + rand.nextInt(max_i), 1 + rand.nextInt(max_i));
		g.setColor(new Color(1, 0, 0, 255));
	}

	public void seedOre(int ore, Graphics g, int loops, int m, int max_i, int l) {
		for (int i = 0; i < loops; i++) {
			int ix = rand.nextInt(s.mapWidth);
			int iy = rand.nextInt(s.mapHeight - s.grassHeight);
			System.out.println(ore + " " + ix + " " + iy + " " + i + " / "
					+ loops);
			if (g.getPixel(ix, iy).getRed() == 1) {
				// System.out.println("ore [" + ore + "]: " + i + " / " + loops
				// + " x: " + ix + " y: " + String.valueOf(iy + 512));
				createOre(ore, g, ix, iy, m, max_i);
			}
		}
	}

	public void randomGen() throws SlickException {
		System.out.println("Initializing Map...");
		s.map = new Tile[s.mapWidth][s.mapHeight];
		light = new int[s.mapWidth][s.mapHeight];
		light_r = new int[s.mapWidth][s.mapHeight];
		light_g = new int[s.mapWidth][s.mapHeight];
		light_b = new int[s.mapWidth][s.mapHeight];
		s.lightoutset.clear();
		for (int ix = 0; ix < s.mapWidth; ix++) {
			for (int iy = 0; iy < s.mapHeight; iy++) {
				if (iy > s.grassHeight + 5) {
					s.map[ix][iy] = new NormalTile(1, true, "Stone");
				} else {
					s.map[ix][iy] = new NormalTile(0, true, "Dirt");
				}
				light[ix][iy] = 0;
			}
		}
		int gg = s.grassHeight + 5;
		Image mapgen = new Image(s.mapWidth, s.mapHeight - gg); // OPTIPLEX 210L
																// cannot handle
																// this.
																// 512x3579
		Graphics g = mapgen.getGraphics();
		g.setBackground(new Color(0, 0, 0, 0));
		g.clear();
		g.setColor(new Color(1, 0, 0, 255));
		System.out.println("Generating Caves...");
		for (int k = 0; k < s.mapWidth * s.mapHeight / 1024; k++) {
			int averagebulk = 6 + rand.nextInt(15);
			int sx = rand.nextInt(mapgen.getWidth());
			int sy = rand.nextInt(mapgen.getHeight());
			g.fillOval(sx, sy, rand.nextInt(averagebulk),
					rand.nextInt(averagebulk));
			boolean left = false;
			boolean right = false;
			boolean up = false;
			boolean down = false;
			int rx = rand.nextInt(4);
			switch (rx) {
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
				break;
			}
			for (int l = 0; l < 25; l++) {
				if (left)
					sx--;
				if (right)
					sx++;
				if (up)
					sy--;
				if (down)
					sy++;
				if (sx > s.mapWidth)
					sx = 0;
				if (sy > s.mapHeight)
					sy = 0;
				if (sx < 0)
					sx = s.mapWidth;
				if (sy < 0)
					sy = s.mapHeight;
				g.fillOval(sx, sy, rand.nextInt(averagebulk),
						rand.nextInt(averagebulk));
				if (rand.nextInt(100) > 90) {
					g.setColor(new Color(2, 0, 0, 255));
					g.fillOval(sx, sy, rand.nextInt(averagebulk),
							rand.nextInt(averagebulk));
					g.setColor(new Color(1, 0, 0, 255));
				}
				if (rand.nextInt(50) > 40) {
					int o = rand.nextInt(4);
					switch (o) {
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
						break;
					}
				}
			}
		}
		System.out.println("Seeding Coal");
		seedOre(3, g, (int) (20000), 3, 3, 15);
		System.out.println("Seeding Iron");
		seedOre(5, g, (int) (s.mapWidth * s.mapHeight / 1700), 2, 2, 200);
		System.out.println("Seeding Diamond");
		seedOre(4, g, (int) (s.mapWidth * s.mapHeight / 5000), 1, 1, 512);
		System.out.println("Seeding Emerald & Ruby");
		seedOre(6, g, (int) (s.mapWidth * s.mapHeight / 6000), 1, 1, 512);
		seedOre(7, g, (int) (s.mapWidth * s.mapHeight / 6000), 1, 1, 512);
		g.flush();
		for (int ix = 0; ix < s.mapWidth; ix++) {
			for (int iy = s.grassHeight + 5; iy < s.mapHeight; iy++) {
				int cu = mapgen.getColor(ix, iy - gg).getRed();
				if (cu == 1) {
					s.map[ix][iy] = null;
				} else if (cu == 2) {
					s.map[ix][iy] = new NormalTile(0, true, "Dirt");
				} else if (cu == 3) {
					s.map[ix][iy] = new CoalOre();
				} else if (cu == 4) {
					s.map[ix][iy] = new DiamondOre();
				} else if (cu == 5) {
					s.map[ix][iy] = new IronOre();
				} else if (cu == 6) {
					s.map[ix][iy] = new RubyOre();
				} else if (cu == 7) {
					s.map[ix][iy] = new EmeraldOre();
				}
			}
		}
		System.out.println("Putting Bedrock...");
		for (int ix = 0; ix < s.mapWidth; ix++) {
			for (int iy = s.mapHeight - 5; iy < s.mapHeight; iy++) {
				s.map[ix][iy] = new NormalTile(2, true, "Bedrock");
			}
		}
		System.out.println("Making Overworld Space...");
		for (int ix = 0; ix < s.mapWidth; ix++) {
			for (int iy = 0; iy < s.grassHeight; iy++) {
				s.map[ix][iy] = null;
			}
		}
		int gheight = s.grassHeight;
		System.out.println("Putting Grass...");
		int dir = 0;
		int alt = 0;
		for (int ix = 0; ix < s.mapWidth; ix++) {
			if (alt < 1) {
				if (dir == 1) {
					if (gheight < s.grassHeight)
						gheight++;
				} else if (dir == 2) {
					gheight--;
				}
				alt = rand.nextInt(3);
			} else {
				alt--;
			}
			if (rand.nextInt(500) > 400) {
				dir = rand.nextInt(3);
			}
			s.map[ix][gheight] = new NormalTile(3, true, "Grass");
			if (gheight < s.grassHeight) {
				for (int iy = gheight + 1; iy < s.grassHeight; iy++) {
					s.map[ix][iy] = new NormalTile(0, true, "Dirt");
				}
			}
		}
		System.out.println("Putting Trees...");
		for (int ix = 0; ix < s.mapWidth; ix++) {
			if (rand.nextInt(512) > 490) {
				int height = 0;
				for (int iy = 0; iy < s.mapHeight; iy++) {
					if (s.map[ix][iy] != null) {
						if (s.map[ix][iy].img == 3) {
							height = iy - 1;
							break;
						}
					}
				}
				for (int i = 0; i < 3 + rand.nextInt(4); i++) {
					if (height > -1 && height < s.mapHeight && ix > -1
							&& ix < s.mapWidth) {
						s.map[ix][height] = new WoodTile();
					}
					height--;
				}
				for (int ixz = -1 + rand.nextInt(2) * -1; ixz < 1 + rand
						.nextInt(4) + 2; ixz++) {
					for (int iyz = -1 + rand.nextInt(2) * -1; iyz < 1 + rand
							.nextInt(4); iyz++) {
						if (ix + ixz > -1 && ix + ixz < s.mapWidth
								&& height + iyz > -1
								&& height + iyz < s.mapHeight) {
							if (s.map[ix + ixz][height + iyz] == null) {
								s.map[ix + ixz][height + iyz] = new LeafTile();
							}
						}
					}
				}
			}
		}
		System.out.println("Reloading Lights...");
		reloadLights(-1);
		System.out.println("Updating Tiles...");
		for (int ix = 0; ix < s.mapWidth; ix++) {
			for (int iy = 0; iy < s.mapHeight; iy++) {
				if (s.map[ix][iy] != null)
					s.map[ix][iy].tileUpdate();
			}
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		logo = new Image("res/logo.png");
		gc.setShowFPS(false);
		playerGui = new PlayerGui(gc);
		container = (AppGameContainer) gc;
		soundBin.add(new Sound("res/sound/hurt.wav")); // 0
		soundBin.add(new Sound("res/sound/hurt2.wav")); // 1
		soundBin.add(new Sound("res/sound/hurt3.wav")); // 2
		soundBin.add(new Sound("res/sound/hurt4.wav")); // 3
		soundBin.add(new Sound("res/sound/hurt5.wav")); // 4
		soundBin.add(new Sound("res/sound/hurt6.wav")); // 5
		soundBin.add(new Sound("res/sound/hurt7.wav")); // 6
		soundBin.add(new Sound("res/sound/hurt8.wav")); // 7
		soundBin.add(new Sound("res/sound/heal.wav")); // 8
		soundBin.add(new Sound("res/sound/command.wav")); // 9
		soundBin.add(new Sound("res/sound/die.wav")); // 10
		soundBin.add(new Sound("res/sound/zombierain.wav")); // 11
		soundBin.add(new Sound("res/sound/item.wav")); // 12
		soundBin.add(new Sound("res/sound/frozen.wav")); // 13
		soundBin.add(new Sound("res/sound/wall.wav")); // 14
		soundBin.add(new Sound("res/sound/godmode.wav")); // 15
		soundBin.add(new Sound("res/sound/godmodeoff.wav")); // 16
		soundBin.add(new Sound("res/sound/house.wav")); // 17
		soundBin.add(new Sound("res/sound/jump.wav")); // 18
		soundBin.add(new Sound("res/sound/place.wav")); // 19
		soundBin.add(new Sound("res/sound/blockbreak.wav")); // 20
		soundBin.add(new Sound("res/sound/tnt.wav")); // 21

		textureBin.add(new Image("res/dirt.png")); // 0
		textureBin.add(new Image("res/stone.png")); // 1
		textureBin.add(new Image("res/bedrock.png")); // 2
		textureBin.add(new Image("res/grass.png")); // 3
		textureBin.add(new Image("res/coal.png")); // 4
		textureBin.add(new Image("res/coalitem.png")); // 5
		textureBin.add(new Image("res/torchdown.png")); // 6
		textureBin.add(new Image("res/torchleft.png")); // 7
		textureBin.add(new Image("res/torchright.png")); // 8
		textureBin.add(new Image("res/wood.png")); // 9
		textureBin.add(new Image("res/leaves.png")); // 10
		textureBin.add(new Image("res/planks.png")); // 11
		textureBin.add(new Image("res/stick.png")); // 12
		textureBin.add(new Image("res/sapling.png")); // 13
		textureBin.add(new Image("res/diamond.png")); // 14
		textureBin.add(new Image("res/diamonditem.png")); // 15
		textureBin.add(new Image("res/chest.png")); // 16
		textureBin.add(new Image("res/craftingtable.png")); // 17
		textureBin.add(new Image("res/woodenpickaxe.png")); // 18
		textureBin.add(new Image("res/stonepickaxe.png")); // 19
		textureBin.add(new Image("res/cobblestone.png")); // 20
		textureBin.add(new Image("res/iron.png")); // 21
		textureBin.add(new Image("res/furnace.png")); // 22
		textureBin.add(new Image("res/ironitem.png")); // 23
		textureBin.add(new Image("res/ironpickaxe.png")); // 24
		textureBin.add(new Image("res/diamondpickaxe.png")); // 25
		textureBin.add(new Image("res/woodenshovel.png")); // 26
		textureBin.add(new Image("res/stoneshovel.png")); // 27
		textureBin.add(new Image("res/ironshovel.png")); // 28
		textureBin.add(new Image("res/diamondshovel.png")); // 29
		textureBin.add(new Image("res/woodenaxe.png")); // 30
		textureBin.add(new Image("res/stoneaxe.png")); // 31
		textureBin.add(new Image("res/ironaxe.png")); // 32
		textureBin.add(new Image("res/diamondaxe.png")); // 33
		textureBin.add(new Image("res/woodensword.png")); // 34
		textureBin.add(new Image("res/stonesword.png")); // 35
		textureBin.add(new Image("res/ironsword.png")); // 36
		textureBin.add(new Image("res/diamondsword.png")); // 37
		textureBin.add(new Image("res/rawpork.png")); // 38
		textureBin.add(new Image("res/pork.png")); // 39
		textureBin.add(new Image("res/ladder.png")); // 40
		textureBin.add(new Image("res/tnt.png")); // 41
		textureBin.add(new Image("res/bonemeal.png")); // 42
		textureBin.add(new Image("res/bricks.png")); // 43
		textureBin.add(new Image("res/stonebricks.png")); // 44
		textureBin.add(new Image("res/ruby.png")); //45
		textureBin.add(new Image("res/rubygem.png")); //46
		textureBin.add(new Image("res/emerald.png")); //47
		textureBin.add(new Image("res/emeraldgem.png")); //48
		breakingBin.add(new Image("res/breaking/breaking1.png"));
		breakingBin.add(new Image("res/breaking/breaking2.png"));
		breakingBin.add(new Image("res/breaking/breaking3.png"));
		breakingBin.add(new Image("res/breaking/breaking4.png"));
		breakingBin.add(new Image("res/breaking/breaking5.png"));
		breakingBin.add(new Image("res/breaking/breaking6.png"));
		breakingBin.add(new Image("res/breaking/breaking7.png"));
		breakingBin.add(new Image("res/breaking/breaking8.png"));
		s.entities.add(new EntityPig(192f, 15000f, 10));
		CraftingRecipes.addCrafts();
	}

	public List<Runnable> remtask = null;

	public boolean reactm = true;

	public boolean reactz = true;

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		/*
		 * if (genWorldNow) { randomGen(); genWorldNow = false; }
		 */
		if (MD.vsync == -1 || milliLas + MD.vsync < System.currentTimeMillis()) {
			if (lw != gc.getWidth() || lh != gc.getHeight()) {
				lw = gc.getWidth();
				lh = gc.getHeight();
				windowResize(gc);
			}
			if (s.map != null && !esmenu) {
				if (mobspawn) {
					if (rand.nextInt(1024) > 1010) {
						float minx = s.mp.x - 1700f;
						float miny = s.mp.y - 1700f;
						float maxx = s.mp.x + 1700f;
						float maxy = s.mp.y + 1700f;
						if (minx < 0f)
							minx = 0f;
						if (miny < 0f)
							miny = 0f;
						if (maxx > s.mapWidth * tileWidth)
							maxx = s.mapWidth * tileWidth;
						if (maxy > s.mapHeight * tileHeight)
							maxy = s.mapHeight * tileHeight;
						float sx = minx + rand.nextFloat() * maxx - minx;
						float sy = miny + rand.nextFloat() * maxy - miny;
						EntityZombie ez = new EntityZombie(sx, sy, 50);
						boolean spawnation = false;
						int zx = (int) (ez.x / tileWidth);
						int zy = (int) (ez.y / tileHeight);
						for (int ix = -5; ix < 6; ix++) {
							boolean brk = false;
							for (int iy = -5; iy < 6; iy++) {
								if (zx + ix > -1 && zy + iy > -1
										&& zx + ix < s.mapWidth
										&& zy + iy < s.mapHeight) {
									if (s.map[zx + ix][zy + iy] != null) {
										spawnation = true;
										brk = true;
										break;
									}
								}
							}
							if (brk)
								break;
						}
						if (spawnation
								&& light[(int) (ez.x / tileWidth)][(int) (ez.y / tileHeight)] < 200
								&& !ez.isColliding()) {
							System.out.println("[DEBUG] zombie spawned at " + sx
									/ tileWidth + " " + sy / tileHeight);
							s.entities.add(ez);
						}
					}
				}
				if (selectedx > -1 && selectedx < s.mapWidth && selectedy > -1
						&& selectedy < s.mapHeight) {
					if (s.canreach && s.map[selectedx][selectedy] != null
							&& Mouse.isButtonDown(0)) {
						if (instantmine) {
							blockbreak -= 8192;
						} else {
							blockbreak -= s.map[selectedx][selectedy]
									.getHardness();
						}
						if (blockbreak < 1) {
							blockbreak = 8192;
							if (s.canreach) {
								breakTile(selectedx, selectedy, true);
								if (s.mp.inven.i[s.mp.invsel] != null) {
									if (s.mp.inven.i[s.mp.invsel].tool) {
										if (!(s.mp.inven.i[s.mp.invsel].use())) {
											s.mp.inven.i[s.mp.invsel] = null;
										}
									}
								}
							}
						}
						if (reactm) {
							blockbreak = 8192;
							reactm = false;
						}
					} else
						reactm = true;
				}
				remtask = new ArrayList<Runnable>();
				for (Runnable r : tasks)
					r.run();
				for (Runnable r : remtask)
					tasks.remove(r);
				healthforce = healthforce * 0.99f;
				float hb = healthbright;
				healthbright += healthforce;
				healthforce -= 0.4f;
				if (healthbright > 255f) {
					healthbright = hb;
					healthforce = healthforce * -0.5f;
				}
				if (healthbright < 0f) {
					healthbright = hb;
					healthforce = healthforce * -0.5f;
				}
				s.mp.update(gc);
				if (!frozen) {
					List<LivingEntity> rementit = new ArrayList<LivingEntity>();
					for (int i = 0; i < s.entities.size(); i++) {
						if (!s.entities.get(i).update())
							rementit.add(s.entities.get(i));
					}
					for (LivingEntity le : rementit)
						s.entities.remove(le);
				}
				milliLas = System.currentTimeMillis();
				List<PickableItem> remi = new ArrayList<PickableItem>();
				for (PickableItem pi : s.items) {
					if (pi.decenting != true
							&& GameTools.GetCollision(s.mp.x, s.mp.y, s.mp.w,
									s.mp.h, pi.x, pi.y, pi.w, pi.h)) {
						int stk = pi.pick.stack;
						for (int ii = 0; ii < s.mp.inven.i.length - 5; ii++) {
							int iii = s.mp.inven.i.length - ii - 1 - 5;
							if (s.mp.inven.i[iii] != null) {
								if (s.mp.inven.i[iii].name == pi.pick.name
										&& s.mp.inven.i[iii].stack < s.mp.inven.i[iii].maxstack) {
									for (int i = 0; i < stk; i++) {
										if (s.mp.inven.i[iii].stack < s.mp.inven.i[iii].maxstack) {
											s.mp.inven.i[iii].stack++;
											stk--;
										} else {
											break;
										}
									}
									if (stk < 1) {
										break;
									}
								}
							}
						}
						pi.pick.stack = stk;
						if (stk > 0) {
							for (int ii = 0; ii < s.mp.inven.i.length - 5; ii++) {
								int iii = s.mp.inven.i.length - 5 - ii - 1;
								if (s.mp.inven.i[iii] == null) {
									s.mp.inven.i[iii] = pi.pick;
									stk = 0;
									break;
								}
							}
						}
						if (stk < 1) {
							pi.decenting = true;
							MD.game.soundBin.get(12).play();
						}
					}
					if (!frozen) {
						if (!pi.update())
							remi.add(pi);
					}
				}
				for (PickableItem pi : remi)
					s.items.remove(pi);
			} else {
				if (esmenu) {
					for (MenuButton mi : esm) {
						if (Mouse.getX() > mi.x && Mouse.getX() < mi.x + mi.w
								&& gc.getHeight() - Mouse.getY() > mi.y
								&& gc.getHeight() - Mouse.getY() < mi.y + mi.h) {
							if (mi.alpha < 201)
								mi.alpha += 5;
							if (mi.alpha > 200)
								mi.alpha = 200;
						} else {
							if (mi.alpha > 0)
								mi.alpha -= 5;
							if (mi.alpha < 0)
								mi.alpha = 0;
						}
					}
				} else {
					for (MenuButton mi : mb) {
						if (Mouse.getX() > mi.x && Mouse.getX() < mi.x + mi.w
								&& gc.getHeight() - Mouse.getY() > mi.y
								&& gc.getHeight() - Mouse.getY() < mi.y + mi.h) {
							if (mi.alpha < 201)
								mi.alpha += 5;
							if (mi.alpha > 200)
								mi.alpha = 200;
						} else {
							if (mi.alpha > 0)
								mi.alpha -= 5;
							if (mi.alpha < 0)
								mi.alpha = 0;
						}
					}
				}

			}
		}
	}

	public static void mineloaderInit() {
		System.out.println("Initiating MineLoader2D...");
		System.out.println("Loading testing mod Mod.class");
		try {
			MineLoader2D.class.getClassLoader().loadClass("Mod");
			Method m = MineLoader2D.getMethod("main", String[].class);
			String[] args = new String[0];
			try {
				m.invoke(null, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("[DEBUG] ModClass not found/NYI: testMod.class");
			System.out.println(e);
		}
	}

	public static void main(String[] args) throws SlickException {
		System.setProperty("org.lwjgl.librarypath", new File("lib/native/windows").getAbsolutePath());
		System.setProperty("java.library.path", new File("lib/native/windows").getAbsolutePath());
		System.out.println("{}- This game is made by Spideynn. 1 year of coding!");
		mineloaderInit();
		if (args.length > 0)
			MD.vsync = Integer.valueOf(args[0]);
		AppGameContainer appgc = new AppGameContainer(MD.game);
		appgc.setAlwaysRender(true);
		appgc.setDisplayMode(800, 600, false);
		appgc.setIcon("res/diamondpickaxe.png");
		appgc.start();
	}

}
