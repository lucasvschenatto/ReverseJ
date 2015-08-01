package reverseJ;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Package;
import org.junit.*;

public class AdapterSequenceToUml2Test {
	protected AdapterSequenceToUml2 adapter;
	@Before
	public void setup(){
		Context context = Context.getInstance();
		adapter = AdapterSequenceToUml2.make(context);
	}
	public static class General extends AdapterSequenceToUml2Test{
		@Test
		public void testMake() {
			adapter = AdapterSequenceToUml2.make(null);
			assertNotNull(adapter);
		}
		@Test
		public void getPackage(){
			Package p = adapter.getPackage();
			assertNotNull(p);
		}
	}
	public static class CreateLifeline{
//		@Before
//		public void setUp() throws Exception {
//			adapter = new AdapterSequenceToUML2();
//			adapter.setContext(ContextTest.createStubContext());
//		}
//		@Test
//		public void CreateLifelineReturnsFalse_If_ContextNotSet(){
//			AdapterSequenceToUML2 g = new AdapterSequenceToUML2();
//			assertFalse(g.createLifeline(null));
//		}
//		@Test
//		public void CreateLifelineDoesNotCreate_If_ContextNotSet(){
//			AdapterSequenceToUML2 g = new AdapterSequenceToUML2();
//			g.createLifeline("test");
//			assertNull(g.getLifeline("test"));	
//		}
//		@Test
//		public void CreateLifelineReturnsTrue_If_ContextSet(){
//			AdapterSequenceToUML2 g = new AdapterSequenceToUML2();
//			g.setContext(ContextTest.createStubContext());
//			assertTrue(g.createLifeline(null));
//		}
//		@Test
//		public void CreateLifelineDoesCreate_If_ContextSet(){
//			AdapterSequenceToUML2 g = new AdapterSequenceToUML2();
//			g.setContext(ContextTest.createStubContext());
//			g.createLifeline("test");
//			assertNotNull(g.getLifeline("test"));	
//		}
//		@Test
//		public void createLifeline(){
//			String name = "lifelineTest";
//			assertNull(adapter.getLifeline(name));		
//			
//			adapter.createLifeline(name);
//			
//			assertNotNull(adapter.getLifeline(name));	
//		}
//		@Test
//		public void createThreeLifelines(){
//			String one = "lifelineOne";
//			String two = "lifelineTwo";
//			String three = "lifelineThree";		
//			
//			adapter.createLifeline(one);
//			adapter.createLifeline(two);
//			adapter.createLifeline(three);
//			
//			assertEquals(one, adapter.getLifeline(one).getName());
//			assertEquals(two, adapter.getLifeline(two).getName());
//			assertEquals(three, adapter.getLifeline(three).getName());
//		}
//		@Test
//		public void getLifelines(){
//			String one = "lifelineOne";
//			String two = "lifelineTwo";
//			String three = "lifelineThree";
//			adapter.createLifeline(one);
//			adapter.createLifeline(two);
//			adapter.createLifeline(three);
//			List<Lifeline>expected = new LinkedList<Lifeline>();
//			expected.add(adapter.getLifeline(one));
//			expected.add(adapter.getLifeline(two));
//			expected.add(adapter.getLifeline(three));
//			
//			List<Lifeline> actual = adapter.getLifelines();
//			
//			assertEquals(expected, actual);
//		}
//		@Test
//		public void lifeLineNameIsSetInConstructor(){
//			String expected = "lifelineTest";
//			
//			adapter.createLifeline(expected);
//			
//			String actual = adapter.getLifeline(expected).getName();
//			assertEquals(expected, actual);
//		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		@Test
//		public void lifelineIsCoverdByContextInteraction(){
//			String name = "lifelineTest";
//			Interaction expected = generator.getContext().getInteraction();
//			
//			generator.createLifeline(name);
//			Interaction actual = generator.getLifeline(name).getInteraction();
//			
//			assertEquals(expected,actual);
//		}
	}
}
