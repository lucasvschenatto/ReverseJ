package reverseJ;
import static org.junit.Assert.*;

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
		public void constructorSetsUtilities() {
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
		public void ifHasNoClass_DoNotCreateLifeline() {
			List<Information> informations = new LinkedList<Information>();

			strategy.generate(informations);

			assertNumberOfCreatedLifelines(0);
		}
		
		@Test
		public void CreateLifeline() {
			String className = "myTestClass";
			Information info = InformationFactory.createClass(className);
			List<Information> informations = new LinkedList<Information>();
			informations.add(info);

			strategy.generate(informations);

			assertLifelineCreated(className);
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
