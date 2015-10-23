package acceptance;

import java.util.List;

import acceptance.story.Story;
import reversej.repository.RepositoryInformation;
import reversej.tracer.RepositoryRecorder;
import reversej.tracer.Tracer;

public class StoryTracerTest {

	public static void main(String[] args) {
		RepositoryRecorder r = new RepositoryInformation();
		Tracer.start(r);
		
		Story s = new Story();		
		s.tellStory();
		
		Tracer.stop();
		
		List<String> informations = r.describeAll();
		for (String information : informations)
			System.out.println(information);
	}
}
