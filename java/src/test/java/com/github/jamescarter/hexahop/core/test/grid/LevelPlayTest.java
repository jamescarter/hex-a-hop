package com.github.jamescarter.hexahop.core.test.grid;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import playn.core.PlayN;
import playn.core.json.JsonParserException;
import playn.java.JavaPlatform;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;
import com.github.jamescarter.hexahop.core.screen.LevelScreen;
import com.github.jamescarter.hexahop.core.tile.Tile;

public class LevelPlayTest {
	static {
		JavaPlatform platform = JavaPlatform.register();
		platform.assets().setPathPrefix("com/github/jamescarter/hexahop/resources");
	}

	private LevelScreen level2;
	private LevelScreen level3;

	@Before
	public void setUp() throws JsonParserException, Exception {
		level2 = new LevelScreen(new Location(0, 0), PlayN.assets().getTextSync("test/levels/test-002.json"));
		level3 = new LevelScreen(new Location(0, 0), PlayN.assets().getTextSync("test/levels/test-003.json"));
	}

	@Test
	public void testCollapsable2Undo() {
		Tile statusTile = level2.getTileGrid().statusTileAt(new Location(4, 5));

		assertEquals(new Location(0, 5), level2.player());

		// step onto all the collapsable2 tiles once
		level2.move(Direction.SOUTH_EAST);
		level2.move(Direction.NORTH_EAST);
		level2.move(Direction.NORTH_EAST);
		level2.move(Direction.SOUTH_EAST);
		level2.move(Direction.SOUTH);

		// step back onto the previous collapsable2 tile
		level2.move(Direction.NORTH);

		assertEquals(new Location(4, 5), level2.player());

		assertFalse(statusTile.isWall());

		assertTrue(level2.undo());

		assertEquals(new Location(4, 6), level2.player());

		// validate that the tile didn't turn into a wall
		assertFalse(statusTile.isWall());
	}

	@Test
	public void testCollapsable2Undo2() {
		ArrayList<Tile> tileList = new ArrayList<Tile>();

		tileList.add(level3.getTileGrid().statusTileAt(new Location(2, 2)));
		tileList.add(level3.getTileGrid().statusTileAt(new Location(2, 3)));
		tileList.add(level3.getTileGrid().statusTileAt(new Location(3, 1)));
		tileList.add(level3.getTileGrid().statusTileAt(new Location(3, 3)));
		tileList.add(level3.getTileGrid().statusTileAt(new Location(4, 2)));
		tileList.add(level3.getTileGrid().statusTileAt(new Location(4, 3)));

		level3.move(Direction.NORTH_EAST);

		for (Tile tile : tileList) {
			assertTrue(tile.isWall());
		}

		level3.move(Direction.SOUTH_WEST);

		for (Tile tile : tileList) {
			assertFalse(tile.isWall());
		}

		// undo the last move that converted the walls to normal tiles
		level3.undo();

		for (Tile tile : tileList) {
			assertTrue(tile.isWall());
		}

		// undo the first move and confirm the gird has the original state when it was loaded
		level3.undo();

		for (Tile tile : tileList) {
			assertTrue(tile.isWall());
		}
	}
}
