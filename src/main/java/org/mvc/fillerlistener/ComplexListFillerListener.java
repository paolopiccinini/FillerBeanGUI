package org.mvc.fillerlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mvc.filler.InnerFiller;

public class ComplexListFillerListener<T> implements ActionListener {

	List<T> listaDaFillare;
	T elemento;
    JFrame telaio;
    JPanel pannello;

    public ComplexListFillerListener(List<T> listToFill, JFrame frame, JPanel panel, T element){
    	listaDaFillare = listToFill;
        elemento = element;
        telaio = frame;
        pannello = panel;
    }

    public void actionPerformed(ActionEvent e) {
    	listaDaFillare.add(elemento);
        if(InnerFiller.openFrame > 1) {
			InnerFiller.openFrame--;
		} else {
			InnerFiller.latch.countDown();
		}
        telaio.dispose();
    }
}
