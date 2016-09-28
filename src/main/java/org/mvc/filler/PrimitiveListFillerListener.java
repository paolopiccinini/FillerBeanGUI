package org.mvc.filler;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PrimitiveListFillerListener<T> implements ActionListener {

	List<T> listaDaFillare;
	JFrame telaio;
	JPanel pannello;
	
	public PrimitiveListFillerListener(List<T> listToFill, JFrame frame, JPanel panel){
		listaDaFillare = listToFill;
		telaio = frame;
		pannello = panel;
	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent arg0) {
		Map<String, String> map = new HashMap<String, String>();
		Component[] comp = pannello.getComponents();
		for (int i = 4; i < pannello.getComponents().length - 1; i = i + 3) {
			JTextField nome = (JTextField) comp[i];
			if (comp[i + 1].getClass() == JTextField.class) {
				JTextField valore = (JTextField) comp[i + 1];
				map.put(nome.getText(), valore.getText());
			}
		}
		listaDaFillare.add((T) map.get("element"));
		telaio.dispose();
	}
}
