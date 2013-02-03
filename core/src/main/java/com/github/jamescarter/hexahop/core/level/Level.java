package com.github.jamescarter.hexahop.core.level;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.jamescarter.hexahop.core.Loadable;

import playn.core.Json.Array;
import playn.core.Json.Object;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.PlayN;

public class Level implements Loadable {
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
		graphics().rootLayer().clear();

		Image bgImage = assets().getImage("images/gradient.png");

		ImageLayer bgLayer = graphics().createImageLayer(bgImage);

		graphics().rootLayer().add(bgLayer);

		for (int row : gridMap.keySet()) {
			List<Tile> tileList = gridMap.get(row);

			for (int col=0; col<tileList.size(); col++) {
				ImageLayer imageLayer = graphics().createImageLayer(tileList.get(col).getImage());

				imageLayer.setTranslation(col * 45, (row * 36) + (18 * (col + 1)));

				graphics().rootLayer().add(imageLayer);
			}
		}
	}
}
