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
}
