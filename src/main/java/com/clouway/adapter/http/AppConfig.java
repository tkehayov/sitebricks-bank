package com.clouway.adapter.http;

import com.clouway.adapter.http.security.ConnectionFilter;
import com.clouway.adapter.http.security.SecurityFilter;
import com.clouway.adapter.http.security.SessionFilter;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.SitebricksModule;

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

        scan(AppConfig.class.getPackage());
      }
    });

  }
}
