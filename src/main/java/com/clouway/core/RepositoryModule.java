package com.clouway.core;

import com.clouway.adapter.db.DataStorage;
import com.clouway.adapter.db.PersistentSessionRepository;
import com.clouway.adapter.db.PersistentUserRepository;
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
    bind(Repository.class).annotatedWith(Names.named("userRepository")).to(PersistentUserRepository.class);
    bind(Repository.class).annotatedWith(Names.named("sessionRepository")).to(PersistentSessionRepository.class);
    bind(Storage.class).to(DataStorage.class);
    bind(Validator.class).to(RegexValidator.class);

  }

  @Provides
  Provider<Connection> provide() {
    Provider<Connection> provider = new ConnectionProvider();

    return provider;
  }
}
