package com.clouway.core;

import com.clouway.adapter.db.DataStorage;
import com.clouway.adapter.db.PersistentSessionRepository;
import com.clouway.adapter.db.PersistentUserRepository;
import com.clouway.adapter.http.bank.CurrentUser;
import com.clouway.core.validator.RegexValidator;
import com.clouway.core.validator.Validator;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.google.inject.servlet.RequestScoped;

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
  ProviderConnection<Connection> provide() {
    ProviderConnection<Connection> providerConnection = new ConnectionProviderConnection();

    return providerConnection;
  }

//  @Provides
//  CurrentUser currentUser() {
//    System.out.println("wewe");
//    CurrentUser currentUser = new CurrentUser();
//    System.out.println(request);
//    String cookieValue = null;
//
//    ProviderConnection<Connection> providerConnection = new ConnectionProviderConnection();
//
//    Storage storage = new DataStorage(providerConnection);
//    UserRepository repository = new PersistentUserRepository(storage);
//    Cookie[] cookies = request.getCookies();
//
//    for (Cookie cookie : cookies) {
//      if (cookie.getName().equals("user")) {
//        cookieValue = cookie.getValue();
//        break;
//      }
//    }
//    UserSession userSession = new PersistentSessionRepository(storage).findOne(cookieValue);
//    return repository.findOne(userSession.userId);
//    return currentUser;
//  }
}
