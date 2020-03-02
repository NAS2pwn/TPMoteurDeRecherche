package application;

import java.io.* ;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class ServletHehe extends HttpServlet {
	
	private static final long serialVersionUID = -5447628966384402397L;
	public static String DIRECTORY_BASE="/home/etudiants/info/nbennouar/eclipse-workspace/moteurRechercheWeb/";//"/root/TPMoteurDeRecherche/";//"/home/chapavoler/eclipse-workspace/moteurRechercheWeb/";
	public static ModeleBooleen modeleBooleen;

	public void init() throws ServletException{
		Index i = new Index();
		IndexInverse iinv=new IndexInverse();
		Crawler c=new Crawler(i,iinv);
		System.out.println("test");
		modeleBooleen=new ModeleBooleen(iinv);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		System.out.println("Nouvelle connexion lo on se fait hacker :'(");
		
		String request=req.getParameter("search");
		JSONObject json=new JSONObject();
		
		if(request==null) {
			json.append("success", "0");
		}
		else {
			Set<Document> documents=modeleBooleen.searchThisShit(request);
			
			json.append("success", "1");
			
			if(documents.size()==0) {
				json.append("found", "0");
			}
			else {
				json.append("found", "1");
				
				JSONObject temp;
				
				for(Document d : documents) {
					temp=new JSONObject();
					temp.append("title", d.getTitle());
					temp.append("date", d.getDate());
					json.append(d.getDocno(),temp);
				}
			}
		}
		
		
		ServletOutputStream out = res.getOutputStream();
		out.print(json.toString());
	} 
}