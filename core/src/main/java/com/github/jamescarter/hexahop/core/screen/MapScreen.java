package com.github.jamescarter.hexahop.core.screen;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.List;

import playn.core.Color;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.Surface;
import playn.core.SurfaceLayer;
import com.github.jamescarter.hexahop.core.callback.LevelLoadCallback;
import com.github.jamescarter.hexahop.core.grid.GridLoader;
import com.github.jamescarter.hexahop.core.grid.MapTileGrid;
import com.github.jamescarter.hexahop.core.grid.TileGrid;
import com.github.jamescarter.hexahop.core.json.StateJson;
import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.tile.MapStatusTile;
import com.github.jamescarter.hexahop.core.tile.Tile;

public class MapScreen extends GridLoader {
	private static final ImageLayer bgLayer = graphics().createImageLayer(assets().getImage("images/map_top.png"));
	private MapTileGrid mapTileGrid = new MapTileGrid();

	public MapScreen() {
		this(null);
	}

	public MapScreen(Location completedLevelLocation) {
		String mapJsonString;

		try {
			mapJsonString = assets().getTextSync("levels/map.json");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		StateJson<Integer> mapJson = new StateJson<Integer>(
			Integer.class,
			mapTileGrid,
			PlayN.json().parse(
				mapJsonString
			),
			StateJson.STORAGE_KEY_MAP
		);

		if (!mapJson.hasStatus()) {
			Location start = mapJson.start();

			mapTileGrid.setStatusAt(start, new MapStatusTile(start));
		}

		if (completedLevelLocation != null) {
			mapTileGrid.unlockConnected(completedLevelLocation);

			StateJson.store(mapTileGrid, StateJson.STORAGE_KEY_MAP);
		}
	}

	@Override
	public void load() {
		SurfaceLayer lineLayer = graphics().createSurfaceLayer(1000, 480);
		Surface surface = lineLayer.surface();
		surface.setFillColor(Color.rgb(39, 23, 107));

		boolean addLineLayer = false;

		// draw lines between connections
		for (int row=0; row<getTileGrid().rows(); row++) {
			List<Tile> tileList = getTileGrid().rowTileList(row);

			for (int col=0; col<tileList.size(); col++) {
				if (tileList.get(col) != null) {
					for (Location toLocation : mapTileGrid.connectedTo(new Location(col, row))) {
						addLineLayer = true;

						surface.drawLine(
							getColPosition(col, 32),
							getRowPosition(row, col, 38),
							getColPosition(toLocation.col(), 32),
							getRowPosition(toLocation.row(), toLocation.col(), 38),
							2
						);
					}
				}
			}
		}

		if (addLineLayer) {
			add(lineLayer);
		}

		super.load();

		PlayN.pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerStart(Pointer.Event event) {
				int x = (int) ((event.x() - getGridLayer().tx()) / 64);
				int y = (int) ((event.y() - getGridLayer().ty()) / 64);

				Location location = new Location(x, y);

				// Make sure the level is activated before allowing the user to load it
				if (mapTileGrid.statusAt(new Location(x, y)) != null) {
					assets().getText("levels/" + getLevelName(mapTileGrid.baseTileAt(location)) + ".json", new LevelLoadCallback(location));
				}
			}
		});
	}

	private String getLevelName(int levelId) {
		StringBuilder sb = new StringBuilder();

		if (levelId < 10) {
			sb.append("00");
		} else if (levelId < 100) {
			sb.append("0");
		}

		sb.append(levelId);

		return sb.toString();
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
