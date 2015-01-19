package diagram;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author kleinner 
 *  Classe não está sendo usada.
 */
public class ClassDiagram {

	public static HashMap<String, ArrayList<String>> classRelationship;

	public ClassDiagram(){
		classRelationship = new HashMap<String, ArrayList<String>>();
	}
	

	public void insertRelationshipClass(String key, ArrayList<String> value) {
		ClassDiagram.classRelationship.put(key, value);
	}

	public void insertModelElementAdjacencyList(String source, String adjacent) {
		ArrayList<String> adjacentList = new ArrayList<String>();
		System.out.println("==>" + classRelationship.size());

		if (ClassDiagram.classRelationship.containsKey(source)) {
			adjacentList = ClassDiagram.classRelationship.get(source);
			adjacentList.add(adjacent);
			ClassDiagram.classRelationship.put(source, adjacentList);
			System.out
					.println("Elemento inserido - insertModelElementAdjacencyList");
		} else {
			adjacentList.add(adjacent);
			ClassDiagram.classRelationship.put(source, adjacentList);
		}

	}

	public boolean isAdjacent(String source, String target) {

		if (ClassDiagram.classRelationship.containsKey(source)) {
			for (String adjacent : ClassDiagram.classRelationship.get(source)) {
				if (adjacent.equalsIgnoreCase(target))
					return true;
			}
		}

		return false;
	}

	public boolean hasModelElement(String key) {
		return ClassDiagram.classRelationship.containsKey(key);

	}

	/**
	 * Definição dos relacinamento entre as classes
	 * */
	public HashMap<String, ArrayList<String>> createClassRelationship(
			ArrayList<String> arrayClass) {

		int size = arrayClass.size();
		System.out.println("size: "+size);
		//if ((size % 2) == 0) {
		if (((size % 2)==0) && (size > 1)) {
			for (int i = 0; i < size; i += 2) {
				if (!arrayClass.get(i).equalsIgnoreCase(arrayClass.get(i + 1))) {
					if (!isAdjacent(arrayClass.get(i), arrayClass.get(i + 1))
							&& !isAdjacent(arrayClass.get(i + 1), arrayClass
									.get(i))) {
						insertModelElementAdjacencyList(arrayClass.get(i),
								arrayClass.get(i + 1));
					}
				}
			}
			return classRelationship;
		}
		
		if (((size % 2)==1)) {
			System.out.println("if (((size % 2)==1)) {");
			for (int i = 0; i < size-1; i += 2) {
				if (!arrayClass.get(i).equalsIgnoreCase(arrayClass.get(i + 1))) {
					if (!isAdjacent(arrayClass.get(i), arrayClass.get(i + 1))
							&& !isAdjacent(arrayClass.get(i + 1), arrayClass
									.get(i))) {
						insertModelElementAdjacencyList(arrayClass.get(i),
								arrayClass.get(i + 1));
					}
				}
			}
			
			if (!arrayClass.get(size-2).equalsIgnoreCase(arrayClass.get(size-1))) {
				if (!isAdjacent(arrayClass.get(size-1), arrayClass.get(size-2))
						&& !isAdjacent(arrayClass.get(size-1), arrayClass
								.get(size-2))) {
					insertModelElementAdjacencyList(arrayClass.get(size-2),
							arrayClass.get(size-2));
				}
			}
			System.out.println("===>"+classRelationship.size());
			System.out.println("if (((size % 2)==1)) { -- FIM");
			return classRelationship;
		}

		
		return classRelationship;

	}

	public HashMap<String, ArrayList<String>> getClassRelationship() {
		return classRelationship;
	}

	public void setClassRelationship(
			HashMap<String, ArrayList<String>> classRelationship) {
		ClassDiagram.classRelationship = classRelationship;
	}

	public static void main(String[] args) {

		/*ArrayList<String> arrayClass = new ArrayList<String>();
		arrayClass.add("c1");
		arrayClass.add("c2");
		arrayClass.add("c4");
		arrayClass.add("c3");

		ClassDiagram defineModelElements = new ClassDiagram();
		HashMap<String, ArrayList<String>> classRelationship = new HashMap<String, ArrayList<String>>();
		classRelationship = defineModelElements
				.createClassRelationship(arrayClass);

		for (String key : classRelationship.keySet()) {

			System.out.println("#" + key + ":");
			for (Iterator iterator = classRelationship.get(key).iterator(); iterator
					.hasNext();) {
				String element = (String) iterator.next();
				System.out.println("." + element);
			}

		}*/

	}
}
