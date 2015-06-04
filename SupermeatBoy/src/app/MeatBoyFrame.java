/*Super Meat Boy
 *Kevin Mao
 *Ritchie Chen
 *Daniel Moore
 *CS3 Final project
 *Gallatin-3rd
 */
package app;
import input.MeatBoyInput;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.TreeMap;

import level.MeatBoyLevel;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MeatBoyFrame extends JFrame implements ActionListener{
	public enum State{
		MAINMENU,CONTROLS,FORESTSELECT, FACTORYSELECT, INGAME, LOADING
	}
	private State state; //must be able to pick a level. do ___ when on menu state. load up the levels... etc. 
	private MeatBoyInput input;
	private MeatBoyLevel currentlevel;
	private BufferedImage forestlsimage;
	private BufferedImage factorylsimage;
	private BufferedImage loadingscreen;
	private BufferedImage menuscreen;
	private BufferedImage meatboyimg;
	private TreeMap<String,String> forestLevels;
	private TreeMap<String,String> factoryLevels;
	private MyPanel forestls;
	private MyPanel factoryls;
	private MyPanel credits;
	private MyPanel controls;
	private MyPanel mainmenu;
	private JButton gotoforest;
	private JButton gotofactory;
	private JButton back,back1,back2,back3;
	private JButton gotocredits;
	private JButton gotocontrols;
	private int position;
	private Image imgicon;
	private int x;
	private int y;
	private int counter;
	private String src;
	private long delay;
	private long lastpress;
	private Timer updater;
	/**
	 * The GUI component of Super Meat Boy. Contains a main menu, a control panel,
	 *  and two level selects - Forest and Factory.
	 *
	 */
	public MeatBoyFrame() {
		setSize(640,500);
		delay=300;
		lastpress=0;
		position=1;
		x=0;
		y=0;
		counter=0;
		src="";
		input = new MeatBoyInput(this);
		forestLevels = new TreeMap<String,String>();
		factoryLevels = new TreeMap<String,String>();
		try{
			imgicon=ImageIO.read(new File("resources/meatboystanding.png"));
			meatboyimg =ImageIO.read(new File("resources/meatboystanding.png"));
			forestlsimage= ImageIO.read(new File("resources/forestlevelselect.png"));
			factorylsimage= ImageIO.read(new File("resources/factorylevelselect.png"));
			loadingscreen= ImageIO.read(new File("resources/loadingscreen.png"));
			menuscreen= ImageIO.read(new File("resources/titlescreen.png"));
		}catch(Exception e){e.printStackTrace();}
		for(int i=1;i<=8;i++){
			forestLevels.put("forest"+i, "forest"+i+".tmx");
			factoryLevels.put("factory"+i, "factory"+i+".tmx");
		}
		state = State.MAINMENU;
		gotoforest = new JButton("To the Forest");
		gotofactory = new JButton("To the Factory");
		gotocredits = new JButton("Credits");
		gotocontrols = new JButton("Controls");
		back = new JButton("Back");	
		back1 = new JButton("Back");	
		back2 = new JButton("Back");	
		back3 = new JButton("Back");	
		gotoforest.addActionListener(this);
		gotofactory.addActionListener(this);
		gotocredits.addActionListener(this);
		gotocontrols.addActionListener(this);
		back.addActionListener(this);
		back1.addActionListener(this);
		back2.addActionListener(this);
		back3.addActionListener(this);
		
		forestls = new MyPanel(forestlsimage);
		factoryls= new MyPanel(factorylsimage);
		controls = new MyPanel(menuscreen);
		mainmenu = new MyPanel(menuscreen);
		
		mainmenu.setLayout(new GridBagLayout());
		controls.setLayout(new BorderLayout());
		forestls.setLayout(new BorderLayout());
		factoryls.setLayout(new BorderLayout());
		//main menu
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(5,5,5,5);
	    c.gridx = 1;
	    c.gridy = 3;
	    c.gridwidth=3;
	    mainmenu.add(gotoforest,c);
	    c.gridx = 1;
	    c.gridy = 4;
	    c.gridwidth=3;
	    mainmenu.add(gotofactory,c);
	    c.gridx = 1;
	    c.gridy = 5;
	    c.gridwidth=3;
	    mainmenu.add(gotocontrols,c);
	    //Controls panel
	    JPanel holder = new JPanel(new GridLayout(5,1));
	    JLabel l1 = new JLabel("F: Use to speed up at any time and jump significantly higher.");
	    l1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
	    JLabel l2 = new JLabel("Left: Moves left");
	    l2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
	    JLabel l3 = new JLabel("Right: Moves right");
	    l3.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
	    JLabel l4 = new JLabel("Space: Used to jump, and walljump when touching a wall.");
	    l4.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
	    JLabel l5 = new JLabel("Enter: Used to enter a level while in levelselect.");
	    l5.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
	    holder.add(l1);
	    holder.add(l2);
	    holder.add(l3);
	    holder.add(l4);
	    holder.add(l5);
	    controls.add(holder, BorderLayout.CENTER);
	    JPanel holder2 = new JPanel(new BorderLayout());
	    holder2.add(back,BorderLayout.WEST);
	    controls.add(holder2,BorderLayout.SOUTH);
	    //forest ls
	    JPanel holder3 = new JPanel(new BorderLayout());
	    holder3.add(back1,BorderLayout.WEST);
	    forestls.add(holder3,BorderLayout.SOUTH);
	    //factory ls
	    JPanel holder4 = new JPanel(new BorderLayout());
	    holder4.add(back2,BorderLayout.WEST);
	    factoryls.add(holder4,BorderLayout.SOUTH);
	    
		setIconImage(imgicon);
		setTitle("Super Meat Boy");
		setLayout(new BorderLayout());
		updater= new Timer(100,this);
		this.getContentPane().add(mainmenu,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setFocusable(true);
		setVisible(true);
		updater.start();
	}
	/**
	 * Updates the state of the game, and acts accordingly.
	 */
	public void update(){
		if(state==State.FACTORYSELECT||state==State.FORESTSELECT){
			if(System.currentTimeMillis()-delay>lastpress&&input.isKeyPressed(KeyEvent.VK_RIGHT)&&position>-1&&position<9){
				if(position<4)
					position++;
				if(position<9&&position>5)
					position--;
				lastpress=System.currentTimeMillis();
				determineCoords(state);
			}
			if(System.currentTimeMillis()-delay>lastpress&&input.isKeyPressed(KeyEvent.VK_LEFT)&&position>-1&&position<9){
				if(position<5&&position>0)
				position--;
				if(position>4&&position<8)
					position++;
				lastpress=System.currentTimeMillis();
				determineCoords(state);
			}
			if(System.currentTimeMillis()-delay>lastpress&&input.isKeyPressed(KeyEvent.VK_UP)&&position>-1&&position<9){
				if(position==5){
					position--;
				}
				lastpress=System.currentTimeMillis();
				determineCoords(state);
			}
			if(System.currentTimeMillis()-delay>lastpress&&input.isKeyPressed(KeyEvent.VK_DOWN)&&position>-1&&position<8){
				if(position==4){
					position++;
				}
				lastpress=System.currentTimeMillis();
				determineCoords(state);
			}
			if(input.isKeyPressed(KeyEvent.VK_ENTER)&&state==State.FORESTSELECT){
				
				state=State.LOADING;
				counter=0;
				src=forestLevels.get("forest"+position);
			}
			if(input.isKeyPressed(KeyEvent.VK_ENTER)&&state==State.FACTORYSELECT){
				state=State.LOADING;
				counter=0;			
				src=factoryLevels.get("factory"+position);
			}
			if(state==State.FORESTSELECT){
				if(counter==0){
					determineCoords(state);
					counter++;
					this.getContentPane().removeAll();
					this.getContentPane().add(forestls,BorderLayout.CENTER);
					revalidate();
				}
				
			}
			else if(state==State.FACTORYSELECT){
				if(counter==0){
					determineCoords(state);
					counter++;
					this.getContentPane().removeAll();
					this.getContentPane().add(factoryls,BorderLayout.CENTER);
					revalidate();
				}
			}
		}
		if(state==State.INGAME){
			this.getContentPane().removeAll();
			this.getContentPane().add(currentlevel,BorderLayout.CENTER);
			currentlevel.start();
			updater.stop();
			revalidate();
		}
	}
	/**
	 * Paints onto the jframe, displaying the loading screen and meatboy character
	 * @param g the graphics object to draw with.
	 */
	public void paint(Graphics g){
		super.paint(g);
		if(state == State.LOADING){
			g.drawImage(loadingscreen, 0,0,null);
			currentlevel=new MeatBoyLevel(this, src);
		}
		
		if(state==State.FACTORYSELECT||state==State.FORESTSELECT){
			BufferedImage bf = new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
			Graphics gtmp= bf.getGraphics();
			gtmp.drawImage(meatboyimg,0,0,null);
			g.drawImage(bf,x,y+30,null);
		}
		
	}
	/**
	 * Restarts the menu updater after a level has been completed.
	 */
	public void restart(){
		updater = new Timer(100,this);
		updater.start();
	}
	public static void main(String[] args) {
		new MeatBoyFrame();
	}
	/**
	 * Deals with the buttons, and continuosly updates and repaints the menu.
	 */
	public void actionPerformed(ActionEvent e) {
		// deal with hidden buttons, gotols buttons, etc.
		String src = e.getSource() instanceof JButton?((JButton)e.getSource()).getText():"";
		if(src.equals("To the Forest")){
			counter=0;
			position=1;
			state=State.FORESTSELECT;
			this.getContentPane().removeAll();
			this.getContentPane().add(forestls, BorderLayout.CENTER);
			revalidate();
			repaint();
		}
		if(src.equals("To the Factory")){
			counter=0;
			position=1;
			state=State.FACTORYSELECT;
			this.getContentPane().removeAll();
			this.getContentPane().add(factoryls, BorderLayout.CENTER);
			revalidate();
			repaint();		
		}
		if(src.equals("Controls")){
			state=State.CONTROLS;
			this.getContentPane().removeAll();
			this.getContentPane().add(controls, BorderLayout.CENTER);
			revalidate();
			repaint();	
		}
		if(src.equals("Back")){
			state=State.MAINMENU;
			counter=0;
			x=0;
			y=0;
			position=1;
			this.getContentPane().removeAll();
			this.getContentPane().add(mainmenu, BorderLayout.CENTER);
			revalidate();
			repaint();	
		}
		update();
		repaint();	
	}
	/**
	 * Sets the state of the game from the meatboy class.
	 * @param s the new state being set
	 */
	public void setState(State s){			
		state=s;
	}
	/**
	 * Determines where meatboy should be painted inside of the level select screen
	 * @param s the state of the game
	 */
	public void determineCoords(State s){
		if(s==State.FACTORYSELECT){
			switch(position){
				case 1:
					x=90;
					y=200;
					break;
				case 2:
					x=230;
					y=200;
					break;
				case 3:
					x=387;
					y=200;
					break;
				case 4:
					x=538;
					y=200;
					break;
				case 5:
					x=537;
					y=306;
					break;
				case 6:
					x=387;
					y=313;
					break;
				case 7:
					x=240;
					y=306;
					break;
				case 8:
					x=90;
					y=311;
					break;
			}
		}
		else{
			switch(position){
				case 1:
					x=103;
					y=227;
					break;
				case 2:
					x=254;
					y=230;
					break;
				case 3:
					x=405;
					y=230;
					break;
				case 4:
					x=552;
					y=224;
					break;
				case 5:
					x=556;
					y=338;
					break;
				case 6:
					x=407;
					y=334;
					break;
				case 7:
					x=253;
					y=336;
					break;
				case 8:
					x=101;
					y=340;
					break;
		}
		}
	}
}