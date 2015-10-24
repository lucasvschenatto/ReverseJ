package reversej.controller;

import java.util.LinkedList;
import java.util.List;

import reversej.diagram.Diagram;
import reversej.diagram.DiagramEngine;
import reversej.diagram.DiagramStrategy;
import reversej.diagram.InformationFactory;
import reversej.diagram.RepositoryProvider;
import reversej.diagram.informationmodel.InformationFactoryImpl;
import reversej.diagram.strategies.ClassDiagram;
import reversej.diagram.strategies.SequenceDiagram;
import reversej.file.FileDiagram;
import reversej.repository.RepositoryInMemory;
import reversej.tracer.RepositoryRecorder;
import reversej.tracer.Tracer;
import reversej.tracer.TracerImmunity;

import java.io.File;

public class Controller implements TracerImmunity {
	private ControllerState state;
	private static RepositoryProvider provider;
	private static RepositoryRecorder recorder;
	public Controller(){
		state = ControllerState.INITIAL;
		RepositoryInMemory i = new RepositoryInMemory();
		recorder = i;
		provider = i;
	}

	public boolean start(){
		return state.start(this);
	}
	public boolean stop(){
		return state.stop(this);
	}
	public boolean save(java.io.File fileToSave){
		return state.save(this, fileToSave);
	}
	public boolean reset(){
		return state.reset(this);
	}
	
	
	
	void setState(ControllerState state){
		this.state = state;
	}
	void startTracer(){
		Tracer.start(recorder);
	}
	void stopTracer(){
		Tracer.stop();
	}
	void saveDiagram(File fileToSave){
		Diagram diagram = makeDiagrams();
		saveDiagramInFile(diagram, fileToSave);
	}
	void resetRepository(){
		RepositoryInMemory i = new RepositoryInMemory();
		recorder = i;
		provider = i;
	}
	void resetDiagram(){
		Diagram.resetInstance();
	}
	
	
	
	
	
	private static void saveDiagramInFile(Diagram diagram, File fileToSave) {
		FileDiagram file = new FileDiagram(diagram, fileToSave);
		file.save();
	}
	private static Diagram makeDiagrams() {
		List<DiagramStrategy> diagramStrategies = new LinkedList<DiagramStrategy>();
		diagramStrategies.add(new ClassDiagram());
		diagramStrategies.add(new SequenceDiagram());
		InformationFactory factory = new InformationFactoryImpl();
		Diagram diagram = new DiagramEngine(provider,factory, diagramStrategies).make();
		return diagram;
	}
	public ControllerState getState() {
		return state;
	}
	public RepositoryRecorder getRecorder() {
		return recorder;
	}
	public RepositoryProvider getProvider() {
		return provider;
	}
}
