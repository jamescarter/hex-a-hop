package com.github.jamescarter.hexahop.core.tile;

import com.github.jamescarter.hexahop.core.level.Location;

public class MapStatusTile extends Tile {
	public MapStatusTile(Location location) {
		super(location, TileImage.INCOMPLETE);
	}

	public void complete() {
		setTileImage(TileImage.COMPLETE);
	}
}
