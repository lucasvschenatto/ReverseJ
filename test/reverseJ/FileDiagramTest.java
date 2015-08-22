package reverseJ;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

public class FileDiagramTest{
	private FileDiagram f;
	@Before
	public void setUp() throws Exception {
		f = createFileDiagram(null,null,null);
	}

	@Test
	public void setsDiagram(){
		Diagram d = Diagram.resetInstance();
		f = createFileDiagram(d,null,null);
		assertEquals(d,f.getDiagram());
	}
	@Test
	public void setsPath(){
		String p = "myPath";
		f = createFileDiagram(null,p,null);
		assertEquals(p,f.getPath());
	}
	@Test
	public void setsName(){
		String n = "myName";
		f = createFileDiagram(null,null,n);
		assertEquals(n,f.getName());
	}
	@Test
	public void saveInPath(){
		java.io.File file = new java.io.File("");   //Dummy file
	    String path = file.getAbsolutePath()+"\\files\\";
		f = createFileDiagram(null,path,"diagram");
//		f.save();
	    assertTrue(new File(path+"diagram.uml").exists());
	}
	
	public FileDiagram createFileDiagram(Diagram diagram, String path, String name){
		return new FileDiagram(diagram, path,name);
	}
	public static class ToDoInGeneralConfig{
		@Test
		public void doesntSaveIfPathIsntDirectory(){
			String path = "incorrect";
		}
		@Test
		public void doesntSaveIfNameIsInvalid(){
			String name = "invalid";
		}
	}
}
