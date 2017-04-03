package sbb.seed.function;

import java.sql.Connection;
import java.sql.SQLException;


@FunctionalInterface
public interface SQLConsumer1<T> {

	public void accept(Connection conn, T t) throws SQLException;

}
