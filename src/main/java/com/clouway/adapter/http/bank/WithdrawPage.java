package com.clouway.adapter.http.bank;

import com.clouway.adapter.db.PersistentBalanceRepository;
import com.clouway.adapter.db.PersistentSessionRepository;
import com.clouway.core.Balance;
import com.clouway.core.NegativeBalanceException;
import com.clouway.core.Repository;
import com.clouway.core.RepositoryModule;
import com.clouway.core.UserSession;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
@At("/profile/withdraw")
@Show("/withdraw.html")
public class WithdrawPage {
  public String fundsMessage;
  public String type;

  public String funds;
  private final Provider<HttpServletRequest> req;

  @Inject
  public WithdrawPage(Provider<HttpServletRequest> req) {
    this.req = req;
  }

  @Post
  public void transactionFunds() {
    List<Cookie> cookies = Arrays.asList(req.get().getCookies());
    FluentIterable<Cookie> filter = getCookieContent(cookies);
    Integer userId = getUserId(filter);

    fundsMessage = "correct";
    try {
      BigDecimal funds = new BigDecimal(this.funds);
      if (funds.signum() == -1) {
        throw new NegativeBalanceException();
      }

      if (type.equals("withdraw")) {
        executeRepositoryTransaction(userId, funds.negate());
      }

    } catch (NumberFormatException e) {
      fundsMessage = "incorrect";
    } catch (NegativeBalanceException e) {
      fundsMessage = "incorrect";
    }
  }

  private Integer getUserId(FluentIterable<Cookie> filter) {
    Injector injector = Guice.createInjector(new RepositoryModule());
    PersistentSessionRepository repository = injector.getInstance(PersistentSessionRepository.class);

    UserSession cookie = repository.findOne(filter.get(0).getValue());

    return cookie.userId;
  }

  private FluentIterable<Cookie> getCookieContent(List<Cookie> cookies) {
    return FluentIterable.from(cookies).filter(new Predicate<Cookie>() {
      public boolean apply(Cookie cookie) {
        return cookie.getName().equals("user");
      }
    });
  }

  private void executeRepositoryTransaction(Integer userId, BigDecimal funds) {
    Injector injector = Guice.createInjector(new RepositoryModule());
    Repository<Balance> repository = injector.getInstance(PersistentBalanceRepository.class);
    BigDecimal userBalance = getBalance(repository, userId).balance();
    Balance balance = new Balance(userId).deposit(userBalance.add(funds));

    repository.update(balance);
  }

  private Balance getBalance(Repository<Balance> repository, Integer userId) {
    return repository.findOne(new Balance(userId));
  }

}
