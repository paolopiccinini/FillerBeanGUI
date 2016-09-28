package org.mvc.filler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

public class CustomFrameListener<T> implements ActionListener {

	T padreOggettoDaFillare;
	Field oggettoDaFillare;
	
	public CustomFrameListener(Field objectToFill, T objectToFillFather){
		oggettoDaFillare = objectToFill;
		padreOggettoDaFillare = objectToFillFather;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e) {
		TheFiller filler = null;
		T nuovaIstanza = null;
		try {
			nuovaIstanza = (T) oggettoDaFillare.getType().newInstance();
			FieldUtil.setProperty(padreOggettoDaFillare, oggettoDaFillare, nuovaIstanza);
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		filler = new TheFiller(nuovaIstanza);
		filler.fill();
	}
}
