package com.github.jamescarter.hexahop.core.level;

import com.github.jamescarter.hexahop.core.player.Direction;

public class Location {
	private int col;
	private int row;

	public Location(int col, int row) {
		this.col = col;
		this.row = row;
	}

	public int col() {
		return col;
	}

	public int row() {
		return row;
	}

	public void move(Direction direction) {
		switch(direction) {
			case NORTH:
				--row;
			break;
			case SOUTH:
				++row;
			break;
			case NORTH_EAST:
				++col;

				if (col % 2 != 0) {
					--row;
				}
			break;
			case SOUTH_EAST:
				++col;

				if (col % 2 == 0) {
					++row;
				}
			break;
			case NORTH_WEST:
				--col;

				if (col % 2 != 0) {
					--row;
				}
			break;
			case SOUTH_WEST:
				--col;

				if (col % 2 == 0) {
					++row;
				}
			break;
		}
	}

	public Location clone() {
		return new Location(col, row);
	}
}
