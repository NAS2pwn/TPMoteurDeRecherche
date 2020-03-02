package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;

public class Crawler {
	private Index index;
	private IndexInverse indexInverse;
	private Map<String, Integer> ;
	public static TreeSet<String> stopWords;
	public ArrayList<String> arborescence;
	public final static String URL_STOPWORDS=ServletHehe.DIRECTORY_BASE+"src/application/stopwords.txt";
	public final static String URL_POSBIN=ServletHehe.DIRECTORY_BASE+"src/application/en-pos-maxent.bin";
	public final static String URL_DICTLEMMATIZE=ServletHehe.DIRECTORY_BASE+"src/application/en-lemmatizer.dict";
	public final static String FOLDER_CORPUS=ServletHehe.DIRECTORY_BASE+"src/application/corpusRInew";
	public static DictionaryLemmatizer lemmatizer;
	public static POSTaggerME posTagger;
	public static SimpleTokenizer simpleTokenizer;
	
	public Crawler(Index index, IndexInverse indexInverse) {
		this.index = index;
		this.indexInverse = indexInverse;
		simpleTokenizer = SimpleTokenizer.INSTANCE;  
		this.lemmatizerList();
		this.stopWordsRead();
		System.out.println(Crawler.stopWords);
		ArrayList<String> iss=listFilesForFolder(new File(Crawler.FOLDER_CORPUS));
		this.crawlAll(iss);
		/*for(Document d : this.index.getListeDocuments()) {
			System.out.println(d.getTermes());
		}*/
	}
	
	public void lemmatizerList() {
		try{
            InputStream posModelIn = new FileInputStream(Crawler.URL_POSBIN);
            POSModel posModel = new POSModel(posModelIn);
            Crawler.posTagger = new POSTaggerME(posModel);
            InputStream dictLemmatizer = new FileInputStream(Crawler.URL_DICTLEMMATIZE);
            Crawler.lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
 
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	public void stopWordsRead() {
		Crawler.stopWords=new TreeSet<String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(Crawler.URL_STOPWORDS));
			String line = reader.readLine();
			while (line != null) {
				Crawler.stopWords.add(line.toLowerCase());
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	
	public void crawlAll(ArrayList<String> fichier) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser parser = factory.newSAXParser();
	        XMLHandler temp;
			for(String f : fichier) {
				temp=new XMLHandler(this.indexInverse);
		        parser.parse(f, temp);
		        temp.getDocument().calcFrequence();
		        this.index.ajouterDoc(temp.getDocument());
		        //System.out.println("hehe");
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
			//Bon j'ignore parce que j'ai bien la flemme de changer le DOCTYPE de quoi que ce soit
		}*/ catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public IndexInverse getIndexInverse() {
		return this.indexInverse;
	}
	
	public double calculTf(Document d, String t) {
		Map<String,Frequences> m = d.getTermes();
		return m.get(t).getNbOc()/m.size();
    }

    public double calculIDF(Set<Document> docs, String t) {
        double n = 0;
        for (Document d : docs)
        	(Map.Entry<String,Frequences> e : d.getTermes().entrySet())
        		n+=e.getValue().getNbOc();
        return Math.log(docs.size() / n);
    }

    public double tfIdf(Document doc, Set<Document> docs, String terme) {
        return calculTf(doc, terme) * calculIDF(docs, terme);
    }

}
