package net.spideynn.miner2d.gui;

import java.io.Serializable;

import net.spideynn.miner2d.blocks.FurnaceTile;
import net.spideynn.miner2d.items.Item;
import net.spideynn.miner2d.items.NormalItem;
import net.spideynn.miner2d.main.MD;
import net.spideynn.miner2d.other.InvenLabel;
import net.spideynn.miner2d.other.InvenSlot;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class FurnaceGui extends Gui implements Serializable {

	private static final long serialVersionUID = 6878901500188385519L;
	public Item holding = null;
	public int ix = 0;
	public int iy = 0;

	public int sw = 0;
	public int sh = 0;

	public Inventory hook;

	public FurnaceTile parent;

	public FurnaceGui() {

	}

	public FurnaceGui(Inventory hook, FurnaceTile parent) {
		this.hook = hook;
		this.parent = parent;
	}

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
		float pixtot = parent.heat / 204f;
		g.setColor(new Color(255, 128, 0, 255));
		g.fillRect(gc.getWidth() / 2 - 630f / 2 + 140, gc.getHeight() / 2
				- 480f / 2 + 121 - pixtot + 40, 40, pixtot);
		g.setColor(new Color(0, 255, 0, 255));
		g.fillRect(gc.getWidth() / 2 - 630f / 2 + 182, gc.getHeight() / 2
				- 480f / 2 + 121, parent.progress / 204, 40);
		if (holding != null) {
			g.drawImage(MD.game.textureBin.get(holding.img), Mouse.getX() - ix
					+ 5, gc.getHeight() - Mouse.getY() - iy + 5);
			if (holding.stack > 1) {
				String d = String.valueOf(holding.stack);
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
		if (button == 0) {
			for (InvenSlot c : guis_s) {
				if (x > c.x && x < c.x + c.w && y > c.y && y < c.y + c.h) {
					if (holding != null) {
						if (c.hook.i[c.slot] == null) {
							c.hook.i[c.slot] = holding;
							holding = null;
						} else {
							if (c.hook.i[c.slot].name.equals(holding.name)) {
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
								holding = slotx;
								ix = Mouse.getX() - c.x;
								iy = sh - Mouse.getY() - c.y;
							}
						}
					} else {
						if (c.hook.i[c.slot] != null) {
							holding = c.hook.i[c.slot];
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
					break;
				}
			}
		}
		MD.game.reloadCrafting();
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
