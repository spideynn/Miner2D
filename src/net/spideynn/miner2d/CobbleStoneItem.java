package net.spideynn.miner2d;

import java.io.Serializable;
import java.util.List;

public class CobbleStoneItem extends Item implements Serializable {

	private static final long serialVersionUID = 3850283249536171706L;

	public CobbleStoneItem() {
		super();
	}

	public CobbleStoneItem(int stack) {
		super(stack, 64, 20, true, "Cobblestone", false, 0);
	}

	@Override
	public Tile getPlace(List args) {
		return new CobbleStoneTile();
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
		CobbleStoneItem copy = new CobbleStoneItem(0);
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
		return "CobbleStoneItem";
	}

	@Override
	public boolean getIsSmeltable() {
		return true;
	}

	@Override
	public Item getSmelted() {
		return new NormalItem(1, 64, 1, "Stone", false, 0);
	}

}
