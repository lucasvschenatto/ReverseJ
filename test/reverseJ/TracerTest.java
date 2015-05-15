package reverseJ;

import java.util.List;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TracerTest extends org.junit.Assert{
	static final String classPrefix = "reverseJ.TracerTest.";
	public static class SetupTracer implements TracerImmunity{
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		@Test
		public void determineStorage() throws Exception{
			InformationStorage exp1 = new InformationStorage();
			InformationStorage exp2 = new InformationStorage();
			Tracer.determineStorage(exp1);
			RecorderStorage act1 = Tracer.getStorage();
			Tracer.determineStorage(exp2);
			RecorderStorage act2 = Tracer.getStorage();
			assertEquals(exp1,act1);
			assertEquals(exp2,act2);
		}
		@Test
		public void start_SetsUpStorage() throws Exception{
			InformationStorage expected = new InformationStorage();
			Tracer.start(expected);
			assertEquals(expected,Tracer.getStorage());
		}
		@Test
		public void isRunning() throws Exception{
			Tracer.start(new InformationStorage());
			assertTrue(Tracer.isRunning());
		}
		@Test
		public void isNotRunning() throws Exception{
			assertFalse(Tracer.isRunning());
			Tracer.start(new InformationStorage());
			Tracer.stop();
			assertFalse(Tracer.isRunning());
		}
		@Test
		public void afterStart_Records(){
			InformationStorage l = new InformationStorage();
			Tracer.start(l);
			new Actor();
			List<String> actual = l.describeAll();
			assertFalse(actual.isEmpty());
		}
		@Test
		public void beforeStart_DoesntRecord() throws Exception{
			InformationStorage l = new InformationStorage();
			Tracer.determineStorage(l);
			new Actor();
			List<String> actual = l.describeAll();
			assertTrue(actual.isEmpty());
		}
		@Test
		public void afterStop_DoesntRecord() throws Exception{
			InformationStorage l = new InformationStorage();
			Tracer.start(l);
			Tracer.stop();
			new Actor();
			List<String> actual = l.describeAll();
			assertTrue(actual.isEmpty());
		}
	}
	public static class StoringStructure implements TracerImmunity{
		private RecorderStorage storate;
		Actor actor;
		@Before
		public void setup(){
			storate = new InformationStorage();
			actor = new Actor();			
			Tracer.start(storate);	
		}
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		@Test
		public void countLinesInstancePublicMethod(){
			actor.playInstancePublic();
			int actualLines = storate.describeAll().size();
			assertEquals(6, actualLines);
		}
		@Test
		public void countLinesInstancePrivateMethod(){
			actor.playInstancePrivate();
			int actualLines = storate.describeAll().size();
			assertEquals(10, actualLines);
		}
		@Test
		public void countLinesStaticPublicMethod(){
			Actor.playStaticPublic();
			int actualLines = storate.describeAll().size();
			assertEquals(6, actualLines);
		}
		@Test
		public void countLinesStaticPrivateMethod(){
			Actor.playStaticPrivate();
			int actualLines = storate.describeAll().size();
			assertEquals(10, actualLines);
		}
		@Test
		public void countLinesPublicConstructor(){
			new Actor();
			int actualLines = storate.describeAll().size();
			assertEquals(6, actualLines);
		}
		@Test
		public void countLinesProtectedConstructor(){
			new Actor("");
			int actualLines = storate.describeAll().size();
			assertEquals(6, actualLines);
		}
		@Test
		public void countLinesPrivateConstructor(){
			new Actor(1.5);
			int actualLines = storate.describeAll().size();
			assertEquals(9, actualLines);
		}
		@Test
		public void countLinesDefaultConstructor(){
			new Actor('a');
			int actualLines = storate.describeAll().size();
			assertEquals(6, actualLines);
		}
		@Test
		public void countLinesInterface(){
			InformationStorage localLog = new InformationStorage();
			InterfaceActor iActor = new Actor('a');
			Tracer.start(localLog);
			iActor.playInstancePublic();
			int actualLines = localLog.describeAll().size();
			assertEquals(7, actualLines);
		}
		@Test
		public void countLinesExceptionThrowAndHandle() throws Exception{
			InformationStorage localLog = new InformationStorage();
			Actor mActor = new Actor('a');
			Tracer.start(localLog);
			try {
				mActor.playInstancePublicThrowException();
			} catch (Exception e)
			{}
			List<String> actual = localLog.describeAll();
			assertEquals(7, actual.size());
			
		}
	}
	public static class ConstructorPublic implements TracerImmunity{
		private InformationStorage log;
		private String expected;
		@Before
		public void setUp(){
			log = new InformationStorage();
			Tracer.start(log);
		}
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		@Test
		public void caller() throws Exception{
			new Actor();
			expected ="ICaller : " + classPrefix + "ConstructorPublic";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void target() throws Exception{
			new Actor();
			expected ="ITarget : " + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void method() throws Exception{
			new Actor();
			expected ="IMethod : <init>";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_NoParameter() throws Exception{
			new Actor();
			expected ="ISignature : ()";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_OneParameter() throws Exception{
			new Actor(true);
			expected ="ISignature : (boolean bPublic)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			new Actor(1, "a");
			expected ="ISignature : (int i, java.lang.String s)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void returnInstanceType() throws Exception{
			new Actor();
			expected ="IReturn : " + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
	}
	public static class ConstructorPrivate implements TracerImmunity{
		private InformationStorage storage;
		private String expected;
		@Before
		public void setup(){
			storage = new InformationStorage();
			expected = null;
			Tracer.start(storage);
		}
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		@Test
		public void caller() throws Exception{
			new Actor(1.5);
			expected ="ICaller : " + classPrefix + "ConstructorPrivate";
			List<String> actual = storage.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void target() throws Exception{
			new Actor(1.5);
			expected ="ITarget : " + classPrefix + "Actor";
			List<String> actual = storage.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void method() throws Exception{
			new Actor(1.5);
			expected ="IMethod : <init>";
			List<String> actual = storage.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_OneParameter() throws Exception{
			new Actor(1.5);
			expected ="ISignature : (double dPrivate)";
			List<String> actual = storage.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void returnInstanceType() throws Exception{
			new Actor(1.5);
			expected ="IReturn : " + classPrefix + "Actor";
			List<String> actual = storage.describeAll();
			assertTrue(actual.contains(expected));
		}
	}
	public static class InstanceMethodPublic implements TracerImmunity{
		private InformationStorage log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			log = new InformationStorage();
			actor = new Actor();
			Tracer.start(log);
		}
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		@Test
		public void caller() throws Exception{
			actor.playInstancePublic();
			expected ="ICaller : " + classPrefix + "InstanceMethodPublic";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void target() throws Exception{
			actor.playInstancePublic();
			expected ="ITarget : " + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void method() throws Exception{
			actor.playInstancePublic();
			expected ="IMethod : playInstancePublic";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playInstancePublic();
			expected ="ISignature : ()";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playInstancePublic(true);
			expected ="ISignature : (boolean b)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			actor.playInstancePublic(1, "a");
			expected ="ISignature : (int i, java.lang.String s)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void returnType() throws Exception{
			actor.playInstancePublic(1, "a");
			expected ="IReturn : java.lang.Boolean";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
	}
	public static class InstanceMethodPrivate implements TracerImmunity{
		private InformationStorage log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			log = new InformationStorage();
			actor = new Actor();
			Tracer.start(log);
		}
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		@Test
		public void caller() throws Exception{
			actor.playInstancePrivate();
			expected ="ICaller : " + classPrefix + "InstanceMethodPrivate";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void target() throws Exception{
			actor.playInstancePrivate();
			expected ="ITarget : " + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void method() throws Exception{
			actor.playInstancePrivate();
			expected ="IMethod : playInstancePrivate";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playInstancePrivate();
			expected ="ISignature : ()";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playInstancePrivate(true);
			expected ="ISignature : (boolean b)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			actor.playInstancePrivate(1, "a");
			expected ="ISignature : (int i, java.lang.String s)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void returnType() throws Exception{
			actor.playInstancePrivate(1, "a");
			expected ="IReturn : java.lang.Boolean";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
	}
	public static class StaticMethodPublic implements TracerImmunity{
		private InformationStorage log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			log = new InformationStorage();
			actor = new Actor();
			Tracer.start(log);
		}
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		@Test
		public void caller() throws Exception{
			Actor.playStaticPublic();
			expected ="ICaller : " + classPrefix + "StaticMethodPublic";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void target() throws Exception{
			Actor.playStaticPublic();
			expected ="ITarget : " + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void method() throws Exception{
			Actor.playStaticPublic();
			expected ="IMethod : playStaticPublic";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_NoParameter() throws Exception{
			Actor.playStaticPublic();
			expected ="ISignature : ()";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_OneParameter() throws Exception{
			Actor.playStaticPublic(true);
			expected ="ISignature : (boolean b)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			Actor.playStaticPublic(1, "a");
			expected ="ISignature : (int i, java.lang.String s)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void returnType() throws Exception{
			Actor.playStaticPublic(1, "a");
			expected ="IReturn : java.lang.Boolean";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
	}
	public static class StaticMethodPrivate implements TracerImmunity{
		private InformationStorage log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			log = new InformationStorage();
			Tracer.start(log);
		}
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		@Test
		public void caller() throws Exception{
			Actor.playStaticPrivate();
			expected ="ICaller : " + classPrefix + "StaticMethodPrivate";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void target() throws Exception{
			Actor.playStaticPrivate();
			expected ="ITarget : " + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void method() throws Exception{
			Actor.playStaticPrivate();
			expected ="IMethod : playStaticPrivate";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_NoParameter() throws Exception{
			Actor.playStaticPrivate();
			expected ="ISignature : ()";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_OneParameter() throws Exception{
			Actor.playStaticPrivate(true);
			expected ="ISignature : (boolean b)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected ="ISignature : (int i, java.lang.String s)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void returnType() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected ="IReturn : java.lang.Boolean";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
	}
	public static class InterfaceMethodPublic implements TracerImmunity{
		private InformationStorage log;
		InterfaceActor iActor;
		String expected;
		@Before
		public void setup(){
			log = new InformationStorage();
			iActor = new Actor();
			Tracer.start(log);
		}
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		@Test
		public void caller() throws Exception{
			iActor.playInstancePublic();
			expected ="ICaller : " + classPrefix + "InterfaceMethodPublic";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void declaredInterface() throws Exception{
			iActor.playInstancePublic();
			expected ="IInterface : " + classPrefix + "InterfaceActor";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void target() throws Exception{
			iActor.playInstancePublic();
			expected ="ITarget : " + classPrefix + "Actor";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void method() throws Exception{
			iActor.playInstancePublic();
			expected ="IMethod : playInstancePublic";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_NoParameter() throws Exception{
			iActor.playInstancePublic();
			expected ="ISignature : ()";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_OneParameter() throws Exception{
			iActor.playInstancePublic(true);
			expected ="ISignature : (boolean b)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			iActor.playInstancePublic(1, "a");
			expected ="ISignature : (int i, java.lang.String s)";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void returnType() throws Exception{
			iActor.playInstancePublic(1, "a");
			expected ="IReturn : java.lang.Boolean";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
	}
	public static class AccessControlModifiers implements TracerImmunity{
		private InformationStorage log;
		private Actor mActor = new Actor();
		private String expected;
		@Before
		public void setUp(){
			log = new InformationStorage();
			Tracer.start(log);
		}
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		@Test
		public void modifierConstructorDefault() throws Exception{
			new Actor('a');
			expected ="IModifiers : ";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void modifierConstructorPublic() throws Exception{
			new Actor(true);
			expected ="IModifiers : public";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void modifierConstructorPrivate() throws Exception{
			new Actor(1.5);
			expected ="IModifiers : private";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void modifierConstructorProtected() throws Exception{
			new Actor("");
			expected ="IModifiers : protected";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void modifierMethodDefault() throws Exception{
			mActor.playInstanceDefault();
			expected ="IModifiers : ";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void modifierMethodPublic() throws Exception{
			mActor.playInstancePublic();
			expected ="IModifiers : public";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void modifierMethodPrivate() throws Exception{
			mActor.playInstancePrivate();
			expected ="IModifiers : private";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void modifierMethodProtected() throws Exception{
			mActor.playInstanceProtected();
			expected ="IModifiers : protected";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
	}
	public static class NonAccessModifiers implements TracerImmunity{
		private InformationStorage log;
		private Actor mActor = new Actor();
		private String expected;
		@Before
		public void setUp(){
			log = new InformationStorage();
			Tracer.start(log);
		}
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		
		@Test
		public void modifierMethodPublicFinal() throws Exception{
			mActor.playInstancePublicFinal();
			expected ="IModifiers : public final";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void modifierMethodPublicSynchronized() throws Exception{
			mActor.playInstancePublicSynchronized();
			expected ="IModifiers : public synchronized";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void modifierMethodPublicNative() throws Exception{
			mActor.playInstancePublicStrictFP();;
			expected ="IModifiers : public strictfp";
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
	}
	public static class ExceptionThrowAndHandle implements TracerImmunity{
		private InformationStorage log;
		private Actor mActor = new Actor();
		private String expected;
		@Before
		public void setUp(){
			log = new InformationStorage();
			Tracer.start(log);
		}
		@After
		public void cleanUp(){
			Tracer.stop();
		}
		@Test
		public void throwException() throws Exception{
			expected ="IThrow : java.lang.ArithmeticException";
			try {
				mActor.playInstancePublicThrowException();
			}
			catch (Exception e) 
			{}
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
		}
		@Test
		public void HandlerException() throws Exception{
			expected ="IHandler : " + classPrefix + "ExceptionThrowAndHandle";
			try {
				mActor.playInstancePublicThrowException();
			}
			catch (Exception e) 
			{}
			List<String> actual = log.describeAll();
			assertTrue(actual.contains(expected));
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
}