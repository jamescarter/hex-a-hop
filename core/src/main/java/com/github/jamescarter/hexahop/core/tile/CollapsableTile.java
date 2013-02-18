package com.github.jamescarter.hexahop.core.tile;

import com.github.jamescarter.hexahop.core.grid.LevelTileGrid;
import com.github.jamescarter.hexahop.core.grid.TileGrid;
import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;

public class CollapsableTile extends Tile {
	private TileGrid<?> tileGrid;
	private boolean isWall;
	private boolean toggledExternalWalls = false;

	public CollapsableTile(TileGrid<?> tileGrid, Location location, boolean isWall) {
		super(location, (isWall) ? TileImage.COLLAPSABLE_WALL : TileImage.COLLAPSABLE);

		this.tileGrid = tileGrid;
		this.isWall = isWall;
	}

	@Override
	public Location stepOn(Direction direction) {
		if (isWall) {
			setTileImage(TileImage.COLLAPSABLE_WALL_ON);
		} else {
			setTileImage(TileImage.COLLAPSABLE_ON);
		}

		return null;
	}

	@Override
	public void stepOff() {
		deactiate();

		if (!containsBreakable(false)) {
			if (containsBreakable(true)) {
				toggleExternalWalls(true);
			} else {
				((LevelTileGrid)tileGrid).complete();
			}
		}
	}
	
	@Override
	public void undo() { 
		if (isActive()) {
			if (isWall) {
				setTileImage(TileImage.COLLAPSABLE_WALL);
			} else {
				setTileImage(TileImage.COLLAPSABLE);
			}
		} else {
			if (toggledExternalWalls) {
				toggleExternalWalls(false);
			}

			activate();
		}
	}

	@Override
	public boolean isWall() {
		return isWall;
	}

	public void toggleWall() {
		isWall = !isWall;

		if (isWall) {
			setTileImage(TileImage.COLLAPSABLE_WALL);
		} else {
			setTileImage(TileImage.COLLAPSABLE);
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
}
