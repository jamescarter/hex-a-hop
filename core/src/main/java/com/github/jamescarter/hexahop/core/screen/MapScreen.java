package com.github.jamescarter.hexahop.core.screen;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Json.Object;
import com.github.jamescarter.hexahop.core.grid.GridLoader;
import com.github.jamescarter.hexahop.core.grid.MapTileGrid;
import com.github.jamescarter.hexahop.core.grid.TileGrid;

public class MapScreen extends GridLoader {
	private static final ImageLayer bgLayer = graphics().createImageLayer(assets().getImage("images/gradient.png"));
	private MapTileGrid mapTileGrid;

	public MapScreen() throws Exception {
		Object jsonObj = PlayN.json().parse(
			assets().getTextSync("levels/map.json")
		);

		mapTileGrid = new MapTileGrid(jsonObj.getArray("grid"));
	}

	@Override
	public void load() {
		super.load();

		// TODO: draw lines between points
	}

	@Override
	public TileGrid<Integer> getTileGrid() {
		return mapTileGrid;
	}

	@Override
	public Layer getBackgroundLayer() {
		return bgLayer;
	}
}
