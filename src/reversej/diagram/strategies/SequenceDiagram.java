package reversej.diagram.strategies;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.eclipse.uml2.uml.Package;

import reversej.TestUtilities;
import reversej.diagram.DiagramStrategy;
import reversej.diagram.Information;
import reversej.diagram.ModelAdapter;
import reversej.diagram.informationmodel.IClass;
import reversej.diagram.informationmodel.IMethod;
import reversej.diagram.informationmodel.IParameters;
import reversej.diagram.informationmodel.IReturn;
import reversej.diagram.informationmodel.InformationFactoryImpl;
import reversej.diagram.strategies.uml2adapter.AdapterSequenceToUml2;

public class SequenceDiagram implements DiagramStrategy {
	private final String void_ = "void";
	private AdapterSequenceToUml2 adapter;
	private List<String> attributeLifelines;
	private LinkedList<MessageOccurrence> attributeMessages;

	public SequenceDiagram(AdapterSequenceToUml2 frameworkAdapter) {
		setAttributes(frameworkAdapter);
	}
	public SequenceDiagram() {
		setAttributes(AdapterSequenceToUml2.make());
	}

	private void setAttributes(AdapterSequenceToUml2 frameworkAdapter) {
		this.adapter = frameworkAdapter;
		this.attributeLifelines = new LinkedList<String>();
		this.attributeMessages = new LinkedList<MessageOccurrence>();
	}

	@Override
	public Package generate(List<Information> informations) {
		if(informations != null && !informations.isEmpty()){
			informations = addFooterAndHeader(informations);
			listLifelines(informations);
			arrangeMessages(informations);
			generateLifelines();
			generateMessages();
		}			
		return adapter.getPackage();
	}
	private List<Information> addFooterAndHeader(List<Information> informations) {
		List<Information> header = new LinkedList<Information>();
		List<Information> footer = new LinkedList<Information>();
		header.add(InformationFactoryImpl.createClass("StartOfFeature"));
//		header.add(InformationFactoryImpl.createModifiers(""));
//		header.add(InformationFactoryImpl.createMethod("StartOfAplication"));
//		header.add(InformationFactoryImpl.createParameters(""));
//		footer.add(InformationFactoryImpl.createReturn("void"));
		List<Information> newInformations = new LinkedList<Information>();
		newInformations.addAll(header);
		newInformations.addAll(informations);
		newInformations.addAll(footer);
		return newInformations;
	}
	@Override
	public ModelAdapter getAdapter() {
		return adapter;
	}
	
	private void generateMessages() {
		for (MessageOccurrence m : attributeMessages)
			adapter.createMessage(m.getCaller(), m.getMessage(), m.getTarget());
	}

	private void generateLifelines() {
		for (String lifeline : attributeLifelines)
			adapter.createLifeline(lifeline);
	}

	private void listLifelines(List<Information> informations) {
		for (Information information : informations)
			if (information instanceof IClass && !attributeLifelines.contains(information.getValue()))
					attributeLifelines.add(information.getValue());
	}
	private void arrangeMessages(List<Information> informations) {
		Stack<String> classes = new Stack<String>();		
		String method = null;
		
		for (Information information : informations){
			
			if(information instanceof IClass)
				classes.push(information.getValue());
			
			else if(information instanceof IMethod)
				method = information.getValue();
			
			else if(information instanceof IParameters)
				attributeMessages.add(mountMessage(classes, signature(method, information)));			
			
			else if(information instanceof IReturn)
				if(information.getValue() == void_)
					classes.pop();
				else
					addReturnMessage(classes, information);
		}
	}

	private void addReturnMessage(Stack<String> classes, Information returnValue) {
		String replier = classes.pop();
		String receiver = classes.empty()? "" : classes.peek();
		MessageOccurrence r = new MessageOccurrence(replier,returnValue.getValue(),receiver);
		attributeMessages.add(r);
	}

	private String signature(String method, Information parameters) {
		return method + "("+ parameters.getValue() + ")";
	}

	private MessageOccurrence mountMessage(Stack<String> classes, String message) {
		String target = classes.pop();
		String caller = classes.empty()?null:classes.peek();
		classes.push(target);
		MessageOccurrence m = new MessageOccurrence(caller, message, target);
		return m;
	}
	
	private class MessageOccurrence{
		private String caller;
		private String message;
		private String target;

		private MessageOccurrence(String caller, String message, String target){
			this.caller = caller;
			this.message = message;
			this.target = target;
		}
		public String getCaller() {return caller; }
		public String getMessage(){return message;}
		public String getTarget() {return target; }
	}
}
