package sbb.seed;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import play.db.DB;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import sbb.seed.function.SQLConsumer;
import sbb.seed.function.SQLConsumer1;
import sbb.seed.util.QuietlyUtils;

public class SimpleController extends Controller{

	private final DataSource ds = DB.getDataSource();
	
	protected DataSource DS(){
		
		
		
		return ds;
	}
	
	protected <T> T reqjson2obj(Class<T> clazz){
		return Json.fromJson(request().body().asJson(), clazz);
	}
	
	protected void runWithDS(SQLConsumer consumer){
		Connection conn = null;
		try{
		
			conn = ds.getConnection();
			
			consumer.accept(conn);
					
		}catch(SQLException e){
			
			throw new RuntimeException(e);
			
		}finally{
			
			QuietlyUtils.close(conn);
			
		}
	}
	
	protected <T> void runWithDS(Class<T> tClazz, SQLConsumer1<T> consumer){
		Connection conn = null;
		try{
		
			conn = ds.getConnection();
			
			T t = reqjson2obj(tClazz);
			
			consumer.accept(conn, t);
					
		}catch(SQLException e){
			
			throw new RuntimeException(e);
			
		}finally{
			
			QuietlyUtils.close(conn);
			
		}
		
	}
	
	protected <T> Result okWithDS(Class<T> tClazz, SQLConsumer1<T> consumer){
		
		runWithDS(tClazz, consumer);
		
		return ok();
		
	}
	
	protected Result okWithDS( SQLConsumer consumer){
		
		runWithDS(consumer);
		
		return ok();
		
	}
	
	public R wrap(Object o){
		return new R(0, null, o);
	}
	
	static class R{
		
		final private int resultCode;
		final private String resultMessage;
		final private Object result;
		
		public R(int resultCode, String resultMessage, Object result) {
			super();
			this.resultCode = resultCode;
			this.resultMessage = resultMessage;
			this.result = result;
		}
		
		public int getResultCode() {
			return resultCode;
		}
		
		public String getResultMessage() {
			return resultMessage;
		}
		
		public Object getResult() {
			return result;
		}
		
	}

}
