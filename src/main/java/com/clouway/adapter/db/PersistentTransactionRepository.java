package com.clouway.adapter.db;

import com.clouway.core.RowFetcher;
import com.clouway.core.Storage;
import com.clouway.core.TransactionHistory;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
public class PersistentTransactionRepository implements TransactionRepository {
  private Storage storage;

  @Inject
  public PersistentTransactionRepository(@Named("transactionRepository") Storage storage) {
    this.storage = storage;
  }

  public void add(TransactionHistory transaction) {
    BigDecimal funds = new BigDecimal(transaction.funds);
    Long date = transaction.date;

    String sql = "insert into transaction_history(user_id, date, funds, type) values(?,?,?,?)";
    storage.update(sql, transaction.userId, date, funds, transaction.transactionType);
  }

  public List<TransactionHistory> limit(int maxTransactions, int page, int user_id) {
    return storage.fetchRows("select user_id, funds, type, date from transaction_history where user_id = " + user_id + " LIMIT " + maxTransactions
            + " OFFSET " + ((maxTransactions * (page - 1))), new RowFetcher() {
      public TransactionHistory fetchRow(ResultSet rs) throws SQLException {
        Integer userId = rs.getInt(1);
        String funds = rs.getString(2);
        String type = rs.getString(3);
        Long date = rs.getLong(4);

        return new TransactionHistory(userId, funds, type, date);
      }
    });
  }

}
