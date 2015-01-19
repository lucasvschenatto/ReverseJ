package teste;
import static org.junit.Assert.*;
import model.ModelAspect;
import model.*;
import model.Log;

import org.junit.*;

public class TracerTests{
	private Log expected;
	private Log actual;
	
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
	}
	@Ignore@Test
	public void TraceMethod(){
		expected = new Log();
		expected.className = "Hero";
		actual = new Log();
		actual.className = "Hero";
//		Hero h = new Hero();
//		h.save();
		compareLogs();
	}
	private void compareLogs() {
		assertEquals(expected.className,actual.className);
	}
	@Before
	public void before(){
		ModelAspect.start();
	}
	@After
	public void after(){
		ModelAspect.stop();
	}
	
	private class Hero{
		private boolean save(){
			return true;
		}
	}

}
