package com.github.jamescarter.hexahop.core.test.grid;

import static org.junit.Assert.*;

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

	private LevelScreen level;

	@Before
	public void setUp() throws JsonParserException, Exception {
		level = new LevelScreen(new Location(0, 0), PlayN.assets().getTextSync("test/levels/test-002.json"));
	}

	@Test
	public void testCollapsable2Undo() {
		Tile statusTile = level.getTileGrid().statusTileAt(new Location(4, 5));

		assertEquals(new Location(0, 5), level.player());

		// step onto all the collapsable2 tiles once
		level.move(Direction.SOUTH_EAST);
		level.move(Direction.NORTH_EAST);
		level.move(Direction.NORTH_EAST);
		level.move(Direction.SOUTH_EAST);
		level.move(Direction.SOUTH);

		// step back onto the previous collapsable2 tile
		level.move(Direction.NORTH);

		assertEquals(new Location(4, 5), level.player());

		assertFalse(statusTile.isWall());

		assertTrue(level.undo());

		assertEquals(new Location(4, 6), level.player());

		// validate that the tile didn't turn into a wall
		assertFalse(statusTile.isWall());
	}
}
