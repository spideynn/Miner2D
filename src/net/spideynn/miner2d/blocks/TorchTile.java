package net.spideynn.miner2d.blocks;

import net.spideynn.miner2d.items.Item;
import net.spideynn.miner2d.main.MD;
import net.spideynn.miner2d.other.Light;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.Serializable;

public class TorchTile extends Tile implements Serializable {

	private static final long serialVersionUID = -4168139384034224464L;
	public Light myLight;

	public TorchTile(int x, int y) {
		super(6, false, true, false, false);
		myLight = new Light(x, y, 200, 5, 255, 255, 255);
	}

	public TorchTile() {
		super();
	}

	@Override
	public String getType() {
		return "Torch";
	}

	@Override
	public void render(Graphics g, int ix, int iy, int tx, int ty) {

		if (MD.game.light[ix][iy] > 0 && img != -1) {
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
		return new TorchItem(1);
	}

	@Override
	public void breakTile() {
		if (MD.game.s.lightoutset.contains(myLight)) {
			MD.game.s.lightoutset.remove(myLight);
			MD.game.reloadLights(-1);
		}
	}

	@Override
	public void placeTile() {
		if (!MD.game.s.lightoutset.contains(myLight)) {
			MD.game.s.lightoutset.add(myLight);
			MD.game.reloadLights(-1);
		}
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
		return 8192;
	}

	@Override
	public boolean tileUpdate() {
		img = -1;
		if (myLight.x - 1 > -1) {
			if (MD.game.s.map[myLight.x - 1][myLight.y] != null) {
				if (MD.game.s.map[myLight.x - 1][myLight.y].getType() != this
						.getType()) {
					img = 7;
				}
			}
		}
		if (myLight.x + 1 < MD.game.s.mapWidth) {
			if (MD.game.s.map[myLight.x + 1][myLight.y] != null) {
				if (MD.game.s.map[myLight.x + 1][myLight.y].getType() != this
						.getType()) {
					img = 8;
				}
			}
		}
		if (myLight.y + 1 < MD.game.s.mapHeight) {
			if (MD.game.s.map[myLight.x][myLight.y + 1] != null) {
				if (MD.game.s.map[myLight.x][myLight.y + 1].getType() != this
						.getType()) {
					img = 6;
				}
			}
		}
		if (img == -1)
			return false;
		else
			return true;
	}

}
