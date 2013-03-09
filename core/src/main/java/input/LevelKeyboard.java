package input;

import static playn.core.PlayN.assets;

import com.github.jamescarter.hexahop.core.callback.MapLoadCallback;
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
			case UP:
			case W:
				level.move(Direction.NORTH);
			break;
			case DOWN:
			case S:
				level.move(Direction.SOUTH);
			break;
			case Q:
				level.move(Direction.NORTH_WEST);
			break;
			case E:
				level.move(Direction.NORTH_EAST);
			break;
			case A:
				level.move(Direction.SOUTH_WEST);
			break;
			case D:
				level.move(Direction.SOUTH_EAST);
			break;
			case F:
				level.complete();
			break;
			case ESCAPE:
			case MENU:
				assets().getText("levels/map.json", new MapLoadCallback());
			default:
		}
	}
}
