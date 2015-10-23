package reversej;

import java.io.File;

import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPlugin;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.eclipse.uml2.uml.internal.resource.UMLHandler;
import org.eclipse.uml2.uml.util.UMLSwitch;
import org.eclipse.uml2.uml.util.UMLValidator;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import reversej.controller.Controller;
import reversej.diagram.Diagram;
import reversej.tracer.Tracer;
import static reversej.controller.ControllerState.*;

public class ControllerTest {
	Controller c;
	boolean successfull;
	@Before
	public void setup(){
		c = new Controller();
	}
	@After
	public void tearDown(){
		Tracer.stop();
	}
	public static class FirstState extends ControllerTest{
		@Test
		public void firstStateIsInitial(){
			assertEquals(INITIAL, c.getState());
		}
	}
	public static class InitialState extends ControllerTest{
		@Test
		public void whenActionStart_DoStartTracing(){
			successfull = c.start();
			assertTrue(successfull);
			assertEquals(TRACING, c.getState());
			assertTrue(Tracer.isRunning());
		}
		@Test
		public void whenActionStop_DoNothing(){
			successfull = c.stop();
			assertFalse(successfull);
			assertEquals(INITIAL, c.getState());
		}
		@Test
		public void whenActionsave_DoNothing(){
			successfull = c.save(null);
			assertFalse(successfull);
			assertEquals(INITIAL, c.getState());
		}
		@Test
		public void whenActionReset_DoNothing(){
			successfull = c.reset();
			assertFalse(successfull);
			assertEquals(INITIAL, c.getState());
		}
	}
	public static class TracingState extends ControllerTest{
		@Before
		public void setup(){
			super.setup();
			c.start();
		}
		@Test
		public void whenActionStart_DoNothing(){
			successfull = c.start();
			assertFalse(successfull);
			assertEquals(TRACING, c.getState());
		}
		@Test
		public void whenActionStop_DoStopTracing(){
			successfull = c.stop();
			assertTrue(successfull);
			assertEquals(TRACED, c.getState());
			assertFalse(Tracer.isRunning());
		}		
		@Test
		public void whenActionsave_DoNothing(){
			successfull = c.save(null);
			assertFalse(successfull);
			assertEquals(TRACING, c.getState());
		}
		@Test
		public void whenActionReset_DoStopTracingAndResetAttributes(){
			successfull = c.reset();
			assertTrue(successfull);
			assertEquals(INITIAL, c.getState());
			assertFalse(Tracer.isRunning());
			assertTrue(c.getRecorder().isEmpty());
			assertTrue(c.getProvider().getAll().isEmpty());
		}
	}
	public static class TracedState extends ControllerTest{
		java.io.File testFile;		
		@Before
		public void setup(){			
			super.setup();
			testFile = new File("testFileName.uml");
			c.start();
			c.stop();
		}
		@After
		public void tearDown(){
			super.tearDown();
			if(testFile.exists())
				testFile.delete();
		}
		@Test
		public void whenActionStart_DoNothing(){
			successfull = c.start();
			assertFalse(successfull);
			assertEquals(TRACED, c.getState());
		}
		@Test
		public void whenActionStop_DoNothing(){
			successfull = c.stop();
			assertFalse(successfull);
			assertEquals(TRACED, c.getState());
		}
		@Test
		public void whenActionsave_DoSaveDiagram(){
			successfull = c.save(testFile);
			assertTrue(successfull);
			assertEquals(SAVED, c.getState());
			assertTrue(testFile.exists());
		}
		@Test
		public void whenActionReset_DoStopTracingAndResetAttributes(){
			successfull = c.reset();
			assertTrue(successfull);
			assertEquals(INITIAL, c.getState());
			assertFalse(Tracer.isRunning());
			assertTrue(c.getRecorder().isEmpty());
			assertTrue(c.getProvider().getAll().isEmpty());
		}
	}
	public static class SavedState extends ControllerTest{
		File first, second;
		@Before
		public void setup(){			
			super.setup();
			first = new File("firstFileName.uml");
			second = new File("secondFileName.uml");
			c.start();
			c.stop();
			c.save(first);
		}
		@After
		public void tearDown(){
			super.tearDown();
			if(first.exists())
				first.delete();
			if(second.exists())
				second.delete();
		}
		@Test
		public void whenActionStart_DoNothing(){
			successfull = c.start();
			assertFalse(successfull);
			assertEquals(SAVED, c.getState());
			assertTrue(first.exists());
			assertFalse(second.exists());
		}
		@Test
		public void whenActionStop_DoNothing(){
			successfull = c.stop();
			assertFalse(successfull);
			assertEquals(SAVED, c.getState());
			assertTrue(first.exists());
			assertFalse(second.exists());
		}
		@Test
		public void whenActionsave_DoSaveDiagram(){
			successfull = c.save(second);
			assertTrue(successfull);
			assertEquals(SAVED, c.getState());
			assertTrue(first.exists());
			assertTrue(second.exists());
		}
		@Test
		public void whenActionReset_DoStopTracingAndResetAttributes(){
			successfull = c.reset();
			assertTrue(successfull);
			assertEquals(INITIAL, c.getState());
			assertFalse(Tracer.isRunning());
			assertTrue(c.getRecorder().isEmpty());
			assertTrue(c.getProvider().getAll().isEmpty());
			assertTrue(first.exists());
			assertFalse(second.exists());
		}
	}
}
