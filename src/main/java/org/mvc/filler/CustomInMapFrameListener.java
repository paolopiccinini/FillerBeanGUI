package org.mvc.filler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
		
		TheFiller filler = new TheFiller(chiaveDaFillare != null ? chiaveDaFillare : valoreDaFillare);
		filler.fill();
	}

}
