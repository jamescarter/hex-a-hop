package com.github.jamescarter.hexahop.core.test.level;

import static org.junit.Assert.*;
import org.junit.Test;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;

public class LocationTest {
	@Test
	public void testTo() {
		assertEquals(Direction.SOUTH_WEST, new Location(7, 1).to(new Location(6, 2)));

		assertEquals(Direction.SOUTH_EAST, new Location(6, 0).to(new Location(7, 0)));
		assertEquals(Direction.SOUTH_EAST, new Location(8, 0).to(new Location(9, 0)));
		assertEquals(Direction.SOUTH_EAST, new Location(9, 1).to(new Location(10, 2)));

		assertEquals(Direction.NORTH_EAST, new Location(9, 0).to(new Location(10, 0)));
		assertEquals(Direction.NORTH_EAST, new Location(7, 0).to(new Location(8, 0)));

		assertEquals(Direction.NORTH_WEST, new Location(9, 1).to(new Location(6, 0)));
	}

	@Test
	public void testMove() {
		Location location = new Location(0, 0);

		location.move(Direction.SOUTH);
		assertEquals(new Location(0, 1), location);

		location.move(Direction.SOUTH_EAST);
		assertEquals(new Location(1, 1), location);

		location.move(Direction.SOUTH_WEST);
		assertEquals(new Location(0, 2), location);

		location.move(Direction.NORTH);
		assertEquals(new Location(0, 1), location);

		location.move(Direction.NORTH_EAST);
		assertEquals(new Location(1, 0), location);

		location.move(Direction.NORTH_WEST);
		assertEquals(new Location(0, 0), location);
	}
}
