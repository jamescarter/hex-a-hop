package com.github.jamescarter.hexahop.core.grid;

import java.util.ArrayList;
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

	public T baseAt(Location location) {
		if (isOutOfBounds(location)) {
			return null;
		}

		return baseGridMap.get(location.row()).get(location.col());
	}

	public Tile statusTileAt(Location location) {
		if (isOutOfBounds(location)) {
			return null;
		}

		return gridStatusMap.get(location.row()).get(location.col());
	}

	public boolean isOutOfBounds(Location location) {
		if (location.row() >= gridStatusMap.size() || location.row() < 0) {
			return true;
		}

		List<Tile> statusList = gridStatusMap.get(location.row());

		if (location.col() >= statusList.size() || location.col() < 0) {
			return true;
		}

		return false;
	}

	public boolean canMove(Location fromLocation, Direction inDirection) {
		Location newLocation = fromLocation.clone();

		newLocation.move(inDirection);

		Tile currentStatusTile = statusTileAt(fromLocation);
		Tile toStatusTile = statusTileAt(newLocation);

		if (toStatusTile != null && (currentStatusTile == null || (toStatusTile.isWall() == currentStatusTile.isWall() && toStatusTile.isActive()))) {
			return true;
		}

		return false;
	}

	public List<Location> baseConnectedTo(Location location) {
		return connectedTo(location, true);
	}

	public List<Location> statusConnectedTo(Location location) {
		return connectedTo(location, false);
	}

	private List<Location> connectedTo(Location location, boolean isBase) {
		List<Location> locationList = new ArrayList<Location>();

		for (Direction direction : Direction.values()) {
			Location newLocation = location.clone();

			newLocation.move(direction);

			if (isBase) {
				if (baseAt(newLocation) != null) {
					locationList.add(newLocation);
				}
			} else {
				Tile statusTile = statusTileAt(newLocation);

				if (statusTile != null && statusTile.isActive()) {
					locationList.add(newLocation);
				}
			}
		}

		return locationList;
	}

	public void setStatusTileAt(Location location, Tile newStatus) {
		gridStatusMap.get(location.row()).set(location.col(), newStatus);
	}

	public void setBaseRowList(int row, List<T> tileList) {
		baseGridMap.put(row, tileList);
	}

	public void setRowTileList(int row, List<Tile> tileList) {
		gridStatusMap.put(row, tileList);
	}
}
