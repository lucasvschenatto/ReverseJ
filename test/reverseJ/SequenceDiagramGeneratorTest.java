package reverseJ;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.junit.*;

public class SequenceDiagramGeneratorTest {
	public static class ContextTest{
		SequenceDiagramGenerator generator;
		@Before
		public void setUp() throws Exception {
			generator = new SequenceDiagramGenerator();
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
	public static class LifelineTest{
		SequenceDiagramGenerator generator;
		@Before
		public void setUp() throws Exception {
			generator = new SequenceDiagramGenerator();
			generator.setContext(ContextTest.createStubContext());
		}
		@Test
		public void CreateLifelineReturnsFalse_If_ContextNotSet(){
			SequenceDiagramGenerator g = new SequenceDiagramGenerator();
			assertFalse(g.createLifeline(null));
		}
		@Test
		public void CreateLifelineDoesNotCreate_If_ContextNotSet(){
			SequenceDiagramGenerator g = new SequenceDiagramGenerator();
			g.createLifeline("test");
			assertNull(g.getLifeline("test"));	
		}
		@Test
		public void CreateLifelineReturnsTrue_If_ContextSet(){
			SequenceDiagramGenerator g = new SequenceDiagramGenerator();
			g.setContext(ContextTest.createStubContext());
			assertTrue(g.createLifeline(null));
		}
		@Test
		public void CreateLifelineDoesCreate_If_ContextSet(){
			SequenceDiagramGenerator g = new SequenceDiagramGenerator();
			g.setContext(ContextTest.createStubContext());
			g.createLifeline("test");
			assertNotNull(g.getLifeline("test"));	
		}
		@Test
		public void createLifeline(){
			String name = "lifelineTest";
			assertNull(generator.getLifeline(name));		
			
			generator.createLifeline(name);
			
			assertNotNull(generator.getLifeline(name));	
		}
		@Test
		public void createThreeLifelines(){
			String one = "lifelineOne";
			String two = "lifelineTwo";
			String three = "lifelineThree";		
			
			generator.createLifeline(one);
			generator.createLifeline(two);
			generator.createLifeline(three);
			
			assertEquals(one, generator.getLifeline(one).getName());
			assertEquals(two, generator.getLifeline(two).getName());
			assertEquals(three, generator.getLifeline(three).getName());
		}
		@Test
		public void getLifelines(){
			String one = "lifelineOne";
			String two = "lifelineTwo";
			String three = "lifelineThree";
			generator.createLifeline(one);
			generator.createLifeline(two);
			generator.createLifeline(three);
			List<Lifeline>expected = new LinkedList<Lifeline>();
			expected.add(generator.getLifeline(one));
			expected.add(generator.getLifeline(two));
			expected.add(generator.getLifeline(three));
			
			List<Lifeline> actual = generator.getLifelines();
			
			assertEquals(expected, actual);
		}
		@Test
		public void lifeLineNameIsSetInConstructor(){
			String expected = "lifelineTest";
			
			generator.createLifeline(expected);
			
			String actual = generator.getLifeline(expected).getName();
			assertEquals(expected, actual);
		}
		@Test
		public void lifelineIsCoverdByContextInteraction(){
			String name = "lifelineTest";
			Interaction expected = generator.getContext().getInteraction();
			
			generator.createLifeline(name);
			Interaction actual = generator.getLifeline(name).getInteraction();
			
			assertEquals(expected,actual);
		}
	}
}
