package reverseJ;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.*;

public class InformationHandlerTest extends ClassDiagramGenerator {
	private RecorderStorage storage;
	private InformationProvider provider;
	private DiagramGenerator diagramGenerator;
	private List<String> concreteClasses;
	private InformationHandler handler;
	public InformationHandlerTest(){
		super(null);
	}
	@Before
	public void setUp() throws Exception {
		concreteClasses = new LinkedList<String>();
		storage = new InformationStorage();
		provider = (InformationStorage)storage;
		diagramGenerator = this;
		handler = new InformationHandler(provider, diagramGenerator);
	}
	@Test
	public void constructorSetsProvider(){
		InformationProvider p = new InformationStorage();
		InformationHandler i = new InformationHandler(p,null);
		
		assertEquals(p,i.getProvider());
	}
	@Test
	public void constructorSetsdiagramGenerator(){
		DiagramGenerator g = ClassDiagramGenerator.make(null);
		InformationHandler i = new InformationHandler(null,g);
		
		assertEquals(g,i.getDiagramGenerator());
	}
	@Test
	public void whenInterpret_HasCallerInformation_CreateConcreteClass() {
		String expected = "Test";
		Information callerInfo = InformationFactory.createCaller(expected);
		storage.addInformation(callerInfo);
		
		handler.interpret();
		
		assertTrue(concreteClasses.contains(expected));
	}
	@Override
	public void createConcreteClass(String name){
		concreteClasses.add(name);
	}
}