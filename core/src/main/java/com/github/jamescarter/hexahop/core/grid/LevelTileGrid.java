package com.github.jamescarter.hexahop.core.grid;

import com.github.jamescarter.hexahop.core.screen.LevelScreen;
import com.github.jamescarter.hexahop.core.tile.Tile;

public class LevelTileGrid extends TileGrid<Tile> {
	private LevelScreen level;

	public LevelTileGrid(LevelScreen level) {
		this.level = level;
	}

	public void complete() {
		level.complete();
	}
}
