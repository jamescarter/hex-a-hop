package com.github.jamescarter.hexahop.core.grid;

import java.util.HashMap;
import java.util.List;

import com.github.jamescarter.hexahop.core.level.Tile;

public abstract class TileGrid<E> {
	protected HashMap<Integer, List<Tile>> baseGridMap = new HashMap<Integer, List<Tile>>();
	protected HashMap<Integer, List<E>> gridStatusMap = new HashMap<Integer, List<E>>();

	public List<Tile> rowTileList(int row) {
		return baseGridMap.get(row);
	}

	public int rows() {
		return baseGridMap.size();
	}

	public int cols() {
		return baseGridMap.get(0).size();
	}
}
