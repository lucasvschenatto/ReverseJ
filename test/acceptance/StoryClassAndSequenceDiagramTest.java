package acceptance;

import java.util.LinkedList;
import java.util.List;

import acceptance.story.Story;
import reversej.MakerAndSaver;
import reversej.diagram.DiagramHandler;
import reversej.diagram.DiagramStrategy;
import reversej.diagram.RepositoryProvider;
import reversej.diagram.strategies.ClassDiagram;
import reversej.diagram.strategies.SequenceDiagram;
import reversej.repository.RepositoryInformation;
import reversej.tracer.RepositoryRecorder;
import reversej.tracer.Tracer;

public class StoryClassAndSequenceDiagramTest {
	public static void main(String[] args) {
		RepositoryInformation i = new RepositoryInformation();
		RepositoryRecorder r = i;
		RepositoryProvider p = i;
		List<DiagramStrategy> lds = new LinkedList<DiagramStrategy>();
		lds.add(new ClassDiagram());
		lds.add(new SequenceDiagram());
		DiagramHandler dM = new MakerAndSaver(p, lds);
		String fileName = Thread.currentThread().getStackTrace()[1].getFileName();
		((MakerAndSaver)dM).setFileName(fileName);
		Tracer.start(r);
		
		Story s = new Story();
		s.tellStory();
		
		Tracer.stop();
		dM.make();
	}
}
