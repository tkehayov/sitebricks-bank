package com.clouway.adapter.db;

import com.clouway.core.Provider;
import com.clouway.core.RowFetcher;
import com.clouway.core.Storage;
import com.clouway.core.UsernameAlreadyExistException;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Tihomir Kehayov (kehayov89@gmail.com)
 */
public class DataStorage implements Storage {

  private Provider<Connection> provider;

  @Inject
  public DataStorage(Provider<Connection> provider) {
    this.provider = provider;
  }

  public void update(String sql) {
    try {
      PreparedStatement statement = provider.get().prepareStatement(sql);
      statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void update(String sql, Object... args) {
    if (args.length == 0) {
      return;
    }
    PreparedStatement preparedStatement;
    try {
      preparedStatement = provider.get().prepareStatement(sql);
      for (int index = 1; index <= args.length; index++) {
        preparedStatement.setObject(index, args[index - 1]);
      }
      preparedStatement.executeUpdate();
      preparedStatement.close();

    } catch (PSQLException pe) {
      throw new UsernameAlreadyExistException();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public <T> List<T> fetchRows(String query, RowFetcher fetcher) {
    List<T> result = Lists.newArrayList();
    try {
      Statement stmt = provider.get().createStatement();
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        T rowItem = fetcher.fetchRow(rs);
        result.add(rowItem);
      }
      stmt.close();
    } catch (PSQLException exception) {
      exception.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }


  public <T> T fetchRow(String sql, RowFetcher rowFetcher) {
    T rowItem = null;

    try {
      Statement stmt = provider.get().createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      rs.next();
      rowItem = rowFetcher.fetchRow(rs);
    } catch (PSQLException exception) {
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rowItem;
  }

}
