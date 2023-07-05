package com.snakegame;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	
	private int[] snakexlength = new int[750];
	private int[] snakeylength = new int[750];
	
	
	private int[] xPos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
	private int[] yPos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
	private Random random = new Random();
	private int enemX,enemY;
	private ImageIcon enemy = new ImageIcon( getClass().getResource("/images/enemy.png"));
	
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private int lengthofsnake = 3;
	private int moves = 0;
	
	private ImageIcon snaketitle = new ImageIcon( getClass().getResource("/images/snaketitle.jpg"));
	private ImageIcon leftmouth = new ImageIcon( getClass().getResource("/images/leftmouth.png"));
	private ImageIcon rightmouth = new ImageIcon( getClass().getResource("/images/rightmouth.png"));
	private ImageIcon upmouth = new ImageIcon( getClass().getResource("/images/upmouth.png"));
	private ImageIcon downmouth = new ImageIcon( getClass().getResource("/images/downmouth.png"));
	private ImageIcon snakeimage = new ImageIcon( getClass().getResource("/images/snakeimage.png"));
	private ImageIcon titleicon = new ImageIcon( getClass().getResource("/images/TitleIcon.png"));
	
	private int score = 0;
	private boolean gameover = false;
	
	private Timer timer;
	private int delay = 100;
	
	/*{
		snakexlength[0] = 100;
		snakexlength[1] = 75;
		snakexlength[2] = 50;
		snakeylength[0] = 100;
		snakeylength[1] = 100;
		snakeylength[2] = 100;
	}*/
	
	public GamePanel(JFrame frame){
		frame.setIconImage(titleicon.getImage());
		
		frame.addKeyListener(this);
		frame.setFocusable(true);
		frame.setFocusTraversalKeysEnabled(true);
		
		timer = new Timer(delay,this);
		timer.start();
		
		newEnemy();
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(Color.WHITE);		//Set color for box borders
		
		g.drawRect(24,10,851,55);		//Draw title box
		
		g.drawRect(24,74,851,576);		//Draw game play box
		snaketitle.paintIcon(this,g,25,11);
		g.setColor(Color.BLACK);
		g.fillRect(25, 75, 850, 575);
		
		if(moves == 0) {
			snakexlength[0] = 100;
			snakexlength[1] = 75;
			snakexlength[2] = 50;
			
			snakeylength[0] = 100;
			snakeylength[1] = 100;
			snakeylength[2] = 100;
		}
		
		if(left) {
			leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}
		if(right) {
			rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}
		if(up) {
			upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}
		if(down) {
			downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}
		
		for(int i=1; i<lengthofsnake; i++) {
			snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);
		}		
		
		enemy.paintIcon(this, g, enemX, enemY);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.drawString("Score : "+score, 750, 40);
		
		if(gameover) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial",Font.BOLD,50));
			g.drawString("Game Over", 300, 300);
			
			g.setFont(new Font("Arial",Font.PLAIN,20));
			g.drawString("Press SPACE to Restart", 350, 350);
		}
		
		g.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(int i=lengthofsnake-1; i>0; i--) {
			snakexlength[i] = snakexlength[i-1];
			snakeylength[i] = snakeylength[i-1];
		}
		
		if(left) {
			snakexlength[0]= snakexlength[0] - 25;
		}
		if(right) {
			snakexlength[0]= snakexlength[0] + 25;
		}
		if(up) {
			snakeylength[0]= snakeylength[0] - 25;
		}
		if(down) {
			snakeylength[0]= snakeylength[0] + 25;
		}
		
		
		if(snakexlength[0]>850)snakexlength[0]=25;
		if(snakexlength[0]<25)snakexlength[0]=850;
		if(snakeylength[0]>625)snakeylength[0]=75;
		if(snakeylength[0]<75)snakeylength[0]=625;

		collidesWithEnemy();
		collidesWithBody();
		
		repaint();
		
	}

	private void collidesWithBody() {
		for(int i=lengthofsnake-1; i>0; i--) {
			if(snakexlength[0]==snakexlength[i] && snakeylength[0]==snakeylength[i]) {
				timer.stop();
				gameover = true;
			}
		}
	}


	private void newEnemy() {
		enemX = xPos[random.nextInt(34)];
		enemY = yPos[random.nextInt(23)];
		for(int i=lengthofsnake-1; i>0; i--) {
			if(snakexlength[i]==enemX && snakeylength[i]==enemY) {
				newEnemy();
			}
		}
	}
	
	private void collidesWithEnemy() {
		if(snakexlength[0]==enemX && snakeylength[0]==enemY) {
			newEnemy();
			lengthofsnake++;
			score++;
		}
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right) ) {
			left = true;
			right = false;
			up = false;
			down = false;
			moves++;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP && (!down) ) {
			left = false;
			right = false;
			up = true;
			down = false;
			moves++;
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up) ) {
			left = false;
			right = false;
			up = false;
			down = true;
			moves++;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left) ) {
			left = false;
			right = true;;
			up = false;
			down = false;
			moves++;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE ) {
			Restart();
		}
	}


	private void Restart() {
		gameover=false;
		moves = 0;
		score = 0;
		lengthofsnake = 3;
		left = false;
		right = true;;
		up = false;
		down = false;
		timer.start();
	}


	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}	
	
	
}
