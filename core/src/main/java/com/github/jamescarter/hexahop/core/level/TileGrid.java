package com.github.jamescarter.hexahop.core.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.jamescarter.hexahop.core.player.Direction;

import playn.core.Json.Array;

public class TileGrid {
	private HashMap<Integer, List<Tile>> tileGridMap = new HashMap<Integer, List<Tile>>();

	public TileGrid(Array gridArray) {
		for (int row=0; row<gridArray.length(); row++) {
			Array tiles = gridArray.getArray(row);
			List<Tile> tileList = new ArrayList<Tile>();

			tileGridMap.put(row, tileList);

			for (int tile=0; tile<tiles.length(); tile++) {
				tileList.add(Tile.getTile(tiles.getInt(tile)));
			}
		}
	}

	public Tile getTile(Location location) {
		if (location.row() >= tileGridMap.size() || location.row() < 0) {
			return Tile.EMPTY;
		}

		List<Tile> tileList = tileGridMap.get(location.row());

		if (location.col() >= tileList.size() || location.col() < 0) {
			return Tile.EMPTY;
		}

		return tileList.get(location.col());
	}

	public boolean isValidMove(Location fromLocation, Direction inDirection) {
		Location newLocation = fromLocation.clone();

		newLocation.move(inDirection);

		if (getTile(newLocation) == Tile.EMPTY) {
			return false;
		}

		return true;
	}

	public List<Tile> rowTileList(int row) {
		return tileGridMap.get(row);
	}

	public int rows() {
		return tileGridMap.size();
	}

	public int cols() {
		return tileGridMap.get(0).size();
	}
}
