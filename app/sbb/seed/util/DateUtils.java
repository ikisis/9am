package sbb.seed.util;

public abstract class DateUtils {

	public static String formatString8(int year, int month, int day){
		StringBuilder sb = new StringBuilder();
		
		sb.append(Integer.toString(year))
		.append(	month<10 ? "0"+Integer.toString(month) : Integer.toString(month)	)
		.append(	day<10 ? "0"+Integer.toString(day) : Integer.toString(day)	)
		;
		
		return sb.toString();
	}
	
}
