package com.github.jamescarter.hexahop.core.player;

public enum Position {
	STANDING	(0),
	LEFT_JUMP	(160),
	LEFT_SLIDE	(478),
	RIGHT_JUMP	(318),
	RIGHT_SLIDE	(638);

	private int y;

	private Position(int y) {
		this.y = y;
	}

	public int y() {
		return y;
	}
}
