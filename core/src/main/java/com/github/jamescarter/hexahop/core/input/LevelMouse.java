package com.github.jamescarter.hexahop.core.input;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;
import com.github.jamescarter.hexahop.core.screen.LevelScreen;

import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;

public class LevelMouse extends Mouse.LayerAdapter {
	private LevelScreen level;

	public LevelMouse(LevelScreen level) {
		this.level = level;
	}

	@Override
	public void onMouseDown(ButtonEvent event) {
		if (event.button() == Mouse.BUTTON_LEFT) {
			Location location = level.getGridLocation(event.x(), event.y());

			Direction direction = level.player().to(location);

			level.move(direction);
		} else {
			level.undo();
		}
	}
}
