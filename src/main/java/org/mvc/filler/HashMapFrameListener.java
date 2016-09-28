package org.mvc.filler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

import org.mvc.util.CustomClassLoader;

public class HashMapFrameListener<T, U, V> implements ActionListener {
	
	T padreDellaMappaDaFillare;
	Field mappaDaFillare;
	
	public HashMapFrameListener(Field mapToFill, T mapToFillFather){
		mappaDaFillare = mapToFill;
		padreDellaMappaDaFillare = mapToFillFather;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e) {
		
        ParameterizedType tipo = (ParameterizedType) mappaDaFillare.getGenericType();
        Class<?> classeDellaChiave = null;
		Class<?> classeDelValore = null;
		int i = 0;
		Type[] types = tipo.getActualTypeArguments();
		try {
			classeDellaChiave = (Class<?>) Class.forName(types[0].toString().substring(6), false, CustomClassLoader.getInstance());
			classeDelValore = (Class<?>) Class.forName(types[1].toString().substring(6), false, CustomClassLoader.getInstance());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		for(Type type : tipo.getActualTypeArguments()) {
//			File f = new File(System.getProperty("java.class.path"));
//			for(File jarFile : f.listFiles()) {
//				String jar = jarFile.getName();
//				try{
//					URL[] urls = { new URL("jar:file:" + f.getAbsolutePath() + "\\" + jar + "!/") };
//					URLClassLoader urlcl = new URLClassLoader(urls);
//					if(i == 0) {
//						classeDellaChiave = (Class<?>)Class.forName(type.toString().substring(6), false, urlcl);
//						if(classeDellaChiave != null) {
//							break;
//						}
//					} else if(i == 1) {
//						classeDelValore = (Class<?>)Class.forName(type.toString().substring(6), false, urlcl);
//						if(classeDelValore != null) {
//							break;
//						}
//					}
//				} catch (MalformedURLException e2) {
//	        		e2.printStackTrace();
//	        	} catch (ClassNotFoundException e3) {
//	        		e3.printStackTrace();
//	        	}
//			}
//			i++;
//		}
        U chiave = null;
        V valore = null;
		try {
			mappaDaFillare.setAccessible(true);
			TheFiller filler = null;
			if(!Validator.tipiPrimitivi.containsKey(classeDelValore) && !Validator.tipiPrimitivi.containsKey(classeDellaChiave)){
				chiave = (U) classeDellaChiave.newInstance();
				valore = (V) classeDelValore.newInstance();
				filler = new TheFiller((Map<U, V>)mappaDaFillare.get(padreDellaMappaDaFillare), chiave, valore);
			} else if(Validator.tipiPrimitivi.containsKey(classeDelValore) && Validator.tipiPrimitivi.containsKey(classeDellaChiave)){
				filler = new TheFiller((Map<T, U>) mappaDaFillare.get(padreDellaMappaDaFillare), classeDellaChiave, classeDelValore);
			} else if(Validator.tipiPrimitivi.containsKey(classeDelValore)) {
				chiave = (U) classeDellaChiave.newInstance();
				filler = new TheFiller((Map<U, V>) mappaDaFillare.get(padreDellaMappaDaFillare), chiave, classeDelValore);
			} else {
				valore = (V) classeDelValore.newInstance();
				filler = new TheFiller((Map<U, V>) mappaDaFillare.get(padreDellaMappaDaFillare), classeDellaChiave, valore);
			}
			filler.fill();
		} catch (InstantiationException e3) {
			e3.printStackTrace();
		} catch (IllegalAccessException e4) {
			e4.printStackTrace();
		}

	}

}
