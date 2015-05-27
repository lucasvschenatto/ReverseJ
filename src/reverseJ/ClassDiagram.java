package reverseJ;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ClassDiagram implements DiagramStrategy {
	private final String constructorCall = "<init>";
	private ClassDiagramUtilities diagramUtilities;
	
	public ClassDiagram(ClassDiagramUtilities utilities) {
		diagramUtilities = utilities;
	}

	@Override
	public void generate(List<Information> informations) {
		generateTypes(informations);
		generateClasses(informations);		
		generateInterfaces(informations);
		generateImplementations(informations);
		generateAssociations(informations);
		generateDependencies(informations);
		generateMethods(informations);
	}

	private void generateDependencies(List<Information> informations) {
		List<String>[] dependencies = getDependencyPairs(informations);
		for (int i = 0; i < dependencies[0].size(); i++) {
			String caller = dependencies[0].get(i);
			String target = dependencies[1].get(i);
			diagramUtilities.createDependency(caller, target);
		}
	}

	private List<String>[] getDependencyPairs(List<Information> informations) {
		@SuppressWarnings("unchecked")
		List<String>[] dependencies = (List<String>[])new List[]{
			new LinkedList<String>(), new LinkedList<String>()};
		
		Information lastCaller = null, lastTarget = null;
		
		for (Information information : informations) {
			if(information instanceof ICaller)
				lastCaller = information;
			else if(information instanceof ITarget)
				lastTarget = information;
			else if(information instanceof IMethod && lastCaller != null && lastTarget != null){
				//DEPENDENCY IS NOT CONSTRUCTOR_CALL
				if(information.getValue() != constructorCall){
					if (!(dependencies[0].contains(lastCaller.getValue())
							&& dependencies[1].contains(lastTarget.getValue()))){
						dependencies[0].add(lastCaller.getValue());
						dependencies[1].add(lastTarget.getValue());
					}
				}
			}
		}		
		return dependencies;
	}

	private void generateAssociations(List<Information> informations) {
		List<String>[] a = getAssociationPairs(informations);
		generateUnidirectionalAssociations(a);
		generateBiDirectionalAssociations(a);
	}

	private void generateUnidirectionalAssociations(List<String>[] associations) {
		associations = removeBiDirectionals(associations);		
		for (int i = 0; i < associations[0].size(); i++) {
			String caller = associations[0].get(i);
			String target = associations[1].get(i);
			diagramUtilities.createUnidirectionalAssociation(caller, target);
		}
	}

	private void generateBiDirectionalAssociations(List<String>[] associations) {
		List<String>[] biDirectionals = getBidirectionals(associations);
		for (int i = 0; i < biDirectionals[0].size(); i++) {
			String caller = biDirectionals[0].get(i);
			String target = biDirectionals[1].get(i);
			diagramUtilities.createBiDirectionalAssociation(caller, target);
		}
	}
	
	private List<String>[] getBidirectionals(List<String>[] associations) {
		@SuppressWarnings("unchecked")
		List<String>[] bidirectionals = (List<String>[])new List[]{
			new LinkedList<String>(), new LinkedList<String>()};
		
		for (int i = 0; i < associations[0].size(); i++) {
			String caller1 = associations[0].get(i);
			String target1 = associations[1].get(i);
			for(int j = i+1; j < associations[0].size();j++){
				String caller2 = associations[0].get(j);
				String target2 = associations[1].get(j);
				if(caller1 == target2 && target1 == caller2){
					bidirectionals[0].add(caller1);
					bidirectionals[1].add(target1);
				}
			}
		}
		return bidirectionals;
	}

	private List<String>[] removeBiDirectionals(List<String>[] associations) {
		List<String>[] unidirectionals = cloneAssociations(associations);
		
		for (int i = 0; i < unidirectionals[0].size(); i++) {
			String caller1 = unidirectionals[0].get(i);
			String target1 = unidirectionals[1].get(i);
			boolean isBidirectional = false;
			for(int j = i+1; j < unidirectionals[0].size();j++){
				String caller2 = unidirectionals[0].get(j);
				String target2 = unidirectionals[1].get(j);
				if(caller1 == target2 && target1 == caller2){
					unidirectionals[0].remove(caller2);
					unidirectionals[1].remove(target2);
					isBidirectional = true;
				}
			}
			if(isBidirectional){
				unidirectionals[0].remove(caller1);
				unidirectionals[1].remove(target1);
			}
		}
		return unidirectionals;
	}

	private List<String>[] cloneAssociations(List<String>[] associations) {
		@SuppressWarnings("unchecked")
		List<String>[] cloned = (List<String>[])new List[]{
			new LinkedList<String>(), new LinkedList<String>()};
		for(String caller: associations[0])
			cloned[0].add(caller);
		for(String target: associations[1])
			cloned[1].add(target);
		return cloned;
	}

	private List<String>[] getAssociationPairs(List<Information> informations){	
		@SuppressWarnings("unchecked")
		List<String>[] associations = (List<String>[])new List[]{
			new LinkedList<String>(), new LinkedList<String>()};
		
		Information lastCaller = null, lastTarget = null;
		
		for (Information information : informations) {
			if(information instanceof ICaller)
				lastCaller = information;
			else if(information instanceof ITarget)
				lastTarget = information;
			else if(information instanceof IMethod && lastCaller != null && lastTarget != null){
				//ASSOCIATION IS CONSTRUCTOR_CALL
				if(information.getValue() == constructorCall){
					if (!(associations[0].contains(lastCaller.getValue())
							&& associations[1].contains(lastTarget.getValue()))){
						associations[0].add(lastCaller.getValue());
						associations[1].add(lastTarget.getValue());
					}
				}
			}
		}		
		return associations;
	}

	private void generateImplementations(List<Information> informations) {
		Information last = null;
		for (Information information : informations) {
			if(information instanceof ITarget && last instanceof IInterface)
				diagramUtilities.createImplementation(last.getValue(),information.getValue());
			last = information;
		}
		
	}

	private void generateTypes(List<Information> informations) {
		List<String> types = new LinkedList<String>();
		for (Information information : informations){
			if(information instanceof ISignature){
				String signature = removeParenthesis(information.getValue());				
				List<String> parameters = Arrays.asList(signature.split(","));
				for (String parameter : parameters){
					String type = (parameter.trim().split(" "))[0];
					if(!types.contains(type))
						types.add(type);
				}
			}else if(information instanceof IReturn){
				if(!types.contains(information.getValue()))
					types.add(information.getValue());
			}
		}
		for (String type : types) {
			diagramUtilities.createType(type);
		}
	}

	private String removeParenthesis(String value) {
		String s = value.replace('(', ' ');
		s = s.replace(')', ' ');
		s = s.trim();
		return s;
	}

	private void generateMethods(List<Information> informations) {
		informations = moveNestedMethodsToFirstLayer(informations);
		List<String> methodNames       = new LinkedList<String>();
		List<String> containingClasses = new LinkedList<String>();
		List<String> signatures        = new LinkedList<String>();
		List<String> returnTypes       = new LinkedList<String>();
		for (Information information : informations) {
			if(information instanceof ITarget)
				containingClasses.add(information.getValue());
			if(information instanceof IMethod)
				methodNames.add(information.getValue());
			if(information instanceof ISignature)
				signatures.add(information.getValue());
			if(information instanceof IReturn)
				returnTypes.add(information.getValue());
		}
		Iterator<String> cIterator = containingClasses.iterator();
		Iterator<String> mIterator = methodNames.iterator();
		Iterator<String> sIterator = signatures.iterator();
		Iterator<String> rIterator = returnTypes.iterator();
		while (cIterator.hasNext() && mIterator.hasNext() && sIterator.hasNext()) {
			if(rIterator.hasNext())
				diagramUtilities.createMethodWithReturn(cIterator.next(),mIterator.next(),sIterator.next(), rIterator.next());
			else
				diagramUtilities.createMethod(cIterator.next(),mIterator.next(),sIterator.next());
		}
	}

	private List<Information> moveNestedMethodsToFirstLayer(
			List<Information> informations) {		
		Information last = null;
		TreeNode tree = new TreeNode();
		for (Information information : informations) {
			if(information instanceof ICaller){
				if(last instanceof ISignature || last instanceof IReturn || last == null){
					tree = tree.addChild(information);
				}else{
					tree = tree.getParent();
					tree = tree.addChild(information);
				}
			}else if(information instanceof IReturn && last instanceof IReturn){
					tree = tree.getParent();
					tree.addInfo(information);
			}else{
				tree.addInfo(information);
			}
			last = information;
		}
		tree = tree.getRoot();
		return tree.getAllInfoInTree();
	}

	private void generateInterfaces(List<Information> informations) {
		List<String> interfaceNames = new LinkedList<String>();
		for (Information information : informations) {
			if(information instanceof IInterface)
				interfaceNames.add(information.getValue());
		}
		interfaceNames = removeDuplicated(interfaceNames);
		for (String interfaceName : interfaceNames){
			diagramUtilities.createInterface(interfaceName);
		}
	}

	private void generateClasses(List<Information> informations) {
		List<String> classNames = new LinkedList<String>();
		for (Information information : informations) {
			if(information instanceof ICaller || information instanceof ITarget
				|| information instanceof IHandler)
				classNames.add(information.getValue());
		}
		classNames = removeDuplicated(classNames);
		for (String className : classNames){
			diagramUtilities.createConcreteClass(className);
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

	@Override
	public ClassDiagramUtilities getUtil() {
		return diagramUtilities;
	}
}
