package reverseJ;


import static reverseJ.RecorderTest.Assert.*;
import org.junit.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class RecorderTest{
	public static class SetupRecorder{
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
	public static class ConstructorPublic extends Log implements RecorderStorageTest{
		private String expected;
		@Before
		public void setUp(){
			Recorder.start(this);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			new Actor();
			expected ="caller : reverseJ.RecorderTest.ConstructorPublic";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			new Actor();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			new Actor();
			expected ="method : <init>";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			new Actor();
			expected ="signature : ()";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			new Actor(true);
			expected ="signature : (boolean b)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			new Actor(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnInstanceType() throws Exception{
			new Actor();
			expected ="return : reverseJ.RecorderTest.Actor";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[4]);
		}
	}
	public static class ConstructorPrivate extends Log implements RecorderStorageTest{
		private String expected;
		@Before
		public void setup(){
			Recorder.start(this);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			new Actor(1.5);
			expected ="caller : reverseJ.RecorderTest.ConstructorPrivate";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			new Actor(1.5);
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			new Actor(1.5);
			expected ="method : <init>";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			new Actor(1.5);
			expected ="signature : (double d)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnInstanceType() throws Exception{
			new Actor(1.5);
			expected ="return : reverseJ.RecorderTest.Actor";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[4]);
		}
	}
	public static class StoringStructure extends Log implements RecorderStorageTest{
		Actor actor;
		int expectedLines;
		@Before
		public void setup(){
			expectedLines = 5;
			actor = new Actor();			
			Recorder.start(this);	
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void setStructureOrder(){			
			RecordingOrder[] expected = InfoOrder.values();
			Recorder.determineOrder(expected);
			RecordingOrder[] actual = Recorder.getOrder();
			assertArrayEquals(expected, actual);
		}
		@Test
		public void countLinesInstancePublicMethod(){
			actor.playInstancePublic();
			int actualLines = super.describeAll().length;
			assertEquals(expectedLines, actualLines);
		}
		@Test
		public void countLinesInstancePrivateMethod(){
			actor.playInstancePrivate();
			int actualLines = super.describeAll().length;
			assertEquals(expectedLines, actualLines);
		}
		@Test
		public void countLinesStaticPublicMethod(){
			Actor.playStaticPublic();
			int actualLines = super.describeAll().length;
			assertEquals(expectedLines, actualLines);
		}
		@Test
		public void countLinesStaticPrivateMethod(){
			Actor.playStaticPrivate();
			int actualLines = super.describeAll().length;
			assertEquals(expectedLines, actualLines);
		}
	}
	public static class InstanceMethodPublic extends Log implements RecorderStorageTest{
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			Recorder.start(this);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			actor.playInstancePublic();
			expected ="caller : reverseJ.RecorderTest.InstanceMethodPublic";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			actor.playInstancePublic();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			actor.playInstancePublic();
			expected ="method : playInstancePublic";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playInstancePublic();
			expected ="signature : ()";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playInstancePublic(true);
			expected ="signature : (boolean b)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			actor.playInstancePublic(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnType() throws Exception{
			actor.playInstancePublic(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[4]);
		}
	}
	public static class InstanceMethodPrivate extends Log implements RecorderStorageTest{
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			Recorder.start(this);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			actor.playInstancePrivate();
			expected ="caller : reverseJ.RecorderTest.InstanceMethodPrivate";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			actor.playInstancePrivate();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			actor.playInstancePrivate();
			expected ="method : playInstancePrivate";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playInstancePrivate();
			expected ="signature : ()";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playInstancePrivate(true);
			expected ="signature : (boolean b)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			actor.playInstancePrivate(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnType() throws Exception{
			actor.playInstancePrivate(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[4]);
			assertInArray(expected, actual);
		}
	}
	public static class StaticMethodPublic extends Log implements RecorderStorageTest{
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			Recorder.start(this);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			Actor.playStaticPublic();
			expected ="caller : reverseJ.RecorderTest.StaticMethodPublic";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			Actor.playStaticPublic();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			Actor.playStaticPublic();
			expected ="method : playStaticPublic";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			Actor.playStaticPublic();
			expected ="signature : ()";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			Actor.playStaticPublic(true);
			expected ="signature : (boolean b)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			Actor.playStaticPublic(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnType() throws Exception{
			Actor.playStaticPublic(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[4]);
		}
	}
	public static class StaticMethodPrivate extends Log implements RecorderStorageTest{
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			Recorder.start(this);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void caller() throws Exception{
			Actor.playStaticPrivate();
			expected ="caller : reverseJ.RecorderTest.StaticMethodPrivate";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			Actor.playStaticPrivate();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			Actor.playStaticPrivate();
			expected ="method : playStaticPrivate";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			Actor.playStaticPrivate();
			expected ="signature : ()";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			Actor.playStaticPrivate(true);
			expected ="signature : (boolean b)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnType() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[4]);
			assertInArray(expected, actual);
		}
	}
	public static class InterfaceMethodPublic extends Log implements RecorderStorageTest{
		InterfaceActor iActor;
		String expected;
		@Before
		public void setup(){
			iActor = new Actor();
			Recorder.start(this);
		}
		@After
		public void cleanUp(){
			Recorder.stop();
		}
		@Test
		public void print() throws Exception{
			iActor.playInstancePublic(1, "a");
			String[] actual = super.describeAll();
			for(String line: actual){
				System.out.println(line);
			}
		}
		@Test
		public void caller() throws Exception{
			iActor.playInstancePublic();
			expected ="caller : reverseJ.RecorderTest.InterfaceMethodPublic";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void declaredInterface() throws Exception{
			iActor.playInstancePublic();
			expected ="interface : reverseJ.RecorderTest.InterfaceActor";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void target() throws Exception{
			iActor.playInstancePublic();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void method() throws Exception{
			iActor.playInstancePublic();
			expected ="method : playInstancePublic";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			iActor.playInstancePublic();
			expected ="signature : ()";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[4]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			iActor.playInstancePublic(true);
			expected ="signature : (boolean b)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[4]);
		}
		@Test@Ignore
		public void signature_TwoParameter() throws Exception{
			iActor.playInstancePublic(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[4]);
		}
		@Test
		public void returnType() throws Exception{
			iActor.playInstancePublic(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = super.describeAll();
			assertEquals(expected, actual[5]);
		}
	}
	static interface InterfaceActor{
		public abstract void playInstancePublic();

		public abstract boolean playInstancePublic(boolean b);

		public abstract boolean playInstancePublic(int i, String s);
	}
	static class Actor implements InterfaceActor{
		public Actor(){}
		public Actor(boolean b){}
		public Actor(int i, String s){}
		private Actor(double d){}
		
		private static void playStaticPrivate()
		{}
		private static boolean playStaticPrivate(boolean b)
		{return b;}
		private static boolean playStaticPrivate(int i, String s)
		{return true;}
		
		public static void playStaticPublic(){}
		public static boolean playStaticPublic(boolean b){return b;}
		public static boolean playStaticPublic(int i, String s){return true;}
		
		private void playInstancePrivate()
		{}
		private boolean playInstancePrivate(boolean b)
		{return b;}
		private boolean playInstancePrivate(int i, String s)
		{return true;}
		
		@Override
		public void playInstancePublic(){}
		@Override
		public boolean playInstancePublic(boolean b){return b;}
		@Override
		public boolean playInstancePublic(int i, String s){return true;}
	}
	static class Assert extends org.junit.Assert{
		static public void assertInArray(String expected, String[] actual){
			boolean inArray = false;
			for (String s : actual)
				if (expected.equals(s))
					inArray = true;
			assertTrue(inArray);
		}
	}
}