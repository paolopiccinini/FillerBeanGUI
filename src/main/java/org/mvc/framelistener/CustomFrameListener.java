package org.mvc.framelistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import org.mvc.filler.InnerFiller;
import org.mvc.util.FieldUtil;

public class CustomFrameListener<T> implements ActionListener {

	T padreOggettoDaFillare;
	Field oggettoDaFillare;
	
	public CustomFrameListener(Field objectToFill, T objectToFillFather){
		oggettoDaFillare = objectToFill;
		padreOggettoDaFillare = objectToFillFather;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e) {
		InnerFiller.openFrame++;
		InnerFiller filler = null;
		try {
			if(FieldUtil.getProperty(padreOggettoDaFillare, oggettoDaFillare) == null) {
				T nuovaIstanza = null;
				try {
					nuovaIstanza = (T) oggettoDaFillare.getType().newInstance();
					FieldUtil.setProperty(padreOggettoDaFillare, oggettoDaFillare, nuovaIstanza);
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				filler = new InnerFiller(nuovaIstanza);
			} else {
				filler = new InnerFiller(FieldUtil.getProperty(padreOggettoDaFillare, oggettoDaFillare));
			}
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		filler.fill();
	}
}
