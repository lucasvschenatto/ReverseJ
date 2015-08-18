package acceptance;

import java.util.LinkedList;
import java.util.List;

import acceptance.story.Story;
import reverseJ.DiagramMaker;
import reverseJ.DiagramStrategy;
import reverseJ.InformationProvider;
import reverseJ.InformationStorageProvider;
import reverseJ.MakerAndSaver;
import reverseJ.RecorderStorage;
import reverseJ.SequenceDiagram;
import reverseJ.Tracer;

public class StorySequenceDiagramTest {
	public static void main(String[] args) {
		InformationStorageProvider i = new InformationStorageProvider();
		RecorderStorage r = i;
		InformationProvider p = i;
		DiagramStrategy dS = new SequenceDiagram();
		List<DiagramStrategy> lds = new LinkedList<DiagramStrategy>();
		lds.add(dS);
		DiagramMaker dM = new MakerAndSaver(p, lds);
		((MakerAndSaver)dM).setFileName("diagramaDeSequencia");
		Tracer.start(r);
		
		Story s = new Story();
		s.tellStory();
		
		Tracer.stop();
		for (String info : r.describeAll()) {
			System.out.println(info);
		}
		dM.make();
	}
}
