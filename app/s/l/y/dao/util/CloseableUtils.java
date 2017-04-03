package s.l.y.dao.util;

import java.io.Closeable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

public abstract class CloseableUtils {

	public static void close(Closeable c){
		if(c!=null)
			try {c.close();} catch (Throwable ignored) {

			}
	}
	
	public static void close(Collection<Closeable> cs){
		if(cs!=null){
			for(Closeable c : cs){
				close(c);
			}
		}
	}
	
	public static void close(PreparedStatement p){
		if(p!=null)
			try {p.close();} catch (Throwable ignored) {

			}
	}
	
	
	public static void close(ResultSet r){
		if(r!=null){
			try {	r.close();	} catch (Throwable ignored) {

			}
		}
	}
	
	public static void close(PreparedStatement p, ResultSet r){
		close(p);
		close(r);
	}

}
