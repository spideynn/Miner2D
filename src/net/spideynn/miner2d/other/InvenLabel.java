package net.spideynn.miner2d.other;

import org.newdawn.slick.*;

import java.io.Serializable;

public class InvenLabel implements InvenMethods, Serializable {

	private static final long serialVersionUID = -870711211755592392L;
	public int x;
	public int y;
	public String text;

	public InvenLabel() {
		this.x = 0;
		this.y = 0;
		this.text = "Deserialized";
	}

	public InvenLabel(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
	}

	@Override
	public void render(Graphics g, GameContainer gc) {
		g.setColor(new Color(0, 0, 0, 255));
		g.drawString(text, x, y);
	}

	@Override
	public void renderTop(Graphics g, GameContainer gc) {

	}

}
