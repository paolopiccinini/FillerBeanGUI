package org.mvc.framelistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.mvc.filler.InnerFiller;
import org.mvc.util.CustomClassLoader;
import org.mvc.util.StringConstants;
import org.mvc.util.Validator;

public class ArrayListFrameListener<T, K> implements ActionListener {

	T padreDellaListaDaFillare;
	Field listaDaFillare;
	
	public ArrayListFrameListener(Field listToFill, T listToFillFather){
		listaDaFillare = listToFill;
		padreDellaListaDaFillare = listToFillFather;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e) {
		InnerFiller.openFrame++;
        ParameterizedType tipo = (ParameterizedType) listaDaFillare.getGenericType();
        Type type = tipo.getActualTypeArguments()[0];
        Class<?> classeDellElemento = null;
        String[] classeDeiParamteriDellElemento = new String[2];
        File f = new File(System.getProperty(StringConstants.PATH));
        String dirtyClassName = type.toString();
		String className = null;
		if(dirtyClassName.substring(0, 5).equals("class")) {
			className = dirtyClassName.substring(6);
		} else {
			int startInner = dirtyClassName.indexOf("<");
			int stopInner = dirtyClassName.indexOf(">");
			className = dirtyClassName.substring(0, startInner);
			if(className.equals(StringConstants.LIST)) {
				className = StringConstants.ARRAYLISTCLASS;
				classeDeiParamteriDellElemento[0] = dirtyClassName.substring(startInner + 1, stopInner);
			} else if(className.equals(StringConstants.MAP)) {
				className = StringConstants.HASHMAPCLASS;
				classeDeiParamteriDellElemento = dirtyClassName.substring(startInner + 1, stopInner).split(", ");
			}
		}
		try {
			classeDellElemento = (Class<?>)Class.forName(className, false, CustomClassLoader.getInstance());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//        for(File jarFile : f.listFiles()) {
//        	String jar = jarFile.getName();
//        	try{
//	        	URL[] urls = { new URL("jar:file:" + f.getAbsolutePath() + "\\" + jar + "!/") };
//	        	URLClassLoader urlcl = new URLClassLoader(urls);
//	        	classeDellElemento = (Class<?>)Class.forName(className, false, urlcl);
//	        	if(classeDellElemento != null) {
//	        		break;
//	        	}
//        	} catch (MalformedURLException e2) {
//        		e2.printStackTrace();
//        	} catch (ClassNotFoundException e3) {
//        		e3.printStackTrace();
//        	}
//        }
        K elemento = null;
		try {
			boolean access = listaDaFillare.isAccessible();
			listaDaFillare.setAccessible(true);
			InnerFiller filler = null;
			if (!Validator.isPrimitive(classeDellElemento) && !Validator.isEnum(classeDellElemento)) {
				elemento = (K) classeDellElemento.newInstance();
				filler = new InnerFiller((List<K>) listaDaFillare.get(padreDellaListaDaFillare), elemento, classeDeiParamteriDellElemento);
			} else {
				filler = new InnerFiller((List<K>) listaDaFillare.get(padreDellaListaDaFillare), classeDellElemento);
			}
			listaDaFillare.setAccessible(access);
			filler.fill();
		} catch (InstantiationException e3) {
			e3.printStackTrace();
		} catch (IllegalAccessException e4) {
			e4.printStackTrace();
		}
	}
}
