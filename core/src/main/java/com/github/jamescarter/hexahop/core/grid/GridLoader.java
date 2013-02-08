package com.github.jamescarter.hexahop.core.grid;

import static playn.core.PlayN.graphics;

import java.util.List;

import com.github.jamescarter.hexahop.core.Loadable;
import com.github.jamescarter.hexahop.core.level.Tile;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.Layer;

public abstract class GridLoader implements Loadable {
	private GroupLayer gridLayer = graphics().createGroupLayer();

	@Override
	public void load() {
		for (int row=0; row<getTileGrid().rows(); row++) {
			List<Tile> tileList = getTileGrid().rowTileList(row);

			// Add even columns first so they overlap properly
			addTiles(gridLayer, tileList, row, 0); // even
			addTiles(gridLayer, tileList, row, 1); // odd
		}

		center(gridLayer);

		graphics().rootLayer().clear();
		graphics().rootLayer().add(getBackgroundLayer());
		graphics().rootLayer().add(gridLayer);
	}

	public void addTiles(GroupLayer levelLayer, List<Tile> tileList, int row, int start) {
		for (int col=start; col<tileList.size(); col+=2) {
			ImageLayer imageLayer = graphics().createImageLayer(tileList.get(col).getImage());

			levelLayer.addAt(imageLayer, getColPosition(col), getRowPosition(row, col));
		}
	}

	public void center(GroupLayer levelLayer) {
		levelLayer.setTranslation(
			((640 - (getTileGrid().cols() * 46)) / 2) - 10,
			((480 - (getTileGrid().rows() * 36)) / 2) - 36
		);
	}

	public int getColPosition(int col) {
		return col * 46;
	}

	public int getRowPosition(int row, int col) {
		if (col % 2 == 0) {
			return (row * 36);
		} else {
			return (row * 36) + 18;	
		}
	}

	public GroupLayer getGridLayer() {
		return gridLayer;
	}

	public abstract TileGrid<?> getTileGrid();
	public abstract Layer getBackgroundLayer();
}