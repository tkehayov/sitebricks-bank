package com.clouway.adapter.http;

import com.clouway.adapter.db.PersistentSessionRepository;
import com.clouway.core.Clock;
import com.clouway.core.RepositoryModule;
import com.clouway.core.UserSession;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Tihomir Kehayov (kehayov89@gmail.com)
 */
@Singleton
public class SessionFilter implements Filter {
  private Injector injector;

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    System.out.println("filter");
    HttpServletRequest servletRequest = (HttpServletRequest) request;

    List<Cookie> cookies = Arrays.asList(servletRequest.getCookies());

    if (isUserLogged(cookies)) {
      String cookieContent = getCookieContent(cookies, "user");
      updateSessionRepository(cookieContent);
    }
    chain.doFilter(request, response);
  }

  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("asdasdasd");
    injector = Guice.createInjector(new RepositoryModule());
  }


  public void destroy() {

  }

  private void updateSessionRepository(String cookieContent) {
    Clock clock = new Clock() {
      public Long currentTime() {
        return new Date().getTime();
      }
    };
    PersistentSessionRepository repository = injector.getInstance(PersistentSessionRepository.class);
    UserSession userSession = repository.findOne(cookieContent);
    UserSession newUserSession = new UserSession(userSession.userId, userSession.expression).withExpires(clock.currentTime() + 600000);

    repository.update(newUserSession);
  }

  private boolean isUserLogged(List<Cookie> cookies) {
    String userCookie = getCookieContent(cookies, "user");

    PersistentSessionRepository cookieRepository = injector.getInstance(PersistentSessionRepository.class);
    UserSession userSession = cookieRepository.findOne(userCookie);
    if (userSession == null) {
      return false;
    }

    return userSession.expression.equals(userCookie);
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