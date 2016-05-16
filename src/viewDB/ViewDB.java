package viewDB;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;

public class ViewDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		EventQueue.invokeLater(new Runnable() {
			public void run() {				
				ViewDBFrame frame=new ViewDBFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);				
			}
		});
	}
	
}
