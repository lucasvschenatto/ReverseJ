package reverseJ;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

public class ClassDiagramStrategyTest extends ClassDiagramUtilities{
	DiagramStrategy strategy;
	List<String> createdClasses;
	List<String> createdInterfaces;
	
	private void assertClassCreated(String className) {
		assertTrue(createdClasses.contains(className));
	}
	private void assertNumberOfCreatedClasses(int number) {
		assertEquals(number, createdClasses.size());	
	}
	
	private void assertInterfaceCreated(String className) {
		assertTrue(createdInterfaces.contains(className));
	}
	private void assertNumberOfCreatedInterfaces(int number) {
		assertEquals(number, createdInterfaces.size());	
	}
	
	@Before
	public void setup(){
		strategy = new ClassDiagram(this);
		createdClasses = new LinkedList<String>();
		createdInterfaces = new LinkedList<String>();
	}
	
	@Test
	public void constructorSetsUtilities(){
		ClassDiagramUtilities expected = ClassDiagramUtilities.make();
		strategy = new ClassDiagram(expected);
		ClassDiagramUtilities actual = strategy.getUtil();
		
		assertNotNull(new ClassDiagram().getUtil());
		assertEquals(expected, actual);
	}
	
	@Test
	public void ifHasCallerInformation_CreateConcreteClass() {
		String className = "myTestClassCaller";
		Information info = InformationFactory.createCaller(className);
		List<Information> informations = new LinkedList<Information>();
		informations.add(info);
		
		strategy.generate(informations);
		
		assertClassCreated(className);
	}
	
	@Test
	public void ifHasTargetInformation_CreateConcreteClass() {
		String className = "myTestClassTarget";
		Information info = InformationFactory.createTarget(className);
		List<Information> informations = new LinkedList<Information>();
		informations.add(info);
		
		strategy.generate(informations);
		
		assertClassCreated(className);
	}
	
	@Test
	public void IfHasHandlerInformation_CreateConcreteClass() {
		String className = "myTestClassTarget";
		Information info = InformationFactory.createHandler(className);
		List<Information> informations = new LinkedList<Information>();
		informations.add(info);
		
		strategy.generate(informations);
		
		assertClassCreated(className);
	}
			
	@Test
	public void doesntDuplicateClasses() {
		String className = "myTestClassTarget";
		List<Information> informations = new LinkedList<Information>();
		
		Information info = InformationFactory.createTarget(className);
		informations.add(info);
		info = InformationFactory.createTarget(className);
		informations.add(info);
		
		info = InformationFactory.createCaller(className);
		informations.add(info);
		info = InformationFactory.createCaller(className);
		informations.add(info);
		
		info = InformationFactory.createHandler(className);
		informations.add(info);
		info = InformationFactory.createHandler(className);
		informations.add(info);		
		
		strategy.generate(informations);
		
		assertClassCreated(className);
		assertNumberOfCreatedClasses(1);
	}
	
	@Test
	public void doesntDelete_not_duplicated_Classes() {
		List<Information> informations = new LinkedList<Information>();
		
		Information info = InformationFactory.createTarget("myTestClassTarget");
		informations.add(info);
		info = InformationFactory.createTarget("myTestClassTarget2");
		informations.add(info);
		
		info = InformationFactory.createCaller("myTestClassCaller");
		informations.add(info);
		info = InformationFactory.createCaller("myTestClassCaller2");
		informations.add(info);
		
		info = InformationFactory.createHandler("myTestClassHandler");
		informations.add(info);
		info = InformationFactory.createHandler("myTestClassHandler2");
		informations.add(info);
		
		
		strategy.generate(informations);
		
		assertClassCreated("myTestClassTarget" );
		assertClassCreated("myTestClassTarget2");
		assertClassCreated("myTestClassCaller" );
		assertClassCreated("myTestClassCaller2");
		assertClassCreated("myTestClassHandler" );
		assertClassCreated("myTestClassHandler2");
	}
	
	@Test
	public void IfHasInterfaceInformation_CreateInterface() {
		String interfaceName = "myInterface";
		Information info = InformationFactory.createInterface(interfaceName);
		List<Information> informations = new LinkedList<Information>();
		informations.add(info);
		
		strategy.generate(informations);
		
		assertInterfaceCreated(interfaceName);
	}
	@Test
	public void doesntDuplicateInterfaces() {
		String interfaceName = "myTestClassTarget";
		List<Information> informations = new LinkedList<Information>();
		
		Information info = InformationFactory.createInterface(interfaceName);
		informations.add(info);
		info = InformationFactory.createInterface(interfaceName);
		informations.add(info);
		
		strategy.generate(informations);
		
		assertInterfaceCreated(interfaceName);
		assertNumberOfCreatedInterfaces(1);
	}
	
	@Test
	public void doesntDelete_not_duplicated_Interfaces() {
		List<Information> informations = new LinkedList<Information>();
		
		Information info = InformationFactory.createInterface("myTestInterface");
		informations.add(info);
		info = InformationFactory.createInterface("myTestInterface2");
		informations.add(info);
		
		strategy.generate(informations);
		
		assertInterfaceCreated("myTestInterface" );
		assertInterfaceCreated("myTestInterface2");
	}
		
	@Test
	public void doesntCreateConcreteClass_ForOtherInformations() {
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
		
		strategy.generate(informations);
		
		assertNumberOfCreatedClasses(0);
	}
	
	@Override
	public void createConcreteClass(String name){
		createdClasses.add(name);
	}
	@Override
	public void createInterface(String name){
		createdInterfaces.add(name);
	}
}
