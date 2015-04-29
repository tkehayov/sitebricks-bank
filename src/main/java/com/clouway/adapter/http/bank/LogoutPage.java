package com.clouway.adapter.http.bank;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
@At("/logout")
@Show("logout.html")
public class LogoutPage {
  private Provider<HttpServletRequest> req;
  private Provider<HttpServletResponse> resp;

  @Inject
  public LogoutPage(Provider<HttpServletRequest> req, Provider<HttpServletResponse> resp) {
    this.req = req;
    this.resp = resp;
  }

  @Get
  public String logoutUser() {
    Cookie[] cookies = req.get().getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("user")) {
        cookie.setMaxAge(0);
        resp.get().addCookie(cookie);
        return "/login";
      }
    }
    return null;
  }
}
