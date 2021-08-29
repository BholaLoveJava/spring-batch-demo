package com.sutherland.batch.job.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomItemProcessor implements ItemProcessor<Integer, Integer> {

	private static final Logger logger = LoggerFactory.getLogger(CustomItemProcessor.class);
	@Override
	public Integer process(Integer item) throws Exception {
		logger.info("INSIDE CustomItemProcessor :: process() method");
		return Integer.sum(10, item);
	}

}
