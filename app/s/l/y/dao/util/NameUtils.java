package s.l.y.dao.util;

import java.io.IOException;
import java.io.StringReader;

public abstract class NameUtils {
	
	public static String className(Class<?> clazz){
		return clazz.getName();
	}
	
	public static String generateTableName(Class<?> clazz){
		
		String cName = clazz.getName().substring(clazz.getName().lastIndexOf('.') +1);		
		
		return name2dbname(cName);
		
	}
	
	public static String name2dbname(String name){
		StringBuilder sb = new StringBuilder();
		
		StringReader sr = new StringReader(name);
		
		int i = 0;
		
		
		
		try {
			
			i = sr.read();
			
			sb.append(Character.toUpperCase((char)i));
			
			while((i = sr.read())!= -1){
				
				
				if(Character.isUpperCase(i)){
					sb.append("_");
				}
				
				sb.append(Character.toUpperCase((char)i));
			}
		} catch (IOException ignored) {

		}
		
		return sb.toString();
	}

}
