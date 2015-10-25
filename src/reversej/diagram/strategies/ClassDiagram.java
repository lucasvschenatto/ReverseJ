package reversej.diagram.strategies;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.eclipse.uml2.uml.Package;

import reversej.diagram.DiagramStrategy;
import reversej.diagram.Information;
import reversej.diagram.ModelAdapter;
import reversej.diagram.informationmodel.IClass;
import reversej.diagram.informationmodel.IHandler;
import reversej.diagram.informationmodel.IInterface;
import reversej.diagram.informationmodel.IMethod;
import reversej.diagram.informationmodel.IModifiers;
import reversej.diagram.informationmodel.IParameters;
import reversej.diagram.informationmodel.IReturn;
import reversej.diagram.informationmodel.InformationFactoryImpl;
import reversej.diagram.strategies.uml2adapter.AdapterClassToUml2;

public class ClassDiagram implements DiagramStrategy {
	private final String constructorCall = "<init>";
	private final String void_ = "void";
	private AdapterClassToUml2 adapter;
	private List<String> attributeClasses;
	private List<String> attributeInterfaces;
	private List<String> attributeTypes;
	private List<Pair> attributeUnidirectionals;
	private List<Pair> attributeBidirectionals;
	public ClassDiagram(){
		setAttributes(AdapterClassToUml2.make());
	}
	public ClassDiagram(AdapterClassToUml2 frameworkAdapter) {
		setAttributes(frameworkAdapter);
	}
	private void setAttributes(AdapterClassToUml2 frameworkAdapter) {
		adapter = frameworkAdapter;
		attributeClasses = new LinkedList<String>();
		attributeInterfaces = new LinkedList<String>();
		attributeTypes = new LinkedList<String>();
		attributeUnidirectionals = new LinkedList<Pair>();
		attributeBidirectionals = new LinkedList<Pair>();
	}

	@Override
	public Package generate(List<Information> informations) {
		if(informations != null && !informations.isEmpty()){
			generateClasses(informations);		
			generateInterfaces(informations);
			generateImplementations(informations);
			generateAssociations(informations);
			generateDependencies(informations);
			generateTypes(informations);
			generateMethods(informations);	
		}
		return adapter.getPackage();
	}

	private void generateDependencies(List<Information> informations) {
		List<Pair> dependencies = getDependencyPairs(informations);
		dependencies = removeDuplicatedPairs(dependencies);
		dependencies = filterOnlyNonUnidirectionals(dependencies, attributeUnidirectionals);
		dependencies = filterOnlyNonBidirectionals(dependencies, attributeBidirectionals);
		for (Pair pair : dependencies) {
			adapter.createDependency(pair.getFirst(), pair.getSecond());
		}
	}

	private List<Pair> filterOnlyNonBidirectionals(List<Pair> dependencies,
			List<Pair> bidirectionals) {
		dependencies = filterOnlyNonUnidirectionals(dependencies, bidirectionals);
		List<Pair> filtered = new LinkedList<Pair>();
		for (Pair pair : dependencies) {
			String caller1 = pair.getFirst();
			String target1 = pair.getSecond();
			boolean isBidirectionalAssociation = false;
			for (Pair pair2 : bidirectionals) {
				String caller2 = pair2.getFirst();
				String target2 = pair2.getSecond();
				if(caller1.equals(target2) && target1.equals(caller2)){
					isBidirectionalAssociation = true;
				}
			}
			if(!isBidirectionalAssociation)
				filtered.add(pair);
		}
		
		return filtered;
	}

	private List<Pair> filterOnlyNonUnidirectionals(List<Pair> dependencies,List<Pair> unidirectionals) {
		List<Pair> filtered = new LinkedList<Pair>();
		for (Pair pair : dependencies) {
			String caller1 = pair.getFirst();
			String target1 = pair.getSecond();
			boolean isUnidirectionalAssociation = false;
			for (Pair pair2 : unidirectionals) {
				String caller2 = pair2.getFirst();
				String target2 = pair2.getSecond();
				if(caller1.equals(caller2) && target1.equals(target2)){
					isUnidirectionalAssociation = true;
				}
			}
			if (!isUnidirectionalAssociation)
				filtered.add(pair);
		}
		return filtered;
	}

	private List<Pair> getDependencyPairs(List<Information> informations) {
		List<Pair> dependencies = new LinkedList<Pair>();
		Stack<String> stack = new Stack<String>();
		boolean isMethod = false;
		Information last = InformationFactoryImpl.createEmpty();
		
		for (Information information : informations) {
			if(information instanceof IInterface)
				stack.push(information.getValue());
			else if(information instanceof IClass && !(last instanceof IInterface))
				stack.push(information.getValue());
			else if(information instanceof IMethod)
				isMethod = (information.getValue() != constructorCall)? true:false;
			else if(information instanceof IReturn){
				String poped = stack.pop();
				if(!stack.empty()){
					if(isMethod){
						dependencies.add(new Pair(stack.peek(), poped));
					}
				}
			}
			last = information;
		}		
		return dependencies;
	}

