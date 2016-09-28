package org.mvc.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassLoader {
	
	private static ClassLoader loader;
	
	private CustomClassLoader() {
		
	}
	
	public static ClassLoader getInstance() throws MalformedURLException {
		if(loader == null) {
			String pathToClass = System.getProperty("java.class.path");
			URL[] urls = new URL[pathToClass.split(";").length];
			int i = 0;
			for(String s : pathToClass.split(";")) {
				File f = new File(s);
				if(f.isDirectory()) {
					urls[i++] = new URL("file:" + f.getAbsolutePath() + "/");
				}
				else {
					urls[i++] = new URL("jar:file:" + f.getAbsolutePath() + "!/");
				}
			}
			loader = new URLClassLoader(urls);			
		}
		return loader;
	}
}
