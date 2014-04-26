package net.spideynn.miner2d.examplemod; //Can be any package.

import net.spideynn.miner2d.mineloader2d.*;

/**
 * Example mod, most of the modinfo is in the mod.yml (YAML config, NO TABS)
 */

public class ExampleMod implements Mod {
	
	public static void main() {
		System.out.println("Test Mod Initilization"); //Just a test.
	}

    @Override
    public void init(ModManager pm) {
            System.out.println("plugin1 initialized!");
            ExampleService testService = (ExampleService) pm.getService("testService");
            testService.testFunction();
    }


}
