package com.hfsgwt.client.componentes.suggest;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.hfsgwt.client.componentes.HFSItem;
import com.hfsgwt.client.componentes.util.HFSEquals;

public class HFSSugestao implements Suggestion {
	private HFSItem item;

	public HFSSugestao() {
		this.item = new HFSItem();
	}

	public HFSSugestao(HFSItem item) {
		this.item = item;
	}

	@Override
	public String getDisplayString() {
		return this.item.getValor();
	}

	@Override
	public String getReplacementString() {
		return this.item.getValor();
	}

	public HFSItem getItem() {
		return item;
	}

	public void setItem(HFSItem item) {
		this.item = item;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof HFSSugestao))
			return false;
		HFSSugestao objeto = (HFSSugestao) obj;
		return HFSEquals.areEqual(this.item, objeto.item)
				&& HFSEquals.areEqual(this.getDisplayString(), objeto
						.getDisplayString())
				&& HFSEquals.areEqual(this.getReplacementString(), objeto
						.getReplacementString());
	}

}
