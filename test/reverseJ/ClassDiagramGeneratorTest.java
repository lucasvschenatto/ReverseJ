package reverseJ;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ClassDiagramGeneratorTest {
public static class makeTests{
		
		@Before
		public void setUp() throws Exception {
		}
		@Test
		public void setGetContext() {			
			Context expected = createStubContext();
			DiagramGenerator generator;
			
			generator = ClassDiagramGenerator.make(expected);
			Context actual = generator.getContext();
			
			assertEquals(expected, actual);
		}
		private static Context createStubContext() {
			Context context = new Context(null,null,null);
			return context;
		}
	}
}
