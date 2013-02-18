package com.github.jamescarter.hexahop.core.level;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.List;

import com.github.jamescarter.hexahop.core.grid.GridLoader;
import com.github.jamescarter.hexahop.core.grid.LevelTileGrid;
import com.github.jamescarter.hexahop.core.grid.TileGrid;
import com.github.jamescarter.hexahop.core.json.StateJson;
import com.github.jamescarter.hexahop.core.player.Direction;
import com.github.jamescarter.hexahop.core.player.Player;
import com.github.jamescarter.hexahop.core.screen.MapScreen;
import com.github.jamescarter.hexahop.core.tile.Tile;

import playn.core.Keyboard.Event;
import playn.core.ImageLayer;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;

public class Level extends GridLoader {
	private static final ImageLayer bgLayer = graphics().createImageLayer(assets().getImage("images/gradient.png"));
	private Location levelLocation;
	private List<Location> moveList = new ArrayList<Location>();
	private LevelTileGrid levelTileGrid = new LevelTileGrid(this);
	private int par;
	private Player player;

	public Level(Location location, String levelJsonString) {
		this.levelLocation = location;

		StateJson<Tile> levelJson = new StateJson<Tile>(
			Tile.class,
			levelTileGrid,
			PlayN.json().parse(levelJsonString),
			"level-" + location.col() + "x" + location.row(),
			true
		);

		par = levelJson.par();
		player = new Player(levelJson.start());
		moveList.add(levelJson.start());
	}

	public int par() {
		return par;
	}

	@Override
	public void load() {
		super.load();

		getGridLayer().add(player);

		// Center grid layer
		getGridLayer().setTranslation(
			((640 - (getTileGrid().cols() * 46)) / 2) - 10,
			((480 - (getTileGrid().rows() * 36)) / 2) - 36
		);

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
			moveList.add(player.location().clone());

			levelTileGrid.statusAt(player.location()).stepOff();

			player.move(direction);

			stepOnTile(player.location(), direction);
		}
	}

	/**
	 * Undo the last move.

	 * @return true if there are more moves to undo, otherwise false.
	 */
	private boolean undo() {
		if (moveList.size() <= 1) {
			return false;
		}

		Location location = moveList.remove(moveList.size() - 1);

		getTile(player.location()).undo();

		player.jumpTo(location, true);

		getTile(player.location()).undo();

		return true;
	}

	private void stepOnTile(Location location, Direction direction) {
		Location endLocation = levelTileGrid.statusAt(location).stepOn(direction);

		if (endLocation != null) {
			player.jumpTo(endLocation, false);

			stepOnTile(endLocation, direction);
		}
	}

	private Tile getTile(Location location) {
		int colPosition = getColPosition(location.col(), 0);
		int rowPosition = getRowPosition(location.row(), location.col(), 0);

		for (int i=0; i<getGridLayer().size(); i++) {
			Layer layer = getGridLayer().get(i);

			if (layer instanceof Tile) {
				if (layer.tx() == colPosition && layer.ty() == rowPosition) {
					return (Tile) layer;
				}
			}
		}

		return null;
	}

	public void complete() {
		new MapScreen(levelLocation).load();
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