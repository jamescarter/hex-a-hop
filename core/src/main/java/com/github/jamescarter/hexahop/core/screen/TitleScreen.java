package com.github.jamescarter.hexahop.core.screen;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Pointer;
import playn.core.TextFormat;
import playn.core.TextLayout;

import com.github.jamescarter.hexahop.core.Loadable;

public class TitleScreen implements Loadable {
	private static final ImageLayer bgLayer = graphics().createImageLayer(assets().getImage("images/gradient.png"));
	private static final ImageLayer titleLayer = graphics().createImageLayer(assets().getImage("images/title.png"));

	@Override
	public void load() {
		GroupLayer rootLayer = graphics().rootLayer();
		GroupLayer titleMenuLayer = graphics().createGroupLayer();

		titleMenuLayer.add(bgLayer);
		titleMenuLayer.add(titleLayer);

		rootLayer.clear();
		rootLayer.add(titleMenuLayer);

		addTitleMenuOptions(titleMenuLayer);
	}

	private void addTitleMenuOptions(GroupLayer titleMenuLayer) {
		Font font = graphics().createFont("Helvetica", Font.Style.PLAIN, 22);

		TextFormat format = new TextFormat().withFont(font);
		TextLayout newGameLayout = graphics().layoutText("New Game", format);
		Layer newGameLayer = createTextLayer(newGameLayout, 0xFFFFFFFF);

		newGameLayer.setTranslation(380, 200);

		newGameLayer.addListener(new Pointer.Adapter() {
			@Override
			public void onPointerEnd(Pointer.Event event) {
				new MapScreen().load();
			}
		});

		titleMenuLayer.add(newGameLayer);
	}

	private Layer createTextLayer(TextLayout layout, int color) {
		CanvasImage image = graphics().createImage(
			(int) Math.ceil(layout.width()),
			(int) Math.ceil(layout.height())
		);

		image.canvas().setFillColor(color);
		image.canvas().fillText(layout, 0, 0);

		return graphics().createImageLayer(image);
	}
}
