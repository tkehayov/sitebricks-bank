package com.clouway.adapter.http.bank;

import com.clouway.adapter.db.PersistentTransactionRepository;
import com.clouway.adapter.db.TransactionRepository;
import com.clouway.core.RepositoryModule;
import com.clouway.core.TransactionHistory;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
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
  private Provider<CurrentUser> currentUser;

  @Inject
  public TransactionHistoryPage(Provider<CurrentUser> currentUser) {
    this.currentUser = currentUser;
  }

  @Get
  public void history() {
    Injector injector = Guice.createInjector(new RepositoryModule());
    Integer id = currentUser.get().get().getId();
    TransactionRepository transaction = injector.getInstance(PersistentTransactionRepository.class);

//    transactions = transaction.limit(3, page, id);
    transactions = transaction.limitLast(2, id);
  }

  public List<TransactionHistory> getTransactions() {
    return transactions;
  }
}
