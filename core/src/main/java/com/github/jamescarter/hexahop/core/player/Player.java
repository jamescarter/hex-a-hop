package com.github.jamescarter.hexahop.core.player;

import static playn.core.PlayN.assets;
import playn.core.Image;

public class Player {
	private static final Image playerImage = assets().getImage("images/player.png");

	public static final Image getImage(Direction dir, Position pos) {
		return playerImage.subImage(dir.x(), pos.y(), 65, 80);
	}
}
