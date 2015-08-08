package reverseJ;
import static org.junit.Assert.*;
import static reverseJ.TestUtilities.*;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Message;
import org.junit.*;
public class SequenceDiagramTest {
	public static class GeneralTests{
		private DiagramStrategy strategy;

		@Test
		public void constructorSetsAdapter() {
			AdapterSequenceToUml2 expected = AdapterSequenceToUml2.make();
			strategy = new SequenceDiagram(expected);
			AdapterToUml2 actual = strategy.getAdapter();

			assertEquals(expected, actual);
		}
		@Test
		public void returnsPackageFromAdapter(){
			AdapterToUml2 adapter = AdapterSequenceToUml2.make();
			strategy = new SequenceDiagram((AdapterSequenceToUml2)adapter);
			org.eclipse.uml2.uml.Package p = strategy.generate(null);
			assertNotNull(p);
			assertEquals(adapter.getPackage(), p);
		}
	}

	public static class CreateLifelineTests extends AdapterSequenceToUml2 {
		private DiagramStrategy strategy;
		private List<String> createdLifelines;
		
		private void assertLifelineCreated(String className) {
			assertTrue(createdLifelines.contains(className));
		}
		private void assertNumberOfCreatedLifelines(int number) {
			assertEquals(number, createdLifelines.size());
		}
		@Override
		public org.eclipse.uml2.uml.Lifeline createLifeline(String name) {
			createdLifelines.add(name);
			return null;
		}
		@Before
		public void setup() {
			strategy = new SequenceDiagram(this);
			createdLifelines = new LinkedList<String>();
		}
		@Test
		public void CreateLifelineTest() {
			String className = "myTestClass";
			Information info = InformationFactory.createClass(className);
			List<Information> informations = new LinkedList<Information>();
			informations.add(info);

			strategy.generate(informations);

			assertLifelineCreated(className);
		}
		@Test
		public void ifHasNoClass_DoNotCreateLifeline() {
			List<Information> informations = new LinkedList<Information>();

			strategy.generate(informations);

			assertNumberOfCreatedLifelines(0);
		}
		@Test
		public void CreateTwoLifelines() {
			String className1 = "myTestClass1";
			String className2 = "myTestClass2";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createClass(className1));
			informations.add(InformationFactory.createClass(className2));

			strategy.generate(informations);

			assertLifelineCreated(className1);
			assertLifelineCreated(className2);
		}
		
		@Test
		public void doesntDuplicateLifelines() {
			List<Information> informations = completeMethodTrace("001");
			informations.addAll(completeMethodTrace("001"));
			informations.addAll(completeMethodTrace("001"));
			informations.addAll(completeMethodTrace("001"));
			informations.addAll(completeMethodTrace("001"));

			strategy.generate(informations);

			assertNumberOfCreatedLifelines(1);
		}
	}
	public static class CreateMessageTests extends AdapterSequenceToUml2 {
		private DiagramStrategy strategy;
		private List<String> createdMessages;

		private void assertMessageCreated(String message) {
			assertTrue(createdMessages.contains(message));
		}
		private void assertNumberOfCreatedMessageCalls(int number) {
			int actual = 0;
			for (String message : createdMessages)
				if(message.contains("("))
					actual++;
			assertEquals(number, actual);
		}
		private void assertNumberOfCreatedMessageReturns(int number) {
			int actual = 0;
			for (String message : createdMessages) {
				if(!message.contains("("))
					actual++;
			}
			assertEquals(number, actual);
		}
		@Override
		public Message createMessage(
				String caller, String message, String target) {
			createdMessages.add(message);
			return null;
		}
		@Before
		public void setup() {
			strategy = new SequenceDiagram(this);
			createdMessages = new LinkedList<String>();
		}
		@Test
		public void createMessageCall(){
			String id = "00001";
			List<Information> informations = completeMethodTrace(id);

			strategy.generate(informations);
			
			assertMessageCreated(METHOD+id + "(" + PARAMETERS + id + ")");
		}
		
		@Test
		public void createTwoMessageCalls(){
			String id1 = "1";
			String id2 = "2";
			List<Information> informations = completeMethodTrace(id1);
			informations.addAll(completeMethodTrace(id2));
			
			strategy.generate(informations);
			
			assertMessageCreated(METHOD+id1 + "(" + PARAMETERS + id1 + ")");
			assertMessageCreated(METHOD+id2 + "(" + PARAMETERS + id2 + ")");
		}
		
		@Test
		public void createTwoMessagesCallsForNestedTrace(){
			String id1 = "1";
			List<Information> informations = completeNestedMethodTrace(id1);
			
			strategy.generate(informations);
			
			assertMessageCreated(METHOD_R + "(" + PARAMETERS + id1 + ")");
			assertNumberOfCreatedMessageCalls(2);
		}
		
		@Test
		public void createReturn(){
			String id = "00001";
			List<Information> informations = completeMethodTrace(id);

			strategy.generate(informations);
			
			assertMessageCreated(RETURN_TYPE+id);
		}
		@Test
		public void createTwoIdenticalReturns(){
			String id = "1";
			List<Information> informations = completeMethodTrace(id);
			informations.addAll(completeMethodTrace(id));
			
			strategy.generate(informations);
			
			assertMessageCreated(RETURN_TYPE+id);
			assertNumberOfCreatedMessageReturns(2);
		}
		@Test
		public void createTwoDifferentReturns(){
			String id1 = "1";
			String id2 = "2";
			List<Information> informations = completeMethodTrace(id1);
			informations.addAll(completeMethodTrace(id2));
			
			strategy.generate(informations);
			
			assertMessageCreated(RETURN_TYPE+id1);
			assertMessageCreated(RETURN_TYPE+id2);
		}
		
		@Test
		public void createTwoReturnsForNestedTrace(){
			String id1 = "1";
			List<Information> informations = completeNestedMethodTrace(id1);
			
			strategy.generate(informations);
			
			assertMessageCreated(RETURN_TYPE+id1);
			assertNumberOfCreatedMessageReturns(2);
		}
		
		@Test
		public void whenReturnIsVoidDoNotCreateReturnMessage(){
			String id = "00001";
			List<Information> informations = completeVoidMethodTrace(id);

			strategy.generate(informations);
			
			assertNumberOfCreatedMessageReturns(0);
			assertNumberOfCreatedMessageCalls(1);
		}
	}
}
