package net.spideynn.miner2d.items;

import java.util.*;

import net.spideynn.miner2d.blocks.Tile;

public interface ItemMethods {

	public Tile getPlace(List<String> args);

	public boolean use();

	public Item copyitem();

	public String getTypeOf();

	public boolean getIsSmeltable();

	public Item getSmelted();

}
