package org.mvc.filler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class FieldUtil {

	public static void setProperty(final Object object, final Field field, final Object value) throws IllegalAccessException {
        boolean access = field.isAccessible();
        field.setAccessible(true);
        field.set(object, value);
        field.setAccessible(access);
    }
	
	public static <T> ArrayList<Field> retrieveFields(T objectToFill) {
		ArrayList<Field> fields = new ArrayList<Field>();
		Class<?> c = objectToFill.getClass();
		while (!c.equals(Object.class)) {
			if (c.getDeclaredFields() != null) {
				fields.addAll(Arrays.asList(c.getDeclaredFields()));
				c = c.getSuperclass();
			}
		}
		return fields;
	}
	
	public static <T> Object stringToObject(Field field, String value) {
		Object valore = null;

		if(field.getType().isEnum()) {
			@SuppressWarnings("unchecked")
			T[] enumsConstants = (T[])field.getType().getEnumConstants();
            for(T constant: enumsConstants) {
            	if(constant.toString().equals(value)) {
            		valore = constant;
            		break;
            	}
            }
		}
		else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
			valore = Integer.parseInt(value);
		} else if (field.getType().equals(Character.class) || field.getType().equals(char.class)) {
			valore = value.charAt(0);
		} else if (field.getType().equals(Short.class) || field.getType().equals(short.class)) {
			valore = Short.parseShort(value);
		} else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
			valore = Double.parseDouble(value);
		} else if (field.getType().equals(String.class)) {
			valore = value;
		} else if (field.getType().equals(BigDecimal.class)) {
			valore = new BigDecimal(value);
		} else if (field.getType().equals(BigInteger.class)) {
			valore = new BigInteger(value);
		} else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
			valore = Boolean.parseBoolean(value);
		} else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
			valore = Long.parseLong(value);
		} else if (field.getType().equals(Calendar.class)) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			try {
				cal.setTime(sdf.parse(value));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			valore = cal;
		} else if (field.getType().equals(Byte.class) || field.getType().equals(byte.class)) {
			valore = Byte.parseByte(value);
		}
		
		return valore;
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> void initialize(T objectToFill){
		ArrayList<Field> fields = FieldUtil.retrieveFields(objectToFill);
		for(Field field : fields){
			if (Validator.HASHMAP.equals(Validator.getGenericInstanceClass(field.getType()))) {
				try {
					FieldUtil.setProperty(objectToFill, field, new HashMap());
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			} else if (Validator.ARRAYLIST.equals(Validator.getGenericInstanceClass(field.getType()))) {
				try {
					FieldUtil.setProperty(objectToFill, field, new ArrayList());
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public static Method getMethod(String methodName, Class<?> clazz){
		Method[] metodi = clazz.getDeclaredMethods();
		for(Method metodo : metodi){
			if (methodName.equals(metodo.getName())){
				return metodo;
			}
		}
		return null;
	}
	
}
