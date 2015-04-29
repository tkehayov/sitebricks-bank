package com.clouway.adapter.http.security;

import com.google.inject.Singleton;
import org.postgresql.ds.PGPoolingDataSource;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */

@Singleton
public class ConnectionFilter implements Filter {
  public static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
  private PGPoolingDataSource dataSource;

  public void init(FilterConfig filterConfig) throws ServletException {
    dataSource = new PGPoolingDataSource();
    dataSource.setServerName("localhost");
    dataSource.setDatabaseName("red-neck-bank");
    dataSource.setUser("postgres");
    dataSource.setPassword("1234");
    dataSource.setMaxConnections(10);
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (threadLocal.get() == null) {
      threadLocal.set(getConnection());
    }
    chain.doFilter(request, response);
    Connection connection = threadLocal.get();
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    threadLocal.set(null);


  }


  public void destroy() {

  }

  private Connection getConnection() {
    Connection connection = null;
    try {
      connection = dataSource.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }
}