	private void generateAssociations(List<Information> informations) {
		List<Pair> a = getAssociationPairs(informations);
		a = removeDuplicatedPairs(a);
		generateUnidirectionalAssociations(a);
		generateBiDirectionalAssociations(a);
	}

	private void generateUnidirectionalAssociations(List<Pair> associations) {
		associations = removeBiDirectionals(associations);
		for (Pair pair : associations) {
			adapter.createUnidirectionalAssociation(pair.getFirst(), pair.getSecond());
		}
		attributeUnidirectionals = associations;
	}

	private void generateBiDirectionalAssociations(List<Pair> associations) {
		List<Pair> bidirectionals = removeUniDirectionals(associations);
		bidirectionals = removeDuplicatedBiDirectionals(bidirectionals);
		for (Pair pair : bidirectionals)
			adapter.createBidirectionalAssociation(pair.getFirst(), pair.getSecond());
		attributeBidirectionals = bidirectionals;
	}
	
	private List<Pair> removeUniDirectionals(List<Pair> associations) {
		List<Pair> bidirectionals = new LinkedList<Pair>();
		
		for (int i = 0; i < associations.size(); i++) {
			Pair pair1 = associations.get(i);
			String caller1 = pair1.getFirst();
			String target1 = pair1.getSecond();
			for(int j = i+1; j < associations.size();j++){
				Pair pair2 = associations.get(j);
				String caller2 = pair2.getFirst();
				String target2 = pair2.getSecond();
				if(caller1.equals(target2) && target1.equals(caller2)){
					bidirectionals.add(pair1);
				}
			}
		}
		return bidirectionals;
	}

	private List<Pair> removeBiDirectionals(List<Pair> associations) {
		List<Pair> unidirectionals = cloneAssociations(associations);
		
		for (int i = 0; i < unidirectionals.size(); i++) {
			Pair pair1 = unidirectionals.get(i);
			String caller1 = pair1.getFirst();
			String target1 = pair1.getSecond();
			boolean isBidirectional = false;
			for(int j = i+1; j < unidirectionals.size();j++){
				Pair pair2 = unidirectionals.get(j);
				String caller2 = pair2.getFirst();
				String target2 = pair2.getSecond();
				if(caller1.equals(target2) && target1.equals(caller2)){
					unidirectionals.remove(pair2);
					isBidirectional = true;
				}
			}
			if(isBidirectional)
				unidirectionals.remove(pair1);
		}
		return unidirectionals;
		}

	private List<Pair> cloneAssociations(List<Pair> associations) {
		List<Pair> cloned = new LinkedList<Pair>();
		for (Pair pair : associations)
			cloned.add(new Pair(pair.getFirst(),pair.getSecond()));
		return cloned;
	}

	private List<Pair> getAssociationPairs(List<Information> informations){
		List<Pair> associations = new LinkedList<Pair>();
		
		Stack<String> stack = new Stack<String>();
		boolean isConstructor = false;
		
		for (Information information : informations) {
			if(information instanceof IClass)
				stack.push(information.getValue());
			else if(information instanceof IMethod)
				isConstructor = (information.getValue() == constructorCall)? true:false;
			else if(information instanceof IReturn){
				String poped = stack.pop();
				if(!stack.empty()){					 
					if(isConstructor){
						associations.add(new Pair(stack.peek(), poped));
					}
				}
			}
		}
		return associations;
	}

	private void generateImplementations(List<Information> informations) {
		Information last = null;
		for (Information information : informations) {
			if(information instanceof IClass && last instanceof IInterface)
				adapter.createImplementation(last.getValue(),information.getValue());
			last = information;
		}
		
	}

	private void generateTypes(List<Information> informations) {
		List<String> types = new LinkedList<String>();
		for (Information information : informations){
			if(information instanceof IParameters){
				String params = information.getValue();				
				List<String> parametersList = Arrays.asList(params.split(","));
				for (String parameter : parametersList){
					if(!parameter.isEmpty()){
						String type = (parameter.trim().split(" "))[0];
						if(!types.contains(type))
							types.add(type);
					}
				}
			}else if(information instanceof IReturn){
				String type = information.getValue();
				if(!types.contains(type) && !type.equals(void_))
					types.add(type);
			}
		}
		types = removeDuplicated(types);
		types.removeAll(attributeClasses);
		types.removeAll(attributeInterfaces);
		for (String type : types) {
			adapter.createType(type);
			attributeTypes.add(type);
		}
	}

	private void generateMethods(List<Information> informations) {
		TreeNode tree = createMethodTree(informations);
		generateMethodsFromTree(tree);
	}

