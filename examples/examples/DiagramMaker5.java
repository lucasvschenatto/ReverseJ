package examples;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Interaction;

public class DiagramMaker5 {

	private static ResourceSet resourceSet = new ResourceSetImpl();
	private static Interaction interaction;
	public static void main(String[] args) throws Exception{
		Package myPackage = createPackage("package5");
		interaction = createInteraction(myPackage, "interaction5");
//		OpaqueBehavior behavior = createOpaqueBehavior(myPackage,"operation1Behavior");
		Lifeline sender = createLifeLine(interaction, "sender5");
		
		Message messageSend = interaction.createMessage("messageSend5");
		Message replyMessage = interaction.createMessage("messageReply5");
		MessageOccurrenceSpecification send1 = createMessageOccurrenceSpecification(sender, messageSend,"messageOcurrenceSpecificationSend5");
		
		Lifeline receiver = createLifeLine(interaction, "receiver5");
		MessageOccurrenceSpecification receive1 = createMessageOccurrenceSpecification(receiver, messageSend,"messageOcurrenceSpecificationReceive5");
		MessageOccurrenceSpecification replyReceive2 = createMessageOccurrenceSpecification(sender, replyMessage,"messageOcurrenceSpecificationReplyReceive5");
		MessageOccurrenceSpecification replySend2 = createMessageOccurrenceSpecification(sender/*receiver*/, replyMessage,"messageOcurrenceSpecificationReplySend5");
		
		replyMessage.setReceiveEvent(replyReceive2);
		replyMessage.setSendEvent(replySend2);
		messageSend.setSendEvent(send1);
		messageSend.setReceiveEvent(receive1);
		messageSend.setMessageSort(MessageSort.CREATE_MESSAGE_LITERAL);
		
		
//		BehaviorExecutionSpecification operation1 = createBehaviorExecutionSpecification(receive1, replySend2, "behaviorExecutionBody");
//		operation1.setBehavior(behavior);
//		ExecutionSpecification e = UMLFactory.eINSTANCE.createActionExecutionSpecification();
//		if(send1.getCovered() == replyReceive2.getCovered()){
//			e.setStart(send1);
//			e.setFinish(replyReceive2);
//			EClass er = UMLFactory.eINSTANCE.
//			Action a = interaction.createAction("aaaaaa", );
//			((ActionExecutionSpecification)e).setAction(a);
			
//		}
//		receiver.destroy();
		
		
		URI outputURI = URI.createFileURI("../ReverseJ/files/diagramStudy52")
		.appendFileExtension(UMLResource.FILE_EXTENSION);
		save(myPackage, outputURI);
	}
	private static OpaqueBehavior createOpaqueBehavior(Package package_,
			String name) {
		
		OpaqueBehavior behavior = UMLFactory.eINSTANCE.createOpaqueBehavior();
		behavior.setPackage(package_);
		behavior.setName("genericBehavior");
		return behavior;
	}
	private static BehaviorExecutionSpecification createBehaviorExecutionSpecification(
			MessageOccurrenceSpecification start,
			MessageOccurrenceSpecification finish, String name) {
		BehaviorExecutionSpecification b = UMLFactory.eINSTANCE.createBehaviorExecutionSpecification();
		b.setEnclosingInteraction(interaction);
		b.setStart(start);
		b.setFinish(finish);
		b.setVisibility(org.eclipse.uml2.uml.VisibilityKind.PUBLIC_LITERAL);
		b.setName("invocationBody1");
		return b;
	}
	private static MessageOccurrenceSpecification createMessageOccurrenceSpecification(
			Lifeline covered, Message message, String name) {
		MessageOccurrenceSpecification m = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
		m.setEnclosingInteraction(interaction);
		m.setCovered(covered);
		m.setMessage(message);
		m.setName(name);
		return m;
	}
	private static Lifeline createLifeLine(Interaction interaction, String name){
		Lifeline l = interaction.createLifeline(name);
		return l;
	}
	private static Interaction createInteraction(Package package_, String name){
		Interaction i = UMLFactory.eINSTANCE.createInteraction();
		i.setName(name);
		i.setPackage(package_);
		return i;
		}
	private static Package createPackage(String name){
		Package p = UMLFactory.eINSTANCE.createPackage();
		p.setName(name);
		return p;
	}
	private static void save(org.eclipse.uml2.uml.Package package_, URI uri) {
		UMLResourcesUtil.init(resourceSet);
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(package_);
		try {
			resource.save(null);
			System.out.println("finished");
		} catch (IOException ioe) {
			err(ioe.getMessage());
		}
	}
	private static void err(String format, Object... args) {
		System.err.printf(format, args);
		if (!format.endsWith("%n"))
			System.err.println();
	}
}
