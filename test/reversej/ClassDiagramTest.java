package reversej;

import static org.junit.Assert.*;
import static reversej.TestUtilities.*;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PrimitiveType;
import org.junit.*;

import reversej.diagram.DiagramStrategy;
import reversej.diagram.Information;
import reversej.diagram.ModelAdapter;
import reversej.diagram.informationmodel.InformationFactoryImpl;
import reversej.diagram.strategies.ClassDiagram;
import reversej.diagram.strategies.uml2adapter.AdapterClassToUml2;

public class ClassDiagramTest {
	private static List<String> createdDependencies;
	private static List<String> createdUnidirectionalAssociations;
	private static List<String> createdBiDirectionalAssociations;
	private static List<String> createdAssociations;
	private static List<String> createdImplementations;
	private static String lastMethod;
	private static List<String> createdMethods;
	private static List<String> createdClasses;
	private static List<String> createdTypes;
	private static DiagramStrategy strategy;
	private static List<String> createdInterfaces;
	static AdapterClassToUml2 adapter;
	@Before
	public void setup(){
		createdDependencies = new LinkedList<String>();
		createdUnidirectionalAssociations = new LinkedList<String>();
		createdBiDirectionalAssociations = new LinkedList<String>();
		createdAssociations = new LinkedList<String>();
		createdImplementations = new LinkedList<String>();
		createdTypes = new LinkedList<String>();
		createdMethods = new LinkedList<String>();
		lastMethod = "";
		createdInterfaces = new LinkedList<String>();
		createdClasses = new LinkedList<String>();
		adapter = new AdapterClassToUml2(){
			@Override
			public Interface createInterface(String name) {
				createdInterfaces.add(name);
				return null;
			}
			@Override
			public Class createConcreteClass(String name) {
				createdClasses.add(name);
				return null;
			}
			@Override
			public PrimitiveType createType(String name) {
				createdTypes.add(name);
				return null;
			}
			@Override
			public InterfaceRealization createImplementation(String interface_,
					String implementer) {
				createdImplementations.add(interface_ + " " + implementer);
				return null;
			}
			@Override
			public Association createUnidirectionalAssociation(String source,
					String target) {
				createdAssociations.add(source + " " + target);
				return null;
			}
			@Override
			public Association createBidirectionalAssociation(String class1,
					String class2) {
				createdBiDirectionalAssociations.add(class1 + " " + class2);
				return null;
			}
			@Override
			public Dependency createDependency(String source, String target) {
				createdDependencies.add(source + " " + target);
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
			@Override
			public Operation createMethod(String className, String methodName,
					String signature) {
				String createdMethod = (className + " " + methodName + " " + signature)
						.trim();
				createdMethods.add(createdMethod);
				lastMethod = createdMethod;
				return null;
			}
		};
		strategy = new ClassDiagram(adapter);
	}
	public static class GeneralTests{
		@Test
		public void constructorSetsAdapter() {
			AdapterClassToUml2 expected = AdapterClassToUml2.make();
			strategy = new ClassDiagram(expected);
			ModelAdapter actual = strategy.getAdapter();
			assertEquals(expected, actual);
		}
		@Test
		public void returnsPackageFromAdapter(){
			AdapterClassToUml2 adapter = AdapterClassToUml2.make();
			strategy = new ClassDiagram(adapter);
			org.eclipse.uml2.uml.Package p = strategy.generate(null);
			assertNotNull(p);
			assertEquals(adapter.getPackage(), p);
		}
	}

	public static class CreateClass extends ClassDiagramTest{
		private void assertClassCreated(String className) {
			assertTrue(createdClasses.contains(className));
		}
		private void assertNumberOfCreatedClasses(int number) {
			assertEquals(number, createdClasses.size());
		}

		@Test
		public void ifHasNoClass_DoNotCreate() {
			List<Information> informations = new LinkedList<Information>();

			strategy.generate(informations);

			assertNumberOfCreatedClasses(0);
		}

		@Test
		public void CreateConcreteClass() {
			String id = "123";
			List<Information> informations = new LinkedList<Information>();
			informations.addAll(completeMethodTrace(id));

			strategy.generate(informations);

			assertClassCreated(CLASS+id);
		}

		@Test@Ignore("There is no handler implementation in tracer")
		public void CreateConcreteClassForHandler() {
			String className = "myTestClassTarget";
			Information info = InformationFactoryImpl.createHandler(className);
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

			assertClassCreated(CLASS + "001");
			assertClassCreated(CLASS + "002");
		}
	}

	public static class CreateMethod extends ClassDiagramTest{
		private void assertMethodContains(String content) {
			assertTrue(lastMethod.contains(content));
		}

		private void assertMethodCreated(String methodName) {
			assertTrue("Expected: \""+methodName+"\" but not found",createdMethods.contains(methodName));
		}

		private void assertNumberOfCreatedMethods(int number) {
			assertEquals(number, createdMethods.size());
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

			assertMethodContains(CLASS+id);
			assertMethodContains(METHOD+id);
			assertMethodContains(PARAMETERS+id);
		}

		@Test
		public void CreateMethodForRightClassWithSignatureAndReturn() {
			String id = "9";
			List<Information> informations = completeMethodTrace(id);

			strategy.generate(informations);

			assertMethodContains(CLASS+id);
			assertMethodContains(METHOD+id);
			assertMethodContains(PARAMETERS+id);
			assertMethodContains(RETURN_TYPE+id);
		}

		@Test
		public void CreateTwoMethodsInSameHierarchy() {
			String id1 = "001";
			String id2 = "002";
			String method1 = CLASS + id1 + SPACE + METHOD+id1+ SPACE
					+ PARAMETERS + id1 + SPACE + RETURN_TYPE + id1;
			String method2 = CLASS + id2 + SPACE + METHOD+id2 + SPACE
					+ PARAMETERS + id2 + SPACE + RETURN_TYPE + id2;
			List<Information> informations = completeMethodTrace(id1);
			informations.addAll(completeMethodTrace(id2));

			strategy.generate(informations);

			assertMethodCreated(method1);
			assertMethodCreated(method2);
		}

		@Test
		public void CreateTwoMethodsNested() {
			String id = "001";
			String method1 = CLASS1 + id + SPACE + METHOD_R + SPACE
					+ PARAMETERS + id + SPACE + RETURN_TYPE + id;
			String method2 = CLASS2 + id + SPACE + METHOD_R + SPACE
					+ PARAMETERS + id + SPACE + RETURN_TYPE + id;

			List<Information> informations = completeNestedMethodTrace(id);

			strategy.generate(informations);

			assertMethodCreated(method1);
			assertMethodCreated(method2);
		}
		@Test
		public void CreateInterfaceMethod() {
			String id = "001";
			String methodInInterface = INTERFACE+id + SPACE + METHOD+id + SPACE
					+ PARAMETERS + id + SPACE + RETURN_TYPE + id;
			List<Information> informations = new LinkedList<Information>();
					informations.addAll(completeInterfaceTrace(id));

			strategy.generate(informations);

			assertMethodCreated(methodInInterface);
		}
		@Test
		public void CreateOneInterfaceAndTwoClassMethodsNested() {
			String id = "001";
			String method1 = CLASS1+id + SPACE + METHOD_R + SPACE
					+ PARAMETERS + id + SPACE + RETURN_TYPE + id;
			String methodInInterface = INTERFACE+id + SPACE + METHOD_R + SPACE
					+ PARAMETERS + id + SPACE + RETURN_TYPE + id;
			String method2 = CLASS2 + id + SPACE + METHOD_R + SPACE
					+ PARAMETERS + id + SPACE + RETURN_TYPE + id;

			List<Information> informations = completeNestedInterfaceTrace(id);

			strategy.generate(informations);

			assertMethodCreated(method1);
			assertMethodCreated(method2);
			assertMethodCreated(methodInInterface);
		}
	}

	public static class CreateInterface extends ClassDiagramTest {
		private void assertInterfaceCreated(String interfaceName) {
			assertTrue(createdInterfaces.contains(interfaceName));
		}

		private void assertNumberOfCreatedInterfaces(int number) {
			assertEquals(number, createdInterfaces.size());
		}

		@Test
		public void ifHasNoInterface_DoNotCreate() {
			List<Information> informations = new LinkedList<Information>();

			strategy.generate(informations);

			assertNumberOfCreatedInterfaces(0);
		}

		@Test
		public void CreateInterfaceFromInterfaceInformation() {
			String id = "987";
			List<Information> informations = new LinkedList<Information>();
			informations.addAll(completeInterfaceTrace(id));

			strategy.generate(informations);

			assertInterfaceCreated(INTERFACE+id);
		}

		@Test
		public void doesntDuplicateInterfaces() {
			String id = "56";
			List<Information> informations = new LinkedList<Information>();
			informations.addAll(completeNestedInterfaceTrace(id));
			informations.addAll(completeNestedInterfaceTrace(id));
			
			strategy.generate(informations);

			assertInterfaceCreated(INTERFACE+id);
			assertNumberOfCreatedInterfaces(1);
		}

		@Test
		public void doesntDeleteNotDuplicatedInterfaces() {
			String id1 = "01";
			String id2 = "02";
			List<Information> informations = new LinkedList<Information>();
			informations.addAll(completeNestedInterfaceTrace(id1));
			informations.addAll(completeNestedInterfaceTrace(id2));

			strategy.generate(informations);

			assertInterfaceCreated(INTERFACE+id1);
			assertInterfaceCreated(INTERFACE+id2);
		}
	}

	public static class CreateTypes extends ClassDiagramTest {
		private void assertTypeCreated(String typeName) {
			assertTrue(createdTypes.contains(typeName));
		}

		private void assertNumberOfCreatedTypes(int number) {
			assertEquals(number, createdTypes.size());
		}
		@Test
		public void CreateTypeFromReturn() {
			List<Information> informations = completeMethodTrace("5");

			strategy.generate(informations);

			assertTypeCreated(RETURN_TYPE + "5");
		}

		@Test
		public void CreateTypeFromSignature() {
			String signature = "int i";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactoryImpl.createClass("aaa"));
			informations.add(InformationFactoryImpl.createModifiers("public"));
			informations.add(InformationFactoryImpl.createMethod("do"));
			informations.add(InformationFactoryImpl.createParameters(signature));
			informations.add(InformationFactoryImpl.createReturn("void"));

			strategy.generate(informations);

			assertTypeCreated("int");
		}

		@Test
		public void CreateTwoTypesFromSignature() {
			String signature = "int i, double d";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactoryImpl.createClass("aaa"));
			informations.add(InformationFactoryImpl.createModifiers("public"));
			informations.add(InformationFactoryImpl.createMethod("do"));
			informations.add(InformationFactoryImpl.createParameters(signature));
			informations.add(InformationFactoryImpl.createReturn("void"));

			strategy.generate(informations);

			assertTypeCreated("int");
			assertTypeCreated("double");
		}

