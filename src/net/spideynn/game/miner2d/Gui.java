package net.spideynn.game.miner2d;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Gui
  implements GuiMethods, Serializable
{
  private static final long serialVersionUID = -4830994385942436334L;
  public List<InvenSlot> guis_s;
  public List<InvenLabel> guis_l;
  
  public Gui()
  {
    this.guis_s = new ArrayList();
    this.guis_l = new ArrayList();
  }
}
