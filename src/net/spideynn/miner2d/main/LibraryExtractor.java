package net.spideynn.miner2d.main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


public class LibraryExtractor {

	public static void getOSLibs() {
	    String osName = System.getProperty("os.name");
		String nativeDir = ""; 
		
		try {
			nativeDir = new File(LibraryExtractor.class.getClass().getProtectionDomain().getCodeSource().getLocation().
                toURI()).getParent();
    } catch (URISyntaxException uriEx) {
        try {
            uriEx.printStackTrace();
            nativeDir = new File(".").getCanonicalPath();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            System.out.println("No library folder found.");
            System.exit(-1);
        }
    }
    nativeDir += File.separator + "lib" + File.separator + "native" + File.separator;
    if (osName.startsWith("Windows")) {
        nativeDir += "windows";
    } else if (osName.startsWith("Linux") || osName.startsWith("FreeBSD")) {
        nativeDir += "linux";
    } else if (osName.startsWith("Mac OS X")) {
        nativeDir += "macosx";
    } else if (osName.startsWith("Solaris") || osName.startsWith("SunOS")) {
        nativeDir += "solaris";
    } else {
        System.out.println("System Not Supported.");
        System.exit(-1);
    }
    System.setProperty("org.lwjgl.librarypath", nativeDir);
	}
}
