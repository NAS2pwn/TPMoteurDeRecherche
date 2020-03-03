package application;

import java.io.* ;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
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
	//Giga faille OSINT mdr, le bon privesc en root avec GhostCat là miam
	public static ModeleBooleen modeleBooleen;
	public static ModeleVectoriel modeleVectoriel;
	public static final String BOOLEAN_MODE="1";
	public static final String VECTORIAL_MODE="2";

	public void init() throws ServletException{
		Index i = new Index();
		IndexInverse iinv=new IndexInverse();
		Crawler c=new Crawler(i,iinv);
		System.out.println("test");
		modeleBooleen=new ModeleBooleen(iinv);
		modeleVectoriel=new ModeleVectoriel(i, iinv);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		/*res.setContentType("application/json");
		System.out.println("Nouvelle connexion lo on se fait hacker :'(");
		
		String param=req.getParameter("param");
		String request=req.getParameter("search");
		JSONArray json=new JSONArray();
		
		JSONObject success0=new JSONObject();
		success0.append("success", "0");
		
		JSONObject success1=new JSONObject();
		success1.append("success", "1");
		
		JSONObject found0=new JSONObject();
		found0.append("found", "0");
		
		JSONObject found1=new JSONObject();
		found1.append("found", "1");
		
		boolean good=true;
		
		if(request==null||param==null) {
			json.put(success0);
		}
		else {
			Collection<Document> documents=null;
			if(param.equals(BOOLEAN_MODE))
				documents=modeleBooleen.searchThisShit(request);
			else if(param.equals(VECTORIAL_MODE))
				documents=modeleVectoriel.completeSearch(request);
			else {
				good=false;
				json.put(success0);
			}
			
			if(good) {
				json.put(success1);
				
				if(documents.size()==0) {
					json.put(found0);
				}
				else {
					json.put(found1);
					
					JSONObject temp;
					
					for(Document d : documents) {
						temp=new JSONObject();
						temp.append("id", d.getDocno());
						temp.append("title", d.getTitle());
						temp.append("date", d.getDate());
						json.put(temp);
					}
				}
			}
		}
		
		ServletOutputStream out = res.getOutputStream();
		out.print(json.toString());*/
		System.out.println(modeleBooleen.parseRequest("gaco and segpa or (hoho and test)"));
	} 
}