package com.sutherland.batch.job.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("rawtypes")
public class CustomItemWriter extends AbstractItemStreamItemWriter {

	private static final Logger logger = LoggerFactory.getLogger(CustomItemWriter.class);
	@SuppressWarnings("unchecked")
	@Override
	public void write(List items) throws Exception {
		items.stream().forEach(System.out::println);
		logger.info("Writing each chunks");

	}

}
