package com.app;
import java.io.IOException;
import java.io.PrintWriter;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.KeyFactory;

public class Login extends HttpServlet{
	
	
	static void execute(HttpServletRequest req,HttpServletResponse res) throws IOException{
		PrintWriter out=res.getWriter();
		out.print("<form action=index.html>");
		out.println("<input type=\"submit\" ");
		out.println("value=\"Home\">");
		out.println("</form>");	
	}
	
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name=(String)req.getSession().getAttribute("name");
		print(resp.getWriter(),name);
			
		}
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException{
		HttpSession session=req.getSession();
		PrintWriter out=res.getWriter();
	
		if(session!=null){
				
		try{	
			String email=req.getParameter("name");
			
			String pass=req.getParameter("pass");
			System.out.println("PASS "+pass);
		
		
		if(!(email.isEmpty()||pass.isEmpty())){
			
			KeyFactory.createKey(User.class.getSimpleName(), email);
			
			PersistenceManager pm=PMF.get().getPersistenceManager();
						
			User e=pm.getObjectById(User.class,email);
		if(pass.equals(e.getPassword()))
		{
			
			String name=e.getName();
			session.setAttribute("email", email);
		
			session.setAttribute("name", name);
			
			print(res.getWriter(),(String)e.getName());
			
			
			
		}
		
		else if(!email.equals(e.getEmail())){
			
			out.println("<p> User not recognised click register to add account </p>");
			
			out.print("<form action=Register.html>");
			out.println("<input type=\"submit\" ");
			out.println("value=\"Register\">");
			out.println("</form>");	
			
			
		}
		else
		{
			out.println("<p>Invalid login try again </p>");
			execute(req,res);
		}
		
		}
		else{
			out.println("<p> Enter the details to proceed </p>");

			RequestDispatcher rd=req.getRequestDispatcher("index.html");
			rd.forward(req, res);
		}
		
	}catch(Exception e ){
		
		
		out.println("<p> Enter the valid email-id to proceed </p>");

		RequestDispatcher rd=req.getRequestDispatcher("index.html");
		rd.forward(req, res);
	
	}		
	}else{
		out.println("<p> Enter to login	 </p>");
		req.getRequestDispatcher("index.html").forward(req, res);
	}
		
	}	
				
	void print(PrintWriter out,String name){

		
		
		out.println("<p>Welcome "+name +" </p>");
		
		out.println("<a href=Name.html><button>change name</button></a>");
		out.println("<a href=Password.html><button>change password</button></a><br>");
		
		
	
		
		out.println("<h3><b>Remainder Saver</b></h3><br>");
		out.println("<a href=Addremainder.html><button>Add Remainder</button></a><br><br>");
	
		
		out.println("<a href=Viewremainder><button>View Remainder</button></a><br><br>");
		out.println("<a href=Deleteremainder.html><button>Delete Remainder</button></a><br><br>");
	
	
	
		out.println("<a href=Logout>Logout</a>");
	
	}
			
}