package GameObjects;

import java.security.GeneralSecurityException;

import javax.swing.ImageIcon;

import MainGmae.My_Panel;

public class Bomb implements Runnable {
	private My_Panel my_panel;
	private int x_bomb;
	private int y_board;
	private int x_bomb_inboard;
	private int y_bomb_inboard;
	private boolean islive = true;
	private int speed_bomb;
	private ImageIcon img_bomb;

	public Bomb(int x_bomb_inboard, int y_bomb_inboard, My_Panel my_panel, int speed_bomb) {
		this.x_bomb_inboard = x_bomb_inboard;
		this.y_bomb_inboard = y_bomb_inboard;
		this.my_panel = my_panel;
		this.speed_bomb = speed_bomb;
		img_bomb = new ImageIcon("15_Bomb_2.png");
	}
	@Override
	public void run() {
		while (islive) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			goDown(speed_bomb);
		}
		my_panel.repaint();
	}

	public void goDown(int count) {
		int y = y_bomb_inboard;
		int x = x_bomb_inboard;
		int[][] board = my_panel.getMap_board();
		board[y][x] = 0;
		if (y < 26) {
			this.y_bomb_inboard++;
		}
		else {
			islive = false;
		}

		my_panel.setMap_board(board);
	}
	
	public int getX_bomb_inboard() {
		return x_bomb_inboard;
	}

	public int getY_bomb_inboard() {
		return y_bomb_inboard;
	}

	public ImageIcon getImg_bomb() {
		return img_bomb;
	}

	public boolean getIsLive() {
		return islive;
	}
}
