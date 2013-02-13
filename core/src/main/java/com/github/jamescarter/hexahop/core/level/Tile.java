package com.github.jamescarter.hexahop.core.level;

import static playn.core.PlayN.assets;
import playn.core.Image;

public enum Tile {
	NORMAL					(1, 1, 0, false, false),
	COLLAPSABLE				(2, 2, 0),
	COLLAPSE_DOOR			(3, 3, 0, true, true),
	TRAMPOLINE				(4, 4, 0, false, false),
	SPINNER					(5, 5, 0),
	WALL					(6, 6, 0, false, true),
	COLLAPSABLE2			(7, 7, 0),
	COLLAPSE_DOOR2			(8, 8, 0),
	GUN						(9, 9, 0),
	TRAP					(10, 0, 1),
	BUILDER					(12, 2, 1),
	FLOATING_BALL			(14, 4, 1),
	LIFT_DOWN				(15, 5, 1),
	LIFT_UP					(16, 6, 1),
	TILE_LINK_2				(32, 9, 5),
	TILE_LINK_3				(33, NORMAL, TILE_LINK_2),
	TILE_LINK_4				(34, COLLAPSABLE, TILE_LINK_2),
	TILE_GREEN_FRAGMENT		(36, TRAMPOLINE, TILE_LINK_2),
	TILE_GREEN_FRAGMENT_1	(37, SPINNER, TILE_LINK_2),
	TILE_ITEM2				(39, COLLAPSABLE2, TILE_LINK_2),
	TILE_GREEN_CRACKED		(41, GUN, TILE_LINK_2),
	TILE_GREEN_CRACKED_WALL	(42, TRAP, TILE_LINK_2),
	YELLOW_CONE				(999, 0, 0),
	UNKNONW_1				(71, COLLAPSABLE2, YELLOW_CONE),
	UNKNONW_2				(69, SPINNER, YELLOW_CONE),
	UNKNONW_3				(66, COLLAPSABLE, YELLOW_CONE),
	UNKNONW_4				(65, NORMAL, YELLOW_CONE),
	UNKNONW_5				(73, GUN, YELLOW_CONE),
	JOIN					(80, 0, 2),
	INCOMPLETE				(81, 1, 2),
	COMPLETE				(82, 2, 2),
	PERFECT					(83, 3, 2);

	private static final Image tileImage = assets().getImage("images/tiles.png");

	private Tile[] tiles;
	private int id;
	private int col;
	private int row;
	private boolean breakable;
	private boolean tall;

	private Tile(int id, int col, int row) {
		this(id, col, row, true, false);
	}

	private Tile(int id, int col, int row, boolean breakable, boolean tall) {
		this.id = id;
		this.col = col;
		this.row = row;
		this.breakable = breakable;
		this.tall = tall;
	}

	private Tile (int id, Tile ... tiles) {
		this.id = id;
		this.tiles = tiles;
	}

	public int id() {
		return id;
	}

	public boolean isBreakable() {
		return breakable;
	}

	public boolean isTall() {
		return tall;
	}

	public Image getImage() {
		if (id == 0) {
			return null;
		} else if (tiles != null) {
			// TODO:
			return null;
		} else {
			return tileImage.subImage(col * 64, row * 64, 64, 64);
		}
	}

	public static Tile getTile(int id) {
		for (Tile tile : values()) {
			if (tile.id() == id) {
				return tile;
			}
		}

		return null;
	}
}
