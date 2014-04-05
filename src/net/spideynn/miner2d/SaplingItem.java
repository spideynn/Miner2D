package net.spideynn.miner2d;

import java.util.List;
import java.io.Serializable;

public class SaplingItem extends Item implements Serializable {

	private static final long serialVersionUID = -718973732332694428L;

	public SaplingItem(int stack) {
		super(stack, 64, 13, true, "Sapling", true, 50);
		this.torchplace = true;
		this.downplace = true;
	}

	public SaplingItem() {
		super();
	}

	@Override
	public Tile getPlace(List<String> args) {
		return new SaplingTile(Integer.valueOf(args.get(0)),
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
	public String getTypeOf() {
		return "SaplingItem";
	}

	@Override
	public Item copyitem() {
		SaplingItem copy = new SaplingItem(0);
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
