package base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 
 */

/**
 * 
 * @author MHGuitarte
 * @version 1.0
 *
 */
public class Queries {

	/**
	 * 
	 * Para el uso correcto de esta clase, deben asignarse primero los valores de
	 * los campos estáticos con el método setQueryInfo. Dichos valores son: -
	 * Esquema de la BDD al que pertenece el objeto - Clave principal para las
	 * instrucciones UPDATE, DELETE y SELECT - Clave secundaria (opcional) para las
	 * mismas instrucciones
	 * 
	 * Con estos valores asignados, ejecutamos los métodos que necesitemos,
	 * dependiendo de la instruccion que queramos realizar en la BDD.
	 * 
	 * IMPORTANTE: El objeto que queramos manipular en la BDD debe tener el mismo
	 * nombre que la tabla de la BDD, y sus atributos de clase deben seguir el mismo
	 * orden que los atributos de la tabla.
	 * 
	 * 
	 * 
	 * Nota: Esta clase se encuentra en una fase temprana de desarrollo, por lo que
	 * aún está sujeta a cambios. Los errores pueden llegar a ser frecuentes.
	 * 
	 **/

	/******************** CONSTANTS AND STATIC FIELDS ********************/
	private static final String START_INSERT = "INSERT INTO ";
	private static final String START_UPDATE = "UPDATE ";
	private static final String START_DELETE = "DELETE FROM ";
	private static final String START_SELECT = "SELECT * FROM ";
	private static final String QUERY_LIMIT = " LIMIT 1";
	private static final String QUERY_ORDER = " ORDER BY ";

	private static String SCHEMA_NAME = "public.";
	private static String PK_NAME = "id";
	private static String SECOND_KEY = "";

	/******************** QUERY INFO SETTER ********************/
	public static void setQueryInfo(String schema, String pk, String second) {
		setSchema(schema);
		setPk(pk);
		setFk(second);
	}

	/******************** INSERT ********************/
	@SuppressWarnings("rawtypes")
	public static Object insert(Object obj) {
		Conexion conn = new Conexion();
		PreparedStatement st;
		Object result = null;

		Class classType = obj.getClass();

		String tableName = classType.getCanonicalName().toLowerCase().replaceAll("[A-Z a-z]{1,60}\\.", "") + " (";
		String rows = "";
		String values = "";

		Field[] fields = classType.getDeclaredFields();

		// Recorremos los valores para generar la consulta SQL
		for (Field f : fields) {

			if (f.equals(Arrays.asList(fields).get(fields.length - 1))) {
				// Si el field que estamos recorriendo es el último

				rows += f.getName() + ") VALUES (";
				values += "?)";

			} else {
				rows += f.getName() + ",";
				values += "?,";
			}

		}

		try {
			st = conn.devolverConexion().prepareStatement(START_INSERT + SCHEMA_NAME + tableName + rows + values);

			// Recorremos de nuevo los valores para asignarlos al PreparedStatement
			for (Field f : fields) {

				st.setObject(Arrays.asList(fields).indexOf(f) + 1, findValue(f, obj));

			}

			result = st.execute();

			// TODO: Controlar excepciones
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}

		return result;

	}

	/******************** UPDATE ********************/
	@SuppressWarnings("rawtypes")
	public static Object update(Object obj) {
		int questionMarks = 0;
		Conexion conn = new Conexion();
		PreparedStatement st;
		Object result = null;

		Class classType = obj.getClass();

		String tableName = classType.getCanonicalName().toLowerCase().replaceAll("[A-Z a-z]{1,60}\\.", "") + " SET ";
		String setters = "";

		Field[] fields = classType.getDeclaredFields();

		for (Field f : fields) {
			if (f.equals(Arrays.asList(fields).get(fields.length - 1))) {
				// Si el field que estamos recorriendo es el último

				setters += f.getName() + " = ? WHERE " + PK_NAME + " = ?";
				questionMarks++;
			} else {
				setters += f.getName() + " = ?, ";
			}
			questionMarks++;
		}

		if (!SECOND_KEY.equals("")) {
			setters += " AND " + SECOND_KEY + " = ?";
		}

		try {
			st = conn.devolverConexion().prepareStatement(START_UPDATE + SCHEMA_NAME + tableName + setters);

			// Recorremos de nuevo los valores para asignarlos al PreparedStatement
			for (Field f : fields) {

				st.setObject(Arrays.asList(fields).indexOf(f) + 1, findValue(f, obj));

				if (f.getName().equals(PK_NAME)) {
					st.setObject(questionMarks, findValue(f, obj));
				}

				if (f.getName().equals(SECOND_KEY)) {
					st.setObject(questionMarks + 1, findValue(f, obj));
				}

			}

			result = st.execute(); // Da false aunque realiza bien la consulta. Por qué?

			// TODO: Controlar excepciones
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}

		return result;

	}

