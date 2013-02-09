package com.github.jamescarter.hexahop.core.callback;

import com.github.jamescarter.hexahop.core.level.Level;

import playn.core.util.Callback;

public class LevelLoadCallback implements Callback<String> {
	@Override
	public void onSuccess(String json) {
		Level level = new Level(json);
		level.load();
	}

	@Override
	public void onFailure(Throwable cause) {
		
	}
}
