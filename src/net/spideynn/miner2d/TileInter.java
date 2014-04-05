package net.spideynn.miner2d;

import org.newdawn.slick.*;

public interface TileInter {

	public String getType();

	public void render(Graphics g, int ix, int iy, int tx, int ty);

	public Item getDrop();

	public void breakTile();

	public void placeTile();

	public void Serialized();

	public void Deserialized();

	public boolean RightClick();

	public int getHardness();

	public boolean tileUpdate();

}
