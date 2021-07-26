package com.sutherland.batch.job.writer;

import java.util.List;

import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("rawtypes")
public class CustomItemWriter extends AbstractItemStreamItemWriter {

	@SuppressWarnings("unchecked")
	@Override
	public void write(List items) throws Exception {
		items.stream().forEach(System.out::println);
		System.out.println("Writing each chunks");

	}

}
