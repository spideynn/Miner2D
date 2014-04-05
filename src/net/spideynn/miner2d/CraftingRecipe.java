package net.spideynn.miner2d;

public class CraftingRecipe {

	public String[][] crafting;
	public Item outcome;
	public int[] misx;
	public int[] misy;

	public Item getItem() {
		return outcome.copyitem();
	}

	public CraftingRecipe(String[][] crafting, Item outcome, int[] misx,
			int[] misy) {
		this.crafting = crafting;
		this.outcome = outcome;
		this.misx = misx;
		this.misy = misy;
	}

}
