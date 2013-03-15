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

	public Direction to(Location to) {
		int toRow = to.row();

		if (to.col() % 2 == 0) {
			--toRow;
		}

		if (col == to.col()) {
			if (row > to.row()) {
				return Direction.NORTH;
			} else {
				return Direction.SOUTH;
			}
		} else if (row > toRow) {
			if (col > to.col()) {
				return Direction.NORTH_WEST;
			} else {
				return Direction.NORTH_EAST;
			}
		} else {
			if (col > to.col()) {
				return Direction.SOUTH_WEST;
			} else {
				return Direction.SOUTH_EAST;
			}
		}
	}

	public Location clone() {
		return new Location(col, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Location) {
			Location that = (Location) obj;

			if (this.col() == that.col() && this.row() == that.row()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return col + ", " + row;
	}
}
