package org.mvc.fillerlistener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.mvc.filler.InnerFiller;
import org.mvc.util.StringConstants;

/**
 * Created by A86N on 20/09/2016.
 */
public class PrimitiveMapFillerListener<T, U> implements ActionListener {

    Map<T, U> mappaDaFillare;
    JFrame telaio;
    JPanel pannello;

    public PrimitiveMapFillerListener(Map<T, U> mapToFill, JFrame frame, JPanel panel){
        mappaDaFillare = mapToFill;
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
        mappaDaFillare.put((T) map.get(StringConstants.KEY), (U) map.get(StringConstants.VALUE));
        if(InnerFiller.openFrame > 1) {
			InnerFiller.openFrame--;
		} else {
			InnerFiller.latch.countDown();
		}
        telaio.dispose();
    }
}
