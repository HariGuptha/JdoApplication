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

public class Delete extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		HttpSession session = req.getSession(false);

		PrintWriter out = res.getWriter();

		if (session != null) {
			String email = req.getParameter("email");

			try {

				if (email != null) {

					PersistenceManager pm = PMF.get().getPersistenceManager();

					User e = pm.getObjectById(User.class, email);

					if (e.getEmail().equals(email)) {

						pm.deletePersistent(e);

						out.println("<p>Account deleted successfully</p>");

						Login.execute(req, res);
					}

					else {

						out.println("<p>Enter a valid email id");
						RequestDispatcher rd = req.getRequestDispatcher("Delete.html");
						rd.forward(req, res);
					}

				}

				else {

					out.println("<p>Enter a valid email id");
					RequestDispatcher rd = req.getRequestDispatcher("Delete.html");
					rd.forward(req, res);
				}

			}

			catch (Exception jd) {

				out.println("<p>Enter a email that exists");

				RequestDispatcher rd = req.getRequestDispatcher("Delete.html");

				rd.forward(req, res);

			}
		} else {
			out.println("<p>Enter to login</p>");

			RequestDispatcher rd = req.getRequestDispatcher("index.html");

			rd.forward(req, res);

		}
	}
}
