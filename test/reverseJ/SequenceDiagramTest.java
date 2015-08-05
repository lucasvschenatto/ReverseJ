package reverseJ;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PrimitiveType;
import org.junit.*;
public class SequenceDiagramTest {
	public static class GeneralTests{
		private DiagramStrategy strategy;

		@Test
		public void constructorSetsAdapter() {
			AdapterSequenceToUml2 expected = AdapterSequenceToUml2.make();
			strategy = new SequenceDiagram(expected);
			AdapterToUml2 actual = strategy.getUtil();

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

	public static class CreateLifeline extends AdapterSequenceToUml2 {
		private final String interaction = "Interaction";
		private final String lifeline = "Lifeline";
		private DiagramStrategy strategy;
		private List<String> createdLifelines;
		private List<String> actionsOrder;
		private boolean interactionCreated;

		private void assertLifelineCreated(String className) {
			assertTrue(createdLifelines.contains(className));
		}
		private void assertNumberOfCreatedLifelines(int number) {
			assertEquals(number, createdLifelines.size());
		}
		private void assertInteractionCreated(){
			assertTrue(interactionCreated);
		}
		private void assertCreationOrder(String ...strings) {
			List<String> expectedOrder = Arrays.asList(strings);
			for(int i = 0; i<expectedOrder.size() & i<actionsOrder.size();i++)
				assertEquals(expectedOrder.get(i), actionsOrder.get(i));			
			assertEquals(expectedOrder.size(),actionsOrder.size());
		}

		@Override
		public org.eclipse.uml2.uml.Lifeline createLifeline(String name) {
			createdLifelines.add(name);
			actionsOrder.add(lifeline);
			return null;
		}
		@Override
		public void createInteraction() {
			interactionCreated = true;
			actionsOrder.add(interaction);
		}
		@Before
		public void setup() {
			strategy = new SequenceDiagram(this);
			createdLifelines = new LinkedList<String>();
			actionsOrder = new LinkedList<String>();
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
		public void createInteractionTest(){
			strategy.generate(null);			
			assertInteractionCreated();
		}
		@Test
		public void creationOrder(){
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createClass("classDummy"));

			strategy.generate(informations);
			
			assertCreationOrder(interaction,lifeline);
		}
		@Test
		public void createMessageSignature(){
			fail("Not yet implemented");
		}
		

//		@Test
//		public void CreateLifeline() {
//			String className = "myTestClass";
//			Information info = InformationFactory.createClass(className);
//			List<Information> informations = new LinkedList<Information>();
//			informations.add(info);
//
//			strategy.generate(informations);
//
//			assertLifelineCreated(className);
//		}
//
//		@Test
//		public void CreateLifelineForHandler() {
//			String className = "myTestClassTarget";
//			Information info = InformationFactory.createHandler(className);
//			List<Information> informations = new LinkedList<Information>();
//			informations.add(info);
//
//			strategy.generate(informations);
//
//			assertLifelineCreated(className);
//		}
//
//		@Test
//		public void doesntDuplicateClasses() {
//			List<Information> informations = completeMethodTrace("001");
//			informations.addAll(completeMethodTrace("001"));
//			informations.addAll(completeMethodTrace("001"));
//			informations.addAll(completeMethodTrace("001"));
//			informations.addAll(completeMethodTrace("001"));
//
//			strategy.generate(informations);
//
//			assertNumberOfCreatedClasses(1);
//		}
//
//		@Test
//		public void doesntDeleteNotDuplicatedClasses() {
//			List<Information> informations = completeMethodTrace("001");
//			informations.addAll(completeMethodTrace("002"));
//
//			strategy.generate(informations);
//
//			assertClassCreated("Class001");
//			assertClassCreated("Class002");
//		}
	}
}
