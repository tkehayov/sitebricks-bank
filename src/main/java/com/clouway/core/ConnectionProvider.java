package com.clouway.core;

import com.clouway.adapter.http.security.ConnectionFilter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
public class ConnectionProvider implements Provider<Connection> {
  public Connection get() {
    return ConnectionFilter.threadLocal.get();

  }
}
