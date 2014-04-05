package net.spideynn.miner2d;

import java.io.Serializable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class CobbleStoneTile extends Tile implements Serializable {

	private static final long serialVersionUID = 7542920515971042381L;

	public CobbleStoneTile() {
		super(20, true, false, true, true);
	}

	@Override
	public String getType() {
		return null;
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
		return new CobbleStoneItem(1);
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
				return 70;
			case "Iron Pickaxe":
				return 100;
			case "Diamond Pickaxe":
				return 180;
			}
		}
		return 10;
	}

	@Override
	public boolean tileUpdate() {
		return true;
	}

}
