package net.spideynn.miner2d;

import org.newdawn.slick.Graphics;

public interface LivingEntityMethods {

	public void render(Graphics g);

	public boolean update();

	public void hit(int amount);

	public String getTypeEntity();

}
