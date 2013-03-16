package com.github.jamescarter.hexahop.core.json;

import java.util.ArrayList;
import java.util.List;

import playn.core.PlayN;
import playn.core.Json.Array;
import playn.core.Json.Object;
import playn.core.Json.Writer;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;

public class LevelMoveJson {
	public static void store(Location location, List<Direction> directionList) {
		if (directionList.isEmpty()) {
			clear(location);
			return;
		}

		Writer writer = PlayN.json().newWriter();
		Writer directionWriter = writer.object().array("list");

		for (Direction direction : directionList) {
			directionWriter.value(direction.toString());
		}

		directionWriter.end();
		writer.end();

		PlayN.storage().setItem(getStorageKey(location), writer.write());
	}

	public static List<Direction> retrieve(Location location) {
		List<Direction> directionList = new ArrayList<Direction>();

		String statusJsonString = PlayN.storage().getItem(getStorageKey(location));

		if (statusJsonString == null) {
			statusJsonString = "{}";
		}

		Object statusJsonObj = PlayN.json().parse(statusJsonString);

		Array directionArray = statusJsonObj.getArray("list");

		if (directionArray != null) {
			for (int i=0; i<directionArray.length(); i++) {
				directionList.add(Direction.valueOf(directionArray.getString(i)));
			}
		}

		return directionList;
	}

	public static void clear(Location location) {
		PlayN.storage().removeItem(getStorageKey(location));
	}

	private static String getStorageKey(Location location) {
		return "level-" + location.col() + "x" + location.row();
	}
}
