package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSLabelHash extends Composite {

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	public enum TipoDigest {
		MD2, MD5, SHA1, SHA256, SHA384, SHA512
	}

	private Label labHash;

	public HFSLabelHash() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getLabHash());
	}

	private Label getLabHash() {
		if (labHash == null) {
			labHash = new Label();
			labHash.setWordWrap(true);
			// labHash.setWidth(largura+"px");
			labHash.setTitle("Gera Hash");
			labHash.setText("Gera Hash");
			labHash.setStyleName("HFSLabelHash");
		}
		return labHash;
	}

	private String getTipoDigest(TipoDigest tipo) {
		String retorno = "";
		switch (tipo) {
		case MD2:
			retorno = "MD2";
			break;
		case MD5:
			retorno = "MD5";
			break;
		case SHA1:
			retorno = "SHA-1";
			break;
		case SHA256:
			retorno = "SHA-256";
			break;
		case SHA384:
			retorno = "SHA-384";
			break;
		case SHA512:
			retorno = "SHA-512";
			break;
		}
		return retorno;
	}

	public void getHash(TipoDigest tipo, String senha) {
		servico.getHash(getTipoDigest(tipo), senha,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						HFSUtil.mostrarFalha(caught, this.getClass(), "getHash");
					}

					public void onSuccess(String sHash) {
						getLabHash().setText(sHash);
					}
				});
	}

	public String getText() {
		return getLabHash().getText();
	}

}
