package reverseJ;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.junit.*;

public class AdapterSequenceToUml2Test {
	protected AdapterSequenceToUml2 adapter;
	@Before
	public void setup(){
		adapter = AdapterSequenceToUml2.make();
	}
	public static class General extends AdapterSequenceToUml2Test{
		@Test
		public void testMake() {
			adapter = AdapterSequenceToUml2.make();
			assertNotNull(adapter);
		}
		@Test
		public void getPackage(){
			Package p = adapter.getPackage();
			assertNotNull(p);
		}
		@Test
		public void getInteraction(){
			NamedElement i = adapter.getInteraction();
			
			assertNotNull(i);
		}
		@Test
		public void interactionNameIsSetToDiagramType(){
			NamedElement actual = adapter.getInteraction();
			assertEquals(AdapterSequenceToUml2.DIAGRAM_TYPE, actual.getName());
		}
		@Test
		public void bindsInteractionToPackage(){			
			NamedElement actual = adapter.getPackage().getOwnedMember(AdapterSequenceToUml2.DIAGRAM_TYPE);
			assertNotNull(actual);
			assertTrue(actual instanceof org.eclipse.uml2.uml.Interaction);
		}
	}
	
	public static class CreateLifeline extends AdapterSequenceToUml2Test{
		@Test
		public void createLifeline_ReturnsLifeline(){
			Lifeline actual = adapter.createLifeline(null);			
			assertNotNull(actual);
		}
		@Test
		public void lifelineNameIsSet(){
			String name = "lifelineTest";
			
			Lifeline actual = adapter.createLifeline(name);
			
			assertEquals(name, actual.getName());
		}
		@Test
		public void bindsLifelineToInteraction(){
			String name = "car";
			
			Lifeline expected = adapter.createLifeline(name);
			
			Lifeline actual = adapter.getInteraction().getLifeline(name);
			assertEquals(expected,actual);
		}
		@Test
		public void createThreeLifelines(){
			String one = "lifelineOne";
			String two = "lifelineTwo";
			String three = "lifelineThree";		
			
			adapter.createLifeline(one);
			adapter.createLifeline(two);
			adapter.createLifeline(three);
			
			assertEquals(one,   adapter.getInteraction().getLifeline(one).getName());
			assertEquals(two,   adapter.getInteraction().getLifeline(two).getName());
			assertEquals(three, adapter.getInteraction().getLifeline(three).getName());
		}
	}
	public static class CreateMessage extends AdapterSequenceToUml2Test{
		@Test
		public void createMessage_ReturnsMessage(){
			Message actual = adapter.createMessage(null, null, null);			
			assertNotNull(actual);
		}
		@Test
		public void messageNameIsSet(){
			String name = "Hello my friend";
			Message actual = adapter.createMessage(null, name, null);
			assertEquals(name,actual.getName());
		}
		@Test
		public void messageHasCaller(){
			Message actual = adapter.createMessage(null, null, null);
			assertNotNull(actual.getSendEvent());
		}
		@Test
		public void sendEventIsCoveredBySender(){
			fail("not yet implemented");
		}
		@Test
		public void messageHasReceiver(){
			Message actual = adapter.createMessage(null, null, null);
			assertNotNull(actual.getReceiveEvent());
		}
//		@Test
//		public void callerIsSet(){
//			String caller = "talker";
//			MessageOccurrenceSpecification actual = adapter.createMessage(caller, null, null);
//			assertNotNull(actual.e)
//		}
	}
}
