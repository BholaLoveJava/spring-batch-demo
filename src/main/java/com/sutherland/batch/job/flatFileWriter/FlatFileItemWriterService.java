package com.sutherland.batch.job.flatFileWriter;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.sutherland.batch.job.model.Product;

@Service
public class FlatFileItemWriterService {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	@StepScope
	public FlatFileItemReader flatFileItemReaderData(@Value("#{jobParameters['fileInput']}") FileSystemResource inputFile) {
        // Step 1 Create FlatFileItemReader Object.
		FlatFileItemReader reader = new FlatFileItemReader();
		// Step 2 Set the Resource.
		reader.setResource(inputFile);
		// Step 3, Set LinesToSkip.
		reader.setLinesToSkip(1);
		
		// Step 4, Set LineMapper.
		reader.setLineMapper(new DefaultLineMapper() {
			{
				// Step 5, Set LineTokenizer
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "productID", "productName", "productDesc", "price", "unit" });
						setDelimiter(",");
					}
				});
				
				// Step 6, Set FieldSetMapper.
				setFieldSetMapper(new BeanWrapperFieldSetMapper() {
					{
						setTargetType(Product.class);
					}
				});

				
			}
		});

		return reader;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	@StepScope
	public FlatFileItemWriter flatFileItemWriterData(@Value("#{jobParameters['fileOutput']}") FileSystemResource outputFile) {
		// Step 1, Create FlatFileItemWriter Object.
		FlatFileItemWriter writer = new FlatFileItemWriter();
        // Step 2, Set Resource.
		writer.setResource(outputFile);
		// Step 3, Set LineAggregator.
		writer.setLineAggregator(new DelimitedLineAggregator() {
			{
				setDelimiter("|");
				// Step 4, Set FieldExtractor.
				setFieldExtractor(new BeanWrapperFieldExtractor() {
					{
						setNames(new String[] {  "productID", "productName", "productDesc", "price", "unit" });
					}
				});
			}
		});

		// Step 5, Set  the Header.
		writer.setHeaderCallback(new FlatFileHeaderCallback() {
			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("productID, productName, productDesc, price, unit");
			}
		});

		// Step 6, Set AppendAllowed TRUE.
		writer.setAppendAllowed(false);

		// Step 7, Set the Footer.
		writer.setFooterCallback(new FlatFileFooterCallback() {
			@Override
			public void writeFooter(Writer writer) throws IOException {
				writer.write(" The file is created at " + new SimpleDateFormat().format(new Date()));
			}
		});
		return writer;
	}
}
