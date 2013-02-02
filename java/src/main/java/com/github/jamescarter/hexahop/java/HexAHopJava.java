package com.github.jamescarter.hexahop.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.github.jamescarter.hexahop.core.HexAHop;

public class HexAHopJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assets().setPathPrefix("com/github/jamescarter/hexahop/resources");
    PlayN.run(new HexAHop());
  }
}
