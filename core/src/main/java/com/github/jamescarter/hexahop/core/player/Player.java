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
		super(graphics().ctx());

		this.location = location;
		this.level = level;

		setWidth(65);
		setHeight(80);
		setDepth(location.row());

		setImage(Position.STANDING);
		setTranslation(level.getColPosition(location.col(), 0), level.getRowPosition(location.row(), location.col(), -18));
	}

	private void setImage(Position position) {
		setImage(playerImage.subImage(direction.x(), position.y(), 65, 80));
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

		setDepth(newLocation.row() - ((location.col() % 2 == 0) ? 0.3f : 0.1f));

		animateMove(isUndo);
	}

	public void look(Direction direction) {
		this.direction = direction;

		setImage(Position.STANDING);
	}

	public Location location() {
		return location;
	}

	private void animateMove(final boolean isUndo) {
		level.anim.action(new Runnable() {
			@Override
			public void run() {
				setImage((left) ? Position.LEFT_JUMP : Position.RIGHT_JUMP);
			}
		}).then().tweenXY(this).to(level.getColPosition(location.col(), 0), level.getRowPosition(location.row(), location.col(), -18)).in(250).then().action(new Runnable() {
			@Override
			public void run() {
				setImage(Position.STANDING);
			}
		}).then().delay(200).then().action(new Runnable() {
			@Override
			public void run() {
				if (isUndo) {
					level.getTileGrid().statusTileAt(location()).undo();
				} else {
					level.stepOnTile(location, direction);
				}
			}
		});
	}
}
