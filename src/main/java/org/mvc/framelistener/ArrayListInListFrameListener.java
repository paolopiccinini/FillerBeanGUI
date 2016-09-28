package org.mvc.framelistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mvc.filler.InnerFiller;
import org.mvc.util.FieldUtil;
import org.mvc.util.StringConstants;
import org.mvc.util.Validator;

public class ArrayListInListFrameListener<T, U> implements ActionListener {

	T elementoDaFillare;
	String[] classeDeiParametriDellElementoDaFillare;
	JPanel pannello;
	JFrame telaio;
	
	public ArrayListInListFrameListener(T elementToFill, String[] elementParamClass, JPanel panel, JFrame frame) {
		elementoDaFillare = elementToFill;
		classeDeiParametriDellElementoDaFillare = elementParamClass;
		pannello = panel;
		telaio = frame;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e) {
		InnerFiller.openFrame++;
		Class<?> classeParametro = null;
		try {
			classeParametro = Class.forName(classeDeiParametriDellElementoDaFillare[0]);
		} catch (ClassNotFoundException e6) {
			e6.printStackTrace();
		}
		if(!Validator.tipiPrimitivi.containsKey(classeParametro) && !Validator.isEnum(classeParametro)){
	        U istanzaGenerica = null;
	        try {
				istanzaGenerica = (U) classeParametro.newInstance();
			} catch (InstantiationException e4) {
				e4.printStackTrace();
			} catch (IllegalAccessException e5) {
				e5.printStackTrace();
			}
			Method add = FieldUtil.getMethod(StringConstants.ADD, List.class);
	        try {
				add.invoke(elementoDaFillare, classeParametro.cast(istanzaGenerica));
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e2) {
				e2.printStackTrace();
			} catch (IllegalAccessException e3) {
				e3.printStackTrace();
			} catch (InvocationTargetException e4) {
				e4.printStackTrace();
			}
	        InnerFiller filler = new InnerFiller(istanzaGenerica);
			filler.fill();
        } else {
        	try {
				InnerFiller filler = new InnerFiller((List<U>) elementoDaFillare, classeParametro);
				filler.fill();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			}
        }
	}
}
