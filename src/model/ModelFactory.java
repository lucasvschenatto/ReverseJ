package model;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.jdom2.Document;
import org.jdom2.Element;

import xml.DOMLibrary;

public class ModelFactory {

	public void executar() {

		Model model = ModelElementFactory.eINSTANCE().createModel(
				"ClassDiagram");

		// HashMap das classes que participam do caso de uso
		HashMap<String, Class> createdClassHashMap = new HashMap<String, Class>();

		Document arvore = new Document();
		arvore = DOMLibrary.eINSTANCE().getDcoumentFromXML(
				"files/AdjacentList.xml");
		Iterator listaElementos = arvore.getContent().iterator();
		Element rote = (Element) listaElementos.next();
		Iterator elementList = rote.getChildren().iterator();

		while (elementList.hasNext()) {
			Element node = (Element) elementList.next();
			
			if(node.getName().equalsIgnoreCase("class")){
					createdClassHashMap.put(node.getAttributeValue("name"), ModelElementFactory.eINSTANCE()
							.createClass(model, node.getAttributeValue("name"), false));				
			}		
		}
		
		Iterator element = rote.getChildren().iterator();
		while (element.hasNext()) {
			Element node = (Element) element.next();		
			
			if(node.getName().equalsIgnoreCase("relationship")){
				ModelElementFactory.eINSTANCE().createAssociation(
					createdClassHashMap.get(node.getAttributeValue("source")), false,
					AggregationKind.NONE_LITERAL, " ", 1,
					LiteralUnlimitedNatural.UNLIMITED,
					createdClassHashMap.get(node.getAttributeValue("target")), false,
					AggregationKind.NONE_LITERAL, "", 1, LiteralUnlimitedNatural.UNLIMITED);
			}
			
		}
		ModelElementFactory.eINSTANCE().save(model,	URI.createURI("files/ClassDiagram.uml"));

	}

}
