package com.github.jamescarter.hexahop.core.callback;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.screen.MapScreen;

import playn.core.util.Callback;

public class MapLoadCallback implements Callback<String> {
	private Location location;

	public MapLoadCallback(Location location) {
		this.location = location;
	}

	@Override
	public void onSuccess(String json) {
		new MapScreen(location, json).load();
	}

	@Override
	public void onFailure(Throwable cause) {
		
	}
}
