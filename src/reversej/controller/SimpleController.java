package reversej.controller;

import java.util.LinkedList;
import java.util.List;

import reversej.diagram.Diagram;
import reversej.diagram.DiagramHandler;
import reversej.diagram.DiagramStrategy;
import reversej.diagram.RepositoryProvider;
import reversej.diagram.strategies.ClassDiagram;
import reversej.diagram.strategies.SequenceDiagram;
import reversej.file.FileDiagramInPackage;
import reversej.repository.RepositoryInformation;
import reversej.tracer.Tracer;
import reversej.tracer.TracerImmunity;

public abstract class SimpleController implements TracerImmunity {
	private static String fileName;
	private static RepositoryProvider provider;
	public static boolean start(String fileName){
		if(!Tracer.isRunning()){
			SimpleController.fileName = fileName;
			RepositoryInformation i = new RepositoryInformation();
			provider = i;		
			Tracer.start(i);
			return true;
		}
		return false;
	}
	public boolean stop(){
		if(Tracer.isRunning()){
			Tracer.stop();			
			Diagram diagram = makeDiagrams();
			saveDiagramInFile(diagram);
			return true;
		}
		return false;
	}
	public boolean reset(){
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
		Diagram diagram = new DiagramHandler(provider, diagramStrategies).make();
		return diagram;
	}
}
