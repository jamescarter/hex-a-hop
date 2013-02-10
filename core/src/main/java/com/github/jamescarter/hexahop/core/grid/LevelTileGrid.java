package com.github.jamescarter.hexahop.core.grid;

import java.util.ArrayList;
import java.util.List;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.level.Tile;
import playn.core.Json.Array;

public class LevelTileGrid extends TileGrid<Tile> {
	public LevelTileGrid(Array gridArray) {
		for (int row=0; row<gridArray.length(); row++) {
			Array tiles = gridArray.getArray(row);

			List<Tile> baseList = new ArrayList<Tile>();
			List<Tile> levelTileList = new ArrayList<Tile>();

			baseGridMap.put(row, baseList);
			gridStatusMap.put(row, levelTileList);

			for (int tile=0; tile<tiles.length(); tile++) {
				baseList.add(Tile.getTile(tiles.getInt(tile)));
				levelTileList.add(Tile.getTile(tiles.getInt(tile)));
			}
		}
	}

	/**
	 * Attempt to deactivate the tile at location
	 * 
	 * @param location
	 * @return returns true if the tile was deactivate, otherwise false
	 */
	public boolean deactivateTile(Location location) {
		List<Tile> tileList = gridStatusMap.get(location.row());

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
		for (int row=0; row<gridStatusMap.size(); row++) {
			for (Tile tile : gridStatusMap.get(row)) {
				if (tile == Tile.COLLAPSABLE || tile == Tile.COLLAPSABLE2) {
					return false;
				}
			}
		}

		return true;
	}
}
