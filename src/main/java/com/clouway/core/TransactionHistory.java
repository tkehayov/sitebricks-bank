package com.clouway.core;

import java.text.SimpleDateFormat;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
public class TransactionHistory {
  public final int userId;
  public final String funds;
  public final String transactionType;
  public final Long date;

  public TransactionHistory(int userId, String funds, String transactionType, Long date) {
    this.userId = userId;
    this.funds = funds;
    this.transactionType = transactionType;
    this.date = date;

  }
}
