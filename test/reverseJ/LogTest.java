package reverseJ;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import reverseJ.Log;

@RunWith(Enclosed.class)
public class LogTest {
	public static class DescribeTests {
		@Test
		public void addOneInformation() throws Exception{
			RecorderStorage g = new Log();
			g.addInformation("name", "Adam");
			String actual = g.describe("name");
			assertEquals("Adam", actual);
		}
		@Test
		public void addTwoInformations() throws Exception{
			RecorderStorage g = new Log();
			g.addInformation("male", "Adam");
			g.addInformation("female", "Eve");
			String male = g.describe("male");
			String female = g.describe("female");
			assertEquals("Adam", male);
			assertEquals("Eve", female);
		}
		@Test(expected=Log.NotFoundInformationException.class)
		public void informationNotFound_ThrowsInformationNotFoundException() throws Exception{
			RecorderStorage g = new Log();
			g.addInformation("name", "Johnathan");
			g.describe("nickName");
		}
	}
	public static class DescribeAllTests{
		@Test
		public void describeOneInformation() throws Exception{
			RecorderStorage g = new Log();
			g.addInformation("name", "Adam");
			String [] actual = g.describeAll();
			assertArrayEquals(new String [] {"name : Adam"}, actual);
		}
		@Test
		public void describeTwoInformations() throws Exception{
			RecorderStorage g = new Log();
			g.addInformation("male", "Adam");
			g.addInformation("female", "Eve");
			String [] actual = g.describeAll();
			assertArrayEquals(new String [] {"male : Adam","female : Eve"}, actual);
		}
		@Test
		public void describeNoInformations() throws Exception{
			String[] expected = new String [] {Log.emptyLogInfo};
			String[]actual = new Log().describeAll();
			assertArrayEquals(expected, actual);
		}
	}
}
