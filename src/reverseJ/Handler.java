package reverseJ;

import java.util.LinkedList;
import java.util.List;

public abstract class Handler implements TracerImmunity {
	private static String fileName;
	private static InformationProvider provider;
	public static void reset(){
		fileName = null;
		provider = null;
		if(Tracer.isRunning())
			Tracer.stop();
	}
	public static boolean start(String fileName){
		if(!Tracer.isRunning()){
			Handler.fileName = fileName;
			InformationStorageProvider i = new InformationStorageProvider();
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
		Diagram diagram = new DiagramMaker(provider, diagramStrategies).make();
		return diagram;
	}
}
