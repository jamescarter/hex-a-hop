package com.github.jamescarter.hexahop.core.input;

import com.github.jamescarter.hexahop.core.screen.LevelScreen;

import playn.core.Touch;

public class LevelTouch extends Touch.LayerAdapter {
	private LevelScreen level;
	private Touch.Event touchStart;

	public LevelTouch(LevelScreen level) {
		this.level = level;
	}

	@Override
	public void onTouchStart(Touch.Event touch) {
		touchStart = touch;
	}

	@Override
	public void onTouchEnd(Touch.Event touchEnd) {
		level.move(
			level.getGridLocation(touchStart.x(), touchStart.y()).to(
				level.getGridLocation(touchEnd.x(), touchEnd.y())
			)
		);
	}
}
