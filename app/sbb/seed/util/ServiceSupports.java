package sbb.seed.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import sbb.seed.function.SQLConsumer;

public class ServiceSupports {

	public static void runWithDS(DataSource ds, SQLConsumer consumer){
		Connection conn = null;
		try{
		
			conn = ds.getConnection();
			
			consumer.accept(ds.getConnection());
					
		}catch(SQLException e){
			
			throw new RuntimeException(e);
			
		}finally{
			
			QuietlyUtils.close(conn);
			
		}
	}
	
}
