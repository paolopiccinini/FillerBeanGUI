package org.mvc.filler;

import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CustomFrameGenerator<T> implements FrameGenerator {
	
	private T oggettoDaFillare;
	private ArrayList<Field> campi;
	public static final String HASHMAP = "HashMap";
	public static final String ARRAYLIST = "ArrayList";
	public static final String ENUM = "Enum";
	public static final String PRIMITIVE = "Primitive";
	public static final String CUSTOM = "Custom";
	public static final String INVALID = "Invalid";
	public static final String INFILLABLE = "Campo non fillabile";
	public static final String OPENFILLER = "Apri filler della classe";
	public static final String ADDELEMENT = "Aggiungi elemento";
	
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
    
    
    public CustomFrameGenerator(T objectToFill, ArrayList<Field> fields){
    	oggettoDaFillare = objectToFill;
    	campi = fields;
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
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //da valutare
    	panel.setLayout(groupLayout);                        
    	groupLayout.setAutoCreateGaps(true);                 
    	groupLayout.setAutoCreateContainerGaps(true);
    	tipoLabel = new JLabel("Tipo parametro");
    	nomeLabel = new JLabel("Nome parametro");
    	valoreLabel = new JLabel("Valore");
    	hParallelGroup1.addComponent(tipoLabel);
        hParallelGroup2.addComponent(nomeLabel);
        hParallelGroup3.addComponent(valoreLabel);
        vParallelGroup.addComponent(tipoLabel);
        vParallelGroup.addComponent(nomeLabel);
        vParallelGroup.addComponent(valoreLabel);
        versGroup.addGroup(vParallelGroup);
    }
    
    @SuppressWarnings("unchecked")
	public void generateRow(Field field) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
    	GroupLayout.ParallelGroup vParallelGroupField = groupLayout.createParallelGroup();
    	String tipoOggetto = Validator.getGenericInstanceClass(field.getType());
    	JTextField tipo = new JTextField();
        tipo.setText(field.getType().getName());
        tipo.setEditable(false);
        JTextField nome = new JTextField();
        nome.setText(field.getName());
        nome.setEditable(false);
        vParallelGroupField.addComponent(tipo);
        vParallelGroupField.addComponent(nome);
        if(ENUM.equals(tipoOggetto)){
    		T[] enumsConstants = (T[])field.getType().getEnumConstants();
        	int i = 0;
        	String[] options = new String[enumsConstants.length];
            for(T constant: enumsConstants) {
            	options[i++] = constant.toString();
            }
        	JComboBox box = new JComboBox(options);
        	box.setSelectedItem(null);
        	hParallelGroup3.addComponent(box);
            vParallelGroupField.addComponent(box);
    	}else if(ARRAYLIST.equals(tipoOggetto) || HASHMAP.equals(tipoOggetto)){
    		JButton button = new JButton(ADDELEMENT);
            addFrameListener(button, field);
            hParallelGroup3.addComponent(button);
            vParallelGroupField.addComponent(button);
    	} else if(CUSTOM.equals(tipoOggetto)){
    		JButton button = new JButton(OPENFILLER);
            addFrameListener(button, field);
            hParallelGroup3.addComponent(button);
            vParallelGroupField.addComponent(button);
    	} else{
    		JTextField valore = new JTextField();
        	if(PRIMITIVE.equals(tipoOggetto)){
                valore.setEditable(true);
        	}else if(INVALID.equals(tipoOggetto)){
        		valore.setText(INFILLABLE);
        		valore.setEditable(false);
        	}
        	hParallelGroup3.addComponent(valore);
            vParallelGroupField.addComponent(valore);
    	}
        hParallelGroup1.addComponent(tipo);
        hParallelGroup2.addComponent(nome);
        versGroup.addGroup(vParallelGroupField);
    }
    
    public void generateButton(){
    	JButton fillButton = null;
		String classType = Validator.getGenericInstanceClass(oggettoDaFillare.getClass());
		if (ARRAYLIST.equals(classType) || HASHMAP.equals(classType)) {
			fillButton = new JButton("Add");
		} else if (PRIMITIVE.equals(classType) || ENUM.equals(classType) || CUSTOM.equals(classType)) {
			fillButton = new JButton("Fill");
		}
		addFillerListener(fillButton);
        hParallelGroup3.addComponent(fillButton);
        vParallelGroupButton.addComponent(fillButton);
    }
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addFrameListener(JButton button, Field field) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String fieldType = Validator.getGenericInstanceClass(field.getType());
		ActionListener listener;
		if (ARRAYLIST.equals(fieldType))
			listener = new ArrayListFrameListener(field, oggettoDaFillare);
		else if (HASHMAP.equals(fieldType))
			listener = new HashMapFrameListener(field, oggettoDaFillare);
		else
			listener = new CustomFrameListener(field, oggettoDaFillare);
		button.addActionListener(listener);
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void addFillerListener(JButton button) {
    	ActionListener listener = new FillerListener(oggettoDaFillare, panel, frame);
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
    	for(Field campo : campi){
    		if (campo.getName().equals("serialVersionUID")) {
                continue;
            }
    		generateRow(campo);
    	}
    	generateButton();
    	framePacker(oggettoDaFillare.getClass().getName());
    }
}
