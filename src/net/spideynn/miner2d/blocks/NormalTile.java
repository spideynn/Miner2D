package net.spideynn.miner2d.blocks;

import net.spideynn.miner2d.items.Item;
import net.spideynn.miner2d.items.NormalItem;
import net.spideynn.miner2d.main.MD;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.Serializable;

public class NormalTile extends Tile implements Serializable {

	private static final long serialVersionUID = -6133637142665569193L;
	boolean drops = false;

	public String name;

	public NormalTile(int img, boolean drops, String name) {
		super(img, true, false, true, true);
		this.drops = drops;
		this.name = name;
	}

	public NormalTile() {
		super();
	}

	@Override
	public String getType() {
		return name;
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
		if (getType().equals("Stone")) {
			if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
				switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
				case "Wooden Pickaxe":
					return new CobbleStoneItem(1);
				case "Stone Pickaxe":
					return new CobbleStoneItem(1);
				case "Iron Pickaxe":
					return new CobbleStoneItem(1);
				case "Diamond Pickaxe":
					return new CobbleStoneItem(1);
				default:
					return null;
				}
			} else {
				return null;
			}
		}
		if (drops) {
			return new NormalItem(1, 64, img, getType(), false, 0);
		} else {
			return null;
		}
	}

	@Override
	public void breakTile() {

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
		return false;
	}

	@Override
	public int getHardness() {
		switch (getType()) {
		case "Dirt":
			if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
				switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
				case "Wooden Shovel":
					return 130;
				case "Stone Shovel":
					return 150;
				case "Iron Shovel":
					return 350;
				case "Diamond Shovel":
					return 600;
				}
			}
			return 100;
		case "Stone":
			if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
				switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
				case "Wooden Pickaxe":
					return 30;
				case "Stone Pickaxe":
					return 70;
				case "Iron Pickaxe":
					return 100;
				case "Diamond Pickaxe":
					return 180;
				}
			}
			return 10;
		case "Bedrock":
			return 0;
		case "Grass":
			if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
				switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
				case "Wooden Shovel":
					return 130;
				case "Stone Shovel":
					return 150;
				case "Iron Shovel":
					return 350;
				case "Diamond Shovel":
					return 600;
				}
			}
			return 100;
		case "Wooden Planks":
			if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
				switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
				case "Wooden Axe":
					return 40;
				case "Stone Axe":
					return 80;
				case "Iron Axe":
					return 120;
				case "Diamond Axe":
					return 240;
				}
			}
			return 20;
		}
		return 0;
	}

	@Override
	public boolean tileUpdate() {
		return true;
	}

}
