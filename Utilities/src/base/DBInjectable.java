package base;

/**
 * 
 */

import java.sql.Connection;

/**
 * @author usuario
 * @version 1.0
 * @deprecated
 */
public interface DBInjectable {

	@Deprecated
	public boolean insert(Connection conn);

	@Deprecated
	public boolean update(Connection conn);

	@Deprecated
	public boolean delete(Connection conn);

	@Deprecated
	public boolean selectOne(Connection conn);

	@Deprecated
	public boolean selectNext(Connection conn);

	@Deprecated
	public boolean getAuth(Connection conn);

}
