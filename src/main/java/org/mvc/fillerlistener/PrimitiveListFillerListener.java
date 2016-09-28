package org.mvc.fillerlistener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.mvc.filler.InnerFiller;
import org.mvc.util.StringConstants;

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
	public void actionPerformed(ActionEvent e) {
		Map<String, String> map = new HashMap<String, String>();
		Component[] comp = pannello.getComponents();
		for (int i = 4; i < pannello.getComponents().length - 1; i = i + 3) {
			JTextField nome = (JTextField) comp[i];
			if (comp[i + 1].getClass() == JTextField.class) {
				JTextField valore = (JTextField) comp[i + 1];
				map.put(nome.getText(), valore.getText());
			} else if(comp[i + 1].getClass() == JComboBox.class) {
				JComboBox box = (JComboBox) comp[i + 1];
				map.put(nome.getText(), String.valueOf(box.getSelectedItem()));
			}
		}
		listaDaFillare.add((T) map.get(StringConstants.ELEMENT));
		if(InnerFiller.openFrame > 1) {
			InnerFiller.openFrame--;
		} else {
			InnerFiller.latch.countDown();
		}
		telaio.dispose();
	}
}
