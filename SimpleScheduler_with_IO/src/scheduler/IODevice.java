package scheduler;

public class IODevice extends Thread {

	private Job theJob;
	private int ioDurationMsec;
	private Scheduler myScheduler;
	
	public void run() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
		//remove theJob from input queue and add theJob back into the ready queue
		myScheduler.finishIO(theJob);
		
		
		
	}
	
}
