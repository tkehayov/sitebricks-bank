package com.clouway;

import static org.hamcrest.core.Is.is;

/**
 * @author Tihomir Kehayov (kehayov89@gmail.com)
 */

public class LoginServletTest {
//  private PersistentUserRepository repository;
//
//  @Mock
//  HttpServletRequest request;
//
//  @Mock
//  HttpServletResponse response;
//
//  @Rule
//  public JUnitRuleMockery context = new JUnitRuleMockery();
//
//  @Rule
//  public DataStoreCleaner dataStoreCleaner = new DataStoreCleaner();
//
//  @Rule
//  public ExpectedException exception = ExpectedException.none();
//
//  @Before
//  public void setUp() throws Exception {
//    Provider<Connection> provider = new FakeConnectionProvider();
//    Storage storage = new DataStorage(provider);
//    repository = new PersistentUserRepository(storage);
//    repository.add(new User("marinov", "ivanov"));
//  }
//
//  @Test
//  public void happyPath() throws ServletException, IOException {
//
//    User one = repository.findOne(new User("marinov", "ivanov"));
//
//    assertThat(one.username, is("marinov"));
//    assertThat(one.password, is("60a48844468f587dbcf92f8eba976f392e450d64"));
//  }
//
//  @Test
//  public void loginWithInvalidUsernamePassword() throws ServletException, IOException {
//    final User user = new User("apple", "bottle");
//
//    User userRepository = repository.findOne(user);
//
//    assertNull(userRepository);
//  }
//
//  private void mockLogin(final User user) throws IOException {
//    final HttpSession fakeSession = new FakeHttpSession();
//
//    context.checking(new Expectations() {
//      {
//        oneOf(request).getParameter("username");
//        will(returnValue(user.username));
//        oneOf(request).getParameter("password");
//        will(returnValue(user.password));
//        oneOf(request).getSession();
//        will(returnValue(fakeSession));
//        oneOf(response).addCookie(with(aNonNull(Cookie.class)));
//        oneOf(response).setStatus(302);
//        oneOf(response).sendRedirect(with(aNonNull(String.class)));
//      }
//    });
//  }


}
