package net.spideynn.miner2d;

import java.io.Serializable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class TntTile extends Tile implements Serializable {

	private static final long serialVersionUID = -7022327364872606356L;

	public int x = 0;
	public int y = 0;

	public boolean explodeX = false;

	public TntTile(int x, int y) {
		super(41, true, false, true, true);
		this.x = x;
		this.y = y;
		this.isInflatable = true;
	}

	public TntTile() {
		super();
	}

	@Override
	public String getType() {
		return "TNT";
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
		return new TntItem(1);
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
		// MD.game.createExplosion(x, y, 5, true, true, this);
		MD.game.s.map[x][y] = null;
		if (explodeX) {
			MD.game.s.entities
					.add(new EntityTnt(x * MD.game.tileWidth, y
							* MD.game.tileHeight, 50 + Runtime.rand
							.nextInt(100), this));
			explodeX = false;
		} else {
			MD.game.s.entities
					.add(new EntityTnt(x * MD.game.tileWidth, y
							* MD.game.tileHeight, 200 + Runtime.rand
							.nextInt(200), this));
		}
		return false;
	}

	@Override
	public int getHardness() {
		return 8192;
	}

	@Override
	public boolean tileUpdate() {
		return true;
	}

}
