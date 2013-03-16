package com.github.jamescarter.hexahop.core.screen;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;


import java.util.ArrayList;
import java.util.List;

import com.github.jamescarter.hexahop.core.HexAHop;
import com.github.jamescarter.hexahop.core.callback.MapLoadCallback;
import com.github.jamescarter.hexahop.core.grid.GridLoader;
import com.github.jamescarter.hexahop.core.grid.LevelTileGrid;
import com.github.jamescarter.hexahop.core.grid.TileGrid;
import com.github.jamescarter.hexahop.core.input.LevelKeyboard;
import com.github.jamescarter.hexahop.core.input.LevelMouse;
import com.github.jamescarter.hexahop.core.input.LevelTouch;
import com.github.jamescarter.hexahop.core.json.LevelMoveJson;
import com.github.jamescarter.hexahop.core.json.GridStateJson;
import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;
import com.github.jamescarter.hexahop.core.player.Player;
import com.github.jamescarter.hexahop.core.tile.Tile;

import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;

public class LevelScreen extends GridLoader {
	private final ImageLayer bgLayer = graphics().createImageLayer(assets().getImage("images/gradient.png"));
	private Location levelLocation;
	private List<Location> moveList = new ArrayList<Location>();
	private List<Direction> directionList = new ArrayList<Direction>();
	private LevelTileGrid levelTileGrid = new LevelTileGrid(this);
	private int par;
	private Player player;
	private boolean drownedInPlace;

	public LevelScreen(Location location, String levelJsonString) {
		this.levelLocation = location;

		GridStateJson<Tile> levelJson = new GridStateJson<Tile>(
			Tile.class,
			levelTileGrid,
			anim,
			PlayN.json().parse(levelJsonString),
			"level-" + location.col() + "x" + location.row(),
			true
		);

		par = levelJson.par();
		player = new Player(this, levelJson.start());
		moveList.add(levelJson.start());

		stepOnTile(player.location(), Direction.SOUTH);

		// replay any previously saved moves
		for (Direction direction : LevelMoveJson.retrieve(location)) {
			move(direction);
		}
	}

	public int par() {
		return par;
	}

	@Override
	public void wasShown() {
		getGridLayer().add(player);

		getGridLayer().setTranslation(
			(((640 * HexAHop.scaleY) - ((getTileGrid().cols() * 46) * HexAHop.scaleY)) / 2) - 18,
			((480 - (getTileGrid().rows() * 36)) / 2) - 36
		);

		bgLayer.addListener(new LevelMouse(this));
		bgLayer.addListener(new LevelTouch(this));
		PlayN.keyboard().setListener(new LevelKeyboard(this));
	}

	public void move(Direction direction) {
		finishAnimation();

		if (player.visible() && levelTileGrid.canMove(player.location(), direction)) {
			moveList.add(player.location().clone());
			directionList.add(direction);

			levelTileGrid.statusTileAt(player.location()).stepOff();

			player.move(direction);
		}
	}

	/**
	 * Undo the last move.

	 * @return true if there are more moves to undo, otherwise false.
	 */
	public boolean undo() {
		finishAnimation();

		if (moveList.size() <= 1) {
			return false;
		}

		Location location = moveList.remove(moveList.size() - 1);
		directionList.remove(directionList.size() - 1);

		if (drownedInPlace || player.visible()) {
			Tile statusTile = levelTileGrid.statusTileAt(player.location());

			if (statusTile != null) {
				statusTile.undo();
			}

			drownedInPlace = false;
		}

		if (!player.visible()) {
			player.setVisible(true);
		}

		player.jumpTo(location, true);

		return true;
	}

	public void stepOnTile(Location location, Direction direction) {
		Tile statusTile = levelTileGrid.statusTileAt(location);

		if (statusTile == null || !statusTile.isActive()) {
			player.setVisible(false);
		} else {
			Location endLocation = statusTile.stepOn(direction);

			if (endLocation == null) {
				if (!levelTileGrid.statusTileAt(location).isActive()) {
					player.setVisible(false);
					drownedInPlace = true;
				}
			} else {
				player.jumpTo(endLocation, false);
			}
		}
	}

	public void finishAnimation() {
		for (int i=1; i<=30; i++) {
			anim.update(2000 * i);
		}
	}

	public Location player() {
		return player.location();
	}

	public void complete() {
		assets().getText("levels/map.json", new MapLoadCallback(levelLocation, moveList.size() - 1 <= par));
		LevelMoveJson.clear(levelLocation);
	}

	public void backToMap() {
		assets().getText("levels/map.json", new MapLoadCallback());
		LevelMoveJson.store(levelLocation, directionList);
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