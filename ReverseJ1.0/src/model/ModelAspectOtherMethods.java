package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModelAspectOtherMethods {
	static String fileXML = "ClassDiagram.xml";
	static String inicioXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n <ClassDiagram>";
	static String fimXML = "</ClassDiagram>";
	
	public static void start() {
//		running = true;
//		str.delete(0, str.length());
//		str.append(inicioXML);
	}
	public static void stop() {
//			running = false;
//			str.append(fimXML);
//			salvarDados(str.toString());
	}
	protected static void salvarDados(String texto) {	       
	        FileWriter arquivo;  	          
	        try {
	        	File folder = new File("files/");
	        	if(!folder.exists())
	        		folder.mkdir();
	            arquivo = new FileWriter(new File(folder,"ExecutationSequence.xml"));
	            arquivo.write(texto);  
	            arquivo.close();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	}
}
