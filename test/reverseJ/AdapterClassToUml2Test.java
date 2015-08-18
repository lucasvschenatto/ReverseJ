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
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;

public class AdapterClassToUml2Test{
	protected AdapterClassToUml2 adapter;
	@Before
	public void setup(){		
		adapter = AdapterClassToUml2.make();
	}
	public static class General extends AdapterClassToUml2Test{
		@Test
		public void testMake() {
			adapter = AdapterClassToUml2.make();
			assertNotNull(adapter);
		}
		@Test
		public void getPackage(){
			Package p = adapter.getPackage();
			assertNotNull(p);
		}
		@Test
		public void packageNameIsClassDiagram(){
			String actual = adapter.getPackage().getName();
			String expected = AdapterClassToUml2.PACKAGE_NAME;
			assertEquals(expected, actual);
		}
	}
	public static class CreateClass extends AdapterClassToUml2Test{
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
	public static class CreateType extends AdapterClassToUml2Test{	
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
		@Test
		public void createType_setsTypeName() {
			String name = "boolean";
			PrimitiveType p = adapter.createType(name);
			assertEquals(name, p.getName());
		}
	}
	public static class CreateInterface extends AdapterClassToUml2Test{
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
	public static class CreateMethod extends AdapterClassToUml2Test{
		@Test
		public void CreateMethod_ReturnsOperation() {
			Operation received = adapter.createMethod(null, null, null);
			assertNotNull(received);
		}
		@Test
		public void CreateMethod_boundToClass() {
			String className = "Visiter";
			adapter.createConcreteClass(className);
			Operation received = adapter.createMethod(className, null, null);
			assertEquals(className, received.getClass_().getName());
		}
		@Test
		public void CreateMethod_setsMethodName() {
			String methodName = "action";
			Operation received = adapter.createMethod(null, methodName, null);
			assertEquals(methodName, received.getName());
		}
		@Test
		public void CreateMethod_setsOneParameters() {
			String signature = "String name";
			PrimitiveType s = adapter.createType("String");
			
			Operation received = adapter.createMethod(null, null, signature);
			Parameter p = received.getOwnedParameter("name", s);
			assertNotNull(p);
			assertEquals(ParameterDirectionKind.IN_LITERAL, p.getDirection());
		}
		@Test
		public void CreateMethod_setsFourParameters() {
			String signature = "String name, int age, double money, boolean isBrazilian";
			PrimitiveType s = adapter.createType("String");
			PrimitiveType i = adapter.createType("int");
			PrimitiveType d = adapter.createType("double");
			PrimitiveType b = adapter.createType("boolean");
			
			Operation received = adapter.createMethod(null, null, signature);
			
			Parameter p = received.getOwnedParameter("name", s);
			assertNotNull(p);
			assertEquals(ParameterDirectionKind.IN_LITERAL, p.getDirection());
			p = received.getOwnedParameter("age", i);
			assertNotNull(p);
			assertEquals(ParameterDirectionKind.IN_LITERAL, p.getDirection());			
			p =received.getOwnedParameter("money", d);
			assertNotNull(p);
			assertEquals(ParameterDirectionKind.IN_LITERAL, p.getDirection());			
			p = received.getOwnedParameter("isBrazilian", b);
			assertNotNull(p);
			assertEquals(ParameterDirectionKind.IN_LITERAL, p.getDirection());
		}
	}
	public static class CreateMethodWithReturn extends AdapterClassToUml2Test{
		@Test
		public void CreateMethodWithReturn_ReturnsOperation() {
			Operation received = adapter.createMethodWithReturn(null, null, null, null);
			assertNotNull(received);
		}
		@Test
		public void CreateMethodWithReturn_boundToClass() {
			String className = "Visiter";
			adapter.createConcreteClass(className);
			Operation received = adapter.createMethodWithReturn(className, null, null, null);
			assertEquals(className, received.getClass_().getName());
		}
		@Test
		public void CreateMethodWithReturn_setsMethodName() {
			String methodName = "action";
			Operation received = adapter.createMethodWithReturn(null, methodName, null, null);
			assertEquals(methodName, received.getName());
		}
		@Test
		public void CreateMethodWithReturn_setsOneParameters() {
			String signature = "String name";
			PrimitiveType s = adapter.createType("String");
			
			Operation received = adapter.createMethodWithReturn(null, null, signature, null);
			
			assertNotNull(received.getOwnedParameter("name", s));
		}
		@Test
		public void CreateMethodWithReturn_setsFourParameters() {
			String signature = "String name, int age, double money, boolean isBrazilian";
			PrimitiveType s = adapter.createType("String");
			PrimitiveType i = adapter.createType("int");
			PrimitiveType d = adapter.createType("double");
			PrimitiveType b = adapter.createType("boolean");
			
			Operation received = adapter.createMethodWithReturn(null, null, signature, null);
			
			assertNotNull(received.getOwnedParameter("name", s));
			assertNotNull(received.getOwnedParameter("age", i));
			assertNotNull(received.getOwnedParameter("money", d));
			assertNotNull(received.getOwnedParameter("isBrazilian", b));
		}
		@Test
		public void CreateMethodWithReturn_setsReturn() {
			String returnType = "int";
			
			Operation received = adapter.createMethodWithReturn(null, null, null, returnType);
			Parameter p = received.getReturnResult();
			assertNotNull(p);
			assertEquals(ParameterDirectionKind.RETURN_LITERAL, p.getDirection());
		}
		@Test
		public void returnTypeIsSet() {
			String returnType = "int";
			PrimitiveType t = adapter.createType(returnType);
			
			Operation received = adapter.createMethodWithReturn(null, null, null, returnType);
			Parameter p = received.getReturnResult();
			assertEquals(t,p.getType());
		}
	}
	public static class CreateImplementation extends AdapterClassToUml2Test{
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
	public static class CreateUnidirectionalAssociation extends AdapterClassToUml2Test{
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
	public static class CreateBidirectionalAssociation extends AdapterClassToUml2Test{
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
	public static class CreateDependency extends AdapterClassToUml2Test{
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
}
