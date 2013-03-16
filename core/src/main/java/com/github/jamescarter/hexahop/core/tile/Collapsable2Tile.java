package com.github.jamescarter.hexahop.core.tile;

import java.util.ArrayList;
import java.util.List;

import playn.core.PlayN;
import tripleplay.anim.Animator;

import com.github.jamescarter.hexahop.core.grid.LevelTileGrid;
import com.github.jamescarter.hexahop.core.grid.TileGrid;
import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;

public class Collapsable2Tile extends Tile {
	private TileGrid<?> tileGrid;
	private List<Tile> toggledTileList = new ArrayList<Tile>();
	private boolean isWall;
	private boolean isBreakable = false;
	private boolean isStoodOn = false;
	private Animator anim;

	public Collapsable2Tile(TileGrid<?> tileGrid, Location location, boolean isWall, Animator anim) {
		super(location, (isWall) ? TileImage.COLLAPSABLE2_WALL : TileImage.COLLAPSABLE2);

		this.tileGrid = tileGrid;
		this.isWall = isWall;
		this.anim = anim;
	}

	@Override
	public Location stepOn(Direction direction) {
		if (isBreakable) {
			if (isWall) {
				setTileImage(TileImage.COLLAPSABLE_WALL_ON);
			} else {
				setTileImage(TileImage.COLLAPSABLE_ON);
			}
		} else {
			if (isWall) {
				setTileImage(TileImage.COLLAPSABLE2_WALL_ON);
			} else {
				setTileImage(TileImage.COLLAPSABLE2_ON);
			}
		}

		isStoodOn = true;

		return null;
	}

	@Override
	public void stepOff() {
		if (isBreakable) {
			deactivate();

			if (!containsBreakable(false)) {
				if (containsBreakable(true)) {
					toggleExternalWalls();
				} else {
					((LevelTileGrid)tileGrid).complete();
				}
			}
		} else {
			setTileImage(TileImage.COLLAPSABLE);

			isBreakable = true;

			if (!containsBreakable2(false)) {
				if (containsBreakable2(true)) {
					toggleExternalWalls2();
				}
			}
		}

		isStoodOn = false;
	}

	@Override
	public void undo() { 
		if (isActive()) {
			if (toggledTileList.size() > 0) {
				for (Tile tile : toggledTileList) {
					if (tile instanceof CollapsableTile) {
						((CollapsableTile) tile).toggleWall();
					} else if (tile instanceof Collapsable2Tile) {
						((Collapsable2Tile) tile).toggleWall();
					}
				}

				toggledTileList.clear();
			}

			if (isBreakable) {
				if (isStoodOn) {
					isStoodOn = false;

					if (isWall) {
						setTileImage(TileImage.COLLAPSABLE_WALL);
					} else {
						setTileImage(TileImage.COLLAPSABLE);
					}
				} else {
					isBreakable = false;
					isStoodOn = true;

					if (isWall) {
						setTileImage(TileImage.COLLAPSABLE2_WALL_ON);
					} else {
						setTileImage(TileImage.COLLAPSABLE2_ON);
					}
				}
			} else {
				isStoodOn = false;

				if (isWall) {
					setTileImage(TileImage.COLLAPSABLE2_WALL);
				} else {
					setTileImage(TileImage.COLLAPSABLE2);
				}
			}
		} else {
			activate();
		}
	}

	@Override
	public boolean isWall() {
		return isWall;
	}

	public boolean isBreakable() {
		return isBreakable;
	}

	public void toggleWall() {
		isWall = !isWall;

		if (isWall) {
			setTileImage(TileImage.COLLAPSABLE2_WALL);
		} else {
			anim.delay(PlayN.random() * 500).then().action(new Runnable() {
				@Override
				public void run() {
					setTileImage(TileImage.COLLAPSABLE2);
				}
			});
		}
	}

	private void toggleExternalWalls() {
		for (int row=0; row<tileGrid.rows(); row++) {
			for (Tile tile : tileGrid.rowTileList(row)) {
				if (tile != null && tile.isWall() && tile.isActive() && tile instanceof CollapsableTile) {
					((CollapsableTile) tile).toggleWall();
					toggledTileList.add(tile);
				}
			}
		}
	}

	private boolean containsBreakable(boolean isWall) {
		for (int row=0; row<tileGrid.rows(); row++) {
			for (Tile tile : tileGrid.rowTileList(row)) {
				if (tile != null && (tile.isWall() == isWall && tile.isActive())) {
					if (tile instanceof CollapsableTile) {
						return true;
					} else if (tile instanceof Collapsable2Tile) {
						Collapsable2Tile tile2 = (Collapsable2Tile) tile;

						if (tile2.isBreakable()) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	private void toggleExternalWalls2() {
		for (int row=0; row<tileGrid.rows(); row++) {
			for (Tile tile : tileGrid.rowTileList(row)) {
				if (tile != null && tile.isWall() && tile.isActive() && tile instanceof Collapsable2Tile) {
					Collapsable2Tile tile2 = (Collapsable2Tile) tile;

					if (!tile2.isBreakable()) {
						tile2.toggleWall();
						toggledTileList.add(tile);
					}
				}
			}
		}
	}

	private boolean containsBreakable2(boolean isWall) {
		for (int row=0; row<tileGrid.rows(); row++) {
			for (Tile tile : tileGrid.rowTileList(row)) {
				if (tile != null && (tile.isWall() == isWall && tile.isActive() && tile instanceof Collapsable2Tile)) {
					Collapsable2Tile tile2 = (Collapsable2Tile) tile;

					if (!tile2.isBreakable()) {
						return true;
					}					
				}
			}
		}

		return false;
	}
}
