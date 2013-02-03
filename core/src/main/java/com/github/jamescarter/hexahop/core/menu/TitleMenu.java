package com.github.jamescarter.hexahop.core.menu;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;

import com.github.jamescarter.hexahop.core.Loadable;

public class TitleMenu implements Loadable {
	private ImageLayer titleLayer;

	@Override
	public void load() {
		GroupLayer rootLayer = graphics().rootLayer();

		rootLayer.clear();

		Image bgImage = assets().getImage("images/gradient.png");
		Image titleImage = assets().getImage("images/title.png");;

		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		titleLayer = graphics().createImageLayer(titleImage);

		rootLayer.add(bgLayer);
		rootLayer.add(titleLayer);
	}
}
