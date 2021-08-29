package com.sutherland.batch.job.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class BillMailerStepExecutionListener implements StepExecutionListener {

	private static final Logger logger = LoggerFactory.getLogger(BillMailerStepExecutionListener.class);
	@Override
	public void beforeStep(StepExecution stepExecution) {

		logger.info("this is before from Step Execution "+stepExecution.getJobExecution().getExecutionContext());
		logger.info("Step job parameters"+stepExecution.getJobExecution().getJobParameters());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("this is after from Step Execution "+stepExecution.getJobExecution().getExecutionContext());
		return null;
	}

}
