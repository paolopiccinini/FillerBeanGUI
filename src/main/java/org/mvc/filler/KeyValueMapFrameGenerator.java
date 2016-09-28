package org.mvc.filler;

import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Created by A86N on 20/09/2016.
 * @param <U>
 * @param <T>
 */
public class KeyValueMapFrameGenerator<T, U> implements FrameGenerator {
	
	private Map<T, U> mappaDaFillare;
	private T chiave;
	private U valore;
	public static final String HASHMAP = "HashMap";
	public static final String ARRAYLIST = "ArrayList";
	public static final String ENUM = "Enum";
	public static final String PRIMITIVE = "Primitive";
	public static final String CUSTOM = "Custom";
	public static final String OPENFILLER = "Apri filler della classe";
	public static final String ADDELEMENT = "Aggiungi elemento";
	public static final String KEY = "key";
	public static final String VALUE = "value";
	
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
    
    
    public KeyValueMapFrameGenerator(Map<T, U> mapToFill, T key, U value){
    	mappaDaFillare = mapToFill;
    	chiave = key;
    	valore = value;
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
	public void generateRow(String type) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
    	GroupLayout.ParallelGroup vParallelGroupField = groupLayout.createParallelGroup();
    	String tipoOggetto = Validator.getGenericInstanceClass((type.equals(KEY) ? chiave : valore).getClass());
    	JTextField tipo = new JTextField();
        tipo.setText((type.equals(KEY) ? chiave : valore).getClass().getName());
        tipo.setEditable(false);
        JTextField nome = new JTextField();
        nome.setText(type.equals(KEY) ? KEY : VALUE);
        nome.setEditable(false);
        vParallelGroupField.addComponent(tipo);
        vParallelGroupField.addComponent(nome);
        if(ENUM.equals(tipoOggetto)){
    		T[] enumsConstants = (T[])((type.equals(KEY) ? chiave : valore).getClass().getEnumConstants());
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
            addFrameListener(button, type.equals(KEY) ? KEY : VALUE);
            hParallelGroup3.addComponent(button);
            vParallelGroupField.addComponent(button);
    	} else if(CUSTOM.equals(tipoOggetto)){
    		JButton button = new JButton(OPENFILLER);
            addFrameListener(button, type.equals(KEY) ? KEY : VALUE);
            hParallelGroup3.addComponent(button);
            vParallelGroupField.addComponent(button);
    	}
        hParallelGroup1.addComponent(tipo);
        hParallelGroup2.addComponent(nome);
        versGroup.addGroup(vParallelGroupField);
    }
    
    public void generateButton(){
    	JButton fillButton = new JButton("Fill");
		addFillerListener(fillButton);
        hParallelGroup3.addComponent(fillButton);
        vParallelGroupButton.addComponent(fillButton);
    }
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addFrameListener(JButton button, String type) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String fieldType = Validator.getGenericInstanceClass((type.equals(KEY) ? chiave : valore).getClass());
		ActionListener listener;
		if (ARRAYLIST.equals(fieldType)){
			if(type.equals(KEY))
				listener = new ArrayListInMapFrameListener(chiave, null, panel, frame);
			else
				listener = new ArrayListInMapFrameListener(null, valore, panel, frame);
		}
		else if (HASHMAP.equals(fieldType)){
			if(type.equals(KEY))
				listener = new HashMapInMapFrameListener(chiave, null, panel, frame);
			else
				listener = new HashMapInMapFrameListener(null, valore, panel, frame);
		}
		else{
			if(type.equals(KEY))
				listener = new CustomInMapFrameListener(chiave, null, panel, frame);
			else
				listener = new CustomInMapFrameListener(null, valore, panel, frame);
		}
		button.addActionListener(listener);
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void addFillerListener(JButton button) {
    	ActionListener listener = new KeyValueMapFillerListener(mappaDaFillare, frame, panel, chiave, valore);
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
    	generateRow(KEY);
    	generateRow(VALUE);
    	generateButton();
    	framePacker("java.util.Map");
    }
}
