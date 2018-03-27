package Scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DataExtractionJob implements Job {
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

				System.out.println("Hello Quartz!");

			}
}
