package MainGmae;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class My_Button extends JButton {
	private int x_loc_but;
	private int y_loc_but;
	private int width;
	private int height;
	private String name_button;

	public My_Button(String name_button, int x_loc_but, int y_loc_but, int width, int height) {
		super(name_button);
		this.name_button = name_button;
		this.x_loc_but = x_loc_but;
		this.y_loc_but = y_loc_but;
		this.width = width;
		this.height = height;
		setFocusable(false);
		setSize(width, height);
		setLocation(x_loc_but, y_loc_but);
		this.setLayout(null);
		setVisible(true);
	}

	///// paint \\\\\
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawString(name_button, x_loc_but, y_loc_but);

	}

}
