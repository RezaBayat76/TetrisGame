package MainGmae;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GameObjects.Bomb;
import GameObjects.Shapes;

public class My_Panel extends JPanel implements Runnable {
	private Dimension dim_screen;
	private ImageIcon[] all_pic;
	private int score = 0;
	private int level = 5 ;
	private int go_right = 20;
	private int go_left = 20;
	private int speed_down;
	private int size_cell = 20;
	private int[][] map_board = new int[28][15];
	private int[][] save_map_board;
	private boolean issaved = false;
	private boolean can_rotate_map = true;
	private boolean can_set_bomb = true;
	private Vector<Shapes> vec_shapes = new Vector<Shapes>();
	private Bomb bomb;
	private Vector<Bomb> vec_bombs = new Vector<Bomb>();
	private byte[] lineOfFile;
	private Shapes shape = new Shapes(getSize_cell(), getSize_cell(), 200, 3, 6, this, map_board, 10);
	private My_Label count_level_label, count_score_label;
	private My_Frame my_frame;
	private int save_level;
	private int save_score;
	private boolean can_go_r_l = true;
	private boolean can_go_d = true;

	public My_Panel(Dimension dim_screen, My_Frame my_frame) {
		this.my_frame = my_frame;
		this.dim_screen = dim_screen;
		setMapBoardFirst();
		setAllPic();
		setSize(getSize_cell() * 18, getSize_cell() * 28);
		setLocation(0, 0);
		setFocusable(true);

		setLayout(null);
		setVisible(true);
	}

	@Override
	public void run() {
		while (true) {
			chooseLevel(level);
		}
	}

	///// startBomb \\\\\
	private void startBomb() {
		Random random = new Random();
		bomb = new Bomb(random.nextInt(13) + 1, 1, this, getSize_cell());
		vec_bombs.add(bomb);
		(new Thread(bomb)).start();
		while (bomb.getIsLive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		vec_bombs.remove(0);
	}

	///// startMapRotation \\\\\
	private void startMapRotation() {

		(new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					// && can_go_d==true
					if (can_go_r_l == true && can_go_d == true) {
						rotateMapBoard();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		})).start();
	}

	///// setAllPic \\\\\
	private void setAllPic() {
		all_pic = new ImageIcon[12];
		all_pic[0] = new ImageIcon("12_Block.png");
		all_pic[1] = new ImageIcon("01_Violet.png");
		all_pic[2] = new ImageIcon("02_Yellow.png");
		all_pic[3] = new ImageIcon("03_Brown.png");
		all_pic[4] = new ImageIcon("04_Orange.png");
		all_pic[5] = new ImageIcon("05_Pink.png");
		all_pic[6] = new ImageIcon("06_Red.png");
		all_pic[7] = new ImageIcon("07_Blue.png");
		all_pic[8] = new ImageIcon("08_Dark_brown.png");
		all_pic[9] = new ImageIcon("09_Dark_yellow.png");
		all_pic[10] = new ImageIcon("10_Lightـyellow.png");
		all_pic[11] = new ImageIcon("14_Block_2.png");
	}

	///// setMapBoardFirst \\\\\
	private void setMapBoardFirst() {
		for (int i = 0; i < 28; i++) {
			for (int j = 0; j < 15; j++) {
				map_board[i][j] = 0;
			}
		}
		for (int i = 0; i < 15; i++) {
			map_board[0][i] = 11;
			map_board[27][i] = 11;
		}
		for (int i = 0; i < 28; i++) {
			map_board[i][0] = 11;
			map_board[i][14] = 11;
		}
	}

