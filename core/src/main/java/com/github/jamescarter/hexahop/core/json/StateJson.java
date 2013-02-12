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
	public static final String STORAGE_KEY_MAP = "mapProgress";

	private HashMap<Integer, List<T>> baseGridMap = new HashMap<Integer, List<T>>();
	private HashMap<Integer, List<Tile>> gridStatusMap = new HashMap<Integer, List<Tile>>();
	private boolean hasStatus = false;
	private int par;
	private Location start;

	public StateJson(Class<T> type, Object baseJsonObj, String storageStatusKey) {
		String statusJsonString = PlayN.storage().getItem(storageStatusKey);

		if (statusJsonString == null) {
			statusJsonString = "{}";
		} else {
			hasStatus = true;
		}

		Object statusJsonObj = PlayN.json().parse(statusJsonString);

		Array baseStartArray = baseJsonObj.getArray("start");
		Array baseGridArray = baseJsonObj.getArray("grid");
		Array statusGridArray = statusJsonObj.getArray("grid");
		par = baseJsonObj.getInt("par");

		start = new Location(baseStartArray.getInt(0), baseStartArray.getInt(1));

		for (int row=0; row<baseGridArray.length(); row++) {
			Array baseValueArray = baseGridArray.getArray(row);
			Array statusValueArray = null;

			if (statusGridArray != null) {
				statusValueArray = statusGridArray.getArray(row);
			}

			List<T> baseValueList = new ArrayList<T>();
			List<Tile> statusValueList = new ArrayList<Tile>();

			for (int i=0; i<baseValueArray.length(); i++) {
				Integer baseValue = baseValueArray.getInt(i);

				if (baseValue == 0) {
					baseValueList.add(null);
				} else {
					if (type == Tile.class) {
						baseValueList.add((T) Tile.getTile(baseValue));
					} else {
						baseValueList.add((T) baseValue);
					}
				}

				if (statusValueArray == null) {
					statusValueList.add(null);
				} else {
					statusValueList.add(Tile.getTile(statusValueArray.getInt(i)));
				}
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

	public static void store(HashMap<Integer, List<Tile>> gridStatusMap, String storageStatusKey) {
		Writer writer = PlayN.json().newWriter();
		Writer gridWriter = writer.object().array("grid");

		for (int row=0; row<gridStatusMap.size(); row++) {
			List<Tile> rowTiles = gridStatusMap.get(row);
			Writer rowWriter = gridWriter.array();

			for (Tile tile : rowTiles) {
				if (tile == null) {
					rowWriter.value(0);
				} else {
					rowWriter.value(tile.id());
				}
			}

			rowWriter.end();
		}

		gridWriter.end();
		writer.end();

		PlayN.storage().setItem(storageStatusKey, writer.write());
	}
}
