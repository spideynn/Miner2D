package net.spideynn.miner2d;

public class CraftingRecipes {

	public static void addCrafts() {
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Wood", "", "" }, { "", "", "" }, { "", "", "" } },
				new NormalItem(4, 64, 11, "Wooden Planks", false, 0),
				new int[] { 0, 1, 2, 0, 1, 2, 0, 1, 2 }, new int[] { 0, 0, 0,
						1, 1, 1, 2, 2, 2 }));
		MD.game.crafts
				.add(new CraftingRecipe(new String[][] {
						{ "Wooden Planks", "Wooden Planks", "" },
						{ "", "", "" }, { "", "", "" } }, new NormalItem(4, 64,
						12, "Stick", false, 0), new int[] { 0, 1, 2, 0, 1, 2 },
						new int[] { 0, 0, 0, 1, 1, 1 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Coal", "Stick", "" }, { "", "", "" }, { "", "", "" } },
				new TorchItem(4), new int[] { 0, 1, 2, 0, 1, 2 }, new int[] {
						0, 0, 0, 1, 1, 1 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Wooden Planks", "Wooden Planks", "" },
				{ "Wooden Planks", "Wooden Planks", "" }, { "", "", "" } },
				new CraftingTableItem(1), new int[] { 0, 1, 0, 1 }, new int[] {
						0, 0, 1, 1 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Wooden Planks", "Wooden Planks", "Wooden Planks" },
				{ "Wooden Planks", "", "Wooden Planks" },
				{ "Wooden Planks", "Wooden Planks", "Wooden Planks" } },
				new ChestItem(1), new int[] { 0 }, new int[] { 0 }));

		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Cobblestone", "Cobblestone", "Cobblestone" },
				{ "Cobblestone", "", "Cobblestone" },
				{ "Cobblestone", "Cobblestone", "Cobblestone" } },
				new FurnaceItem(1), new int[] { 0 }, new int[] { 0 })); //Cannot instantiate the type FurnaceItem

		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Wooden Planks", "", "" },
				{ "Wooden Planks", "Stick", "Stick" },
				{ "Wooden Planks", "", "" } }, new WoodenPickaxeItem(),
				new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Cobblestone", "", "" }, { "Cobblestone", "Stick", "Stick" },
				{ "Cobblestone", "", "" } }, new StonePickaxeItem(),
				new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Iron Ingot", "", "" }, { "Iron Ingot", "Stick", "Stick" },
				{ "Iron Ingot", "", "" } }, new IronPickaxeItem(),
				new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Diamond", "", "" }, { "Diamond", "Stick", "Stick" },
				{ "Diamond", "", "" } }, new DiamondPickaxeItem(),
				new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] { { "", "", "" },
				{ "Wooden Planks", "Stick", "Stick" }, { "", "", "" } },
				new WoodenShovelItem(), new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] { { "", "", "" },
				{ "Cobblestone", "Stick", "Stick" }, { "", "", "" } },
				new StoneShovelItem(), new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] { { "", "", "" },
				{ "Iron Ingot", "Stick", "Stick" }, { "", "", "" } },
				new IronShovelItem(), new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] { { "", "", "" },
				{ "Diamond", "Stick", "Stick" }, { "", "", "" } },
				new DiamondShovelItem(), new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Wooden Planks", "Wooden Planks", "" },
				{ "Wooden Planks", "Stick", "Stick" }, { "", "", "" } },
				new WoodenAxeItem(), new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Cobblestone", "Cobblestone", "" },
				{ "Cobblestone", "Stick", "Stick" }, { "", "", "" } },
				new StoneAxeItem(), new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Iron Ingot", "Iron Ingot", "" },
				{ "Iron Ingot", "Stick", "Stick" }, { "", "", "" } },
				new IronAxeItem(), new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Diamond", "Diamond", "" }, { "Diamond", "Stick", "Stick" },
				{ "", "", "" } }, new DiamondAxeItem(), new int[] { 0 },
				new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(
				new String[][] { { "", "", "" },
						{ "Wooden Planks", "Wooden Planks", "Stick" },
						{ "", "", "" } }, new WoodenSwordItem(),
				new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] { { "", "", "" },
				{ "Cobblestone", "Cobblestone", "Stick" }, { "", "", "" } },
				new StoneSwordItem(), new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] { { "", "", "" },
				{ "Iron Ingot", "Iron Ingot", "Stick" }, { "", "", "" } },
				new IronSwordItem(), new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] { { "", "", "" },
				{ "Diamond", "Diamond", "Stick" }, { "", "", "" } },
				new DiamondSwordItem(), new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Stick", "Stick", "Stick" }, { "", "Stick", "" },
				{ "Stick", "Stick", "Stick" } }, new LadderItem(4),
				new int[] { 0 }, new int[] { 0 }));
		MD.game.crafts.add(new CraftingRecipe(new String[][] {
				{ "Dirt", "Dirt", "Dirt" }, { "Dirt", "Dirt", "Dirt" },
				{ "Dirt", "Dirt", "Dirt" } }, new TntItem(1), new int[] { 0 },
				new int[] { 0 }));

	}

}
