package net.spideynn.miner2d;

import org.newdawn.slick.*;

import org.lwjgl.input.Mouse;

import java.io.Serializable;

public class InvenSlot implements InvenMethods, Serializable {

	private static final long serialVersionUID = -2361169259713072225L;
	public int x, y, w, h, slot;
	public Inventory hook;

	public InvenSlot(int x, int y, int w, int h, int slot, Inventory hook) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.slot = slot;
		this.hook = hook;
	}

	public InvenSlot() {
		this.x = 0;
		this.y = 0;
		this.w = 40;
		this.h = 40;
		this.slot = 0;
		this.hook = null;
	}

	@Override
	public void render(Graphics g, GameContainer gc) {
		g.setLineWidth(1f);
		g.setColor(new Color(127, 127, 127, 255));
		g.drawRect(x, y, w, h);

		if (this.hook.i[this.slot] != null) {
			g.drawImage(MD.game.textureBin.get(this.hook.i[this.slot].img),
					x + 4, y + 4);
			if (this.hook.i[this.slot].stack > 1) {
				String d = String.valueOf(this.hook.i[this.slot].stack);
				g.setColor(new Color(0, 0, 0, 192));
				g.fillRect(x + 40 - g.getFont().getWidth(d) - 2, y + 40
						- g.getFont().getHeight(d) - 2,
						g.getFont().getWidth(d), g.getFont().getHeight(d));
				g.setColor(new Color(0, 128, 255, 255));
				g.drawString(d, x + 40 - g.getFont().getWidth(d) - 2, y + 40
						- g.getFont().getHeight(d) - 2);
			}
			if (this.hook.i[this.slot].tool) {
				g.setColor(new Color(0, 0, 0, 255));
				g.fillRect(x, y + 40f - 5f, 40f, 4f);
				g.setColor(new Color(0, 255, 0, 255));
				g.fillRect(x, y + 40f - 5f,
						(int) (this.hook.i[slot].usage / 204f), 4f);
			}
		}
	}

	@Override
	public void renderTop(Graphics g, GameContainer gc) {
		if (Mouse.getX() > x && Mouse.getX() < x + w
				&& gc.getHeight() - Mouse.getY() > y
				&& gc.getHeight() - Mouse.getY() < y + h) {
			g.setColor(new Color(255, 255, 255, 96));
			g.fillRect(x, y, w, h);
			if (this.hook.i[this.slot] != null) {
				String s = this.hook.i[this.slot].name + "\nIs Fuel: "
						+ String.valueOf(this.hook.i[this.slot].smeltable)
						+ "\nSmelt Last: " + this.hook.i[this.slot].smeltlast;
				g.setColor(new Color(0, 92, 192, 128));
				g.fillRect(Mouse.getX() + 10, gc.getHeight() - Mouse.getY()
						+ 10, g.getFont().getWidth(s) + 1, g.getFont()
						.getHeight(s) + 1);
				g.setColor(new Color(255, 255, 255, 255));
				g.drawString(s, Mouse.getX() + 10,
						gc.getHeight() - Mouse.getY() + 10);
			}
		}
	}

}
