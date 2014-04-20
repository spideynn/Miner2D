package net.spideynn.miner2d; //Can be any package.

public class Mod {
	
	public static void ModInfo() { //Modify this to your liking.
		String ModVersion = "v0.1"; //Mod Version
		String ModInfo = "Test Mod"; //Some info about the mod. Required
		System.out.println("Loaded Mod Info: " + ModVersion + " " + ModInfo); //Logs the loaded mod.
	}

	public static void main() {
		ModInfo(); //Required.
		System.out.println("Test Mod Initilization"); //Just a test.
	}

}
