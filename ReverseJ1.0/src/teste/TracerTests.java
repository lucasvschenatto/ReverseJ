package teste;
import static org.junit.Assert.*;
import model.*;

import org.junit.*;

public class TracerTests{
	private static Log expected;
	private static Log actual;
	
	@Test
	public void turnOn() {
		ModelAspect.start();
		assertTrue(ModelAspect.isRunning());
	}
	@Test
	public void turnOf(){
		ModelAspect.stop();
		assertFalse(ModelAspect.isRunning());
	}
	@Test
	public void pickOutConstructor(){
		C1 c = new C1();
		c.doNothing();
		c.doNothing();
		expected.classType = c.toString();
		compareLogs();
	}
	@Ignore@Test
	public void TraceMethod(){
		expected = new Log();
//		expected.className = "Hero";
		actual = new Log();
//		actual.className = "Hero";
//		Hero h = new Hero();
//		h.save();
		compareLogs();
	}
	private void compareLogs() {
		assertEquals(expected.classType,actual.classType);
	}
	@Before
	public void before(){
		ModelAspect.start();
		expected = new Log();
	}
	@After
	public void after(){
		ModelAspect.stop();
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
