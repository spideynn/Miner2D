package net.spideynn.miner2d.blocks;

import net.spideynn.miner2d.gui.ChestGui;
import net.spideynn.miner2d.gui.Inventory;
import net.spideynn.miner2d.items.Item;
import net.spideynn.miner2d.main.MD;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.io.Serializable;

public class ChestTile extends Tile implements Serializable {

	private static final long serialVersionUID = -2276445059398829142L;

	public Inventory chest;
	public ChestGui chestgui;

	public int x;
	public int y;

	public ChestTile(int x, int y) {
		super(16, true, true, true, false);
		this.x = x;
		this.y = y;
		chest = new Inventory(new Item[40]);
		chestgui = new ChestGui(chest);
	}

	public ChestTile() {
		super(16, true, true, true, false);
		this.x = 0;
		this.y = 0;
		chest = new Inventory(new Item[40]);
		chestgui = new ChestGui(chest);
	}

	@Override
	public String getType() {
		return "Chest";
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
		return new ChestItem(1);
	}

	@Override
	public void breakTile() {
		chest.drop(x * MD.game.tileWidth, y * MD.game.tileHeight);
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
		MD.game.currentGui = chestgui;
		chestgui.resized((GameContainer) MD.game.container);
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
