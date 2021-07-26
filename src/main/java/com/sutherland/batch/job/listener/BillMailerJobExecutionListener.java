package com.sutherland.batch.job.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class BillMailerJobExecutionListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {

		System.out.println("before starting the job Name :: "+jobExecution.getJobInstance().getJobName());
		System.out.println("before starting the job :: "+jobExecution.getExecutionContext().toString());
		jobExecution.getExecutionContext().put("Name", "Bhola Kumar2");
		System.out.println("before starting the job :: "+jobExecution.getExecutionContext().toString());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		System.out.println("after finishing the job :: "+jobExecution.getExecutionContext().toString());
	}

}
