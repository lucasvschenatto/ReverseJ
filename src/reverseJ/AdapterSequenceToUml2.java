package reverseJ;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;

class AdapterSequenceToUml2 implements AdapterToUml2{
	public static final String PACKAGE_NAME = "Sequence Diagram";
	public static final String DIAGRAM_TYPE = PACKAGE_NAME;
	public static final String SEPARATOR = "::";
	private Diagram diagram;
	private Package rootPackage;
	private Interaction interaction;

	protected AdapterSequenceToUml2(String packageName) {
		setAttributes(packageName);
	}

	private void setAttributes(String packageName) {
		diagram = Diagram.getInstance();
		rootPackage = diagram.getModel().createNestedPackage(packageName);
		interaction = UMLFactory.eINSTANCE.createInteraction();
		interaction.setName(DIAGRAM_TYPE);
		interaction.setPackage(rootPackage);
		Package classPackage = (Package)diagram.getModel().getMember(AdapterClassToUml2.PACKAGE_NAME);
		rootPackage.createPackageImport(classPackage);
	}

	public AdapterSequenceToUml2() {
		setAttributes("default");
	}

	public static AdapterSequenceToUml2 make() {
		return new AdapterSequenceToUml2(PACKAGE_NAME);
	}

	public Package getPackage() {
		return rootPackage;
	}
	
	public Interaction getInteraction() {		
		return interaction;
	}
	public Lifeline createLifeline(String name) {
		Lifeline newLifeline = UMLFactory.eINSTANCE.createLifeline();
		newLifeline.setInteraction(interaction);
		newLifeline.setName(name);
		NamedElement pe = rootPackage.getImportedMember(name);
		if(pe != null){
			Property p = interaction.createOwnedAttribute(name, (Type)pe);
			newLifeline.setRepresents(p);
		}
		return newLifeline;
	}

	public Message createMessage(String sender, String messageContent, String receiver) {
		Message m = interaction.createMessage(messageContent);
		MessageOccurrenceSpecification send = createMessageOccurrenceSpecification(sender,m);
		MessageOccurrenceSpecification receive = createMessageOccurrenceSpecification(receiver,m);
		m.setSendEvent(send);
		m.setReceiveEvent(receive);
		return m;
		
	}
	private MessageOccurrenceSpecification createMessageOccurrenceSpecification(
			String covered, Message message) {
		MessageOccurrenceSpecification mo = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
		mo.setEnclosingInteraction(interaction);
		mo.setCovered(interaction.getLifeline(covered));
		mo.setMessage(message);
		String name = message.isSetName()?
				covered + SEPARATOR + message.getName() : covered + SEPARATOR; 
		mo.setName(name);
		return mo;
	}

	public Diagram getContext() {
		return diagram;
	}

}
