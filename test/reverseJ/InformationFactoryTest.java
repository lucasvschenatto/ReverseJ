package reverseJ;

import static org.junit.Assert.*;
import org.junit.*;

public class InformationFactoryTest {
	@Test
	public void createCaller() {
		String value = "test";
		String expectedDescribe = "ICaller : test";
		Information i = InformationFactory.createCaller(value);
		assertTrue(i instanceof ICaller);
		assertEquals(value, i.getValue());
		assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createInterface() {
		String value = "test";
		String expectedDescribe = "IInterface : test";
		Information i = InformationFactory.createInterface(value);
		assertTrue(i instanceof IInterface);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createTarget() {
		String value = "test";
		String expectedDescribe = "ITarget : test";
		Information i = InformationFactory.createTarget(value);
		assertTrue(i instanceof ITarget);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createModifiers() {
		String value = "test";
		String expectedDescribe = "IModifiers : test";
		Information i = InformationFactory.createModifiers(value);
		assertTrue(i instanceof IModifiers);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createMethod() {
		String value = "test";
		String expectedDescribe = "IMethod : test";
		Information i = InformationFactory.createMethod(value);
		assertTrue(i instanceof IMethod);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createSignature() {
		String value = "test";
		String expectedDescribe = "IParameters : test";
		Information i = InformationFactory.createParameters(value);
		assertTrue(i instanceof IParameters);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createReturn() {
		String value = "test";
		String expectedDescribe = "IReturn : test";
		Information i = InformationFactory.createReturn(value);
		assertTrue(i instanceof IReturn);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createThrow() {
		String value = "test";
		String expectedDescribe = "IThrow : test";
		Information i = InformationFactory.createThrow(value);
		assertTrue(i instanceof IThrow);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createHandler() {
		String value = "test";
		String expectedDescribe = "IHandler : test";
		Information i = InformationFactory.createHandler(value);
		assertTrue(i instanceof IHandler);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createDummy() {
		String value = "test";
		String expectedDescribe = "IDummy : test";
		Information i = InformationFactory.createDummy(value);
		assertTrue(i instanceof IDummy);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
}
