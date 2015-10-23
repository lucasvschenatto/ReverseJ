package reversej.diagram;

import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;

public class Diagram {
	private static Diagram instance;
	protected Model model_;
	public static Diagram getInstance(){
		if(instance == null)
			instance = new Diagram("root");
		return instance;
	}
	public static Diagram resetInstance(){
		instance = null;
		return getInstance();
	}
	private Diagram(String name) {
		model_ = UMLFactory.eINSTANCE.createModel();
		model_.setName(name);
	}
	public Model getModel() {
		return model_;		
	}
}
