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

public class ArrayListInMapFrameListener<T, U, K> implements ActionListener {
	
	T chiaveDaFillare;
	String[] classeDeiParametriDellaChiave;
	U valoreDaFillare;
	String[] classeDeiParametriDelValore;
	JPanel pannello;
	JFrame telaio;
	
	public ArrayListInMapFrameListener(T keyToFill, String[] keyParamClass, U valueToFill, String[] valueParamClass, JPanel panel, JFrame frame) {
		chiaveDaFillare = keyToFill;
		classeDeiParametriDellaChiave = keyParamClass;
		valoreDaFillare = valueToFill;
		classeDeiParametriDelValore = valueParamClass;
		pannello = panel;
		telaio = frame;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e) {
		InnerFiller.openFrame++;
		Class<?> classeParametro = null;
		try {
			classeParametro = Class.forName(chiaveDaFillare != null ? classeDeiParametriDellaChiave[0] : classeDeiParametriDelValore[0]);
		} catch (ClassNotFoundException e6) {
			e6.printStackTrace();
		}
		if(!Validator.tipiPrimitivi.containsKey(classeParametro) && !Validator.isEnum(classeParametro)){
	        K istanzaGenerica = null;
	        try {
				istanzaGenerica = (K) classeParametro.newInstance();
			} catch (InstantiationException e4) {
				e4.printStackTrace();
			} catch (IllegalAccessException e5) {
				e5.printStackTrace();
			}
			Method add = FieldUtil.getMethod(StringConstants.ADD, List.class);
	        try {
				add.invoke(chiaveDaFillare != null ? chiaveDaFillare : valoreDaFillare, classeParametro.cast(istanzaGenerica));
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
				InnerFiller filler = new InnerFiller((List<K>) (chiaveDaFillare != null ? chiaveDaFillare : valoreDaFillare), classeParametro);
				filler.fill();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			}
        }
	}

}
