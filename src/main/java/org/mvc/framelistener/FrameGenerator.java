package org.mvc.framelistener;

import java.lang.reflect.InvocationTargetException;

public interface FrameGenerator {
	
	public void generateFrame() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException;
}
