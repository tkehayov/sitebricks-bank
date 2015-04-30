package com.clouway.adapter.http.bank;

import com.clouway.adapter.db.PersistentTransactionRepository;
import com.clouway.adapter.db.TransactionRepository;
import com.clouway.core.Provider;
import com.clouway.core.RepositoryModule;
import com.clouway.core.TransactionHistory;
import com.clouway.core.User;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;

import java.util.List;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
@At("/profile/transaction-history")
@Show("transaction-history.html")
public class TransactionHistoryPage {
  public Integer page = 1;
  private List<TransactionHistory> transactions;
  private Provider<User> user;

  @Inject
  public TransactionHistoryPage(Provider<User> user) {
    this.user = user;
  }

  @Get

  public void history() {
    Injector injector = Guice.createInjector(new RepositoryModule());
    TransactionRepository transaction = injector.getInstance(PersistentTransactionRepository.class);
    user.get().getId();
    transactions = transaction.limit(2, page, 2776);

  }

  public List<TransactionHistory> getTransactions() {
    return transactions;
  }
}
