package reversej;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import reversej.diagram.Diagram;
import reversej.file.FileDiagramInPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class FileDiagramTest{
	private FileDiagramInPackage fileDiagramInPackage;
	private static String EXTENSION = ".uml";
	private static java.io.File folder;
	private static String name;
	private static String nameWithExtension;
	@BeforeClass
	public static void setUpClass(){
		folder = new java.io.File(System.getProperty("user.dir")+"\\files\\");
		name = "diagram";
		nameWithExtension = name+EXTENSION;
	}
	
	@Before
	public void setUp() throws Exception {
		fileDiagramInPackage = createFileDiagram(null,null);
		Diagram.resetInstance();
	}
	@After
	public void tearDown(){		
		if(folder.exists())
			for(File file : folder.listFiles())
				if(file.getName().equals(nameWithExtension))
					file.delete();
	}
	@AfterClass
	public static void tearDownClass(){
		if(folder.exists() && folder.list() == null)
			folder.delete();
	}
	
	@Test
	public void setsDiagram(){
		Diagram d = Diagram.resetInstance();
		fileDiagramInPackage = createFileDiagram(d,null);
		assertEquals(d,fileDiagramInPackage.getDiagram());
	}
	@Test
	public void setsfileName(){
		String p = "myName";
		fileDiagramInPackage = createFileDiagram(null,p);
		assertEquals(p,fileDiagramInPackage.getFileName());
	}
	@Test
	public void savingFolderIsFilesUnderProject(){
		assertEquals(folder.getAbsoluteFile(),new File(fileDiagramInPackage.getSavingFolderPath()));
	}
	@Test
	public void saveInFolderFilesUnderProjectLocationWithUMLExtension(){
		fileDiagramInPackage = createFileDiagram(Diagram.getInstance(),name);
		fileDiagramInPackage.save();
		java.io.File file = new File(folder, nameWithExtension);
	    assertTrue(file.exists());
	    assertTrue(file.isFile());
	}
	@Test
	public void savesModelInFile(){
		fileDiagramInPackage = createFileDiagram(Diagram.getInstance(),name);
		fileDiagramInPackage.save();
	    assertFileHasModel();
	}

	private void assertFileHasModel() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(folder, nameWithExtension)));
	        String line = br.readLine();
	        boolean hasModel = false;
	        while (line != null && !hasModel){
	        	hasModel = line.contains("<uml:Model");
	        	line = br.readLine();
	        }
	        br.close();
	        assertTrue(hasModel);
	    } catch (Exception e) {
			fail(e.getMessage());
		}
	}
	public FileDiagramInPackage createFileDiagram(Diagram diagram, String fileName){
		return new FileDiagramInPackage(diagram, fileName);
	}
}
