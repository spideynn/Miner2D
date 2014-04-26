package net.spideynn.miner2d.blocks;

import net.spideynn.miner2d.items.CoalItem;
import net.spideynn.miner2d.items.Item;
import net.spideynn.miner2d.main.MD;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.Serializable;

public class CoalOre extends Tile implements Serializable {

	private static final long serialVersionUID = 6578222707082157350L;

	public CoalOre() {
		super(4, true, false, true, true);
	}

	@Override
	public String getType() {
		return "Coal Ore";
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
				return new CoalItem(1);
			case "Stone Pickaxe":
				return new CoalItem(1);
			case "Iron Pickaxe":
				return new CoalItem(1);
			case "Diamond Pickaxe":
				return new CoalItem(1);
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
				return 30;
			case "Stone Pickaxe":
				return 60;
			case "Iron Pickaxe":
				return 80;
			case "Diamond Pickaxe":
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
