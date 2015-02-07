package util;

/**
 * @author Lucas
 * 
 */
public class Principal {
	private Storage s;
	public void startAnalysis(){
		s = new Storage();
	}
	public void finishAnalysyis(){
		s.close();
	}
//	public void generateXMI(){
//		
//	}
//	public void generateXMI(String pathName){
//		
//	}
//	public void generateUML(){
//		
//	}
//	public void generateUML(String pathName){
//		
//	}
}
