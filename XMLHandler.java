package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import opennlp.tools.tokenize.SimpleTokenizer;

public class XMLHandler extends DefaultHandler{
   private boolean body=false;
   private boolean docno=false;
   private boolean date=false;
   private boolean title=false;
   private boolean trailer=false;
   private Document document;
   private IndexInverse indexInverse;
   
   public XMLHandler(IndexInverse indexInverse) {
	   this.indexInverse=indexInverse;
   }
   
   public Document getDocument() {
	   return this.document;
   }

   public void startDocument() throws SAXException {
	   this.document=new Document();
   }
   
   public void startElement(String namespaceURI, String lname, String qname, Attributes attrs) throws SAXException {
      if(qname.toUpperCase().equals("DOCNO"))
    	  this.docno=true;
      else if(qname.toUpperCase().equals("DATE_TIME"))
    	  this.date=true;
      else if(qname.toUpperCase().equals("HEADLINE"))
    	  this.title=true;
      else if(qname.toUpperCase().equals("BODY"))
    	  this.body=true;
      else if(qname.toUpperCase().equals("TRAILER"))
    	  this.trailer=true;
   }   

   public void endElement(String uri, String localName, String qName)
         throws SAXException{
     this.docno=false;
     this.date=false;
     this.title=false;
     this.trailer=false;
   }
   
   public void characters(char[] data, int start, int end){
      String str = new String(data, start, end);
      if(this.docno)
    	  this.document.setDocno(str);
      else if (this.date)
    	  this.document.setDate(str);
      else if (this.title)
    	  this.document.setTitle(str);
      
      if(this.body&&!this.trailer) {
		String tokens[] = Crawler.simpleTokenizer.tokenize(str);
		String tags[] = Crawler.posTagger.tag(tokens); 
		String lemmas[] = Crawler.lemmatizer.lemmatize(tokens, tags);
		ArrayList<String> finalement=new ArrayList<>();
		/*for(String l : lemmas) {
		  System.out.println(l);
		}*/
/*		System.out.print("[");
		for(int i=0;i<tokens.length;i++) {
			System.out.print(tokens[i]+", ");
		}
		System.out.println("]");
		System.out.print("[");
		for(int i=0;i<tokens.length;i++) {
			System.out.print(lemmas[i]+", ");
		}
		System.out.println("]");*/
		for(int i=0;i<tokens.length;i++) {
			if(lemmas[i].equals("O"))
				finalement.add(tokens[i]);
			else
				finalement.add(lemmas[i]);
		}
		/*System.out.println(finalement);
		System.out.println("\n");*/
		List<String> liste=finalement.parallelStream().map(x -> x.toLowerCase()).collect(Collectors.toList());
		ArrayList<String> arrayListe=new ArrayList<String>();
		arrayListe.addAll(liste);
		arrayListe=Document.rmStopWords(arrayListe, Crawler.stopWords);
		this.document.addTermes(arrayListe);
		this.indexInverse.addTermes(arrayListe,this.document);
      }
   }
}