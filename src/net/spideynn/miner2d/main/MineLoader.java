package net.spideynn.miner2d.main;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;

/**
 * Marked as deprecated because of new rewritten modloader - see @MineLoader2D.java
 * Will be removed in next release
 */

@Deprecated
public abstract class MineLoader extends ClassLoader {
	
	public abstract void onEnable();
	public abstract void onInit();
	public abstract void onDisable();

  public MineLoader() {
    super(MineLoader.class.getClassLoader());
  }

  public MineLoader(ClassLoader parent) {
    super(parent);
  }

  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    System.out.println("MineLoader2D is loading modClass " + name);
    return super.loadClass(name);
  }//loadClass

  @Override
  public synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    System.out.println("MineLoader2D is loading " + name + " with resolve " + resolve);
    return super.loadClass(name, resolve);
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    System.out.println("MineLoader2D findClass " + name);
    return super.findClass(name);
  }

  @Override
  protected URL findResource(String name) {
    System.out.println("MineLoader2D findResource " + name);
    return super.findResource(name);
  }

  @Override
  protected Enumeration<URL> findResources(String name) throws IOException {
    System.out.println("MineLoader2D findResources " + name);
    return super.findResources(name);
  }

  @Override
  protected Package getPackage(String name) {
    System.out.println("MineLoader2D getPackage " + name);
    return super.getPackage(name);
  }

  @Override
  public URL getResource(String name) {
    System.out.println("MineLoader2D getResource " + name);
    return super.getResource(name);
  }

  @Override
  public InputStream getResourceAsStream(String name) {
    System.out.println("MineLoader2D getResourceAsStream " + name);
    return super.getResourceAsStream(name);
	}

  @Override
  public Enumeration<URL> getResources(String name) throws IOException {
    System.out.println("MineLoader2D getResources " + name);
    return super.getResources(name);
  }

public static Method getMethod(String string, Class<String[]> class1) {
	return null;
}
}