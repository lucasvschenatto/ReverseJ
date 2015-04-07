package reverseJ;


import static reverseJ.RecorderTest.Assert.*;
import org.junit.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class RecorderTest{
	public static class ConstructorPublic extends Log{
		private String expected;
		@Before
		public void setup(){
			Recorder.determineLog(this);
		}
		@Test
		public void caller() throws Exception{
			new Actor();
			expected ="caller : reverseJ.RecorderTest.ConstructorPublic";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			new Actor();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			new Actor();
			expected ="method : <init>";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			new Actor();
			expected ="signature : ()";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			new Actor(true);
			expected ="signature : (boolean b)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			new Actor(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnInstanceType() throws Exception{
			new Actor();
			expected ="return : reverseJ.RecorderTest.Actor";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[4]);
		}
	}
	public static class ConstructorPrivate extends Log{
		private String expected;
		@Before
		public void setup(){
			Recorder.determineLog(this);
		}
		@Test
		public void caller() throws Exception{
			new Actor(1.5);
			expected ="caller : reverseJ.RecorderTest.ConstructorPrivate";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			new Actor(1.5);
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			new Actor(1.5);
			expected ="method : <init>";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			new Actor(1.5);
			expected ="signature : (double d)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnInstanceType() throws Exception{
			new Actor(1.5);
			expected ="return : reverseJ.RecorderTest.Actor";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[4]);
		}
	}
	public static class StoringStructure extends Log{
		Actor actor;
		int expectedLines;
		@Before
		public void setup(){
			expectedLines = 5;
			actor = new Actor();			
			Recorder.determineLog(this);			
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
			int actualLines = this.describeAll().length;
			assertEquals(expectedLines, actualLines);
		}
		@Test
		public void countLinesInstancePrivateMethod(){
			actor.playInstancePrivate();
			int actualLines = this.describeAll().length;
			assertEquals(expectedLines, actualLines);
		}
		@Test
		public void countLinesStaticPublicMethod(){
			Actor.playStaticPublic();
			int actualLines = this.describeAll().length;
			assertEquals(expectedLines, actualLines);
		}
		@Test
		public void countLinesStaticPrivateMethod(){
			Actor.playStaticPrivate();
			int actualLines = this.describeAll().length;
			assertEquals(expectedLines, actualLines);
		}
	}
	public static class InstanceMethodPublic extends Log{
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			Recorder.determineLog(this);
		}
		@Test
		public void caller() throws Exception{
			actor.playInstancePublic();
			expected ="caller : reverseJ.RecorderTest.InstanceMethodPublic";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			actor.playInstancePublic();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			actor.playInstancePublic();
			expected ="method : playInstancePublic";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playInstancePublic();
			expected ="signature : ()";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playInstancePublic(true);
			expected ="signature : (boolean b)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			actor.playInstancePublic(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnType() throws Exception{
			actor.playInstancePublic(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[4]);
		}
	}
	public static class InstanceMethodPrivate extends Log{
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			Recorder.determineLog(this);
		}
		@Test
		public void caller() throws Exception{
			actor.playInstancePrivate();
			expected ="caller : reverseJ.RecorderTest.InstanceMethodPrivate";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			actor.playInstancePrivate();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			actor.playInstancePrivate();
			expected ="method : playInstancePrivate";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playInstancePrivate();
			expected ="signature : ()";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playInstancePrivate(true);
			expected ="signature : (boolean b)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			actor.playInstancePrivate(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnType() throws Exception{
			actor.playInstancePrivate(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[4]);
			assertInArray(expected, actual);
		}
	}
	public static class StaticMethodPublic extends Log{
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			Recorder.determineLog(this);
		}
		@Test
		public void caller() throws Exception{
			Actor.playStaticPublic();
			expected ="caller : reverseJ.RecorderTest.StaticMethodPublic";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			Actor.playStaticPublic();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			Actor.playStaticPublic();
			expected ="method : playStaticPublic";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			Actor.playStaticPublic();
			expected ="signature : ()";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			Actor.playStaticPublic(true);
			expected ="signature : (boolean b)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			Actor.playStaticPublic(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnType() throws Exception{
			Actor.playStaticPublic(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[4]);
		}
	}
	public static class StaticMethodPrivate extends Log{
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			Recorder.determineLog(this);
		}
		@Test
		public void caller() throws Exception{
			Actor.playStaticPrivate();
			expected ="caller : reverseJ.RecorderTest.StaticMethodPrivate";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			Actor.playStaticPrivate();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			Actor.playStaticPrivate();
			expected ="method : playStaticPrivate";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			Actor.playStaticPrivate();
			expected ="signature : ()";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			Actor.playStaticPrivate(true);
			expected ="signature : (boolean b)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void returnType() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[4]);
			assertInArray(expected, actual);
		}
	}
	static class Actor{
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
		
		public void playInstancePublic(){}
		public boolean playInstancePublic(boolean b){return b;}
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