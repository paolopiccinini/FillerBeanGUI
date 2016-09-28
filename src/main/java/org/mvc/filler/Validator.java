package org.mvc.filler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public final class Validator {
	
	public static final String HASHMAP = "HashMap";
	public static final String ARRAYLIST = "ArrayList";
	public static final String ENUM = "Enum";
	public static final String PRIMITIVE = "Primitive";
	public static final String CUSTOM = "Custom";
	public static final String INVALID = "Invalid";
	public static final Map<Class<?>, Object> tipiPrimitivi = new HashMap<Class<?>, Object>();
	
	static {
		tipiPrimitivi.put(Integer.class, 0);
		tipiPrimitivi.put(Character.class, '-');
		tipiPrimitivi.put(Short.class, 0);
		tipiPrimitivi.put(Double.class, 0.0);
		tipiPrimitivi.put(String.class, "0");
		tipiPrimitivi.put(BigDecimal.class, BigDecimal.ZERO);
		tipiPrimitivi.put(BigInteger.class, BigInteger.ZERO);
		tipiPrimitivi.put(Boolean.class, Boolean.TRUE);
		tipiPrimitivi.put(Long.class, 0L);
		tipiPrimitivi.put(Calendar.class, Calendar.getInstance());
		tipiPrimitivi.put(char.class, '-');
		tipiPrimitivi.put(int.class, 0);
		tipiPrimitivi.put(long.class, 0L);
		tipiPrimitivi.put(byte.class, 0);
		tipiPrimitivi.put(Byte.class, 0);
		tipiPrimitivi.put(double.class, 0.0);
		tipiPrimitivi.put(boolean.class, true);
		tipiPrimitivi.put(short.class, 0);
	}
	
	public static boolean isHashMap(Class<?> clazz){
		return(clazz.isAssignableFrom(HashMap.class));
	}
	
	public static boolean isArrayList(Class<?> clazz){
		return(clazz.isAssignableFrom(ArrayList.class));
	}
	
	public static boolean isEnum(Class<?> clazz){
		return(clazz.isEnum());
	}
	
	public static boolean isPrimitive(Class<?> clazz){
		return(tipiPrimitivi.containsKey(clazz));
	}
	
	public static boolean isFillable(Class<?> clazz){
		return clazz != Object.class;
	}
	
	public static String getGenericInstanceClass(Class<?> clazz){
		if(isHashMap(clazz))
			return HASHMAP;
		else if(isArrayList(clazz))
			return ARRAYLIST;
		else if(isEnum(clazz))
			return ENUM;
		else if(isPrimitive(clazz))
			return PRIMITIVE;
		else if(isFillable(clazz))
			return CUSTOM;
		else
			return INVALID;
	}
}
