package com.github.jamescarter.hexahop.core.grid;

import java.util.ArrayList;
import java.util.List;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.level.Tile;
import com.github.jamescarter.hexahop.core.player.Direction;

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

	public Tile getTile(Location location) {
		if (location.row() >= gridStatusMap.size() || location.row() < 0) {
			return Tile.EMPTY;
		}

		List<Tile> tileList = gridStatusMap.get(location.row());

		if (location.col() >= tileList.size() || location.col() < 0) {
			return Tile.EMPTY;
		}

		return tileList.get(location.col());
	}

	public boolean isValidMove(Location fromLocation, Direction inDirection) {
		Location newLocation = fromLocation.clone();

		newLocation.move(inDirection);

		if (getTile(newLocation) == Tile.EMPTY) {
			return false;
		}

		return true;
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
			tileList.set(location.col(), Tile.EMPTY);
			return true;
		}

		return false;
	}

	public void restoreTile(Location location) {
		gridStatusMap.get(location.row()).set(
			location.col(),
			baseGridMap.get(location.row()).get(location.col())
		);
	}
}
