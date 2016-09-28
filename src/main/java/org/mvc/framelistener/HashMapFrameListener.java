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
import java.util.Map;

import org.mvc.filler.InnerFiller;
import org.mvc.util.CustomClassLoader;
import org.mvc.util.StringConstants;
import org.mvc.util.Validator;

public class HashMapFrameListener<T, U, V> implements ActionListener {
	
	T padreDellaMappaDaFillare;
	Field mappaDaFillare;
	
	public HashMapFrameListener(Field mapToFill, T mapToFillFather){
		mappaDaFillare = mapToFill;
		padreDellaMappaDaFillare = mapToFillFather;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e) {
		InnerFiller.openFrame++;
        ParameterizedType tipo = (ParameterizedType) mappaDaFillare.getGenericType();
        Class<?> classeDellaChiave = null;
		Class<?> classeDelValore = null;
		String[] classeDeiParametriDellaChiave = new String[2];
		String[] classeDeiParametriDelValore = new String[2];
		int i = 0;
		for(Type type : tipo.getActualTypeArguments()) {
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
					if(i == 0)
						classeDeiParametriDellaChiave[0] = dirtyClassName.substring(startInner + 1, stopInner);
					else if(i == 1)
						classeDeiParametriDelValore[0] = dirtyClassName.substring(startInner + 1, stopInner);;
					
				} else if(className.equals(StringConstants.MAP)) {
					className = StringConstants.HASHMAPCLASS;
					if(i == 0)
						classeDeiParametriDellaChiave = dirtyClassName.substring(startInner + 1, stopInner).split(", ");
					else if(i == 1)
						classeDeiParametriDelValore = dirtyClassName.substring(startInner + 1, stopInner).split(", ");
				}
			}
			try {
				if (i == 0) {
					classeDellaChiave = (Class<?>) Class.forName(className, false, CustomClassLoader.getInstance());
				} else if (i == 1) {
					classeDelValore = (Class<?>) Class.forName(className, false, CustomClassLoader.getInstance());
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			i++;
		}
        U chiave = null;
        V valore = null;
		try {
			boolean access = mappaDaFillare.isAccessible();
			mappaDaFillare.setAccessible(true);
			InnerFiller filler = null;
			if (!Validator.isPrimitive(classeDelValore) && !Validator.isPrimitive(classeDellaChiave)
			 && !Validator.isEnum(classeDelValore) && !Validator.isEnum(classeDellaChiave)) {
				chiave = (U) classeDellaChiave.newInstance();
				valore = (V) classeDelValore.newInstance();
				filler = new InnerFiller((Map<U, V>) mappaDaFillare.get(padreDellaMappaDaFillare),	chiave, classeDeiParametriDellaChiave, valore, classeDeiParametriDelValore);
			} else if ((Validator.isPrimitive(classeDelValore) || Validator.isEnum(classeDelValore))
					&& (Validator.isPrimitive(classeDellaChiave) || Validator.isEnum(classeDellaChiave))) {
				filler = new InnerFiller((Map<U, V>) mappaDaFillare.get(padreDellaMappaDaFillare), classeDellaChiave, classeDelValore);
			} else if (Validator.isPrimitive(classeDelValore) || Validator.isEnum(classeDelValore)) {
				chiave = (U) classeDellaChiave.newInstance();
				filler = new InnerFiller((Map<U, V>) mappaDaFillare.get(padreDellaMappaDaFillare), chiave, classeDeiParametriDellaChiave, classeDelValore);
			} else {
				valore = (V) classeDelValore.newInstance();
				filler = new InnerFiller((Map<U, V>) mappaDaFillare.get(padreDellaMappaDaFillare), classeDellaChiave, valore, classeDeiParametriDelValore);
			}
			mappaDaFillare.setAccessible(access);
			filler.fill();
		} catch (InstantiationException e3) {
			e3.printStackTrace();
		} catch (IllegalAccessException e4) {
			e4.printStackTrace();
		}

	}

}
