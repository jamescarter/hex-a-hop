package com.github.jamescarter.hexahop.core.player;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import com.github.jamescarter.hexahop.core.level.Location;

import playn.core.Image;
import playn.core.gl.ImageLayerGL;

public class Player extends ImageLayerGL {
	private static final Image playerImage = assets().getImage("images/player.png");
	private Direction direction = Direction.SOUTH;
	private Position position = Position.STANDING;
	private Location location;

	public Player(Location location) {
		super(graphics().ctx(), playerImage);

		this.location = location;

		setWidth(65);
		setHeight(80);

		setImage();

		setPosition();
	}

	private void setImage() {
		setImage(playerImage.subImage(direction.x(), position.y(), 65, 80));
	}

	private void setPosition() {
		setTranslation(getGridColPosition(), getGridRowPosition());
	}

	private int getGridColPosition() {
		return location.col() * 46;
	}

	private int getGridRowPosition() {
		if (location.col() % 2 == 0) {
			return (location.row() * 36) - 18;
		} else {
			return (location.row() * 36);
		}
	}

	public void move(Direction direction) {
		this.direction = direction;

		location.move(direction);

		jumpTo(location, false);
	}

	public void jumpTo(Location newLocation, boolean isUndo) {
		if (isUndo) {
			direction = newLocation.to(location);
		}

		this.location = newLocation;

		setImage();
		setPosition();
	}

	public Location location() {
		return location;
	}
}
