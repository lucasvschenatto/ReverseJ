package reversej;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import reversej.diagram.RepositoryProvider;
import reversej.information.Information;
import reversej.information.impl.InformationFactoryImpl;
import reversej.repository.RepositoryInformation;
import reversej.tracer.RepositoryRecorder;

public class InformationProviderTest {
	RepositoryRecorder repositoryRecorder;
	RepositoryProvider provider;

	@Before
	public void setUp() throws Exception {
		repositoryRecorder = new RepositoryInformation();
		provider = (RepositoryInformation)repositoryRecorder;
	}
	@Test
	public void getNext() {		
		Information expected1 = InformationFactoryImpl.createEmpty("nome Fulano");
		Information expected2 = InformationFactoryImpl.createEmpty("idade 100");
		repositoryRecorder.addInformation(expected1);
		repositoryRecorder.addInformation(expected2);
		
		Information actual1 = provider.getNext();
		Information actual2 = provider.getNext();
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}
	@Test
	public void getNext_IfStorageIsEmpty_ReturnsNull() {
		provider = new RepositoryInformation();
		Information actual = provider.getNext();		
		assertNull(actual);
	}
	@Test
	public void getNext_AfterLast_ReturnsNull() {		
		Information info = InformationFactoryImpl.createEmpty("nome Fulano");
		repositoryRecorder.addInformation(info);
		repositoryRecorder.addInformation(info);
		
		provider.getNext();
		provider.getNext();
		Information actual3 = provider.getNext();
		
		assertNull(actual3);
	}

	@Test
	public void getAllInformations() {		
		Information info1 = InformationFactoryImpl.createEmpty("nome Fulano");
		Information info2 = InformationFactoryImpl.createEmpty("idade 100");
		
		repositoryRecorder.addInformation(info1);
		repositoryRecorder.addInformation(info2);
		
		List <Information> actual = provider.getAll();
		List <Information> expected = new LinkedList <Information>();
		expected.add(info1);
		expected.add(info2);
		
		assertInformationListEquals(expected, actual);
	}
	
	private static void assertInformationListEquals(List<Information> expected, List<Information> actual){
		assertTrue("Different size", expected.size() == actual.size());
		assertTrue("Different objects",actual.containsAll(expected));
	}
}
