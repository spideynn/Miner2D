package net.spideynn.miner2d;

import java.io.Serializable;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class FurnaceTile extends Tile implements Serializable {

	private static final long serialVersionUID = -8214472882518073268L;

	public Inventory furnace;
	public FurnaceGui guime;

	public int heat = 0;
	public int progress = 0;

	public int takheat = 0;

	public int x;
	public int y;

	public String lName = "";
	public String currentSmelt = "";

	public Object myTask;

	public FurnaceTile(int x, int y) {
		super(22, true, false, true, true);
		this.x = x;
		this.y = y;
		furnace = new Inventory(new Item[3]);
		guime = new FurnaceGui(furnace, this);
	}

	public FurnaceTile() {
		super(22, true, false, true, true);
		furnace = new Inventory(new Item[3]);
		guime = new FurnaceGui(furnace, this);
	}

	@Override
	public String getType() {
		return "Furnace";
	}

	@Override
	public void render(Graphics g, int ix, int iy, int tx, int ty) {
		if (MD.game.light[ix][iy] > 0) {
			g.drawImage(MD.game.textureBin.get(MD.game.s.map[ix][iy].img), tx
					* MD.game.tileWidth - MD.game.s.camX, ty
					* MD.game.tileHeight - MD.game.s.camY, new Color(
					MD.game.light_r[ix][iy], MD.game.light_g[ix][iy],
					MD.game.light_b[ix][iy], MD.game.light[ix][iy]));
		} else {
			g.setColor(new Color(0, 0, 0, 255));
			g.fillRect(ix * MD.game.tileWidth - MD.game.s.camX, iy
					* MD.game.tileHeight - MD.game.s.camY, MD.game.tileWidth,
					MD.game.tileWidth);
		}
	}

	@Override
	public Item getDrop() {
		if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
			switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
			case "Wooden Pickaxe":
				return new FurnaceItem(1);
			case "Stone Pickaxe":
				return new FurnaceItem(1);
			case "Iron Pickaxe":
				return new FurnaceItem(1);
			case "Diamond Pickaxe":
				return new FurnaceItem(1);
			}
		}
		return null;
	}

	public void resetTask() {
		myTask = new Runnable() {

			@Override
			public void run() {
				if (furnace.i[0] != null && furnace.i[0].getIsSmeltable()) {
					if (furnace.i[0].name != lName) {
						currentSmelt = furnace.i[0].getSmelted().name;
					}
					lName = furnace.i[0].name;
				}
				if (heat > 0) {
					if (furnace.i[0] != null && furnace.i[0].getIsSmeltable()) {
						boolean dosmelt = true;
						if (furnace.i[2] != null) {
							dosmelt = false;
							if (furnace.i[2].name == currentSmelt
									&& furnace.i[2].stack < furnace.i[2].maxstack) {
								dosmelt = true;
							}
						}
						if (dosmelt) {
							progress += 10;
							if (progress >= 8192) {
								if (furnace.i[2] == null) {
									furnace.i[2] = furnace.i[0].getSmelted();
								} else {
									furnace.i[2].stack += furnace.i[0]
											.getSmelted().stack;
								}
								if (!(furnace.i[0].use()))
									furnace.i[0] = null;
								progress = 0;
							}
						} else {
							progress = 0;
						}
					} else {
						progress = 0;
					}
					heat -= takheat;
				} else {
					if (heat < 0)
						heat = 0;
					if (furnace.i[0] != null && furnace.i[1] != null
							&& furnace.i[1].smeltable) {
						takheat = furnace.i[1].smeltlast;
						System.out.println(takheat + " "
								+ furnace.i[1].smeltlast);
						heat = 8192;
						if (!(furnace.i[1].use()))
							furnace.i[1] = null;
					}
				}
			}
		};
	}

	@Override public void breakTile() { furnace.drop(x * MD.game.tileWidth, y *
	  MD.game.tileHeight); if (MD.game.tasks.contains(myTask))
	  MD.game.tasks.remove(myTask); myTask = null; }

	@Override
	public void placeTile() {
		resetTask();
		if (!(MD.game.tasks.contains(myTask)))
			MD.game.tasks.add((Runnable) (myTask));
	}

	@Override
	public void Serialized() {
		if (MD.game.tasks.contains(myTask))
			MD.game.tasks.remove(myTask);
		myTask = null;
	}

	@Override
	public void Deserialized() {
		resetTask();
		if (!(MD.game.tasks.contains(myTask)))
			MD.game.tasks.add((Runnable) (myTask));
	}

	@Override
	public boolean RightClick() {
		MD.game.currentGui = guime;
		guime.resized((GameContainer) MD.game.container);
		MD.game.inventory = true;
		return false;
	}

	@Override
	public int getHardness() {
		if (MD.game.s.mp.inven.i[MD.game.s.mp.invsel] != null) {
			switch (MD.game.s.mp.inven.i[MD.game.s.mp.invsel].name) {
			case "Wooden Pickaxe":
				return 30;
			case "Stone Pickaxe":
				return 70;
			case "Iron Pickaxe":
				return 100;
			case "Diamond Pickaxe":
				return 180;
			}
		}
		return 10;
	}

	@Override
	public boolean tileUpdate() {
		return true;
	}

}
