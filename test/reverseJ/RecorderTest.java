package reverseJ;

import static org.junit.Assert.*;

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
		@Test
		@Ignore
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
	public static class Method extends Log{
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			Recorder.determineLog(this);
		}
		@Test
		public void caller() throws Exception{
			actor.play();
			expected ="caller : reverseJ.RecorderTest.Method";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			actor.play();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			actor.play();
			expected ="method : play";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.play();
			expected ="signature : ()";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameter() throws Exception{
			actor.play(1, "a");
			expected ="signature : (int i, java.lang.String s)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_returnType() throws Exception{
			actor.play(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[4]);
		}
	}
	public static class MethodPrivate extends Log{
		Actor actor;
		String expected;
		@Before
		public void setup(){
			actor = new Actor();
			Recorder.determineLog(this);
		}
		@Test
		public void caller() throws Exception{
			actor.playPrivate();
			expected ="caller : reverseJ.RecorderTest.MethodPrivate";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[0]);
		}
		@Test
		public void target() throws Exception{
			actor.playPrivate();
			expected ="target : reverseJ.RecorderTest.Actor";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[1]);
		}
		@Test
		public void method() throws Exception{
			actor.playPrivate();
			expected ="method : access$0";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[2]);
		}
		@Test
		public void signature_NoParameter() throws Exception{
			actor.playPrivate();
			expected ="signature : ()";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_OneParameter() throws Exception{
			actor.playPrivate(true);
			expected ="signature : (reverseJ.RecorderTest.Actor arg0, boolean arg1)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_TwoParameters() throws Exception{
			actor.playPrivate(1, "a");
			expected ="signature : (reverseJ.RecorderTest.Actor arg0, int arg1, java.lang.String arg2)";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[3]);
		}
		@Test
		public void signature_returnType() throws Exception{
			actor.playPrivate(1, "a");
			expected ="return : java.lang.Boolean";
			String[] actual = this.describeAll();
			assertEquals(expected, actual[4]);
		}
	}
	static class Actor{
		static void play(){}
		static boolean play(int i, String s){return true;}
		private static void playPrivate(){}
		private boolean playPrivate(int i, String s){return true;}
		private boolean playPrivate(boolean b){return b;}
	}
}