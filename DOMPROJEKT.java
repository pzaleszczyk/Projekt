import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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
class Produkt{
	String nazwa, cena;
	int ilosc;
	String[] SkladNazwa;
	String[] SkladWaga;
	String jednostka;
	String waluta;

	Produkt(){

	}
}
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

	void updateAction() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		chooseKebab();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		Element kebabownia = doc.getDocumentElement();
		NodeList nl = kebabownia.getElementsByTagName("produkt");
		Element ee = (Element) nl.item(Integer.parseInt(id)-1);
		//do ee przypisujemy produkt o podanym id


		System.out.print(ee.getElementsByTagName("nazwa").item(0).getTextContent()+" ");
		System.out.print(ee.getElementsByTagName("cena").item(0).getTextContent());
		System.out.println(ee.getElementsByTagName("cena").item(0).getAttributes().item(0).getTextContent());
		System.out.println("Podaj nowe wartosci. (nazwa,cena,waluta). Puste pola pozostawiaja je niezmienione.");
		Scanner keyboard = new Scanner(System.in);
		String in1 = keyboard.nextLine();
		if(!in1.isEmpty()) ee.getElementsByTagName("nazwa").item(0).setTextContent(in1);
		in1 = keyboard.nextLine();
		if(!in1.isEmpty()) ee.getElementsByTagName("cena").item(0).setTextContent(in1);
		in1 = keyboard.nextLine();
		if(!in1.isEmpty()) ee.getElementsByTagName("cena").item(0).getAttributes().item(0).setNodeValue(in1);



		//robimy liste skladnikow
		NodeList skladList = ee.getElementsByTagName("skladnik");
		//iteracja przez skladniki
		for(int i=0 ; i < skladList.getLength(); i++) {
			Element skladEle = (Element) skladList.item(i);
			System.out.print(skladEle.getElementsByTagName("nazwa").item(0).getTextContent()+" ");
			System.out.print(skladEle.getElementsByTagName("ilosc").item(0).getTextContent());
			System.out.println(skladEle.getElementsByTagName("ilosc").item(0).getAttributes().item(0).getTextContent());
			System.out.println("Podaj nowe wartosci. (nazwa, ilosc, jednostka). Puste pola pozostawiaja je niezmienione.");
			in1 = keyboard.nextLine();
			if(!in1.isEmpty()) skladEle.getElementsByTagName("nazwa").item(0).setTextContent(in1);
			in1 = keyboard.nextLine();
			if(!in1.isEmpty()) skladEle.getElementsByTagName("ilosc").item(0).setTextContent(in1);
			in1 = keyboard.nextLine();
			if(!in1.isEmpty()) skladEle.getElementsByTagName("ilosc").item(0).getAttributes().item(0).setNodeValue(in1);
		}	



		// Write to file
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		tf.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
		DOMSource ds = new DOMSource(doc);
		StreamResult sr = new StreamResult("KebabUpdate.xml");
		tf.transform(ds, sr);
	}

	void printId() throws ParserConfigurationException, SAXException, IOException {
		chooseKebab();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		Element kebabownia = doc.getDocumentElement();
		NodeList nl = kebabownia.getElementsByTagName("produkt");
		Element ee = (Element) nl.item(Integer.parseInt(id)-1);
		//do ee przypisujemy produkt o podanym id

		System.out.print(ee.getElementsByTagName("nazwa").item(0).getTextContent()+" ");
		System.out.print(ee.getElementsByTagName("cena").item(0).getTextContent());
		System.out.println(ee.getElementsByTagName("cena").item(0).getAttributes().item(0).getTextContent());

		//robimy liste skladnikow
		NodeList skladList = ee.getElementsByTagName("skladnik");
		//iteracja przez skladniki
		for(int i=0 ; i < skladList.getLength(); i++) {
			Element skladEle = (Element) skladList.item(i);
			System.out.print(skladEle.getElementsByTagName("nazwa").item(0).getTextContent()+" ");
			System.out.print(skladEle.getElementsByTagName("ilosc").item(0).getTextContent());
			System.out.println(skladEle.getElementsByTagName("ilosc").item(0).getAttributes().item(0).getTextContent());
		}	
	}

	void insertAction() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Podaj nazwe, cene[pln], ilosc skladnikow");
		String innazwa = keyboard.nextLine();
		String incena = keyboard.nextLine();
		int inilosc = Integer.parseInt(keyboard.nextLine());
		System.out.println("Podaj "+inilosc+" skladnikow.");
		String[] skladnikiNazwa = new String[inilosc];
		String[] skladnikiIlosc = new String[inilosc];
		for(int o=0; o<inilosc; o++) {
			System.out.println("Nazwa:");
			skladnikiNazwa[o] = keyboard.nextLine();
			System.out.println("Ilosc[g]:");
			skladnikiIlosc[o] = keyboard.nextLine();
		}


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
		nazwa.appendChild(doc.createTextNode(innazwa));
		produkt.appendChild(nazwa);
		//Cena
		Element cena = doc.createElement("cena");
		cena.appendChild(doc.createTextNode(incena));
		cena.setAttribute("waluta", "pln");
		produkt.appendChild(cena);
		//Skladniki
		Element skladniki = doc.createElement("skladniki");
		/////////////////////FOR NA SKLADNIKI///////////////////////////////
		Element skladnik,nazwaS,waga;
		for(int m=0; m<inilosc; m++) {
			skladnik = doc.createElement("skladnik");
			//Nazwa
			nazwaS = doc.createElement("nazwa");
			nazwaS.appendChild(doc.createTextNode(skladnikiNazwa[m]));
			skladnik.appendChild(nazwaS);
			//Ilosc
			waga = doc.createElement("ilosc");
			waga.appendChild(doc.createTextNode(skladnikiIlosc[m]));
			waga.setAttribute("jednostka", "g");
			skladnik.appendChild(waga);
			skladniki.appendChild(skladnik);
		}
		produkt.appendChild(skladniki);


		produkty.appendChild(produkt);
		// Write to file
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		tf.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
		DOMSource ds = new DOMSource(doc);
		StreamResult sr = new StreamResult("KebabInsert.xml");
		tf.transform(ds, sr);

	}//DONE

	void deleteAction() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		chooseKebab();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		Element kebabownia = doc.getDocumentElement();
		NodeList nl = kebabownia.getElementsByTagName("produkt");
		Element ee = (Element) nl.item(Integer.parseInt(id)-1);

		ee.getParentNode().removeChild(ee);


		// Write to file
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		tf.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
		DOMSource ds = new DOMSource(doc);
		StreamResult sr = new StreamResult("KebabDelete.xml");
		tf.transform(ds, sr);
	}

	void sortAction() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		Element kebabownia = doc.getDocumentElement();
		NodeList nl = kebabownia.getElementsByTagName("produkt");
		NodeList produktylist = kebabownia.getElementsByTagName("produkty");
		Element produkty = (Element) produktylist.item(0);
		int length = nl.getLength();

		int[] order = new int[nl.getLength()];
		Element[] elements = new Element[nl.getLength()];

		//Dodajemy do listy by posortowac
		for (int i = 0; i < length; i++) {
			Element ee = (Element) nl.item(i);
			order[i] = Integer.parseInt(ee.getElementsByTagName("cena").item(0).getTextContent());
			elements[i] = ee;
		}
		//Sortujemy
		int temp;
		Element tempElement;
		for (int i = 1; i < length; i++) {
			for (int j = i; j > 0; j--) {
				if (order[j] < order[j-1]) {
					temp = order[j];
					order[j] = order[j - 1];
					order[j - 1] = temp;

					tempElement = elements[j];
					elements[j] = elements[j - 1];
					elements[j - 1] = tempElement;
				}
			}
		}
		//dodajemy posortowane nody
		for (int i = 0; i < length; i++) {
			elements[i].getElementsByTagName("nazwa").item(0).getTextContent();
			produkty.appendChild(elements[i]);
		}

		// Write to file
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		tf.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
		DOMSource ds = new DOMSource(doc);
		StreamResult sr = new StreamResult("KebabSort.xml");
		tf.transform(ds, sr);


	}


	public static void main(String argv[])  throws SAXException, IOException, ParserConfigurationException, TransformerException{
		DOMPROJEKT a = new DOMPROJEKT();
		a.print("Kebabownia.xml");
		a.chooseAction();
		//a.print("Kebab.xml");
		//sort //
	} 
}
