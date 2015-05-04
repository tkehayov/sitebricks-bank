package com.clouway.adapter.http;

import com.clouway.adapter.db.DataStorage;
import com.clouway.adapter.http.bank.CurrentUser;
import com.clouway.adapter.http.security.ConnectionFilter;
import com.clouway.adapter.http.security.SecurityFilter;
import com.clouway.adapter.http.security.SessionFilter;
import com.clouway.core.ConnectionProviderConnection;
import com.clouway.core.ProviderConnection;
import com.clouway.core.RepositoryModule;
import com.clouway.core.Storage;
import com.clouway.core.TransactionHistory;
import com.clouway.core.validator.RegexValidator;
import com.clouway.core.validator.Validator;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.SitebricksModule;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
public class AppConfig extends GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new ServletModule() {
      @Override
      protected void configureServlets() {
        filter("/*").through(ConnectionFilter.class);
        filter("/*").through(SessionFilter.class);
        filter("/profile/*").through(SecurityFilter.class);
      }
    }, new SitebricksModule() {

      @Override
      protected void configureSitebricks() {
//        embed(HelloWorld.class).as("Hello");
        scan(AppConfig.class.getPackage());
//        at("/profile/transaction-history").show(TransactionHistory.class);
      }
    });

  }
}
