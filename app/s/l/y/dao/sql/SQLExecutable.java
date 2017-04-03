package s.l.y.dao.sql;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import s.l.y.dao.util.CloseableUtils;

public abstract class SQLExecutable implements Closeable{
	
	PreparedStatement p;
	
	List<Closeable> closeables;
	
	public SQLExecutable(String sql, Connection c) throws SQLException{
		
		p = c.prepareStatement(sql);

	}
	
	public void addCloseable(Closeable c){
		if(closeables == null)closeables = new ArrayList<Closeable>();
		
		closeables.add(c);
	}
	
	public SQLExecutable prepare() throws SQLException{
		prepare(p);
		
		return this;
	}
	
	public int executeUpdate() throws SQLException{
		return p.executeUpdate();
	}
	
	public int[] executeBatch() throws SQLException{
		
		return p.executeBatch();
	}
	
	@Override
	public void close(){
		CloseableUtils.close(p);
		CloseableUtils.close(closeables);
		
	}
	
	abstract protected void prepare(PreparedStatement p) throws SQLException;
	
}
