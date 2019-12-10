package base;


import java.util.ResourceBundle;

/**
 * @author MHGuitarte
 * @version 1.0
 *
 */
public class ReadProps {

	/**
	 * loadProperties(): El método busca un archivo .properties dentro del paquete
	 * resources y devuelve el ResourceBundle resultante. La idea es almacenar las
	 * consultas en el .properties para no tener que incrustarlas en el código java
	 * directamente.
	 */
	public static ResourceBundle loadProperties(String fileName) {
		return ResourceBundle.getBundle("resources." + fileName);
	}

	/**
	 * setQuery(): Este método busca en un archivo .properties (con el método
	 * loadProperties()) la consulta que necesitemos, y nos la devuelve como String.
	 * El objetivo es crear las consultas en el .properties y mandarlas a un
	 * PreparedStatement cuando se necesite en los modelos que vayamos creando.
	 */

	public static String setQuery(String fileName, String query) {
		return loadProperties(fileName).getString(query);
	}

}
