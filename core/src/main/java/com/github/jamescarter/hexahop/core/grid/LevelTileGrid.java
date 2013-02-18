package com.github.jamescarter.hexahop.core.grid;

import com.github.jamescarter.hexahop.core.level.Level;
import com.github.jamescarter.hexahop.core.tile.Tile;

public class LevelTileGrid extends TileGrid<Tile> {
	private Level level;

	public LevelTileGrid(Level level) {
		this.level = level;
	}

	public void complete() {
		level.complete();
	}
}
