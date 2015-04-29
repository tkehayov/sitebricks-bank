package clouway;

import com.clouway.adapter.db.DataStorage;
import com.clouway.adapter.db.PersistentUserRepository;
import com.clouway.core.ConnectionProvider;
import com.clouway.core.Provider;
import com.clouway.core.Repository;
import com.clouway.core.Storage;
import com.clouway.core.User;
import org.junit.rules.ExternalResource;

import java.sql.Connection;

/**
 * @author Tihomir Kehayov (kehayov89@gmail.com)
 */
public class RegisterUser extends ExternalResource {

  @Override
  protected void before() throws Throwable {
    final String username = "georges";
    final String password = "georges";

    Provider<Connection> provider = new ConnectionProvider();

    Storage storage = new DataStorage(provider);
    Repository userRepository = new PersistentUserRepository(storage);
    userRepository.add(new User(username, password));
  }


}
