package org.mvc.fillerlistener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.mvc.filler.InnerFiller;
import org.mvc.util.FieldUtil;
import org.mvc.util.StringConstants;

public class CustomFillerListener<T> implements ActionListener {

	T oggettoDaFillare;
	JPanel pannello;
	JFrame telaio;
	
	public CustomFillerListener(T objectTofill, JPanel panel, JFrame frame){
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
				if(map.get(field.getName()) != null && !map.get(field.getName()).equals(StringConstants.EMPTY)){
					try {
						FieldUtil.setProperty(oggettoDaFillare, field, FieldUtil.stringToObject(field, map.get(field.getName())));
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		if(InnerFiller.openFrame > 1) {
			InnerFiller.openFrame--;
		} else {
			String path = System.getProperty(StringConstants.USERDIR) + StringConstants.FOLDER;
        	File f = new File(path + oggettoDaFillare.getClass().getName());
        	PrintWriter pw = null;
        	try {
        		pw = new PrintWriter(f);
//        		pw.write(new XStream().toXML(oggettoDaFillare));
//        		System.out.println("stampo oggetto finiti: \n" + new XStream().toXML(oggettoDaFillare));
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	} finally {
        		pw.close();
        	}  
			InnerFiller.latch.countDown();
		}
		telaio.dispose();
	}

}
