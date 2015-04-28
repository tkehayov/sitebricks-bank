package com.clouway.adapter.http;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.SitebricksModule;
import com.google.sitebricks.SitebricksServletModule;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
public class AppConfig extends GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new SitebricksModule() {

      @Override
      protected void configureSitebricks() {

        scan(AppConfig.class.getPackage());
      }
    }, new ServletModule() {
      @Override
      protected void configureServlets() {
        filter("/*").through(SessionFilter.class);
        filter("/profile/*").through(SecurityFilter.class);
      }
    });

  }
}
