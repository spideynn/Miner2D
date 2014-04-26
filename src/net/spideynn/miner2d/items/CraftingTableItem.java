package net.spideynn.miner2d.items;

import java.util.List;

import net.spideynn.miner2d.blocks.Tile;
import net.spideynn.miner2d.other.CraftingTableTile;

public class CraftingTableItem extends Item {

	private static final long serialVersionUID = 8703525867809156153L;

	public CraftingTableItem() {
		super();
	}

	public CraftingTableItem(int stack) {
		super(stack, 64, 17, true, "Crafting Table", false, 0);
	}

	@SuppressWarnings("rawtypes")
	@Override
	  public Tile getPlace(List args)
	  {
	    return new CraftingTableTile(Integer.valueOf((String)args.get(0)), Integer.valueOf((String)args.get(1)).intValue(), this.healthrep);
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
		CraftingTableItem copy = new CraftingTableItem(0);
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
		return "CraftingTableItem";
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
