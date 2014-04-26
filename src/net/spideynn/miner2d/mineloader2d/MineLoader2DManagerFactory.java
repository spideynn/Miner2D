package net.spideynn.miner2d.mineloader2d;

/**
 * Used to create the PluginManager for ML2D 
 */

public class MineLoader2DManagerFactory {

	public static ModManager createPluginManager(String pluginsDir) {
		return new MineLoader2DManagerImpl(pluginsDir);
	}

}
