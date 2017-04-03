package s.l.y.dao.bind;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface iBinder {
	
	void value2pstmt(PreparedStatement p,int seq, Object v) throws SQLException;

}
