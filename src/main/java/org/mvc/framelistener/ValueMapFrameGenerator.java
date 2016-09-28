package org.mvc.framelistener;

import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.mvc.fillerlistener.ValueMapFillerListener;
import org.mvc.framelistener.ArrayListInMapFrameListener;
import org.mvc.framelistener.CustomInMapFrameListener;
import org.mvc.framelistener.HashMapInMapFrameListener;
import org.mvc.util.StringConstants;
import org.mvc.util.Validator;

/**
 * Created by A86N on 20/09/2016.
 */
public class ValueMapFrameGenerator<T, U> implements FrameGenerator {
	
	Map<T, U> mappaDaFillare;
	U valore;
	String[] classeDeiParametriDelValore;
	Class<?> classeDellaChiave;
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
    
    
    public ValueMapFrameGenerator(Map<T, U> mapToFill, Class<?> keyClass, U value, String[] valueParamClass){
    	mappaDaFillare = mapToFill;
    	valore = value;
    	classeDeiParametriDelValore = valueParamClass;
    	classeDellaChiave = keyClass;
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
	public void generatePrimitiveRow() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        GroupLayout.ParallelGroup vParallelGroupField = groupLayout.createParallelGroup();
        JTextField tipo = new JTextField();
        tipo.setText(classeDellaChiave.getName());
        tipo.setEditable(false);
        JTextField nome = new JTextField();
        nome.setText(StringConstants.KEY);
        nome.setEditable(false);
        vParallelGroupField.addComponent(tipo);
        vParallelGroupField.addComponent(nome);
        if(StringConstants.ENUM.equals(Validator.getGenericInstanceClass(classeDellaChiave))){
    		T[] enumsConstants = (T[])classeDellaChiave.getEnumConstants();
        	int i = 0;
        	String[] options = new String[enumsConstants.length];
            for(T constant: enumsConstants) {
            	options[i++] = constant.toString();
            }
        	JComboBox box = new JComboBox(options);
        	box.setSelectedItem(null);
        	hParallelGroup3.addComponent(box);
            vParallelGroupField.addComponent(box);
    	} else {
	        JTextField valore = new JTextField();
	        valore.setEditable(true);
	        hParallelGroup3.addComponent(valore);
	        vParallelGroupField.addComponent(valore);
    	}
        hParallelGroup1.addComponent(tipo);
        hParallelGroup2.addComponent(nome);
        versGroup.addGroup(vParallelGroupField);
    }
    
    @SuppressWarnings("unchecked")
	public void generateObjectRow() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
    	GroupLayout.ParallelGroup vParallelGroupField = groupLayout.createParallelGroup();
    	String tipoOggetto = Validator.getGenericInstanceClass(valore.getClass());
    	JTextField tipo = new JTextField();
        tipo.setText(valore.getClass().getName());
        tipo.setEditable(false);
        JTextField nome = new JTextField();
        nome.setText(StringConstants.VALUE);
        nome.setEditable(false);
        vParallelGroupField.addComponent(tipo);
        vParallelGroupField.addComponent(nome);
        if(StringConstants.ENUM.equals(tipoOggetto)){
    		T[] enumsConstants = (T[])(valore.getClass().getEnumConstants());
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
            addFrameListener(button);
            hParallelGroup3.addComponent(button);
            vParallelGroupField.addComponent(button);
    	} else if(StringConstants.CUSTOM.equals(tipoOggetto)){
    		JButton button = new JButton(StringConstants.OPENFILLER);
            addFrameListener(button);
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
	public void addFrameListener(JButton button) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String fieldType = Validator.getGenericInstanceClass(valore.getClass());
		ActionListener listener;
		if (StringConstants.ARRAYLIST.equals(fieldType)){
			listener = new ArrayListInMapFrameListener(null, null, valore, classeDeiParametriDelValore, panel, frame);
		}
		else if (StringConstants.HASHMAP.equals(fieldType)){
			listener = new HashMapInMapFrameListener(null, null, valore, classeDeiParametriDelValore, panel, frame);
		}
		else{
			listener = new CustomInMapFrameListener(null, valore, panel, frame);
		}
		button.addActionListener(listener);
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void addFillerListener(JButton button) {
    	ActionListener listener = new ValueMapFillerListener(mappaDaFillare, frame, panel, valore);
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
    	generatePrimitiveRow();    	
    	generateObjectRow();
    	generateButton();
    	framePacker(StringConstants.MAP);
    }
}
