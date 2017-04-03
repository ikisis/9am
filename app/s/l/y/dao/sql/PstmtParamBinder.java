package s.l.y.dao.sql;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PstmtParamBinder{
	
	private final PreparedStatement p;
	
	private int index = 1;
	
	synchronized private int i(){
		return index++;
	}
	
	public PstmtParamBinder(PreparedStatement p) {
		this.p = p;
	}
	
	public PstmtParamBinder bind(String a) throws SQLException{
		
		p.setString(i(), a);
		
		return this;
	}
	
	public PstmtParamBinder bind(double a) throws SQLException{
		
		p.setDouble(i(), a);
		
		return this;
	}	
	
	public PstmtParamBinder bind(float a) throws SQLException{
		
		p.setFloat(i(), a);
		
		return this;
	}	
	
	public PstmtParamBinder bind(int a) throws SQLException{
		
		p.setInt(i(), a);
		
		return this;
	}	
	
	
	public PstmtParamBinder bind(long a) throws SQLException{
		
		p.setLong(i(), a);
		
		return this;
	}
	
	public PstmtParamBinder bind(InputStream is, long len) throws SQLException{
		
		p.setBinaryStream(i(), is, len);
		
		return this;
	}
	
}
