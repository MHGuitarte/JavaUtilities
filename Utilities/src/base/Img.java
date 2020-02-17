package base;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Img
 */
@WebServlet("/img")
public class Img extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PreparedStatement st;
	private Conexion conn;
	private byte[] img;
	private ServletOutputStream sos;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession().getAttribute("conn") != null) {
			conn = (Conexion) request.getSession().getAttribute("conn");
		} else {
			conn = new Conexion();
		}

		if (request.getParameter("i") != null || !request.getParameter("i").equals("")) {
			try {
				img = obtenerImagen(request.getParameter("i"));
			} catch (SQLException e) {
				request.setAttribute("imgError", "Error en la carga de la imagen");
			}
			response.setContentType("image/png");
			response.setContentLength(img.length);

			sos = response.getOutputStream();
			sos.write(img);
		}

	}

	public byte[] obtenerImagen(String id) throws SQLException {
		ResultSet res;
		byte[] imagen = null;

		st = conn.devolverConexion().prepareStatement("SELECT id FROM public.images WHERE id = ?");
		st.setString(1, id);

		res = st.executeQuery();

		if (res.next()) {
			imagen = res.getBytes(1);
		}

		return imagen;
	}

}
