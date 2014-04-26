package net.spideynn.miner2d.mineloader2d;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Loads plugins using a URLClassloader
 */

public class MineLoader2D extends URLClassLoader {

	public MineLoader2D() {
		super(new URL[] {});
	}

	private void addFile(File file) throws MalformedURLException {
		addURL(file.toURI().toURL());
	}

	public void loadPlugin(File pluginDir) {
		try {
			File classesDir = new File(pluginDir, "mods");
			if (classesDir.exists())
				addFile(classesDir);
			File libDir = new File(pluginDir, "mods/lib");
			File[] jars = libDir.listFiles();
			if (jars != null)
				for (File jar : jars)
					addFile(jar);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
