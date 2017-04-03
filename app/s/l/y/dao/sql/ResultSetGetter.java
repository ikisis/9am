package s.l.y.dao.sql;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ResultSetGetter {
	
	final private ResultSet r;
	
	public ResultSetGetter(ResultSet r){
		this.r = r;
	}
	
	public int getInteger(String s) throws SQLException{
		return r.getInt(s);
	}
	
	public String getString(String s) throws SQLException{
		
		final String val = r.getString(s);
		
		return val;
	}
	
	public InputStream getBinaryStream(String s) throws SQLException{
		return r.getBinaryStream(s);
	}
	
	public Optional<String> getStringOpt(String s) throws SQLException{
		return Optional.ofNullable(getString(s));
	}

}
