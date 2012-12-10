package ui;


import java.awt.Graphics;


import javax.swing.JPanel;

import model.*;

public class DisplayPanel extends JPanel implements Runnable {
	
	int delay,width,height;
	DisplayMap displayMap;
	
	
	public DisplayPanel(int delay,DisplayMap displayMap){
		super();
		this.delay = delay;
		this.displayMap = displayMap;
		
		setDoubleBuffered(true);

	}
	
	public void paint(Graphics g)
	{
		super.paint(g); 
		displayMap.drawNetwork(g);
		displayMap.drawAllRoads(g);
		displayMap.drawTrafficLights(g);
	}
	
	 public void addNotify() {
	     super.addNotify();
	     Thread refresher = new Thread(this);
	     refresher.start();
	 }
	
	public void run()
	{
		while(true)
		{
			long start = System.nanoTime();
			repaint();
			long end = System.nanoTime();
			try {
				Thread.sleep(delay-((start-end)/10000000));
			} catch (InterruptedException e) {
			}
		}
	}
}
