package s.l.y.dao.bind.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import s.l.y.dao.bind.iBinder;

public class IntegerBinder implements iBinder{

	@Override
	public void value2pstmt(PreparedStatement p, int seq, Object v) throws SQLException {
		
		p.setInt(seq, v == null ? 0 : (Integer)v);
	
	}

}
