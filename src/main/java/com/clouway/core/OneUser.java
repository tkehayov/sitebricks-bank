package com.clouway.core;

import com.google.inject.Inject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */

public class OneUser {
  private HttpServletRequest request;

  @Inject
  public OneUser(HttpServletRequest request) {
    this.request = request;
  }

}
