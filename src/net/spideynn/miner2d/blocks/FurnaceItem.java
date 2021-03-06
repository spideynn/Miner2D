package net.spideynn.miner2d.blocks;

import java.util.List;
import java.io.Serializable;

import net.spideynn.miner2d.items.Item;

public class FurnaceItem extends Item implements Serializable {

	private static final long serialVersionUID = -2824086520794311053L;

	public FurnaceItem(int stack) {
		super(stack, 64, 22, true, "Furnace", false, 0);
	}

	@Override
	public Tile getPlace(List<String> args) {
		return new FurnaceTile(Integer.valueOf(args.get(0)),
				Integer.valueOf(args.get(1)));
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
		FurnaceItem copy = new FurnaceItem(0);
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
		return "FurnaceItem";
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
