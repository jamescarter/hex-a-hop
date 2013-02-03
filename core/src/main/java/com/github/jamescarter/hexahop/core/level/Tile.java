package com.github.jamescarter.hexahop.core.level;

import static playn.core.PlayN.assets;
import playn.core.Image;

public enum Tile {
	EMPTY,
	NORMAL					(1,  64, 4),
	COLLAPSABLE				(2, 128, 4),
	COLLAPSE_DOOR			(3, 192, 4),
	TRAMPOLINE				(4, 256, 4),
	SPINNER					(5, 320, 4),
	WALL					(6, 384, 4),
	COLLAPSABLE2			(7, 448, 4),
	COLLAPSE_DOOR2			(8, 513, 4),
	GUN						(9, 576, 4),
	TRAP					(10, 0, 68),
	BUILDER					(12, 128, 68),
	FLOATING_BALL			(14, 256, 68),
	LIFT_DOWN				(15, 320, 68),
	LIFT_UP					(16, 384, 68),
	TILE_LINK_2				(32, 576, 320),
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
	UNKNONW_5				(73, GUN, YELLOW_CONE);

	private static final Image tileImage = assets().getImage("images/tiles.png");

	private Tile[] tiles;
	private int id;
	private int x;
	private int y;

	private Tile() { }

	private Tile(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	private Tile (int id, Tile ... tiles) {
		this.id = id;
		this.tiles = tiles;
	}

	public int id() {
		return id;
	}

	public Image getImage() {
		if (id == 0) {
			return null;
		} else if (tiles != null) {
			// TODO:
			return null;
		} else {
			return tileImage.subImage(x, y, 64, 60);
		}
	}

	public static Tile getTile(int id) {
		for (Tile tile : values()) {
			if (tile.id() == id) {
				return tile;
			}
		}

		return EMPTY;
	}
}
