package com.clouway.adapter.http;


import com.clouway.adapter.db.PersistentSessionRepository;
import com.clouway.core.Clock;
import com.clouway.core.Repository;
import com.clouway.core.RepositoryModule;
import com.clouway.core.UserSession;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.session.JDBCSessionManager.Session;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
@Singleton
public class SecurityFilter implements Filter {
  private PersistentSessionRepository repository;
  private String notEmptyCookieContent;

  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) req;
    HttpServletResponse servletResponse = (HttpServletResponse) resp;
    List<Cookie> cookies = Arrays.asList(servletRequest.getCookies());
    String cookieContent = getCookieContent(cookies, "user");

    if (cookieContent != null) {
      notEmptyCookieContent = cookieContent;
    }

    if (cookieContent == null || !isValidCookieInRepository(cookies)) {
      deleteCookieFromRepository(notEmptyCookieContent);
      deleteCookieFromUser(cookies, servletResponse);

      servletResponse.sendRedirect("/login");
      return;
    }

    filterChain.doFilter(req, resp);
  }

  public void init(FilterConfig filterConfig) throws ServletException {
    Injector injector = Guice.createInjector(new RepositoryModule());
//    RepositoryFactory factory = new RepositoryFactory();
//    repository = factory.getRepository(PersistentSessionRepository.class);
    repository = injector.getInstance(PersistentSessionRepository.class);
  }

  public void destroy() {

  }

  private void deleteCookieFromUser(List<Cookie> cookies, HttpServletResponse servletResponse) {
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("user")) {
        cookie.setMaxAge(0);
        servletResponse.addCookie(cookie);
        return;
      }
    }
  }

  private void deleteCookieFromRepository(String cookieContent) {
    UserSession cookieToDelete = repository.findOne(cookieContent);
    repository.delete(cookieToDelete);
  }

  private boolean isValidCookieInRepository(List<Cookie> cookies) {
    Clock tiktak = new Clock() {
      public Long currentTime() {
        return new Date().getTime();
      }
    };
    String userCookie = getCookieContent(cookies, "user");

    UserSession userSession = repository.findOne(userCookie);
    if (userSession == null) {
      return false;
    }

    Long expires = userSession.getExpires();
    if (expires < tiktak.currentTime()) {
      return false;
    }
    return userSession != null;
  }

  private String getCookieContent(List<Cookie> cookies, String key) {
    String cookieValue = null;
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(key)) {
        cookieValue = cookie.getValue();
        break;
      }
    }
    return cookieValue;
  }
}