package com.github.jamescarter.hexahop.core.input;

import com.github.jamescarter.hexahop.core.player.Direction;
import com.github.jamescarter.hexahop.core.screen.LevelScreen;

import playn.core.Keyboard;
import playn.core.Keyboard.Event;

public class LevelKeyboard extends Keyboard.Adapter {
	private LevelScreen level;

	public LevelKeyboard(LevelScreen level) {
		this.level = level;
	}

	@Override
	public void onKeyDown(Event event) {
		switch(event.key()) {
			case U:
			case Z:
			case BACK:
				level.undo();
			break;
			case NP8:
			case W:
			case UP:
				level.move(Direction.NORTH);
			break;
			case NP2:
			case S:
			case DOWN:
				level.move(Direction.SOUTH);
			break;
			case NP7:
			case Q:
				level.move(Direction.NORTH_WEST);
			break;
			case NP9:
			case E:
				level.move(Direction.NORTH_EAST);
			break;
			case NP1:
			case A:
				level.move(Direction.SOUTH_WEST);
			break;
			case NP3:
			case D:
				level.move(Direction.SOUTH_EAST);
			break;
			case ESCAPE:
			case MENU:
				level.backToMap();
			default:
		}
	}
}