		@Test
		public void DoesntRepeatTypes() {
			String parameters = "int i, int d";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactoryImpl.createClass("aaa"));
			informations.add(InformationFactoryImpl.createModifiers("public"));
			informations.add(InformationFactoryImpl.createMethod("do"));
			informations.add(InformationFactoryImpl.createParameters(parameters));
			informations.add(InformationFactoryImpl.createReturn("void"));

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
			informations.add(InformationFactoryImpl.createClass(className));
			informations.add(InformationFactoryImpl.createModifiers("public"));
			informations.add(InformationFactoryImpl.createMethod("do"));
			informations.add(InformationFactoryImpl.createParameters(parameters));
			informations.add(InformationFactoryImpl.createReturn("void"));
			
			strategy.generate(informations);
			
			assertTypeCreated("int");
			assertNumberOfCreatedTypes(1);
		}
		@Test
		public void doNotCreate_IfParameterIsInterfaceType() {
			String interfaceName = "Car";
			String parameters = interfaceName+" c, boolean b";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactoryImpl.createInterface(interfaceName));
			informations.add(InformationFactoryImpl.createClass("aaa"));
			informations.add(InformationFactoryImpl.createModifiers("public"));
			informations.add(InformationFactoryImpl.createMethod("do"));
			informations.add(InformationFactoryImpl.createParameters(parameters));
			informations.add(InformationFactoryImpl.createReturn("void"));
			strategy.generate(informations);
			
			assertTypeCreated("boolean");
			assertNumberOfCreatedTypes(1);
		}
	}

	public static class CreateImplementation extends ClassDiagramTest {
		private void assertImplementationCreated(String implementationName) {
			assertTrue(createdImplementations.contains(implementationName));
		}

		private void assertNumberOfCreatedImplementations(int number) {
			assertEquals(number, createdImplementations.size());
		}
		
		@Test
		public void CreateOneImplementation() {
			String id = "A1";
			List<Information> informations = completeInterfaceTrace(id);

			strategy.generate(informations);

			assertImplementationCreated(INTERFACE + id + SPACE + CLASS + id);
		}

		@Test
		public void CreateTwoImplementations() {
			String id1 = "A1";
			String id2 = "B2";
			List<Information> informations = completeInterfaceTrace(id1);
			informations.addAll(completeInterfaceTrace(id2));

			strategy.generate(informations);

			assertImplementationCreated(INTERFACE + id1 + SPACE + CLASS + id1);
			assertImplementationCreated(INTERFACE + id2 + SPACE + CLASS + id2);
			assertNumberOfCreatedImplementations(2);
		}
	}

	public static class CreateUnidirectionalAssociation extends ClassDiagramTest {
		private void assertAssociationCreated(String associationName) {
			assertTrue(createdAssociations.contains(associationName));
		}

		private void assertNumberOfCreatedAssociations(int number) {
			assertEquals(number, createdAssociations.size());
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

	public static class CreateBidirectionalAssociation extends ClassDiagramTest {
		private void assertBiDirectionalAssociationCreated(
				String associationName) {
			assertTrue(createdBiDirectionalAssociations
					.contains(associationName));
		}

		private void assertNumberOfCreatedBiDirectionalAssociations(int number) {
			assertEquals(number, createdBiDirectionalAssociations.size());
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

	public static class UniBiDirectionalAssociation extends ClassDiagramTest {

		private void assertNumberOfCreatedUnidirectionals(int number) {
			assertEquals(number, createdUnidirectionalAssociations.size());
		}

		private void assertNumberOfCreatedBiDirectionals(int number) {
			assertEquals(number, createdBiDirectionalAssociations.size());
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

	public static class CreateDependency extends ClassDiagramTest {
		private void assertDependencyCreated(String dependencyName) {
			assertTrue(createdDependencies.contains(dependencyName));
		}

		private void assertNumberOfCreatedDependencies(int number) {
			assertEquals(number, createdDependencies.size());
		}

		
		@Test
		public void CreateOneDependency() {
			String id = "123";
			List<Information> informations = completeNestedMethodTrace(id);

			strategy.generate(informations);

			assertDependencyCreated(CLASS1 + id + SPACE + CLASS2 + id);
		}

		@Test
		public void CreateTwoDependencies() {
			String id1 = "123";
			String id2 = "111";
			List<Information> informations = completeNestedMethodTrace(id1);
			informations.addAll(completeNestedMethodTrace(id2));

			strategy.generate(informations);

			assertDependencyCreated(CLASS1 + id1 + SPACE + CLASS2 + id1);
			assertDependencyCreated(CLASS1 + id2 + SPACE + CLASS2 + id2);
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

			assertDependencyCreated(CLASS1 + id + SPACE + INTERFACE + id);
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

	public static class AssociationDependenciy extends ClassDiagramTest {
		private void assertNumberOfCreatedUnidirectionals(int number) {
			assertEquals(number, createdUnidirectionalAssociations.size());
		}

		private void assertNumberOfCreatedBiDirectionals(int number) {
			assertEquals(number, createdBiDirectionalAssociations.size());
		}

		private void assertNumberOfCreatedDependencies(int number) {
			assertEquals(number, createdDependencies.size());
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
