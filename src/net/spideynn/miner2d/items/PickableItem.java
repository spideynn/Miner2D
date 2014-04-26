package net.spideynn.miner2d.items;

import net.spideynn.miner2d.main.MD;
import net.spideynn.miner2d.other.GameTools;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.Serializable;

public class PickableItem implements Serializable {

	private static final long serialVersionUID = -7280476475468706820L;
	public Item pick;
	public float x, y, fx, fy, w, h;

	public int alpha = 200;

	public boolean decenting = false;

	public int tickX = 0;

	public PickableItem(Item pick, float x, float y, float fx, float fy,
			float w, float h) {
		this.pick = pick;
		this.x = x;
		this.y = y;
		this.fx = fx;
		this.fy = fy;
		this.w = w;
		this.h = h;
	}

	public PickableItem() {
		this.pick = null;
		this.x = 0f;
		this.y = 0f;
		this.fx = 0f;
		this.fy = 0f;
		this.w = 0f;
		this.h = 0f;
	}

	public boolean isColliding() {
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

	public void render(Graphics g, int light) {
		g.drawImage(MD.game.textureBin.get(pick.img), x - MD.game.s.camX, y
				- MD.game.s.camY, x + w - MD.game.s.camX, y + h
				- MD.game.s.camY, 0, 0, MD.game.tileWidth, MD.game.tileHeight,
				new Color(light, light, light, alpha));
	}

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
		if (y < 1f) {
			y = 2f;
			fy = fy * -0.5f;
		}
		if (x < 1f) {
			x = 2f;
			fx = fx * -0.5f;
		}
		if (x > MD.game.s.mapWidth * MD.game.tileWidth) {
			x = MD.game.s.mapWidth * MD.game.tileWidth - 1f;
			fx = fx * -0.5f;
		}
		if (y > MD.game.s.mapHeight * MD.game.tileHeight) {
			y = MD.game.s.mapHeight * MD.game.tileHeight - 1f;
			fy = fy * -0.5f;
		}

		if (decenting) {
			if (MD.game.s.mp.x + MD.game.s.mp.w / 2 > x + w / 2) {
				fx += 0.05f;
			}
			if (MD.game.s.mp.x + MD.game.s.mp.w / 2 < x + w / 2) {
				fx -= 0.05f;
			}
			if (MD.game.s.mp.y + MD.game.s.mp.h / 2 < y + h / 2) {
				fy -= 0.2f;
			}
			if (tickX < 0) {
				x++;
				y++;
				w -= 2;
				h -= 2;
				tickX = 10;
			} else
				tickX--;
			alpha -= 5;
			if (alpha < 0) {
				return false;
			} else
				return true;
		} else
			return true;
	}

}
