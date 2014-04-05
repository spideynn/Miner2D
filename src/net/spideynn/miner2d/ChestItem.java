package net.spideynn.miner2d;

import java.util.List;
import java.io.Serializable;

public class ChestItem extends Item implements Serializable {

	private static final long serialVersionUID = -7069120650735135251L;

	public ChestItem(int stack) {
		super(stack, 64, 16, true, "Chest", false, 0);
	}

	@Override
	public Tile getPlace(List<String> args) {
		return new ChestTile(Integer.valueOf(args.get(0)), Integer.valueOf(args
				.get(1)));
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
	public Item copyitem() {
		ChestItem copy = new ChestItem(0);
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
	public String getTypeOf() {
		return "ChestItem";
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
