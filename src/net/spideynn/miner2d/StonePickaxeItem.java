package net.spideynn.miner2d;

import java.io.Serializable;
import java.util.List;

import net.spideynn.miner2d.Tile;

public class StonePickaxeItem extends Item implements Serializable {

	private static final long serialVersionUID = 1160889111397151970L;

	public StonePickaxeItem() {
		super(1, 1, 19, false, "Stone Pickaxe", false, 0);
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
		StonePickaxeItem copy = new StonePickaxeItem();
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
		return "StonePickaxeItem";
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
