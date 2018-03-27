package Scheduler;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class ConnectionScheduler {
	public static void main(String[] args) throws Exception {
		
		// JobDetail job = new JobDetail();
		// job.setName("dummyJobName");
		// job.setJobClass(HelloJob.class);

		JobDetail job = JobBuilder.newJob(VerifyConnection.class)
				.withIdentity("ValideDatabase", "group1").build();

		// SimpleTrigger trigger = new SimpleTrigger();
		// trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		// trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		// trigger.setRepeatInterval(30000);

		// Trigger the job to run on the next round minute
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("StartDBValidation", "group1")
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(30).repeatForever())
				.build();

		// schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}
	public static void updateConnection() throws SchedulerException {
		JobDetail job = JobBuilder.newJob(VerifyConnection.class)
				.withIdentity("LoadDBConnection", "group1").build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("TriggerLoadDBConnection", "group1")
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule())
				.build();

		// schedule it
		Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
		scheduler2.start();
		scheduler2.scheduleJob(job, trigger);

	}
}
