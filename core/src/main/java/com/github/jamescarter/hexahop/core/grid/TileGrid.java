package com.github.jamescarter.hexahop.core.grid;

import java.util.HashMap;
import java.util.List;
import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;
import com.github.jamescarter.hexahop.core.tile.Tile;

public abstract class TileGrid<T> {
	private HashMap<Integer, List<T>> baseGridMap = new HashMap<Integer, List<T>>();
	private HashMap<Integer, List<Tile>> gridStatusMap = new HashMap<Integer, List<Tile>>();

	public List<Tile> rowTileList(int row) {
		return gridStatusMap.get(row);
	}

	public int rows() {
		return gridStatusMap.size();
	}

	public int cols() {
		return gridStatusMap.get(0).size();
	}

	public T baseTileAt(Location location) {
		if (location.row() >= baseGridMap.size() || location.row() < 0) {
			return null;
		}

		List<T> baseTileList = baseGridMap.get(location.row());

		if (location.col() >= baseTileList.size() || location.col() < 0) {
			return null;
		}

		return baseTileList.get(location.col());
	}

	public Tile statusAt(Location location) {
		if (location.row() >= gridStatusMap.size() || location.row() < 0) {
			return null;
		}

		List<Tile> statusList = gridStatusMap.get(location.row());

		if (location.col() >= statusList.size() || location.col() < 0) {
			return null;
		}

		return statusList.get(location.col());
	}

	public boolean canMove(Location fromLocation, Direction inDirection) {
		Location newLocation = fromLocation.clone();

		newLocation.move(inDirection);

		Tile currentStatusTile = statusAt(fromLocation);
		Tile toStatusTile = statusAt(newLocation);

		if (toStatusTile != null && (currentStatusTile == null || (toStatusTile.isWall() == currentStatusTile.isWall() && toStatusTile.isActive()))) {
			return true;
		}

		return false;
	}

	public void setStatusAt(Location location, Tile newStatus) {
		gridStatusMap.get(location.row()).set(location.col(), newStatus);
	}

	public void setBaseRowTileList(int row, List<T> tileList) {
		baseGridMap.put(row, tileList);
	}

	public void setRowTileList(int row, List<Tile> tileList) {
		gridStatusMap.put(row, tileList);
	}
}
