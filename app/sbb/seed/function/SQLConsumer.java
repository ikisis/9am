package sbb.seed.function;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLConsumer {

	public void accept(Connection conn) throws SQLException;
}
