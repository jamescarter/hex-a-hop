package com.github.jamescarter.hexahop.core.grid;

import static playn.core.PlayN.graphics;

import java.util.List;

import com.github.jamescarter.hexahop.core.HexAHop;
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

			addTiles(gridLayer, tileList, row);
		}

		layer.add(getBackgroundLayer());
		layer.add(gridLayer);
	}

	public void addTiles(GroupLayer levelLayer, List<Tile> tileList, int row) {
		for (int col=0; col<tileList.size(); col++) {
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
		// Offset clicked location based on where the levelLayer is positioned
		x = x / HexAHop.scaleY;
		y = y / HexAHop.scaleY;

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