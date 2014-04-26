package net.spideynn.miner2d.items;

import java.io.Serializable;
import java.util.List;

import net.spideynn.miner2d.blocks.Tile;

public class IronIngotItem extends Item implements Serializable {

	public IronIngotItem() {
		super();
	}

	public IronIngotItem(int stack) {
		super(stack, 64, 23, false, "Iron Ingot", false, 0);
	}

	private static final long serialVersionUID = -3878752859926626762L;

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
		IronIngotItem copy = new IronIngotItem(0);
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
		return "IronIngotItem";
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
