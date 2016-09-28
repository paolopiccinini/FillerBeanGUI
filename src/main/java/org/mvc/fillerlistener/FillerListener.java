package org.mvc.fillerlistener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.mvc.util.FieldUtil;

public class FillerListener<T> implements ActionListener {

	T oggettoDaFillare;
	JPanel pannello;
	JFrame telaio;
	
	public FillerListener(T objectTofill, JPanel panel, JFrame frame){
		oggettoDaFillare = objectTofill;
		pannello = panel;
		telaio = frame;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(oggettoDaFillare != null){
			ArrayList<Field> fields = FieldUtil.retrieveFields(oggettoDaFillare);
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
			for(Field field : fields){
				if(map.get(field.getName()) != null && !map.get(field.getName()).equals("")){
					try {
						FieldUtil.setProperty(oggettoDaFillare, field, FieldUtil.stringToObject(field, map.get(field.getName())));
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		System.out.println(oggettoDaFillare); //TODO delete
		telaio.dispose();
	}

}
