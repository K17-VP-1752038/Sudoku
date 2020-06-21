package frame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import solve.sudoku;

public class Game extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel time_run;
	private JButton btn_new, btn_check, btn_submit, btn_restart;
	private JFormattedTextField[][] grid;
	private sudoku puzzle;
	private boolean changeColor;
	private static long second = 0;
	private Timer timer;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Game() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(610, 382);
		setLocationRelativeTo(null);
		setTitle("Game sudoku");
		setResizable(false);
		Image icon = Toolkit.getDefaultToolkit().getImage("img/sudoku.png");  
        setIconImage(icon);
		
		puzzle = new sudoku(9);
		changeColor = false;
		
		BufferedImage img;
		try {
			img = ImageIO.read(new File("img/background.jpg"));
			ImagePanel contentPane = new ImagePanel(img);
			contentPane.setLayout(new BorderLayout(10, 5));
			setContentPane(contentPane);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// tao o khung 3x3
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(9, 9));
		board.setPreferredSize(new Dimension(265, 265));
		board.setBackground(new Color(148, 83, 5));
		
		NumberFormat number = NumberFormat.getNumberInstance();
		int x, y;
		grid = new JFormattedTextField[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				x = 1;
				y = 1;
				if(i == 2 || i == 5) y = 2;
				if(j == 2 || j == 5) x = 2;
				
				grid[i][j] = new JFormattedTextField(number);
				grid[i][j].setDocument(new JFormattedTextFieldLimit(1));
				grid[i][j].setFont(new Font("Arial", Font.PLAIN, 14));
				grid[i][j].setHorizontalAlignment(JFormattedTextField.CENTER);
				grid[i][j].setBorder(BorderFactory.createMatteBorder(0, 0, y, x, new Color(148, 83, 5)));
				board.add(grid[i][j]);
				
				
//				grid[i][j].addKeyListener(new KeyAdapter() {
//				    public void keyPressed(KeyEvent evt) {
//				           if(evt.getKeyCode() == KeyEvent.VK_UP) {
//				        	   
//				           }
//				           if(evt.getKeyCode() == KeyEvent.VK_RIGHT) {
//				        	   grid[i][j + 1].requestFocus();
//				           }
//				       }
//				});
				grid[i][j].addMouseListener(new MouseAdapter() {
					   @Override
					    public void mousePressed(MouseEvent e) {
						   if(changeColor)
							   resetPuzzleFont();
					    }
				});
			}
		}
		
		// Fill the sudoku puzzle at begin of program
		puzzle.createPuzzle();
		setSudokuContent(puzzle.getGrid());
		puzzle.print();
		
		JPanel outer_board = new JPanel();
		outer_board.add(board);
		
		// Bo dem thoi gian
		JPanel time_panel = new JPanel();
		time_panel.setLayout(new BoxLayout(time_panel, BoxLayout.Y_AXIS));
		JLabel time_lbl = new JLabel("Time :");
		time_run = new JLabel("00:00:00");
		
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				second++;
				LocalTime now = LocalTime.of(0, 0, 0);
				//LocalTime now = LocalTime.parse("00:00:00", dtf);
				LocalTime now2 = now.plusSeconds(second);
				time_run.setText(now2.format(DateTimeFormatter.ISO_LOCAL_TIME));
			}
			
		});
		timer.setRepeats(true);
		timer.start();
		
		time_panel.add(Box.createRigidArea(new Dimension(60, 150)));
		time_panel.add(time_lbl);
		time_panel.add(time_run);
		
		// Panel cho cac button
		JPanel btn_panel = new JPanel();
		btn_panel.setLayout(new BoxLayout(btn_panel, BoxLayout.Y_AXIS));
		btn_panel.setBorder(new EmptyBorder(0,0,0,20));
		
		btn_new = new JButton("New game");
		Dimension btn_size = btn_new.getMaximumSize();
		btn_new.addActionListener(this);
		
		btn_check = new JButton("Check");
		btn_check.setMaximumSize(btn_size);
		btn_check.addActionListener(this);
		
		btn_restart = new JButton("Restart");
		btn_restart.setMaximumSize(btn_size);
		btn_restart.addActionListener(this);
		
		btn_submit = new JButton("Submit");
		btn_submit.setMaximumSize(btn_size);
		btn_submit.addActionListener(this);
		
		btn_panel.add(Box.createVerticalGlue());
		btn_panel.add(btn_new);
		btn_panel.add(Box.createRigidArea(new Dimension(0, 15)));
		btn_panel.add(btn_restart);
		btn_panel.add(Box.createRigidArea(new Dimension(0, 15)));
		btn_panel.add(btn_check);
		btn_panel.add(Box.createRigidArea(new Dimension(0, 15)));
		btn_panel.add(btn_submit);
		btn_panel.add(Box.createVerticalGlue());
		
		// Panel title
		JPanel tit_pan = new JPanel();
		tit_pan.setBorder(new EmptyBorder(8,0,0,0));;
		tit_pan.add(new JLabel("This is a SUDOKU"));
