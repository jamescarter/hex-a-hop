package com.github.jamescarter.hexahop.core.level;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.jamescarter.hexahop.core.Loadable;
import com.github.jamescarter.hexahop.core.player.Direction;
import com.github.jamescarter.hexahop.core.player.Player;
import playn.core.GroupLayer;
import playn.core.Json.Array;
import playn.core.Json.Object;
import playn.core.Key;
import playn.core.Keyboard.Event;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Keyboard;
import playn.core.PlayN;
import playn.core.Pointer;

public class Level implements Loadable {
	private static final Image bgImage = assets().getImage("images/gradient.png");
	private HashMap<Integer, List<Tile>> gridMap = new HashMap<Integer, List<Tile>>();
	private List<Direction> moveList = new ArrayList<Direction>();
	private String name;
	private int par;
	private Player player;

	public Level(String json) {
		Object jsonObj = PlayN.json().parse(json);

		name = jsonObj.getString("name");
		par = jsonObj.getInt("par");

		Array start = jsonObj.getArray("start");
		Array gridArray = jsonObj.getArray("grid");

		player = new Player(start.getInt(0), start.getInt(1));

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
		final GroupLayer levelLayer = graphics().createGroupLayer();

		ImageLayer bgLayer = graphics().createImageLayer(bgImage);

		for (int row : gridMap.keySet()) {
			List<Tile> tileList = gridMap.get(row);

			// Add even columns first so they overlap properly
			addTiles(levelLayer, tileList, row, 0); // even
			addTiles(levelLayer, tileList, row, 1); // odd
		}

		levelLayer.add(player);

		center(levelLayer);

		graphics().rootLayer().clear();
		graphics().rootLayer().add(bgLayer);
		graphics().rootLayer().add(levelLayer);

		PlayN.pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerStart(Pointer.Event event) {
				// Offset clicked location based on where the levelLayer is centered
				Direction direction = player.getDirection(
					event.x() - levelLayer.tx(),
					event.y() - levelLayer.ty()
				);

				move(direction);
			}
		});

		PlayN.keyboard().setListener(new Keyboard.Adapter() {
			@Override
			public void onKeyDown(Event event) {
				if (event.key().equals(Key.U) || event.key().equals(Key.Z)) {
					undo();
				} else if (event.key().equals(Key.UP) || event.key().equals(Key.W)) {
					move(Direction.NORTH);
				} else if (event.key().equals(Key.DOWN) || event.key().equals(Key.S)) {
					move(Direction.SOUTH);
				} else if (event.key().equals(Key.Q)) {
					move(Direction.NORTH_WEST);
				} else if (event.key().equals(Key.E)) {
					move(Direction.NORTH_EAST);
				} else if (event.key().equals(Key.A)) {
					move(Direction.SOUTH_WEST);
				} else if (event.key().equals(Key.D)) {
					move(Direction.SOUTH_EAST);
				}
			}
		});
	}

	private void move(Direction direction) {
		// TODO: check it's valid -> add-to-undo -> player.move(direction, isUndo);

		moveList.add(direction);

		player.move(direction, false);
	}

	/**
	 * Undo the last move.

	 * @return true if there are more moves to undo, otherwise false.
	 */
	private boolean undo() {
		if (moveList.isEmpty()) {
			return false;
		}

		Direction direction = moveList.remove(moveList.size() - 1);

		player.move(direction, true);

		return true;
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
			((480 - (height * 36)) / 2) - 36
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