package s.l.y.dao.bind.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import s.l.y.dao.bind.iBinder;

public class StringBinder implements iBinder{


	@Override
	public void value2pstmt(PreparedStatement p, int seq, Object v) throws SQLException {
		
		p.setString(seq, (String)v);

	}

}
