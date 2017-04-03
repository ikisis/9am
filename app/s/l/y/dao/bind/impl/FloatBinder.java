package s.l.y.dao.bind.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import s.l.y.dao.bind.iBinder;

public class FloatBinder implements iBinder{

	@Override
	public void value2pstmt(PreparedStatement p, int seq, Object v) throws SQLException {
		
		p.setFloat(seq, v == null ? 0F : (Float)v);
		
	}

}
