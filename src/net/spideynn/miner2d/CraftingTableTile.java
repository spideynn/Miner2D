package net.spideynn.miner2d;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class CraftingTableTile extends Tile {

	private static final long serialVersionUID = 9161704985136984017L;

	public Inventory crafting;
	public CraftingTableGui megui;

	public int x;
	public int y;

	public CraftingTableTile(int x, int y) {
		super(17, true, false, true, true);
		this.x = x;
		this.y = y;
		crafting = new Inventory(new Item[10]);
		megui = new CraftingTableGui(crafting);
	}

	public CraftingTableTile(Integer integer) {
		super(17, true, false, true, true);
		crafting = new Inventory(new Item[10]);
		megui = new CraftingTableGui(crafting);
	}

	@Override
	public String getType() {
		return "Crafting Table";
	}

	@Override
	public void render(Graphics g, int ix, int iy, int tx, int ty) {
		if (MD.game.light[ix][iy] > 0) {
			g.drawImage(MD.game.textureBin.get(MD.game.s.map[ix][iy].img), tx
					* MD.game.tileWidth - MD.game.s.camX, ty
					* MD.game.tileHeight - MD.game.s.camY, new Color(
					MD.game.light_r[ix][iy], MD.game.light_g[ix][iy],
					MD.game.light_b[ix][iy], MD.game.light[ix][iy]));
		} else {
			g.setColor(new Color(0, 0, 0, 255));
			g.fillRect(ix * MD.game.tileWidth - MD.game.s.camX, iy
					* MD.game.tileHeight - MD.game.s.camY, MD.game.tileWidth,
					MD.game.tileWidth);
		}
	}

	@Override
	public Item getDrop() {
		return new CraftingTableItem(1);
	}

	@Override
	public void breakTile() {
		crafting.drop(x * MD.game.tileWidth, y * MD.game.tileHeight);
	}

	@Override
	public void placeTile() {

	}

	@Override
	public void Serialized() {

	}

	@Override
	public void Deserialized() {

	}

	@Override
	public boolean RightClick() {
		MD.game.currentGui = megui;
		megui.resized((GameContainer) MD.game.container);
		MD.game.inventory = true;
		return false;
	}

	@Override
	public int getHardness() {
		if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
			switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
			case "Wooden Axe":
				return 30;
			case "Stone Axe":
				return 60;
			case "Iron Axe":
				return 80;
			case "Diamond Axe":
				return 120;
			}
		}
		return 10;
	}

	@Override
	public boolean tileUpdate() {
		return true;
	}

}
