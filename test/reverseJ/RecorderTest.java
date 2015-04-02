package reverseJ;


import static reverseJ.RecorderTest.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class RecorderTest{
	public static class Constructor extends Log{
		private String expected;
		private String[] expectedAll;
		@Before
		public void setup(){
			Recorder.determineLog(this);
		}
		@Test @Ignore
		public void caller() throws Exception{
			new Actor();
			expected ="caller : reverseJ.RecorderTest.caller";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		@Ignore
		public void catchConstructor() throws Exception{
			new Actor();
			expectedAll = new String[]{
					"constructor : reverseJ.RecorderTest.Actor",
//					"caller : reverseJ.RecorderTest.Method",
//					"target : reverseJ.RecorderTest.Actor",
//					"method : constructor",
					};
			String[] actual = this.describeAll();
			assertArrayEquals(expectedAll, actual);
		}
	}
	public static class LogCreationStructure extends Log{
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			Recorder.determineLog(this);
		}
		@Test
		public void countLines(){
			actor.playInstancePublic();
			int expectedLines = 5;
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
		public void signature_returnType() throws Exception{
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
			expected ="method : access$0";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playInstancePrivate();
			expected ="signature : (reverseJ.RecorderTest.Actor arg0)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playInstancePrivate(true);
			expected ="signature : (reverseJ.RecorderTest.Actor arg0, boolean arg1)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			actor.playInstancePrivate(1, "a");
			expected ="signature : (reverseJ.RecorderTest.Actor arg0, int arg1, java.lang.String arg2)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_returnType() throws Exception{
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
		public void signature_returnType() throws Exception{
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
		@Test @Ignore
		public void method() throws Exception{
			Actor.playStaticPrivate();
			expected ="method : access$0";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test @Ignore
		public void signature_NoParameter() throws Exception{
			Actor.playStaticPrivate();
			expected ="signature : (reverseJ.RecorderTest.Actor arg0)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test @Ignore
		public void signature_OneParameter() throws Exception{
			Actor.playStaticPrivate(true);
			expected ="signature : (reverseJ.RecorderTest.Actor arg0, boolean arg1)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test  @Ignore
		public void signature_TwoParameters() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected ="signature : (reverseJ.RecorderTest.Actor arg0, int arg1, java.lang.String arg2)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_returnType() throws Exception{
			Actor.playStaticPrivate(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[4]);
			assertInArray(expected, actual);
		}
	}
	static class Actor{
		private static void playStaticPrivate(){}
		private static boolean playStaticPrivate(int i, String s){return true;}
		private static boolean playStaticPrivate(boolean b){return b;}
		
		public static void playStaticPublic(){}
		public static boolean playStaticPublic(int i, String s){return true;}
		public static boolean playStaticPublic(boolean b){return b;}
		
		private void playInstancePrivate(){}
		private boolean playInstancePrivate(int i, String s){return true;}
		private boolean playInstancePrivate(boolean b){return b;}
		
		public void playInstancePublic(){}
		public boolean playInstancePublic(int i, String s){return true;}
		public boolean playInstancePublic(boolean b){return b;}
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