package reversej;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import reversej.repository.RepositoryInMemory;
import reversej.tracer.RepositoryRecorder;
import reversej.tracer.TracerController;
import reversej.tracer.TracerImmunity;

@RunWith(Enclosed.class)
public class TracerTest {
	static final String iInterface = "IInterface";
	static final String iClass = "IClass";
	static final String iModifiers = "IModifiers";
	static final String iMethod = "IMethod";
	static final String iParameters = "IParameters";
	static final String iReturn = "IReturn";
	static final String iSubReturn = "ISubReturn";
	static final String iSuperReturn = "ISuperReturn";
	static final String iThrow = "IThrow";
	static final String iHandler = "IHandler";
	static final String separator = " : ";
	static final String classPrefix = "reversej.TracerTest.";
	public static class SetupTracer implements TracerImmunity{
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		@Test
		public void determineStorage() throws Exception{
			RepositoryInMemory exp1 = new RepositoryInMemory();
			RepositoryInMemory exp2 = new RepositoryInMemory();
			TracerController.determineStorage(exp1);
			RepositoryRecorder act1 = TracerController.getStorage();
			TracerController.determineStorage(exp2);
			RepositoryRecorder act2 = TracerController.getStorage();
			assertEquals(exp1,act1);
			assertEquals(exp2,act2);
		}
		@Test
		public void start_SetsUpStorage() throws Exception{
			RepositoryInMemory expected = new RepositoryInMemory();
			TracerController.start(expected);
			assertEquals(expected,TracerController.getStorage());
		}
		@Test
		public void isRunning() throws Exception{
			TracerController.start(new RepositoryInMemory());
			assertTrue(TracerController.isRunning());
		}
		@Test
		public void isNotRunning() throws Exception{
			assertFalse(TracerController.isRunning());
			TracerController.start(new RepositoryInMemory());
			TracerController.stop();
			assertFalse(TracerController.isRunning());
		}
		@Test
		public void afterStart_Records(){
			RepositoryInMemory l = new RepositoryInMemory();
			TracerController.start(l);
			new Actor();
			List<String> actual = l.describeAll();
			assertFalse(actual.isEmpty());
		}
		@Test
		public void beforeStart_DoesntRecord() throws Exception{
			RepositoryInMemory l = new RepositoryInMemory();
			TracerController.determineStorage(l);
			new Actor();
			List<String> actual = l.describeAll();
			assertTrue(actual.isEmpty());
		}
		@Test
		public void afterStop_DoesntRecord() throws Exception{
			RepositoryInMemory l = new RepositoryInMemory();
			TracerController.start(l);
			TracerController.stop();
			new Actor();
			List<String> actual = l.describeAll();
			assertTrue(actual.isEmpty());
		}
	}
	public static class StoringStructure implements TracerImmunity{
		private RepositoryRecorder repository;
		Actor actor;
		@Before
		public void setup(){
			repository = new RepositoryInMemory();
			actor = new Actor();			
			TracerController.start(repository);	
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		@Test
		public void countLinesInstancePublicMethod(){
			actor.playInstancePublic();
			int actualLines = repository.describeAll().size();
			assertEquals(5, actualLines);
		}
		@Test
		public void countLinesInstancePrivateMethod(){
			actor.playInstancePrivate();
			int actualLines = repository.describeAll().size();
			assertEquals(10, actualLines);
		}
		@Test
		public void countLinesStaticPublicMethod(){
			Actor.playStaticPublic();
			int actualLines = repository.describeAll().size();
			assertEquals(5, actualLines);
		}
		@Test
		public void countLinesStaticPrivateMethod(){
			Actor.playStaticPrivate();
			int actualLines = repository.describeAll().size();
			assertEquals(10, actualLines);
		}
		@Test
		public void countLinesPublicConstructor(){
			new Actor();
			int actualLines = repository.describeAll().size();
			assertEquals(5, actualLines);
		}
		@Test
		public void countLinesProtectedConstructor(){
			new Actor("");
			int actualLines = repository.describeAll().size();
			assertEquals(5, actualLines);
		}
		@Test
		public void countLinesPrivateConstructor(){
			new Actor(1.5);
			int actualLines = repository.describeAll().size();
			assertEquals(9, actualLines);
		}
		@Test
		public void countLinesDefaultConstructor(){
			new Actor('a');
			int actualLines = repository.describeAll().size();
			assertEquals(5, actualLines);
		}
		@Test
		public void countLinesInterface(){
			RepositoryInMemory repository = new RepositoryInMemory();
			InterfaceActor iActor = new Actor('a');
			TracerController.start(repository);
			iActor.playInstancePublic();
			int actualLines = repository.describeAll().size();
			assertEquals(6, actualLines);
		}
		@Test
		public void countLinesExceptionThrowAndHandle() throws Exception{
			RepositoryInMemory repository = new RepositoryInMemory();
			Actor mActor = new Actor('a');
			TracerController.start(repository);
			try {
				mActor.playInstancePublicThrowException();
			} catch (Exception e)
			{}
			List<String> actual = repository.describeAll();
			assertEquals(6, actual.size());			
		}
	}
	public static class ConstructorPublic implements TracerImmunity{
		private RepositoryInMemory repository;
		private String expected;
		@Before
		public void setUp(){
			repository = new RepositoryInMemory();
			TracerController.start(repository);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		
		@Test
		public void target() throws Exception{
			new Actor();
			expected =iClass + separator + classPrefix + "Actor";
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void method() throws Exception{
			new Actor();
			expected =iMethod + separator + "<init>";
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			new Actor();
			expected =iParameters + separator ;
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			new Actor(true);
			expected =iParameters + separator + "boolean bPublic";
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			new Actor(1, "a");
			expected =iParameters + separator + "int i, java.lang.String s";
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void returnInstanceType() throws Exception{
			new Actor();
			expected =iReturn + separator + "" + classPrefix + "Actor";
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
	}
	public static class ConstructorPrivate implements TracerImmunity{
		private RepositoryInMemory repository;
		private String expected;
		@Before
		public void setup(){
			repository = new RepositoryInMemory();
			expected = null;
			TracerController.start(repository);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		
		@Test
		public void target() throws Exception{
			new Actor(1.5);
			expected =iClass + separator + classPrefix + "Actor";
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void method() throws Exception{
			new Actor(1.5);
			expected =iMethod + separator + "<init>";
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			new Actor(1.5);
			expected =iParameters + separator + "double dPrivate";
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void returnInstanceType() throws Exception{
			new Actor(1.5);
			expected =iReturn + separator + "" + classPrefix + "Actor";
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
	}
	public static class superSubConstructor implements TracerImmunity{
		private RepositoryInMemory repository;
		private String expected;
		@Before
		public void setup(){
			repository = new RepositoryInMemory();
			expected = null;
			TracerController.start(repository);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		@Test
		public void returnSuperInSuperReturn(){
			new MaleActor(1.5);
			expected =iSuperReturn + separator + "" + classPrefix + "Actor";
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void returnSubInSubReturn(){
			new MaleActor(1.5);
			expected =iSubReturn + separator + "" + classPrefix + "MaleActor";
			List<String> actual = repository.describeAll();
			assertInList(actual,expected);
		}
	}
	public static class InstanceMethodPublic implements TracerImmunity{
		private RepositoryInMemory log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			log = new RepositoryInMemory();
			actor = new Actor();
			TracerController.start(log);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		
		@Test
		public void target() throws Exception{
			actor.playInstancePublic();
			expected =iClass + separator + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void method() throws Exception{
			actor.playInstancePublic();
			expected =iMethod + separator + "playInstancePublic";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playInstancePublic();
			expected =iParameters + separator;
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playInstancePublic(true);
			expected =iParameters + separator + "boolean b";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			actor.playInstancePublic(1, "a");
			expected =iParameters + separator + "int i, java.lang.String s";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void returnType() throws Exception{
			actor.playInstancePublic(1, "a");
			expected =iReturn + separator + "java.lang.Boolean";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
	}
	public static class InstanceMethodPrivate implements TracerImmunity{
		private RepositoryInMemory log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			log = new RepositoryInMemory();
			actor = new Actor();
			TracerController.start(log);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		
		@Test
		public void target() throws Exception{
			actor.playInstancePrivate();
			expected =iClass + separator + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void method() throws Exception{
			actor.playInstancePrivate();
			expected =iMethod + separator + "playInstancePrivate";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playInstancePrivate();
			expected =iParameters + separator;
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playInstancePrivate(true);
			expected =iParameters + separator + "boolean b";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			actor.playInstancePrivate(1, "a");
			expected =iParameters + separator + "int i, java.lang.String s";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void returnType() throws Exception{
			actor.playInstancePrivate(1, "a");
			expected =iReturn + separator + "java.lang.Boolean";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
	}
	public static class StaticMethodPublic implements TracerImmunity{
		private RepositoryInMemory log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			log = new RepositoryInMemory();
			actor = new Actor();
			TracerController.start(log);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		
		@Test
		public void target() throws Exception{
			Actor.playStaticPublic();
			expected =iClass + separator + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void method() throws Exception{
			Actor.playStaticPublic();
			expected =iMethod + separator + "playStaticPublic";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			Actor.playStaticPublic();
			expected =iParameters + separator;
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			Actor.playStaticPublic(true);
			expected =iParameters + separator + "boolean b";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			Actor.playStaticPublic(1, "a");
			expected =iParameters + separator + "int i, java.lang.String s";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void returnType() throws Exception{
			Actor.playStaticPublic(1, "a");
			expected =iReturn + separator + "java.lang.Boolean";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
	}
	public static class StaticMethodPrivate implements TracerImmunity{
		private RepositoryInMemory log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			log = new RepositoryInMemory();
			TracerController.start(log);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		
		@Test
		public void target() throws Exception{
			Actor.playStaticPrivate();
			expected =iClass + separator + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void method() throws Exception{
			Actor.playStaticPrivate();
			expected =iMethod + separator + "playStaticPrivate";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			Actor.playStaticPrivate();
			expected =iParameters + separator;
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			Actor.playStaticPrivate(true);
			expected =iParameters + separator + "boolean b";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected =iParameters + separator + "int i, java.lang.String s";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void returnType() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected =iReturn + separator + "java.lang.Boolean";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
	}
	public static class InterfaceMethodPublic implements TracerImmunity{
		private RepositoryInMemory log;
		InterfaceActor iActor;
		String expected;
		@Before
		public void setup(){
			log = new RepositoryInMemory();
			iActor = new Actor();
			TracerController.start(log);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		
		@Test
		public void declaredInterface() throws Exception{
			iActor.playInstancePublic();
			expected =iInterface + separator + classPrefix + "InterfaceActor";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void target() throws Exception{
			iActor.playInstancePublic();
			expected =iClass + separator + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void method() throws Exception{
			iActor.playInstancePublic();
			expected =iMethod + separator + "playInstancePublic";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			iActor.playInstancePublic();
			expected =iParameters + separator;
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			iActor.playInstancePublic(true);
			expected =iParameters + separator + "boolean b";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			iActor.playInstancePublic(1, "a");
			expected =iParameters + separator + "int i, java.lang.String s";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void returnType() throws Exception{
			iActor.playInstancePublic(1, "a");
			expected =iReturn + separator + "java.lang.Boolean";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
	}
	public static class AccessControlModifiers implements TracerImmunity{
		private RepositoryInMemory log;
		private Actor mActor = new Actor();
		private String expected;
		@Before
		public void setUp(){
			log = new RepositoryInMemory();
			TracerController.start(log);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		@Test
		public void modifierConstructorDefault() throws Exception{
			new Actor('a');
			expected =iModifiers + separator + "package level";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void modifierConstructorPublic() throws Exception{
			new Actor(true);
			expected =iModifiers + separator + "public";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void modifierConstructorPrivate() throws Exception{
			new Actor(1.5);
			expected =iModifiers + separator + "private";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void modifierConstructorProtected() throws Exception{
			new Actor("");
			expected =iModifiers + separator + "protected";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void modifierMethodDefault() throws Exception{
			mActor.playInstanceDefault();
			expected =iModifiers + separator + "package level";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void modifierMethodPublic() throws Exception{
			mActor.playInstancePublic();
			expected =iModifiers + separator + "public";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void modifierMethodPrivate() throws Exception{
			mActor.playInstancePrivate();
			expected =iModifiers + separator + "private";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void modifierMethodProtected() throws Exception{
			mActor.playInstanceProtected();
			expected =iModifiers + separator + "protected";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
	}
	public static class NonAccessModifiers implements TracerImmunity{
		private RepositoryInMemory log;
		private Actor mActor = new Actor();
		private String expected;
		@Before
		public void setUp(){
			log = new RepositoryInMemory();
			TracerController.start(log);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		
		@Test
		public void modifierMethodPublicFinal() throws Exception{
			mActor.playInstancePublicFinal();
			expected =iModifiers + separator + "public final";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void modifierMethodPublicSynchronized() throws Exception{
			mActor.playInstancePublicSynchronized();
			expected =iModifiers + separator + "public synchronized";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void modifierMethodPublicNative() throws Exception{
			mActor.playInstancePublicStrictFP();;
			expected =iModifiers + separator + "public strictfp";
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
	}
	public static class ExceptionThrowAndHandle implements TracerImmunity{
		private RepositoryInMemory log;
		private Actor mActor = new Actor();
		private String expected;
		@Before
		public void setUp(){
			log = new RepositoryInMemory();
			TracerController.start(log);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		@Test
		public void throwException() throws Exception{
			expected =iThrow + separator + "java.lang.ArithmeticException";
			try {
				mActor.playInstancePublicThrowException();
			}
			catch (Exception e) 
			{}
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
		@Test
		public void HandlerException() throws Exception{
			expected =iHandler + separator + classPrefix + "ExceptionThrowAndHandle";
			try {
				mActor.playInstancePublicThrowException();
			}
			catch (Exception e) 
			{}
			List<String> actual = log.describeAll();
			assertInList(actual,expected);
		}
	}
	public static class javaImmunity implements TracerImmunity{
		private RepositoryInMemory repository;
		private String expected;
		@Before
		public void setup(){
			repository = new RepositoryInMemory();
			expected = null;
			TracerController.start(repository);
		}
		@After
		public void cleanUp(){
			TracerController.stop();
		}
		@Test
		public void stringBuilderHasNoReturn(){
			double salario = 2000.50;
			String building = "first part" + salario;
			int actual = repository.describeAll().size();
			assertEquals(0,actual);
		}
	}
	static interface InterfaceActor{
		public abstract void playInstancePublic();

		public abstract boolean playInstancePublic(boolean b);

		public abstract boolean playInstancePublic(int i, String s);
	}
	static class Actor implements InterfaceActor{
		Actor(char cDefault)
		{}
		public Actor()
		{}
		public Actor(boolean bPublic)
		{}
		public Actor(int i, String s)
		{}
		private Actor(double dPrivate)
		{}
		protected Actor(String sProtected)
		{}
		
		private static void playStaticPrivate()
		{}
		private static boolean playStaticPrivate(boolean b)
		{return b;}
		private static boolean playStaticPrivate(int i, String s)
		{return true;}
		private void playInstancePrivate()
		{}
		private boolean playInstancePrivate(boolean b)
		{return b;}
		private boolean playInstancePrivate(int i, String s)
		{return true;}
		
		public static void playStaticPublic()
		{}
		public static boolean playStaticPublic(boolean b)
		{return b;}
		public static boolean playStaticPublic(int i, String s)
		{return true;}
		@Override
		public void playInstancePublic()
		{}
		@Override
		public boolean playInstancePublic(boolean b)
		{return b;}
		@Override
		public boolean playInstancePublic(int i, String s)
		{return true;}		
		public final void playInstancePublicFinal()
		{}
		public synchronized void playInstancePublicSynchronized()
		{}
		public strictfp void playInstancePublicStrictFP()
		{}
		public double playInstancePublicThrowException () throws Exception
		{ return 5/0;}
		
		protected void playInstanceProtected()
		{}
		void playInstanceDefault()
		{}
	}
	static class MaleActor extends Actor{
		MaleActor(char subCDefault)
		{super(subCDefault);}
		public MaleActor()
		{super();}
		public MaleActor(boolean subBPublic)
		{super(subBPublic);}
		public MaleActor(int i, String s)
		{super(i,s);}
		private MaleActor(double subdPrivate)
		{super(subdPrivate);}
		protected MaleActor(String subSProtected)
		{super(subSProtected);}
	}
	static void assertInList(List<String> list, String name){
		boolean found = false;
		for (String item : list) {
			found = item.equals(name)? true:found;
		}
		if(!found){
			String message = "not found \""+name+"\"";
			System.out.println("---------------------");
			System.out.println(message);
			for (String string : list) {
				System.out.println(string);
			}
			System.out.println("---------------------end");
			fail(message);
		}
			
	}
}