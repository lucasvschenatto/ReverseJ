package reverseJ;

import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;

public class Context {
	private InfoProvider provider;
	private Package package_;
	private Interaction interaction;
	Context(InfoProvider provider, String packageName, String InteracionName){
		setInfoProvider(provider);
		setPackage(packageName);
		setInteraction(InteracionName);
	}
	private void setInfoProvider(InfoProvider provider) {
		this.provider = provider;
	}
	public InfoProvider getInfoProvider() {
		return provider;
	}

	private void setPackage(String name) {
		package_ = UMLFactory.eINSTANCE.createPackage();
		package_.setName(name);
	}
	public Package getPackage() {
		return package_;
		
	}

	private void setInteraction(String name) {
		interaction = UMLFactory.eINSTANCE.createInteraction();
		interaction.setPackage(package_);
		interaction.setName(name);
	}
	public Interaction getInteraction() {
		return interaction;
	}
}
