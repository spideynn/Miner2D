package net.spideynn.miner2d.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.GameContainer;

public interface GuiMethods {

	public void render(Graphics g, GameContainer gc);

	public void mousePressed(int button, int x, int y);

	public void resized(GameContainer gc);

}
