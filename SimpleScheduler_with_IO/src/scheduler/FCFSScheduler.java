package scheduler;

/*
 * @author: Brianna Wurtsmith
 * I apologize if there were other authors listed here, or comments. 
 * Some of my files removed the comments when I imported into Eclipse.
 * */

import java.util.concurrent.ConcurrentLinkedQueue;

public class FCFSScheduler extends Scheduler {

	/*
	 * TO_DO: your data structure to support a FCFS scheduler and the abstract
	 * methods of Scheduler
	 */
	ConcurrentLinkedQueue<Job> myJobQueue = new ConcurrentLinkedQueue<Job>();
	ConcurrentLinkedQueue<Job> myIOQueue = new ConcurrentLinkedQueue<Job>();  //this is new

	/**
	 * If the ready queue is empty, return false. Otherwise, start the next job
	 * in the queue, returning true. If the queue is empty return false. Make
	 * the next job in the ready queue run. You should probably invoke
	 * Thread.start() on it.
	 */
	public boolean makeRun() {
		/*
		 * Place code here that gets the next Job from the ready queue and
		 * invokes start() on it
		 *
		 */
		Job someJob = myJobQueue.poll();
		
		if (someJob == null) {
			return false;
		}
		else {
			currentlyRunningJob = someJob;
			if (currentlyRunningJob.isAlive()) { //if job ran, had IO burst, and now will run some more...
				currentlyRunningJob.notify();
			} else {                             //if job has never run (this is the first time)
				currentlyRunningJob.start();
			}
			return true;
		}
		
		//there should be an await() somewhere in makeRun
		
	}

	/**
	 * blockTilThereIsAJob() Invoked by OS simulator when it wants to get a new
	 * Job to run. Will block if the ready queue is empty until a Job is added
	 * to the queue.
	 */
	public void blockTilThereIsAJob() {
		if (hasRunningJob())
			return;
		//System.out.println("TO_DO: blockTilThereIsAJob not yet implemented");
		/*
		 * Place code here that will cause the calling thread to block until the
		 * ready queue contains a Job
		 */
		while (myJobQueue.isEmpty()) {
			//System.out.print("Blocking - Empty Queue. ");
		}
		System.out.println("\nEvidently there is now a job on readyQ");
	}

	@Override
	public void add(Job J) {
		myJobQueue.add(J);

	}

	@Override
	public void remove(Job J) {
		myJobQueue.remove(J);

	}

	@Override
	public boolean hasJobsQueued() {
		if (myJobQueue.isEmpty() && myIOQueue.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void startIO() {
		myIOQueue.add(getRunningJob());
		getRunningJob().notify();
	}

	@Override
	public void finishIO(Job j) {
		myIOQueue.remove(j);
		myJobQueue.add(j);
		
	}

	@Override
	public boolean hasReadyJobs() {
		// Used in makeRun
		if (myJobQueue.isEmpty()) {
			return false;
		}
		else {
			return true;
		}		
	}
}
