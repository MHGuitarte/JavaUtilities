/**
 * 
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author usuario
 *
 */
public class User {

	private static final int BAJA = 0;
	private static final int REGISTRADO_NO_VALIDADO = 1;
	private static final int REGISTRADO_VALIDADO = 2;

	private String mail;
	private byte[] salt;
	private String dbPass;

	public User() {

	}

	public User(String mail, byte[] salt, String dbPass) {
		this.mail = mail;
		this.salt = salt;
		this.dbPass = dbPass;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public String getDbPass() {
		return dbPass;
	}

	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}
	
	

	/**
	*	@author mhg
	**/

	public boolean insert(Connection conn) throws SQLException {
		PreparedStatement st = conn
				.prepareStatement("INSERT INTO " + "public.users" + " (" + "mail, pass, salt, estado" + ") VALUES (" + values + ")");

		//TODO: Insert your code here

		

		return !st.execute();
	}

}
