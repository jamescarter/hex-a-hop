package com.github.jamescarter.hexahop.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.github.jamescarter.hexahop.core.HexAHop;

public class HexAHopActivity extends GameActivity {
	@Override
	public void main() {
		platform().assets().setPathPrefix("com/github/jamescarter/hexahop/resources");

		PlayN.run(new HexAHop(true));
	}

	@Override
	public void onBackPressed() {
		// allow keyboard listener to handle back button
	}
}
