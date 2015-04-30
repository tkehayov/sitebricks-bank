package clouway;

import com.clouway.adapter.db.DataStorage;
import com.clouway.adapter.db.PersistentTransactionRepository;
import com.clouway.adapter.db.TransactionRepository;
import com.clouway.core.Provider;
import com.clouway.core.Storage;
import com.clouway.core.TransactionHistory;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import static com.clouway.core.CalendarUtil.dateOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
public class TransactionHistoryTest {
  @Rule
  public DataStoreCleaner cleaner = new DataStoreCleaner();

  @Test
  public void happyPath() {
    Provider<Connection> fakeProvider = new FakeConnectionProvider();
    Storage storage = new DataStorage(fakeProvider);

    TransactionRepository repository = new PersistentTransactionRepository(storage);
    Long date = dateOf(2014, 1, 23);

    repository.add(new TransactionHistory(43, "23.2", "deposit", date));
    repository.add(new TransactionHistory(41, "21.2", "withdraw", date));
    repository.add(new TransactionHistory(43, "23.2", "deposit", date));
    repository.add(new TransactionHistory(44, "3.2", "withdraw", date));
    repository.add(new TransactionHistory(43, "2.2", "deposit", date));
    repository.add(new TransactionHistory(43, "41.2", "withdraw", date));
    repository.add(new TransactionHistory(43, "1.2", "withdraw", date));

    List<TransactionHistory> limit = repository.limit(5, 1, 43);

    assertThat(limit.size(), is(5));
    assertThat(limit.get(0).date, is(date));
    assertThat(limit.get(0).transactionType, is("deposit"));
    assertThat(limit.get(0).userId, is(43));
  }

  @Test
  public void limitSecondPage() {
    Provider<Connection> fakeProvider = new FakeConnectionProvider();
    Storage storage = new DataStorage(fakeProvider);

    TransactionRepository repository = new PersistentTransactionRepository(storage);
    Long date = dateOf(2014, 1, 23);

    repository.add(new TransactionHistory(43, "23.2", "deposit", date));
    repository.add(new TransactionHistory(41, "21.2", "withdraw", date));
    repository.add(new TransactionHistory(43, "23.2", "deposit", date));
    repository.add(new TransactionHistory(44, "3.2", "withdraw", date));
    repository.add(new TransactionHistory(43, "2.2", "deposit", date));
    repository.add(new TransactionHistory(43, "41.2", "withdraw", date));
    repository.add(new TransactionHistory(43, "1.2", "withdraw", date));
    repository.add(new TransactionHistory(43, "1.2", "deposit", date));

    List<TransactionHistory> limit = repository.limit(5, 2, 43);

    assertThat(limit.size(), is(1));
    assertThat(limit.get(0).date, is(date));
    assertThat(limit.get(0).transactionType, is("deposit"));
    assertThat(limit.get(0).userId, is(43));
  }
}