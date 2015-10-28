package reversej.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;          
import javax.swing.filechooser.FileFilter;

import reversej.controller.Controller;

import java.awt.*;
import java.io.File;


public class ScreenControl {
	private static Controller controller;
	public static void main(String[] args) {
		
		Dimension size = new Dimension(150, 30);
		JButton buttonStart = new JButton("Start");
		JButton buttonStop = new JButton("Stop");
		JButton buttonReset = new JButton("Reset");
		JButton buttonSave = new JButton("Save");

		buttonStart.setPreferredSize(size);
		buttonStop.setPreferredSize(size);
		buttonReset.setPreferredSize(size);
		buttonSave.setPreferredSize(size);
//		buttonStart.setSize(size);
//		buttonStop.setSize(size);
//		buttonReset.setSize(size);
//		buttonSave.setSize(size);
		
		SpringLayout spring = new SpringLayout();
		JPanel panel = new JPanel();		
		int distance = 15;
		
		spring.putConstraint(SpringLayout.NORTH, buttonStart, distance, SpringLayout.NORTH, panel);
		spring.putConstraint(SpringLayout.NORTH, buttonReset, distance, SpringLayout.SOUTH, buttonStop);
		spring.putConstraint(SpringLayout.NORTH, buttonStop, distance, SpringLayout.SOUTH, buttonStart);
		spring.putConstraint(SpringLayout.NORTH, buttonSave, distance, SpringLayout.SOUTH, buttonReset);
		spring.putConstraint(SpringLayout.WEST, buttonStart, distance, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.WEST, buttonReset, distance, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.WEST, buttonStop, distance, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.WEST, buttonSave, distance, SpringLayout.WEST, panel);
//		spring.putConstraint(SpringLayout.EAST, buttonStart, -distance, SpringLayout.EAST, panel);
//		spring.putConstraint(SpringLayout.EAST, buttonReset, -distance, SpringLayout.EAST, panel);
//		spring.putConstraint(SpringLayout.EAST, buttonStop, -distance, SpringLayout.EAST, panel);
//		spring.putConstraint(SpringLayout.EAST, buttonSave, -distance, SpringLayout.EAST, panel);
		
		panel.setLayout(spring);
		panel.add(buttonStart);
		panel.add(buttonStop);
		panel.add(buttonReset);
		panel.add(buttonSave);
		
		JFrame janela = new JFrame("ReverseJ");
		janela.setMinimumSize(new Dimension(200,250));
//		SwingUtilities.updateComponentTreeUI(janela);
		
		janela.add(panel);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.pack();
		janela.setVisible(true);
		
		buttonStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				controller.start();
				updateState(controller.getState().toString());
			}});
		buttonStop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				controller.stop();
			}});
		buttonReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				controller.reset();
			}});
		buttonSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new FileFilter() {
					public String getDescription() {
						return "UML Files";
					}
					public boolean accept(File f) {
						return f.getName().toLowerCase().endsWith(".uml")
					              || f.isDirectory();
					}
				});
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
					  controller.save(fileChooser.getSelectedFile());
			}});
		
		System.out.println("fez processamento"+5+2);
	}
	
	public static void updateState(String estado){
		
	}
}
