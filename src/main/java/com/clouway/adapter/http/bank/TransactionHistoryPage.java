package com.clouway.adapter.http.bank;

import com.clouway.adapter.db.PersistentTransactionRepository;
import com.clouway.adapter.db.TransactionRepository;
import com.clouway.core.RepositoryModule;
import com.clouway.core.TransactionHistory;
import com.google.inject.Guice;
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
  public Integer page = 0;

  @Get
  public void history() {
    Injector injector = Guice.createInjector(new RepositoryModule());
    TransactionRepository transaction = injector.getInstance(PersistentTransactionRepository.class);
    List<TransactionHistory> limit = transaction.limit(5, 2, 2776);
    for (TransactionHistory transactionHistory : limit) {
      System.out.println(transactionHistory.transactionType);
    }
    System.out.println(limit);
    System.out.println("ddd");
  }
}
