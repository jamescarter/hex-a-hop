package com.github.jamescarter.hexahop.core.player;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.screen.LevelScreen;

import playn.core.Image;
import playn.core.gl.ImageLayerGL;

public class Player extends ImageLayerGL {
	private static final Image playerImage = assets().getImage("images/player.png");
	private Direction direction = Direction.SOUTH;
	private LevelScreen level;
	private Location location;
	private boolean left = true;

	public Player(LevelScreen level, Location location) {
		super(graphics().ctx(), null);

		this.location = location;
		this.level = level;

		setWidth(65);
		setHeight(80);

		setImage(Position.STANDING);
		setTranslation(getGridColPosition(), getGridRowPosition());
	}

	private void setImage(Position position) {
		setImage(playerImage.subImage(direction.x(), position.y(), 65, 80));
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
		this.left = !left;

		animateMove();
	}

	public Location location() {
		return location;
	}

	private void animateMove() {
		level.anim.action(new Runnable() {
			@Override
			public void run() {
				setImage((left) ? Position.LEFT_JUMP : Position.RIGHT_JUMP);
			}
		}).then().tweenXY(this).to(getGridColPosition(), getGridRowPosition()).in(250).then().action(new Runnable() {
			@Override
			public void run() {
				setImage(Position.STANDING);
			}
		}).then().delay(0.5f).then().action(new Runnable() {
			@Override
			public void run() {
				level.stepOnTile(location, direction);
			}
		});
	}
}
