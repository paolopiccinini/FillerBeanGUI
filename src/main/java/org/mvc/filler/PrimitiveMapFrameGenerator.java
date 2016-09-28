package org.mvc.filler;

import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Created by A86N on 20/09/2016.
 */
public class PrimitiveMapFrameGenerator<T, U> implements FrameGenerator {
    public static final String KEY = "key";
    public static final String VALUE = "value";
    Map<T, U> mappaDaFillare;
    Class<?> classeDellaChiave;
    Class<?> classeDelValore;
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


    public PrimitiveMapFrameGenerator(Map<T, U> mapToFill, Class<?> keyClass, Class<?> valueClass){
        mappaDaFillare = mapToFill;
        classeDellaChiave = keyClass;
        classeDelValore = valueClass;
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

    public void generateRow(Class<?> clazz, String type) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        GroupLayout.ParallelGroup vParallelGroupField = groupLayout.createParallelGroup();
        JTextField tipo = new JTextField();
        tipo.setText(clazz.getName());
        tipo.setEditable(false);
        JTextField nome = new JTextField();
        nome.setText(type);
        nome.setEditable(false);
        vParallelGroupField.addComponent(tipo);
        vParallelGroupField.addComponent(nome);
        JTextField valore = new JTextField();
        valore.setEditable(true);
        hParallelGroup3.addComponent(valore);
        vParallelGroupField.addComponent(valore);
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

    public void addFillerListener(JButton button) {
        ActionListener listener = new PrimitiveMapFillerListener<T, U>(mappaDaFillare, frame, panel);
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

    public void generateFrame() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        generateRow(classeDellaChiave, KEY);
        generateRow(classeDelValore, VALUE);
        generateButton();
        framePacker("java.util.Map");
    }
}
