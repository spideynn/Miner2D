package net.spideynn.game.miner2d;

public class CraftingRecipe
{
  public String[][] crafting;
  public Item outcome;
  public int[] misx;
  public int[] misy;
  
  public Item getItem()
  {
    return this.outcome.copyitem();
  }
  
  public CraftingRecipe(String[][] crafting, Item outcome, int[] misx, int[] misy)
  {
    this.crafting = crafting;
    this.outcome = outcome;
    this.misx = misx;
    this.misy = misy;
  }
}
