package com.github.jamescarter.hexahop.core.player;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import playn.core.Image;
import playn.core.gl.ImageLayerGL;

public class Player extends ImageLayerGL {
	private static final Image playerImage = assets().getImage("images/player.png");
	private Direction direction = Direction.SOUTH;
	private Position position = Position.STANDING;

	private int col;
	private int row;

	public Player(int col, int row) {
		super(graphics().ctx(), playerImage);

		this.col = col;
		this.row = row;

		setWidth(65);
		setHeight(80);

		setImage(getImage());

		setPosition();
	}

	private Image getImage() {
		return playerImage.subImage(direction.x(), position.y(), 65, 80);
	}

	private void setPosition() {
		setTranslation(getColPosition(), getRowPosition());
	}

	private int getColPosition() {
		return col * 46;
	}

	private int getRowPosition() {
		if (col % 2 == 0) {
			return (row * 36);
		} else {
			return (row * 36) + 18;	
		}
	}

	public Direction getDirection(float selectedX, float selectedY) {
		// modify player co-ordinates to center of player image
		float playerX = tx() + 30;
		float playerY = ty() + 42;

		boolean isNorthOrSouthOnly = (Math.abs(selectedX - playerX) < 10) ? true : false;
		boolean isNorth = (selectedY < playerY) ? true : false;
		boolean isEast = (selectedX > playerX) ? true : false;

		if (isNorthOrSouthOnly) {
			if (isNorth) {
				return Direction.NORTH;
			} else {
				return Direction.SOUTH;
			}
		} else {
			if (isNorth) {
				if (isEast) {
					return Direction.NORTH_EAST;
				} else {
					return Direction.NORTH_WEST;
				}
			} else {
				if (isEast) {
					return Direction.SOUTH_EAST;
				} else {
					return Direction.SOUTH_WEST;
				}
			}
		}
	}
}
