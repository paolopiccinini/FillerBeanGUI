package org.mvc.fillerlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mvc.filler.InnerFiller;

public class KeyValueMapFillerListener<T, U> implements ActionListener {
	
	Map<T, U> mappaDaFillare;
	T chiave;
	U valore;
    JFrame telaio;
    JPanel pannello;

    public KeyValueMapFillerListener(Map<T, U> mapToFill, JFrame frame, JPanel panel, T key, U value){
        mappaDaFillare = mapToFill;
        chiave = key;
        valore = value;
        telaio = frame;
        pannello = panel;
    }

    public void actionPerformed(ActionEvent e) {
        mappaDaFillare.put(chiave, valore);
        if(InnerFiller.openFrame > 1) {
			InnerFiller.openFrame--;
		} else {
			InnerFiller.latch.countDown();
		}
        telaio.dispose();
    }

}
