package net.spideynn.miner2d;

import java.util.List;
import java.io.Serializable;

import net.spideynn.miner2d.Tile;
import net.spideynn.miner2d.WoodTile;

public class WoodItem extends Item implements Serializable {

	private static final long serialVersionUID = -8024660163167111672L;

	public WoodItem(int stack) {
		super(stack, 64, 9, true, "Wood", true, 200);
	}

	public WoodItem() {
		super();
	}

	@Override
	public Tile getPlace(List<String> args) {
		return new WoodTile();
	}

	@Override
	public boolean use() {
		this.stack--;
		if (this.stack < 1)
			return false;
		else
			return true;
	}

	@Override
	public String getTypeOf() {
		return "WoodItem";
	}

	@Override
	public Item copyitem() {
		WoodItem copy = new WoodItem(0);
		copy.downplace = this.downplace;
		copy.img = this.img;
		copy.leftplace = this.leftplace;
		copy.maxstack = this.maxstack;
		copy.name = this.name;
		copy.placeable = this.placeable;
		copy.rightplace = this.rightplace;
		copy.smeltable = this.smeltable;
		copy.smeltlast = this.smeltlast;
		copy.stack = this.stack;
		copy.torchplace = this.torchplace;
		copy.upplace = this.upplace;
		copy.tool = this.tool;
		copy.usage = this.usage;
		return copy;
	}

	@Override
	public boolean getIsSmeltable() {
		return false;
	}

	@Override
	public Item getSmelted() {
		return null;
	}

}
