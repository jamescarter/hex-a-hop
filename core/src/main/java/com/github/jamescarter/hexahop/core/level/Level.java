package com.github.jamescarter.hexahop.core.level;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.jamescarter.hexahop.core.Loadable;
import playn.core.GroupLayer;
import playn.core.Json.Array;
import playn.core.Json.Object;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.PlayN;

public class Level implements Loadable {
	private static final Image bgImage = assets().getImage("images/gradient.png");
	private HashMap<Integer, List<Tile>> gridMap = new HashMap<Integer, List<Tile>>();
	private String name;
	private int par;

	public Level(String json) {
		Object jsonObj = PlayN.json().parse(json);

		name = jsonObj.getString("name");
		par = jsonObj.getInt("par");

		Array gridArray = jsonObj.getArray("grid");

		for (int row=0; row<gridArray.length(); row++) {
			Array tiles = gridArray.getArray(row);
			List<Tile> tileList = new ArrayList<Tile>();

			gridMap.put(row, tileList);

			for (int tile=0; tile<tiles.length(); tile++) {
				tileList.add(Tile.getTile(tiles.getInt(tile)));
			}
		}
	}

	public String getName() {
		return name;
	}

	public int getPar() {
		return par;
	}

	public Tile getTile(int row, int col) {
		return gridMap.get(row).get(col);
	}

	@Override
	public void load() {
		GroupLayer levelLayer = graphics().createGroupLayer();

		ImageLayer bgLayer = graphics().createImageLayer(bgImage);

		for (int row : gridMap.keySet()) {
			List<Tile> tileList = gridMap.get(row);

			// Add even columns first so they overlap properly
			addTiles(levelLayer, tileList, row, 0); // even
			addTiles(levelLayer, tileList, row, 1); // odd
		}

		center(levelLayer);

		graphics().rootLayer().clear();
		graphics().rootLayer().add(bgLayer);
		graphics().rootLayer().add(levelLayer);
	}

	private void addTiles(GroupLayer levelLayer, List<Tile> tileList, int row, int start) {
		for (int col=start; col<tileList.size(); col+=2) {
			ImageLayer imageLayer = graphics().createImageLayer(tileList.get(col).getImage());

			imageLayer.setTranslation(getColPosition(col), getRowPosition(row, col));

			levelLayer.add(imageLayer);
		}
	}

	private void center(GroupLayer levelLayer) {
		int width = gridMap.get(0).size();
		int height = gridMap.size();

		levelLayer.setTranslation(
			((640 - (width * 46)) / 2) - 10,
			((480 - (height * 36)) / 2) - 32
		);
	}

	private int getColPosition(int col) {
		return col * 46;
	}

	private int getRowPosition(int row, int col) {
		if (col % 2 == 0) {
			return (row * 36);
		} else {
			return (row * 36) + 18;	
		}
	}
}
