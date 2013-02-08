package com.github.jamescarter.hexahop.core.grid;

import java.util.ArrayList;
import java.util.List;

import com.github.jamescarter.hexahop.core.level.Tile;

import playn.core.Json.Array;

public class MapTileGrid extends TileGrid<Integer> {
	public MapTileGrid(Array gridArray) {
		// TODO: only populate the tile grid with available levels
		for (int row=0; row<gridArray.length(); row++) {
			Array tiles = gridArray.getArray(row);

			List<Tile> tileList = new ArrayList<Tile>();
			List<Integer> levelIdList = new ArrayList<Integer>();

			baseGridMap.put(row, tileList);
			gridStatusMap.put(row, levelIdList);

			for (int tile=0; tile<tiles.length(); tile++) {
				int levelId = tiles.getInt(tile);

				tileList.add((levelId == 0) ? Tile.EMPTY : Tile.INCOMPLETE);
				levelIdList.add(levelId);
			}
		}
	}
}
