package net.spideynn.miner2d;

import javax.swing.*;

import java.awt.event.*;
import java.awt.Toolkit;

public class GenerateWorldJFrame extends JFrame {

	private static final long serialVersionUID = 9097393072134360074L;
	public JTextField Width;
	public JTextField Height;
	public JButton Generate;

	public GenerateWorldJFrame() {
		setSize(320, 240);
		java.awt.Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(d.width / 2 - 320 / 2, d.height / 2 - 240 / 2);
		setLayout(null);
		setTitle("Generate New World");
		Width = new JTextField("256");
		Height = new JTextField("2048");
		Generate = new JButton("Generate!");
		Generate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MD.game.s.mapWidth = Integer.valueOf(Width.getText());
				MD.game.s.mapHeight = Integer.valueOf(Height.getText());
				MD.game.genWorldNow = true;
			}
		});
		Width.setLocation(2, 2);
		Width.setSize(128, 23);
		Height.setLocation(2, 30);
		Height.setSize(128, 23);
		Generate.setLocation(320 / 2 - 128 / 2, 100);
		Generate.setSize(128, 23);
		add(Width);
		add(Height);
		add(Generate);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
