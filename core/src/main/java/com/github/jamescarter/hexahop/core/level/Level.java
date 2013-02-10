package com.github.jamescarter.hexahop.core.level;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.List;

import com.github.jamescarter.hexahop.core.grid.GridLoader;
import com.github.jamescarter.hexahop.core.grid.LevelTileGrid;
import com.github.jamescarter.hexahop.core.grid.TileGrid;
import com.github.jamescarter.hexahop.core.player.Direction;
import com.github.jamescarter.hexahop.core.player.Player;
import com.github.jamescarter.hexahop.core.screen.MapScreen;

import playn.core.Json.Array;
import playn.core.Json.Object;
import playn.core.Keyboard.Event;
import playn.core.ImageLayer;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;

public class Level extends GridLoader {
	private static final ImageLayer bgLayer = graphics().createImageLayer(assets().getImage("images/gradient.png"));
	private Location location;
	private List<Direction> moveList = new ArrayList<Direction>();
	private LevelTileGrid levelTileGrid;
	private int par;
	private Player player;

	public Level(Location location, String levelJson) {
		this.location = location;

		Object jsonObj = PlayN.json().parse(levelJson);

		par = jsonObj.getInt("par");

		Array start = jsonObj.getArray("start");

		player = new Player(new Location(start.getInt(0), start.getInt(1)));

		levelTileGrid = new LevelTileGrid(jsonObj.getArray("grid"));
	}

	public int par() {
		return par;
	}

	@Override
	public void load() {
		super.load();

		getGridLayer().add(player);

		PlayN.pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerStart(Pointer.Event event) {
				// Offset clicked location based on where the levelLayer is centered
				Direction direction = player.getDirection(
					event.x() - getGridLayer().tx(),
					event.y() - getGridLayer().ty()
				);

				move(direction);
			}
		});

		PlayN.keyboard().setListener(new Keyboard.Adapter() {
			@Override
			public void onKeyDown(Event event) {
				switch(event.key()) {
					case U:
					case Z:
						undo();
					break;
					case UP:
					case W:
						move(Direction.NORTH);
					break;
					case DOWN:
					case S:
						move(Direction.SOUTH);
					break;
					case Q:
						move(Direction.NORTH_WEST);
					break;
					case E:
						move(Direction.NORTH_EAST);
					break;
					case A:
						move(Direction.SOUTH_WEST);
					break;
					case D:
						move(Direction.SOUTH_EAST);
					break;
					default:
				}
			}
		});
	}

	private void move(Direction direction) {
		if (levelTileGrid.canMove(player.location(), direction)) {
			moveList.add(direction);

			deactivateTile(player.location());

			player.move(direction, false);

			// TODO: levelTileGrid.activateTile(player.location());

			if (levelTileGrid.complete()) {
				new MapScreen(location).load();
			}
		}
	}

	/**
	 * Undo the last move.

	 * @return true if there are more moves to undo, otherwise false.
	 */
	private boolean undo() {
		if (moveList.isEmpty()) {
			return false;
		}

		Direction direction = moveList.remove(moveList.size() - 1);

		player.move(direction, true);

		restoreTile(player.location());

		return true;
	}

	private void deactivateTile(Location location) {
		if (levelTileGrid.deactivateTile(player.location())) {
			getLayer(location).setVisible(false);
		}
	}

	private void restoreTile(Location location) {
		getLayer(location).setVisible(true);

		levelTileGrid.restoreTile(player.location());
	}

	private Layer getLayer(Location location) {
		int colPosition = getColPosition(location.col(), 0);
		int rowPosition = getRowPosition(location.row(), location.col(), 0);

		for (int i=0; i<getGridLayer().size(); i++) {
			Layer layer = getGridLayer().get(i);

			if (layer instanceof ImageLayer) {
				if (layer.tx() == colPosition && layer.ty() == rowPosition) {
					return layer;
				}
			}
		}

		return null;
	}

	@Override
	public TileGrid<Tile> getTileGrid() {
		return levelTileGrid;
	}

	@Override
	public Layer getBackgroundLayer() {
		return bgLayer;
	}
}