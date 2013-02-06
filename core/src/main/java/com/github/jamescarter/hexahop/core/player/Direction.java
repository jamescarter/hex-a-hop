package com.github.jamescarter.hexahop.core.player;

public enum Direction {
	NORTH		(0),
	SOUTH		(190),
	NORTH_EAST	(64),
	NORTH_WEST	(320),
	SOUTH_WEST	(258),
	SOUTH_EAST	(126);

	private int x;

	private Direction(int x) {
		this.x = x;
	}

	public int x() {
		return x;
	}

	public Direction opposite() {
		switch(this) {
			case NORTH:
				return SOUTH;
			case NORTH_EAST:
				return SOUTH_WEST;
			case NORTH_WEST:
				return SOUTH_EAST;
			case SOUTH:
				return NORTH;
			case SOUTH_EAST:
				return NORTH_WEST;
			case SOUTH_WEST:
				return NORTH_EAST;
		}

		return null;
	}
}