	///// paint \\\\\
	@Override
	public void paint(Graphics g) {
		// super.paint(g);
		for (int i = 0; i < 28; i++) {
			for (int j = 0; j < 15; j++) {
				g.drawImage(all_pic[map_board[i][j]].getImage(), j * getSize_cell(), i * getSize_cell(), getSize_cell(),
						getSize_cell(), null);
				// repaint();
			}
		}
		for (Shapes sh : vec_shapes) {
			int[][] shape = sh.getMap_shape();
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (shape[i][j] == 1) {
						g.drawImage(sh.getShape_pic().getImage(),
								sh.getX_shape_inboard() * getSize_cell() + j * getSize_cell(),
								sh.getY_shape_inboard() * getSize_cell() + i * getSize_cell(), getSize_cell(),
								getSize_cell(), null);
					}
				}
			}
		}
		for (Bomb b : vec_bombs) {
			g.drawImage(b.getImg_bomb().getImage(), b.getX_bomb_inboard() * getSize_cell(),
					b.getY_bomb_inboard() * getSize_cell(), getSize_cell(), getSize_cell(), null);
		}
		my_frame.updateLabels();
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}

	///// chooseLevel \\\\\
	private void chooseLevel(int level) {
		Random random = new Random();
		int choice = random.nextInt(10) + 1;
		if (can_rotate_map == true && level > 2) {
			startMapRotation();
		}
		if (choice <= 8 && level >= 5) {
			shape = new Shapes(getSize_cell(), getSize_cell(), 350 - (40 * (level - 1)), random.nextInt(10) + 2, 1,
					this, map_board, random.nextInt(level + 3) + 1);
			vec_shapes.add(shape);
			(new Thread(shape)).start();
			while (!shape.isfixted) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			vec_shapes.remove(0);
			checkMapBoard();
			checkGameOver();
		} else if (level <= 4) {
			shape = new Shapes(getSize_cell(), getSize_cell(), 350 - (40 * (level - 1)), random.nextInt(10) + 2, 1,
					this, map_board, random.nextInt(level + 3) + 1);
			vec_shapes.add(shape);
			(new Thread(shape)).start();
			while (!shape.isfixted) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			vec_shapes.remove(0);
			checkMapBoard();
			checkGameOver();
		} else if (choice >= 9 && level >= 5) {
			startBomb();
		}
	}

	///// checkMapBoard \\\\\
	private void checkMapBoard() {
		boolean iswin = true;
		for (int i = 1; i < 27; i++) {
			for (int j = 1; j < 14; j++) {
				if (map_board[i][j] == 0) {
					iswin = false;
				}
			}
			if (iswin) {
				changeMapBoard(i);
				score += 100;
				if (score == 500) {
					level++;
					score = 0;
				}
			}
			iswin = true;
		}
	}

	///// changeMapBoard \\\\\
	private void changeMapBoard(int row) {
		int[][] temp = new int[28][15];
		for (int i = 0; i < row; i++) {
			temp[i + 1] = map_board[i];
		}
		for (int i = row + 1; i < 28; i++) {
			temp[i] = map_board[i];
		}
		for (int i = 0; i < 15; i++) {
			temp[0][i] = 11;
		}
		for (int i = 0; i < 15; i++) {
			temp[1][i] = 0;
		}
		temp[1][0] = 11;
		temp[1][14] = 11;
		map_board = temp;

	}

	///// saveMapBoard \\\\\
	public void saveMapBoard() {
		save_map_board = new int[28][15];
		save_level = level;
		save_score = score;
		for (int i = 0; i < 28; i++) {
			for (int j = 0; j < 15; j++) {
				save_map_board[i][j] = map_board[i][j];
			}
		}
		issaved = true;
	}

	///// loadMapBoard \\\\\
	public void loadMapBoard() {
		level = save_level;
		score = save_score;
		if (issaved == true) {

			System.out.println("is loaded");
			for (int i = 0; i < 28; i++) {
				for (int j = 0; j < 15; j++) {
					map_board[i][j] = save_map_board[i][j];
				}
			}
			// this.repaint();
		}
	}

	///// writeToFile \\\\\
	private void writeToFile() {
		BufferedWriter output = null;
		try {
			File file = new File("mapBoard.txt");
			output = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < 28; i++) {
				for (int j = 0; j < 15; j++) {
					output.write(Integer.toString(map_board[i][j]));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	///// rotateMapBoard \\\\\
	private void rotateMapBoard() {
		can_rotate_map = false;
		int[][] temp_total = new int[28][15];
		for (int i = 0; i < 28; i++) {
			temp_total[i][13] = map_board[i][1];
			temp_total[i][0] = 11;
			temp_total[i][14] = 11;
		}
		for (int i = 1; i < 13; i++) {
			for (int j = 0; j < 28; j++) {
				temp_total[j][i] = map_board[j][i + 1];
			}
		}
		if (!vec_shapes.isEmpty()) {
			checkOverlap();
		}

		map_board = temp_total;
	}

	/////////// bayad taghir konad
	private void checkOverlap() {
		for (int i = 1; i < 27; i++) {
			for (int j = 1; j < 14; j++) {// must be change
				if (map_board[i][j] != 0) {
					transferShape(i, j);
					// break;
				}
			}
		}

	}

	private void transferShape(int i, int j) {
		boolean checkRow = false;
		int x = vec_shapes.get(0).getX_shape_inboard();
		int y = vec_shapes.get(0).getY_shape_inboard();
		int[][] temp = vec_shapes.get(0).getMap_shape();
		int tempForK = 0;
		for (int k = 0; k < 4; k++) {
			if (y + k == i) {
				tempForK = k;
				checkRow = true;
				// break;
			}
		}
		if (checkRow == true) {
			for (int h = 0; h < 4; h++) {
				if (temp[tempForK][3 - h] == 1 && x + h == j ) {// changed
					// for(int z=0;z<4;z++){
					// if(temp[0][z]==1){
					// vec_shapes.get(0).setIsfixted(false);
					// }
					// }
					if (x == 2) {// changed
						vec_shapes.get(0).setX_shape_inboard(x - 1);
						vec_shapes.get(0).setIsfixted(true);
					} else if (x > 2) {
						vec_shapes.get(0).setX_shape_inboard(x - 1);
						break;
					}
				}
			}
		}
	}

	private void checkGameOver() {
		boolean isGameOver = false;
		for (int i = 1; i < 14; i++) {
			if (map_board[1][i] != 0) {
				isGameOver = true;
			}
		}
		if (isGameOver == true) {
			JOptionPane.showMessageDialog(null, "GMAE OVER", "GMAE OVER", 0);

//			Thread.currentThread().destroy();
			System.exit(0);

		}
	}

	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			can_go_r_l = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			can_go_r_l = false;
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			shape.goRight2(go_right);
			// can_go_r_l = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			shape.goLeft2(go_left);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			shape.rotate();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			shape.fastDown();
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			can_go_r_l = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			can_go_r_l = true;
		}
	}

	public void setMap_board(int[][] map_board) {
		this.map_board = map_board;
	}

	public int[][] getMap_board() {
		return map_board;
	}

	public int getSize_cell() {
		return size_cell;
	}

	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

	public boolean getCan_go_d() {
		return can_go_d;
	}

	public void setCan_go_d(boolean b) {
		can_go_d = b;
	}

}
