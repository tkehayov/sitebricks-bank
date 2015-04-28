package com.clouway.core;

import com.clouway.adapter.db.DataStorage;
import com.clouway.core.validator.RegexValidator;
import com.clouway.core.validator.Validator;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

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
    bind(Storage.class).to(DataStorage.class);
    bind(Validator.class).to(RegexValidator.class);
//    bind(GuiceFilter.class).to(SecurityFilter.class);
  }



  @Provides
  Provider<Connection> provide() {
    Provider<Connection> provider = new ConnectionProvider();

    return provider;
  }
}
