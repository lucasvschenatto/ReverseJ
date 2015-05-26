package reverseJ;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.*;

public class ClassDiagramStrategyTest{
	public static List<Information> createCompleteMethodTrace(String id){
		String caller     = "Caller"  +id;
		String target     = "Target"  +id;
		String modifiers  = "modifier"+id;
		String method     = "method"  +id;
		String signature  = "double d"+id;
		String returnType = "boolean" +id;
		
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createCaller(caller));
		informations.add(InformationFactory.createTarget(target));
		informations.add(InformationFactory.createModifiers(modifiers));
		informations.add(InformationFactory.createMethod(method));
		informations.add(InformationFactory.createSignature(signature));
		informations.add(InformationFactory.createReturn(returnType));
		
		return informations;
	}
	public static List<Information> createCompleteConstructorTrace(String id){
		String caller     = "Caller"  +id;
		String target     = "Target"  +id;
		String modifiers  = "modifier"+id;
		String method     = "<init>";
		String signature  = "double d"+id;
		String returnType = target;
		
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createCaller(caller));
		informations.add(InformationFactory.createTarget(target));
		informations.add(InformationFactory.createModifiers(modifiers));
		informations.add(InformationFactory.createMethod(method));
		informations.add(InformationFactory.createSignature(signature));
		informations.add(InformationFactory.createReturn(returnType));
		
		return informations;
	}
	public static List<Information> createCompleteBiDirectionalConstructorTrace(String id){
		String caller     = "Caller"  +id;
		String target     = "Target"  +id;
		String modifiers  = "modifier"+id;
		String method     = "<init>";
		String signature  = "double d"+id;
		//returnType is either caller or target
		
		List<Information> informations = new LinkedList<Information>();
		
		informations.add(InformationFactory.createCaller(caller));
		informations.add(InformationFactory.createTarget(target));
		informations.add(InformationFactory.createModifiers(modifiers));
		informations.add(InformationFactory.createMethod(method));
		informations.add(InformationFactory.createSignature(signature));
		informations.add(InformationFactory.createReturn(target));
		
		informations.add(InformationFactory.createCaller(target));
		informations.add(InformationFactory.createTarget(caller));
		informations.add(InformationFactory.createModifiers(modifiers));
		informations.add(InformationFactory.createMethod(method));
		informations.add(InformationFactory.createSignature(signature));
		informations.add(InformationFactory.createReturn(caller));
		
		return informations;
	}
	public static List<Information> createCompleteBiDirectionalMethodTrace(String id){
		String caller     = "Caller"  +id;
		String target     = "Target"  +id;
		String modifiers  = "modifier"+id;
		String method     = "method"  +id;
		String signature  = "double d"+id;
		String returnType = "boolean" +id;
		
		List<Information> informations = new LinkedList<Information>();
		
		informations.add(InformationFactory.createCaller(caller));
		informations.add(InformationFactory.createTarget(target));
		informations.add(InformationFactory.createModifiers(modifiers));
		informations.add(InformationFactory.createMethod(method));
		informations.add(InformationFactory.createSignature(signature));
		informations.add(InformationFactory.createReturn(returnType));
		
		informations.add(InformationFactory.createCaller(target));
		informations.add(InformationFactory.createTarget(caller));
		informations.add(InformationFactory.createModifiers(modifiers));
		informations.add(InformationFactory.createMethod(method));
		informations.add(InformationFactory.createSignature(signature));
		informations.add(InformationFactory.createReturn(returnType));
		
		return informations;
	}
	public static List<Information> nestInformations(List<Information> nesting, List<Information> nested){
		Information last = nesting.get(nesting.size()-1);
		nesting.remove(nesting.size()-1);
		nesting.addAll(nested);
		nesting.add(last);
		return nesting;
	}
	public static class GeneralTests extends ClassDiagramUtilities{
		private DiagramStrategy strategy;
		@Test
		public void constructorSetsUtilities(){
			ClassDiagramUtilities expected = ClassDiagramUtilities.make();
			strategy = new ClassDiagram(expected);
			ClassDiagramUtilities actual = strategy.getUtil();

			assertEquals(expected, actual);
		}
		@Test
		public void noParameterConstructorSetsUtilities(){
			assertNotNull(new ClassDiagram().getUtil());
		}
	}
	public static class CreateClass extends ClassDiagramUtilities{
		private DiagramStrategy strategy;
		private List<String> createdClasses;
		
		private void assertClassCreated(String className) {
			assertTrue(createdClasses.contains(className));
		}
		private void assertNumberOfCreatedClasses(int number) {
			assertEquals(number, createdClasses.size());	
		}
		@Override
		public void createConcreteClass(String name){
			createdClasses.add(name);
		}
		@Before
		public void setup(){
			strategy = new ClassDiagram(this);
			createdClasses = new LinkedList<String>();
		}
		@Test
		public void ifHasNoClass_DoNotCreate(){
			List<Information> informations = new LinkedList<Information>();
			
			strategy.generate(informations);
			
			assertNumberOfCreatedClasses(0);
		}
		@Test
		public void CreateConcreteClassForCaller() {
			String className = "myTestClassCaller";
			Information info = InformationFactory.createCaller(className);
			List<Information> informations = new LinkedList<Information>();
			informations.add(info);
			
			strategy.generate(informations);
			
			assertClassCreated(className);
		}		
		@Test
		public void CreateConcreteClassForTarget() {
			String className = "myTestClassTarget";
			Information info = InformationFactory.createTarget(className);
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
			List<Information> informations = createCompleteMethodTrace("001");
			informations.addAll(createCompleteMethodTrace("001"));
			informations.addAll(createCompleteMethodTrace("001"));
			informations.addAll(createCompleteMethodTrace("001"));
			informations.addAll(createCompleteMethodTrace("001"));
			
			strategy.generate(informations);
			
			assertNumberOfCreatedClasses(2);
		}		
		@Test
		public void doesntDeleteNotDuplicatedClasses() {
			List<Information> informations = createCompleteMethodTrace("001");
			informations.addAll(createCompleteMethodTrace("002"));
			
			strategy.generate(informations);
			
			assertClassCreated("Target001" );
			assertClassCreated("Target002" );
			assertClassCreated("Caller001" );
			assertClassCreated("Caller002" );
		}
	}
	public static class CreateMethod extends ClassDiagramUtilities{
		private DiagramStrategy strategy;
		private String lastMethod;
		private List<String> createdMethods;
		private void assertMethodContains(String content){
			lastMethod.contains(content);
		}
		private void assertMethodCreated(String methodName) {
			assertTrue(createdMethods.contains(methodName));
		}
		private void assertNumberOfCreatedMethods(int number) {
			assertEquals(number, createdMethods.size());	
		}
		@Override
		public void createMethod(String className, String methodName, String signature) {
			String createdMethod = (className+" "+methodName+" "+signature).trim();
			createdMethods.add(createdMethod);
			lastMethod = createdMethod;
		}
		@Override
		public void createMethodWithReturn(String className, String methodName, String signature, String returnType) {
			String createdMethod = (className+" "+methodName+" "+signature+" "+returnType).trim();
			createdMethods.add(createdMethod);
			lastMethod = createdMethod;
		}
		@Before
		public void setup(){
			strategy = new ClassDiagram(this);
			createdMethods = new LinkedList<String>();
			lastMethod = "";
		}
		@Test
		public void ifHasNoMethods_DoNotCreate(){
			List<Information> informations = new LinkedList<Information>();
			
			strategy.generate(informations);
			
			assertNumberOfCreatedMethods(0);
		}
		
		@Test
		public void CreateMethodForRightClassWithSignature(){
			List<Information> informations = new LinkedList<Information>();
			String className = "containingClass";
			String methodName = "myMethod";
			String signature = "int number";
			Information t = InformationFactory.createTarget(className);
			Information m = InformationFactory.createMethod(methodName);
			Information s = InformationFactory.createSignature(signature);
						
			informations.add(t);
			informations.add(m);
			informations.add(s);
			
			strategy.generate(informations);
			
			assertMethodContains(className);
			assertMethodContains(methodName);
			assertMethodContains(signature);
		}
		@Test
		public void CreateMethodForRightClassWithSignatureAndReturn(){
			List<Information> informations = createCompleteMethodTrace("9");
			
			strategy.generate(informations);
			
			assertMethodContains("Target9");
			assertMethodContains("method9");
			assertMethodContains("double d9");
			assertMethodContains("boolean9");
		}
		@Test
		public void CreateTwoMethodsInSameHierarchy(){
			List<Information> informations = createCompleteMethodTrace("001");
			informations.addAll(createCompleteMethodTrace("002"));
			
			String method1 = "Target001 method001 double d001 boolean001";
			String method2 = "Target002 method002 double d002 boolean002";
			strategy.generate(informations);
			
			assertMethodCreated(method1);
			assertMethodCreated(method2);
		}
		
		@Test
		public void CreateTwoMethodsNested(){
			List<Information> informations;
			informations = nestInformations( createCompleteMethodTrace("001"),
											createCompleteMethodTrace("002"));			
			String method1 = "Target001 method001 double d001 boolean001";
			String method2 = "Target002 method002 double d002 boolean002";
			
			strategy.generate(informations);
			
			assertMethodCreated(method1);
			assertMethodCreated(method2);
		}
	}
	public static class CreateInterface extends ClassDiagramUtilities{
		private DiagramStrategy strategy;
		private List<String> createdInterfaces;
		
		private void assertInterfaceCreated(String interfaceName) {
			assertTrue(createdInterfaces.contains(interfaceName));
		}
		private void assertNumberOfCreatedInterfaces(int number) {
			assertEquals(number, createdInterfaces.size());	
		}
		
		@Override
		public void createInterface(String name){
			createdInterfaces.add(name);
		}
		
		@Before
		public void setup(){
			strategy = new ClassDiagram(this);
			createdInterfaces = new LinkedList<String>();
		}
		@Test
		public void ifHasNoInterface_DoNotCreate(){
			List<Information> informations = new LinkedList<Information>();
			
			strategy.generate(informations);
			
			assertNumberOfCreatedInterfaces(0);
		}
		@Test
		public void CreateInterfaceFromInterfaceInformation() {
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
		public void doesntDeleteNotDuplicatedInterfaces() {
			List<Information> informations = new LinkedList<Information>();
			Information info = InformationFactory.createInterface("myTestInterface");
			informations.add(info);
			info = InformationFactory.createInterface("myTestInterface2");
			informations.add(info);
			
			strategy.generate(informations);
			
			assertInterfaceCreated("myTestInterface" );
			assertInterfaceCreated("myTestInterface2");
		}
	}
	public static class CreateTypes extends ClassDiagramUtilities{
		private DiagramStrategy strategy;
		private List<String> createdTypes;
		
		private void assertTypeCreated(String typeName) {
			assertTrue(createdTypes.contains(typeName));
		}
		private void assertNumberOfCreatedTypes(int number) {
			assertEquals(number, createdTypes.size());	
		}
		
		@Override
		public void createType(String name){
			createdTypes.add(name);
		}
		
		@Before
		public void setup(){
			strategy = new ClassDiagram(this);
			createdTypes = new LinkedList<String>();
		}
		@Test
		public void CreateTypeFromReturn() {
			String returnType = "boolean";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createReturn(returnType));
			
			strategy.generate(informations);
			
			assertTypeCreated("boolean");
		}
		@Test
		public void CreateTypeFromSignature() {
			String signature = "int i";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createSignature(signature));
			
			strategy.generate(informations);
			
			assertTypeCreated("int");
		}
		@Test
		public void CreateTwoTypesFromSignature() {
			String signature = "int i, double d";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createSignature(signature));
			
			strategy.generate(informations);
			
			assertTypeCreated("int");
			assertTypeCreated("double");
		}
		@Test
		public void DoesntRepeatTypes() {
			String signature = "int i, int d";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createSignature(signature));
			
			strategy.generate(informations);
			
			assertNumberOfCreatedTypes(1);
		}
		@Test
		public void ifHasNoType_DoNotCreate(){
			List<Information> informations = new LinkedList<Information>();
			
			strategy.generate(informations);
			
			assertNumberOfCreatedTypes(0);
		}
	}
	public static class CreateImplementation extends ClassDiagramUtilities{
		private DiagramStrategy strategy;
		private List<String> createdImplementations;
		
		private void assertImplementationCreated(String implementationName) {
			assertTrue(createdImplementations.contains(implementationName));
		}
		private void assertNumberOfCreatedImplementations(int number) {
			assertEquals(number, createdImplementations.size());	
		}
		
		@Override
		public void createImplementation(String interface_, String implementer){
			createdImplementations.add(interface_+" "+implementer);
		}
		
		@Before
		public void setup(){
			strategy = new ClassDiagram(this);
			createdImplementations = new LinkedList<String>();
		}
		@Test
		public void CreateOneImplementation() {
			String interfaceName = "Human";
			String target = "Female";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createInterface(interfaceName));
			informations.add(InformationFactory.createTarget(target));
			
			strategy.generate(informations);
			
			assertImplementationCreated("Human Female");
		}
		@Test
		public void CreateTwoImplementations() {
			String interface1 = "Human";
			String target1 = "Female";
			String interface2 = "Female";
			String target2 = "Girl";
			List<Information> informations = new LinkedList<Information>();
			informations.add(InformationFactory.createInterface(interface1));
			informations.add(InformationFactory.createTarget(target1));
			informations.add(InformationFactory.createInterface(interface2));
			informations.add(InformationFactory.createTarget(target2));
			
			strategy.generate(informations);
			
			assertImplementationCreated("Human Female");
			assertImplementationCreated("Female Girl");
			assertNumberOfCreatedImplementations(2);
		}
	}
	public static class CreateUnidirectionalAssociation extends ClassDiagramUtilities{
		private DiagramStrategy strategy;
		private List<String> createdAssociations;
		
		private void assertAssociationCreated(String associationName) {
			assertTrue(createdAssociations.contains(associationName));
		}
		private void assertNumberOfCreatedAssociations(int number) {
			assertEquals(number, createdAssociations.size());	
		}
		
		@Override
		public void createUnidirectionalAssociation(String caller, String target){
			createdAssociations.add(caller+" "+target);
		}
		
		@Before
		public void setup(){
			strategy = new ClassDiagram(this);
			createdAssociations = new LinkedList<String>();
		}
		@Test
		public void CreateOneUnidirectional() {
			List<Information> informations = createCompleteConstructorTrace("123");
			
			strategy.generate(informations);
			
			assertAssociationCreated("Caller123 Target123");
		}
		@Test
		public void CreateTwoUnidirectionalAssociations() {
			List<Information> informations = createCompleteConstructorTrace("123");
			informations.addAll(createCompleteConstructorTrace("111"));
			
			strategy.generate(informations);
			
			assertAssociationCreated("Caller123 Target123");
			assertAssociationCreated("Caller111 Target111");
		}
		@Test
		public void DoesntRepeatUnidirectionalAssociations() {
			List<Information> informations = createCompleteConstructorTrace("123");
			informations.addAll(createCompleteConstructorTrace("123"));
			
			strategy.generate(informations);
			
			assertAssociationCreated("Caller123 Target123");
			assertNumberOfCreatedAssociations(1);
		}
	}
	public static class CreateBidirectionalAssociation extends ClassDiagramUtilities{
		private DiagramStrategy strategy;
		private List<String> createdBiDirectionalAssociations;
		
		private void assertBiDirectionalAssociationCreated(String associationName) {
			assertTrue(createdBiDirectionalAssociations.contains(associationName));
		}
		private void assertNumberOfCreatedBiDirectionalAssociations(int number) {
			assertEquals(number, createdBiDirectionalAssociations.size());	
		}
		
		@Override
		public void createBiDirectionalAssociation(String class1, String class2){
			createdBiDirectionalAssociations.add(class1+" "+class2);
		}
		
		@Before
		public void setup(){
			strategy = new ClassDiagram(this);
			createdBiDirectionalAssociations = new LinkedList<String>();
		}
		@Test
		public void CreateBiDirectionalAssociation(){
			List<Information> informations = createCompleteBiDirectionalConstructorTrace("333");
			
			strategy.generate(informations);
			
			assertBiDirectionalAssociationCreated("Caller333 Target333");
		}
		@Test
		public void CreateTwoBiDirectionalAssociations() {
			List<Information> informations = createCompleteBiDirectionalConstructorTrace("123");
			informations.addAll(createCompleteBiDirectionalConstructorTrace("111"));
			
			strategy.generate(informations);
			
			assertBiDirectionalAssociationCreated("Caller123 Target123");
			assertBiDirectionalAssociationCreated("Caller111 Target111");
		}
		@Test
		public void DoesntRepeatBiDirectionalAssociations() {
			List<Information> informations = createCompleteBiDirectionalConstructorTrace("123");
			informations.addAll(createCompleteBiDirectionalConstructorTrace("123"));
			
			strategy.generate(informations);
			
			assertBiDirectionalAssociationCreated("Caller123 Target123");
			assertNumberOfCreatedBiDirectionalAssociations(1);
		}
		
		@Test
		public void DoesntCreateBiDirectionalAssociationForMethodCalls() {
			List<Information> informations = createCompleteBiDirectionalMethodTrace("123");
			
			strategy.generate(informations);
			
			assertNumberOfCreatedBiDirectionalAssociations(0);
		}
	}
	public static class UniBiDirectionalAssociationTests extends ClassDiagramUtilities{
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
		public void createUnidirectionalAssociation(String caller, String target){
			createdUnidirectionalAssociations.add(caller+" "+target);
		}
		@Override
		public void createBiDirectionalAssociation(String class1, String class2){
			createdBiDirectionalAssociations.add(class1+" "+class2);
		}
		
		@Before
		public void setup(){
			strategy = new ClassDiagram(this);
			createdUnidirectionalAssociations = new LinkedList<String>();
			createdBiDirectionalAssociations = new LinkedList<String>();
		}
		
		@Test
		public void BiDirectional_DoesntCreate_Unidirectional(){
			List<Information> informations = createCompleteBiDirectionalConstructorTrace("333");
			
			strategy.generate(informations);
			
			assertNumberOfCreatedUnidirectionals(0);
		}
		@Test
		public void Unidirectional_DoesntCreate_BiDirectional(){
			List<Information> informations = createCompleteConstructorTrace("123");
			
			strategy.generate(informations);
			
			assertNumberOfCreatedBiDirectionals(0);
		}
	}
	public static class CreateDependency extends ClassDiagramUtilities{
		private DiagramStrategy strategy;
		private List<String> createdDependencies;
		
		private void assertDependencyCreated(String dependencyName) {
			assertTrue(createdDependencies.contains(dependencyName));
		}
		private void assertNumberOfCreatedDependencies(int number) {
			assertEquals(number, createdDependencies.size());	
		}
		
		@Override
		public void createDependency(String caller, String target){
			createdDependencies.add(caller+" "+target);
		}
		
		@Before
		public void setup(){
			strategy = new ClassDiagram(this);
			createdDependencies = new LinkedList<String>();
		}
		@Test
		public void CreateOneDependency() {
			List<Information> informations = createCompleteMethodTrace("123");
			
			strategy.generate(informations);
			
			assertDependencyCreated("Caller123 Target123");
		}
		@Test
		public void CreateTwoDependencies() {
			List<Information> informations = createCompleteMethodTrace("123");
			informations.addAll(createCompleteMethodTrace("111"));
			
			strategy.generate(informations);
			
			assertDependencyCreated("Caller123 Target123");
			assertDependencyCreated("Caller111 Target111");
		}
		@Test
		public void DoesntRepeatDependencies() {
			List<Information> informations = createCompleteMethodTrace("123");
			informations.addAll(createCompleteMethodTrace("123"));
			
			strategy.generate(informations);
			
			assertDependencyCreated("Caller123 Target123");
			assertNumberOfCreatedDependencies(1);
		}
	}
	public static class AssociationDependenciyTests extends ClassDiagramUtilities{
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
		public void createUnidirectionalAssociation(String caller, String target){
			createdUnidirectionalAssociations.add(caller+" "+target);
		}
		@Override
		public void createBiDirectionalAssociation(String class1, String class2){
			createdBiDirectionalAssociations.add(class1+" "+class2);
		}
		@Override
		public void createDependency(String caller, String target){
			createdDependencies.add(caller+" "+target);
		}
		
		@Before
		public void setup(){
			strategy = new ClassDiagram(this);
			createdUnidirectionalAssociations = new LinkedList<String>();
			createdBiDirectionalAssociations = new LinkedList<String>();
			createdDependencies = new LinkedList<String>();
		}
		@Test
		public void DoesntCreateUnidirectionalForMethodCalls() {
			List<Information> informations = createCompleteMethodTrace("123");
			
			strategy.generate(informations);
			
			assertNumberOfCreatedUnidirectionals(0);
		}
		@Test
		public void DoesntCreateBiDirectionalForMethodCalls() {
			List<Information> informations = createCompleteMethodTrace("123");
			
			strategy.generate(informations);
			
			assertNumberOfCreatedBiDirectionals(0);
		}
		@Test
		public void DoesntCreatedDependencyForConstructorCalls() {
			List<Information> informations = createCompleteConstructorTrace("123");
			
			strategy.generate(informations);
			
			assertNumberOfCreatedDependencies(0);
		}
	}
}
