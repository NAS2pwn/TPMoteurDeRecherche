package application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import opennlp.tools.tokenize.SimpleTokenizer;

public class XMLHandler extends DefaultHandler{
   private boolean body=false;
   private boolean docno=false;
   private boolean date=false;
   private boolean title=false;
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

   public void endDocument() throws SAXException {
		//System.out.println(this.document);
		//System.out.println("Fin du parsing");
   }
   
   public void startElement(String namespaceURI, String lname,
         String qname, Attributes attrs) throws SAXException {
      //System.out.println("namsespaceURI "+namespaceURI+" lname "+lname+" qname "+qname+" attrs "+attrs);
      if(qname=="DOCNO")
    	  this.docno=true;
      else if(qname=="DATE_TIME")
    	  this.date=true;
      else if(qname=="HEADLINE")
    	  this.title=true;
      else if(qname=="BODY")
    	  this.body=true;
   }   

   public void endElement(String uri, String localName, String qName)
         throws SAXException{
     //System.out.println("uri " + uri + " localName " + localName + " qName " + qName);
     this.docno=false;
     this.date=false;
     this.title=false;
   }
   
   /**
    * permet de récupérer la valeur d'un nœud
    */  
   public void characters(char[] data, int start, int end){
	  Main.gacooo++;
      String str = new String(data, start, end);
      if(this.docno)
    	  this.document.setDocno(str);
      else if (this.date)
    	  this.document.setDate(str);
      else if (this.title)
    	  this.document.setTitle(str);
      
      if(this.body) {
    	  //str=TraitementDocument.sanitize(str);
    	  //List<String> liste=TraitementDocument.splitDoc(str);
    	  SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;  
          
          //Tokenizing the given sentence 
          String tokens[] = simpleTokenizer.tokenize(str);
          List<String> liste=Arrays.asList(tokens);
    	  //liste=TraitementDocument.rmStopWords(liste, Crawler.listeStopWord);
    	  this.document.addTermes(liste);
    	  //System.out.println("liste "+liste+" doc "+this.document+" gacooo "+Main.gacooo);
    	  this.indexInverse.addTermes(liste,this.document);
      }
      //System.out.println("Donnée du nœud " + node + " : " + str);
   }
}