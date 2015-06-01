package reverseJ;

import static org.junit.Assert.*;

import org.junit.*;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;

public class ClassDiagramFrameworkAdapterTest{
	protected ClassDiagramFrameworkAdapter adapter;
	@Before
	public void setup(){
		Context context = Context.getInstance();
		adapter = ClassDiagramFrameworkAdapter.make(context);
	}
	public static class General extends ClassDiagramFrameworkAdapterTest{
		@Test
		public void testMake() {
			adapter = ClassDiagramFrameworkAdapter.make(null);
			assertNotNull(adapter);
		}
		@Test
		public void getPackage(){
			Package p = adapter.getPackage();
			assertNotNull(p);
		}
	}
	public static class CreateClass extends ClassDiagramFrameworkAdapterTest{
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
	public static class CreateType extends ClassDiagramFrameworkAdapterTest{	
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
	public static class CreateInterface extends ClassDiagramFrameworkAdapterTest{
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
	public static class CreateMethod extends ClassDiagramFrameworkAdapterTest{
		
	}
	public static class CreateImplementation extends ClassDiagramFrameworkAdapterTest{
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
	public static class CreateUnidirectionalAssociation extends ClassDiagramFrameworkAdapterTest{
		@Test
		public void createUnidirectional_ReturnsNotNull() {
			String callerName = "Person";
			adapter.createConcreteClass(callerName);
			String targetName = "Employee";
			adapter.createConcreteClass(targetName);
			Association received = adapter.createUnidirectionalAssociation(callerName, targetName);
			assertNotNull(received);
		}
		@Test
		public void createUnidirectional_BoundToCaller() {
			String callerName = "Person";
			adapter.createConcreteClass(callerName);
			String targetName = "Employee";
			adapter.createConcreteClass(targetName);
			Association received = adapter.createUnidirectionalAssociation(callerName, targetName);
			fail("Not finished yet");
//			received.getNavigableOwnedEnd(callerName, );
		}
	}
	public static class ToDo extends ClassDiagramFrameworkAdapterTest{
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

		@Test@Ignore
		public void testCreateBiDirectionalAssociation() {
			fail("Not yet implemented");
		}

		@Test@Ignore
		public void testCreateDependency() {
			fail("Not yet implemented");
		}
	}
}
