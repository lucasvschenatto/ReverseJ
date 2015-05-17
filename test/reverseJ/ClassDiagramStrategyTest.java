package reverseJ;

import static org.junit.Assert.*;

import java.util.List;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

public class ClassDiagramStrategyTest extends ClassDiagramUtilities{
	DiagramStrategy strategy;
	@Before
	public void setup(){
		strategy = new ClassDiagram(this);
	}
	@Test
	public void constructorSetsUtilities(){
		ClassDiagramUtilities expected = ClassDiagramUtilities.make();
		strategy = new ClassDiagram(expected);
		ClassDiagramUtilities actual = strategy.getUtil();
		
		assertNotNull(new ClassDiagram().getUtil());
		assertEquals(expected, actual);
	}
	@Test@Ignore
	public void whenGenerate_IfHasCallerInformation_CreateConcreteClass() {
		String expected = "Test";
		Information info = InformationFactory.createCaller(expected);
		List<Information> informations = new LinkedList<Information>();
		informations.add(info);
		
		Diagram d = strategy.generate(informations);
		
		fail("to do method");
	}
	@Test@Ignore
	public void whenGenerate_IfHasTargetInformation_CreateConcreteClass() {
		String expected = "Test";
		Information info = InformationFactory.createTarget(expected);
		List<Information> informations = new LinkedList<Information>();
		informations.add(info);
		
		Diagram d = strategy.generate(informations);
		
		fail("to do method");
	}
	@Test@Ignore
	public void whenGenerate_IfHasHandlerInformation_CreateConcreteClass() {
		String expected = "Test";
		Information info = InformationFactory.createHandler(expected);
		List<Information> informations = new LinkedList<Information>();
		informations.add(info);
		
		Diagram d = strategy.generate(informations);
		
		fail("to do method");
	}
	@Test@Ignore
	public void whenGenerate_DoesntCreateConcreteClass_ForOtherInformations() {
		String name = "Test";
		Information info;
		List<Information> informations = new LinkedList<Information>();
		
		info = InformationFactory.createDummy(name);
		informations.add(info);
		info = InformationFactory.createInterface(name);
		informations.add(info);
		info = InformationFactory.createModifiers(name);
		informations.add(info);
		info = InformationFactory.createMethod(name);
		informations.add(info);
		info = InformationFactory.createSignature(name);
		informations.add(info);
		info = InformationFactory.createReturn(name);
		informations.add(info);
		info = InformationFactory.createThrow(name);
		informations.add(info);
		
		Diagram d = strategy.generate(informations);
		
		fail("to do method");
	}

}
