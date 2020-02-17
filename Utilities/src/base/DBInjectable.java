package base;

/**
 * 
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author usuario
 * @version 1.0
 * 
 */
public interface DBInjectable {

	public default boolean noResultQuery(PreparedStatement st) throws SQLException {
		return !st.execute();
	}

	public default ResultSet resultQUery(PreparedStatement st) throws SQLException {
		return st.executeQuery();
	}
	
	

}
