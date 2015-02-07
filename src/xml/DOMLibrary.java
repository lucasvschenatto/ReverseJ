package xml;

import java.io.*;
import java.util.*;
import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.xml.parsers.*;

//import org.w3c.dom.*;
import org.xml.sax.*;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;



public class DOMLibrary {
	private static DOMLibrary theLib;

	public synchronized static DOMLibrary eINSTANCE() {
		if (theLib == null)
			theLib = new DOMLibrary();
		return theLib;
	}

	// instance variables below this
	private Hashtable<String, Document> domHash;

	private String lastErr = "none";

	// private constructor to ensure singleton
	private DOMLibrary() {
		domHash = new Hashtable<String, Document>();
	}

	// retorna ou um Document ou uma String se acontecer algum erro
	private Object loadXML(String src, boolean validate) {
		File xmlFile = new File(src);
		String err = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(validate);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = (Document) db.parse(xmlFile);
			return doc;
		} catch (ParserConfigurationException pce) {
			err = pce.toString();
		} catch (SAXParseException spe) {
			StringBuffer sb = new StringBuffer(spe.toString());
			sb.append("\n  Line number: " + spe.getLineNumber());
			sb.append("\nColumn number: " + spe.getColumnNumber());
			sb.append("\n Public ID: " + spe.getPublicId());
			sb.append("\n System ID: " + spe.getSystemId() + "\n");
			err = sb.toString();
		} catch (SAXException se) {
			err = se.toString();
			if (se.getException() != null) {
				err += " caused by: " + se.getException().toString();
			}
		} catch (IOException ie) {
			err = ie.toString();
		}
		return err;
	} // end loadXML

	/*
	 * Outras classes acessam documentos na biblioteca DOM chamando o método
	 * getDOM mostrado no código abaixo. Este método é chamado passando uma
	 * String com a localização do arquivo XML. Se este arquivo já sofreu
	 * parsing, a referência para o objeto Document é retornada. Caso contrário,
	 * o arquivo sofrerá o parsing através da chamada do método loadXML.
	 */

	// ou retorna um Document ou null se tiver problemas
	public synchronized Document getDOM(String src, boolean validate) {
		Document doc = new Document();
		doc = (Document) domHash.get(src);
		if (doc == null) {
			System.out.println("DOMlibrary.getDOM new " + src);
			doc = (Document) loadXML(src, validate);
			domHash.put(src, doc);
			// if (doc instanceof String) {
			// lastErr = (String) doc;
			// }
		}
		// se não for um documento, então deve ser uma String de erro
		// if (!(doc instanceof Document)) {
		// return (Document) doc;
		// }
		return doc;
	}

	// utilize isto para forçar a remoção de um DOM. Retorna
	// a última cópia do DOM ou null caso não exista
	public synchronized Document removeDOM(String src) {
		Document dom = (Document) domHash.get(src);
		if (dom != null) {
			domHash.remove(src);
			// System.out.println("Removed " + src );
		}
		return dom;
	}

	// utilize isto para forçar uma atualização de um DOM
	public synchronized Document reloadDOM(String src, boolean validate) {
		if (domHash.get(src) != null) {
			domHash.remove(src);
		}
		return getDOM(src, validate);
	}

	public String getLastErr() {
		return lastErr;
	}

	/**
	 * Cria um XML onde os nodes são:
	 *  1.
	 * */
	public void createClassDiagramModelXML(String location, 
			HashMap<String, ArrayList<String>> relationshipList) {

		Element classDiagram = new Element("ClassDiagram");
		
		/**
		 * 
		 * */
		ArrayList<String> classList = new ArrayList<String>();

		for (String name : relationshipList.keySet()) {

			System.out.println("class created: " + name);
			Element classes = new Element("class");
			classes.setAttribute(new Attribute("name", name));
			classDiagram.addContent(classes);
			
			//Adicionar na lista de classe
			classList.add(name);

		}

		// Cria todos os relacionamento no XML
		for (String key : relationshipList.keySet()) {
			for (Iterator<?> iterator = relationshipList.get(key).iterator(); iterator
					.hasNext();) {
				String target = (String) iterator.next();
				Element relationship = new Element("relationship");
				relationship.setAttribute(new Attribute("source", key));
				relationship.setAttribute(new Attribute("target", target));
				classDiagram.addContent(relationship);
				
				if(!classList.contains(target)){
					System.out.println("class created: " + target);
					Element classes = new Element("class");
					classes.setAttribute(new Attribute("name", target));
					classDiagram.addContent(classes);
					//Adicionar na lista de classe
					classList.add(target);
				}
			}
		}

		Document doc = new Document();
		doc.setRootElement(classDiagram);

		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(location), "UTF8"));

			XMLOutputter xout = new XMLOutputter();
			xout.output((org.jdom2.Document) doc, out);

			System.out.println("XML criado com sucesso!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * */
	public void createClassDiagramXML(
			HashMap<String, ArrayList<String>> relationshipList) {

		Element classDiagram = new Element("ClassDiagram");

		for (String name : relationshipList.keySet()) {

			System.out.println("class created: " + name);
			Element classes = new Element("class");
			classes.setAttribute(new Attribute("name", name));
			classDiagram.addContent(classes);

		}

		// Cria todos os relacionamento no XML
		for (String key : relationshipList.keySet()) {
			for (Iterator<?> iterator = relationshipList.get(key).iterator(); iterator
					.hasNext();) {
				String target = (String) iterator.next();
				Element relationship = new Element("relationship");
				relationship.setAttribute(new Attribute("source", key));
				relationship.setAttribute(new Attribute("target", target));
				classDiagram.addContent(relationship);
			}
		}

		Document doc = new Document();
		doc.setRootElement(classDiagram);

		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("files/ClassDiagramOutput.xml"), "UTF8"));

			XMLOutputter xout = new XMLOutputter();
			xout.output((org.jdom2.Document) doc, out);

			System.out.println("XML criado com sucesso!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Document getDcoumentFromXML(String str) {

		Document doc = null;

		SAXBuilder sb = new SAXBuilder();

		try {
			doc = sb.build(new File(str));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return doc;
	}

	public static void main(String[] args) {
		DOMLibrary domLibrary = new DOMLibrary();

		ArrayList<String> classList = new ArrayList<String>();
		classList.add("C1");
		classList.add("C2");
		classList.add("C3");

		ArrayList<String> c1Relationship = new ArrayList<String>();
		c1Relationship.add("C2");
		c1Relationship.add("C3");

		ArrayList<String> c2Relationship = new ArrayList<String>();
		c2Relationship.add("C3");

		HashMap<String, ArrayList<String>> relationshipList = new HashMap<String, ArrayList<String>>();
		relationshipList.put("C1", c1Relationship);
		relationshipList.put("C2", c2Relationship);

		domLibrary.createClassDiagramXML(relationshipList);

		Document document = new Document();

		document = domLibrary.getDcoumentFromXML("files/ExecutationSequence.xml");

		System.out.println("==" + document.getContent().size());

		List<?> elemInfo2 = document.getContent();
		Iterator<?> elemIterator2 = elemInfo2.iterator();
		while (elemIterator2.hasNext()) {
			Element e = (Element) elemIterator2.next();
			System.out.println(e.getName());

			for (Iterator<?> iterator = e.getChildren().iterator(); iterator
					.hasNext();) {
				Element object = (Element) iterator.next();

				if (object.getName().equalsIgnoreCase("class")) {
					System.out.println("Class:: Name: "
							+ object.getAttributeValue("name"));
					for (Iterator<?> iterator2 = object.getChildren().iterator(); iterator2
							.hasNext();) {
						Element adjacentClass = (Element) iterator2.next();
						System.out.println("--AdjancetClass:: Name: "
								+ adjacentClass.getAttributeValue("name"));
					}
					
				} else {
					System.out.println("-" + object.getName() + ":: source: "
							+ object.getAttributeValue("source") + ", target: "
							+ object.getAttributeValue("target"));
				}

			}

		}

	}

}
