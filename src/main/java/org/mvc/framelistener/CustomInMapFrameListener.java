package org.mvc.framelistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mvc.filler.InnerFiller;

public class CustomInMapFrameListener<T, U> implements ActionListener {

	T chiaveDaFillare;
	U valoreDaFillare;
	JPanel pannello;
	JFrame telaio;
	
	public CustomInMapFrameListener(T keyToFill, U valueToFill, JPanel panel, JFrame frame){
		chiaveDaFillare = keyToFill;
		valoreDaFillare = valueToFill;
		pannello = panel;
		telaio = frame;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void actionPerformed(ActionEvent e) {
		InnerFiller.openFrame++;
		InnerFiller filler = new InnerFiller(chiaveDaFillare != null ? chiaveDaFillare : valoreDaFillare);
		filler.fill();
	}

}
