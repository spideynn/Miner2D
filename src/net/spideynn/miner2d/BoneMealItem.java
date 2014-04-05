package net.spideynn.miner2d;

import java.io.Serializable;
import java.util.List;

import net.spideynn.miner2d.Tile;

public class BoneMealItem extends Item implements Serializable {

	public BoneMealItem() {
		super();
	}

	public BoneMealItem(int stack) {
		super(stack, 64, 42, false, "Bone Meal", false, 0);
	}

	private static final long serialVersionUID = -2472540187096920993L;

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
		BoneMealItem copy = new BoneMealItem(0);
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
		return "BoneMealItem";
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
