package net.spideynn.miner2d.main;

/**
 * Required for mods to function. If it does not exist in the mod, then it will not load.
 * Sets the name, modid, and version for a mod.
 */
public @interface Mod {

	String modid();
	String version();
	String modname();

}
