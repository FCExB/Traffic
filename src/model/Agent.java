package model;

public abstract class Agent extends Thread {
	int delay;
	boolean running;
	
	public Agent(int delay){
		this.delay = delay;
		running = true;
	}
	
	abstract void act();
	
	public void run()
	{
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e1) {
		}
		
		while(running)
		{
			long start = System.nanoTime();
			act();
			long end = System.nanoTime();
			try {
				Thread.sleep(delay-((start-end)/10000000));
			} catch (InterruptedException e) {
			}
		}
	}	
}
