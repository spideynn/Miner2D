package net.spideynn.miner2d;

import java.io.Serializable;
import java.util.List;

public class StoneSwordItem extends Item implements Serializable {

	private static final long serialVersionUID = 3481100067244664735L;

	public StoneSwordItem() {
		super(1, 1, 35, false, "Stone Sword", false, 0);
		this.tool = true;
		this.usage = 8192;
	}

	@Override
	public Tile getPlace(List<String> args) {
		return null;
	}

	@Override
	public boolean use() {
		this.usage -= 60;
		if (this.usage < 1)
			return false;
		else
			return true;
	}

	@Override
	public Item copyitem() {
		StoneSwordItem copy = new StoneSwordItem();
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
		return "StoneSwordItem";
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
