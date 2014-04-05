package net.spideynn.miner2d;

import java.io.Serializable;
import java.util.List;

import net.spideynn.miner2d.Tile;

public class WoodenSwordItem extends Item implements Serializable {

	private static final long serialVersionUID = -1428157298432154711L;

	public WoodenSwordItem() {
		super(1, 1, 34, false, "Wooden Sword", false, 0);
		this.tool = true;
		this.usage = 8192;
	}

	@Override
	public Tile getPlace(List<String> args) {
		return null;
	}

	@Override
	public boolean use() {
		this.usage -= 100;
		if (this.usage < 1)
			return false;
		else
			return true;
	}

	@Override
	public Item copyitem() {
		WoodenSwordItem copy = new WoodenSwordItem();
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
		return "WoodenSwordItem";
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
