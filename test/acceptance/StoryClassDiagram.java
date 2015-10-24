package acceptance;

import java.util.LinkedList;
import java.util.List;

import acceptance.story.Story;
import reversej.MakerAndSaver;
import reversej.diagram.DiagramEngine;
import reversej.diagram.DiagramStrategy;
import reversej.diagram.InformationFactory;
import reversej.diagram.informationmodel.InformationFactoryImpl;
import reversej.diagram.strategies.ClassDiagram;
import reversej.repository.RepositoryInMemory;
import reversej.tracer.Tracer;

public class StoryClassDiagram {
	public static void main(String[] args) {
		RepositoryInMemory repository = new RepositoryInMemory();
		InformationFactory factory = new InformationFactoryImpl();
		List<DiagramStrategy> strategies = new LinkedList<DiagramStrategy>();
		strategies.add(new ClassDiagram());
		DiagramEngine engine = new MakerAndSaver(repository, factory, strategies);
		String fileName = Thread.currentThread().getStackTrace()[1].getFileName();
		((MakerAndSaver)engine).setFileName(fileName);
		Tracer.start(repository);
		
		Story story = new Story();
		story.tellStory();
		
		Tracer.stop();
		engine.make();
	}
}
