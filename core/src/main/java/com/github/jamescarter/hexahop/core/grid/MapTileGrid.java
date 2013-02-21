package com.github.jamescarter.hexahop.core.grid;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.tile.MapStatusTile;

public class MapTileGrid extends TileGrid<Integer> {
	public void unlockConnected(Location levelLocation) {
		((MapStatusTile)statusAt(levelLocation)).complete();

		for (Location location : baseConnectedTo(levelLocation)) {
			if (statusAt(location) == null) {
				// Ignore the "join" tiles
				if (baseTileAt(location) > 0) {
					setStatusAt(location, new MapStatusTile(location));
				}
			}
		}
	}
}
