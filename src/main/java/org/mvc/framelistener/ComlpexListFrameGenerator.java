package org.mvc.framelistener;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.mvc.fillerlistener.ComplexListFillerListener;
import org.mvc.util.StringConstants;
import org.mvc.util.Validator;

public class ComlpexListFrameGenerator<T> implements FrameGenerator {

	List<T> listaDaFillare;
	T elemento;
	String[] classeDeiParametriDellElemento;
	JFrame frame;                             
	JPanel panel;                             
	GroupLayout groupLayout;                  
	GroupLayout.SequentialGroup horsGroup;    
	GroupLayout.SequentialGroup versGroup;    
	GroupLayout.ParallelGroup hParallelGroup1;
	GroupLayout.ParallelGroup hParallelGroup2;
	GroupLayout.ParallelGroup hParallelGroup3;
	GroupLayout.ParallelGroup vParallelGroup;
	GroupLayout.ParallelGroup vParallelGroupButton;
	JLabel tipoLabel;
    JLabel nomeLabel;
    JLabel valoreLabel;
    
    public ComlpexListFrameGenerator(List<T> listToFill, T element, String[] elementParamClass){
    	listaDaFillare = listToFill;
    	elemento = element;
    	classeDeiParametriDellElemento = elementParamClass;
    	frame = new JFrame();
    	panel = new JPanel();
    	groupLayout = new GroupLayout(panel);
    	horsGroup = groupLayout.createSequentialGroup();
    	versGroup = groupLayout.createSequentialGroup();
    	hParallelGroup1 = groupLayout.createParallelGroup();
    	hParallelGroup2 = groupLayout.createParallelGroup();
    	hParallelGroup3 = groupLayout.createParallelGroup();
    	vParallelGroup = groupLayout.createParallelGroup();
    	vParallelGroupButton = groupLayout.createParallelGroup();
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	panel.setLayout(groupLayout);                        
    	groupLayout.setAutoCreateGaps(true);                 
    	groupLayout.setAutoCreateContainerGaps(true);
    	tipoLabel = new JLabel(StringConstants.TIPO);
    	nomeLabel = new JLabel(StringConstants.NOME);
    	valoreLabel = new JLabel(StringConstants.VALORE);
    	hParallelGroup1.addComponent(tipoLabel);
        hParallelGroup2.addComponent(nomeLabel);
        hParallelGroup3.addComponent(valoreLabel);
        vParallelGroup.addComponent(tipoLabel);
        vParallelGroup.addComponent(nomeLabel);
        vParallelGroup.addComponent(valoreLabel);
        versGroup.addGroup(vParallelGroup);
    }
    
    @SuppressWarnings("unchecked")
	public void generateRow(String type) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
    	GroupLayout.ParallelGroup vParallelGroupField = groupLayout.createParallelGroup();
    	String tipoOggetto = Validator.getGenericInstanceClass(elemento.getClass());
    	JTextField tipo = new JTextField();
        tipo.setText(elemento.getClass().getName());
        tipo.setEditable(false);
        JTextField nome = new JTextField();
        nome.setText(type);
        nome.setEditable(false);
        vParallelGroupField.addComponent(tipo);
        vParallelGroupField.addComponent(nome);
        if(StringConstants.ENUM.equals(tipoOggetto)){
    		T[] enumsConstants = (T[])(elemento.getClass().getEnumConstants());
        	int i = 0;
        	String[] options = new String[enumsConstants.length];
            for(T constant: enumsConstants) {
            	options[i++] = constant.toString();
            }
        	JComboBox box = new JComboBox(options);
        	box.setSelectedItem(null);
        	hParallelGroup3.addComponent(box);
            vParallelGroupField.addComponent(box);
    	}else if(StringConstants.ARRAYLIST.equals(tipoOggetto) || StringConstants.HASHMAP.equals(tipoOggetto)){
    		JButton button = new JButton(StringConstants.ADDELEMENT);
            addFrameListener(button, type);
            hParallelGroup3.addComponent(button);
            vParallelGroupField.addComponent(button);
    	} else if(StringConstants.CUSTOM.equals(tipoOggetto)) {
    		JButton button = new JButton(StringConstants.OPENFILLER);
            addFrameListener(button, type);
            hParallelGroup3.addComponent(button);
            vParallelGroupField.addComponent(button);
    	} else if(StringConstants.INVALID.equals(tipoOggetto)) {
    		JTextField valore = new JTextField();
    		valore.setText(StringConstants.UNFILLABLE);
    		valore.setEditable(false);
    		hParallelGroup3.addComponent(valore);
            vParallelGroupField.addComponent(valore);
    	}
        hParallelGroup1.addComponent(tipo);
        hParallelGroup2.addComponent(nome);
        versGroup.addGroup(vParallelGroupField);
    }
    
    public void generateButton(){
    	JButton fillButton = new JButton(StringConstants.FILLBUTTON);
		addFillerListener(fillButton);
        hParallelGroup3.addComponent(fillButton);
        vParallelGroupButton.addComponent(fillButton);
    }
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addFrameListener(JButton button, String type) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String fieldType = Validator.getGenericInstanceClass(elemento.getClass());
		ActionListener listener;
		if (StringConstants.ARRAYLIST.equals(fieldType)){
			listener = new ArrayListInListFrameListener(elemento, classeDeiParametriDellElemento, panel, frame);
		}
		else if (StringConstants.HASHMAP.equals(fieldType)){
			listener = new HashMapInListFrameListener(elemento, classeDeiParametriDellElemento, panel, frame);
		}
		else{
			listener = new CustomInListFrameListener(elemento, panel, frame);
		}
		button.addActionListener(listener);
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void addFillerListener(JButton button) {
    	ActionListener listener = new ComplexListFillerListener(listaDaFillare, frame, panel, elemento);
    	button.addActionListener(listener);
    }
    
    public void framePacker(String title){
    	horsGroup.addGroup(hParallelGroup1);
        horsGroup.addGroup(hParallelGroup2);
        horsGroup.addGroup(hParallelGroup3);
        versGroup.addGroup(vParallelGroupButton);
        groupLayout.setHorizontalGroup(horsGroup);
        groupLayout.setVerticalGroup(versGroup);
        frame.add(panel);
        frame.setResizable(true);
        frame.setTitle(title);
        frame.pack();
        frame.setVisible(true);
    }

    public void generateFrame() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    	generateRow(StringConstants.ELEMENT);
    	generateButton();
    	framePacker(StringConstants.MAP);
    }

}
