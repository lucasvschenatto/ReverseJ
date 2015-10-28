package reversej.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileFilter;

import reversej.tracer.TracerImmunity;

public class UserView implements Runnable, TracerImmunity{
	private Controller controller;
	private JButton buttonStart;
	private JButton buttonStop;
	private JButton buttonReset;
	private JButton buttonSave;
	public UserView(Controller controller){
		this.controller = controller;
	}
	@Override
	public void run() {
		instanciateButtons();
		setButtonListeners();
		setState(ControllerState.INITIAL);
		
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
		
		panel.setLayout(spring);
		panel.add(buttonStart);
		panel.add(buttonStop);
		panel.add(buttonReset);
		panel.add(buttonSave);
		
		JFrame janela = new JFrame("ReverseJ");
		janela.setMinimumSize(new Dimension(400,250));
		
		
		janela.add(panel);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.pack();
		janela.setVisible(true);
		
	}
	private void instanciateButtons() {
		Dimension size = new Dimension(150, 30);
		buttonStart = new JButton("Start");
		buttonStop = new JButton("Stop");
		buttonReset = new JButton("Reset");
		buttonSave = new JButton("Save");

		buttonStart.setPreferredSize(size);
		buttonStop.setPreferredSize(size);
		buttonReset.setPreferredSize(size);
		buttonSave.setPreferredSize(size);
	}
	private void setButtonListeners() {
		buttonStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				controller.start();
				setState(controller.getState());
			}});
		buttonStop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				controller.stop();
				setState(controller.getState());
			}});
		buttonReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				controller.reset();
				setState(controller.getState());
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
				setState(controller.getState());
			}});
	}
	public void setState(ControllerState state){
		switch (state.toString()){
		case "INITIAL":
			buttonStart.setEnabled(true);
			buttonStop.setEnabled(false);
			buttonReset.setEnabled(true);
			buttonSave.setEnabled(false);
			break;
		case "TRACING":
			buttonStart.setEnabled(false);
			buttonStop.setEnabled(true);
			buttonReset.setEnabled(true);
			buttonSave.setEnabled(false);
			break;
		case "TRACED":
			buttonStart.setEnabled(false);
			buttonStop.setEnabled(false);
			buttonReset.setEnabled(true);
			buttonSave.setEnabled(true);
			break;
		case "SAVED":
			buttonStart.setEnabled(false);
			buttonStop.setEnabled(false);
			buttonReset.setEnabled(true);
			buttonSave.setEnabled(false);
			break;
		}
	}

}
