package app;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import level.MeatBoyLevel;
import javax.swing.JFrame;


public class MeatBoyFrame extends JFrame {
	public MeatBoyFrame() {
		setSize(640,480);
		MeatBoyLevel level1 = new MeatBoyLevel(this);
		Image imgicon=Toolkit.getDefaultToolkit().createImage("resources/meatboystanding.png");
		setIconImage(imgicon);
		setTitle("Super Meat Boy");
		setLayout(new BorderLayout());
		
		add(level1,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		new MeatBoyFrame();

	}
}