package acceptance;

import java.util.List;

import acceptance.story.Story;
import reversej.repository.RepositoryInMemory;
import reversej.tracer.RepositoryRecorder;
import reversej.tracer.TracerController;

public class PrintStory {

	public static void main(String[] args) {
		RepositoryRecorder r = new RepositoryInMemory();
		TracerController.start(r);
		
		Story s = new Story();		
		s.tellStory();
		
		TracerController.stop();
		
		List<String> informations = r.describeAll();
		for (String information : informations)
			System.out.println(information);
	}
}
