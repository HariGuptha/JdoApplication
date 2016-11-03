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

public class Name extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(false);
		if (session != null) {
			String name = req.getParameter("name");
			try {

				if (!(name.isEmpty() || name == null)) {
					PersistenceManager pm = PMF.get().getPersistenceManager();

					String email = (String) session.getAttribute("email");

					User e = pm.getObjectById(User.class, email);

					e.setName(name);

					req.getSession().removeAttribute("name");
					req.getSession().setAttribute("name", name);

					res.sendRedirect("Login");

				}

				else {

					out.println("<p>Enter a valid name</p>");

					RequestDispatcher rd = req.getRequestDispatcher("Name.html");

					rd.forward(req, res);

				}

			}

			catch (Exception e) {

				out.println("<p>Enter a valid name</p>");

				RequestDispatcher rd = req.getRequestDispatcher("Name.html");

				rd.forward(req, res);

			}
		} else {
			out.println("<p>Enter to login</p>");

			RequestDispatcher rd = req.getRequestDispatcher("index.html");

			rd.forward(req, res);

		}
	}

}
