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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;

public class DOMPROJEKT {
	File xmlFile = new File("Kebabownia.xml");
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	
	String id;
	String field;

	void print(String filename) throws SAXException, IOException, ParserConfigurationException {
		xmlFile = new File(filename);
		
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

	void chooseAction() throws SAXException, IOException, ParserConfigurationException, TransformerException {
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

	void insertAction() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		
		Element kebabownia = doc.getDocumentElement();
		NodeList nl = kebabownia.getElementsByTagName("produkty");
		Element produkty = (Element) nl.item(0);
		
		//Tworzymy nowy produkt
			Element produkt = doc.createElement("produkt");
		//Nazwa
			Element nazwa = doc.createElement("nazwa");
			nazwa.appendChild(doc.createTextNode("Test1"));
			produkt.appendChild(nazwa);
		//Cena
			Element cena = doc.createElement("cena");
			cena.appendChild(doc.createTextNode("Test2"));
			cena.setAttribute("waluta", "pln");
			produkt.appendChild(cena);
		//Skladniki
			Element skladniki = doc.createElement("skladniki");
		/////////////////////FOR NA SKLADNIKI///////////////////////////////
		//Tworzymy nowy skladnik
			Element skladnik = doc.createElement("skladnik");
			//Nazwa
			Element nazwaS = doc.createElement("nazwa");
			nazwaS.appendChild(doc.createTextNode("Test3"));
			skladnik.appendChild(nazwaS);
			//Waga
			Element waga = doc.createElement("waga");
			waga.appendChild(doc.createTextNode("Test4"));
			waga.setAttribute("jednostka", "g");
			skladnik.appendChild(waga);
			skladniki.appendChild(skladnik);
		//Tworzymy nowy skladnik
			skladnik = doc.createElement("skladnik");
			//Nazwa
			nazwaS = doc.createElement("nazwa");
			nazwaS.appendChild(doc.createTextNode("Test5"));
			skladnik.appendChild(nazwaS);
			//Waga
			waga = doc.createElement("waga");
			waga.appendChild(doc.createTextNode("Test6"));
			waga.setAttribute("jednostka", "kg");
			skladnik.appendChild(waga);
			skladniki.appendChild(skladnik);
		///////////////////////////////////////////////////////////////////////
			produkt.appendChild(skladniki);
			

		produkty.appendChild(produkt);
		// Write to file
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		tf.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
		DOMSource ds = new DOMSource(doc);
		StreamResult sr = new StreamResult("Kebab.xml");
		tf.transform(ds, sr);
		
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
	
	public static void main(String argv[])  throws SAXException, IOException, ParserConfigurationException, TransformerException{
		DOMPROJEKT a = new DOMPROJEKT();
		a.print("Kebabownia.xml");
		a.chooseAction();
		a.print("Kebab.xml");
	} 
}