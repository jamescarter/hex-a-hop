package com.github.jamescarter.hexahop.core.screen;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;

import com.github.jamescarter.hexahop.core.Loadable;

public class TitleScreen implements Loadable {
	@Override
	public void load() {
		GroupLayer rootLayer = graphics().rootLayer();

		GroupLayer titleMenuLayer = graphics().createGroupLayer();

		Image bgImage = assets().getImage("images/gradient.png");
		Image titleImage = assets().getImage("images/title.png");;

		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		ImageLayer titleLayer = graphics().createImageLayer(titleImage);

		titleMenuLayer.add(bgLayer);
		titleMenuLayer.add(titleLayer);

		rootLayer.clear();
		rootLayer.add(titleMenuLayer);
	}
}
