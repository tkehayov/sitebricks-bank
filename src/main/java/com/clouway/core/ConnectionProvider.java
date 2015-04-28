package com.clouway.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
public class ConnectionProvider implements Provider<Connection> {
  public Connection get() {
    try {
      Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/redneckbank", "postgres", "1234");
      return connection;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
