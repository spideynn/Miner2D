package net.spideynn.miner2d.items;

import java.io.Serializable;
import java.util.List;

import net.spideynn.miner2d.blocks.Tile;

public class EmeraldItem extends Item implements Serializable {

	private static final long serialVersionUID = 4985867592626848622L;

	public String getTypeOf() {
		return "EmeraldItem";
	}

	public EmeraldItem(int stack) {
		super(stack, 64, 48, false, "Emerald", false, 0);
	}

	public EmeraldItem() {
		super();
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
		EmeraldItem copy = new EmeraldItem(0);
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