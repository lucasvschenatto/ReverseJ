package reverseJ;

import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;

public class Context {
	protected Model model_;
//	private Interaction interaction;
//	Context(String modelName, String InteracionName){
//		setModel(modelName);
//		setInteraction(InteracionName);
//	}
	public static Context getInstance(){
		return new Context("root");
	}
	private Context(String name) {
		model_ = UMLFactory.eINSTANCE.createModel();
		model_.setName(name);
	}
	public Model getModel() {
		return model_;		
	}

//	private void setInteraction(String name) {
//		interaction = UMLFactory.eINSTANCE.createInteraction();
//		interaction.setPackage(model_);
//		interaction.setName(name);
//	}
//	public Interaction getInteraction() {
//		return interaction;
//	}
}
