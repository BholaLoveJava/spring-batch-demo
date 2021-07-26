package com.sutherland.batch.job.writer;

import java.util.List;

import org.springframework.batch.item.support.AbstractItemStreamItemWriter;

@SuppressWarnings("rawtypes")
public class ConsoleItemWriter extends AbstractItemStreamItemWriter{

	@SuppressWarnings("unchecked")
	@Override
	public void write(List items) throws Exception {
		items.stream().forEach(System.out::println);
		 System.out.println(" ************ writing each chunck ***********");
	}

}
