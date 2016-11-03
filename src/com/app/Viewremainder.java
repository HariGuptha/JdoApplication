package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Viewremainder extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession session = req.getSession(false);
		PrintWriter out = res.getWriter();

		if (session != null) {
			res.setContentType("text/html");

			PersistenceManager pm = PMF.get().getPersistenceManager();

			String email = (String) req.getSession().getAttribute("email");

			try {
				Remainder r = pm.getObjectById(Remainder.class, email);

				ArrayList<String> titlearray = r.getTitle();
				ArrayList<String> messagearray = r.getMessage();
				if (!(titlearray.isEmpty() && messagearray.isEmpty())) {
					out.println("<table border=\"1\"><tr><th>Title</th>");
					out.println("<th>Message</th></tr>");

					for (int i = 0; i < titlearray.size(); i++) {

						out.println("<tr><td>" + titlearray.get(i) + "</td>");
						out.println("<td>" + messagearray.get(i) + "</td></tr>");

					}

					out.println("</table>");
				} else {

					out.println("<p>No remainder found</p>");

				}

			} catch (Exception e) {
				out.println("<p>No remainder found</p>");

			}

		} else {
			out.println("<p>Enter to login</p>");

			RequestDispatcher rd = req.getRequestDispatcher("index.html");

			rd.forward(req, res);
		}
	}
}
