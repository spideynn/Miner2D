package net.spideynn.miner2d;

import java.io.Serializable;
import java.util.List;

import net.spideynn.miner2d.Tile;

public class DiamondAxeItem extends Item implements Serializable {

	private static final long serialVersionUID = 6598414866294390667L;

	public DiamondAxeItem() {
		super(1, 1, 33, false, "Diamond Axe", false, 0);
		this.tool = true;
		this.usage = 8192;
	}

	@Override
	public Tile getPlace(List<String> args) {
		return null;
	}

	@Override
	public boolean use() {
		this.usage -= 5;
		if (this.usage < 1)
			return false;
		else
			return true;
	}

	@Override
	public Item copyitem() {
		DiamondAxeItem copy = new DiamondAxeItem();
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
		return "DiamondAxeItem";
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
