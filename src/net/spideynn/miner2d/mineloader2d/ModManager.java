package net.spideynn.miner2d.mineloader2d;

import java.io.FileNotFoundException;

/**
 * Service Management
 */

public interface ModManager {

	public void init() throws FileNotFoundException;

	public void registerService(String name, ModService service);
	public ModService getService(String string);
	
}
