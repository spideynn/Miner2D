package net.spideynn.miner2d;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 1514632737086473050L;
	public int maxhealth;
	public boolean groundh = true;
	public int invsel;
	public boolean open;
	public float x;
	public float y;
	public float w;
	public float h;
	public float lasty = 16000;
	public int health;
	public float fy;
	public float fx = 0f;
	public boolean react = true;
	public Inventory inven;
	public float groundhit = 0f;

	public int jumpgrav = 0;

	public void takeHealth(int amount) {
		if (amount < 0) {
			MD.game.soundBin.get(8).play();
		} else if (amount > 0) {
			MD.game.soundBin.get(Runtime.rand.nextInt(8)).play();
		}
		if (!MD.game.godmode)
			health -= amount;
		MD.game.healthforce += 10f;
		if (health < 1) {
			inven.drop(x, y);
			MD.game.soundBin.get(10).play();
			Player p = new Player(MD.game.s.mapWidth * MD.game.tileWidth / 2,
					0f, 24, 24, 100);
			p.lasty = 0f;
			while (!p.isColliding())
				p.y++;
			p.y--;
			p.lasty = p.y;
			MD.game.s.mp = p;
		}
	}

	public boolean isCollidingLadder() {
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
						if (MD.game.s.map[tx][ty].isLadder) {
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
		for (LivingEntity le : MD.game.s.entities) {
			if (le.getTypeEntity().equals("Tnt")
					&& GameTools.GetCollision(x, y, w, h, le.x, le.y, le.w,
							le.h)) {
				return true;
			}
		}
		return false;
	}

	public void update(GameContainer gc) {
		if (!MD.game.inventory) {
			int curx = Mouse.getX();
			curx += MD.game.s.camOffsX;
			int cx = curx / MD.game.tileWidth;
			int cury = gc.getHeight() - Mouse.getY();
			cury += MD.game.s.camOffsY;
			int cy = cury / MD.game.tileHeight;
			MD.game.selectedx = (int) (cx + MD.game.s.camX / MD.game.tileWidth);
			MD.game.selectedy = (int) (cy + MD.game.s.camY / MD.game.tileHeight);

			if (!(MD.game.lx == MD.game.selectedx)
					|| !(MD.game.ly == MD.game.selectedy)) {
				MD.game.blockbreak = 8192;
			}

			MD.game.lx = MD.game.selectedx;
			MD.game.ly = MD.game.selectedy;
			if (inven.i[invsel] != null && inven.i[invsel].torchplace) {
				boolean dox = false;
				if (inven.i[invsel].rightplace) {
					if (MD.game.selectedx + 1 < MD.game.s.mapWidth) {
						if (MD.game.s.map[MD.game.selectedx + 1][MD.game.selectedy] != null
								&& inven.i[invsel] != null) {
							if (MD.game.s.map[MD.game.selectedx + 1][MD.game.selectedy]
									.getType() != inven.i[invsel].name) {
								dox = true;
							}
						}
					}
				}
				if (inven.i[invsel].leftplace) {
					if (!dox) {
						if (MD.game.selectedx - 1 > -1) {
							if (MD.game.s.map[MD.game.selectedx - 1][MD.game.selectedy] != null
									&& inven.i[invsel] != null) {
								if (MD.game.s.map[MD.game.selectedx - 1][MD.game.selectedy]
										.getType() != inven.i[invsel].name) {
									dox = true;
								}
							}
						}
					}
				}
				if (inven.i[invsel].downplace) {
					if (!dox) {
						if (MD.game.selectedy + 1 < MD.game.s.mapHeight) {
							if (MD.game.s.map[MD.game.selectedx][MD.game.selectedy + 1] != null
									&& inven.i[invsel] != null) {
								if (MD.game.s.map[MD.game.selectedx][MD.game.selectedy + 1]
										.getType() != inven.i[invsel].name) {
									dox = true;
								}
							}
						}
					}
				}
				if (inven.i[invsel].upplace) {
					if (!dox) {
						if (MD.game.selectedy - 1 < MD.game.s.mapHeight) {
							if (MD.game.s.map[MD.game.selectedx][MD.game.selectedy - 1] != null
									&& inven.i[invsel] != null) {
								if (MD.game.s.map[MD.game.selectedx][MD.game.selectedy - 1]
										.getType() != inven.i[invsel].name) {
									dox = true;
								}
							}
						}
					}
				}
				MD.game.drawselected = dox;
			} else {
				MD.game.drawselected = true;
			}
			MD.game.s.canreach = true;
			if (MD.game.s.canreach && MD.game.drawselected) {
				int spx = (int) (x / MD.game.tileWidth);
				int spy = (int) (y / MD.game.tileHeight);
				spx -= MD.game.selectedx;
				spy -= MD.game.selectedy;
				if (spx < 0)
					spx *= -1;
				if (spy < 0)
					spy *= -1;
				if (spx + spy > MD.game.reach) {
					MD.game.s.canreach = false;
				}
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)
				|| Keyboard.isKeyDown(Keyboard.KEY_X)) {
			if (react) {
				if (!MD.game.inCommand) {
					if (MD.game.physics) {
						y += 4;
						if (MD.game.airjump || isColliding()) {
							fy -= 5f;
							MD.game.soundBin.get(18).play();
						}
						y -= 4;
					} else {
						y += 4;
						if (MD.game.airjump || isColliding()) {
							jumpgrav = 40;
							MD.game.soundBin.get(18).play();
						}
						y -= 4;
					}
				}
				react = false;
			}
		} else
			react = true;

		MD.game.s.camX = x - gc.getWidth() / 2 + w / 2;
		MD.game.s.camY = y - gc.getHeight() / 2 + h / 2;
		if (MD.game.s.camX < 0)
			MD.game.s.camX = 0;
		if (MD.game.s.camY < 0)
			MD.game.s.camY = 0;
		if (MD.game.s.camX > MD.game.s.mapWidth * MD.game.tileWidth
				- gc.getWidth())
			MD.game.s.camX = MD.game.s.mapWidth * MD.game.tileWidth
					- gc.getWidth();
		if (MD.game.s.camY > MD.game.s.mapHeight * MD.game.tileHeight
				- gc.getHeight())
			MD.game.s.camY = MD.game.s.mapHeight * MD.game.tileHeight
					- gc.getHeight();
		MD.game.s.camOffsX += MD.game.s.camX - MD.game.s.lcamX;
		MD.game.s.camOffsY += MD.game.s.camY - MD.game.s.lcamY;
		while (true) {
			if (MD.game.s.camOffsX > MD.game.tileWidth) {
				float lef = MD.game.s.camOffsX - MD.game.tileWidth;
				MD.game.s.camOffsX = lef;
			}
			if (MD.game.s.camOffsX < 0f) {
				float lef = MD.game.s.camOffsX * -1f;
				MD.game.s.camOffsX = MD.game.tileWidth - lef;
			}
			if (!(MD.game.s.camOffsX > MD.game.tileWidth)
					&& !(MD.game.s.camOffsX < 0F)) {
				break;
			}
		}
		while (true) {
			if (MD.game.s.camOffsY > MD.game.tileHeight) {
				float lef = MD.game.s.camOffsY - MD.game.tileHeight;
				MD.game.s.camOffsY = lef;
			}
			if (MD.game.s.camOffsY < 0f) {
				float lef = MD.game.s.camOffsY * -1f;
				MD.game.s.camOffsY = MD.game.tileHeight - lef;
			}
			if (!(MD.game.s.camOffsY > MD.game.tileHeight)
					&& !(MD.game.s.camOffsY < 0F)) {
				break;
			}
		}
		MD.game.s.lcamX = MD.game.s.camX;
		MD.game.s.lcamY = MD.game.s.camY;
		if (MD.game.physics) {
			fy = fy * 0.99f;
			fx = fx * 0.99f;
			float lx = x;
			x += fx;
			if (!MD.game.inCommand) {
				if (Keyboard.isKeyDown(Keyboard.KEY_D)
						|| Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
					x += 2.5f;
					fx = fx * 0.88f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_A)
						|| Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
					x -= 2.5f;
					fx = fx * 0.88f;
				}
			}

			if (isColliding()) {
				x = lx;
				fx = fx * -0.5f;
			}
			float ly = y;
			if (!(isCollidingLadder())) {
				fy += 0.1f;
				y += fy;
			}
			if (isCollidingLadder()) {
				fy = 0f;
				if (!MD.game.inCommand) {
					if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
						y -= 1.5f;
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
						y += 1.5f;
					}
				}
			}
			if (isColliding()) {
				y = ly;
				fy = 0F;
				if (groundh) {
					int xll = (int) (groundhit / MD.game.tileHeight);
					if (xll > 4) {
						takeHealth(xll - 4);
					}
					groundhit = 0f;
					groundh = false;
				}
			} else
				groundh = true;
			groundhit += y - lasty > 0f ? y - lasty : 0f;
		} else {
			float lx = x;
			if (Keyboard.isKeyDown(Keyboard.KEY_D)
					|| Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				x += 2.5f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)
					|| Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				x -= 2.5f;
			}
			if (isColliding())
				x = lx;
			if (isCollidingLadder()) {

			} else {
				float ly = y;
				if (jumpgrav > 0) {
					jumpgrav--;
					y -= 7f;
				}
				y += 4;
				if (isColliding())
					y = ly;
			}
		}

		lasty = y;
		if (y < 0f)
			y = 0f;
		if (x < 0f)
			x = 0f;
		if (x > MD.game.s.mapWidth * MD.game.tileWidth - w)
			x = MD.game.s.mapWidth * MD.game.tileWidth - w;
		if (y > MD.game.s.mapHeight * MD.game.tileHeight - h)
			y = MD.game.s.mapHeight * MD.game.tileHeight - h;
	}

	public Player(float x, float y, float w, float h, int health) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.maxhealth = health;
		this.health = maxhealth;
		this.inven = new Inventory(new Item[45]);
		this.open = true;
		this.invsel = 30;
	}

	public Player() {
		this.x = 0f;
		this.y = 0f;
		this.w = 0f;
		this.h = 0f;
		this.maxhealth = 0;
		this.health = 0;
		this.inven = new Inventory(new Item[45]);
		this.open = true;
		this.invsel = 30;
	}

}
