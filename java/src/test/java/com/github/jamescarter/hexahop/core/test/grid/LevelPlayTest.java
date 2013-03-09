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
import com.github.jamescarter.hexahop.core.tile.TileImage;

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
		assertEquals(new Location(0, 5), level.player());

		level.move(Direction.SOUTH_EAST);
		level.move(Direction.NORTH_EAST);
		level.move(Direction.NORTH_EAST);
		level.move(Direction.SOUTH_EAST);
		level.move(Direction.SOUTH);
		level.move(Direction.NORTH);

		assertEquals(new Location(4, 5), level.player());

		assertTrue(level.undo());

		assertEquals(new Location(4, 6), level.player());
	}
}
