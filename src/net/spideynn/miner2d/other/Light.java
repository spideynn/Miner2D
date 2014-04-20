package net.spideynn.miner2d.other;

import java.io.Serializable;

public class Light implements Serializable {

	private static final long serialVersionUID = -4200910030494767453L;
	public int x;
	public int y;
	public int light;
	public int r;
	public int g;
	public int b;
	public int rad;

	public Light(int x, int y, int light, int rad, int r, int g, int b) {
		this.x = x;
		this.y = y;
		this.light = light;
		this.r = r;
		this.g = g;
		this.b = b;
		this.rad = rad;
	}

	public Light() {
		this.x = 0;
		this.y = 0;
		this.light = 0;
		this.r = 0;
		this.g = 0;
		this.b = 0;
		this.rad = 0;
	}

}
