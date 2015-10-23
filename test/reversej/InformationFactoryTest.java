package reversej;

import static org.junit.Assert.*;

import org.junit.*;

import reversej.information.Information;
import reversej.information.impl.ICaller;
import reversej.information.impl.IEmpty;
import reversej.information.impl.IHandler;
import reversej.information.impl.IInterface;
import reversej.information.impl.IMethod;
import reversej.information.impl.IModifiers;
import reversej.information.impl.IParameters;
import reversej.information.impl.IReturn;
import reversej.information.impl.ITarget;
import reversej.information.impl.IThrow;
import reversej.information.impl.InformationFactoryImpl;

public class InformationFactoryTest {
	@Test
	public void createCaller() {
		String value = "test";
		String expectedDescribe = "ICaller : test";
		Information i = InformationFactoryImpl.createCaller(value);
		assertTrue(i instanceof ICaller);
		assertEquals(value, i.getValue());
		assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createInterface() {
		String value = "test";
		String expectedDescribe = "IInterface : test";
		Information i = InformationFactoryImpl.createInterface(value);
		assertTrue(i instanceof IInterface);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createTarget() {
		String value = "test";
		String expectedDescribe = "ITarget : test";
		Information i = InformationFactoryImpl.createTarget(value);
		assertTrue(i instanceof ITarget);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createModifiers() {
		String value = "test";
		String expectedDescribe = "IModifiers : test";
		Information i = InformationFactoryImpl.createModifiers(value);
		assertTrue(i instanceof IModifiers);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createMethod() {
		String value = "test";
		String expectedDescribe = "IMethod : test";
		Information i = InformationFactoryImpl.createMethod(value);
		assertTrue(i instanceof IMethod);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createSignature() {
		String value = "test";
		String expectedDescribe = "IParameters : test";
		Information i = InformationFactoryImpl.createParameters(value);
		assertTrue(i instanceof IParameters);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createReturn() {
		String value = "test";
		String expectedDescribe = "IReturn : test";
		Information i = InformationFactoryImpl.createReturn(value);
		assertTrue(i instanceof IReturn);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createThrow() {
		String value = "test";
		String expectedDescribe = "IThrow : test";
		Information i = InformationFactoryImpl.createThrow(value);
		assertTrue(i instanceof IThrow);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createHandler() {
		String value = "test";
		String expectedDescribe = "IHandler : test";
		Information i = InformationFactoryImpl.createHandler(value);
		assertTrue(i instanceof IHandler);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
	@Test
	public void createDummy() {
		String value = "test";
		String expectedDescribe = "IEmpty : test";
		Information i = InformationFactoryImpl.createEmpty(value);
		assertTrue(i instanceof IEmpty);
		assertEquals(value, i.getValue());
	assertEquals(expectedDescribe, i.describe());
	}
}
