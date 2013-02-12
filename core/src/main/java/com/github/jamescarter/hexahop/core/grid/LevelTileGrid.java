package com.github.jamescarter.hexahop.core.grid;

import java.util.HashMap;
import java.util.List;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.level.Tile;

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
		List<Tile> tileList = rowTileList(location.row());

		if (tileList.get(location.col()) != Tile.NORMAL) {
			tileList.set(location.col(), null);
			return true;
		}

		return false;
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
