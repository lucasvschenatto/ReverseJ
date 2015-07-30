package reverseJ;

import static org.junit.Assert.*;

import org.junit.*;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;

public class AdapterClassToUML2Test{
	protected AdapterClassToUml2 adapter;
	@Before
	public void setup(){
		Context context = Context.getInstance();
		adapter = AdapterClassToUml2.make(context);
	}
	public static class General extends AdapterClassToUML2Test{
		@Test
		public void testMake() {
			adapter = AdapterClassToUml2.make(null);
			assertNotNull(adapter);
		}
		@Test
		public void getPackage(){
			Package p = adapter.getPackage();
			assertNotNull(p);
		}
	}
	public static class CreateClass extends AdapterClassToUML2Test{
		@Test
		public void CreateConcrete_ReturnsClass() {
			String name = "Employee";
			adapter.createConcreteClass(name);
			NamedElement actual = adapter.getPackage().getOwnedMember(name); 
			assertNotNull(actual);
			assertTrue(actual instanceof org.eclipse.uml2.uml.Class);			
		}
		@Test
		public void CreateConcreteClass_CreatesInPackage() {
			String name = "Employee";
			NamedElement received = adapter.createConcreteClass(name);
			NamedElement actual = adapter.getPackage().getOwnedMember(name);
			assertEquals(received, actual);
		}
		@Test
		public void CreateConcreteClass_ReturnsAConcreteClass() {
			String name = "Employee";
			adapter.createConcreteClass(name);
			NamedElement actual = adapter.getPackage().getOwnedMember(name);
			assertFalse(((org.eclipse.uml2.uml.Class)actual).isAbstract());		
		}
	}
	public static class CreateType extends AdapterClassToUML2Test{	
		@Test
		public void createType_ReturnsType() {
			String name = "boolean";
			PrimitiveType received = adapter.createType(name);
			assertNotNull(received);
		}
		@Test
		public void createTypeInPackage_InsertReturnedTypeInPackage() {
			String name = "boolean";
			PrimitiveType p = adapter.createType(name);
			assertEquals(p, adapter.getPackage().getOwnedType(name));
		}
	}
	public static class CreateInterface extends AdapterClassToUML2Test{
		@Test
		public void createInterface_ReturnsInterface() {
			String name = "Person";
			Interface received = adapter.createInterface(name);
			assertNotNull(received);
		}
		@Test
		public void createInterface_InsertReturnedInterfaceInPackage() {
			String name = "Person";
			Interface received = adapter.createInterface(name);
			assertEquals(received, adapter.getPackage().getOwnedMember(name));
		}
	}
	public static class CreateMethod extends AdapterClassToUML2Test{
		
	}
	public static class CreateImplementation extends AdapterClassToUML2Test{
		@Test
		public void createImplementation_ReturnsImplementation() {
			String interfaceName = "Person";
			adapter.createInterface(interfaceName);
			String implementerName = "Employee";
			adapter.createConcreteClass(implementerName);
			InterfaceRealization received = adapter.createImplementation(interfaceName, implementerName);
			assertNotNull(received);
		}
		@Test
		public void createImplementation_BoundToImplClass() {
			String interfaceName = "Person";
			adapter.createInterface(interfaceName);
			String implementerName = "Employee";
			adapter.createConcreteClass(implementerName);
			InterfaceRealization received = adapter.createImplementation(interfaceName, implementerName);
			assertEquals(implementerName,received.getImplementingClassifier().getName());
		}
		@Test
		public void createImplementation_BoundToInterface() {
			String interfaceName = "Person";
			adapter.createInterface(interfaceName);
			String implementerName = "Employee";
			adapter.createConcreteClass(implementerName);
			InterfaceRealization received = adapter.createImplementation(interfaceName, implementerName);
			assertEquals(interfaceName,received.getContract().getName());
		}
	}
	public static class CreateUnidirectionalAssociation extends AdapterClassToUML2Test{
		@Test
		public void createUnidirectional_ReturnsNotNull() {
			String callerName = "Person";
			adapter.createConcreteClass(callerName);
			String targetName = "Employee";
			adapter.createConcreteClass(targetName);
			Association received = adapter.createUnidirectionalAssociation(callerName, targetName);
			assertNotNull(received);
		}
	}
	public static class CreateBidirectionalAssociation extends AdapterClassToUML2Test{
		@Test
		public void createBidirectional_ReturnsNotNull() {
			String class1 = "Male";
			adapter.createConcreteClass(class1);
			String class2 = "Female";
			adapter.createConcreteClass(class2);
			Association received = adapter.createBidirectionalAssociation(class1, class2);
			assertNotNull(received);
		}
	}
	public static class CreateDependency extends AdapterClassToUML2Test{
		@Test
		public void createDependency_ReturnsNotNull() {
			String depender = "Driver";
			adapter.createConcreteClass(depender);
			String dependedUpon = "Vehicle";
			adapter.createConcreteClass(dependedUpon);
			Dependency received = adapter.createDependency(depender, dependedUpon);
			assertNotNull(received);
		}
	}
	public static class ToDo extends AdapterClassToUML2Test{
		@Test@Ignore
		public void CreateMethod_ReturnsMethod() {
			String className = "Visiter";
			String signature = "String name, int age";
			String methodName = "action";
			Operation received = adapter.createMethod(className, methodName, signature);
			assertNotNull(received);
		}
		@Test@Ignore
		public void testCreateMethodWithReturn() {
			fail("Not yet implemented");
		}
	}
}
