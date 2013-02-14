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

	/**
	 * Activate the tile the user landed on.
	 * 
	 * @param location Tile location to activate
	 * @param direction Direction user is heading
	 * @return the location the user was pushed to
	 */
	public Location activateTile(Location location, Direction direction) {
		switch (statusAt(location)) {
			case TRAMPOLINE:
				for (int i=0; i<2; i++) {
					Location newLocation = location.clone();
					newLocation.move(direction);

					if (canMove(location, direction) || statusAt(newLocation) == null) {
						location.move(direction);
					} else if (i == 0) {
						return null;
					} else {
						break;
					}
				}

				return location;
			default:
		}

		return null;
	}

	/**
	 * Restore tile to its previous state when the level started.
	 * 
	 * @param location
	 */
	public void restoreTile(Location location) {
		setStatusAt(location, baseTileAt(location));
	}

	public boolean contains(Tile findTile) {
		for (int row=0; row<rows(); row++) {
			if (rowTileList(row).contains(findTile)) {
				return true;
			}
		}

		return false;
	}
}