	/******************** DELETE ********************/
	@SuppressWarnings("rawtypes")
	public static Object delete(Object obj) {

		Conexion conn = new Conexion();
		PreparedStatement st;
		Object result = null;

		String conditions = "";

		Class classType = obj.getClass();

		String tableName = classType.getCanonicalName().toLowerCase().replaceAll("[A-Z a-z]{1,60}\\.", "") + " WHERE ";

		Field[] fields = classType.getDeclaredFields();

		for (Field f : fields) {

			if (f.getName().equals(PK_NAME)) {
				conditions += f.getName() + " = ?";
			}

			if (f.getName().equals(SECOND_KEY)) {
				conditions += " AND " + f.getName() + " = ?";
			}

		}

		try {
			st = conn.devolverConexion().prepareStatement(START_DELETE + SCHEMA_NAME + tableName + conditions);

			// Recorremos de nuevo los valores para asignarlos al PreparedStatement
			int fieldIndex = 1;
			for (Field f : fields) {

				if (f.getName().equals(PK_NAME)) {
					st.setObject(fieldIndex, findValue(f, obj));
				}

				if (f.getName().equals(SECOND_KEY)) {
					st.setObject(fieldIndex, findValue(f, obj));
				}

			}

			result = st.execute(); // Da false aunque realiza bien la consulta. Por qué?

			// TODO: Controlar excepciones
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}

		return result;

	}

