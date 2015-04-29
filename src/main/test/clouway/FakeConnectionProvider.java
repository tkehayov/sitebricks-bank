package clouway;

import com.clouway.core.Provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Tihomir Kehayov (kehayov89@gmail.com)
 */
public class FakeConnectionProvider implements Provider<Connection> {

  public Connection get() {

    try {
      return DriverManager.getConnection("jdbc:postgresql://localhost/red-neck-bank", "postgres", "1234");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
