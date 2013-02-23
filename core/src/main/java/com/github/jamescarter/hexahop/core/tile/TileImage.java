package com.github.jamescarter.hexahop.core.tile;

import static playn.core.PlayN.assets;
import playn.core.Image;

public enum TileImage {
	STONE					( 1, 1, 0),
	STONE_WALL				( 6, 6, 0),

	COLLAPSABLE				( 2, 2, 0),
	COLLAPSABLE_ON			(22, 1, 4),
	COLLAPSABLE_WALL		( 3, 3, 0),
	COLLAPSABLE_WALL_ON		(23, 2, 4),

	COLLAPSABLE2			( 7, 7, 0),
	COLLAPSABLE2_ON			(27, 3, 4),
	COLLAPSABLE2_WALL		( 8, 8, 0),
	COLLAPSABLE2_WALL_ON	(28, 7, 0),

	LIFT_DOWN				(15, 5, 1),
	LIFT_UP					(16, 6, 1),

	BUILDER					(12, 2, 1),
	TRAMPOLINE				( 4, 4, 0),
	SPINNER					( 5, 5, 0),
	GUN						( 9, 9, 0),

	JOIN					(80, 0, 2),
	INCOMPLETE				(81, 1, 2),
	COMPLETE				(82, 2, 2),
	PERFECT					(83, 3, 2);

	private static final Image tileImage = assets().getImage("images/tiles.png");

	private int id;
	private int col;
	private int row;

	private TileImage (int id, int col, int row) {
		this.id = id;
		this.col = col;
		this.row = row;
	}

	public int id() {
		return id;
	}

	public Image getImage() {
		return tileImage.subImage(col * 64, row * 64, 64, 64);
	}

	public static TileImage getTileImage(int id) {
		for (TileImage ti : values()) {
			if (ti.id() == id) {
				return ti;
			}
		}

		return null;
	}
}
