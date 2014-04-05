package net.spideynn.miner2d;

import java.util.*;

import java.io.Serializable;

public abstract class Gui implements GuiMethods, Serializable {

	private static final long serialVersionUID = -4830994385942436334L;
	public List<InvenSlot> guis_s;
	public List<InvenLabel> guis_l;

	public Gui() {
		this.guis_s = new ArrayList<InvenSlot>();
		this.guis_l = new ArrayList<InvenLabel>();
	}

}
