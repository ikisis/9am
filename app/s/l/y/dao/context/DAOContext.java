package s.l.y.dao.context;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.Map;

import s.l.y.dao.bind.iBinder;
import s.l.y.dao.sql.SQLExecutable;
import s.l.y.dao.util.CloseableUtils;
import static s.l.y.dao.context.ContextInitializer.initBinderMap;

public class DAOContext {
	
	public static final Map<String, iBinder> BINDER_MAP = initBinderMap();


	private static ThreadLocal<SQLExecutable> tl = new ThreadLocal<SQLExecutable>();
	
	
	public static int executeUpdate(SQLExecutable exec) throws SQLException{
		
		tl.set(exec);
		
		try{ return exec.prepare().executeUpdate(); } finally{
			
			CloseableUtils.close(exec);
			tl.remove();
			
		}
	}

	public static int[] executeBatch(SQLExecutable exec) throws SQLException{
		
		tl.set(exec);
		
		try{ return exec.prepare().executeBatch(); } finally{
			
			CloseableUtils.close(exec);
			tl.remove();
			
		}
	}
	
	public static void addCloseable(Closeable c){
		if(tl == null)throw new IllegalStateException("SQLExecutable is not setted");
		tl.get().addCloseable(c);
	}
	

}
