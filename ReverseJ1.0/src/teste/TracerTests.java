package teste;

import static org.junit.Assert.*;
import model.*;

import org.junit.*;

public class TracerTests{
	private static Log expected;
	private static Log actual;
	
	@Test
	public void turnOn() {
		TracerAspect.start();
		assertTrue(TracerAspect.isRunning());
	}
	@Test
	public void turnOf(){
		TracerAspect.stop();
		assertFalse(TracerAspect.isRunning());
	}
	@Test
	public void pickOutConstructor(){
		C1 c = new C1();
		expected.classType = c.getClass();
		compareLogs(expected, actual);
	}
	@Test
	public void PickOutConstructorsParameter(){
		String param = "Parameter for testing";
		expected.parameters.add(param);
		C1 c = new C1(param);
		compareLogs(expected, actual);
		
	}
	
	
	
	@Test
	public void PickOutMethodCall(){
	}
	
	private void compareLogs(Log expected, Log actual) {
		assertEquals(expected.classType,actual.classType);
	}
	@Before
	public void before(){
		TracerAspect.start();
		expected = new Log();
	}
	@After
	public void after(){
		TracerAspect.stop();
		expected = null;
	}
	
	public static void setResult(Log result){
		actual = result;
	}
	
	private class Hero{
		private boolean save(){
			return true;
		}
	}

}
