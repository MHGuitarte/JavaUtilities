import java.lang.reflect.Field;
import java.util.Date;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public interface JSONEncoder extends Encoder.Text<Object> {

    public static final JSONEncoder enc = new JSONEncoder() {

	@Override
	public void init(EndpointConfig arg0) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
	    // TODO Auto-generated method stub

	}
    };

    @Override
    default String encode(Object obj) throws EncodeException {
	String result = "{";
	Class<?> clase = obj.getClass();

	for (Field f : clase.getDeclaredFields()) {
	    try {
		result += findType(f, obj);
		result += ", ";
	    } catch (IllegalArgumentException e) {
		System.out.println("ERROR DE ARGUMENTO MAL");
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		System.out.println("NO SE PUDO ACCEDER A LA CLASE");
		e.printStackTrace();
	    }
	}

	result = result.substring(0, result.length() - 2);
	result += "}";

	return result;
    }

    @SuppressWarnings("rawtypes")
    default String findType(Field f, Object obj) throws IllegalArgumentException, IllegalAccessException {

	String value = "\"" + f.getName() + "\":";

	switch (f.getType().getCanonicalName()) {

	case "java.lang.String": {
	    value += "\"" + (String) f.get(obj) + "\"";
	    break;
	}

	case "int": {
	    value += f.getInt(obj);
	    break;
	}

	case "double": {
	    value += f.getDouble(obj);
	    break;
	}

	case "float": {
	    value += f.getFloat(obj);
	    break;
	}

	case "short": {
	    value += f.getShort(obj);
	    break;
	}

	case "long": {
	    value += f.getLong(obj);
	    break;
	}

	case "char": {
	    value += "\"" + f.getChar(obj) + "\"";
	    break;
	}

	case "byte": {
	    value += f.getByte(obj);
	    break;
	}

	case "boolean": {
	    value += f.getBoolean(obj);
	    break;
	}

	case "java.util.Date": {
	    value += "\"" + (Date) f.get(obj) + "\"";
	    break;
	}

	default: {
	    value = null;
	    break;
	}

	}
	return value;
    }

    public static String parse(Object obj) throws EncodeException {
	return enc.encode(obj);
    }

}
