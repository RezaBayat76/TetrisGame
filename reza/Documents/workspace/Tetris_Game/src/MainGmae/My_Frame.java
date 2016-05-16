package MainGmae;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class My_Frame extends JFrame implements ActionListener, KeyListener {
	private Dimension dim_screen;
	private My_Panel my_panel;
	private int size_cell = 20;
	private My_Button save_button, load_button;
	private My_Label score_label, level_label, count_score_label, count_level_label;

	public My_Frame() {
		dim_screen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(getSize_cell() * 15, getSize_cell() * 31 + 10);
		setLocation(((int) dim_screen.getWidth() / 8) * 3 +25, ((int) dim_screen.getHeight() / 8) * 2 - 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Tetris");
		my_panel = new My_Panel(dim_screen, this);
		(new Thread(my_panel)).start();
		this.getContentPane().add(my_panel);
		addKeyListener(this);
		setFocusable(true);
		
		//setResizable(false);
		setButtons();
		setLabels();
		setLayout(null);
		setVisible(true);

	}

	///// setButtons \\\\\
	private void setButtons() {
		save_button = new My_Button("SAVE", getSize_cell() * 12 - 5, getSize_cell() * 28 + 2, getSize_cell() * 3,
				getSize_cell());
		save_button.addActionListener(this);
		save_button.setActionCommand("SAVE");

		getContentPane().add(save_button);
		load_button = new My_Button("LOAD", getSize_cell() * 12 - 5, getSize_cell() * 29 + 5, getSize_cell() * 3,
				getSize_cell());
		load_button.addActionListener(this);
		load_button.setActionCommand("LOAD");
		getContentPane().add(load_button);

	}

	///// setLabels \\\\\
	private void setLabels() {
		level_label = new My_Label(" Level : ", 5, getSize_cell() * 28 + 2, 60, 15);
		getContentPane().add(level_label);
		score_label = new My_Label(" Score : ", 5, getSize_cell() * 29 + 5, 60, 15);
		getContentPane().add(score_label);
		count_level_label = new My_Label(Integer.toString(my_panel.getLevel()), 65, getSize_cell() * 28 + 2, 60, 15);
		getContentPane().add(count_level_label);
		count_score_label = new My_Label(Integer.toString(my_panel.getScore()), 65, getSize_cell() * 29 + 5, 60, 15);
		getContentPane().add(count_score_label);

	}

	public void updateLabels() {
		count_level_label.setText(Integer.toString(my_panel.getLevel()));
		count_score_label.setText(Integer.toString(my_panel.getScore()));
	}

	@Override
	protected void processEvent(AWTEvent arg0) {
		// System.out.println(arg0.getID());
		super.processEvent(arg0);

	}

	public int getSize_cell() {
		return size_cell;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command;
		command = e.getActionCommand();
		if ("SAVE".equals(command)) {
			my_panel.saveMapBoard();
			setFocusable(true);
		} else if ("LOAD".equals(command)) {
			my_panel.loadMapBoard();
			setFocusable(true);

		}
		//etFocusable(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		my_panel.keyTyped(e);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		my_panel.keyPressed(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		my_panel.keyReleased(e);
		
	}

}
