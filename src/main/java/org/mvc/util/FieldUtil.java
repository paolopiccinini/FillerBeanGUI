package org.mvc.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import com.thoughtworks.xstream.XStream;

public class FieldUtil {

	public static void setProperty(final Object object, final Field field, final Object value) throws IllegalAccessException {
        boolean access = field.isAccessible();
        field.setAccessible(true);
        field.set(object, value);
        field.setAccessible(access);
    }
	
	public static Object getProperty(final Object object, final Field field) throws IllegalAccessException {
        Object obj = null;
		boolean access = field.isAccessible();
        field.setAccessible(true);
        obj = field.get(object);
        field.setAccessible(access);
        return obj;
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
	
	private static <T> ArrayList<Method> retrieveMethods(T objectToFill) {
    	ArrayList<Method> methods = new ArrayList<Method>();
    	Class<?> c = objectToFill.getClass();
    	while (!c.equals(Object.class)) {
			if (c.getDeclaredFields() != null) {
				methods.addAll(Arrays.asList(c.getDeclaredMethods()));
				c = c.getSuperclass();
			}
    	}
    	return methods;
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
			SimpleDateFormat sdf = new SimpleDateFormat(StringConstants.DATEFORMAT);
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
	public static <T> void initialize(T objectToFill) throws IllegalAccessException {
		String path = System.getProperty(StringConstants.USERDIR) + StringConstants.FOLDER;
    	File f = new File(path + objectToFill.getClass().getName());
    	if(f.exists()) {
    		objectToFill = (T) new XStream().fromXML(f);
    	}
		ArrayList<Field> fields = FieldUtil.retrieveFields(objectToFill);
		for(Field field : fields){
			if(FieldUtil.getProperty(objectToFill, field) == null) {
				if (StringConstants.HASHMAP.equals(Validator.getGenericInstanceClass(field.getType()))) {
					try {
						FieldUtil.setProperty(objectToFill, field, new HashMap());
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
				} else if (StringConstants.ARRAYLIST.equals(Validator.getGenericInstanceClass(field.getType()))) {
					try {
						FieldUtil.setProperty(objectToFill, field, new ArrayList());
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	public static Method getMethod(String methodName, Class<?> clazz) {
		Method[] metodi = clazz.getDeclaredMethods();
		for(Method metodo : metodi){
			if (methodName.equals(metodo.getName())){
				return metodo;
			}
		}
		return null;
	}
	
	private static Method findMethod(String prefix, ArrayList<Method> methods, String fieldName) {
		String nameMethod = prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		for (Method m : methods) {
			if (m.getName().equals(nameMethod)) {
				return m;
			}
		}
		return null;
	}
	
	public static <T> String getExistingFieldValue(Field field, T objectToFill) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Method metodo = null;
        if(field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
        	metodo = findMethod(StringConstants.IS, FieldUtil.retrieveMethods(objectToFill), field.getName());
		} else {
			metodo = findMethod(StringConstants.GET, FieldUtil.retrieveMethods(objectToFill), field.getName());
		}
        Object value = metodo.invoke(objectToFill);
        if(value != null)
        	return String.valueOf(value);
        else
        	return null;
	}
	
	public static <T> String getExistingEnumValue(Field field, T objectToFill) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Method metodo = null;
		metodo = findMethod(StringConstants.GET, FieldUtil.retrieveMethods(objectToFill), field.getName());
        Object value = metodo.invoke(objectToFill);
        if(value != null)
        	return String.valueOf(value);
        else
        	return null;
	}
	
}