	/******************** SELECT ONE ********************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object selectOne(Object obj) {
		Conexion conn = new Conexion();
		PreparedStatement st;
		ResultSet res;
		Object result = null;

		String conditions = "";

		Class classType = obj.getClass();

		String tableName = classType.getCanonicalName().toLowerCase().replaceAll("[A-Z a-z]{1,60}\\.", "") + " WHERE ";

		// Declaramos el Object result como una nueva instancia de la clase que queremos
		// devolver
		try {
			result = classType.getDeclaredConstructor().newInstance();
		} catch (InstantiationException e1) {
			System.out.println("No se ha podido instanciar la clase");
		} catch (IllegalAccessException e1) {
			System.out.println("Acceso a la invocación de la instancia no permitido");
		} catch (IllegalArgumentException e1) {
			System.out.println("Los argumentos ofrecidos no son correctos");
		} catch (InvocationTargetException e1) {
			System.out.println("Objetivo de invocación erróneo (?)");
		} catch (NoSuchMethodException e1) {
			System.out.println("No existe el método llamado");
		} catch (SecurityException e1) {
			System.out.println("Error de seguridad");
		}

		// Buscamos el filtro principal y el secundario
		Field[] fields = classType.getDeclaredFields();

		for (Field f : fields) {

			if (f.getName().equals(PK_NAME)) {
				conditions += f.getName() + " = ?";
			}

			if (f.getName().equals(SECOND_KEY)) {
				conditions += " AND " + f.getName() + " = ?";
			}

		}

		// Ejecutamos la sentencia con los valores buscados
		try {
			st = conn.devolverConexion().prepareStatement(
					START_SELECT + SCHEMA_NAME + tableName + conditions + QUERY_ORDER + PK_NAME + QUERY_LIMIT);

			for (Field f : fields) {

				if (f.getName().equals(PK_NAME)) {
					st.setObject(1, findValue(f, obj));

				}

				if (f.getName().equals(SECOND_KEY)) {
					st.setObject(2, findValue(f, obj));
				}

			}

			res = st.executeQuery();

			// Asignamos los valores en la instancia que creamos antes de la clase a
			// devolver (almacenada en el Object result)
			if (res.next()) {
				for (Field f : fields) {
					Object aux = null;
					aux = res.getObject(Arrays.asList(fields).indexOf(f) + 1, findType(f, obj));
					f.set(result, aux);

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}

		// Devolvemos result casteado (no es necesario, simplemente por si acaso).
		// No es necesario porque al usar este método se nos pedirá otro casteo
		return classType.cast(result);
	}

	/******************** SELECT NEXT ********************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object selectNext(Object obj) {
		Conexion conn = new Conexion();
		PreparedStatement st;
		ResultSet res;
		Object result = null;

		String pk_condition = "";
		String fk_condition = "";

		Class classType = obj.getClass();

		String tableName = classType.getCanonicalName().toLowerCase().replaceAll("[A-Z a-z]{1,60}\\.", "") + " WHERE ";

		// Declaramos el Object result como una nueva instancia de la clase que queremos
		// devolver
		try {
			result = classType.getDeclaredConstructor().newInstance();
		} catch (InstantiationException e1) {
			System.out.println("No se ha podido instanciar la clase");
		} catch (IllegalAccessException e1) {
			System.out.println("Acceso a la invocación de la instancia no permitido");
		} catch (IllegalArgumentException e1) {
			System.out.println("Los argumentos ofrecidos no son correctos");
		} catch (InvocationTargetException e1) {
			System.out.println("Objetivo de invocación erróneo (?)");
		} catch (NoSuchMethodException e1) {
			System.out.println("No existe el método llamado");
		} catch (SecurityException e1) {
			System.out.println("Error de seguridad");
		}

		// Buscamos el filtro principal y el secundario
		Field[] fields = classType.getDeclaredFields();

		for (Field f : fields) {

			if (f.getName().equals(PK_NAME)) {
				pk_condition += f.getName() + " > ?";
			}

			if (f.getName().equals(SECOND_KEY)) {
				pk_condition = pk_condition.replaceAll(">", "=");
				fk_condition += " AND " + f.getName() + " > ?";
			}

		}

		// Ejecutamos la sentencia con los valores buscados

		try {

			st = conn.devolverConexion().prepareStatement(START_SELECT + SCHEMA_NAME + tableName + pk_condition
					+ fk_condition + QUERY_ORDER + PK_NAME + QUERY_LIMIT);

			for (Field f : fields) {

				if (f.getName().equals(PK_NAME)) {
					st.setObject(1, findValue(f, obj));

				}

				if (f.getName().equals(SECOND_KEY)) {
					st.setObject(2, findValue(f, obj));
				}

			}

			res = st.executeQuery();

			// Asignamos los valores en la instancia que creamos antes de la clase a
			// devolver (almacenada en el Object result)
			if (res.next()) {
				for (Field f : fields) {
					Object aux = null;
					aux = res.getObject(Arrays.asList(fields).indexOf(f) + 1, findType(f, obj));
					f.set(result, aux);

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}

		// Devolvemos el objeto resultante de la consulta o nulo si hay error
		return classType.cast(result);
	}

	/**
	 * 
	 * findValue(Field, Object):Object -> Este método devuelve el valor de un
	 * atributo de tipo desconocido para un Objeto también desconocido, casteado en
	 * su valor correspondiente.
	 * 
	 * El método tiene como fin agilizar y dinamizar la obtención de valores de un
	 * objeto de tipo desconocido para la gestión de consultas sql.
	 * 
	 */
	public static Object findValue(Field f, Object obj) throws IllegalArgumentException, IllegalAccessException {
		Object value;
		switch (f.getType().getName()) {

		case "java.lang.String": {
			value = (String) f.get(obj);
			break;
		}

		case "int": {
			value = f.getInt(obj);
			break;
		}

		case "double": {
			value = f.getDouble(obj);
			break;
		}

		case "float": {
			value = f.getFloat(obj);
			break;
		}

		case "short": {
			value = f.getShort(obj);
			break;
		}

		case "long": {
			value = f.getLong(obj);
			break;
		}

		case "char": {
			value = f.getChar(obj);
			break;
		}

		case "byte": {
			value = f.getByte(obj);
			break;
		}

		case "boolean": {
			value = f.getBoolean(obj);
			break;
		}

		default: {
			value = null;
			break;
		}

		}

		return value;
	}

	/**
	 * 
	 * findType(Field, Object):Class -> Este método devuelve la clase a la que
	 * pertenece un Field de un objeto concreto. El método tiene como fin dinamizar
	 * la recepción de datos de las consultas sql
	 * 
	 **/
	@SuppressWarnings("rawtypes")
	public static Class findType(Field f, Object obj) {

		Class value = null;

		switch (f.getType().getName()) {

		case "java.lang.String": {
			value = String.class;
			break;
		}

		case "int": {
			value = Integer.class;
			break;
		}

		case "double": {
			value = Double.class;
			break;
		}

		case "float": {
			value = Float.class;
			break;
		}

		case "short": {
			value = Short.class;
			break;
		}

		case "long": {
			value = Long.class;
			break;
		}

		case "char": {
			value = Character.class;
			break;
		}

		case "byte": {
			value = Byte.class;
			break;
		}

		case "boolean": {
			value = Boolean.class;
			break;
		}

		default: {
			value = null;
			break;
		}

		}

		return value;
	}

	/******************** STATIC FIELDS SETTERS ********************/
	public static void setSchema(String sCHEMA_NAME) {
		SCHEMA_NAME = sCHEMA_NAME;
	}

	public static void setPk(String pK_NAME) {
		PK_NAME = pK_NAME;
	}

	public static void setFk(String sECOND_KEY) {
		SECOND_KEY = sECOND_KEY;
	}

}
