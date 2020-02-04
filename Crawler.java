package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Crawler {
	private Index index;
	private IndexInverse indexInverse;
	public static List<String> listeStopWord;
	public ArrayList<String> arborescence;
	
	public Crawler(Index index, IndexInverse indexInverse) {
		this.index = index;
		this.indexInverse = indexInverse;
		Crawler.stopWords();
		System.out.println("gaco");
		ArrayList<String> iss=Crawler.listFilesForFolder(new File("src/application/corpusRInew"));
		//System.out.println(iss);
		this.parseAll(iss);
		System.out.println("wtf");
		//System.out.println(this.indexInverse);
		System.out.println("oyea");
		//System.out.println(this.indexInverse.getIndexInverse().get("flashier").stream().findFirst().get().getTermes());
		//System.out.println("oui ptn tu clc");
		System.out.println(this.index.getListeDocuments().stream().findAny().get().getTermes());
		//this.indexerLesDocument();
	}
	
	public static void stopWords() {
		String texte = "";
		try {
			texte = TraitementDocument.extraireTexte("src/application/stopwords.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Crawler.listeStopWord = Arrays.asList(texte.split("\n"));
		
	}
	
	public static ArrayList<String> listFilesForFolder(File folder) {
		ArrayList<String> a = new ArrayList<>();
	    for (final File fileEntry : folder.listFiles()) {
	       	if (fileEntry.isDirectory()) {
	            a.addAll(listFilesForFolder(fileEntry));
	       	}
	        else
	            a.add(fileEntry.getAbsolutePath());
	    }
	    return a;
	}
	
	public void parseAll(ArrayList<String> fichier) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser parser = factory.newSAXParser();
	        XMLHandler temp;
			for(String f : fichier) {
				temp=new XMLHandler(this.indexInverse);
		        parser.parse(f, temp);
		        temp.getDocument().calcFrequence();
		        this.index.ajouterDoc(temp.getDocument());
		        System.out.println("hehe");
		        //System.out.println(temp.getDocument().getTermes());
			}
		}
		catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} /*catch (SAXParseException e) {
			//System.out.println("mais naaan");
			//throw new RuntimeException("bon");
			//Bon j'ignore parce que j'ai bien la flemme de changer le DTD de quoi que ce soit
		}*/ catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
