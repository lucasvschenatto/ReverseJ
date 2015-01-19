package xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jdom2.Document;
import org.jdom2.Element;

/**
 * Fornece a Lista de Adjacência com a definição dos relacionamentos
 * 
 * */

public class AdjacentClassXML {

	public static HashMap<String, ArrayList<String>> listaAdjacencia = new HashMap<String, ArrayList<String>>();

	public void inserirListaAdjacencia(String source, String adjacent) {
		ArrayList<String> adjacentList = new ArrayList<String>();

		imprimirListaAdjacencia();

		if (AdjacentClassXML.listaAdjacencia.containsKey(source)) {
			adjacentList = AdjacentClassXML.listaAdjacencia.get(source);
			adjacentList.add(adjacent);
			AdjacentClassXML.listaAdjacencia.put(source, adjacentList);
		} else {
			adjacentList.add(adjacent);
			AdjacentClassXML.listaAdjacencia.put(source, adjacentList);
		}

		imprimirListaAdjacencia();

	}

	public ArrayList<String> getFilhosPrimario(Element element) {

		ArrayList<String> filhosPrimarioList = new ArrayList<String>();

		Element _element = new Element(element.getAttributeValue("name"));

		for (Iterator<?> iterator = _element.getChildren().iterator(); iterator
				.hasNext();) {
			iterator.next();
		}

		return filhosPrimarioList;

	}

	public void executar() {

		Document arvore = new Document();
		arvore = DOMLibrary.eINSTANCE().getDcoumentFromXML("files/ExecutationSequence.xml");
		Iterator<?> listaElementos = arvore.getContent().iterator();
		Element nodePai = (Element) listaElementos.next();
		Iterator<?> lista = nodePai.getChildren().iterator();
		while (lista.hasNext()) {
			Element node = (Element) lista.next();
			criarLista(node);

		}
		
		DOMLibrary.eINSTANCE().createClassDiagramModelXML("files/AdjacentList.xml",
				listaAdjacencia);

	}

	public void criarLista(Element nodePai) {

		for (Iterator<?> iterator = nodePai.getChildren().iterator(); iterator
				.hasNext();) {
			Element nodeFillho = (Element) iterator.next();

			if (nodePai.getAttributeValue("name").equalsIgnoreCase(
					nodeFillho.getAttributeValue("name"))) {

				if (listaAdjacencia.containsKey(nodePai
						.getAttributeValue("name"))
						|| listaAdjacencia.containsValue(nodePai
								.getAttributeValue("name"))) {
					// continue;
				} else {
					listaAdjacencia.put(nodePai.getAttributeValue("name"),
							new ArrayList<String>());
				}

			} else {

				System.out.println("Elementos diferentes: "
						+ nodePai.getAttributeValue("name") + " (Pai) "
						+ nodeFillho.getAttributeValue("name") + " (Filho)");

				if (listaAdjacencia.isEmpty()) {
					inserirListaAdjacencia(nodePai.getAttributeValue("name"),
							nodeFillho.getAttributeValue("name"));
				} else if (isAdjacent(nodePai.getAttributeValue("name"),
						nodeFillho.getAttributeValue("name"))) {
					// continue;
				} else {
					inserirListaAdjacencia(nodePai.getAttributeValue("name"),
							nodeFillho.getAttributeValue("name"));
				}

			}

			criarLista(nodeFillho);
		}
		
		

	}

	// Retorna TRUE se o par [primario,secundario] ou [secundario,primario]
	// estiver na lista de adjacência
	public boolean isAdjacent(String primario, String secundario) {

		System.out.println("Primario: " + primario + " - Secundario: "
				+ secundario);
		if (AdjacentClassXML.listaAdjacencia.containsKey(primario)) {

			String aux = " " + secundario;

			System.out.println("Lista dos Elementos*: " + aux);
			if (AdjacentClassXML.listaAdjacencia.get(primario).contains(secundario)) {
				System.out.println("==> isAdjacente: a" + primario
						+ "a (primario) b" + secundario
						+ "b (secundário) -- São adjacente.");
				return true;
			}

		}

		if (AdjacentClassXML.listaAdjacencia.containsKey(secundario)) {
			for (String adjacent1 : AdjacentClassXML.listaAdjacencia.get(secundario)) {

				if (adjacent1.equalsIgnoreCase(primario)) {
					System.out.println("--- IGUAL -----");
				} else {
					System.out.println("adjacent1: " + adjacent1
							+ " - primario: " + primario);
				}
			}

			if (AdjacentClassXML.listaAdjacencia.get(secundario).contains(primario)) {
				System.out.println("==> isAdjacente: a" + primario
						+ "a (primario) b" + secundario
						+ "b (secundário) -- São adjacente.");
				return true;
			}
		}

		System.out.println("==> isAdjacente: " + primario + " (primario) "
				+ secundario + " (secundário) -- Não são adjacente.");
		return false;
	}

	public void imprimirListaAdjacencia() {

		new ArrayList<String>();

		StringBuffer strListaAdjacencia = new StringBuffer();

//		strListaAdjacencia
//				.append("================= Lista de Adjacência ==================== \n");

		for (Iterator<String> iterator = listaAdjacencia.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();

			strListaAdjacencia.append("#" + key + " = ");

			for (Iterator<?> iterator2 = listaAdjacencia.get(key).iterator(); iterator2
					.hasNext();) {
				String string = (String) iterator2.next();
				strListaAdjacencia.append(" " + string + " ");
				System.out.println(" [" + string + "] ");
			}

			strListaAdjacencia.append("; \n");

		}
//
//		strListaAdjacencia
//				.append("===================================================== \n");
		System.out.println(strListaAdjacencia);
	}

//	public static void main(String[] args) {
//		AdjacentClassXML teste = new AdjacentClassXML();
//		teste.executar();
//		teste.imprimirListaAdjacencia();
//		DOMLibrary.eINSTANCE().createClassDiagramModelXML(listaAdjacencia);
//
//	}

}
