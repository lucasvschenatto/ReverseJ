package reverseJ;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import reverseJ.Log;

@RunWith(Enclosed.class)
public class LogTest {
	public static class Describe {
		@Test
		public void addOneInformation() throws Exception{
			RecorderStorage g = new Log();
			g.addInformation("MALE", "Adam");
			String actual = g.describe("MALE");
			assertEquals("Adam", actual);
		}
		@Test
		public void addTwoInformations() throws Exception{
			RecorderStorage g = new Log();
			g.addInformation("MALE", "Adam");
			g.addInformation("FEMALE", "Eve");
			String male = g.describe("MALE");
			String female = g.describe("FEMALE");
			assertEquals("Adam", male);
			assertEquals("Eve", female);
		}
		@Test(expected=Log.NotFoundInformationException.class)
		public void informationNotFound_ThrowsInformationNotFoundException() throws Exception{
			RecorderStorage g = new Log();
			g.addInformation("MALE", "Johnathan");
			g.describe("FEMALE");
		}
	}
	public static class DescribeAll{
		@Test
		public void describeOneInformation() throws Exception{
			RecorderStorage g = new Log();
			g.addInformation("MALE", "Adam");
			String [] actual = g.describeAll();
			assertArrayEquals(new String [] {"MALE : Adam"}, actual);
		}
		@Test
		public void describeTwoInformations() throws Exception{
			RecorderStorage g = new Log();
			g.addInformation("MALE", "Adam");
			g.addInformation("FEMALE", "Eve");
			String [] actual = g.describeAll();
			assertArrayEquals(new String [] {"MALE : Adam","FEMALE : Eve"}, actual);
		}
		@Test
		public void describeNoInformations() throws Exception{
			String[] expected = new String [] {Log.emptyLogInfo};
			String[]actual = new Log().describeAll();
			assertArrayEquals(expected, actual);
		}
	}
	public static class Size{
		@Test
		public void sizeZero(){
			RecorderStorage g = new Log();
			assertEquals(0,g.size());
		}
		@Test
		public void sizeOne(){
			RecorderStorage g = new Log();
			g.addInformation("FEMALE", "A");
			assertEquals(1,g.size());
		}
		@Test
		public void sizeTen(){
			RecorderStorage g = new Log();
			int times = 10;
			for(int i = 1;i<=times;i++)
				g.addInformation("FEMALE", "A" + i);
			assertEquals(times,g.size());
		}
	}
}