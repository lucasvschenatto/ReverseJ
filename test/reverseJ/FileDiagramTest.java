package reverseJ;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class FileDiagramTest{
	private FileDiagram fileDiagram;
	private static java.io.File folder;
	private static String path;
	@BeforeClass
	public static void setUpClass(){
		folder = new java.io.File("UnitTestingFolder");
		folder.mkdir();
		path = folder.getAbsolutePath()+"\\diagram.uml";
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
				file.delete();
	}@AfterClass
	public static void tearDownClass(){
		if(folder.exists())
			folder.delete();
	}

	@Test
	public void setsDiagram(){
		Diagram d = Diagram.resetInstance();
		fileDiagram = createFileDiagram(d,null);
		assertEquals(d,fileDiagram.getDiagram());
	}
	@Test
	public void setsPath(){
		String p = "myPath";
		fileDiagram = createFileDiagram(null,p);
		assertEquals(p,fileDiagram.getPath());
	}
	@Test
	public void saveInPath(){
		fileDiagram = createFileDiagram(Diagram.getInstance(),path);
		fileDiagram.save();
		java.io.File file = new File(path);
	    assertTrue(file.exists());
	    assertTrue(file.isFile());
	}
	@Test
	public void savesModelInFile(){
		fileDiagram = createFileDiagram(Diagram.getInstance(),path);
		fileDiagram.save();
	    assertFileHasModel();
	}

	private void assertFileHasModel() {
		try {		
			BufferedReader br = new BufferedReader(new FileReader(path));
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
	public FileDiagram createFileDiagram(Diagram diagram, String path){
		return new FileDiagram(diagram, path);
	}
	public static class ToDoInGeneralConfig{
		@Test@Ignore
		public void nullDiagram(){
		}
		@Test@Ignore
		public void fileExtension(){
			
		}
		@Test@Ignore
		public void doesntSaveIfPathIsntDirectory(){
//			String path = "incorrect";
		}
		@Test@Ignore
		public void doesntSaveIfNameIsInvalid(){
//			String name = "invalid";
		}
		@Test@Ignore
		public void nullPath(){
//			fileDiagram = createFileDiagram(null,null);
//			fileDiagram.save();
		}
		@Test@Ignore
		public void EmptyPath(){
//			fileDiagram = createFileDiagram(null,null);
//			fileDiagram.save();
		}
	}
}
