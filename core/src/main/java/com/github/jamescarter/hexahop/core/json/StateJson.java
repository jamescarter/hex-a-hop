package com.github.jamescarter.hexahop.core.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.level.Tile;
import com.github.jamescarter.hexahop.core.player.Direction;

import playn.core.Json.Array;
import playn.core.Json.Object;
import playn.core.Json.Writer;
import playn.core.PlayN;

public class StateJson<T> {
	private HashMap<Integer, List<T>> baseGridMap = new HashMap<Integer, List<T>>();
	private HashMap<Integer, List<Tile>> gridStatusMap = new HashMap<Integer, List<Tile>>();
	private boolean hasStatus = false;
	private int par;
	private Location start;

	public StateJson(Class<T> type, Object mapJsonObj, String storageStatusKey) {
		Array startArray = mapJsonObj.getArray("start");
		Array gridArray = mapJsonObj.getArray("grid");
		par = mapJsonObj.getInt("par");

		start = new Location(startArray.getInt(0), startArray.getInt(1));

		for (int row=0; row<gridArray.length(); row++) {
			Array valueArray = gridArray.getArray(row);

			List<T> baseValueList = new ArrayList<T>();
			List<Tile> statusValueList = new ArrayList<Tile>();

			for (int i=0; i<valueArray.length(); i++) {
				Integer value = valueArray.getInt(i);

				if (value == 0) {
					baseValueList.add(null);
				} else {
					if (type == Tile.class) {
						baseValueList.add((T) Tile.getTile(value));
					} else {
						baseValueList.add((T) type.cast(value));
					}
				}

				statusValueList.add(null);
			}

			baseGridMap.put(row, baseValueList);
			gridStatusMap.put(row, statusValueList);
		}
	}

	public HashMap<Integer, List<T>> getBaseGridMap() {
		return baseGridMap;
	}

	public HashMap<Integer, List<Tile>> getGridStatusMap() {
		return gridStatusMap;
	}

	public int par() {
		return par;
	}

	public Location start() {
		return start;
	}

	public boolean hasStatus() {
		return hasStatus;
	}

	public List<Direction> getMoveList() {
		// TODO:
		return new ArrayList<Direction>();
	}
}
