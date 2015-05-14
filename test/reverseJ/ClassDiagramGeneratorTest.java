package reverseJ;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ClassDiagramGeneratorTest {
	public static class ContextTest{
		ClassDiagramGenerator generator;
		@Before
		public void setUp() throws Exception {
			generator = new ClassDiagramGenerator();
		}
		@Test
		public void setGetContext() {
			assertNull(generator.getContext());
			Context expected = createStubContext();
			generator.setContext(expected);
			Context actual = generator.getContext();
			assertEquals(expected, actual);
		}
		private static Context createStubContext() {
			Context context = new Context(null,null,null);
			return context;
		}
	}
}
