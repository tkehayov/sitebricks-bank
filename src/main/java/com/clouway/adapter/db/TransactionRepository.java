package com.clouway.adapter.db;

import com.clouway.core.TransactionHistory;

import java.util.List;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
public interface TransactionRepository {
  void add(TransactionHistory transaction);

  <TransactionHistory> List<TransactionHistory> limit(int maxTransactions, int page, int user_id);
}
