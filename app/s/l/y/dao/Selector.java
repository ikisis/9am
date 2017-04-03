package s.l.y.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import s.l.y.dao.bind.iBinder;
import s.l.y.dao.context.DAOContext;
import s.l.y.dao.sql.PstmtParamBinder;
import s.l.y.dao.sql.ResultSetGetter;
import s.l.y.dao.sql.SQLExecutable;
import s.l.y.dao.util.CloseableUtils;
import s.l.y.dao.util.NameUtils;

public class Selector {
	
	public static <T> int update(Connection conn, T set, T where) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		
		final ArrayList<Object> params = new ArrayList<>();
		
		Class<?> schema = set.getClass();
		
		sql.append("update ")
		.append(NameUtils.generateTableName(schema))
		.append("\nset ");
		
		for(Method m : schema.getMethods()){
			
			if(m.getName().startsWith("get") && !Modifier.isStatic(m.getModifiers()) && m.getParameterTypes().length == 0 && !m.getDeclaringClass().equals(Object.class)){
				
				
				Object value = getValue(m, set);
				
				if(value == null)continue;
				
				params.add(value);
							
				String fn = m.getName().substring("get".length());

				sql.append(NameUtils.name2dbname(fn))
				.append("= ? ")
				.append(",");
				
			}
			
		}
		
		sql
		.deleteCharAt(sql.length()-1)
		.append("\n")
		.append("where 1=1\n");
		
		schema = where.getClass();
		
		for(Method m : schema.getMethods()){
			
			if(m.getName().startsWith("get") && !Modifier.isStatic(m.getModifiers()) && m.getParameterTypes().length == 0 && !m.getDeclaringClass().equals(Object.class)){
				
				
				Object value = getValue(m, where);
				
				if(value == null)continue;
				
				params.add(value);
							
				String fn = m.getName().substring("get".length());

				sql
				.append("and ")
				.append(NameUtils.name2dbname(fn))
				.append("= ? ");
				
			}
			
		}
		
		sql.deleteCharAt(sql.length()-1).append("\n");
				
		int result = DAOContext.executeUpdate(new SQLExecutable(sql.toString(), conn) {
			
			@Override
			protected void prepare(PreparedStatement p) throws SQLException {
				int seq = 1;

				for(Object v : params){
					iBinder binder = DAOContext.BINDER_MAP.get(NameUtils.className(v.getClass()));
					if(binder != null){
						binder.value2pstmt(p, seq++, v);
					}
				}
			}
		});
		
		return result;
		
	}
	
	private static Object getValue(Method m, Object obj){
		Object ret = null;
		
		m.setAccessible(true);
		
		try {
			ret = m.invoke(obj, new Object[0]);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
		return ret;
	}
	
	
	public static void forUpdate(Connection conn, Sql sql, PstmtSetter pstmtSetter)throws SQLException {
		PreparedStatement p = null;
		
		
		try {
			
			p = conn.prepareStatement(sql.sql());
			
			pstmtSetter.set(new PstmtParamBinder(p));
			
			p.executeUpdate();
			
			
		} catch (SQLException e) {
			throw e;
		} finally{
			
			CloseableUtils.close(p);
			
		}
	}
	
	public static void single(Connection c, Sql sql, PstmtSetter pstmtSetter, ResultSetUser1 resultSetUser) throws SQLException {
		single(c, new Fetchable() {
			
			@Override
			public boolean use(ResultSetGetter r) throws SQLException {	
				resultSetUser.use(r);	
				return false;
			}
			
			@Override
			public String sql() {	return sql.sql();	}
			
			@Override
			public void set(PstmtParamBinder p) throws SQLException {	pstmtSetter.set(p);	}
		});
	}
	
	private static void single(Connection conn, Fetchable fetchable) throws SQLException{
		
		PreparedStatement p = null;
		
		ResultSet r = null;
		
		try {
			
			p = conn.prepareStatement(fetchable.sql());
			
			fetchable.set(new PstmtParamBinder(p));
			
			r = p.executeQuery();
			
			if(r.next() && fetchable.use(new ResultSetGetter(r)));
			
			
		} catch (SQLException e) {
			throw e;
		} finally{
			
			CloseableUtils.close(p, r);
			
		}
		
	}
	
	public static void fetch(Connection c, Sql sql, PstmtSetter pstmtSetter, ResultSetUser resultSetUser) throws SQLException {
		fetch(c, new Fetchable() {
			
			@Override
			public boolean use(ResultSetGetter r) throws SQLException {	return resultSetUser.use(r);	}
			
			@Override
			public String sql() {	return sql.sql();	}
			
			@Override
			public void set(PstmtParamBinder p) throws SQLException {	pstmtSetter.set(p);	}
		});
	}

	private static void fetch(Connection conn, Fetchable fetchable) throws SQLException{
		
		PreparedStatement p = null;
		
		ResultSet r = null;
		
		try {
			
			p = conn.prepareStatement(fetchable.sql());
			
			fetchable.set(new PstmtParamBinder(p));
			
			r = p.executeQuery();
			
			while(r.next() && fetchable.use(new ResultSetGetter(r)));
			
			
		} catch (SQLException e) {
			throw e;
		} finally{
			
			CloseableUtils.close(p, r);
			
		}
		
	}
	
	private interface Fetchable{
		public String sql();
		public void set(PstmtParamBinder p) throws SQLException;
		public boolean use(ResultSetGetter r) throws SQLException;
	}
	
	public interface Sql{
		public String sql();
	}
	
	public interface PstmtSetter{
		public void set(PstmtParamBinder p) throws SQLException;
	}
	
	public interface ResultSetUser{

		public boolean use(ResultSetGetter r) throws SQLException;
	}
	
	public interface ResultSetUser1{

		public void use(ResultSetGetter r) throws SQLException;
	}
	

	
}
