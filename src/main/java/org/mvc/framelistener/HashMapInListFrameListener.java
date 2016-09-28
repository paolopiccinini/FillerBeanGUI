package org.mvc.framelistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mvc.filler.InnerFiller;
import org.mvc.util.Validator;

public class HashMapInListFrameListener<T, U, V> implements ActionListener {

	T elementoDaFillare;
	String[] classeDeiParametriDellElementoDaFillare;
	JPanel pannello;
	JFrame telaio;
	
	public HashMapInListFrameListener(T elementToFill, String[] elementParamClass, JPanel panel, JFrame frame) {
		elementoDaFillare = elementToFill;
		classeDeiParametriDellElementoDaFillare = elementParamClass;
		pannello = panel;
		telaio = frame;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e) {
		InnerFiller.openFrame++;
		Class<?> classeDellaChiave = null;
		Class<?> classeDelValore = null;
		try {
			classeDellaChiave = Class.forName(classeDeiParametriDellElementoDaFillare[0]);
			classeDelValore = Class.forName(classeDeiParametriDellElementoDaFillare[1]);
		} catch (ClassNotFoundException e6) {
			e6.printStackTrace();
		}
		U chiave = null;
        V valore = null;
		try {
			InnerFiller filler = null;
			if (!Validator.isPrimitive(classeDelValore) && !Validator.isPrimitive(classeDellaChiave) && !Validator.isEnum(classeDelValore) && !Validator.isEnum(classeDellaChiave)) {
				chiave = (U) classeDellaChiave.newInstance();
				valore = (V) classeDelValore.newInstance();
				filler = new InnerFiller((Map<U, V>) elementoDaFillare,	chiave, null, valore, null);
			} else if ((Validator.isPrimitive(classeDelValore) || Validator.isEnum(classeDelValore))
					&& (Validator.isPrimitive(classeDellaChiave) || Validator.isEnum(classeDellaChiave))) {
				filler = new InnerFiller((Map<U, V>) elementoDaFillare, classeDellaChiave, classeDelValore);
			} else if (Validator.isPrimitive(classeDelValore) || Validator.isEnum(classeDelValore)) {
				chiave = (U) classeDellaChiave.newInstance();
				filler = new InnerFiller((Map<U, V>) elementoDaFillare, chiave, null, classeDelValore);
			} else {
				valore = (V) classeDelValore.newInstance();
				filler = new InnerFiller((Map<U, V>) elementoDaFillare, classeDellaChiave, valore, null);
			}
			filler.fill();
		} catch (InstantiationException e3) {
			e3.printStackTrace();
		} catch (IllegalAccessException e4) {
			e4.printStackTrace();
		}
	}

}
