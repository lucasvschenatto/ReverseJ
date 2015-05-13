package reverseJ;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;

public class SequenceDiagramGenerator {
	private InfoProvider provider;
	private Package package_;
	private Interaction interaction;
	private Lifeline lifelines;
	public void setInfoProvider(InfoProvider provider) {
		this.provider = provider;
	}

	public InfoProvider getInfoProvider() {
		return provider;
	}

	public void setContextPackage(String name) {
		package_ = UMLFactory.eINSTANCE.createPackage();
		package_.setName(name);
	}

	public Package getContextPackage() {
		return package_;
		
	}

	public Interaction getContextInteraction() {
		return interaction;
	}

	public void setContextInteraction(String name) {
		interaction = UMLFactory.eINSTANCE.createInteraction();
		interaction.setPackage(package_);
		interaction.setName(name);
	}

	public Lifeline getLifeline(String name) {
		return lifelines;
	}

	public void createLifeline(String name) {
		lifelines = UMLFactory.eINSTANCE.createLifeline();
		lifelines.setInteraction(interaction);
		lifelines.setName(name);
	}

}
