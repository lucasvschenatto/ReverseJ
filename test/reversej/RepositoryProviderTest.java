package reversej;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import reversej.diagram.Information;
import reversej.diagram.InformationFactory;
import reversej.diagram.RepositoryProvider;
import reversej.diagram.informationmodel.InformationFactoryImpl;
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
	public static class InterfaceMethods extends RepositoryProviderTest{
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
	}
	
	public static class FixCorruptedData extends RepositoryProviderTest{
		@Test
		public void addReturnToSuperCall(){
			String id1 = "01";
			String id2 = "02";
			List<Information> expected = new LinkedList<Information>();
			expected.add(InformationFactoryImpl.createClass(TestUtilities.CLASS + id1));
			expected.add(InformationFactoryImpl.createModifiers(TestUtilities.MODIFIERS + id1));
			expected.add(InformationFactoryImpl.createMethod(TestUtilities.METHOD + id1));
			expected.add(InformationFactoryImpl.createParameters(TestUtilities.PARAMETERS + id1));
			expected.add(InformationFactoryImpl.createReturn(TestUtilities.RETURN_TYPE + id2));
			expected.add(InformationFactoryImpl.createClass(TestUtilities.CLASS + id2));
			expected.add(InformationFactoryImpl.createModifiers(TestUtilities.MODIFIERS + id2));
			expected.add(InformationFactoryImpl.createMethod(TestUtilities.METHOD + id2));
			expected.add(InformationFactoryImpl.createParameters(TestUtilities.PARAMETERS + id2));
			expected.add(InformationFactoryImpl.createReturn(TestUtilities.RETURN_TYPE + id2));
			
			repositoryRecorder.addInformation("Class",TestUtilities.CLASS + id1);
			repositoryRecorder.addInformation("Modifiers",TestUtilities.MODIFIERS + id1);
			repositoryRecorder.addInformation("Method",TestUtilities.METHOD + id1);
			repositoryRecorder.addInformation("Parameters",TestUtilities.PARAMETERS + id1);
			repositoryRecorder.addInformation("SuperReturn",TestUtilities.RETURN_TYPE + id2);
			repositoryRecorder.addInformation("Class",TestUtilities.CLASS + id2);
			repositoryRecorder.addInformation("Modifiers",TestUtilities.MODIFIERS + id2);
			repositoryRecorder.addInformation("Method",TestUtilities.METHOD + id2);
			repositoryRecorder.addInformation("Parameters",TestUtilities.PARAMETERS + id2);
			repositoryRecorder.addInformation("SubReturn",TestUtilities.RETURN_TYPE + id2);
			
			List<Information> actual = provider.getAll(factory);
			
			assertInformationListEquals(expected, actual);
		}
	}
	private static void assertEqualInformationAttributes(Information expected, Information actual){
		assertEquals(expected.getClass(),actual.getClass());
		assertEquals(expected.getValue(),actual.getValue());
	}
	private static void assertInformationListEquals(List<Information> expected, List<Information> actual){
		String message = "size expected \""+expected.size()+"\", actual \""+actual.size()+"\"";
		assertTrue(message, expected.size() == actual.size());
		assertListsInformationsAreEquivalent(expected, actual);
	}
	private static void assertListsInformationsAreEquivalent(List<Information> expected,
			List<Information> actual) {
		for (Information expInfo : expected) {
			boolean found = false;
			for (Information actInfo : actual) {
				if(expInfo.getClass().equals(actInfo.getClass()))
					if(expInfo.getValue().equals(actInfo.getValue())){
						found = true;
						break;
					}
			}
			assertTrue("Information \""+expInfo.describe()+"\" not found",found);
		}
	}
}
