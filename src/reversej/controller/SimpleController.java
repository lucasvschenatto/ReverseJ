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
import reversej.file.FileDiagramInPackage;
import reversej.repository.RepositoryInMemory;
import reversej.tracer.Tracer;
import reversej.tracer.TracerImmunity;

public abstract class SimpleController implements TracerImmunity {
	private static String fileName;
	private static RepositoryProvider provider;
	public static boolean start(String fileName){
		if(!Tracer.isRunning()){
			SimpleController.fileName = fileName;
			RepositoryInMemory i = new RepositoryInMemory();
			provider = i;		
			Tracer.start(i);
			return true;
		}
		return false;
	}
	public static boolean stop(){
		if(Tracer.isRunning()){
			Tracer.stop();			
			Diagram diagram = makeDiagrams();
			saveDiagramInFile(diagram);
			return true;
		}
		return false;
	}
	public static boolean reset(){
		fileName = null;
		provider = null;
		if(Tracer.isRunning())
			Tracer.stop();
		return true;
	}
	
	private static void saveDiagramInFile(Diagram diagram) {
		FileDiagramInPackage file = new FileDiagramInPackage(diagram, fileName);
		file.save();
	}
	private static Diagram makeDiagrams() {
		List<DiagramStrategy> diagramStrategies = new LinkedList<DiagramStrategy>();
		diagramStrategies.add(new ClassDiagram());
		diagramStrategies.add(new SequenceDiagram());	
		InformationFactory factory = new InformationFactoryImpl();
		Diagram diagram = new DiagramEngine(provider, factory, diagramStrategies).make();
		return diagram;
	}
}
