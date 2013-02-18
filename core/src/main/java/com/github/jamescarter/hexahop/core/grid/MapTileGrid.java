package com.github.jamescarter.hexahop.core.grid;

import java.util.ArrayList;
import java.util.List;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;
import com.github.jamescarter.hexahop.core.tile.MapStatusTile;

public class MapTileGrid extends TileGrid<Integer> {
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
		((MapStatusTile)statusAt(levelLocation)).complete();

		for (Location location : connectedTo(levelLocation)) {
			setStatusAt(location, new MapStatusTile(location));
		}
	}
}