//		title.setFont(new Font("Arial", Font.BOLD, 13));
		
		//chinh opacity cho cac panel
		outer_board.setOpaque(false);
		time_panel.setOpaque(false);
		btn_panel.setOpaque(false);
		tit_pan.setOpaque(false);
		
		
		getContentPane().add(tit_pan, BorderLayout.NORTH);
		getContentPane().add(outer_board, BorderLayout.CENTER);
		getContentPane().add(time_panel, BorderLayout.WEST);
		getContentPane().add(btn_panel, BorderLayout.EAST);
	}
	
	// reset timer
	private void resetTime() {
		timer.stop();
		time_run.setText("00:00:00");
		second = 0;
		timer.restart();
	}
	
	// Reset the puzzle color
	private void resetPuzzleFont() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(grid[i][j].getBackground().equals(new Color(246, 178, 151)))
					grid[i][j].setBackground(Color.WHITE);
			}
		}
		changeColor = false;
	}
	
	// Fill the sudoku puzzle
	private void setSudokuContent(int[][] arr) {
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++) {
				grid[i][j].setText(null);
				grid[i][j].setBackground(Color.WHITE);
				grid[i][j].setEditable(true);
				grid[i][j].setEnabled(true);
				if(arr[i][j] > 0) {
					grid[i][j].setText(arr[i][j] + "");
					grid[i][j].setBackground(new Color(215, 215, 215));
					grid[i][j].setEnabled(false);
					grid[i][j].setDisabledTextColor(Color.BLACK);
				}
			}
	}
	
	private boolean checkSudoku() {
		int[][] sol = puzzle.getSolution();

		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++)
				if(grid[i][j].getText().isBlank() || (Integer.parseInt(grid[i][j].getText()) != sol[i][j]))
					return false;
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_new) {
			puzzle.createPuzzle();
			setSudokuContent(puzzle.getGrid());
			resetPuzzleFont();
			resetTime();
		}
		
		if(e.getSource() == btn_check) {
			int[][] sol = puzzle.getSolution();
			
			for(int i = 0; i < 9; i++)
				for(int j = 0; j < 9; j++) {
					if(grid[i][j].getBackground().equals(new Color(246, 178, 151)))
						grid[i][j].setBackground(Color.WHITE);
					
					if(grid[i][j].getText().isBlank() || (Integer.parseInt(grid[i][j].getText()) != sol[i][j])) {
						grid[i][j].setBackground(new Color(246, 178, 151));
					}
				}
			changeColor = true;
		}
		
		if(e.getSource() == btn_restart) {
			setSudokuContent(puzzle.getGrid());
			resetPuzzleFont();
			resetTime();
		}
		
		if(e.getSource() == btn_submit) {
			
			if(!checkSudoku()) {
				JOptionPane.showMessageDialog(null, "Sorry but the answer is not correct :( ");
				if(!changeColor) {
					int[][] sol = puzzle.getSolution();

					for(int i = 0; i < 9; i++)
						for(int j = 0; j < 9; j++)
							if(grid[i][j].getText().isBlank() || (Integer.parseInt(grid[i][j].getText()) != sol[i][j])) {
								grid[i][j].setBackground(new Color(246, 178, 151));
								grid[i][j].setEditable(false);
							}
				}
			}
			else {
				timer.stop();
				JOptionPane.showMessageDialog(null, "Conglatulation! You're a genious :D\n Your time is " + time_run);
			}
			
		}
	}
}