	private void generateMethodsFromTree(TreeNode tree) {
		List<Information> methodInformation = tree.getNodeInfo();
		if(!tree.getNodeInfo().isEmpty()){
			if (!(methodInformation.get(methodInformation.size()-1) instanceof IReturn)){
				/*AspectJ does not capture super calls to non-static methods.
				 It is necessary to add a return aftertime*/
				methodInformation.add(InformationFactoryImpl.createError());
			}
//			if ((!(methodInformation.get(0) instanceof IInterface)&&!(methodInformation.get(0) instanceof IClass))
//					||!(methodInformation.get(1) instanceof IModifiers)
//					||!(methodInformation.get(2) instanceof IMethod)
//					||!(methodInformation.get(3) instanceof IParameters)
//					||!(methodInformation.get(4) instanceof IReturn)){
//				System.out.println("wrong------------start");
//				for(Information info : methodInformation)
//					System.out.println(info.describe());
//				System.out.println("wrong------------end");
//			}
			if (methodInformation.get(4).getValue()==void_)
				adapter.createMethod(methodInformation.get(0).getValue(), methodInformation.get(2).getValue(), methodInformation.get(3).getValue());
			else
				adapter.createMethodWithReturn(methodInformation.get(0).getValue(), methodInformation.get(2).getValue(), methodInformation.get(3).getValue(),methodInformation.get(4).getValue());
		}
						
			
		for (TreeNode child : tree.getChildren()) {
			generateMethodsFromTree(child);
		}
	}
	private TreeNode createMethodTree(
			List<Information> informations) {
		TreeNode tree = new TreeNode();
		Information last = null;
		for(Information information :informations){
			if(information instanceof IClass){
				if(last == null || last instanceof IInterface){
					tree.addInfo(information);
				}else
					tree = tree.addChild(information);
			}
			else if(information instanceof IInterface)
				tree = tree.addChild(information);
			else if(information instanceof IReturn){
				tree.addInfo(information);
				tree = (tree.getParent() == null)? tree:tree.getParent();
			}
			else
				tree.addInfo(information);
			last = information;
		}
		tree = inferInterfaceMethods(tree);
		return tree;
	}

	private TreeNode inferInterfaceMethods(TreeNode tree) {
		for (TreeNode node : tree.getChildren()){
			List<Information> nodeInfo = node.getNodeInfo();
			if(nodeInfo.get(0) instanceof IInterface){
				TreeNode child = node.addChild(nodeInfo.get(1));
				for (int i = 2; i < nodeInfo.size(); i++)
					child.addInfo(nodeInfo.get(i));
				nodeInfo.remove(1);
			}
			inferInterfaceMethods(node);
		}		
		return tree;
	}
	private void generateInterfaces(List<Information> informations) {
		List<String> interfaceNames = new LinkedList<String>();
		for (Information information : informations) {
			if(information instanceof IInterface)
				interfaceNames.add(information.getValue());
		}
		interfaceNames = removeDuplicated(interfaceNames);
		for (String interfaceName : interfaceNames){
			adapter.createInterface(interfaceName);
			attributeInterfaces.add(interfaceName);
		}
	}

	private void generateClasses(List<Information> informations) {
		List<String> classNames = new LinkedList<String>();
		for (Information information : informations) {
			if(information instanceof IClass
				|| information instanceof IHandler)
				classNames.add(information.getValue());
		}
		classNames = removeDuplicated(classNames);
		for (String className : classNames){
			adapter.createConcreteClass(className);
			attributeClasses.add(className);
		}
	}
	
	private List<String> removeDuplicated(List<String> classesNames) {
		String previous = null;
		classesNames.sort(null);
		for (Iterator<String> iterator = classesNames.iterator(); iterator.hasNext();) {
			String current = iterator.next();
			if( current.equals(previous))
				iterator.remove();
			previous = current;
		}
		return classesNames;
	}
	private List<Pair> removeDuplicatedBiDirectionals(List<Pair> pairs) {
		pairs = removeDuplicatedPairs(pairs);
		for (int i = 0; i < pairs.size(); i++) {
			Pair pair = pairs.get(i);
			String caller1 = pair.getFirst();
			String target1 = pair.getSecond();
			for(int j = i+1; j < pairs.size();j++){
				Pair pair2 = pairs.get(j);
				String caller2 = pair2.getFirst();
				String target2 = pair2.getSecond();
				if(caller1.equals(target2) && target1.equals(caller2)){
					pairs.remove(pair2);
				}
			}
		}
		return pairs;
	}
	private List<Pair> removeDuplicatedPairs(List<Pair> pairs) {
		for (int i = 0; i < pairs.size(); i++) {
			Pair pair1 = pairs.get(i);
			String caller1 = pair1.getFirst();
			String target1 = pair1.getSecond();
			for (int j = i+1; j < pairs.size();j++){
				Pair pair2 = pairs.get(j);
				String caller2 = pair2.getFirst();
				String target2 = pair2.getSecond();
				if(caller1.equals(caller2) && target1.equals(target2)){
					pairs.remove(pair2);
				}
			}
		}
		return pairs;
	}

	@Override
	public ModelAdapter getAdapter() {
		return adapter;
	}
	protected class Pair{
		private String first;
		private String second;
		Pair(String first, String second){
			this.first = first;
			this.second = second;
		}
		public String getFirst(){
			return first;
		}
		public String getSecond(){
			return second;
		}
	}
}
