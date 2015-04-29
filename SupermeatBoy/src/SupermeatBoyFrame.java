import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;


public class SupermeatBoyFrame extends JFrame implements ActionListener{
	private Timer time;
	private MeatBoy player;
	public static void main(String[] args) throws IOException{
		SupermeatBoyFrame mb = new SupermeatBoyFrame();
	}
	public SupermeatBoyFrame() throws IOException {
		player = new MeatBoy(this);
		setSize(500,600);
		setTitle("Super Meat Boy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		time=new Timer(5,this);
		time.start();
		setVisible(true);
	}
	public void update(){
		
	}
	public void paint(Graphics g){
		super.paint(g);
		try {
			player.draw(g);
		} catch (IOException e) {e.printStackTrace();}
	}
	public void actionPerformed(ActionEvent e){
		update();
		repaint();
	}
	
}
