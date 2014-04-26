package net.spideynn.miner2d.blocks;

import java.io.Serializable;

import net.spideynn.miner2d.items.EmeraldItem;
import net.spideynn.miner2d.items.Item;
import net.spideynn.miner2d.main.MD;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class EmeraldOre extends Tile implements Serializable {

	private static final long serialVersionUID = -8131267294591539L;
	
	@Override
	public String getType() {
		return "Emerald Ore";
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
		if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
			switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
			case "Wooden Pickaxe":
				return null;
			case "Stone Pickaxe":
				return null;
			case "Iron Pickaxe":
				return new EmeraldItem(1);
			case "Diamond Pickaxe":
				return new EmeraldItem(1);
			case "Ruby Pickaxe":
				return new EmeraldItem(1);
			case "Emerald Pickaxe":
				return new EmeraldItem(1);
			}
		}
		return null;
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
		if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
			switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
			case "Wooden Pickaxe":
				return 10000000;
			case "Stone Pickaxe":
				return 10000000;
			case "Iron Pickaxe":
				return 120;
			case "Diamond Pickaxe":
				return 200;
			}
		}
		return 10000000;
	}

	@Override
	public boolean tileUpdate() {
		return true;
	}

}
