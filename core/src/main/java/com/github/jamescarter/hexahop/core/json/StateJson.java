package com.github.jamescarter.hexahop.core.json;

import java.util.ArrayList;
import java.util.List;

import com.github.jamescarter.hexahop.core.grid.TileGrid;
import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.tile.Tile;

import playn.core.Json.Array;
import playn.core.Json.Object;
import playn.core.Json.Writer;
import playn.core.PlayN;

public class StateJson<T> {
	public static final String STORAGE_KEY_MAP = "mapProgress";
	private boolean hasStatus = false;
	private int par;
	private Location start;

	public StateJson(Class<T> type, TileGrid<T> tileGrid, Object baseJsonObj, String storageStatusKey) {
		this(type, tileGrid, baseJsonObj, storageStatusKey, false);
	}

	public StateJson(Class<T> type, TileGrid<T> tileGrid, Object baseJsonObj, String storageStatusKey, boolean cloneBase) {
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

			for (int col=0; col<baseValueArray.length(); col++) {
				Integer baseValue = baseValueArray.getInt(col);

				if (baseValue == 0) {
					baseValueList.add(null);
				} else {
					if (type == Tile.class) {
						baseValueList.add((T) Tile.newTile(tileGrid, new Location(col, row), baseValue));
					} else {
						baseValueList.add((T) baseValue);
					}
				}

				if (statusValueArray == null) {
					if (cloneBase) {
						statusValueList.add((Tile) baseValueList.get(baseValueList.size() - 1));
					} else {
						statusValueList.add(null);
					}
				} else {
					statusValueList.add(Tile.newTile(tileGrid, new Location(col, row), statusValueArray.getInt(col)));
				}
			}

			tileGrid.setBaseRowTileList(row, baseValueList);
			tileGrid.setRowTileList(row, statusValueList);
		}
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

	public static void store(TileGrid<?> tileGrid, String storageStatusKey) {
		Writer writer = PlayN.json().newWriter();
		Writer gridWriter = writer.object().array("grid");

		for (int row=0; row<tileGrid.rows(); row++) {
			List<Tile> rowTiles = tileGrid.rowTileList(row);
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
