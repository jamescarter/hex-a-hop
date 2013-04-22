package com.github.jamescarter.hexahop.core;

import com.github.jamescarter.hexahop.core.screen.HexScreen;
import com.github.jamescarter.hexahop.core.screen.TitleScreen;

import playn.core.Game;
import playn.core.PlayN;
import playn.core.util.Clock;

public class HexAHop extends Game.Default {
	public static float scaleY = 1.0f;
	private static final int UPDATE_RATE = 50;
	private Clock.Source clock = new Clock.Source(UPDATE_RATE);

	public HexAHop() {
		this(false);
	}

	public HexAHop(boolean scale) {
		super(UPDATE_RATE);

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
		clock.paint(alpha);

		HexScreen.screens.paint(clock);
	}

	@Override
	public void update(int delta) {
		clock.update(delta);

		HexScreen.screens.update(delta);
	}
}
