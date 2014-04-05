package net.spideynn.miner2d;

public class MenuButton {

	public String text;
	public int x;
	public int y;
	public int w;
	public int h;
	public int alpha;

	public Runnable clickAction = null;

	public MenuButton(String text, int x, int y, int w, int h,
			Runnable clickAction) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.alpha = 0;
		this.clickAction = clickAction;
	}

}
