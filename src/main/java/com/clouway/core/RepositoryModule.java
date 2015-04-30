package com.clouway.core;

import com.clouway.adapter.db.DataStorage;
import com.clouway.adapter.db.PersistentSessionRepository;
import com.clouway.adapter.db.PersistentUserRepository;
import com.clouway.core.validator.RegexValidator;
import com.clouway.core.validator.Validator;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
public class RepositoryModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(Storage.class).annotatedWith(Names.named("userRepository")).to(DataStorage.class);
    bind(Storage.class).annotatedWith(Names.named("sessionRepository")).to(DataStorage.class);
    bind(Storage.class).annotatedWith(Names.named("balanceRepository")).to(DataStorage.class);
    bind(Storage.class).annotatedWith(Names.named("transactionRepository")).to(DataStorage.class);
    bind(Storage.class).to(DataStorage.class);
    bind(Validator.class).to(RegexValidator.class);
  }

  @Provides
  Provider<Connection> provide() {
    Provider<Connection> provider = new ConnectionProvider();

    return provider;
  }

  @Provides
  User userProvider(HttpServletRequest request) {
    String cookieValue = null;

    Provider<Connection> provider = new ConnectionProvider();
    Storage storage = new DataStorage(provider);
    UserRepository repository = new PersistentUserRepository(storage);
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("user")) {
        cookieValue = cookie.getValue();
        break;
      }
    }

    UserSession userSession = new PersistentSessionRepository(storage).findOne(cookieValue);
    return repository.findOne(userSession.userId);
  }
}
