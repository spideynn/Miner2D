package net.spideynn.miner2d;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.Serializable;

public class SaplingTile extends Tile implements Serializable {

	private static final long serialVersionUID = -7426700668462552715L;
	public Random rand = new Random();
	public int x;
	public int y;
	public Object growth = null;
	public int growleft = 8192 + rand.nextInt(8192);

	public SaplingTile(int x, int y) {
		super(13, false, true, false, false);
		this.x = x;
		this.y = y;
	}

	public SaplingTile() {
		super();
	}

	@Override
	public String getType() {
		return "Sapling";
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
		return new SaplingItem(1);
	}

	@Override
	public void breakTile() {
		if (MD.game.tasks.contains(growth))
			MD.game.tasks.remove(growth);
	}

	@Override
	public void placeTile() {
		setGrowth();
		if (!MD.game.tasks.contains(growth))
			MD.game.tasks.add((Runnable) growth);
	}

	@Override
	public void Serialized() {
		if (MD.game.tasks.contains(growth))
			MD.game.tasks.remove(growth);
		growth = null;
	}

	private void setGrowth() {
		growth = new Runnable() {
			@Override
			public void run() {
				if (MD.game.light[x][y] > 200)
					growleft--;
				if (growleft < 1) {
					MD.game.s.map[x][y] = null;
					int height = y;
					int ix = x;
					for (int i = 0; i < 3 + rand.nextInt(4); i++) {
						if (height > -1 && height < MD.game.s.mapHeight
								&& ix > -1 && ix < MD.game.s.mapWidth) {
							MD.game.s.map[ix][height] = new WoodTile();
							height--;
						}
					}
					for (int ixz = -1 + rand.nextInt(2) * -1; ixz < 1 + rand
							.nextInt(4) + 2; ixz++) {
						for (int iyz = -1 + rand.nextInt(2) * -1; iyz < 1 + rand
								.nextInt(4); iyz++) {
							if (ix + ixz > -1 && ix + ixz < MD.game.s.mapWidth
									&& height + iyz > -1
									&& height + iyz < MD.game.s.mapHeight) {
								if (MD.game.s.map[ix + ixz][height + iyz] == null) {
									MD.game.s.map[ix + ixz][height + iyz] = new LeafTile();
								}
							}
						}
					}
					MD.game.remtask.add((Runnable) growth);
				}
			}
		};
	}

	@Override
	public void Deserialized() {
		setGrowth();
		if (!MD.game.tasks.contains(growth))
			MD.game.tasks.add((Runnable) growth);
	}

	@Override
	public boolean RightClick() {
		if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null
				&& MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name
						.equals("Bone Meal")) {
			growleft = 0;
			return true;
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
