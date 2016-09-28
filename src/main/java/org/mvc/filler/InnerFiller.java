package org.mvc.filler;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.mvc.framegenerator.ComlpexListFrameGenerator;
import org.mvc.framegenerator.CustomFrameGenerator;
import org.mvc.framegenerator.FrameGenerator;
import org.mvc.framegenerator.KeyMapFrameGenerator;
import org.mvc.framegenerator.KeyValueMapFrameGenerator;
import org.mvc.framegenerator.PrimitiveListFrameGenerator;
import org.mvc.framegenerator.PrimitiveMapFrameGenerator;
import org.mvc.framegenerator.ValueMapFrameGenerator;
import org.mvc.util.FieldUtil;


public class InnerFiller<T, U> {
	
	private FrameGenerator generatoreDiFrame;
	public static CountDownLatch latch;
	public static int openFrame = 1;

	public InnerFiller(T objectToFill){
		try {
			FieldUtil.initialize(objectToFill);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		generatoreDiFrame = new CustomFrameGenerator<T>(objectToFill, FieldUtil.retrieveFields(objectToFill));
	}
	
	public InnerFiller(List<T> listToFill, Class<?> clazz){
		generatoreDiFrame = new PrimitiveListFrameGenerator<T>(listToFill, clazz);
	}
	
	public InnerFiller(List<T> listToFill, T element, String[] elementeParamClass){
		generatoreDiFrame = new ComlpexListFrameGenerator<T>(listToFill, element, elementeParamClass);
	}

	public InnerFiller(Map<T, U> mapToFill, Class<?> keyClass, Class<?> valueClass){
		generatoreDiFrame = new PrimitiveMapFrameGenerator<T, U>(mapToFill, keyClass, valueClass);
	}

	public InnerFiller(Map<T, U> mapToFill, Class<?> keyClass, U value, String[] valueParamClass) {
		generatoreDiFrame = new ValueMapFrameGenerator<T, U>(mapToFill, keyClass, value, valueParamClass);
	}

	public InnerFiller(Map<T, U> mapToFill, T key, String[] keyParamClass, Class<?> valueClass) {
		generatoreDiFrame = new KeyMapFrameGenerator<T, U>(mapToFill, key, keyParamClass, valueClass);
	}

	public InnerFiller(Map<T, U> mapToFill, T key, String[] keyParamClass, U value, String[] valueParamClass) {
		generatoreDiFrame = new KeyValueMapFrameGenerator<T, U>(mapToFill, key, keyParamClass, value, valueParamClass);
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
		} catch (IllegalArgumentException e3) {
			e3.printStackTrace();
		} catch (InvocationTargetException e4) {
			e4.printStackTrace();
		}
	}
	
}
