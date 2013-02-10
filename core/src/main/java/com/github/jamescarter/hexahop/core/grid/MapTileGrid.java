package com.github.jamescarter.hexahop.core.grid;

import java.util.ArrayList;
import java.util.List;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.level.Tile;
import com.github.jamescarter.hexahop.core.player.Direction;

import playn.core.Json.Array;
import playn.core.Json.Object;

public class MapTileGrid extends TileGrid<Integer> {
	public MapTileGrid(Object jsonObj) {
		Array startArray = jsonObj.getArray("start");
		Array gridArray = jsonObj.getArray("grid");

		for (int row=0; row<gridArray.length(); row++) {
			Array tiles = gridArray.getArray(row);

			List<Integer> levelIdList = new ArrayList<Integer>();
			List<Tile> tileList = new ArrayList<Tile>();

			baseGridMap.put(row, levelIdList);
			gridStatusMap.put(row, tileList);

			for (int tile=0; tile<tiles.length(); tile++) {
				int levelId = tiles.getInt(tile);

				tileList.add(null);

				if (levelId == 0) {
					levelIdList.add(null);
				} else {
					levelIdList.add(levelId);
				}
			}
		}

		gridStatusMap.get(startArray.getInt(1)).set(startArray.getInt(0), Tile.INCOMPLETE);
	}

	public List<Location> connectedTo(Location location) {
		List<Location> locationList = new ArrayList<Location>();

		for (Direction direction : Direction.values()) {
			Location newLocation = location.clone();

			newLocation.move(direction);

			if (baseTileAt(newLocation) != null) {
				locationList.add(newLocation);
			}
		}

		return locationList;
	}

	public void unlockConnected(Location levelLocation) {
		for (Location location : connectedTo(levelLocation)) {
			setStatusAt(location, Tile.INCOMPLETE);
		}
	}
}
