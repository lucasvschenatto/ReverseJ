package reversej;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import reversej.diagram.RepositoryProvider;
import reversej.information.Information;
import reversej.information.InformationFactory;
import reversej.information.impl.InformationFactoryImpl;
import reversej.repository.RepositoryInMemory;
import reversej.tracer.RepositoryRecorder;

public class RepositoryProviderTest {
	RepositoryRecorder repositoryRecorder;
	RepositoryProvider provider;
	InformationFactory factory;

	@Before
	public void setUp() throws Exception {
		repositoryRecorder = new RepositoryInMemory();
		provider = (RepositoryInMemory)repositoryRecorder;
		factory = new InformationFactoryImpl();
	}
	@Test
	public void getNext() {		
		Information expected1 = InformationFactoryImpl.createGeneric("nome Fulano");
		Information expected2 = InformationFactoryImpl.createGeneric("idade 100");
		repositoryRecorder.addInformation("Generic","nome Fulano");
		repositoryRecorder.addInformation("Generic","idade 100");
		
		Information actual1 = provider.getNext(factory);
		Information actual2 = provider.getNext(factory);
		
		assertEqualInformationAttributes(expected1, actual1);
		assertEqualInformationAttributes(expected2, actual2);
	}
	@Test
	public void getNext_IfStorageIsEmpty_ReturnsNull() {
		provider = new RepositoryInMemory();
		Information actual = provider.getNext(factory);		
		assertNull(actual);
	}
	@Test
	public void getNext_AfterLast_ReturnsNull() {		
		repositoryRecorder.addInformation("Generic","nome Fulano");
		repositoryRecorder.addInformation("Generic","nome Fulano");
		
		provider.getNext(factory);
		provider.getNext(factory);
		Information actual3 = provider.getNext(factory);
		
		assertNull(actual3);
	}

	@Test
	public void getAllInformations() {		
		Information info1 = InformationFactoryImpl.createGeneric("nome Fulano");
		Information info2 = InformationFactoryImpl.createGeneric("idade 100");
		
		repositoryRecorder.addInformation("Generic","nome Fulano");
		repositoryRecorder.addInformation("Generic","idade 100");
		
		List <Information> actual = provider.getAll(factory);
		List <Information> expected = new LinkedList <Information>();
		expected.add(info1);
		expected.add(info2);
		
		assertInformationListEquals(expected, actual);
	}
	private void assertEqualInformationAttributes(Information expected, Information actual){
		assertEquals(expected.getClass(),actual.getClass());
		assertEquals(expected.getValue(),actual.getValue());
	}
	private static void assertInformationListEquals(List<Information> expected, List<Information> actual){
		assertTrue("Different size", expected.size() == actual.size());
		assertListsInformationsAreEquivalent(expected, actual);
	}
	private static void assertListsInformationsAreEquivalent(List<Information> expected,
			List<Information> actual) {
		for (Information expInfo : expected) {
			boolean found = false;
			for (Information actInfo : actual) {
				if(expInfo.getClass()==actInfo.getClass())
					if(expInfo.getValue()==actInfo.getValue()){
						found = true;
						break;
					}
			}
			assertTrue("Information \""+expInfo.describe()+"\" not found",found);
		}
	}
}
