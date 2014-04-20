package net.spideynn.miner2d.blocks;

import java.io.Serializable;
import java.util.List;

import net.spideynn.miner2d.blocks.StoneTile;
import net.spideynn.miner2d.blocks.Tile;
import net.spideynn.miner2d.items.Item;

public class StoneItem extends Item implements Serializable {

	private static final long serialVersionUID = -2146905826619571745L;

	public StoneItem(int stack, int img, String name) {
		super(stack, 64, img, true, name, false, 0);
	}

	public StoneItem(int stack, int maxstack, int img, String name) {
		super(stack, maxstack, img, true, name, false, 0);
	}

	public StoneItem() {
		super();
	}

	@Override
	public Tile getPlace(List<String> args) {
		return new StoneTile(img, name);
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
		StoneItem copy = new StoneItem(0, 0, "");
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
		return "StoneItem";
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
