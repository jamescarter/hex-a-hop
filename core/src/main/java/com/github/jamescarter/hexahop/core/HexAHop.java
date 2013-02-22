package com.github.jamescarter.hexahop.core;

import com.github.jamescarter.hexahop.core.screen.TitleScreen;

import playn.core.Game;
import playn.core.PlayN;

public class HexAHop implements Game {
	public static float gameWidth;

	public HexAHop() {
		this(false);
	}

	public HexAHop(boolean scale) {
		if (scale) {
			float scaleY = (float) PlayN.graphics().screenHeight() / 480;

			PlayN.graphics().rootLayer().setScale(scaleY, scaleY);

			gameWidth = PlayN.graphics().screenWidth() / PlayN.graphics().rootLayer().scaleY();
		} else {
			gameWidth = 640;
		}
	}

	@Override
	public void init() {
		new TitleScreen().load();
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything here!
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public int updateRate() {
		return 25;
	}
}
