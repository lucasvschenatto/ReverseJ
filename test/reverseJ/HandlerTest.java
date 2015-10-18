package reverseJ;

import acceptance.story.Story;


public class HandlerTest {
	public static void main(String[] args) {
		Boolean b = Handler.start(Thread.currentThread().getStackTrace()[1].getFileName());
		int i = b? 1:2;
		Story s = new Story();
		s.tellStory();
		
		Handler.stop();
	}
}
