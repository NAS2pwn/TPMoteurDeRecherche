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
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
	//private Map<String, Integer> occCorpus;
	public static Map<String, Double> IDFCorpus;
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
		Document.maxNbLikes=0;
		this.index = index;
		this.indexInverse = indexInverse;
		simpleTokenizer = SimpleTokenizer.INSTANCE;  
		this.lemmatizerList();
		this.stopWordsRead();
		System.out.println(Crawler.stopWords);
		ArrayList<String> iss=listFilesForFolder(new File(Crawler.FOLDER_CORPUS));
		this.crawlAll(iss);
		IDFCorpus=new TreeMap<>();
		this.calcIDFCorpus();
		this.calcTfIDFAllDocs();
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
	
	/*private void calcOccCorpus() {
		for(Document d : this.index.getListeDocuments()) {
			for(Map.Entry<String,Frequences> terme : d.getTermes().entrySet()) {
				if(!this.occCorpus.containsKey(terme.getKey()))
					this.occCorpus.put(terme.getKey(), terme.getValue().getNbOc());
				else
					this.occCorpus.replace(terme.getKey(),this.occCorpus.get(terme.getKey())+terme.getValue().getNbOc());
			}
		}
	}*/
	
	private void calcIDFCorpus() {
		IDFCorpus.clear();
		for(String terme:this.getIndexInverse().getTermesSorted())
			IDFCorpus.put(terme, calculIDF(terme));
	}
	
	public double calculTf(Document d, String t) {
		return d.getTermes().get(t).getNbOc()/d.getNbOccurencesTotale();
    }
	
    public double calculIDF(String t) {
    	Set<Document> docs=this.index.getListeDocuments();
        int n = 0;
        for (Document d : docs)
        	if(d.containsTerme(t))
        		n++;
        return Math.log((double) docs.size() / (double) n);
    }

    public double calcTfIDFfromScratch(Document doc, String terme) {
        return calculTf(doc,terme) * calculIDF(terme);
    }
    
    public double calcTfIDF(Document doc, String terme) {
    	if(this.IDFCorpus==null||!this.IDFCorpus.containsKey(terme))
    		throw new Error("Assurez-vous d'avoir calculer les IDF du corpus avec this.calcIDFCorpus(), sinon utilisez calcTfIDFfromScratch(Document doc, String t), qui est moins performant");
    	return calculTf(doc,terme) * IDFCorpus.get(terme);
    }
    
    public void calcTfIDFAllDocs() {
		for(Document d: this.index.getListeDocuments()) {
			for(Map.Entry<String,Frequences> entry : d.getTermes().entrySet()) {
				entry.getValue().setTfIDF(calcTfIDF(d,entry.getKey()));
			}
		}
	}
    
    public static ArrayList<String> traiterString(String str) {
    	String tokens[] = Crawler.simpleTokenizer.tokenize(str);
		String tags[] = Crawler.posTagger.tag(tokens); 
		String lemmas[] = Crawler.lemmatizer.lemmatize(tokens, tags);
		ArrayList<String> finalement=new ArrayList<>();
		for(int i=0;i<tokens.length;i++) {
			if(lemmas[i].equals("O"))
				finalement.add(tokens[i]);
			else
				finalement.add(lemmas[i]);
		}
		List<String> liste=finalement.parallelStream().map(x -> x.toLowerCase()).collect(Collectors.toList());
		ArrayList<String> arrayListe=new ArrayList<String>();
		arrayListe.addAll(liste);
		arrayListe=Document.rmStopWords(arrayListe, Crawler.stopWords);
		
		return arrayListe;
    }
}
