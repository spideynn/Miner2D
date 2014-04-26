package net.spideynn.miner2d.blocks;

import net.spideynn.miner2d.items.Item;
import net.spideynn.miner2d.main.MD;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Random;
import java.io.Serializable;

public class LeafTile extends Tile implements Serializable {

	private static final long serialVersionUID = 7112155102756621932L;
	Random rand = new Random();

	public LeafTile() {
		super(10, true, true, true, false);
	}

	@Override
	public String getType() {
		return blockDta.getName(img);
	}

	@Override
	public void render(Graphics g, int ix, int iy, int tx, int ty) {
		if (MD.game.light[ix][iy] > 0) {
			g.drawImage(MD.game.textureBin.get(MD.game.s.map[ix][iy].img), tx
					* MD.game.tileWidth - MD.game.s.camX, ty
					* MD.game.tileHeight - MD.game.s.camY, new Color(
					MD.game.light[ix][iy], MD.game.light[ix][iy],
					MD.game.light[ix][iy], 255));
		} else {
			g.setColor(new Color(0, 0, 0, 255));
			g.fillRect(ix * MD.game.tileWidth - MD.game.s.camX, iy
					* MD.game.tileHeight - MD.game.s.camY, MD.game.tileWidth,
					MD.game.tileWidth);
		}
	}

	@Override
	public Item getDrop() {
		if (rand.nextInt(100) > 80) {
			return new SaplingItem(1);
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
		if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
			switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
			case "Wooden Sword":
				return 180;
			case "Stone Sword":
				return 240;
			case "Iron Sword":
				return 360;
			case "Diamond Sword":
				return 480;
			}
		}
		return 120;
	}

	@Override
	public boolean tileUpdate() {
		return true;
	}

}
