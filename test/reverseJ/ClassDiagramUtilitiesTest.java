package reverseJ;

import static org.junit.Assert.*;

import org.junit.*;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;

public class ClassDiagramUtilitiesTest{
	protected ClassDiagramUtilities c;
	@Before
	public void setup(){
		Context context = Context.getInstance();
		c = ClassDiagramUtilities.make(context);
	}
	public static class General extends ClassDiagramUtilitiesTest{
		@Test
		public void testMake() {
			c = ClassDiagramUtilities.make(null);
			assertNotNull(c);
		}
		@Test
		public void getPackage(){
			Package p = c.getPackage();
			assertNotNull(p);
		}
	}
	public static class CreateClass extends ClassDiagramUtilitiesTest{
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
	public static class CreateType extends ClassDiagramUtilitiesTest{	
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
	public static class CreateInterface extends ClassDiagramUtilitiesTest{
		@Test
		public void CreateInterface_ReturnsInterface() {
			String name = "Person";
			Interface received = c.createInterface(name);
			assertNotNull(received);
		}
		@Test
		public void CreateInterface_InsertReturnedInterfaceInPackage() {
			String name = "Person";
			Interface received = c.createInterface(name);
			assertEquals(received, c.getPackage().getOwnedMember(name));
		}
	}
	public static class CreateMethod extends ClassDiagramUtilitiesTest{
		@Test
		public void CreateMethod_ReturnsMethod() {
			String className = "Visiter";
			String signature = "String name, int age";
			String methodName = "action";
			Operation received = c.createMethod(className, methodName, signature);
			assertNotNull(received);
		}
	}
	public static class toDo{
		@Test@Ignore
		public void testCreateMethodWithReturn() {
			fail("Not yet implemented");
		}
		@Test@Ignore
		public void testCreateImplementation() {
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
