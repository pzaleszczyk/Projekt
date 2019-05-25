import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class DOMPROJEKT {
	File xmlFile = new File("Kebabownia.xml");
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	
	String id;
	String field;

	void print() throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);

		doc.getDocumentElement().normalize();

		System.out.println(doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("produkt");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			//System.out.println("\nCurrent Element: " + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) nNode;

				Node node1 = elem.getElementsByTagName("nazwa").item(0);
				System.out.printf("Nazwa: %s%n", node1.getTextContent());

				Node node2 = elem.getElementsByTagName("cena").item(0);
				System.out.printf("Cena: %s%n", node2.getTextContent());

				NodeList skladList = elem.getElementsByTagName("skladnik");
				System.out.printf("Skladniki[%s]:%n", skladList.getLength());

				for (int j = 0; j < skladList.getLength(); j++) {
					//Na liste wrzucamy kolejne skladniki
					Node skladNode = skladList.item(j);
					//Jesli element to wpisujemy
					if (skladNode.getNodeType() == Node.ELEMENT_NODE) {
						Element skladnik = (Element) skladNode;

						Node node = skladnik.getElementsByTagName("nazwa").item(0);
						System.out.printf("   Nazwa: %s%n", node.getTextContent());

						node = skladnik.getElementsByTagName("ilosc").item(0);
						System.out.printf("   Ilosc: %s%n", node.getTextContent());
					}
				}

			}
		}
	}
	void chooseAction() throws SAXException, IOException, ParserConfigurationException {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Choose action (update,insert,delete,sort)");
		String input = keyboard.nextLine();
		switch(input) {
		case "update": updateAction();	break;
		case "delete": deleteAction();	break;
		case "insert": insertAction();	break;
		case "sort": sortAction();		break;
		default : System.out.println("Wrong name");
		chooseAction();
		}
	}
	void chooseKebab() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Name kebab (number) to change it");
		id = keyboard.nextLine();
		
	}
	void chooseField() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Name field (nazwa,cena,skladniki) to change it");
		String in1 = keyboard.nextLine();
		switch(in1) {
		case "nazwa": field = "nazwa"; 
		case "cena": field = "cena"; 
		case "skladniki": chooseSkladnik(); break;
		default: chooseField();
		}
	}
	void chooseSkladnik() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Name field (nazwaS, ilosc) to change it");
		String in1 = keyboard.nextLine();
		switch(in1) {
		case "nazwaS": field = "nazwaS";
		case "ilosc": field = "ilosc";
		default: chooseSkladnik();
		}
	}

	void updateAction() throws ParserConfigurationException, SAXException, IOException {
		chooseKebab();
		chooseField();
		
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		System.out.println(doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("produkt");
	}

	void insertAction() throws ParserConfigurationException, SAXException, IOException {
	
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder domBuilder = domFactory.newDocumentBuilder();

	    Document newDoc = domBuilder.newDocument();
	    Element rootElement = newDoc.createElement("parent");
	    newDoc.appendChild(rootElement);
	    Element rowElement = newDoc.createElement("row");

	    Element curElement = newDoc.createElement("newValue");
	    curElement.appendChild(newDoc.createTextNode("newText"));
	    rowElement.appendChild(curElement);
	    rootElement.appendChild(rowElement);
	    
	    toString(newDoc);
		
	}//DONE

	void deleteAction() throws SAXException, IOException, ParserConfigurationException {
		chooseKebab();
		chooseField();
		
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		System.out.println(doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("produkt");
		
	}

	void sortAction() throws ParserConfigurationException, SAXException, IOException {
		chooseKebab();
		chooseField();
		
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		System.out.println(doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("produkt");
		
	}
	public  void main(String argv[])  throws SAXException, IOException, ParserConfigurationException{
		DOMPROJEKT a = new DOMPROJEKT();
		a.print();
		a.chooseAction();
	} 
}