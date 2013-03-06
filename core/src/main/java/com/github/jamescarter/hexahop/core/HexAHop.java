package com.github.jamescarter.hexahop.core;

import com.github.jamescarter.hexahop.core.screen.HexScreen;
import com.github.jamescarter.hexahop.core.screen.TitleScreen;

import playn.core.Game;
import playn.core.PlayN;

public class HexAHop implements Game {
	public static float scaleY = 1.0f;

	public HexAHop() {
		this(false);
	}

	public HexAHop(boolean scale) {
		if (scale) {
			scaleY = (float) PlayN.graphics().screenHeight() / 480;

			PlayN.graphics().rootLayer().setScale(scaleY, scaleY);
		}
	}

	@Override
	public void init() {
		HexScreen.screens.push(new TitleScreen());
	}

	@Override
	public void paint(float alpha) {
		HexScreen.screens.paint(alpha);
	}

	@Override
	public void update(float delta) {
		HexScreen.screens.update(delta);
	}

	@Override
	public int updateRate() {
		return 50;
	}
}
