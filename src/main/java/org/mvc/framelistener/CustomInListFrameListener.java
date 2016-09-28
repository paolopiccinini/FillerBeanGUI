package org.mvc.framelistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mvc.filler.InnerFiller;

public class CustomInListFrameListener<T> implements ActionListener {

	T elementoDaFillare;
	JPanel pannello;
	JFrame telaio;
	
	public CustomInListFrameListener(T elementToFill, JPanel panel, JFrame frame){
		elementoDaFillare = elementToFill;
		pannello = panel;
		telaio = frame;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void actionPerformed(ActionEvent e) {
		InnerFiller.openFrame++;
		InnerFiller filler = new InnerFiller(elementoDaFillare);
		filler.fill();
	}

}
