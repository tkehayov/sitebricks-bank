package com.clouway.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Tihomir Kehayov (kehayov89@gmail.com)
 */
public class Hash {


  public static String getSha(String expression) {
    StringBuilder sb;

    MessageDigest hash = null;
    try {
      hash = MessageDigest.getInstance("sha");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    hash.update(expression.getBytes());
    byte[] digest = hash.digest();
    sb = new StringBuilder();
    for (byte b : digest) {
      sb.append(String.format("%02x", b & 0xff));
    }

    return sb.toString();
  }
}
