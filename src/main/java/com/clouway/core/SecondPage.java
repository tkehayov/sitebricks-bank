package com.clouway.core;

import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
@At("/second")
@Show("second.html")
public class SecondPage {
  public String username = "";

  @Post
  public void getUsername() {
    System.out.println(username);
  }

  @Get
  public String red() {
    return "/homepage";
  }
}
