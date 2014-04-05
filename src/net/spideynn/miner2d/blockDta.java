package net.spideynn.miner2d;

public class blockDta {

	public static String getName(int ind) {
		switch (ind) {
		case 0:
			return "Dirt";
		case 1:
			return "Stone";
		case 2:
			return "Bedrock";
		case 3:
			return "Grass";
		case 4:
			return "Coal Ore";
		case 9:
			return "Wood";
		case 10:
			return "Leaves";
		case 11:
			return "Wooden Planks";
		}
		return "Unknown";
	}

}
