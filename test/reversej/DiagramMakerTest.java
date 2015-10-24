package reversej;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.aspectj.runtime.reflect.Factory;
import org.eclipse.uml2.uml.Package;
import org.junit.*;

import reversej.diagram.Diagram;
import reversej.diagram.DiagramEngine;
import reversej.diagram.DiagramStrategy;
import reversej.diagram.Information;
import reversej.diagram.InformationFactory;
import reversej.diagram.RepositoryProvider;
import reversej.diagram.informationmodel.InformationFactoryImpl;
import reversej.diagram.strategies.uml2adapter.AdapterClassToUml2;

public class DiagramMakerTest{
	private static boolean strategyCreateMethodWasCalled;
	private static List<Information> actualPassedInformations; 
	private DiagramEngine diagramEngine;
	private RepositoryProvider provider;
	private List<DiagramStrategy> strategy;
	private InformationFactory factory;
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
		diagramEngine = new DiagramEngine(expected,null, null);
		
		RepositoryProvider actual = diagramEngine.getProvider();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void constructorSetsDiagramType(){
		List<DiagramStrategy> expected = new LinkedList<DiagramStrategy>(); 
		expected.add(createStubDiagramStrategy());
		diagramEngine = new DiagramEngine(null,null, expected);
		
		List<DiagramStrategy> actual = diagramEngine.getDiagramStrategies();
		
		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void constructorSetsInformationFactory(){
		InformationFactory expected = new InformationFactoryImpl();
		diagramEngine = new DiagramEngine(null,expected, null);
		
		InformationFactory actual = diagramEngine.getInformationFactory();
		
		assertEquals(expected, actual);
	}
	@Test
	public void whenMake_CallsCreateMethodInDiagramStrategy(){
		diagramEngine = createDiagramMaker();
		diagramEngine.make();
		assertTrue(strategyCreateMethodWasCalled);
	}
	@Test
	public void whenMake_PassesInformationsFromProviderToDiagram_ThruCreateMethod(){
		List<Information> expected = new LinkedList<Information>();
		diagramEngine = createDiagramMaker(expected);
		
		diagramEngine.make();
		
		assertEquals(expected, actualPassedInformations);
	}
	@Test
	public void make_returnsDiagram(){
		diagramEngine = createDiagramMaker();
		Diagram diagram = diagramEngine.make();
		assertNotNull(diagram);
	}
	
	
	
	
	
	
	
	
	protected static void PassedInformations(List<Information> informations) {
		actualPassedInformations = informations;
	}
	protected static void createMethodWasCalled(){
		strategyCreateMethodWasCalled = true;
	}
	private DiagramEngine createDiagramMaker(){
		provider = createStubProvider();
		strategy.clear();
		strategy.add(createStubDiagramStrategy());
		factory = createStubFactory();
		return new DiagramEngine(provider, factory, strategy);
	}
	private DiagramEngine createDiagramMaker(List<Information> informations){
		provider = createStrubProvider(informations);
		strategy.clear();
		strategy.add(createStubDiagramStrategy());
		factory = createStubFactory();
		return new DiagramEngine(provider,factory, strategy);
	}
	private DiagramStrategy createStubDiagramStrategy() {
		return new DiagramStrategy() {
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
	}
	private RepositoryProvider createStubProvider() {
		return new RepositoryProvider() {
			@Override
			public List<Information> getAll(InformationFactory factory) {
				return null;
			}
			@Override
			public Information getNext(InformationFactory factory) {
				return null;
			}
		};
	}
	private RepositoryProvider createStrubProvider(List<Information> list) {
		return new RepositoryProvider() {
			@Override
			public Information getNext(InformationFactory factory) {
				return null;
			}
			@Override
			public List<Information> getAll(InformationFactory factory) {
				List<Information> local = list;
				return local;
			}
		};
	}
	private InformationFactory createStubFactory(){
		return new InformationFactory() {
			
			@Override
			public Information create(String type, String value) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}