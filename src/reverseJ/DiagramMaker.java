package reverseJ;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Interaction;

public class DiagramMaker {

//	public static boolean DEBUG = true;

	private static File outputDir;
	private static ResourceSet resourceSet = new ResourceSetImpl();
	public static void main(String[] args) throws Exception{
		Package myPackage = createPackage("packageSequenceGoal");
		Interaction myInteraction = createInteraction(myPackage, "interactionTest1");
		Lifeline caller = createLifeLine(myInteraction, "caller");
		Lifeline target = createLifeLine(myInteraction, "target");
		
		Message m = myInteraction.createMessage("talk");
		
//		OccurrenceSpecification oSS = UMLFactory.eINSTANCE.createOccurrenceSpecification();
//		oSS.setEnclosingInteraction(myInteraction);
//		oSS.setCovered(caller);
//		oSS.setName("ocurrenceSpecificationStart");
//		OccurrenceSpecification oSF = UMLFactory.eINSTANCE.createOccurrenceSpecification();
//		oSF.setEnclosingInteraction(myInteraction);
//		oSF.setCovered(caller);
//		oSF.setName("ocurrenceSpecificationFinish");
//		
//		ActionExecutionSpecification aES = UMLFactory.eINSTANCE.createActionExecutionSpecification();
//		aES.setEnclosingInteraction(myInteraction);
//		aES.setStart(oSS);
//		aES.setFinish(oSF);
//		aES.setName("ActionExecutionSpecification1");
		
		MessageOccurrenceSpecification invStart1 = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
		invStart1.setEnclosingInteraction(myInteraction);
		invStart1.setName("invocationStart1");
		invStart1.setMessage(m);
		invStart1.setCovered(caller);
		MessageOccurrenceSpecification execStart1 = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
		execStart1.setEnclosingInteraction(myInteraction);
		execStart1.setName("executionStart1");
		execStart1.setMessage(m);
		execStart1.setCovered(target);
		
		BehaviorExecutionSpecification invBody1 = UMLFactory.eINSTANCE.createBehaviorExecutionSpecification();
		invBody1.setEnclosingInteraction(myInteraction);
		invBody1.setName("invocationBody1");
		invBody1.setStart(invStart1);
		
		BehaviorExecutionSpecification execBody1 = UMLFactory.eINSTANCE.createBehaviorExecutionSpecification();
		execBody1.setEnclosingInteraction(myInteraction);
		execBody1.setName("executionBody1");
		execBody1.setStart(execStart1);
		
		
		MessageOccurrenceSpecification invFinish1 = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
		invFinish1.setEnclosingInteraction(myInteraction);
		invFinish1.setName("invocationFinish1");
		invFinish1.setCovered(caller);
		MessageOccurrenceSpecification execFinish1 = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
		execFinish1.setEnclosingInteraction(myInteraction);
		execFinish1.setName("executionFinish1");
		execFinish1.setCovered(target);
		
		invBody1.setFinish(invFinish1);
		execBody1.setFinish(execFinish1);
		
//		ExecutionOccurrenceSpecification a = UMLFactory.eINSTANCE.createExecutionOccurrenceSpecification();
//		a.setEnclosingInteraction(myInteraction);
//		a.setName("executionBody2");
//		a.setExecution(execBody1);
//		a.setCovered(target);
		
		
		
		
		
		
		
//		MessageOccurrenceSpecification invStart1 = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
//		invStart1.setEnclosingInteraction(myInteraction);
//		invStart1.setName("invocationStart1");
//		invStart1.setMessage(m);
//		invStart1.setCovered(caller);
//		MessageOccurrenceSpecification invFinish1 = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
//		invFinish1.setEnclosingInteraction(myInteraction);
//		invFinish1.setName("invocationFinish1");
//		invFinish1.setCovered(caller);
//		BehaviorExecutionSpecification invBody1 = UMLFactory.eINSTANCE.createBehaviorExecutionSpecification();
//		invBody1.setEnclosingInteraction(myInteraction);
//		invBody1.setName("invocationBody1");
//		invBody1.setStart(invStart1);
//		invBody1.setFinish(invFinish1);
//		
//		
//		
//		
//		
//		MessageOccurrenceSpecification execStart1 = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
//		execStart1.setEnclosingInteraction(myInteraction);
//		execStart1.setName("executionStart1");
//		execStart1.setMessage(m);
//		execStart1.setCovered(target);
//		MessageOccurrenceSpecification execFinish1 = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
//		execFinish1.setEnclosingInteraction(myInteraction);
//		execFinish1.setName("executionFinish1");
//		execFinish1.setCovered(target);
//		BehaviorExecutionSpecification execBody1 = UMLFactory.eINSTANCE.createBehaviorExecutionSpecification();
//		execBody1.setEnclosingInteraction(myInteraction);
//		execBody1.setName("executionBody1");
//		execBody1.setStart(execStart1);
//		execBody1.setFinish(execFinish1);
//		ExecutionOccurrenceSpecification a = UMLFactory.eINSTANCE.createExecutionOccurrenceSpecification();
//		a.setEnclosingInteraction(myInteraction);
//		a.setName("executionBody2");
//		a.setExecution(execBody1);
//		a.setCovered(target);
		
		
		
		m.setSendEvent(invStart1);
		m.setReceiveEvent(execStart1);
		m.setInteraction(myInteraction);
		
		
		
//		ExecutionSpecification eS = UMLFactory.eINSTANCE.createExecutionOccurrenceSpecification().getExecution();
//		myInteraction.createFragment("invocation-1-start", eS.eClass());
//		UMLPackage uPackage = UMLFactory.eINSTANCE.createMessage();
//		MessageEnd mE = org.eclipse.uml2.uml.UMLPackage.getMessageEnd();
		
//		Model myModel = createModel("sequenceTest");
		URI outputURI = URI.createFileURI("../ReverseJ/files/diagramStudy")
		.appendFileExtension(UMLResource.FILE_EXTENSION);
		save(myPackage, outputURI);
	} 

	//
	// Model-building utilities
	//
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
		// Create a resource-set to contain the resource(s) that we are saving
		// Initialize registrations of resource factories, library models,
		// profiles, Ecore metadata, and other dependencies required for
		// serializing and working with UML resources. This is only necessary in
		// applications that are not hosted in the Eclipse platform run-time, in
		// which case these registrations are discovered automatically from
		// Eclipse extension points.
		UMLResourcesUtil.init(resourceSet);

		// Create the output resource and add our model package to it.
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(package_);

		// And save
		try {
			resource.save(null); // no save options needed
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
