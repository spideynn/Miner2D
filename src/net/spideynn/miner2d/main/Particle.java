package net.spideynn.miner2d.main;

import org.newdawn.slick.Image;

public class Particle {

	public float x;
	public float y;
	public float fx;
	public float fy;
	public float rot;
	public Image img;

	public Particle(float x, float y, float fx, float fy, float rot, Image img) {
		this.x = x;
		this.y = y;
		this.fx = fx;
		this.fy = fy;
		this.rot = rot;
		this.img = img;
	}

	public Particle() {
		this.x = 0f;
		this.y = 0f;
		this.fx = 0f;
		this.fy = 0f;
		this.rot = 0f;
		this.img = null;
	}

}
