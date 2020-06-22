package frame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Menu extends JFrame implements ActionListener {

	private JButton play, how, exit, back;
	private JPanel outer_center, rule_panel, center_panel ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
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
	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 382);
		setLocationRelativeTo(null);
		setTitle("Game menu");
		setResizable(false);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(new BorderLayout(0, 0));
		
		//cai dat background
		BufferedImage img;
		try {
			img = ImageIO.read(new File("img/osaka_cpy.jpg"));
			ImagePanel contentPane = new ImagePanel(img);
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//label sudoku game
		JPanel north_panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
		JLabel title = new JLabel("SUDOKU GAME");
		title.setFont(new Font("Arial", Font.ROMAN_BASELINE, 20));
		title.setForeground(new Color(252,137,75));
		
		north_panel.add(title);
		
		//panel center bao gom cac button
		center_panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
		JPanel btn_panel = new JPanel();
		btn_panel.setLayout(new BoxLayout(btn_panel, BoxLayout.Y_AXIS));
		btn_panel.setBorder(new EmptyBorder(50, 0, 50, 0));
		
		play = new JButton("Play");
		Dimension btn_size = new Dimension(100, 30);
		play.setSize(btn_size);
		play.setPreferredSize(btn_size);
		play.setMaximumSize(btn_size);
		play.addActionListener(this);
		
		how = new JButton("How to play");
		how.setSize(btn_size);
		how.setPreferredSize(btn_size);
		how.setMaximumSize(btn_size);
		how.addActionListener(this);
		
		exit = new JButton("Exit");
		exit.setSize(btn_size);
		exit.setPreferredSize(btn_size);
		exit.setMaximumSize(btn_size);
		exit.addActionListener(this);
		
		Dimension gap = new Dimension(0, 20);
		btn_panel.add(play);
		btn_panel.add(Box.createRigidArea(gap));
		btn_panel.add(how);
		btn_panel.add(Box.createRigidArea(gap));
		btn_panel.add(exit);
		
		center_panel.add(btn_panel);
		
		//panel cardlayout
		outer_center = new JPanel();
		outer_center.setLayout(new CardLayout());
		
		//panel cach choi
		rule_panel = new JPanel();
		rule_panel.setLayout(new GridLayout(0, 1));
		JLabel rule = new JLabel("How to play");
		rule.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel r1 = new JLabel("<html><p>Each column must contain all of the numbers 1 through 9 and no two numbers in the same column" + 
								"of a Sudoku puzzle can be the same.</p></html>");
		JLabel r2 = new JLabel("<html><p>Each row must contain all of the numbers 1 through 9 and no two numbers in the same row "
				+ "of a Sudoku puzzle can be the same.</p></html>");
		JLabel r3 = new JLabel("<html><p>Each block must contain all of the numbers 1 through 9 and no two numbers in the same block "
				+ "of a Sudoku puzzle can be the same.</p></html>");
		
		rule.setForeground(new Color(254, 182, 89 ));
		r1.setForeground(Color.white);
		r2.setForeground(Color.white);
		r3.setForeground(Color.white);
		
		JPanel back_panel = new JPanel();
		back = new JButton("Back");
		back.setHorizontalAlignment(JButton.CENTER);
		back.addActionListener(this);
		back_panel.add(back);
		
		//rule_panel add cac label rule
		rule_panel.add(rule);
		rule_panel.add(r1);
		rule_panel.add(r2);
		rule_panel.add(r3);
		rule_panel.add(back_panel);
		
		//cardlayout add cac card con
		outer_center.add(center_panel, "menu");
		outer_center.add(rule_panel, "how");
		
		north_panel.setOpaque(false);
		center_panel.setOpaque(false);
		btn_panel.setOpaque(false);
		outer_center.setOpaque(false);
		back_panel.setOpaque(false);
		
		getContentPane().add(north_panel, BorderLayout.NORTH);
		getContentPane().add(outer_center, BorderLayout.CENTER);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == exit) {
			int close = JOptionPane.showConfirmDialog(getContentPane(), "Exit the game?", "EXIT", JOptionPane.YES_NO_OPTION);
			if(close == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			else {
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
		if(e.getSource() == play) {
			Game game = new Game();
			game.setVisible(true);
			this.dispose();
		}
		if(e.getSource() == how) {
			CardLayout cl = (CardLayout) outer_center.getLayout();
			cl.show(outer_center, "how");
			rule_panel.setOpaque(false);
		}
		if(e.getSource() == back) {
			CardLayout cl = (CardLayout) outer_center.getLayout();
			cl.show(outer_center, "menu");
		}
	}

}
