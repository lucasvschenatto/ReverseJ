package reverseJ;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;

class AdapterSequenceToUml2 implements AdapterToUml2{
	public static final String DIAGRAM_TYPE = "Sequence Diagram";
	private Context context;
	private Package rootPackage;
	private Interaction interaction;

	protected AdapterSequenceToUml2(String packageName) {
		setAttributes(packageName);
	}

	private void setAttributes(String packageName) {
		context = Context.getInstance();
		rootPackage = context.getModel().createNestedPackage(packageName);
		interaction = UMLFactory.eINSTANCE.createInteraction();
		interaction.setName(DIAGRAM_TYPE);
		interaction.setPackage(rootPackage);
	}

	public AdapterSequenceToUml2() {
		setAttributes("default");
	}

	public static AdapterSequenceToUml2 make() {
		return new AdapterSequenceToUml2("classDiagram");
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
		return newLifeline;
	}

	public Message createMessage(String sender, String messageContent, String receiver) {
		Message m = interaction.createMessage(messageContent);
		MessageOccurrenceSpecification send = createMessageOccurrenceSpecification(sender,m);
		MessageOccurrenceSpecification receive = createMessageOccurrenceSpecification(sender,m);
		m.setSendEvent(send);
		m.setReceiveEvent(receive);
		return m;
		
	}
	private MessageOccurrenceSpecification createMessageOccurrenceSpecification(
			String covered, Message message) {
		MessageOccurrenceSpecification mo = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
//		m.setEnclosingInteraction(interaction);
//		m.setCovered(covered);
//		m.setMessage(message);
//		m.setName(name);
		return mo;
	}

}
