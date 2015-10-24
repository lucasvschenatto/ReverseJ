package reversej;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import reversej.information.Information;
import reversej.information.impl.InformationFactoryImpl;
import reversej.repository.RepositoryInMemory;
import reversej.tracer.RepositoryRecorder;

public class RepositoryRecorderTest {
	private static RepositoryRecorder recorder;
	public static class getInfo{
		@Before
		public void setup(){
			recorder = new RepositoryInMemory();
		}
		@Test
		public void addOneInformation() throws Exception{
			String expected = "Generic : test";
			recorder = new RepositoryInMemory();
			recorder.addInformation("Generic","test");
			String actual = recorder.describeInfo("test");
			assertEquals(expected, actual);
		}
		@Test
		public void addTwoInformations() throws Exception{
			String expectedOne = "Generic : one";
			String expectedTwo = "Generic : two";
			recorder = new RepositoryInMemory();
			recorder.addInformation("Generic","one");
			recorder.addInformation("Generic","two");
			String actualOne = recorder.describeInfo("one");
			String actualTwo = recorder.describeInfo("two");
			assertEquals(expectedOne, actualOne);
			assertEquals(expectedTwo, actualTwo);
		}
		@Test
		public void informationNotFound() throws Exception{
			RepositoryRecorder g = new RepositoryInMemory();
			assertNull(g.describeInfo(null));
		}
	}
	public static class DescribeAll{
		@Before
		public void setup(){
			recorder = new RepositoryInMemory();
		}
		@Test
		public void describeOneInformation() throws Exception{
			Information expected = InformationFactoryImpl.createGeneric("expected");
			recorder.addInformation("Generic","expected");
			List<String> actual = recorder.describeAll();
			assertTrue(actual.contains(expected.describe()));
		}
		@Test
		public void describeTwoInformations() throws Exception{
			Information one = InformationFactoryImpl.createGeneric("valueOne");
			Information two = InformationFactoryImpl.createGeneric("valueTwo");
			recorder.addInformation("Generic","valueOne");
			recorder.addInformation("Generic","valueTwo");
			List<String> actual = recorder.describeAll();
			assertTrue(actual.contains(one.describe()));
			assertTrue(actual.contains(two.describe()));
		}
		@Test
		public void describeNoInformations() throws Exception{
			List<String> actual = new RepositoryInMemory().describeAll();
			assertTrue(actual.isEmpty());
		}
	}
	public static class Size{
		@Before
		public void setup(){
			recorder = new RepositoryInMemory();
		}
		@Test
		public void sizeZero(){			
			assertTrue(recorder.isEmpty());
			assertEquals(0,recorder.size());
		}
		@Test
		public void sizeOne(){
			recorder.addInformation("Generic","info");
			assertEquals(1,recorder.size());
		}
		@Test
		public void sizeTen(){
			int times = 10;
			for(int i = 1;i<=times;i++)
				recorder.addInformation("Generic","info");
			assertEquals(times,recorder.size());
		}
	}
}