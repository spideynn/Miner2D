package net.spideynn.miner2d.gui;

import java.util.*;
import java.io.Serializable;

import net.spideynn.miner2d.other.InvenLabel;
import net.spideynn.miner2d.other.InvenSlot;

public abstract class Gui implements GuiMethods, Serializable {

	private static final long serialVersionUID = -4830994385942436334L;
	public List<InvenSlot> guis_s;
	public List<InvenLabel> guis_l;

	public Gui() {
		this.guis_s = new ArrayList<InvenSlot>();
		this.guis_l = new ArrayList<InvenLabel>();
	}

}
