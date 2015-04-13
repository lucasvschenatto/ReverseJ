package reverseJ;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class RecorderTest extends org.junit.Assert{
	public static class SetupRecorder implements RecorderStorageTest{
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void determineStorage() throws Exception{
			Log exp1 = new Log();
			Log exp2 = new Log();
			Recorder.determineStorage(exp1);
			RecorderStorage act1 = Recorder.getStorage();
			Recorder.determineStorage(exp2);
			RecorderStorage act2 = Recorder.getStorage();
			assertEquals(exp1,act1);
			assertEquals(exp2,act2);
		}
		@Test
		public void start_SetsUpStorage() throws Exception{
			Log expected = new Log();
			Recorder.start(expected);
			assertEquals(expected,Recorder.getStorage());
		}
		@Test
		public void isRunning() throws Exception{
			Recorder.start(new Log());
			assertTrue(Recorder.isRunning());
		}
		@Test
		public void isNotRunning() throws Exception{
			assertFalse(Recorder.isRunning());
			Recorder.start(new Log());
			Recorder.stop();
			assertFalse(Recorder.isRunning());
		}
		@Test
		public void afterStart_Records(){
			Log l = new Log();
			Recorder.start(l);
			new Actor();
			String[] actual = l.describeAll();
			assertNotEquals(Log.emptyLogInfo,actual[0]);
		}
		@Test
		public void beforeStart_DoesntRecord() throws Exception{
			Log l = new Log();
			Recorder.determineStorage(l);
			new Actor();
			String[] actual = l.describeAll();
			assertEquals(Log.emptyLogInfo,actual[0]);
		}
		@Test
		public void afterStop_DoesntRecord() throws Exception{
			Log l = new Log();
			Recorder.start(l);
			Recorder.stop();
			new Actor();
			String[] actual = l.describeAll();
			assertEquals(Log.emptyLogInfo,actual[0]);
		}
	}
	public static class StoringStructure implements RecorderStorageTest{
		private Log log;
		Actor actor;
		int totalLines;
		@Before
		public void setup(){
			log = new Log();
			totalLines = InfoOrder.values().length;
			actor = new Actor();			
			Recorder.start(log);	
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void print() throws Exception{
			InterfaceActor iActor = new Actor();
			iActor.playInstancePublic(1, "a");
			String[] actual = log.describeAll();
			for(String line: actual){
				System.out.println(line);
			}
		}
		@Test
		public void setStructureOrder(){			
			RecorderInfo[] expected = InfoOrder.values();
			Recorder.determineOrder(expected);
			RecorderInfo[] actual = Recorder.getOrder();
			assertArrayEquals(expected, actual);
		}
		@Test
		public void countLinesInstancePublicMethod(){
			actor.playInstancePublic();
			int actualLines = log.describeAll().length;
			assertEquals(totalLines-1, actualLines);
		}
		@Test
		public void countLinesInstancePrivateMethod(){
			actor.playInstancePrivate();
			int actualLines = log.describeAll().length;
			assertEquals(10, actualLines);
		}
		@Test
		public void countLinesStaticPublicMethod(){
			Actor.playStaticPublic();
			int actualLines = log.describeAll().length;
			assertEquals(totalLines-1, actualLines);
		}
		@Test
		public void countLinesStaticPrivateMethod(){
			Actor.playStaticPrivate();
			int actualLines = log.describeAll().length;
			assertEquals(10, actualLines);
		}
		@Test
		public void countLinesPublicConstructor(){
			new Actor();
			int actualLines = log.describeAll().length;
			assertEquals(totalLines-1, actualLines);
		}
		@Test
		public void countLinesProtectedConstructor(){
			new Actor("");
			int actualLines = log.describeAll().length;
			assertEquals(totalLines-1, actualLines);
		}
		@Test
		public void countLinesPrivateConstructor(){
			new Actor(1.5);
			int actualLines = log.describeAll().length;
			assertEquals(totalLines+2, actualLines);
		}
		@Test
		public void countLinesDefaultConstructor(){
			new Actor('a');
			int actualLines = log.describeAll().length;
			assertEquals(totalLines-1, actualLines);
		}
		@Test
		public void countLinesInterface(){
			Log localLog = new Log();
			InterfaceActor iActor = new Actor('a');
			Recorder.start(localLog);
			iActor.playInstancePublic();
			int actualLines = localLog.describeAll().length;
			assertEquals(totalLines, actualLines);
		}
	}
	public static class ConstructorPublic implements RecorderStorageTest{
		private Log log;
		private String expected;
		@Before
		public void setUp(){
			log = new Log();
			Recorder.start(log);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			new Actor();
			expected ="CALLER : reverseJ.RecorderTest.ConstructorPublic";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void target() throws Exception{
			new Actor();
			expected ="TARGET : reverseJ.RecorderTest.Actor";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void method() throws Exception{
			new Actor();
			expected ="METHOD : <init>";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			new Actor();
			expected ="SIGNATURE : ()";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			new Actor(true);
			expected ="SIGNATURE : (boolean bPublic)";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			new Actor(1, "a");
			expected ="SIGNATURE : (int i, java.lang.String s)";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void returnInstanceType() throws Exception{
			new Actor();
			expected ="RETURN : reverseJ.RecorderTest.Actor";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
	}
	public static class ConstructorPrivate implements RecorderStorageTest{
		private Log log;
		private String expected;
		@Before
		public void setup(){
			log = new Log();
			Recorder.start(log);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			new Actor(1.5);
			expected ="CALLER : reverseJ.RecorderTest.ConstructorPrivate";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<1);
		}
		@Test
		public void target() throws Exception{
			new Actor(1.5);
			expected ="TARGET : reverseJ.RecorderTest.Actor";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<1);
		}
		@Test
		public void method() throws Exception{
			new Actor(1.5);
			expected ="METHOD : <init>";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<1);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			new Actor(1.5);
			expected ="SIGNATURE : (double dPrivate)";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<1);
		}
		@Test
		public void returnInstanceType() throws Exception{
			new Actor(1.5);
			expected ="RETURN : reverseJ.RecorderTest.Actor";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<1);
		}
	}
	public static class InstanceMethodPublic implements RecorderStorageTest{
		private Log log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			log = new Log();
			actor = new Actor();
			Recorder.start(log);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			actor.playInstancePublic();
			expected ="CALLER : reverseJ.RecorderTest.InstanceMethodPublic";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void target() throws Exception{
			actor.playInstancePublic();
			expected ="TARGET : reverseJ.RecorderTest.Actor";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void method() throws Exception{
			actor.playInstancePublic();
			expected ="METHOD : playInstancePublic";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playInstancePublic();
			expected ="SIGNATURE : ()";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playInstancePublic(true);
			expected ="SIGNATURE : (boolean b)";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			actor.playInstancePublic(1, "a");
			expected ="SIGNATURE : (int i, java.lang.String s)";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void returnType() throws Exception{
			actor.playInstancePublic(1, "a");
			expected ="RETURN : java.lang.Boolean";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
	}
	public static class InstanceMethodPrivate implements RecorderStorageTest{
		private Log log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			log = new Log();
			actor = new Actor();
			Recorder.start(log);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			actor.playInstancePrivate();
			expected ="CALLER : reverseJ.RecorderTest.InstanceMethodPrivate";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void target() throws Exception{
			actor.playInstancePrivate();
			expected ="TARGET : reverseJ.RecorderTest.Actor";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void method() throws Exception{
			actor.playInstancePrivate();
			expected ="METHOD : playInstancePrivate";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playInstancePrivate();
			expected ="SIGNATURE : ()";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playInstancePrivate(true);
			expected ="SIGNATURE : (boolean b)";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			actor.playInstancePrivate(1, "a");
			expected ="SIGNATURE : (int i, java.lang.String s)";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void returnType() throws Exception{
			actor.playInstancePrivate(1, "a");
			expected ="RETURN : java.lang.Boolean";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
	}
	public static class StaticMethodPublic implements RecorderStorageTest{
		private Log log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			log = new Log();
			actor = new Actor();
			Recorder.start(log);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			Actor.playStaticPublic();
			expected ="CALLER : reverseJ.RecorderTest.StaticMethodPublic";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void target() throws Exception{
			Actor.playStaticPublic();
			expected ="TARGET : reverseJ.RecorderTest.Actor";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void method() throws Exception{
			Actor.playStaticPublic();
			expected ="METHOD : playStaticPublic";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			Actor.playStaticPublic();
			expected ="SIGNATURE : ()";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			Actor.playStaticPublic(true);
			expected ="SIGNATURE : (boolean b)";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			Actor.playStaticPublic(1, "a");
			expected ="SIGNATURE : (int i, java.lang.String s)";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void returnType() throws Exception{
			Actor.playStaticPublic(1, "a");
			expected ="RETURN : java.lang.Boolean";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
	}
	public static class StaticMethodPrivate implements RecorderStorageTest{
		private Log log;
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			log = new Log();
			Recorder.start(log);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			Actor.playStaticPrivate();
			expected ="CALLER : reverseJ.RecorderTest.StaticMethodPrivate";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void target() throws Exception{
			Actor.playStaticPrivate();
			expected ="TARGET : reverseJ.RecorderTest.Actor";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void method() throws Exception{
			Actor.playStaticPrivate();
			expected ="METHOD : playStaticPrivate";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			Actor.playStaticPrivate();
			expected ="SIGNATURE : ()";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			Actor.playStaticPrivate(true);
			expected ="SIGNATURE : (boolean b)";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected ="SIGNATURE : (int i, java.lang.String s)";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void returnType() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected ="RETURN : java.lang.Boolean";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
	}
	public static class InterfaceMethodPublic implements RecorderStorageTest{
		private Log log;
		InterfaceActor iActor;
		String expected;
		@Before
		public void setup(){
			log = new Log();
			iActor = new Actor();
			Recorder.start(log);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			iActor.playInstancePublic();
			expected ="CALLER : reverseJ.RecorderTest.InterfaceMethodPublic";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<=1);
		}
		@Test
		public void declaredInterface() throws Exception{
			iActor.playInstancePublic();
			expected ="INTERFACE : reverseJ.RecorderTest.InterfaceActor";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<=1);
		}
		@Test
		public void target() throws Exception{
			iActor.playInstancePublic();
			expected ="TARGET : reverseJ.RecorderTest.Actor";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<=1);
		}
		@Test
		public void method() throws Exception{
			iActor.playInstancePublic();
			expected ="METHOD : playInstancePublic";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<=1);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			iActor.playInstancePublic();
			expected ="SIGNATURE : ()";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<=1);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			iActor.playInstancePublic(true);
			expected ="SIGNATURE : (boolean b)";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<=1);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			iActor.playInstancePublic(1, "a");
			expected ="SIGNATURE : (int i, java.lang.String s)";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<=1);
		}
		@Test
		public void returnType() throws Exception{
			iActor.playInstancePublic(1, "a");
			expected ="RETURN : java.lang.Boolean";
			String[] actual = log.describeAll();
			assertInArray(expected, actual);
			assertTrue(InfoOrder.values().length-actual.length<=1);
		}
	}
	public static class AccessControlModifiers implements RecorderStorageTest{
		private Log log;
		private Actor mActor = new Actor();
		private String expected;
		@Before
		public void setUp(){
			log = new Log();
			Recorder.start(log);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void modifierConstructorDefault() throws Exception{
			new Actor('a');
			expected ="MODIFIERS : ";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void modifierConstructorPublic() throws Exception{
			new Actor(true);
			expected ="MODIFIERS : public";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void modifierConstructorPrivate() throws Exception{
			new Actor(1.5);
			expected ="MODIFIERS : private";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void modifierConstructorProtected() throws Exception{
			new Actor("");
			expected ="MODIFIERS : protected";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void modifierMethodDefault() throws Exception{
			mActor.playInstanceDefault();
			expected ="MODIFIERS : ";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void modifierMethodPublic() throws Exception{
			mActor.playInstancePublic();
			expected ="MODIFIERS : public";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void modifierMethodPrivate() throws Exception{
			mActor.playInstancePrivate();
			expected ="MODIFIERS : private";
			String[] actual = log.describeAll();
//			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void modifierMethodProtected() throws Exception{
			mActor.playInstanceProtected();
			expected ="MODIFIERS : protected";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
	}
	public static class NonAccessModifiers implements RecorderStorageTest{
		private Log log;
		private Actor mActor = new Actor();
		private String expected;
		@Before
		public void setUp(){
			log = new Log();
			Recorder.start(log);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		
		@Test
		public void modifierMethodPublicFinal() throws Exception{
			mActor.playInstancePublicFinal();
			expected ="MODIFIERS : public final";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void modifierMethodPublicSynchronized() throws Exception{
			mActor.playInstancePublicSynchronized();
			expected ="MODIFIERS : public synchronized";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
		@Test
		public void modifierMethodPublicNative() throws Exception{
			mActor.playInstancePublicStrictFP();;
			expected ="MODIFIERS : public strictfp";
			String[] actual = log.describeAll();
			assertEquals(InfoOrder.values().length-1,actual.length);
			assertInArray(expected, actual);
		}
	}
	static interface InterfaceActor{
		public abstract void playInstancePublic();

		public abstract boolean playInstancePublic(boolean b);

		public abstract boolean playInstancePublic(int i, String s);
	}
	static class Actor implements InterfaceActor{
		Actor(char cDefault){}
		public Actor(){}
		public Actor(boolean bPublic){}
		public Actor(int i, String s){}
		private Actor(double dPrivate){}
		protected Actor(String sProtected){}
		
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
		
		protected void playInstanceProtected()
		{}
		void playInstanceDefault()
		{}
		
		
	}
	private static void assertInArray(String expected, String[] actual){
		boolean inArray = false;
		for (String s : actual)
			if (expected.equals(s))
				inArray = true;
		assertTrue(inArray);		
	}
}