package net.spideynn.miner2d.entities;

import java.io.Serializable;

import net.spideynn.miner2d.items.PickableItem;
import net.spideynn.miner2d.items.RawPorkchopItem;
import net.spideynn.miner2d.main.MD;
import net.spideynn.miner2d.main.Runtime;
import net.spideynn.miner2d.other.GameTools;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class EntityPig extends LivingEntity implements Serializable {

	private static final long serialVersionUID = 563225355819558856L;

	public float redness = 0f;
	public float forceredness = 0f;

	public EntityPig(float x, float y, int health) {
		super(x, y, 24f, 24f, health);
	}

	public EntityPig() {
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

	@Override
	public void render(Graphics g) {
		if (this.x + this.w > MD.game.s.camX
				&& this.x < MD.game.s.camX + MD.game.container.getWidth()
				&& this.y + this.h > MD.game.s.camY
				&& this.y < MD.game.s.camY + MD.game.container.getHeight()) {
			g.setColor(new Color(
					MD.game.light[(int) (this.x / MD.game.tileWidth)][(int) (this.y / MD.game.tileHeight)],
					0,
					(int) (MD.game.light[(int) (this.x / MD.game.tileWidth)][(int) (this.y / MD.game.tileHeight)] / 2.56),
					255));
			g.fillRect(this.x - MD.game.s.camX, this.y - MD.game.s.camY,
					this.w, this.h);
			g.setColor(new Color(255, 0, 0, (int) (redness)));
			g.fillRect(this.x - MD.game.s.camX, this.y - MD.game.s.camY,
					this.w, this.h);
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
		forceredness *= 0.99f;
		forceredness -= 0.04f;
		float lr = redness;
		redness += forceredness;
		if (redness < 0f || redness > 255f) {
			redness = lr;
			forceredness *= -0.5f;
		}
		if (this.health < 1) {
			for (int i = 0; i < Runtime.rand.nextInt(3); i++) {
				MD.game.s.items.add(new PickableItem(new RawPorkchopItem(1),
						this.x, this.y, Runtime.rand.nextFloat() * 5f - 2.5f,
						Runtime.rand.nextFloat() * -2f, 16f, 16f));
			}
			MD.game.soundBin.get(10).play();
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void hit(int amount) {
		MD.game.soundBin.get(Runtime.rand.nextInt(8)).play();
		if (amount == -1) {
			if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
				switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
				case "Wooden Sword":
					this.health -= 4;
					break;
				case "Stone Sword":
					this.health -= 12;
					break;
				case "Iron Sword":
					this.health -= 15;
					break;
				case "Diamond Sword":
					this.health -= 25;
					break;
				default:
					this.health -= 2;
					break;

				}
			} else {
				this.health -= 2;
			}
		} else {
			this.health -= amount;
		}

		forceredness += 8f;
		if (amount == -1) {
			this.fy -= 3f;
			if (this.x > MD.game.s.mp.x) {
				this.fx += 2f;
			} else {
				this.fx -= 2f;
			}
		}
	}

	@Override
	public String getTypeEntity() {
		return "Pig";
	}

}
