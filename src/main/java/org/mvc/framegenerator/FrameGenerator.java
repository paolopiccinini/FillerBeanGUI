package org.mvc.framegenerator;

import java.lang.reflect.InvocationTargetException;

public interface FrameGenerator {
	
	public void generateFrame() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException;
}
