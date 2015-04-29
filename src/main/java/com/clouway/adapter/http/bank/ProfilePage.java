package com.clouway.adapter.http.bank;

import com.clouway.adapter.db.PersistentBalanceRepository;
import com.clouway.adapter.db.PersistentSessionRepository;
import com.clouway.core.Balance;
import com.clouway.core.Repository;
import com.clouway.core.RepositoryModule;
import com.clouway.core.UserSession;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;

import javax.inject.Provider;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
@At("/profile/welcome")
@Show("/welcome.html")
public class ProfilePage {
  private final Provider<HttpServletRequest> request;
  private final Provider<HttpServletResponse> response;
  public String username = "";
  public BigDecimal balance;

  @Inject
  public ProfilePage(Provider<HttpServletRequest> request, Provider<HttpServletResponse> response) {
    this.request = request;
    this.response = response;
  }

  @Get
  public void balance() {
    List<Cookie> cookies = Arrays.asList(request.get().getCookies());
    FluentIterable<Cookie> filter = getCookieContent(cookies);

    Balance userBalance = getBalance(filter);
    username = userBalance.getUsername();
    balance = userBalance.balance();
  }

  private Balance getBalance(FluentIterable<Cookie> filter) {
    Injector injector = Guice.createInjector(new RepositoryModule());
    Repository<Balance> repository = injector.getInstance(PersistentBalanceRepository.class);

    Integer userId = getUserId(filter);

    return repository.findOne(new Balance(userId));
  }

  private Integer getUserId(FluentIterable<Cookie> filter) {
    Injector injector = Guice.createInjector(new RepositoryModule());
    PersistentSessionRepository repository = injector.getInstance(PersistentSessionRepository.class);
    UserSession session = repository.findOne(filter.get(0).getValue());

    return session.userId;
  }

  private FluentIterable<Cookie> getCookieContent(List<Cookie> cookies) {
    return FluentIterable.from(cookies).filter(new Predicate<Cookie>() {
      public boolean apply(Cookie cookie) {
        return cookie.getName().equals("user");
      }
    });


  }
}
