package base;


import java.sql.SQLException;

@SuppressWarnings("serial")
public class PostgreSQLHandler extends Exception {

	private SQLException error;

	public PostgreSQLHandler(SQLException e) {
		this.error = e;

	}

	public String safeErrorHandling() {
		String responseStatement = "";

		error.printStackTrace();

		switch (error.getSQLState().substring(0, 2)) {

		case "01": {
			responseStatement = "Atención, su transacción puede suponer un peligro";
			break;
		}

		case "02": {
			responseStatement = "Error: No existen datos";
			break;
		}

		case "03": {
			responseStatement = "Error: La instrucción SQL no se encuentra completa";
			break;
		}

		case "08": {
			responseStatement = "Error en la conexión";
			break;
		}

		case "09": {
			responseStatement = "Un disparador se activó en la base de datos";
			break;
		}

		case "0A": {
			responseStatement = "Especificación no soportada";
			break;
		}

		case "0B": {
			responseStatement = "Error en la iniciación de la transacción";
			break;
		}

		case "0F": {
			responseStatement = "Excepción de localizador";

			break;
		}

		case "0L": {
			responseStatement = "Invalid Grantor";
			break;
		}

		case "0P": {
			responseStatement = "Especificación de rol inválida";
			break;
		}

		case "0Z": {
			responseStatement = "Error en los diagnósticos";
			break;
		}

		case "20": {
			responseStatement = "Caso no encontrado";
			break;
		}

		case "21": {
			responseStatement = "Violación de cardinalidad";
			break;
		}

		case "22": {
			responseStatement = "Error en la inserción de datos. Compruébelos e inténtelo de nuevo";
			break;
		}

		case "23": {
			responseStatement = "Violación de la integridad. Compruebe los campos identificativos, los campos referenciales y los campos nulos, por favor.";
			break;
		}

		case "24": {
			responseStatement = "Error de cursor inválido";
			break;
		}

		case "25": {
			responseStatement = "Error en el estado de la transacción";
			break;
		}

		case "26": {
			responseStatement = "Error en la sintaxis SQL";
			break;
		}

		case "27": {
			responseStatement = "Violación de datos en un disparador";
			break;
		}

		case "28": {
			responseStatement = "Autorización inválida";
			break;
		}

		case "2B": {
			responseStatement = "Error en el descriptor de privilegios";
			break;
		}

		case "2D": {
			responseStatement = "Error en la finalización de la transacción";
			break;
		}

		case "2F": {
			responseStatement = "Error de permisos";
			break;
		}

		case "34": {
			responseStatement = "Nombre de cursor inválido";
			break;
		}

		case "38": {
			responseStatement = "Error de permisos";
			break;
		}

		case "39": {
			responseStatement = "Valor nulo no válido o protoclo violado";
			break;
		}

		case "3B": {
			responseStatement = "Error de punto de guardado";
			break;
		}

		case "3D": {
			responseStatement = "Nombre de catálogo inválido";
			break;
		}

		case "3F": {
			responseStatement = "Nombre de esquema inválido";
			break;
		}

		case "40": {
			responseStatement = "Transacción cancelada por parte del servidor";
			break;
		}

		case "42": {
			responseStatement = "Error de sintaxis interna";
			break;
		}

		case "44": {
			responseStatement = "Violacion de la opción WITCH_CHECK";
			break;
		}

		case "53": {
			responseStatement = "Recursos insuficientes";
			break;
		}

		case "54": {
			responseStatement = "Limite de programa excedido";
			break;
		}

		case "55": {
			responseStatement = "Objeto sin estado requerido";
			break;
		}

		case "57": {
			responseStatement = "Error de operador";
			break;
		}

		case "58": {
			responseStatement = "Error de sistema externo";
			break;
		}

		case "72": {
			responseStatement = "Error de versión: versión de PostgreSQL desfasada";
			break;
		}

		case "F0": {
			responseStatement = "Error de archivo de configuración";
			break;
		}

		case "HV": {
			responseStatement = "Error en el Sistema de Envoltura de Datos Ajenos";
			break;
		}

		case "P0": {
			responseStatement = "Error de lenguaje interno";
			break;
		}

		case "XX": {
			responseStatement = "Error interno del servidor";
			break;
		}

		default: {
			responseStatement = "Error Code (?????): Error no encontrado";
			break;
		}
		}

		return responseStatement;
	}

}
