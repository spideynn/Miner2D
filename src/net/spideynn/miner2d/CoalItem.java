package net.spideynn.miner2d;

import java.util.List;
import java.io.Serializable;

public class CoalItem extends Item implements Serializable {

	private static final long serialVersionUID = 2250187269868653858L;

	public String getTypeOf() {
		return "CoalItem";
	}

	public CoalItem(int stack) {
		super(stack, 64, 5, false, "Coal", true, 1);
	}

	public CoalItem() {
		super(0, 0, 0, false, "", true, 1);
	}

	@Override
	public Tile getPlace(List<String> args) {
		return null;
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
		CoalItem copy = new CoalItem(0);
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
