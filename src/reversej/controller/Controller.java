package reversej.controller;

import java.util.LinkedList;
import java.util.List;

import reversej.diagram.Diagram;
import reversej.diagram.DiagramHandler;
import reversej.diagram.DiagramStrategy;
import reversej.diagram.RepositoryProvider;
import reversej.diagram.strategies.ClassDiagram;
import reversej.diagram.strategies.SequenceDiagram;
import reversej.file.FileDiagram;
import reversej.repository.RepositoryInformation;
import reversej.tracer.Tracer;
import reversej.tracer.TracerImmunity;

public abstract class Controller implements TracerImmunity {
	private static String fileName;
	private static RepositoryProvider provider;
	public static void reset(){
		fileName = null;
		provider = null;
		if(Tracer.isRunning())
			Tracer.stop();
	}
	public static boolean start(String fileName){
		if(!Tracer.isRunning()){
			Controller.fileName = fileName;
			RepositoryInformation i = new RepositoryInformation();
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
	private static void saveDiagramInFile(Diagram diagram) {
		FileDiagram file = new FileDiagram(diagram, fileName);
		file.save();
	}
	private static Diagram makeDiagrams() {
		List<DiagramStrategy> diagramStrategies = new LinkedList<DiagramStrategy>();
		diagramStrategies.add(new ClassDiagram());
		diagramStrategies.add(new SequenceDiagram());			
		Diagram diagram = new DiagramHandler(provider, diagramStrategies).make();
		return diagram;
	}
}
