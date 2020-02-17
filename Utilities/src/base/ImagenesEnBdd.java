/**
 * 
 */
package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author usuario
 *
 */
public class ImagenesEnBdd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Conexion conn = new Conexion();

			PreparedStatement st = conn.devolverConexion()
					.prepareStatement("INSERT INTO public.images (id, Img) VALUES(?, ?)");

			String s = "filePath";

			File f = new File(s);
			InputStream input = new FileInputStream(f);

			st.setString(1, ""); //Meter nombre
			st.setBinaryStream(2, input);

			st.execute();
			System.out.println("todo ok");
			conn.cerrarConexion();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
