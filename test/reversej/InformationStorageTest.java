package reversej;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import reversej.information.Information;
import reversej.information.impl.InformationFactoryImpl;
import reversej.repository.RepositoryInformation;
import reversej.tracer.RepositoryRecorder;

public class InformationStorageTest {
	public static class getInfo{
		@Test
		public void addOneInformation() throws Exception{
			Information expected = InformationFactoryImpl.createEmpty("test");
			RepositoryRecorder g = new RepositoryInformation();
			g.addInformation(expected);
			Information actual = g.getInfo("test");
			assertEquals(expected, actual);
		}
		@Test
		public void addTwoInformations() throws Exception{
			Information expectedOne = InformationFactoryImpl.createEmpty("one");
			Information expectedTwo = InformationFactoryImpl.createEmpty("two");
			RepositoryRecorder g = new RepositoryInformation();
			g.addInformation(expectedOne);
			g.addInformation(expectedTwo);
			Information actualOne = g.getInfo("one");
			Information actualTwo = g.getInfo("two");
			assertEquals(expectedOne, actualOne);
			assertEquals(expectedTwo, actualTwo);
		}
		@Test
		public void informationNotFound() throws Exception{
			RepositoryRecorder g = new RepositoryInformation();
			assertNull(g.getInfo(null));
		}		
	}
	public static class DescribeAll{
		@Test
		public void describeOneInformation() throws Exception{
			Information expected = InformationFactoryImpl.createEmpty("expected");
			RepositoryRecorder g = new RepositoryInformation();
			g.addInformation(expected);
			List<String> actual = g.describeAll();
			assertTrue(actual.contains(expected.describe()));
		}
		@Test
		public void describeTwoInformations() throws Exception{
			Information one = InformationFactoryImpl.createEmpty("valueOne");
			Information two = InformationFactoryImpl.createEmpty("valueTwo");
			RepositoryRecorder g = new RepositoryInformation();
			g.addInformation(one);
			g.addInformation(two);
			List<String> actual = g.describeAll();
			assertTrue(actual.contains(one.describe()));
			assertTrue(actual.contains(two.describe()));
		}
		@Test
		public void describeNoInformations() throws Exception{
			List<String> actual = new RepositoryInformation().describeAll();
			assertTrue(actual.isEmpty());
		}
	}
	public static class Size{
		@Test
		public void sizeZero(){
			RepositoryRecorder g = new RepositoryInformation();
			assertTrue(g.isEmpty());
			assertEquals(0,g.size());
		}
		@Test
		public void sizeOne(){
			Information info = InformationFactoryImpl.createEmpty("info");
			RepositoryRecorder g = new RepositoryInformation();
			g.addInformation(info);
			assertEquals(1,g.size());
		}
		@Test
		public void sizeTen(){
			Information info = InformationFactoryImpl.createEmpty("info");
			RepositoryRecorder g = new RepositoryInformation();
			int times = 10;
			for(int i = 1;i<=times;i++)
				g.addInformation(info);
			assertEquals(times,g.size());
		}
	}
	public static class Util{
		@Test
		public void addInformationObject() throws Exception{
			RepositoryInformation g = new RepositoryInformation();
			Information expected = InformationFactoryImpl.createCaller("test");
			g.addInformation(expected);
			List<Information> l = g.getAll();
			assertTrue(l.contains(expected));
			
		}
	}
}