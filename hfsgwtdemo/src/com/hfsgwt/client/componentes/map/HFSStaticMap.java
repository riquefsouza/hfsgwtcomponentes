package com.hfsgwt.client.componentes.map;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

public class HFSStaticMap extends Composite {
	private Image image;

	public HFSStaticMap() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getImage());
	}

	private Image getImage() {
		if (image == null) {
			image = new Image();
		}
		return image;
	}

	public void mostrarMapa(int largura, int altura, double latitude,
			double longitude, int zoom) {
		String url = "http://maps.google.com/staticmap?" + "center=" + latitude
				+ "," + longitude + "&zoom=" + zoom + "&size=" + largura + "x"
				+ altura + "&maptype=mobile\\&key=MAPS_API_KEY&sensor=false";
		this.getImage().setSize(largura + "px", altura + "px");
		this.getImage().setUrl(url);
	}

}
