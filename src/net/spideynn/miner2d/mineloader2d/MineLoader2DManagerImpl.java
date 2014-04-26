package net.spideynn.miner2d.mineloader2d;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * Manages plugins and reads the plugin.yml
 */

public class MineLoader2DManagerImpl implements ModManager {

	private String pluginsDir;
	private MineLoader2D classLoader;
	private List<String> loadedPlugins = new ArrayList<String>();
	private Map<String, ModService> services = new HashMap<String, ModService>();

	public MineLoader2DManagerImpl(String pluginsDir) {
		this.pluginsDir = pluginsDir;
		this.classLoader = new MineLoader2D();
	}

	@Override
	public void init() {
		File dh = new File(this.pluginsDir);
		for (File pluginDir : dh.listFiles())
			if(!pluginDir.getName().startsWith("."))
				loadPlugin(pluginDir);
	}

	@SuppressWarnings("unchecked")
	private void loadPlugin(File pluginDir) {
		try {
			if (loadedPlugins.contains(pluginDir.getAbsolutePath()))
				return;
			classLoader.loadPlugin(pluginDir);
			Yaml yaml = new Yaml();
			Map<String, String> pluginConf = (Map<String, String>) yaml
					.load(new FileInputStream(
							new File(pluginDir, "plugin.yml")));
			String plugin_class = pluginConf.get("plugin_class");
			String deps = pluginConf.get("dependencies");
			if (deps != null)
				loadPluginDependencies(deps);
			Class<?> pluginClass = classLoader.loadClass(plugin_class);
			Mod plugin = (Mod) pluginClass.newInstance();
			plugin.init(this);
			loadedPlugins.add(pluginDir.getAbsolutePath());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadPluginDependencies(String dependencies) {
		String[] deps = dependencies.split(",");
		for (String dep : deps)
			loadPlugin(new File(pluginsDir, dep));
	}

	@Override
	public void registerService(String name, ModService service) {
		services.put(name, service);
	}

	@Override
	public ModService getService(String name) {
		return services.get(name);
	}

}
