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

public class ClassDiagramTest {
	static final String space = " ";
	static final String interface_ = "Interface";
	static final String class_ = "Class";
	static final String class1 = "Caller";
	static final String class2 = "Target";
	static final String modifiers = "modifier";
	static final String method = "method";
	static final String methodR = "run";
	static final String methodC = "<init>";
	static final String parameters = "double d";
	static final String returnType = "boolean";
	static final String returnVoid = "void";
	static final String returnClass1 = class1;
	static final String returnClass2 = class2;

	public static List<Information> completeMethodTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(class_ + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(method + id));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createReturn(returnType + id));

		return informations;
	}
	public static List<Information> completeVoidMethodTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(class_ + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(method + id));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createReturn(returnVoid));

		return informations;
	}

	public static List<Information> completeNestedMethodTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(class1 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodR));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createClass(class2 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodR));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createReturn(returnType + id));
		informations.add(InformationFactory.createReturn(returnType + id));

		return informations;
	}

	public static List<Information> completeInterfaceTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createInterface(interface_ + id));
		informations.add(InformationFactory.createClass(class_ + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(method + id));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createReturn(returnType + id));

		return informations;
	}
	
	public static List<Information> completeNestedInterfaceTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(class1 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodR));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createInterface(interface_ + id));
		informations.add(InformationFactory.createClass(class2 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodR));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createReturn(returnType + id));
		informations.add(InformationFactory.createReturn(returnType + id));

		return informations;
	}

	public static List<Information> completeConstructorTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(class_ + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodC));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createReturn(returnType + id));

		return informations;
	}

	public static List<Information> completeNestedConstructorTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(class1 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodR));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createClass(class2 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodC));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createReturn(returnClass2));
		informations.add(InformationFactory.createReturn(returnClass1));

		return informations;
	}

	public static List<Information> completeBiDirectionalConstructorTrace(
			String id) {
		List<Information> informations = new LinkedList<Information>();

		informations.add(InformationFactory.createClass(class1 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodR));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createClass(class2 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodC));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createReturn(returnClass2));
		informations.add(InformationFactory.createReturn(returnClass1));

		informations.add(InformationFactory.createClass(class2 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodR));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createClass(class1 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodC));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createReturn(returnClass1));
		informations.add(InformationFactory.createReturn(returnClass2));
		return informations;
	}

	public static List<Information> completeBiDirectionalMethodTrace(String id) {
		List<Information> informations = new LinkedList<Information>();

		informations.add(InformationFactory.createClass(class1 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodR));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createClass(class2 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodR));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createReturn(returnClass2));
		informations.add(InformationFactory.createReturn(returnClass1));

		informations.add(InformationFactory.createClass(class2 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodR));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createClass(class1 + id));
		informations.add(InformationFactory.createModifiers(modifiers + id));
		informations.add(InformationFactory.createMethod(methodR));
		informations.add(InformationFactory.createParameters(parameters + id));
		informations.add(InformationFactory.createReturn(returnClass1));
		informations.add(InformationFactory.createReturn(returnClass2));
		return informations;
	}

	public static List<Information> nestInformations(List<Information> nesting,
			List<Information> nested) {
		Information last = nesting.get(nesting.size() - 1);
		nesting.remove(nesting.size() - 1);
		nesting.addAll(nested);
		nesting.add(last);
		return nesting;
	}

	public static class GeneralTests extends AdapterClassToUml2 {
		private DiagramStrategy strategy;

		@Test
		public void constructorSetsUtilities() {
			AdapterClassToUml2 expected = AdapterClassToUml2.make();
			strategy = new ClassDiagram(expected);
			AdapterToUml2 actual = strategy.getUtil();
			assertEquals(expected, actual);
		}
		@Test
		public void returnsPackageFromAdapter(){
			AdapterToUml2 adapter = AdapterClassToUml2.make();
			strategy = new ClassDiagram((AdapterClassToUml2)adapter);
			org.eclipse.uml2.uml.Package p = strategy.generate(null);
			assertNotNull(p);
			assertEquals(adapter.getPackage(), p);
		}
	}

	public static class CreateClass extends AdapterClassToUml2 {
		private DiagramStrategy strategy;
		private List<String> createdClasses;

		private void assertClassCreated(String className) {
			assertTrue(createdClasses.contains(className));
		}

		private void assertNumberOfCreatedClasses(int number) {
			assertEquals(number, createdClasses.size());
		}

		@Override
		public org.eclipse.uml2.uml.Class createConcreteClass(String name) {
			createdClasses.add(name);
			return null;
		}

		@Before
		public void setup() {
			strategy = new ClassDiagram(this);
			createdClasses = new LinkedList<String>();
		}

		@Test
		public void ifHasNoClass_DoNotCreate() {
			List<Information> informations = new LinkedList<Information>();

			strategy.generate(informations);

			assertNumberOfCreatedClasses(0);
		}

		@Test
		public void CreateConcreteClass() {
			String className = "myTestClass";
			Information info = InformationFactory.createClass(className);
			List<Information> informations = new LinkedList<Information>();
			informations.add(info);

			strategy.generate(informations);

			assertClassCreated(className);
		}

		@Test
		public void CreateConcreteClassForHandler() {
			String className = "myTestClassTarget";
			Information info = InformationFactory.createHandler(className);
			List<Information> informations = new LinkedList<Information>();
			informations.add(info);

			strategy.generate(informations);

			assertClassCreated(className);
		}

		@Test
		public void doesntDuplicateClasses() {
			List<Information> informations = completeMethodTrace("001");
			informations.addAll(completeMethodTrace("001"));
			informations.addAll(completeMethodTrace("001"));
			informations.addAll(completeMethodTrace("001"));
			informations.addAll(completeMethodTrace("001"));

			strategy.generate(informations);

			assertNumberOfCreatedClasses(1);
		}

		@Test
		public void doesntDeleteNotDuplicatedClasses() {
			List<Information> informations = completeMethodTrace("001");
			informations.addAll(completeMethodTrace("002"));

			strategy.generate(informations);

			assertClassCreated("Class001");
			assertClassCreated("Class002");
		}
	}

	public static class CreateMethod extends AdapterClassToUml2 {
		private DiagramStrategy strategy;
		private String lastMethod;
		private List<String> createdMethods;

		private void assertMethodContains(String content) {
			assertTrue(lastMethod.contains(content));
		}

		private void assertMethodCreated(String methodName) {
			assertTrue(createdMethods.contains(methodName));
		}

		private void assertNumberOfCreatedMethods(int number) {
			assertEquals(number, createdMethods.size());
		}

		@Override
		public Operation createMethod(String className, String methodName,
				String signature) {
			String createdMethod = (className + " " + methodName + " " + signature)
					.trim();
			createdMethods.add(createdMethod);
			lastMethod = createdMethod;
			return null;
		}

		@Override
		public Operation createMethodWithReturn(String className, String methodName,
				String signature, String returnType) {
			String createdMethod = (className + " " + methodName + " "
					+ signature + " " + returnType).trim();
			createdMethods.add(createdMethod);
			lastMethod = createdMethod;
			return null;
		}

		@Before
		public void setup() {
			strategy = new ClassDiagram(this);
			createdMethods = new LinkedList<String>();
			lastMethod = "";
		}

		@Test
		public void ifHasNoMethods_DoNotCreate() {
			List<Information> informations = new LinkedList<Information>();

			strategy.generate(informations);

			assertNumberOfCreatedMethods(0);
		}

		@Test
		public void CreateMethodForRightClassWithSignature() {
			String id = "w";
			List<Information> informations = completeMethodTrace(id);

			strategy.generate(informations);

			assertMethodContains(class_+id);
			assertMethodContains(method+id);
			assertMethodContains(parameters+id);
		}

		@Test
		public void CreateMethodForRightClassWithSignatureAndReturn() {
			String id = "9";
			List<Information> informations = completeMethodTrace(id);

			strategy.generate(informations);

			assertMethodContains(class_+id);
			assertMethodContains(method+id);
			assertMethodContains(parameters+id);
			assertMethodContains(returnType+id);
		}

		@Test
		public void CreateTwoMethodsInSameHierarchy() {
			String id1 = "001";
			String id2 = "002";
			String method1 = class_ + id1 + space + method+id1+ space
					+ parameters + id1 + space + returnType + id1;
			String method2 = class_ + id2 + space + method+id2 + space
					+ parameters + id2 + space + returnType + id2;
			List<Information> informations = completeMethodTrace(id1);
			informations.addAll(completeMethodTrace(id2));

			strategy.generate(informations);

			assertMethodCreated(method1);
			assertMethodCreated(method2);
		}

		@Test
		public void CreateTwoMethodsNested() {
			String id = "001";
			String method1 = class1 + id + space + methodR + space
					+ parameters + id + space + returnType + id;
			String method2 = class2 + id + space + methodR + space
					+ parameters + id + space + returnType + id;

			List<Information> informations = completeNestedMethodTrace(id);

			strategy.generate(informations);

			assertMethodCreated(method1);
			assertMethodCreated(method2);
		}
	}

	public static class CreateInterface extends AdapterClassToUml2 {
		private DiagramStrategy strategy;
		private List<String> createdInterfaces;

		private void assertInterfaceCreated(String interfaceName) {
			assertTrue(createdInterfaces.contains(interfaceName));
		}

		private void assertNumberOfCreatedInterfaces(int number) {
			assertEquals(number, createdInterfaces.size());
		}

		@Override
		public Interface createInterface(String name) {
			createdInterfaces.add(name);
			return null;
		}

		@Before
		public void setup() {
			strategy = new ClassDiagram(this);
			createdInterfaces = new LinkedList<String>();
		}

		@Test
		public void ifHasNoInterface_DoNotCreate() {
			List<Information> informations = new LinkedList<Information>();

			strategy.generate(informations);

			assertNumberOfCreatedInterfaces(0);
		}

		@Test
		public void CreateInterfaceFromInterfaceInformation() {
			String interfaceName = "myInterface";
			Information info = InformationFactory
					.createInterface(interfaceName);
			List<Information> informations = new LinkedList<Information>();
			informations.add(info);

			strategy.generate(informations);

			assertInterfaceCreated(interfaceName);
		}

		@Test
		public void doesntDuplicateInterfaces() {
			String interfaceName = "myTestClassTarget";
			List<Information> informations = new LinkedList<Information>();

			Information info = InformationFactory
					.createInterface(interfaceName);
			informations.add(info);
			info = InformationFactory.createInterface(interfaceName);
			informations.add(info);

			strategy.generate(informations);

			assertInterfaceCreated(interfaceName);
			assertNumberOfCreatedInterfaces(1);
		}

		@Test
		public void doesntDeleteNotDuplicatedInterfaces() {
			List<Information> informations = new LinkedList<Information>();
			Information info = InformationFactory
					.createInterface("myTestInterface");
			informations.add(info);
			info = InformationFactory.createInterface("myTestInterface2");
			informations.add(info);

			strategy.generate(informations);

			assertInterfaceCreated("myTestInterface");
			assertInterfaceCreated("myTestInterface2");
		}
	}

	public static class CreateTypes extends AdapterClassToUml2 {
		private DiagramStrategy strategy;
		private List<String> createdTypes;

		private void assertTypeCreated(String typeName) {
			assertTrue(createdTypes.contains(typeName));
		}

		private void assertNumberOfCreatedTypes(int number) {
			assertEquals(number, createdTypes.size());
		}

		@Override
		public PrimitiveType createType(String name) {
			createdTypes.add(name);
			return null;
		}

		@Before
		public void setup() {
			strategy = new ClassDiagram(this);
			createdTypes = new LinkedList<String>();
		}

		@Test
		public void CreateTypeFromReturn() {
			List<Information> informations = completeMethodTrace("5");

			strategy.generate(informations);

			assertTypeCreated(returnType + "5");
		}

		@Test
		public void CreateTypeFromSignature() {
			String signature = "int i";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createParameters(signature));

			strategy.generate(informations);

			assertTypeCreated("int");
		}

		@Test
		public void CreateTwoTypesFromSignature() {
			String signature = "int i, double d";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createParameters(signature));

			strategy.generate(informations);

			assertTypeCreated("int");
			assertTypeCreated("double");
		}

		@Test
		public void DoesntRepeatTypes() {
			String parameters = "int i, int d";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createParameters(parameters));

			strategy.generate(informations);

			assertNumberOfCreatedTypes(1);
		}

		@Test
		public void ifHasNoType_DoNotCreate() {
			List<Information> informations = new LinkedList<Information>();
			
			strategy.generate(informations);

			assertNumberOfCreatedTypes(0);
		}
		
		@Test
		public void doNotCreate_IfParameterIsClassType() {
			String className = "Car";
			String parameters = className+" c, int i";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createClass(className));
			informations.add(InformationFactory.createParameters(parameters));
			strategy.generate(informations);
			
			assertTypeCreated("int");
			assertNumberOfCreatedTypes(1);
		}
		@Test
		public void doNotCreate_IfParameterIsInterfaceType() {
			String interfaceName = "Car";
			String parameters = interfaceName+" c, boolean b";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createInterface(interfaceName));
			informations.add(InformationFactory.createParameters(parameters));
			strategy.generate(informations);
			
			assertTypeCreated("boolean");
			assertNumberOfCreatedTypes(1);
		}
	}

	public static class CreateImplementation extends
			AdapterClassToUml2 {
		private DiagramStrategy strategy;
		private List<String> createdImplementations;

		private void assertImplementationCreated(String implementationName) {
			assertTrue(createdImplementations.contains(implementationName));
		}

		private void assertNumberOfCreatedImplementations(int number) {
			assertEquals(number, createdImplementations.size());
		}

		@Override
		public InterfaceRealization createImplementation(String interface_, String implementer) {
			createdImplementations.add(interface_ + " " + implementer);
			return null;
		}

		@Before
		public void setup() {
			strategy = new ClassDiagram(this);
			createdImplementations = new LinkedList<String>();
		}

		@Test
		public void CreateOneImplementation() {
			String id = "A1";
			List<Information> informations = completeInterfaceTrace(id);

			strategy.generate(informations);

			assertImplementationCreated(interface_ + id + space + class_ + id);
		}

		@Test
		public void CreateTwoImplementations() {
			String id1 = "A1";
			String id2 = "B2";
			List<Information> informations = completeInterfaceTrace(id1);
			informations.addAll(completeInterfaceTrace(id2));

			strategy.generate(informations);

			assertImplementationCreated(interface_ + id1 + space + class_ + id1);
			assertImplementationCreated(interface_ + id2 + space + class_ + id2);
			assertNumberOfCreatedImplementations(2);
		}
	}

	public static class CreateUnidirectionalAssociation extends
			AdapterClassToUml2 {
		private DiagramStrategy strategy;
		private List<String> createdAssociations;

		private void assertAssociationCreated(String associationName) {
			assertTrue(createdAssociations.contains(associationName));
		}

		private void assertNumberOfCreatedAssociations(int number) {
			assertEquals(number, createdAssociations.size());
		}

		@Override
		public Association createUnidirectionalAssociation(String caller, String target) {
			createdAssociations.add(caller + " " + target);
			return null;
		}

		@Before
		public void setup() {
			strategy = new ClassDiagram(this);
			createdAssociations = new LinkedList<String>();
		}

		@Test
		public void CreateOneUnidirectional() {
			List<Information> informations = completeNestedConstructorTrace("123");

			strategy.generate(informations);

			assertAssociationCreated("Caller123 Target123");
		}

		@Test
		public void CreateTwoUnidirectionalAssociations() {
			List<Information> informations = completeNestedConstructorTrace("123");
			informations.addAll(completeNestedConstructorTrace("111"));

			strategy.generate(informations);

			assertAssociationCreated("Caller123 Target123");
			assertAssociationCreated("Caller111 Target111");
		}

		@Test
		public void DoesntRepeatUnidirectionalAssociations() {
			List<Information> informations = completeNestedConstructorTrace("123");
			informations.addAll(completeNestedConstructorTrace("123"));

			strategy.generate(informations);

			assertAssociationCreated("Caller123 Target123");
			assertNumberOfCreatedAssociations(1);
		}
	}

	public static class CreateBidirectionalAssociation extends
			AdapterClassToUml2 {
		private DiagramStrategy strategy;
		private List<String> createdBiDirectionalAssociations;

		private void assertBiDirectionalAssociationCreated(
				String associationName) {
			assertTrue(createdBiDirectionalAssociations
					.contains(associationName));
		}

		private void assertNumberOfCreatedBiDirectionalAssociations(int number) {
			assertEquals(number, createdBiDirectionalAssociations.size());
		}

		@Override
		public Association createBidirectionalAssociation(String class1, String class2) {
			createdBiDirectionalAssociations.add(class1 + " " + class2);
			return null;
		}

		@Before
		public void setup() {
			strategy = new ClassDiagram(this);
			createdBiDirectionalAssociations = new LinkedList<String>();
		}

		@Test
		public void CreateBiDirectionalAssociation() {
			List<Information> informations = completeBiDirectionalConstructorTrace("333");

			strategy.generate(informations);

			assertBiDirectionalAssociationCreated("Caller333 Target333");
		}

		@Test
		public void CreateTwoBiDirectionalAssociations() {
			List<Information> informations = completeBiDirectionalConstructorTrace("123");
			informations.addAll(completeBiDirectionalConstructorTrace("111"));

			strategy.generate(informations);

			assertBiDirectionalAssociationCreated("Caller123 Target123");
			assertBiDirectionalAssociationCreated("Caller111 Target111");
		}

		@Test
		public void DoesntRepeatBiDirectionalAssociations() {
			List<Information> informations = completeBiDirectionalConstructorTrace("123");
			informations.addAll(completeBiDirectionalConstructorTrace("123"));

			strategy.generate(informations);

			assertBiDirectionalAssociationCreated("Caller123 Target123");
			assertNumberOfCreatedBiDirectionalAssociations(1);
		}

		@Test
		public void DoesntCreateBiDirectionalAssociationForMethodCalls() {
			List<Information> informations = completeBiDirectionalMethodTrace("123");

			strategy.generate(informations);

			assertNumberOfCreatedBiDirectionalAssociations(0);
		}
	}

	public static class UniBiDirectionalAssociationTests extends
			AdapterClassToUml2 {
		private DiagramStrategy strategy;
		private List<String> createdUnidirectionalAssociations;
		private List<String> createdBiDirectionalAssociations;

		private void assertNumberOfCreatedUnidirectionals(int number) {
			assertEquals(number, createdUnidirectionalAssociations.size());
		}

		private void assertNumberOfCreatedBiDirectionals(int number) {
			assertEquals(number, createdBiDirectionalAssociations.size());
		}

		@Override
		public Association createUnidirectionalAssociation(String caller, String target) {
			createdUnidirectionalAssociations.add(caller + " " + target);
			return null;
		}

		@Override
		public Association createBidirectionalAssociation(String class1, String class2) {
			createdBiDirectionalAssociations.add(class1 + " " + class2);
			return null;
		}

		@Before
		public void setup() {
			strategy = new ClassDiagram(this);
			createdUnidirectionalAssociations = new LinkedList<String>();
			createdBiDirectionalAssociations = new LinkedList<String>();
		}

		@Test
		public void BiDirectional_DoesntCreate_Unidirectional() {
			List<Information> informations = completeBiDirectionalConstructorTrace("333");

			strategy.generate(informations);

			assertNumberOfCreatedUnidirectionals(0);
		}

		@Test
		public void Unidirectional_DoesntCreate_BiDirectional() {
			List<Information> informations = completeConstructorTrace("123");

			strategy.generate(informations);

			assertNumberOfCreatedBiDirectionals(0);
		}
	}

	public static class CreateDependency extends AdapterClassToUml2 {
		private DiagramStrategy strategy;
		private List<String> createdDependencies;

		private void assertDependencyCreated(String dependencyName) {
			assertTrue(createdDependencies.contains(dependencyName));
		}

		private void assertNumberOfCreatedDependencies(int number) {
			assertEquals(number, createdDependencies.size());
		}

		@Override
		public Dependency createDependency(String caller, String target) {
			createdDependencies.add(caller + " " + target);
			return null;
		}

		@Before
		public void setup() {
			strategy = new ClassDiagram(this);
			createdDependencies = new LinkedList<String>();
		}

		@Test
		public void CreateOneDependency() {
			String id = "123";
			List<Information> informations = completeNestedMethodTrace(id);

			strategy.generate(informations);

			assertDependencyCreated(class1 + id + space + class2 + id);
		}

		@Test
		public void CreateTwoDependencies() {
			String id1 = "123";
			String id2 = "111";
			List<Information> informations = completeNestedMethodTrace(id1);
			informations.addAll(completeNestedMethodTrace(id2));

			strategy.generate(informations);

			assertDependencyCreated(class1 + id1 + space + class2 + id1);
			assertDependencyCreated(class1 + id2 + space + class2 + id2);
			assertNumberOfCreatedDependencies(2);
		}

		@Test
		public void DoesntRepeatDependencies() {
			List<Information> informations = completeNestedMethodTrace("123");
			informations.addAll(completeNestedMethodTrace("123"));

			strategy.generate(informations);

			assertDependencyCreated("Caller123 Target123");
			assertNumberOfCreatedDependencies(1);
		}
		@Test
		public void createDependency_TargetIsInterface(){
			String id = "123";
			List<Information> informations = completeNestedInterfaceTrace(id);

			strategy.generate(informations);

			assertDependencyCreated(class1 + id + space + interface_ + id);
			assertNumberOfCreatedDependencies(1);
		}
		@Test
		public void doesntCreateDependency_IfThereIsUnidirectionalAssociation(){
			String id = "123";
			List<Information> informations = completeNestedConstructorTrace(id);
			informations.addAll(completeNestedMethodTrace(id));
			
			strategy.generate(informations);
			
			assertNumberOfCreatedDependencies(0);
		}
		@Test
		public void doesntCreateDependency_ForNotUnidirectionalAssociation(){
			String id = "123";
			String id2 = "aaa";
			List<Information> informations = completeNestedConstructorTrace(id);
			informations.addAll(completeNestedMethodTrace(id));
			informations.addAll(completeNestedMethodTrace(id2));
			
			strategy.generate(informations);
			
			assertNumberOfCreatedDependencies(1);
		}
		@Test
		public void doesntCreateDependency_IfThereIsBidirectionalAssociation(){
			String id = "123";
			List<Information> informations = completeBiDirectionalConstructorTrace(id);
			informations.addAll(completeNestedMethodTrace(id));
			
			strategy.generate(informations);
			
			assertNumberOfCreatedDependencies(0);
		}
		@Test
		public void CreateDependency_ForNotBidirectionalAssociation(){
			String id = "123";
			String id2 = "dependency";
			List<Information> informations = completeBiDirectionalConstructorTrace(id);
			informations.addAll(completeNestedMethodTrace(id));
			informations.addAll(completeNestedMethodTrace(id2));
			
			strategy.generate(informations);
			
			assertNumberOfCreatedDependencies(1);
		}
	}

	public static class AssociationDependenciyTests extends
			AdapterClassToUml2 {
		private DiagramStrategy strategy;
		private List<String> createdUnidirectionalAssociations;
		private List<String> createdBiDirectionalAssociations;
		private List<String> createdDependencies;

		private void assertNumberOfCreatedUnidirectionals(int number) {
			assertEquals(number, createdUnidirectionalAssociations.size());
		}

		private void assertNumberOfCreatedBiDirectionals(int number) {
			assertEquals(number, createdBiDirectionalAssociations.size());
		}

		private void assertNumberOfCreatedDependencies(int number) {
			assertEquals(number, createdDependencies.size());
		}

		@Override
		public Association createUnidirectionalAssociation(String caller, String target) {
			createdUnidirectionalAssociations.add(caller + " " + target);
			return null;
		}

		@Override
		public Association createBidirectionalAssociation(String class1, String class2) {
			createdBiDirectionalAssociations.add(class1 + " " + class2);
			return null;
		}

		@Override
		public Dependency createDependency(String caller, String target) {
			createdDependencies.add(caller + " " + target);
			return null;
		}

		@Before
		public void setup() {
			strategy = new ClassDiagram(this);
			createdUnidirectionalAssociations = new LinkedList<String>();
			createdBiDirectionalAssociations = new LinkedList<String>();
			createdDependencies = new LinkedList<String>();
		}

		@Test
		public void DoesntCreateUnidirectionalForMethodCalls() {
			List<Information> informations = completeMethodTrace("123");

			strategy.generate(informations);

			assertNumberOfCreatedUnidirectionals(0);
		}

		@Test
		public void DoesntCreateBiDirectionalForMethodCalls() {
			List<Information> informations = completeMethodTrace("123");

			strategy.generate(informations);

			assertNumberOfCreatedBiDirectionals(0);
		}

		@Test
		public void DoesntCreatedDependencyForConstructorCalls() {
			List<Information> informations = completeConstructorTrace("123");

			strategy.generate(informations);

			assertNumberOfCreatedDependencies(0);
		}
	}
}
