package net.spideynn.game.miner2d;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class GenerateWorldJFrame
  extends JFrame
{
  private static final long serialVersionUID = 9097393072134360074L;
  public JTextField Width;
  public JTextField Height;
  public JButton Generate;
  
  public GenerateWorldJFrame()
  {
    setSize(320, 240);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(d.width / 2 - 160, d.height / 2 - 120);
    setLayout(null);
    setTitle("Generate New World");
    this.Width = new JTextField("256");
    this.Height = new JTextField("2048");
    this.Generate = new JButton("Generate!");
    this.Generate.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        MD.game.s.mapWidth = Integer.valueOf(GenerateWorldJFrame.this.Width.getText()).intValue();
        MD.game.s.mapHeight = Integer.valueOf(GenerateWorldJFrame.this.Height.getText()).intValue();
        MD.game.genWorldNow = true;
      }
    });
    this.Width.setLocation(2, 2);
    this.Width.setSize(128, 23);
    this.Height.setLocation(2, 30);
    this.Height.setSize(128, 23);
    this.Generate.setLocation(96, 100);
    this.Generate.setSize(128, 23);
    add(this.Width);
    add(this.Height);
    add(this.Generate);
    setDefaultCloseOperation(2);
  }
}
