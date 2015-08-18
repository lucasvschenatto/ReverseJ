package acceptance;

import java.util.LinkedList;
import java.util.List;

import acceptance.story.Story;
import reverseJ.ClassDiagram;
import reverseJ.DiagramMaker;
import reverseJ.DiagramStrategy;
import reverseJ.InformationProvider;
import reverseJ.InformationStorageProvider;
import reverseJ.MakerAndSaver;
import reverseJ.RecorderStorage;
import reverseJ.SequenceDiagram;
import reverseJ.Tracer;

public class StoryClassAndSequenceDiagramTest {
	public static void main(String[] args) {
		InformationStorageProvider i = new InformationStorageProvider();
		RecorderStorage r = i;
		InformationProvider p = i;
		List<DiagramStrategy> lds = new LinkedList<DiagramStrategy>();
		lds.add(new SequenceDiagram());
		lds.add(new ClassDiagram());
		DiagramMaker dM = new MakerAndSaver(p, lds);
		((MakerAndSaver)dM).setFileName("classeESequencia");
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
