package com.github.jamescarter.hexahop.core.tile;

import com.github.jamescarter.hexahop.core.grid.LevelTileGrid;
import com.github.jamescarter.hexahop.core.grid.TileGrid;
import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;

public class Collapsable2Tile extends Tile {
	private TileGrid<?> tileGrid;
	private boolean isWall;
	private boolean isBreakable = false;
	private boolean toggledExternalWalls = false;

	public Collapsable2Tile(TileGrid<?> tileGrid, Location location, boolean isWall) {
		super(location, (isWall) ? TileImage.COLLAPSABLE2_WALL : TileImage.COLLAPSABLE2);

		this.tileGrid = tileGrid;
		this.isWall = isWall;
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

		return null;
	}

	@Override
	public void stepOff() {
		if (isBreakable) {
			deactiate();

			if (!containsBreakable(false)) {
				if (containsBreakable(true)) {
					toggleExternalWalls(true);
				} else {
					((LevelTileGrid)tileGrid).complete();
				}
			}
		} else {
			setTileImage(TileImage.COLLAPSABLE);

			isBreakable = true;

			if (!containsBreakable2(false)) {
				if (containsBreakable2(true)) {
					toggleExternalWalls2(true);
				}
			}
		}
	}
	
	@Override
	public void undo() { 
		if (isActive()) {
			if (toggledExternalWalls) {
				toggleExternalWalls2(false);
			}

			if (isBreakable) {
				if (isWall) {
					setTileImage(TileImage.COLLAPSABLE_WALL);
				} else {
					setTileImage(TileImage.COLLAPSABLE);
				}

				isBreakable = false;
			} else {
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
			setTileImage(TileImage.COLLAPSABLE2);
		}
	}

	private void toggleExternalWalls(boolean flag) {
		for (int row=0; row<tileGrid.rows(); row++) {
			for (Tile tile : tileGrid.rowTileList(row)) {
				if (tile != null && tile.isWall() == flag && tile.isActive() && tile instanceof CollapsableTile) {
					((CollapsableTile) tile).toggleWall();
				}
			}
		}

		toggledExternalWalls = flag;
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

	private void toggleExternalWalls2(boolean flag) {
		for (int row=0; row<tileGrid.rows(); row++) {
			for (Tile tile : tileGrid.rowTileList(row)) {
				if (tile != null && tile.isWall() == flag && tile.isActive() && tile instanceof Collapsable2Tile) {
					Collapsable2Tile tile2 = (Collapsable2Tile) tile;

					if (!tile2.isBreakable()) {
						tile2.toggleWall();
					}
				}
			}
		}

		toggledExternalWalls = flag;
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
