package com.sutherland.batch.job.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class BillMailerJobExecutionListener implements JobExecutionListener {

	private static final Logger logger = LoggerFactory.getLogger(BillMailerJobExecutionListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {

		logger.info("before starting the job Name :: "+jobExecution.getJobInstance().getJobName());
		logger.info("before starting the job :: "+jobExecution.getExecutionContext().toString());
		jobExecution.getExecutionContext().put("Name", "Bhola Kumar2");
		logger.info("before starting the job :: "+jobExecution.getExecutionContext().toString());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		logger.info("after finishing the job :: "+jobExecution.getExecutionContext().toString());
	}

}
