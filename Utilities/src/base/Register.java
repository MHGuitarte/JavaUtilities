package base;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String mail;
	private String pass, repeatPass;
	private User u;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		mail = request.getParameter("mail");
		pass = request.getParameter("pass");
		repeatPass = request.getParameter("repeatPass");

		if (checkPass(pass, repeatPass)) {
			
		} else {
			request.setAttribute("error", "Las contraseñas no coinciden");
		}

	}

	public boolean checkPass(String pass, String repeatPass) {
		if (pass.equals(repeatPass)) {
			return true;
		} else {
			return false;
		}
	}

}
