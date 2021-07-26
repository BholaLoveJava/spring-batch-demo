package com.sutherland.batch.job.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class BillMailerStepExecutionListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {

		System.out.println("this is before from Step Execution "+stepExecution.getJobExecution().getExecutionContext());
		System.out.println("Step job parameters"+stepExecution.getJobExecution().getJobParameters());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("this is after from Step Execution "+stepExecution.getJobExecution().getExecutionContext());
		return null;
	}

}
