package org.mvc.filler;

import java.util.List;
import java.util.Map;


public class TheFiller {
	
	private FrameGenerator generatoreDiFrame;

	public <T> TheFiller(T objectToFill){
		FieldUtil.initialize(objectToFill);
		generatoreDiFrame = new CustomFrameGenerator<T>(objectToFill, FieldUtil.retrieveFields(objectToFill));
	}
	
	public <T> TheFiller(List<T> listToFill, Class<?> clazz){
		generatoreDiFrame = new PrimitiveListFrameGenerator<T>(listToFill, clazz);
	}

	public <T, U> TheFiller(Map<T, U> mapToFill, Class<?> keyClass, Class<?> valueClass){
		generatoreDiFrame = new PrimitiveMapFrameGenerator<T, U>(mapToFill, keyClass, valueClass);
	}

	public <T, U> TheFiller(Map<T, U> mapToFill, Class<?> keyClass, U value) {
		generatoreDiFrame = new ValueMapFrameGenerator<T, U>(mapToFill, keyClass, value);
	}

	public <T, U> TheFiller(Map<T, U> mapToFill, T key, Class<?> valueClass) {
		generatoreDiFrame = new KeyMapFrameGenerator<T, U>(mapToFill, key, valueClass);
	}

	public <T, U> TheFiller(Map<T, U> mapToFill, T key, U value) {
		generatoreDiFrame = new KeyValueMapFrameGenerator<T, U>(mapToFill, key, value);
	}

	public void fill(){
		try {
			generatoreDiFrame.generateFrame();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
	}
	
}
