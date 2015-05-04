package com.clouway.adapter.http.bank;

import com.clouway.adapter.db.DataStorage;
import com.clouway.adapter.db.PersistentSessionRepository;
import com.clouway.adapter.db.PersistentUserRepository;
import com.clouway.core.ConnectionProviderConnection;
import com.clouway.core.ProviderConnection;
import com.clouway.core.Storage;
import com.clouway.core.User;
import com.clouway.core.UserRepository;
import com.clouway.core.UserSession;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
@RequestScoped
public class CurrentUser implements Provider<User> {
  private Provider<HttpServletRequest> request;

  @Inject
  public CurrentUser(Provider<HttpServletRequest> request) {
    this.request = request;
  }

  private int getId() {
    String cookieValue = null;
    ProviderConnection<Connection> providerConnection = new ConnectionProviderConnection();

    Storage storage = new DataStorage(providerConnection);
    UserRepository repository = new PersistentUserRepository(storage);
    Cookie[] cookies = request.get().getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("user")) {
        cookieValue = cookie.getValue();
        break;
      }
    }

    UserSession userSession = new PersistentSessionRepository(storage).findOne(cookieValue);
    return repository.findOne(userSession.userId).getId();
  }

  public User get() {
    String cookieValue = null;
    ProviderConnection<Connection> providerConnection = new ConnectionProviderConnection();

    Storage storage = new DataStorage(providerConnection);
    UserRepository userRepository = new PersistentUserRepository(storage);
    Cookie[] cookies = request.get().getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("user")) {
        cookieValue = cookie.getValue();
        break;
      }
    }

    UserSession userSession = new PersistentSessionRepository(storage).findOne(cookieValue);
    User one = userRepository.findOne(userSession.userId);
    return one;
  }
}
