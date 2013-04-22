package com.github.jamescarter.hexahop.core.screen;

import playn.core.PlayN;
import tripleplay.game.AnimScreen;
import tripleplay.game.ScreenStack;

public class HexScreen extends AnimScreen {
	public static final ScreenStack screens = new ScreenStack() {
		@Override
		protected void handleError(RuntimeException error) {
			PlayN.log().warn("Screen failure", error);
		}
	};

	@Override
	public void wasRemoved() {
		layer.destroy();
	}
}
