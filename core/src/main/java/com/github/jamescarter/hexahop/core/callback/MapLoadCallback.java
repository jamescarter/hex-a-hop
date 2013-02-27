package com.github.jamescarter.hexahop.core.callback;

import com.github.jamescarter.hexahop.core.level.Location;
import com.github.jamescarter.hexahop.core.screen.HexScreen;
import com.github.jamescarter.hexahop.core.screen.MapScreen;

import playn.core.util.Callback;

public class MapLoadCallback implements Callback<String> {
	private Location location;
	private boolean par;

	public MapLoadCallback() {
		this(null, false);
	}

	public MapLoadCallback(Location location, boolean par) {
		this.location = location;
		this.par = par;
	}

	@Override
	public void onSuccess(String json) {
		HexScreen.screens.replace(new MapScreen(location, par, json));
	}

	@Override
	public void onFailure(Throwable cause) {
		
	}
}
