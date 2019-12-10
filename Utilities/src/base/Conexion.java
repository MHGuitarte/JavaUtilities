package base;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	Connection connection;

	private final String DB_URL = "jdbc:postgresql://ns3034756.ip-91-121-81.eu/amhurtado";
	private final String USER = "amhurtado";
	private final String PASS = "amhurtado";

	public Conexion() {
		try {
			Class.forName("org.postgresql.Driver");

			connection = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch (SQLException e) {
			System.out.println("Error de SQL");
		} catch (ClassNotFoundException e) {
			System.out.println("Surgió un error al abrir la conexión, inténtelo más tarde");
			e.printStackTrace();
		}
	}

	public Connection devolverConexion() {
		return connection;
	}

	public void cerrarConexion() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Surgió un error al cerrar la conexión, inténtelo más tarde.");
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void finalize() throws Throwable {
		cerrarConexion();
		super.finalize();
	}

}
