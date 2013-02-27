package com.github.jamescarter.hexahop.core.grid;

import static playn.core.PlayN.graphics;

import java.util.List;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.screen.HexScreen;
import com.github.jamescarter.hexahop.core.tile.Tile;

import playn.core.GroupLayer;
import playn.core.Layer;

public abstract class GridLoader extends HexScreen {
	private GroupLayer gridLayer = graphics().createGroupLayer();

	@Override
	public void wasAdded() {
		for (int row=0; row<getTileGrid().rows(); row++) {
			List<Tile> tileList = getTileGrid().rowTileList(row);

			// Add even column tiles first so they overlap properly with the odd columns
			addTiles(gridLayer, tileList, row, 0); // even
			addTiles(gridLayer, tileList, row, 1); // odd
		}

		layer.add(getBackgroundLayer());
		layer.add(gridLayer);
	}

	public void addTiles(GroupLayer levelLayer, List<Tile> tileList, int row, int start) {
		for (int col=start; col<tileList.size(); col+=2) {
			Tile tile = tileList.get(col);

			if (tile != null) {
				levelLayer.addAt(tile, getColPosition(col, 0), getRowPosition(row, col, 0));
			}
		}
	}

	public void add(Layer layer) {
		gridLayer.add(layer);
	}

	public int getColPosition(int col, int offset) {
		return (col * 46) + offset;
	}

	public int getRowPosition(int row, int col, int offset) {
		if (col % 2 == 0) {
			return (row * 36) + offset;
		} else {
			return (row * 36) + 18 + offset;	
		}
	}

	public Location getGridLocation(float x, float y) {
		// Offset clicked location based on where the levelLayer is centered
		int col = (int) ((x - getGridLayer().tx()) / 48);
		int row = (int) ((y - getGridLayer().ty()) / 38);

		if (col % 2 != 0) {
			row--;
		}

		return new Location(col, row);
	}

	public GroupLayer getGridLayer() {
		return gridLayer;
	}

	public abstract TileGrid<?> getTileGrid();
	public abstract Layer getBackgroundLayer();
}