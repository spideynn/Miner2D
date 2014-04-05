package net.spideynn.miner2d;

import java.util.*;
import java.io.Serializable;

public class NormalItem extends Item implements Serializable {

	private static final long serialVersionUID = -185610813414377694L;

	public String getTypeOf() {
		return "NormalItem";
	}

	public boolean use() {
		this.stack--;
		if (this.stack < 1)
			return false;
		else
			return true;
	}

	public NormalItem(int stack, int maxstack, int img, String Name,
			boolean smeltable, int smeltlast) {
		super(stack, maxstack, img, true, Name, smeltable, smeltlast);
	}

	public NormalItem() {
		super();
	}

	@Override
	public Tile getPlace(List<String> args) {
		return new NormalTile(this.img, true, name);
	}

	@Override
	public Item copyitem() {
		NormalItem copy = new NormalItem(0, 0, 0, "", false, 0);
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
