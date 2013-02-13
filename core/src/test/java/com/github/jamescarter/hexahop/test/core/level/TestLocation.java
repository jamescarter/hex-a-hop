package com.github.jamescarter.hexahop.test.core.level;

import static org.junit.Assert.*;
import org.junit.Test;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.player.Direction;

public class TestLocation {
	@Test
	public void testTo() {
		assertEquals(new Location(7, 1).to(new Location(6, 2)), Direction.SOUTH_WEST);

		assertEquals(new Location(6, 0).to(new Location(7, 0)), Direction.SOUTH_EAST);
		assertEquals(new Location(8, 0).to(new Location(9, 0)), Direction.SOUTH_EAST);
		assertEquals(new Location(9, 1).to(new Location(10, 2)), Direction.SOUTH_EAST);

		assertEquals(new Location(9, 0).to(new Location(10, 0)), Direction.NORTH_EAST);
		assertEquals(new Location(7, 0).to(new Location(8, 0)), Direction.NORTH_EAST);

		assertEquals(new Location(9, 1).to(new Location(6, 0)), Direction.NORTH_WEST);
	}
}
