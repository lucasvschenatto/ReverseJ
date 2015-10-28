package reversej.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;          

import java.awt.*;


public class ScreenControl {
	public static void main(String[] args) {
		
		JButton buttonStart = new JButton("Start");
		JButton buttonStop = new JButton("Stop");
		JButton buttonReset = new JButton("Reset");
		JButton buttonSave = new JButton("Save");
		Dimension size = new Dimension(150, 30);
		int distance = 15;
		buttonStart.setPreferredSize(size);
		buttonStop.setPreferredSize(size);
		buttonReset.setPreferredSize(size);
		buttonSave.setPreferredSize(size);
		buttonStart.setSize(size);
		buttonStop.setSize(size);
		buttonReset.setSize(size);
		buttonSave.setSize(size);
		
		
		buttonStart.setLocation(5,600);
		
		
		
		JPanel panel = new JPanel();
		
		SpringLayout spring = new SpringLayout();
		
		spring.putConstraint(SpringLayout.NORTH, buttonStart, distance, SpringLayout.NORTH, panel);
		spring.putConstraint(SpringLayout.NORTH, buttonStop, distance, SpringLayout.SOUTH, buttonStart);
		spring.putConstraint(SpringLayout.NORTH, buttonReset, distance, SpringLayout.SOUTH, buttonStop);
		spring.putConstraint(SpringLayout.NORTH, buttonSave, distance, SpringLayout.SOUTH, buttonReset);
		spring.putConstraint(SpringLayout.WEST, buttonStart, distance, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.WEST, buttonStop, distance, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.WEST, buttonReset, distance, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.WEST, buttonSave, distance, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.EAST, buttonStart, -distance, SpringLayout.EAST, panel);
		spring.putConstraint(SpringLayout.EAST, buttonStop, -distance, SpringLayout.EAST, panel);
		spring.putConstraint(SpringLayout.EAST, buttonReset, -distance, SpringLayout.EAST, panel);
		spring.putConstraint(SpringLayout.EAST, buttonSave, -distance, SpringLayout.EAST, panel);
		
		panel.add(buttonStart);
		panel.add(buttonStop);
		panel.add(buttonReset);
		panel.add(buttonSave);
		panel.setLayout(spring);
		
		JFrame janela = new JFrame("ReverseJ");
		janela.setMinimumSize(new Dimension(60,250));
		SwingUtilities.updateComponentTreeUI(janela);
		
		janela.add(panel);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.pack();
		janela.setVisible(true);
		
		buttonStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("hahaha");	
				buttonSave.setEnabled(false);
			}});
		buttonStop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("hehehehehe");
				buttonSave.setEnabled(true);
			}});
		
		System.out.println("fez processamento"+5+2);
	}
}
