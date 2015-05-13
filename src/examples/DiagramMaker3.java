package examples;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Interaction;

public class DiagramMaker3 {

	private static ResourceSet resourceSet = new ResourceSetImpl();
	private static Interaction interaction;
	public static void main(String[] args) throws Exception{
		Package myPackage = createPackage("packageSequenceGoal");
		interaction = createInteraction(myPackage, "interactionTest1");
		OpaqueBehavior behavior = createOpaqueBehavior(myPackage,"operation1Behavior");
		Lifeline sender = createLifeLine(interaction, "sender");
		Lifeline receiver = createLifeLine(interaction, "receiver");
		
		Message messageSend = interaction.createMessage("messageSend");
		Message replyMessage = interaction.createMessage("replyMessage");
		
		MessageOccurrenceSpecification send1 = createMessageOccurrenceSpecification(sender, messageSend,"send1");
		messageSend.setSendEvent(send1);
		MessageOccurrenceSpecification receive1 = createMessageOccurrenceSpecification(receiver, messageSend,"receive1");
		messageSend.setReceiveEvent(receive1);
		
		MessageOccurrenceSpecification replySend2 = createMessageOccurrenceSpecification(receiver, replyMessage,"replySend2");
		replyMessage.setSendEvent(replySend2);
		
		BehaviorExecutionSpecification operation1 = createBehaviorExecutionSpecification(receive1, replySend2, "behaviorExecutionBody");
		operation1.setBehavior(behavior);
		
		MessageOccurrenceSpecification replyReceive2 = createMessageOccurrenceSpecification(sender, replyMessage,"replyReceive2");
		replyMessage.setReceiveEvent(replyReceive2);
		
		
		URI outputURI = URI.createFileURI("../ReverseJ/files/diagramStudy3")
		.appendFileExtension(UMLResource.FILE_EXTENSION);
		save(myPackage, outputURI);
	} 
	private static OpaqueBehavior createOpaqueBehavior(Package package_,
			String name) {
		OpaqueBehavior behavior = UMLFactory.eINSTANCE.createOpaqueBehavior();
		behavior.setPackage(package_);
		behavior.setName("operation1Behavior");
		return behavior;
	}
	private static BehaviorExecutionSpecification createBehaviorExecutionSpecification(
			MessageOccurrenceSpecification start,
			MessageOccurrenceSpecification finish, String name) {
		BehaviorExecutionSpecification b = UMLFactory.eINSTANCE.createBehaviorExecutionSpecification();
		b.setEnclosingInteraction(interaction);
		b.setStart(start);
		b.setFinish(finish);
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
