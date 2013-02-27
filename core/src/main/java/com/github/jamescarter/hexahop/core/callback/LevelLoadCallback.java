package com.github.jamescarter.hexahop.core.callback;

import com.github.jamescarter.hexahop.core.level.Level;
import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.screen.HexScreen;

import playn.core.util.Callback;

public class LevelLoadCallback implements Callback<String> {
	private Location location;

	public LevelLoadCallback(Location location) {
		this.location = location;
	}

	@Override
	public void onSuccess(String json) {
		HexScreen.screens.replace(new Level(location, json));
	}

	@Override
	public void onFailure(Throwable cause) {
		
	}
}
