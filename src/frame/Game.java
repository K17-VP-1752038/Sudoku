package frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalTime;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import solve.sudoku;

public class Game extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JLabel time_run;
	private JButton btn_new, btn_check, btn_submit, btn_restart;
	private JFormattedTextField[][] grid;
	private sudoku puzzle;
	private static long second = 0;
	
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
		setBounds(100, 100, 590, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("Game sudoku");
		setResizable(false);
		
		puzzle = new sudoku(9);
		
		BufferedImage img;
		try {
			img = ImageIO.read(new File("img/background.jpg"));
			ImagePanel img_pan = new ImagePanel(img);
			img_pan.setLayout(new BorderLayout(10, 5));
			setContentPane(img_pan);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// tao o khung 3x3
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(9, 9));
		board.setPreferredSize(new Dimension(250, 250));
		board.setBorder(BorderFactory.createLineBorder(Color.orange.darker(), 1));
		
		NumberFormat number = NumberFormat.getNumberInstance();
		grid = new JFormattedTextField[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				grid[i][j] = new JFormattedTextField(number);
				grid[i][j].setSize(new Dimension(50, 50));
				grid[i][j].setMaximumSize(getSize());
				grid[i][j].setFont(new Font("Arial", Font.PLAIN, 14));
				grid[i][j].setHorizontalAlignment(JFormattedTextField.CENTER);
				grid[i][j].setBorder(BorderFactory.createLineBorder(Color.orange.darker(), 1));
				board.add(grid[i][j]);
			}
		}
		
		// Fill the sudoku puzzle at begin of program
		puzzle.createPuzzle();
		setSudokuContent(puzzle.getGrid());
		
		JPanel outer_board = new JPanel();
		outer_board.add(board);
		
		// Bo dem thoi gian
		JPanel time_panel = new JPanel();
		time_panel.setLayout(new BoxLayout(time_panel, BoxLayout.Y_AXIS));
		JLabel time_lbl = new JLabel("Time :");
		time_run = new JLabel("00:00:00");
		
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				second++;
				LocalTime now = LocalTime.parse("00:00:00");
				LocalTime now2 = now.plusSeconds(second);
				time_run.setText(now2.toString());
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
		tit_pan.add(new JLabel("SUDOKU"));
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

	// Fill the sudoku puzzle
	private void setSudokuContent(int[][] arr) {
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++) {
				grid[i][j].setValue(null);
				grid[i][j].setEditable(true);
				if(arr[i][j] > 0) {
					grid[i][j].setValue((int)arr[i][j]);
					grid[i][j].setEditable(false);
				}
			}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_new) {
			puzzle.createPuzzle();
			setSudokuContent(puzzle.getGrid());
		}
		if(e.getSource() == btn_check) {
			int[][] sol = puzzle.getSolution();
			
			for(int i = 0; i < 9; i++)
				for(int j = 0; j < 9; j++) {
					if((int)grid[i][j].getValue() != sol[i][j]) {
						
					}
				}
		}
	}
}