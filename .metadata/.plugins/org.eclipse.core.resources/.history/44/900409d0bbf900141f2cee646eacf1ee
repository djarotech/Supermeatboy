package app;
import java.awt.BorderLayout;
import level.MeatBoyLevel;
import javax.swing.JFrame;


public class MeatBoyFrame extends JFrame {
	public MeatBoyFrame() {
		setSize(640,640);
		MeatBoyLevel level1 = new MeatBoyLevel(this);
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