package com.github.jamescarter.hexahop.core.tile;

import com.github.jamescarter.hexahop.core.level.Location;

public class MapStatusTile extends Tile {
	public MapStatusTile(Location location) {
		super(location, TileImage.INCOMPLETE);
	}

	public void complete() {
		if (id() != TileImage.PERFECT.id()) {
			setTileImage(TileImage.COMPLETE);
		}
	}

	public void perfect() {
		setTileImage(TileImage.PERFECT);
	}
}
