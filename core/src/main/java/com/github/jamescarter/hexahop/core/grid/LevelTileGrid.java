package com.github.jamescarter.hexahop.core.grid;

import java.util.HashMap;
import java.util.List;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.level.Tile;
import com.github.jamescarter.hexahop.core.player.Direction;

public class LevelTileGrid extends TileGrid<Tile> {
	public LevelTileGrid(HashMap<Integer, List<Tile>> baseGridMap, HashMap<Integer, List<Tile>> gridStatusMap) {
		super(baseGridMap, gridStatusMap);
	}

	/**
	 * Attempt to deactivate the tile at location
	 * 
	 * @param location
	 * @return returns true if the tile was deactivate, otherwise false
	 */
	public boolean deactivateTile(Location location) {
		if (statusAt(location).isBreakable()) {
			setStatusAt(location, null);

			return true;
		}

		return false;
	}

	public Location activateTile(Location location, Direction fromDirection) {
		switch (statusAt(location)) {
			case TRAMPOLINE:
				location.move(fromDirection);
				location.move(fromDirection);

				return location;
			default:
		}

		return null;
	}

	public void restoreTile(Location location) {
		setStatusAt(location, baseTileAt(location));
	}

	public boolean complete() {
		for (int row=0; row<rows(); row++) {
			for (Tile tile : rowTileList(row)) {
				if (tile == Tile.COLLAPSABLE || tile == Tile.COLLAPSABLE2) {
					return false;
				}
			}
		}

		return true;
	}
}
