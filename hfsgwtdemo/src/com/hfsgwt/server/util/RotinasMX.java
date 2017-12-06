package com.hfsgwt.server.util;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.hfsgwt.client.componentes.menuxml.MXEstrutura;
import com.hfsgwt.client.componentes.menuxml.MXItem;
import com.hfsgwt.client.componentes.menuxml.MXMenu;
import com.hfsgwt.client.componentes.menuxml.MXSistema;
import com.hfsgwt.server.xml.menuxml.ItemTagList;
import com.hfsgwt.server.xml.menuxml.MenuTagList;
import com.hfsgwt.server.xml.menuxml.MenuXmlObject;
import com.hfsgwt.server.xml.menuxml.SistemaTagList;

public final class RotinasMX {
	private static Logger log = Logger.getLogger(RotinasMX.class);
	private static RotinasMX instancia;

	private RotinasMX() {
		super();
	}

	public static RotinasMX getInstancia() {
		if (instancia == null) {
			instancia = new RotinasMX();
		}
		return instancia;
	}

	public MXEstrutura carregaMX(String arqMenuXML, String codigoSistema) {
		MXEstrutura mx = new MXEstrutura();

		try {
			StringList sl = Rotinas.lerArquivo2(arqMenuXML);
			MenuXmlObject mxo = new MenuXmlObject();
			MenuXmlObject mxo2 = (MenuXmlObject) mxo.fromXML(sl.getText());

			ItemTagList itemtl;
			MenuTagList menutl;
			SistemaTagList sistematl;

			MXSistema[] sistemas = new MXSistema[mxo2.getSistemas().length];
			for (int i = 0; i < mxo2.getSistemas().length; i++) {

				MXMenu[] menus = new MXMenu[mxo2.getSistemas()[i].getMenus().length];
				for (int j = 0; j < mxo2.getSistemas()[i].getMenus().length; j++) {

					MXItem[] items = new MXItem[mxo2.getSistemas()[i]
							.getMenus()[j].getItems().length];
					for (int k = 0; k < mxo2.getSistemas()[i].getMenus()[j]
							.getItems().length; k++) {
						itemtl = mxo2.getSistemas()[i].getMenus()[j].getItems()[k];
						items[k] = new MXItem(itemtl.getOrdem(), itemtl
								.getCodigo(), itemtl.getLabel(), itemtl
								.getLink());
					}
					menutl = mxo2.getSistemas()[i].getMenus()[j];
					menus[j] = new MXMenu(menutl.getOrdem(),
							menutl.getCodigo(), menutl.getLabel(), items);
				}
				sistematl = mxo2.getSistemas()[i];
				sistemas[i] = new MXSistema(sistematl.getOrdem(), sistematl
						.getCodigo(), sistematl.getLabel(), menus);
			}
			mx.setSistemas(sistemas);

		} catch (IOException e) {
			log.error("Erro de entrada/saída ao ler arquivo: " + arqMenuXML, e);
		}
		return mx;
	}
}
