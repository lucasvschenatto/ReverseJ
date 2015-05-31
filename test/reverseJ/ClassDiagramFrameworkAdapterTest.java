package reverseJ;

import static org.junit.Assert.*;

import org.junit.*;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;

public class ClassDiagramFrameworkAdapterTest{
	protected ClassDiagramFrameworkAdapter c;
	@Before
	public void setup(){
		Context context = Context.getInstance();
		c = ClassDiagramFrameworkAdapter.make(context);
	}
	public static class General extends ClassDiagramFrameworkAdapterTest{
		@Test
		public void testMake() {
			c = ClassDiagramFrameworkAdapter.make(null);
			assertNotNull(c);
		}
		@Test
		public void getPackage(){
			Package p = c.getPackage();
			assertNotNull(p);
		}
	}
	public static class CreateClass extends ClassDiagramFrameworkAdapterTest{
		@Test
		public void CreateConcrete_ReturnsClass() {
			String name = "Employee";
			c.createConcreteClass(name);
			NamedElement actual = c.getPackage().getOwnedMember(name); 
			assertNotNull(actual);
			assertTrue(actual instanceof org.eclipse.uml2.uml.Class);			
		}
		@Test
		public void CreateConcreteClass_CreatesInPackage() {
			String name = "Employee";
			NamedElement received = c.createConcreteClass(name);
			NamedElement actual = c.getPackage().getOwnedMember(name);
			assertEquals(received, actual);
		}
		@Test
		public void CreateConcreteClass_ReturnsAConcreteClass() {
			String name = "Employee";
			c.createConcreteClass(name);
			NamedElement actual = c.getPackage().getOwnedMember(name);
			assertFalse(((org.eclipse.uml2.uml.Class)actual).isAbstract());		
		}
	}
	public static class CreateType extends ClassDiagramFrameworkAdapterTest{	
		@Test
		public void createType_ReturnsType() {
			String name = "boolean";
			PrimitiveType received = c.createType(name);
			assertNotNull(received);
		}
		@Test
		public void createTypeInPackage_InsertReturnedTypeInPackage() {
			String name = "boolean";
			PrimitiveType p = c.createType(name);
			assertEquals(p, c.getPackage().getOwnedType(name));
		}
	}
	public static class CreateInterface extends ClassDiagramFrameworkAdapterTest{
		@Test
		public void createInterface_ReturnsInterface() {
			String name = "Person";
			Interface received = c.createInterface(name);
			assertNotNull(received);
		}
		@Test
		public void createInterface_InsertReturnedInterfaceInPackage() {
			String name = "Person";
			Interface received = c.createInterface(name);
			assertEquals(received, c.getPackage().getOwnedMember(name));
		}
	}
	public static class CreateMethod extends ClassDiagramFrameworkAdapterTest{
		
	}
	public static class CreateImplementation extends ClassDiagramFrameworkAdapterTest{
		@Test
		public void createImplementation_ReturnsImplementation() {
			String interfaceName = "Person";
			c.createInterface(interfaceName);
			String implementerName = "Employee";
			c.createConcreteClass(implementerName);
			InterfaceRealization received = c.createImplementation(interfaceName, implementerName);
			assertNotNull(received);
		}
		@Test
		public void createImplementation_BoundToImplClass() {
			String interfaceName = "Person";
			c.createInterface(interfaceName);
			String implementerName = "Employee";
			c.createConcreteClass(implementerName);
			InterfaceRealization received = c.createImplementation(interfaceName, implementerName);
			assertEquals(implementerName,received.getImplementingClassifier().getName());
		}
		@Test
		public void createImplementation_BoundToInterface() {
			String interfaceName = "Person";
			c.createInterface(interfaceName);
			String implementerName = "Employee";
			c.createConcreteClass(implementerName);
			InterfaceRealization received = c.createImplementation(interfaceName, implementerName);
			assertEquals(interfaceName,received.getContract().getName());
		}
	}
	public static class toDo extends ClassDiagramFrameworkAdapterTest{
		@Test@Ignore
		public void CreateMethod_ReturnsMethod() {
			String className = "Visiter";
			String signature = "String name, int age";
			String methodName = "action";
			Operation received = c.createMethod(className, methodName, signature);
			assertNotNull(received);
		}
		@Test@Ignore
		public void testCreateMethodWithReturn() {
			fail("Not yet implemented");
		}
		

		@Test@Ignore
		public void testCreateUnidirectionalAssociation() {
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
