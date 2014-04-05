package net.spideynn.miner2d;

import java.util.Random;
import java.io.Serializable;

public class Inventory implements Serializable {

	private static final long serialVersionUID = 1091823313571841697L;
	public Random rand = new Random();
	public Item[] i;

	public void drop(float x, float y) {
		for (Item it : i) {
			if (it != null) {
				MD.game.s.items.add(new PickableItem(it, x, y,
						rand.nextFloat() * 5 - 2.5f,
						rand.nextFloat() * 5 * -1f, 16f, 16f));
			}
		}
	}

	public Inventory(Item[] i) {
		this.i = i;
	}

	public Inventory() {

	}

}
