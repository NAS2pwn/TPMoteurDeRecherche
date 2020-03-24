package application;

import java.io.* ;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONArray;

public class ServletHehe extends HttpServlet {
	
	private static final long serialVersionUID = -5447628966384402397L;
	public static String DIRECTORY_BASE=/*"/home/etudiants/info/nbennouar/eclipse-workspace/moteurRechercheWeb/";//"/root/TPMoteurDeRecherche/";//*/"/home/chapavoler/eclipse-workspace/moteurRechercheWeb/";
	//Giga faille OSINT mdr
	public static Index index;
	public static IndexInverse indexInv;
	public static ModeleBooleen modeleBooleen;
	public static ModeleVectoriel modeleVectoriel;
	public static final String BOOLEAN_MODE="1";
	public static final String VECTORIAL_MODE="2";

	public void init() throws ServletException{
		index = new Index();
		indexInv=new IndexInverse();
		Crawler c=new Crawler(index,indexInv);
		System.out.println("test");
		modeleBooleen=new ModeleBooleen(index,indexInv);
		modeleVectoriel=new ModeleVectoriel(index,indexInv);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		System.out.println("Nouvelle connexion lo on se fait hacker :'(");
		
		String param=req.getParameter("param");
		String request=req.getParameter("search");
		
		JSONObject json=new JSONObject();
		
		JSONArray results=new JSONArray();
		
		boolean good=true;
		
		if(request==null||param==null) {
			json.append("success", "0");
		}
		else {
			Collection<Document> documents=null;
			if(param.equals(BOOLEAN_MODE))
				documents=modeleBooleen.search(request);
			else if(param.equals(VECTORIAL_MODE))
				documents=modeleVectoriel.completeSearch(request);
			else {
				good=false;
				json.append("found", "0");
			}
			
			if(good) {
				json.append("success", "1");
				
				if(documents.size()==0) {
					json.append("found", "0");
				}
				else {
					json.append("found", "1");
					
					JSONObject temp;
					
					for(Document d : documents) {
						temp=new JSONObject();
						temp.append("id", d.getDocno());
						temp.append("title", d.getTitle());
						temp.append("date", d.getDate());
						if(param.equals(BOOLEAN_MODE))
							temp.append("pertinence", d.getPertinence());
						results.put(temp);
					}
					
					json.append("results", results);
				}
			}
		}
		
		ServletOutputStream out = res.getOutputStream();
		out.print(json.toString());
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		System.out.println("Hohoho feedback :)");
		
		JSONObject json=new JSONObject();
		
		String param=req.getParameter("param");
		String doc=req.getParameter("doc");
		String like=req.getParameter("like");
		String terme=req.getParameter("terme");
		
		Optional<Document> opDoc=ServletHehe.index.getListeDocuments().parallelStream().filter(x -> x.getTitle().equals(doc)).findAny();
		
		if(param==null||doc==null) {
			json.append("success", "0");
		}
		else if (param.equals("likeIt") && opDoc.isPresent() && like!=null) { //Bon y a 0 sécu on peut like like ou dislike n'importe comment pour l'instant
			if(like.equals("1")) {
				json.append("success", "1");
				opDoc.get().like();
			}
			else if (like.equals("-1")) {
				json.append("success", "1");
				opDoc.get().unlike();
			}
			else {
				json.append("success", "0");
				json.append("error", "bien joué bg ca te faire rire ??? >:(");
			}
		}
		else if (param.equals("clickOnThisShit") && opDoc.isPresent() && terme!=null) {
			Crawler.simpleTokenizer.tokenize(terme);
		}
		else
			json.append("success", "0");
		
		
	}
}