package com.github.jamescarter.hexahop.core;

import com.github.jamescarter.hexahop.core.screen.TitleScreen;

import playn.core.Game;

public class HexAHop implements Game {
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
