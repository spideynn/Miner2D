package net.spideynn.miner2d.entities;

import java.io.Serializable;

import net.spideynn.miner2d.blocks.Tile;
import net.spideynn.miner2d.main.MD;
import net.spideynn.miner2d.other.GameTools;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class EntityTnt extends LivingEntity implements Serializable {

	private static final long serialVersionUID = -5599792152206583907L;

	public int alpha = 0;
	public boolean d = true;
	public Tile parent;
	public int xplode = 0;

	public EntityTnt() {
		super();
	}

	public boolean isColliding() {
		if (x < 0f)
			return true;
		if (y < 0f)
			return true;
		if (x > MD.game.s.mapWidth * MD.game.tileWidth)
			return true;
		if (y > MD.game.s.mapHeight * MD.game.tileHeight)
			return true;
		int sx = (int) x / MD.game.tileWidth;
		int sy = (int) y / MD.game.tileHeight;
		for (int ix = -2; ix < 3; ix++) {
			boolean brk = false;
			for (int iy = -2; iy < 3; iy++) {
				int tx = sx + ix;
				int ty = sy + iy;
				if (tx > -1 && ty > -1 && tx < MD.game.s.mapWidth
						&& ty < MD.game.s.mapHeight) {
					if (MD.game.s.map[tx][ty] != null
							&& GameTools.GetCollision(x, y, w, h, tx
									* MD.game.tileWidth, ty
									* MD.game.tileHeight, MD.game.tileWidth,
									MD.game.tileHeight)) {
						if (MD.game.s.map[tx][ty].collision) {
							return true;
						}
					}
				}
			}
			if (brk)
				break;
		}
		return false;
	}

	public EntityTnt(float x, float y, int health, Tile parent) {
		super(x, y, MD.game.tileWidth, MD.game.tileHeight, 1);
		this.parent = parent;
		this.xplode = health;
	}

	@Override
	public void render(Graphics g) {
		if (this.x + this.w > MD.game.s.camX
				&& this.x < MD.game.s.camX + MD.game.container.getWidth()
				&& this.y + this.h > MD.game.s.camY
				&& this.y < MD.game.s.camY + MD.game.container.getHeight()) {
			int ix = (int) (this.x / MD.game.tileWidth);
			int iy = (int) (this.y / MD.game.tileHeight);
			g.drawImage(MD.game.textureBin.get(41), x - MD.game.s.camX, y
					- MD.game.s.camY, x - MD.game.s.camX + MD.game.tileWidth, y
					- MD.game.s.camY + MD.game.tileHeight, 0f, 0f,
					MD.game.tileWidth, MD.game.tileHeight, new Color(
							MD.game.light_r[ix][iy], MD.game.light_g[ix][iy],
							MD.game.light_b[ix][iy], MD.game.light[ix][iy]));
			g.setColor(new Color(255, 255, 255, alpha));
			g.fillRect(x - MD.game.s.camX, y - MD.game.s.camY, w, h);
		}
	}

	@Override
	public boolean update() {
		fx = fx * 0.99f;
		fy = fy * 0.99f;
		fy += 0.1f;
		float lx = x;
		x += fx;
		if (isColliding()) {
			x = lx;
			fx = fx * -0.5f;
		}
		float ly = y;
		y += fy;
		if (isColliding()) {
			y = ly;
			fy = fy * -0.5f;
		}
		if (d) {
			alpha++;
			if (alpha > 199) {
				d = false;
			}
		} else {
			if (alpha > 0) {
				alpha--;
			} else {
				d = true;
			}
		}
		this.xplode--;
		if (this.xplode < 1) {
			MD.game.createExplosion((int) (this.x / MD.game.tileWidth),
					(int) (this.y / MD.game.tileHeight), 4, true, true, parent);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void hit(int amount) {

	}

	@Override
	public String getTypeEntity() {
		return "Tnt";
	}

}
