package net.spideynn.miner2d.gui;

import java.io.Serializable;

import net.spideynn.miner2d.items.Item;
import net.spideynn.miner2d.items.NormalItem;
import net.spideynn.miner2d.main.MD;
import net.spideynn.miner2d.other.CraftingRecipe;
import net.spideynn.miner2d.other.InvenLabel;
import net.spideynn.miner2d.other.InvenSlot;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class CraftingTableGui extends Gui implements Serializable {

	public Item holding = null;
	public int ix = 0;
	public int iy = 0;

	public Inventory hook;

	public int sw = 0;
	public int sh = 0;

	public CraftingTableGui(Inventory chest) {
		sw = 0;
		sh = 0;
		hook = chest;
	}

	public CraftingTableGui() {
		sw = 0;
		sh = 0;
	}

	private static final long serialVersionUID = -7301460994349206324L;

	@Override
	public void render(Graphics g, GameContainer gc) {
		g.setLineWidth(1f);
		g.setColor(new Color(0, 96, 255, 128));
		g.fillRoundRect(gc.getWidth() / 2 - 650f / 2,
				gc.getHeight() / 2 - 500f / 2, 650f, 500f, 10);
		g.setColor(Color.black);
		g.drawRoundRect(gc.getWidth() / 2 - 650f / 2,
				gc.getHeight() / 2 - 500f / 2, 650f, 500f, 10);
		g.setColor(new Color(212, 212, 212, 255));
		g.fillRoundRect(gc.getWidth() / 2 - 630f / 2,
				gc.getHeight() / 2 - 480f / 2, 630f, 480f, 10);
		g.setColor(new Color(127, 127, 127, 255));
		for (InvenSlot ic : this.guis_s) {
			ic.render(g, gc);
		}
		for (InvenSlot ic : this.guis_s) {
			ic.renderTop(g, gc);
		}
		for (InvenLabel ic : this.guis_l) {
			ic.render(g, gc);
		}
		if (this.holding != null) {
			g.drawImage(MD.game.textureBin.get(holding.img), Mouse.getX() - ix
					+ 5, gc.getHeight() - Mouse.getY() - iy + 5);
			if (this.holding.stack > 1) {
				String d = String.valueOf(this.holding.stack);
				g.setColor(new Color(0, 0, 0, 192));
				g.fillRect(Mouse.getX() - ix + 5 + 40 - g.getFont().getWidth(d)
						- 2, gc.getHeight() - Mouse.getY() - iy + 40
						- g.getFont().getHeight(d) - 2,
						g.getFont().getWidth(d), g.getFont().getHeight(d));
				g.setColor(new Color(0, 128, 255, 255));
				g.drawString(d, Mouse.getX() - ix + 5 + 40
						- g.getFont().getWidth(d) - 2,
						gc.getHeight() - Mouse.getY() - iy + 40
								- g.getFont().getHeight(d) - 2);
			}
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		boolean refreshInven = true;
		if (button == 0) {
			for (InvenSlot c : guis_s) {
				if (x > c.x && x < c.x + c.w && y > c.y && y < c.y + c.h) {
					if (holding != null) {
						if (c.hook == hook && c.slot == 9) {
							if (c.hook.i[9] != null) {
								if (c.hook.i[9].name.equals(holding.name)) {
									int takenaway = 0;
									for (int i = 0; i < c.hook.i[9].stack; i++) {
										if (holding.stack < holding.maxstack) {
											holding.stack++;
											takenaway++;
										} else {
											break;
										}
									}
									c.hook.i[9].stack -= takenaway;
									if (c.hook.i[9].stack < 1) {
										c.hook.i[9] = null;
										craft();
									} else {
										refreshInven = false;
									}
								}
							}
						} else {
							if (c.hook.i[c.slot] == null) {
								c.hook.i[c.slot] = holding;
								holding = null;
							} else {
								if (c.hook.i[c.slot].name == holding.name) {
									int added = 0;
									for (int i = 0; i < holding.stack; i++) {
										if (c.hook.i[c.slot].stack < c.hook.i[c.slot].maxstack) {
											c.hook.i[c.slot].stack++;
											added++;
										} else
											break;
									}
									holding.stack -= added;
									if (holding.stack < 1) {
										holding = null;
									}
								} else {
									Item slotx = c.hook.i[c.slot];
									c.hook.i[c.slot] = holding;
									if (c.hook == hook && c.slot == 9)
										craft();
									holding = slotx;
									ix = Mouse.getX() - c.x;
									iy = sh - Mouse.getY() - c.y;
								}
							}
						}
					} else {
						if (c.hook.i[c.slot] != null) {
							holding = c.hook.i[c.slot];
							if (c.hook == hook && c.slot == 9)
								craft();
							ix = Mouse.getX() - c.x;
							iy = sh - Mouse.getY() - c.y;
							c.hook.i[c.slot] = null;
							if (c.slot == 44) {
								MD.game.craft();
							}
						}
					}
					break;
				}
			}
		} else if (button == 1) {
			for (InvenSlot c : guis_s) {
				if (x > c.x && x < c.x + c.w && y > c.y && y < c.y + c.h) {
					if (!(c.hook == hook && c.slot == 9)) {
						if (holding != null) {
							if (c.hook.i[c.slot] == null) {
								Item cop = holding.copyitem();
								cop.stack = 1;
								c.hook.i[c.slot] = cop;
								holding.stack--;
								if (holding.stack < 1) {
									holding = null;
								}
							} else {
								if (c.hook.i[c.slot].name == holding.name) {
									if (c.hook.i[c.slot].stack < c.hook.i[c.slot].maxstack) {
										c.hook.i[c.slot].stack++;
										holding.stack--;
										if (holding.stack < 1) {
											holding = null;
										}
									}
								} else {
									Item slotx = c.hook.i[c.slot];
									c.hook.i[c.slot] = holding;
									holding = slotx;
									if (c.hook == hook && c.slot == 9)
										craft();
									ix = Mouse.getX() - c.x;
									iy = sh - Mouse.getY() - c.y;
								}
							}
						} else {
							if (c.hook.i[c.slot] != null) {
								if (c.hook.i[c.slot].stack == 1) {
									holding = c.hook.i[c.slot];
									c.hook.i[c.slot] = null;
								} else {
									int half = (int) (c.hook.i[c.slot].stack / 2);
									if (c.hook == hook && c.slot == 9)
										craft();
									holding = new NormalItem(half,
											c.hook.i[c.slot].maxstack,
											c.hook.i[c.slot].img,
											c.hook.i[c.slot].name,
											c.hook.i[c.slot].smeltable,
											c.hook.i[c.slot].smeltlast);
									ix = Mouse.getX() - c.x;
									iy = sh - Mouse.getY() - c.y;
									c.hook.i[c.slot].stack -= half;
									if (c.hook.i[c.slot].stack < 1) {
										c.hook.i[c.slot] = null;
									}
								}
							}
						}
					}

					break;
				}
			}
		}
		if (refreshInven)
			reloadCrafting();
	}

	public void craft() {
		for (int i = 0; i < 9; i++) {
			if (this.hook.i[i] != null) {
				this.hook.i[i].stack--;
				if (this.hook.i[i].stack < 1) {
					this.hook.i[i] = null;
				}
			}
		}
	}

	public void reloadCrafting() {
		hook.i[9] = null;
		String[][] current = new String[3][3];
		for (int ix = 0; ix < 3; ix++) {
			for (int iy = 0; iy < 3; iy++) {
				current[ix][iy] = "";
			}
		}
		if (hook.i[0] != null)
			current[0][0] = hook.i[0].name;
		if (hook.i[1] != null)
			current[1][0] = hook.i[1].name;
		if (hook.i[2] != null)
			current[2][0] = hook.i[2].name;
		if (hook.i[3] != null)
			current[0][1] = hook.i[3].name;
		if (hook.i[4] != null)
			current[1][1] = hook.i[4].name;
		if (hook.i[5] != null)
			current[2][1] = hook.i[5].name;
		if (hook.i[6] != null)
			current[0][2] = hook.i[6].name;
		if (hook.i[7] != null)
			current[1][2] = hook.i[7].name;
		if (hook.i[8] != null)
			current[2][2] = hook.i[8].name;
		for (CraftingRecipe cp : MD.game.crafts) {
			boolean brk1 = false;
			for (int ix_ = 0; ix_ < cp.misx.length; ix_++) {
				int ax = cp.misx[ix_];
				int ay = cp.misy[ix_];
				String[][] currentcompare = new String[3][3];
				for (int ixx = 0; ixx < 3; ixx++) {
					for (int iyx = 0; iyx < 3; iyx++) {
						int tx = ixx + ax;
						int ty = iyx + ay;
						if (tx > -1 && tx < 3 && ty > -1 && ty < 3) {
							currentcompare[tx][ty] = cp.crafting[ixx][iyx];
						}
					}
				}
				for (int ixx = 0; ixx < 3; ixx++) {
					for (int iyx = 0; iyx < 3; iyx++) {
						if (currentcompare[ixx][iyx] == null) {
							currentcompare[ixx][iyx] = "";
						}
					}
				}
				if (current[0][0].equals(currentcompare[0][0])) {
					if (current[1][0].equals(currentcompare[1][0])) {
						if (current[2][0].equals(currentcompare[2][0])) {
							if (current[0][1].equals(currentcompare[0][1])) {
								if (current[1][1].equals(currentcompare[1][1])) {
									if (current[2][1]
											.equals(currentcompare[2][1])) {
										if (current[0][2]
												.equals(currentcompare[0][2])) {
											if (current[1][2]
													.equals(currentcompare[1][2])) {
												if (current[2][2]
														.equals(currentcompare[2][2])) {
													hook.i[9] = cp.getItem();
													brk1 = true;
													break;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			if (brk1)
				break;
		}
	}

	@Override
	public void resized(GameContainer gc) {
		sw = gc.getWidth();
		sh = gc.getHeight();
		this.guis_s.clear();
		this.guis_l.clear();
		float tranx = gc.getWidth() / 2 - 630f / 2;
		float trany = gc.getHeight() / 2 - 480f / 2;
		float x = 630f / 2 - 450f / 2;
		float y = 460 - 180;
		int slot = 0;
		for (int iy = 0; iy < 4; iy++) {
			for (int ix = 0; ix < 10; ix++) {
				this.guis_s.add(new InvenSlot((int) (tranx + x + ix * 45),
						(int) (trany + y), 40, 40, slot, MD.game.s.mp.inven));

				slot++;
			}
			x = 630f / 2 - 450f / 2;
			y += 45;
		}
		x = 630f / 2 - 450f / 2;
		y = 460 - 380;
		slot = 0;
		for (int iy = 0; iy < 3; iy++) {
			for (int ix = 0; ix < 3; ix++) {
				this.guis_s.add(new InvenSlot((int) (tranx + x + ix * 45),
						(int) (trany + y), 40, 40, slot, hook));
				slot++;
			}
			x = 630f / 2 - 450f / 2;
			y += 45;
		}
		this.guis_s.add(new InvenSlot((int) (tranx + x + 45 * 3), (int) (trany
				+ y - 45 * 2), 40, 40, 9, hook));
		this.guis_l.add(new InvenLabel((int) (tranx + 630f / 2 - 450f / 2),
				(int) (trany + 260), "Inventory"));
		this.guis_l.add(new InvenLabel((int) (tranx + 630f / 2 - 450f / 2),
				(int) (trany + 60), "Crafting"));

	}

}
