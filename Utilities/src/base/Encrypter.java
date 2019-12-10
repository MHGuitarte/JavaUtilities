package base;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author MHGuitarte
 * @version 1.0
 */
public class Encrypter {

	private static final String algorithm = "SHA-256";
	private static final String srInstance = "SHA1PRNG";

	/*
	 * generateSalt(): Este método genera una clave "salt" aleatoria para añadir una
	 * capa extra de seguridad a la contraseña. El método devuelve un array de bytes
	 * que se debe guardar (generalmente en la base de datos con
	 * PreparedStatement.setBinaryStream()) para comprobar posteriormente las
	 * contraseñas introducidas de cara al acceso de un usuario.
	 */
	public static byte[] generateSalt() {
		try {
			SecureRandom sr = SecureRandom.getInstance(srInstance);
			byte[] salt = new byte[16];
			sr.nextBytes(salt);
			return salt;
		} catch (NoSuchAlgorithmException e) {
			// TODO: Mejorar manejo de errores (sout)
			return null;
		}

	}

	/*
	 * generatePass(): Este método genera, a partir de una cadena de texto
	 * "contraseña", una contraseña segura, basada en el algoritmo de encriptación
	 * SHA-256. Para generar dicha contraseña, se necesita una clave salt, que puede
	 * ser obtenida con el método anterior. La contraseña encriptada se devolverá
	 * como una cadena de texto, lista para ser almacenada (principalmente, en una
	 * BDD).
	 */
	public static String generatePass(String pass, byte[] salt) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(salt);
			byte[] hashedPass = md.digest(stringToHash(pass));
			return hashToString(hashedPass);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

	}

	/*
	 * hashToString(): Método auxiliar que convierte un array de bytes en base 10 a
	 * una cadena de dígitos hexadecimales. El fin de esta conversión de base es
	 * reducir la carga de dígitos demasiados extensos, a la vez que se dificulta la
	 * desencriptación no deseada de la contraseña.
	 */
	private static String hashToString(byte[] hash) {

		StringBuilder builder = new StringBuilder();

		for (byte b : hash) {
			builder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}

		return builder.toString();

	}

	/*
	 * stringToHash(): Método auxiliar que convierte una cadena de texto en un array
	 * de bytes. Su única función es encapsular en un método una función de una
	 * clase externa.
	 */

	private static byte[] stringToHash(String s) {
		return s.getBytes();
	}

	/*
	 * comparePass(): Este método permite comparar de manera rápida una contraseña
	 * obtenida por entrada del usuario con una contraseña y su clave "salt"
	 * obtenidas de una fuente de almacenamiento (BDD, fichero, etc). El método
	 * devuelve un boolean con valor true si las contraseñas coinciden.
	 */
	public static boolean comparePass(String pass, byte[] salt, String dbPass) {
		if (dbPass.equals(generatePass(pass, salt))) {
			return true; // Pass de la BDD y pass del usuario coinciden
		} else {
			return false; // No coinciden las pass
		}
	}

}
