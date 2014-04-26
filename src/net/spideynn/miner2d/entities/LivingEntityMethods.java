package net.spideynn.miner2d.entities;

import org.newdawn.slick.Graphics;

public interface LivingEntityMethods {

	public void render(Graphics g);

	public boolean update();

	public void hit(int amount);

	public String getTypeEntity();

}
