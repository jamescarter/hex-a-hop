package com.github.jamescarter.hexahop.core.test.grid;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import playn.core.PlayN;
import playn.core.json.JsonParserException;
import playn.java.JavaPlatform;

import com.github.jamescarter.hexahop.core.grid.LevelTileGrid;
import com.github.jamescarter.hexahop.core.json.StateJson;
import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;
import com.github.jamescarter.hexahop.core.tile.Collapsable2Tile;
import com.github.jamescarter.hexahop.core.tile.CollapsableTile;
import com.github.jamescarter.hexahop.core.tile.GunTile;
import com.github.jamescarter.hexahop.core.tile.StoneTile;
import com.github.jamescarter.hexahop.core.tile.Tile;
import com.github.jamescarter.hexahop.core.tile.TrampolineTile;

public class LevelTileGridTest {
	static {
		JavaPlatform platform = JavaPlatform.register();
		platform.assets().setPathPrefix("com/github/jamescarter/hexahop/resources");
	}

	private LevelTileGrid levelTileGrid;
	private Location playerLocation;

	@Before
	public void setUp() throws JsonParserException, Exception {
		levelTileGrid = new LevelTileGrid(null);
		playerLocation = new Location(5, 4);

		new StateJson<Tile>(
			Tile.class,
			levelTileGrid,
			null,
			PlayN.json().parse(PlayN.assets().getTextSync("test/levels/test-001.json")),
			"levelTileGridTest",
			true
		);
	}

	@Test
	public void testRowsAndCols() {
		assertEquals(8, levelTileGrid.rows());
		assertEquals(11, levelTileGrid.cols());
	}

	@Test
	public void testBaseTileAt() {
		assertTrue(levelTileGrid.baseTileAt(new Location(1, 3)) instanceof CollapsableTile);
		assertTrue(levelTileGrid.baseTileAt(new Location(6, 6)) instanceof Collapsable2Tile);
		assertTrue(levelTileGrid.baseTileAt(new Location(8, 0)) instanceof GunTile);
		assertTrue(levelTileGrid.baseTileAt(new Location(0, 4)) instanceof StoneTile);
		assertTrue(levelTileGrid.baseTileAt(new Location(2, 4)) instanceof TrampolineTile);
	}

	@Test
	public void testStatusAt() {
		assertTrue(levelTileGrid.statusAt(new Location(1, 3)) instanceof CollapsableTile);
		assertTrue(levelTileGrid.statusAt(new Location(6, 6)) instanceof Collapsable2Tile);
		assertTrue(levelTileGrid.statusAt(new Location(8, 0)) instanceof GunTile);
		assertTrue(levelTileGrid.statusAt(new Location(0, 4)) instanceof StoneTile);
		assertTrue(levelTileGrid.statusAt(new Location(2, 4)) instanceof TrampolineTile);
	}

	@Test
	public void testIsOutOfBounds() {
		assertTrue(levelTileGrid.isOutOfBounds(new Location(-1, 2)));
		assertTrue(levelTileGrid.isOutOfBounds(new Location(1, -1)));
		assertTrue(levelTileGrid.isOutOfBounds(new Location(10, 8)));
		assertTrue(levelTileGrid.isOutOfBounds(new Location(11, 7)));
	}

	@Test
	public void testIsNotOutOfBounds() {
		assertFalse(levelTileGrid.isOutOfBounds(new Location(0, 0)));
		assertFalse(levelTileGrid.isOutOfBounds(new Location(10, 7)));
	}

	@Test
	public void testCanMove() {
		assertTrue(levelTileGrid.canMove(playerLocation, Direction.NORTH));
		assertTrue(levelTileGrid.canMove(playerLocation, Direction.SOUTH_WEST));
		assertTrue(levelTileGrid.canMove(playerLocation, Direction.NORTH_WEST));
	}

	@Test
	public void testCantMove() {
		assertFalse(levelTileGrid.canMove(playerLocation, Direction.SOUTH));
		assertFalse(levelTileGrid.canMove(playerLocation, Direction.SOUTH_EAST));
		assertFalse(levelTileGrid.canMove(playerLocation, Direction.NORTH_EAST));
	}

	@Test
	public void testStatusConnectedTo() {
		assertEquals(3, levelTileGrid.statusConnectedTo(playerLocation).size());
	}
}
