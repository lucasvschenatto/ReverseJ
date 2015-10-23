package reversej;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import reversej.diagram.Diagram;
import reversej.file.FileDiagram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileDiagramTest{
	private FileDiagram fileDiagram;
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
		fileDiagram = createFileDiagram(null,null);
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
		fileDiagram = createFileDiagram(d,null);
		assertEquals(d,fileDiagram.getDiagram());
	}
	@Test
	public void setsfileName(){
		String p = "myName";
		fileDiagram = createFileDiagram(null,p);
		assertEquals(p,fileDiagram.getFileName());
	}
	@Test
	public void savingFolderIsFilesUnderProject(){
		assertEquals(folder.getAbsoluteFile(),new File(fileDiagram.getSavingFolderPath()));
	}
	@Test
	public void saveInFolderFilesUnderProjectLocationWithUMLExtension(){
		fileDiagram = createFileDiagram(Diagram.getInstance(),name);
		fileDiagram.save();
		java.io.File file = new File(folder, nameWithExtension);
	    assertTrue(file.exists());
	    assertTrue(file.isFile());
	}
	@Test
	public void savesModelInFile(){
		fileDiagram = createFileDiagram(Diagram.getInstance(),name);
		fileDiagram.save();
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
	public FileDiagram createFileDiagram(Diagram diagram, String fileName){
		return new FileDiagram(diagram, fileName);
	}
}
