package ui;
import javax.swing.JFrame;

import model.*;

public class Program extends JFrame {
	
	public Program(){
		
		RealNetwork world = new RealNetwork();
		
		setSize(1000,770);
		setVisible(true);
		add(new DisplayPanel(75,new DisplayMap(650,650,world)));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){	
		new Program();
	}

}
