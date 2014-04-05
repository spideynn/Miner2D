package net.spideynn.miner2d;

import java.util.*;

public interface ItemMethods {

	public Tile getPlace(List<String> args);

	public boolean use();

	public Item copyitem();

	public String getTypeOf();

	public boolean getIsSmeltable();

	public Item getSmelted();

}
