package com.clouway.adapter.http;

import com.google.inject.TypeLiteral;
import com.google.sitebricks.client.WebResponse;

import java.util.Map;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
public class Response implements WebResponse {
  public Map<String, String> getHeaders() {
    return null;
  }

  public int status() {
    return 0;
  }

  public <T> ResponseTransportBuilder<T> to(Class<T> aClass) {
    return null;
  }

  public <T> ResponseTransportBuilder<T> to(TypeLiteral<T> typeLiteral) {
    return null;
  }
}
