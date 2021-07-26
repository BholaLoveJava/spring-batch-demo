package com.sutherland.batch.job.reader;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component
public class CustomItemReader extends AbstractItemStreamItemReader {

	Integer[] intArray = { 1, 2, 3, 4, 5, 6, 7, 8 };
	List<Integer> listArray = Arrays.asList(intArray);

	int index = 0;
	
	@Override
	public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		Integer nextItem = null;
		if(index < listArray.size()) {
			nextItem = listArray.get(index);
			index++;
		}else {
			index = 0;
		}
		return nextItem;
	}
}
