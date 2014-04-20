package net.spideynn.miner2d.blocks;

import java.util.*;
import java.io.Serializable;

import net.spideynn.miner2d.items.Item;

public class TorchItem extends Item implements Serializable {

	private static final long serialVersionUID = 5928612531670144079L;

	public String getTypeOf() {
		return "TorchItem";
	}

	public TorchItem(int stack) {
		super(stack, 64, 6, true, "Torch", false, 0);
		this.torchplace = true;
		this.downplace = true;
		this.leftplace = true;
		this.rightplace = true;
	}

	public TorchItem() {
		super();
	}

	@Override
	public Tile getPlace(List<String> args) {
		return new TorchTile(Integer.valueOf(args.get(0)), Integer.valueOf(args
				.get(1)));
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
		TorchItem copy = new TorchItem(0);
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
