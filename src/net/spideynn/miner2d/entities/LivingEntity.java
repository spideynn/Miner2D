package net.spideynn.miner2d.entities;

import java.io.Serializable;

public abstract class LivingEntity implements Serializable, LivingEntityMethods {

	private static final long serialVersionUID = -5279682885461103877L;

	public float x;
	public float y;
	public float w;
	public float h;
	public int health;
	public float fx;
	public float fy;

	public LivingEntity(float x, float y, float w, float h, int health) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.health = health;
		this.fx = 0f;
		this.fy = 0f;
	}

	public LivingEntity() {
		this.x = 0f;
		this.y = 0f;
		this.w = 24f;
		this.h = 24f;
		this.health = 10;
		this.fx = 0f;
		this.fy = 0f;
	}

}
