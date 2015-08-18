package acceptance;

import java.util.List;

import acceptance.story.Story;
import reverseJ.InformationStorageProvider;
import reverseJ.RecorderStorage;
import reverseJ.Tracer;

public class StoryTracerTest {

	public static void main(String[] args) {
		RecorderStorage r = new InformationStorageProvider();
		Tracer.start(r);
		
		Story s = new Story();		
		s.tellStory();
		
		Tracer.stop();
		
		List<String> informations = r.describeAll();
		for (String information : informations)
			System.out.println(information);
	}
}
