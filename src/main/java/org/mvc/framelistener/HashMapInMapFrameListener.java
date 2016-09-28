package org.mvc.framelistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mvc.filler.InnerFiller;
import org.mvc.util.Validator;

public class HashMapInMapFrameListener<T, U, V, Z> implements ActionListener {

	T chiaveDaFillare;
	String[] classeDeiParametriDellaChiave;
	U valoreDaFillare;
	String[] classeDeiParametriDelValore;
	JPanel pannello;
	JFrame telaio;
	
	public HashMapInMapFrameListener(T keyToFill, String[] keyParamClass, U valueToFill, String[] valueParamClass, JPanel panel, JFrame frame) {
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
		Class<?> classeDellaChiave = null;
		Class<?> classeDelValore = null;
		try {
			classeDellaChiave = Class.forName(chiaveDaFillare != null ? classeDeiParametriDellaChiave[0] : classeDeiParametriDelValore[0]);
			classeDelValore = Class.forName(chiaveDaFillare != null ? classeDeiParametriDellaChiave[1] : classeDeiParametriDelValore[1]);
		} catch (ClassNotFoundException e6) {
			e6.printStackTrace();
		}
		V chiave = null;
        Z valore = null;
		try {
			InnerFiller filler = null;
			if (!Validator.isPrimitive(classeDelValore) && !Validator.isPrimitive(classeDellaChiave) && !Validator.isEnum(classeDelValore) && !Validator.isEnum(classeDellaChiave)) {
				chiave = (V) classeDellaChiave.newInstance();
				valore = (Z) classeDelValore.newInstance();
				
//				((Map<V, Z>)(chiaveDaFillare != null ? chiaveDaFillare : valoreDaFillare)).put(chiave, valore);
				
				filler = new InnerFiller((Map<V, Z>) (chiaveDaFillare != null ? chiaveDaFillare : valoreDaFillare),	chiave, null, valore, null);
			} else if ((Validator.isPrimitive(classeDelValore) || Validator.isEnum(classeDelValore))
					&& (Validator.isPrimitive(classeDellaChiave) || Validator.isEnum(classeDellaChiave))) {
				filler = new InnerFiller((Map<V, Z>) (chiaveDaFillare != null ? chiaveDaFillare : valoreDaFillare), classeDellaChiave, classeDelValore);
			} else if (Validator.isPrimitive(classeDelValore) || Validator.isEnum(classeDelValore)) {
				chiave = (V) classeDellaChiave.newInstance();
				filler = new InnerFiller((Map<V, Z>) (chiaveDaFillare != null ? chiaveDaFillare : valoreDaFillare), chiave, null, classeDelValore);
			} else {
				valore = (Z) classeDelValore.newInstance();
				filler = new InnerFiller((Map<V, Z>) (chiaveDaFillare != null ? chiaveDaFillare : valoreDaFillare), classeDellaChiave, valore, null);
			}
			filler.fill();
		} catch (InstantiationException e3) {
			e3.printStackTrace();
		} catch (IllegalAccessException e4) {
			e4.printStackTrace();
		}
	}


}
