package reverseJ;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.*;

import reverseJ.InformationStorage;

public class InformationStorageTest {
	public static class getInfo{
		@Test
		public void addOneInformation() throws Exception{
			Information expected = InformationFactory.createDummy("test");
			RecorderStorage g = new InformationStorage();
			g.addInformation(expected);
			Information actual = g.getInfo("test");
			assertEquals(expected, actual);
		}
		@Test
		public void addTwoInformations() throws Exception{
			Information expectedOne = InformationFactory.createDummy("one");
			Information expectedTwo = InformationFactory.createDummy("two");
			RecorderStorage g = new InformationStorage();
			g.addInformation(expectedOne);
			g.addInformation(expectedTwo);
			Information actualOne = g.getInfo("one");
			Information actualTwo = g.getInfo("two");
			assertEquals(expectedOne, actualOne);
			assertEquals(expectedTwo, actualTwo);
		}
		@Test
		public void informationNotFound() throws Exception{
			RecorderStorage g = new InformationStorage();
			assertNull(g.getInfo(null));
		}		
	}
	public static class DescribeAll{
		@Test
		public void describeOneInformation() throws Exception{
			Information expected = InformationFactory.createDummy("expected");
			RecorderStorage g = new InformationStorage();
			g.addInformation(expected);
			List<String> actual = g.describeAll();
			assertTrue(actual.contains(expected.describe()));
		}
		@Test
		public void describeTwoInformations() throws Exception{
			Information one = InformationFactory.createDummy("valueOne");
			Information two = InformationFactory.createDummy("valueTwo");
			RecorderStorage g = new InformationStorage();
			g.addInformation(one);
			g.addInformation(two);
			List<String> actual = g.describeAll();
			assertTrue(actual.contains(one.describe()));
			assertTrue(actual.contains(two.describe()));
		}
		@Test
		public void describeNoInformations() throws Exception{
			List<String> actual = new InformationStorage().describeAll();
			assertTrue(actual.isEmpty());
		}
	}
	public static class Size{
		@Test
		public void sizeZero(){
			RecorderStorage g = new InformationStorage();
			assertTrue(g.isEmpty());
			assertEquals(0,g.size());
		}
		@Test
		public void sizeOne(){
			Information info = InformationFactory.createDummy("info");
			RecorderStorage g = new InformationStorage();
			g.addInformation(info);
			assertEquals(1,g.size());
		}
		@Test
		public void sizeTen(){
			Information info = InformationFactory.createDummy("info");
			RecorderStorage g = new InformationStorage();
			int times = 10;
			for(int i = 1;i<=times;i++)
				g.addInformation(info);
			assertEquals(times,g.size());
		}
	}
	public static class Util{
		@Test
		public void addInformationObject() throws Exception{
			InformationStorage g = new InformationStorage();
			Information expected = InformationFactory.createCaller("test");
			g.addInformation(expected);
			List<Information> l = g.getAll();
			assertTrue(l.contains(expected));
			
		}
	}
}