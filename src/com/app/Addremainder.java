package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Addremainder extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		HttpSession session = req.getSession(false);
		PrintWriter out = res.getWriter();
		if (session != null) {
			String title = req.getParameter("title");
			String message = req.getParameter("message");

			String email = (String) session.getAttribute("email");

			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {

				Remainder rm = pm.getObjectById(Remainder.class, email);

				rm.addTitle(title);
				rm.addMessage(message);

				out.println("<p>Remainder added Successfully</p>");

				res.sendRedirect("Login");

			}

			catch (JDOObjectNotFoundException e) {

				Remainder rm = new Remainder();
				ArrayList<String> titlearray = new ArrayList<String>();
				ArrayList<String> messagearray = new ArrayList<String>();
				titlearray.add(title);
				messagearray.add(message);

				rm.setEmail(email);
				rm.setTitle(titlearray);
				rm.setMessage(messagearray);

				pm.makePersistent(rm);
				out.println("<p>Remainder added Successfully</p>");

				res.sendRedirect("Login");

			}
		} else {
			out.println("<p>Enter to login</p>");

			RequestDispatcher rd = req.getRequestDispatcher("index.html");

			rd.forward(req, res);

		}
	}

}
