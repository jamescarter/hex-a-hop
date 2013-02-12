package com.github.jamescarter.hexahop.core.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.level.Tile;
import com.github.jamescarter.hexahop.core.player.Direction;

public class MapTileGrid extends TileGrid<Integer> {
	public MapTileGrid(HashMap<Integer, List<Integer>> baseGridMap, HashMap<Integer, List<Tile>> gridStatusMap) {
		super(baseGridMap, gridStatusMap);
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
		setStatusAt(levelLocation, Tile.COMPLETE);

		for (Location location : connectedTo(levelLocation)) {
			if (statusAt(location) == null) {
				setStatusAt(location, Tile.INCOMPLETE);
			}
		}
	}
}
