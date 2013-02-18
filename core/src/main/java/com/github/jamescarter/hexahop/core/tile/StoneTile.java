package com.github.jamescarter.hexahop.core.tile;

import com.github.jamescarter.hexahop.core.level.Location;

public class StoneTile extends Tile {
	private boolean isWall;

	public StoneTile(Location location, boolean isWall) {
		super(location, (isWall) ? TileImage.STONE_WALL : TileImage.STONE);

		this.isWall = isWall;
	}

	@Override
	public boolean isWall() {
		return isWall;
	}
}
