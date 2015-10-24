package reversej;

import static org.junit.Assert.*;

import org.junit.*;

import reversej.information.Information;
import reversej.information.InformationFactory;
import reversej.information.impl.ICaller;
import reversej.information.impl.IClass;
import reversej.information.impl.IEmpty;
import reversej.information.impl.IError;
import reversej.information.impl.IGeneric;
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
	private static InformationFactory factory;
	@BeforeClass
	public static void setup(){
		factory = new InformationFactoryImpl();
	}
	@Test
	public void regularCreations(){
		assertCreateRegular("Caller","myInfo", ICaller.class);
		assertCreateRegular("Caller","", ICaller.class);
		assertCreateRegular("Caller",null, ICaller.class);
		
		assertCreateRegular("Class","myInfo", IClass.class);
		assertCreateRegular("Class","", IClass.class);
		assertCreateRegular("Class",null, IClass.class);
		
		
		assertCreateRegular("Generic","myInfo", IGeneric.class);
		assertCreateRegular("Generic","", IGeneric.class);
		assertCreateRegular("Generic",null, IGeneric.class);
		
		assertCreateRegular("Handler","myInfo", IHandler.class);
		assertCreateRegular("Handler","", IHandler.class);
		assertCreateRegular("Handler",null, IHandler.class);
		
		assertCreateRegular("Interface","myInfo", IInterface.class);
		assertCreateRegular("Interface","", IInterface.class);
		assertCreateRegular("Interface",null, IInterface.class);
		
		assertCreateRegular("Method","myInfo", IMethod.class);
		assertCreateRegular("Method","", IMethod.class);
		assertCreateRegular("Method",null, IMethod.class);
		
		assertCreateRegular("Modifiers","myInfo", IModifiers.class);
		assertCreateRegular("Modifiers","", IModifiers.class);
		assertCreateRegular("Modifiers",null, IModifiers.class);
		
		assertCreateRegular("Parameters","myInfo", IParameters.class);
		assertCreateRegular("Parameters","", IParameters.class);
		assertCreateRegular("Parameters",null, IParameters.class);
		
		assertCreateRegular("Return","myInfo", IReturn.class);
		assertCreateRegular("Return","", IReturn.class);
		assertCreateRegular("Return",null, IReturn.class);
		
		assertCreateRegular("Target","myInfo", ITarget.class);
		assertCreateRegular("Target","", ITarget.class);
		assertCreateRegular("Target",null, ITarget.class);
		
		assertCreateRegular("Throw","myInfo", IThrow.class);
		assertCreateRegular("Throw","", IThrow.class);
		assertCreateRegular("Throw",null, IThrow.class);
	}
	@Test
	public void irregularCreations(){
		assertCreateEmpty("Empty",null,IEmpty.class);
		assertCreateEmpty("Empty","",IEmpty.class);
		assertCreateEmpty("Empty","nothing",IEmpty.class);
		
		assertCreateError(null,null,IError.class);
		assertCreateError(null,"fail",IError.class);
		assertCreateError("not",null,IError.class);
		assertCreateError("","",IError.class);
		assertCreateError("lol","",IError.class);
		assertCreateError("","blerg",IError.class);
		assertCreateError("what?","hahaha",IError.class);
		assertCreateError("Error","not correct",IError.class);
	}
	
	private void assertCreateEmpty(String className, String value, @SuppressWarnings("rawtypes") Class type){
		Information actual = factory.create(className, value);
		assertClassType(type, actual);
		assertEquals( "", actual.getValue());
		assertEquals("IEmpty",actual.describe());
	}
	private void assertCreateError(String className, String value, @SuppressWarnings("rawtypes") Class type){
		Information actual = factory.create(className, value);
		assertClassType(type, actual);
		assertEquals( "information_type_not_found", actual.getValue());
		assertEquals("IError : "+actual.getValue(),actual.describe());
	}
	private void assertCreateRegular(String className, String value, @SuppressWarnings("rawtypes") Class type){
		Information actual = factory.create(className, value);
		assertClassType(type, actual);
		assertEquals((value==null?"":value) , actual.getValue());
		assertRegularDescribe(className,value, actual);
	}
	private void assertClassType(@SuppressWarnings("rawtypes") Class type, Information actual) {
		String message = "expected type \""+type.getSimpleName()+"\" but was \""+actual.getClass().getSimpleName()+"\"";
		assertTrue(message,type.isInstance(actual));
	}
	private void assertRegularDescribe(String className, String value, Information information){
		value = value==null?"":value;
		String expected = "I"+className+" : "+value;
		String actual = information.describe();
		String message = "expected describe\""+expected+"\" but was \""+actual+"\"";
		assertEquals(message,expected , actual);
	}
}
