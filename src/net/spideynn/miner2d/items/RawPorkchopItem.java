package net.spideynn.miner2d.items;

import java.io.Serializable;
import java.util.List;

import net.spideynn.miner2d.blocks.Tile;

public class RawPorkchopItem extends Item implements Serializable {

	private static final long serialVersionUID = -4445676516371400539L;

	public RawPorkchopItem(int stack) {
		super(stack, 64, 38, false, "Raw Porkchop", false, 0);
		this.food = true;
		this.healthrep = 5;
	}

	public RawPorkchopItem() {
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
		RawPorkchopItem copy = new RawPorkchopItem(0);
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
		return "RawPorkchopItem";
	}

	@Override
	public boolean getIsSmeltable() {
		return true;
	}

	@Override
	public Item getSmelted() {
		return new PorkchopItem(1);
	}

}
