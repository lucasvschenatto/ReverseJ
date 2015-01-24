package util;

import static org.junit.Assert.*;
import org.junit.*;

public class TracerTest {
	private static Log expected;
	private static Log actual;

	@Test
	public void turnOn() {
		Tracer.start();
		assertTrue(Tracer.isRunning());
	}

	@Test
	public void turnOf() {
		Tracer.stop();
		assertFalse(Tracer.isRunning());
	}

	@Test
	public void pickOutConstructor() {
		DummyClassForTest c = new DummyClassForTest();
//		expected.classType = c.getClass();
		compareLogs(expected, actual);
	}

	@Test
	public void PickOutConstructorsParameter() {
		String param = "Parameter for testing";
//		expected.parameters.add(param);
		DummyClassForTest c = new DummyClassForTest(param);
		compareLogs(expected, actual);

	}

	@Test
	public void PickOutMethodCall() {
	}

	private void compareLogs(Log expected, Log actual) {
//		assertEquals(expected.classType, actual.classType);
	}

	@Before
	public void before() {
		Tracer.start();
		expected = new Log();
	}

	@After
	public void after() {
		Tracer.stop();
		expected = null;
	}

	public static void setResult(Log result) {
		actual = result;
	}

	private class Hero {
		private boolean save() {
			return true;
		}
	}

}
