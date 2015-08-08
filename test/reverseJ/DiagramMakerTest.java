package reverseJ;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Package;
import org.junit.*;

public class DiagramMakerTest{
	private static boolean strategyCreateMethodWasCalled;
	private static List<Information> actualPassedInformations; 
	private DiagramMaker diagramMaker;
	private InformationProvider provider;
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
		InformationProvider expected = createStubProvider();
		diagramMaker = new DiagramMaker(expected,null);
		
		InformationProvider actual = diagramMaker.getProvider();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void constructorSetsDiagramType(){
		List<DiagramStrategy> expected = new LinkedList<DiagramStrategy>(); 
		expected.add(createStubDiagramStrategy());
		diagramMaker = new DiagramMaker(null,expected);
		
		List<DiagramStrategy> actual = diagramMaker.getDiagramStrategies();
		
		assertEquals(expected.get(0), actual.get(0));
	}

	
	@Test
	public void whenMake_CallsCreateMethodInDiagramStrategy(){
		diagramMaker = createDiagramMaker();
		diagramMaker.make();
		assertTrue(strategyCreateMethodWasCalled);
	}
	@Test
	public void whenMake_PassesInformationsFromProviderToDiagram_ThruCreateMethod(){
		List<Information> expected = new LinkedList<Information>();
		diagramMaker = createDiagramMaker(expected);
		
		diagramMaker.make();
		
		assertEquals(expected, actualPassedInformations);
	}
	
	
	
	
	
	
	
	
	
	protected static void PassedInformations(List<Information> informations) {
		actualPassedInformations = informations;
	}
	protected static void createMethodWasCalled(){
		strategyCreateMethodWasCalled = true;
	}
	private DiagramMaker createDiagramMaker(){
		provider = createStubProvider();
		strategy.clear();
		strategy.add(createStubDiagramStrategy());
		return new DiagramMaker(provider,strategy);
	}
	private DiagramMaker createDiagramMaker(List<Information> informations){
		provider = createStrubProvider(informations);
		strategy.clear();
		strategy.add(createStubDiagramStrategy());
		return new DiagramMaker(provider,strategy);
	}
	private DiagramMaker createDiagramMaker(DiagramObject diagram) {
		provider = createStubProvider();
		strategy.clear();
		strategy.add(createStubDiagramStrategy(diagram));
		return new DiagramMaker(provider,strategy);
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
	private DiagramStrategy createStubDiagramStrategy(DiagramObject diag) {
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
	private InformationProvider createStubProvider() {
		InformationProvider expected = new InformationProvider() {
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
	private InformationProvider createStrubProvider(List<Information> list) {
		InformationProvider provider = new InformationProvider() {
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