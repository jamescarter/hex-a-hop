package com.github.jamescarter.hexahop.core.screen;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.TextFormat;
import playn.core.TextLayout;
import tripleplay.game.Screen;
import com.github.jamescarter.hexahop.core.callback.MapLoadCallback;

public class TitleScreen extends Screen {
	private static final ImageLayer bgLayer = graphics().createImageLayer(assets().getImage("images/gradient.png"));
	private static final ImageLayer titleLayer = graphics().createImageLayer(assets().getImage("images/title.png"));

	@Override
	public void wasAdded() {
		GroupLayer titleMenuLayer = graphics().createGroupLayer();

		titleMenuLayer.add(bgLayer);
		titleMenuLayer.add(titleLayer);

		layer.add(titleMenuLayer);

		addTitleMenuOptions(titleMenuLayer);
	}

	@Override
	public void wasShown() {
		PlayN.keyboard().setListener(null);
	}

	private void addTitleMenuOptions(GroupLayer titleMenuLayer) {
		Font font = graphics().createFont("Helvetica", Font.Style.PLAIN, 22);

		TextFormat format = new TextFormat().withFont(font);
		TextLayout newGameLayout = graphics().layoutText("Play", format);
		Layer newGameLayer = createTextLayer(newGameLayout, 0xFFFFFFFF);

		newGameLayer.setTranslation(420, 200);

		bgLayer.addListener(new Pointer.Adapter() {
			@Override
			public void onPointerEnd(Pointer.Event event) {
				assets().getText("levels/map.json", new MapLoadCallback());
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
