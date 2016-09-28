package org.mvc.filler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.mvc.util.CustomClassLoader;

public class ArrayListFrameListener<T, K> implements ActionListener {

	T padreDellaListaDaFillare;
	Field listaDaFillare;
	
	public ArrayListFrameListener(Field listToFill, T listToFillFather){
		listaDaFillare = listToFill;
		padreDellaListaDaFillare = listToFillFather;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e) {
		
        ParameterizedType tipo = (ParameterizedType) listaDaFillare.getGenericType();
        File f = new File(System.getProperty("java.class.path"));
        Class<?> classeGenerica = null;
		try {
			classeGenerica = (Class<?>)Class.forName(tipo.getActualTypeArguments()[0].toString().substring(6), false, CustomClassLoader.getInstance());
		} catch (ClassNotFoundException e6) {
			// TODO Auto-generated catch block
			e6.printStackTrace();
		} catch (MalformedURLException e6) {
			// TODO Auto-generated catch block
			e6.printStackTrace();
		};
//        for(File jarFile : f.listFiles()) {
//        	String jar = jarFile.getName();
//        	try{
//	        	URL[] urls = { new URL("jar:file:" + f.getAbsolutePath() + "\\" + jar + "!/") };
//	        	URLClassLoader urlcl = new URLClassLoader(urls);
//				classeGenerica = (Class<?>)Class.forName(tipo.getActualTypeArguments()[0].toString().substring(6), false, urlcl);
//	        	if(classeGenerica != null) {
//	        		break;
//	        	}
//        	} catch (MalformedURLException e2) {
//        		e2.printStackTrace();
//        	} catch (ClassNotFoundException e3) {
//        		e3.printStackTrace();
//        	}
//        }
        
        if(!Validator.tipiPrimitivi.containsKey(classeGenerica)){
	        K istanzaGenerica = null;
	        try {
				istanzaGenerica = (K) classeGenerica.newInstance();
			} catch (InstantiationException e4) {
				e4.printStackTrace();
			} catch (IllegalAccessException e5) {
				e5.printStackTrace();
			}
			Method add = FieldUtil.getMethod("add", List.class);
	        try {
	        	listaDaFillare.setAccessible(true);
				add.invoke(listaDaFillare.get(padreDellaListaDaFillare), classeGenerica.cast(istanzaGenerica));
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e2) {
				e2.printStackTrace();
			} catch (IllegalAccessException e3) {
				e3.printStackTrace();
			} catch (InvocationTargetException e4) {
				e4.printStackTrace();
			}
	        TheFiller filler = new TheFiller(istanzaGenerica);
			filler.fill();
        } else {
        	try {
        		listaDaFillare.setAccessible(true);
				TheFiller filler = new TheFiller((List<T>) listaDaFillare.get(padreDellaListaDaFillare), classeGenerica);
				filler.fill();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
        }
	}
}
