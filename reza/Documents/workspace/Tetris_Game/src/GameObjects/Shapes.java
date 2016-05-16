package GameObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;

import MainGmae.My_Panel;

public class Shapes implements Runnable {
	private int x_shape;
	private int y_shape;
	private int old_y_shape = 0;
	private int x_shape_inboard;
	private int y_shape_inboard;
	private int speed_shape;
	private My_Panel my_panel;
	private int value_shape;
	protected ImageIcon shape_pic;
	private int[][] map_board;
	private int[][] map_shape = new int[4][4];
	private File file_shape;
	public boolean isfixted = false;

	public Shapes(int x_shape, int y_shape, int speed_shape, int x_shape_inboard, int y_shape_inboard,
			My_Panel my_panel, int[][] map_board, int value_shape) {
		this.x_shape_inboard = x_shape_inboard;
		this.y_shape_inboard = y_shape_inboard;
		this.x_shape = x_shape;
		this.y_shape = y_shape;
		this.speed_shape = speed_shape;
		this.my_panel = my_panel;
		this.map_board = map_board;
		this.value_shape = value_shape;
		setMap(value_shape);
	}

	@Override
	public void run() {

		while (!isfixted) {
			try {
				// my_panel.setCan_go_d(true);
				Thread.sleep(speed_shape);
				// my_panel.setCan_go_d(false);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			goDown(speed_shape);toString();
			//my_panel.repaint();
		}
	}

	///// setMap \\\\
	private void setMap(int value) {
		switch (value) {
		case 1:// J
			readFile("Shape_J.txt");
			shape_pic = new ImageIcon("01_Violet.png");
			break;

		case 2:// L_Long
			readFile("Shape_L_Long.txt");
			shape_pic = new ImageIcon("02_Yellow.png");
			break;

		case 3:// L
			readFile("Shape_L.txt");
			shape_pic = new ImageIcon("03_Brown.png");
			break;

		case 4:// line
			readFile("Shape_Line.txt");
			shape_pic = new ImageIcon("04_Orange.png");
			break;

		case 5:// N
			readFile("Shape_N.txt");
			shape_pic = new ImageIcon("05_Pink.png");
			break;

		case 6:// S
			readFile("Shape_S.txt");
			shape_pic = new ImageIcon("06_Red.png");
			break;

		case 7:// Square
			readFile("Shape_Square.txt");
			shape_pic = new ImageIcon("07_Blue.png");
			break;

		case 8:// T
			readFile("Shape_T.txt");
			shape_pic = new ImageIcon("08_Dark_brown.png");
			break;

		case 9:// Y
			readFile("Shape_Y.txt");
			shape_pic = new ImageIcon("09_Dark_yellow.png");
			break;

		case 10:// Z
			readFile("Shape_Z.txt");
			shape_pic = new ImageIcon("10_Lightـyellow.png");
			break;

		default:
			break;
		}
	}

	///// readFile \\\\\
	public void readFile(String name_file) {
		FileInputStream file_input = null;
		file_shape = new File(name_file);
		try {
			file_input = new FileInputStream(file_shape);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		byte[] lineOfFile = new byte[5];
		for (int i = 0; i < 4; i++) {
			try {
				file_input.read(lineOfFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int j = 0; j < 4; j++) {
				map_shape[i][j] = lineOfFile[j] - 48;
			}
		}
	}

	///// gRright2 \\\\\
	public void goRight2(int count) {
		int[][] board = my_panel.getMap_board();
		int x = getX_shape_inboard();
		int y = getY_shape_inboard();
		boolean row1 = true, row2 = true, row3 = true, row4 = true;
		for (int i = 0; i < 4; i++) {
			if (map_shape[0][3 - i] == 1) {
				if (x + 4 - i < 15) {
					if (board[y][x + 4 - i] == 0) {
						row1 = true;
					} else {
						row1 = false;
					}
				}
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (map_shape[1][3 - i] == 1) {
				if (x + 4 - i < 15) {
					if (board[y + 1][x + 4 - i] == 0) {
						row2 = true;
					} else {
						row2 = false;
					}
				}
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (map_shape[2][3 - i] == 1) {
				if (x + 4 - i < 15) {
					if (board[y + 2][x + 4 - i] == 0) {
						row3 = true;
					} else {
						row3 = false;
					}
				}
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (map_shape[3][3 - i] == 1) {
				if (x + 4 - i < 15) {
					if (board[y + 3][x + 4 - i] == 0) {
						row4 = true;
					} else {
						row4 = false;
					}
				}
				break;
			}
		}
		if (row1 == true && row2 == true && row3 == true && row4 == true) {
			this.x_shape += count;
			this.x_shape_inboard++;
		}
	}

	///// goLeft2 \\\\\
	public void goLeft2(int count) {
		int[][] board = my_panel.getMap_board();
		int x = getX_shape_inboard();
		int y = getY_shape_inboard();
		boolean row1 = true, row2 = true, row3 = true, row4 = true;

		for (int i = 0; i < 4; i++) {
			if (map_shape[0][i] == 1) {
				if (x + i - 1 >= 0) {
					if (board[y][x + i - 1] == 0) {
						row1 = true;
					} else {
						row1 = false;
					}
				}
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (map_shape[1][i] == 1) {
				if (x + i - 1 >= 0) {
					if (board[y + 1][x + i - 1] == 0) {
						row2 = true;
					} else {
						row2 = false;
					}
				}
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (map_shape[2][i] == 1) {
				if (x + i - 1 >= 0) {
					if (board[y + 2][x + i - 1] == 0) {
						row3 = true;
					} else {
						row3 = false;
					}
				}
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (map_shape[3][i] == 1) {
				if (x + i - 1 >= 0) {
					if (board[y + 3][x + i - 1] == 0) {
						row4 = true;
					} else {
						row4 = false;
					}
				}
				break;
			}
		}
		if (row1 == true && row2 == true && row3 == true && row4 == true) {
			this.x_shape -= count;
			this.x_shape_inboard--;
		}
	}

	///// goDown \\\\\
	public void goDown(int count) {
		int[][] board = my_panel.getMap_board();
		int x = getX_shape_inboard();
		int y = getY_shape_inboard();
		boolean column1 = true, column2 = true, column3 = true, column4 = true;
		for (int i = 0; i < 4; i++) {
			if (map_shape[3 - i][0] == 1) {
				if (y + 4 - i < 28) {
					if (board[y + 4 - i][x] == 0) {
						column1 = true;
					} else {
						column1 = false;
					}
				}
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (map_shape[3 - i][1] == 1) {
				if (y + 4 - i < 28) {
					if (board[y + 4 - i][x + 1] == 0) {
						column2 = true;
					} else {
						column2 = false;
					}
				}
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (map_shape[3 - i][2] == 1) {
				if (y + 4 - i < 28) {
					if (board[y + 4 - i][x + 2] == 0) {
						column3 = true;
					} else {
						column3 = false;
					}
				}
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (map_shape[3 - i][3] == 1) {
				if (y + 4 - i < 28) {
					if (board[y + 4 - i][x + 3] == 0) {
						column4 = true;
					} else {
						column4 = false;
					}
				}
				break;
			}
		}
		if (column1 == true && column2 == true && column3 == true && column4 == true) {
			this.y_shape += count;
			this.y_shape_inboard++;

		}
		if (this.y_shape_inboard == old_y_shape) {
			isfixted = true;

		}
		old_y_shape = this.y_shape_inboard;
		changeBoard();
	}

	public void fastDown() {
		speed_shape = 10;
	}

	///// changeBoard \\\\\
	private void changeBoard() {
		int[][] board = my_panel.getMap_board();
		int x = this.x_shape_inboard;
		int y = this.y_shape_inboard;
		if (isfixted) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (map_shape[i][j] == 1) {
						board[y + i][x + j] = this.value_shape;
					}
				}
			}
			my_panel.setMap_board(board);
		}
	}

	///// rotate \\\\\
	public void rotate() {
		int x = this.x_shape_inboard;
		int y = this.y_shape_inboard;
		boolean can_rotate = true;
		int[][] board = my_panel.getMap_board();
		int[][] temp = new int[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				temp[j][3 - i] = map_shape[i][j];
			}
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (temp[i][j] == 1) {
					if (y+i<28 &&(  x + j >= 15 || x + j < 0 || board[y + i][x + j] != 0)) {
						can_rotate = false;
					}
				}
			}
		}
		if (can_rotate) {
			map_shape = temp;
		}
	}

	public void setX_shape(int x_shape) {
		this.x_shape += x_shape;
	}

	public int getX_shape() {
		return x_shape;
	}

	public void setY_shape(int y_shape) {
		this.y_shape += y_shape;

	}

	public int getY_shape() {
		return y_shape;
	}

	public void setX_shape_inboard(int x_shape_inboard) {
		this.x_shape_inboard = x_shape_inboard;
	}

	public int getX_shape_inboard() {
		return x_shape_inboard;
	}

	public void setY_shape_inboard(int y_shape_inboard) {
		this.y_shape_inboard += y_shape_inboard;
	}

	public int getY_shape_inboard() {
		return y_shape_inboard;
	}

	public int[][] getMap_shape() {
		return map_shape;
	}

	public ImageIcon getShape_pic() {
		return shape_pic;
	}

	// isfixted
	public void setIsfixted(boolean b) {
		isfixted = b;

	}

}
