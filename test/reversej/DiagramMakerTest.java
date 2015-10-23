package reversej;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Package;
import org.junit.*;

import reversej.diagram.Diagram;
import reversej.diagram.DiagramHandler;
import reversej.diagram.DiagramStrategy;
import reversej.diagram.RepositoryProvider;
import reversej.diagram.strategies.uml2adapter.AdapterClassToUml2;
import reversej.information.Information;

public class DiagramMakerTest{
	private static boolean strategyCreateMethodWasCalled;
	private static List<Information> actualPassedInformations; 
	private DiagramHandler diagramHandler;
	private RepositoryProvider provider;
	private List<DiagramStrategy> strategy;
	@Before
	public void setup(){
		actualPassedInformations = null;
		strategyCreateMethodWasCalled = false;
		provider = createStubProvider();
		strategy = new LinkedList<DiagramStrategy>();
		strategy.add(createStubDiagramStrategy());
	}
	@Test
	public void constructorSetsProvider(){
		RepositoryProvider expected = createStubProvider();
		diagramHandler = new DiagramHandler(expected,null);
		
		RepositoryProvider actual = diagramHandler.getProvider();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void constructorSetsDiagramType(){
		List<DiagramStrategy> expected = new LinkedList<DiagramStrategy>(); 
		expected.add(createStubDiagramStrategy());
		diagramHandler = new DiagramHandler(null,expected);
		
		List<DiagramStrategy> actual = diagramHandler.getDiagramStrategies();
		
		assertEquals(expected.get(0), actual.get(0));
	}

	
	@Test
	public void whenMake_CallsCreateMethodInDiagramStrategy(){
		diagramHandler = createDiagramMaker();
		diagramHandler.make();
		assertTrue(strategyCreateMethodWasCalled);
	}
	@Test
	public void whenMake_PassesInformationsFromProviderToDiagram_ThruCreateMethod(){
		List<Information> expected = new LinkedList<Information>();
		diagramHandler = createDiagramMaker(expected);
		
		diagramHandler.make();
		
		assertEquals(expected, actualPassedInformations);
	}
	@Test
	public void make_returnsDiagram(){
		diagramHandler = createDiagramMaker();
		Diagram diagram = diagramHandler.make();
		assertNotNull(diagram);
	}
	
	
	
	
	
	
	
	
	protected static void PassedInformations(List<Information> informations) {
		actualPassedInformations = informations;
	}
	protected static void createMethodWasCalled(){
		strategyCreateMethodWasCalled = true;
	}
	private DiagramHandler createDiagramMaker(){
		provider = createStubProvider();
		strategy.clear();
		strategy.add(createStubDiagramStrategy());
		return new DiagramHandler(provider,strategy);
	}
	private DiagramHandler createDiagramMaker(List<Information> informations){
		provider = createStrubProvider(informations);
		strategy.clear();
		strategy.add(createStubDiagramStrategy());
		return new DiagramHandler(provider,strategy);
	}
	private DiagramStrategy createStubDiagramStrategy() {
		DiagramStrategy expected = new DiagramStrategy() {
			@Override
			public Package generate(List<Information> informations) {
				DiagramMakerTest.createMethodWasCalled();
				DiagramMakerTest.PassedInformations(informations);
				return null;
			}

			@Override
			public AdapterClassToUml2 getAdapter() {
				return null;
			}
		};
		return expected;
	}
	private RepositoryProvider createStubProvider() {
		RepositoryProvider expected = new RepositoryProvider() {
			@Override
			public List<Information> getAll() {
				return null;
			}
			@Override
			public Information getNext() {
				return null;
			}
		};
		return expected;
	}
	private RepositoryProvider createStrubProvider(List<Information> list) {
		RepositoryProvider provider = new RepositoryProvider() {
			@Override
			public Information getNext() {
				return null;
			}
			@Override
			public List<Information> getAll() {
				List<Information> local = list;
				return local;
			}
		};
		return provider;
	}
}