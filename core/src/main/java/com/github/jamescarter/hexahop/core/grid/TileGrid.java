package com.github.jamescarter.hexahop.core.grid;

import java.util.HashMap;
import java.util.List;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.level.Tile;
import com.github.jamescarter.hexahop.core.player.Direction;

public abstract class TileGrid<E> {
	protected HashMap<Integer, List<Tile>> baseGridMap = new HashMap<Integer, List<Tile>>();
	protected HashMap<Integer, List<E>> gridStatusMap = new HashMap<Integer, List<E>>();

	public List<Tile> rowTileList(int row) {
		return baseGridMap.get(row);
	}

	public int rows() {
		return gridStatusMap.size();
	}

	public int cols() {
		return gridStatusMap.get(0).size();
	}

	public Tile baseTileAt(Location location) {
		return baseGridMap.get(location.row()).get(location.col());
	}

	public E statusAt(Location location) {
		if (location.row() >= gridStatusMap.size() || location.row() < 0) {
			return null;
		}

		List<E> statusList = gridStatusMap.get(location.row());

		if (location.col() >= statusList.size() || location.col() < 0) {
			return null;
		}

		return statusList.get(location.col());
	}

	public boolean canMove(Location fromLocation, Direction inDirection) {
		Location newLocation = fromLocation.clone();

		newLocation.move(inDirection);

		if (statusAt(newLocation) != null) {
			return true;
		}

		return false;
	}
}
