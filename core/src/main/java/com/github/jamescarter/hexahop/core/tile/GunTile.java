package com.github.jamescarter.hexahop.core.tile;

import java.util.ArrayList;
import java.util.List;

import com.github.jamescarter.hexahop.core.grid.TileGrid;
import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;

public class GunTile extends Tile {
	private TileGrid<?> tileGrid;
	private List<List<Tile>> destroyedTileList = new ArrayList<List<Tile>>();

	public GunTile(TileGrid<?> tileGrid, Location location) {
		super(location, TileImage.GUN);

		this.tileGrid = tileGrid;
	}

	/**
	 * Find the next tile in the line of sight and destroy it.
	 * 
	 * If another gun is hit, it will destroy the surrounding tiles.
	 */
	@Override
	public Location stepOn(Direction direction) {
		Location lineLocation = location().clone();
		List<Tile> destroyedList = new ArrayList<Tile>();
		Tile statusTile = null;

		do {
			lineLocation.move(direction);

			if (tileGrid.isOutOfBounds(lineLocation)) {
				break;
			}

			statusTile = tileGrid.statusTileAt(lineLocation);
		} while (statusTile == null || !statusTile.isActive());

		if (statusTile != null) {
			statusTile.deactivate();

			destroyedList.add(statusTile);

			if (statusTile instanceof GunTile) {
				for (Location location : tileGrid.statusConnectedTo(lineLocation)) {
					statusTile = tileGrid.statusTileAt(location);

					statusTile.deactivate();

					destroyedList.add(statusTile);
				}
			}
		}

		destroyedTileList.add(destroyedList);

		return null;
	}

	@Override
	public void stepOff() {
		destroyedTileList.add(new ArrayList<Tile>());
	}

	@Override
	public void undo() {
		if (destroyedTileList.size() > 0) {
			for (Tile tile : destroyedTileList.remove(destroyedTileList.size() - 1)) {
				tile.activate();
			}
		}
	}
}
