package com.github.jamescarter.hexahop.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import com.github.jamescarter.hexahop.core.HexAHop;

public class HexAHopHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("hexahop/");
    PlayN.run(new HexAHop());
  }
}
