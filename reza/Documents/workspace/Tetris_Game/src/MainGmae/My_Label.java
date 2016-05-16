package MainGmae;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class My_Label extends JLabel {
	private String name_label;
	public My_Label(String name_label,int x_loc_but,int y_loc_but,int width ,int height) {
		super(name_label);
		this.name_label=name_label;
		setFont(new Font(getFont().getName(), getFont().getStyle(), 15));
		setSize(width, height);
		setLocation(x_loc_but, y_loc_but);
		setLayout(null);
		setVisible(true);
	}

}
