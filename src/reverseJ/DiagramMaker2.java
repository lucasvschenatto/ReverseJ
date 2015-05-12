package reverseJ;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Interaction;

public class DiagramMaker2 {
	private static ResourceSet resourceSet = new ResourceSetImpl();
	public static void main(String[] args) throws Exception{
		Package myPackage = UMLFactory.eINSTANCE.createPackage();
		myPackage.setName("packageSequenceGoal");
		Interaction myInteraction = UMLFactory.eINSTANCE.createInteraction();
		myInteraction.setName("interactionTest1");
		myInteraction.setPackage(myPackage);
		Lifeline caller = myInteraction.createLifeline("caller");
		Lifeline target = myInteraction.createLifeline("target");
		
		
		ExecutionOccurrenceSpecification eCallerStart = UMLFactory.eINSTANCE.createExecutionOccurrenceSpecification();
		eCallerStart.setCovered(caller);
		eCallerStart.setEnclosingInteraction(myInteraction);
		eCallerStart.setName("startInvocation1");
		
		ExecutionOccurrenceSpecification eTargetStart = UMLFactory.eINSTANCE.createExecutionOccurrenceSpecification();
		eTargetStart.setCovered(target);
		eTargetStart.setEnclosingInteraction(myInteraction);
		eTargetStart.setName("startExecution1");
		
		ExecutionOccurrenceSpecification eTargetFinish = UMLFactory.eINSTANCE.createExecutionOccurrenceSpecification();
		eTargetFinish.setCovered(target);
		eTargetFinish.setEnclosingInteraction(myInteraction);
		eTargetFinish.setName("finishExecution1");
		
		ExecutionOccurrenceSpecification eCallerFinish = UMLFactory.eINSTANCE.createExecutionOccurrenceSpecification();
		eCallerFinish.setCovered(caller);
		eCallerFinish.setEnclosingInteraction(myInteraction);
		eCallerFinish.setName("finishInvocation1");
		
		ActionExecutionSpecification invBody1 = UMLFactory.eINSTANCE.createActionExecutionSpecification();
		invBody1.setEnclosingInteraction(myInteraction);
		invBody1.setName("invocationBody1");
		ActionExecutionSpecification execBody1 = UMLFactory.eINSTANCE.createActionExecutionSpecification();
		execBody1.setEnclosingInteraction(myInteraction);
		execBody1.setName("executionBody1");
		
		eCallerStart.setExecution(invBody1);		
		eCallerFinish.setExecution(invBody1);
		eTargetStart.setExecution(execBody1);
		eTargetFinish.setExecution(execBody1);
		
		invBody1.setStart(eCallerStart);
		invBody1.setFinish(eCallerFinish);
		execBody1.setStart(eTargetStart);
		execBody1.setFinish(eTargetFinish);
		
		save(myPackage);
	}
	
	private static void save(org.eclipse.uml2.uml.Package package_) {
		URI uri = URI.createFileURI("../ReverseJ/files/diagramStudy2")
				.appendFileExtension(UMLResource.FILE_EXTENSION);
		UMLResourcesUtil.init(resourceSet);
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(package_);
		try {
			resource.save(null);
		} catch (IOException ioe) {
		}
	}
}
