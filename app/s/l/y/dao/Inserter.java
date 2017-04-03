package s.l.y.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import s.l.y.dao.bind.iBinder;
import s.l.y.dao.context.DAOContext;
import s.l.y.dao.sql.SQLExecutable;
import s.l.y.dao.util.NameUtils;

public class Inserter {
	
	
	private static MapperFactory mapperFactory = new MapperFactory();
	
	public static <T> void insert(Connection c, final T domain) throws SQLException{
		
		if(domain == null)return;
		
		final Mapper mapper = mapperFactory.getMapper(domain.getClass());

		DAOContext.executeBatch(new SQLExecutable(mapper.sql, c) {

			@Override protected void prepare(PreparedStatement p) throws SQLException {
				
				mapper.parameters(p, domain);
				
				p.addBatch();
			
			}
			
		});
		
	}
	
	public static <T> void insert(Connection c, final T[] domains) throws SQLException{
		
		if(domains == null)return;
		
		Mapper tmp = null;
		
		try{ tmp = mapperFactory.getMapper(domains[0].getClass()); }catch(NullPointerException npe){
			
			for(T domain : domains){
				
				if(domain != null){
					
					tmp = mapperFactory.getMapper(domain.getClass());
					
					break;
					
				}
				
			}
			
		}
		
		final Mapper mapper = tmp;
		
		DAOContext.executeBatch(new SQLExecutable(mapper.sql, c) {
			
			@Override protected void prepare(PreparedStatement p) throws SQLException {
				
				for(T domain : domains){
					
					mapper.parameters(p, domain);
					
					p.addBatch();
					
				}
				
			}
			
		});
		
	}
	
	public static <T> void run(Connection c, final Collection<T> domains) throws SQLException{
		
		if(domains == null)return;
		
		Mapper tmp = null;
		
		for(T domain : domains){
			
			if(domain != null){
				
				tmp = mapperFactory.getMapper(domain.getClass());
				
				break;
				
			}
			
		}
		
		final Mapper mapper = tmp;
		
		DAOContext.executeBatch(new SQLExecutable(mapper.sql, c) {
			
			@Override protected void prepare(PreparedStatement p) throws SQLException {
				
				for(T domain : domains){
					
					mapper.parameters(p, domain);
					
					p.addBatch();
				
				}				
			
			}
			
		});
		
	}
	
	static class Mapper {
		
		private String sql;
		
		private final ArrayList<F> fields = new ArrayList<F>();
		
		class F{
			
			private String tn;
			
			private String fn;
						
			public F(String tn, String fn){
				this.tn = tn;
				this.fn = fn;
				
			}

		}
		
		public Mapper(Class<?> schema){
			
			if(schema == null)throw new IllegalArgumentException();
			
			StringBuilder sql = new StringBuilder(), temp = new StringBuilder();
			
			sql.append("insert into ").append(NameUtils.generateTableName(schema)).append("(");
			
			temp.append("(");
			
			for(Method m : schema.getMethods()){
				
				if(m.getName().startsWith("get") && !Modifier.isStatic(m.getModifiers()) && m.getParameterTypes().length == 0 && !m.getDeclaringClass().equals(Object.class)){
					
					String fn = m.getName().substring("get".length());
					
					this.fields.add(new F(NameUtils.className(m.getReturnType()), fn));
					
					sql.append(NameUtils.name2dbname(fn)).append(",");
					
					temp.append("?,");
					
				}
				
			}
			
			temp.deleteCharAt(temp.length()-1).append(")");
			sql.deleteCharAt(sql.length()-1).append(")\n").append("values").append(temp.toString());	
			
			this.sql = sql.toString();
			
		}

		public void parameters(PreparedStatement p, Object domain){
			
			int seq = 1;

			for(F f : this.fields){
				
				try {
					
					Method m = domain.getClass().getMethod("get" + f.fn, new Class<?>[0]);
					
					m.setAccessible(true);
					
					Object v = m.invoke(domain, new Object[0]);
										
					iBinder binder = DAOContext.BINDER_MAP.get(f.tn);
					
					if(binder != null)
						binder.value2pstmt(p, seq, v);
					else{
						if(v instanceof Serializable){
							
						}
					}
					
					seq++;
					
				} catch (Throwable ignored) {
					//will be removed..
					throw new RuntimeException(ignored);
				}
			}
		}
		
		public void value2pstmt(Object v, PreparedStatement p){
			
		}
	}
	
	static class MapperFactory{
		
		private HashMap<String, Mapper> mapperMap = new HashMap<String, Inserter.Mapper>();
		
		public Mapper getMapper(Class<?> clazz){
			
			Mapper mapper = this.mapperMap.get(NameUtils.className(clazz));
			
			if(mapper == null){
				
				mapper = new Mapper(clazz);
				
				synchronized (mapperMap) {
					
					mapperMap.put(NameUtils.className(clazz), mapper);
					
				}
			}
			
			return mapper;
			
		}
		
	}
	

}
