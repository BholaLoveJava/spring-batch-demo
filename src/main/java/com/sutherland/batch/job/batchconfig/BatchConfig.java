package com.sutherland.batch.job.batchconfig;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.sutherland.batch.job.flatFileWriter.FlatFileItemWriterService;
import com.sutherland.batch.job.listener.BillMailerJobExecutionListener;
import com.sutherland.batch.job.listener.BillMailerStepExecutionListener;
import com.sutherland.batch.job.model.Product;
import com.sutherland.batch.job.model.ProductDataModel;
import com.sutherland.batch.job.processor.CustomItemProcessor;
import com.sutherland.batch.job.reader.CustomItemReader;
import com.sutherland.batch.job.reader.ProductServiceAdapter;
import com.sutherland.batch.job.writer.ConsoleItemWriter;
import com.sutherland.batch.job.writer.CustomItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Autowired
	private BillMailerJobExecutionListener billMailerJobListener;

	@Autowired
	private BillMailerStepExecutionListener billMailerStepListener;

	@Autowired
	private CustomItemProcessor customItemProcessor;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ProductServiceAdapter productServiceAdapter;
	
	@Autowired
	private FlatFileItemWriterService flatFileItemWriterService;

	@Bean
	public Step step1() {
		return steps
				.get("step1")
				.listener(billMailerStepListener)
				.tasklet(billMailerTasklet())
				.build();
	}

	@Bean
	public CustomItemWriter writer() {
		return new CustomItemWriter();
	}

	@Bean
	public CustomItemReader reader() {
		return  new CustomItemReader();
	}

	@SuppressWarnings("rawtypes")
	@StepScope
	@Bean
	public StaxEventItemReader xmlItemReader(@Value("#{jobParameters['fileInput']}") FileSystemResource inputFile) {
		// step 1 where to read the xml file
		StaxEventItemReader reader = new StaxEventItemReader();
		reader.setResource(inputFile);
		
		// step 2 need to let reader to know which tags describe the domain object
		reader.setFragmentRootElementName("product");

		// step 3 tell reader how to parse XML and which domain object to be mapped
		reader.setUnmarshaller(new Jaxb2Marshaller() {
			{
				setClassesToBeBound(Product.class);
			}
		});

		return reader;

	}
	
	@SuppressWarnings("rawtypes")
	@StepScope
	@Bean
	public FlatFileItemReader flatFileItemReader(@Value("#{jobParameters['fileInput']}") FileSystemResource inputFile) {
		FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
		// step 1 let reader know where is the file
		reader.setResource(inputFile);

		// step 2 create the line Mapper
		reader.setLineMapper(new DefaultLineMapper<Product>() { // Here Set LineMapper
			{
				setLineTokenizer(new DelimitedLineTokenizer() {  // Here DelimitedLineTokenizer
					{
						setNames(new String[] { "prodId", "productName", "prodDesc", "price", "unit" });
						setDelimiter("|");
					}
				});

				setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {  // Here Set FieldSEtMapper
					{
						setTargetType(Product.class);
					}
				});
			}
		}
);
		// step 3 tell reader to skip the header
		reader.setLinesToSkip(1);
		return reader;

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@StepScope
	@Bean
	public FlatFileItemReader flatFixedFileItemReader(@Value("#{jobParameters['fileInput']}") FileSystemResource inputFile) {
		FlatFileItemReader reader = new FlatFileItemReader();
		// step 1 let reader know where is the file
		reader.setResource(inputFile);

		// step 2 create the line Mapper
		reader.setLineMapper(new DefaultLineMapper<Product>() {  // Here Set LineMapper
			{
				setLineTokenizer(new FixedLengthTokenizer() {  // Here FixedLengthTokenizer
					{
						setNames(new String[] { "productID", "productName", "ProductDesc", "price", "unit" });
						setColumns(new Range(1, 16), 
								   new Range(17, 41), 
								   new Range(42, 65), 
								   new Range(66, 73),
								   new Range(74, 80)

								);
					}
				});

				setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() { // Here Set FieldSetMapper
					{
						setTargetType(Product.class);
					}
				});
			}
		}

				);
		// step 3 tell reader to skip the header
		reader.setLinesToSkip(1);
		return reader;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public JdbcCursorItemReader jdbcCursorItemReader() {
		// step 1 Create JdbcCursorItemReader Object.
		JdbcCursorItemReader reader = new JdbcCursorItemReader();
		
		// step 2 Set DataSource.
		reader.setDataSource(this.dataSource);
		
		// step 3 Set Sql Query.
		reader.setSql("select id, productname, productcategory , productsubcategory, productcontainer, productbasemargin from products");
		
		// step 4 Set RowMapper
		reader.setRowMapper(new BeanPropertyRowMapper() {
			{
				setMappedClass(ProductDataModel.class);
			}
		});
		return reader;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@StepScope
	@Bean
	public JsonItemReader jsonItemReader(@Value("#{jobParameters['fileInput']}") FileSystemResource inputFile) {
		// step 1 Create JsonItemReader Object.
		// step2 Pass inputfile and create JacksonJsonObjectReader Object, with arguments as Domain Class. 
		JsonItemReader reader = new JsonItemReader(inputFile, new JacksonJsonObjectReader(Product.class));
		return reader;

	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	public ItemReaderAdapter serviceJSONItemReader() {
		// step 1 Create ItemReaderAdapter Object.
		ItemReaderAdapter reader = new ItemReaderAdapter();
		// step 2 , Set TargetObject.
		reader.setTargetObject(productServiceAdapter);
		// step 3 , Set TargetMethod.
		reader.setTargetMethod("nextProduct");

		return reader;
	}
	
	@SuppressWarnings("unchecked")
	@Bean
	public Step step2() {
		return steps.get("step2")
				.<Integer,Integer>chunk(3)
				//.reader(flatFileItemReader( null )) // CSV FlatFile Reader.
				//.reader(xmlItemReader(null))  // Xml File Reader.
				//.reader(flatFixedFileItemReader(null)) // Fixed Length File Reader.
				//.reader(jdbcCursorItemReader()) // Reading From Database Table, JDBC Data Reader.
				//.reader(jsonItemReader(null)) // Reading From JSON File.
				.reader(serviceJSONItemReader()) // Reading JSON Data From REST Service.
				.writer(new ConsoleItemWriter())
				.build();
	}

	private Tasklet billMailerTasklet() {
		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Bill Mailer Triggered!!!");
				return RepeatStatus.FINISHED;
			}
		};
	}

	@Bean
	public Job billMailerJob() {
		return jobs
				.get("billMailerJob")
				.incrementer(new RunIdIncrementer())
				.listener(billMailerJobListener)
				.start(step1())
				.next(step2())
				.build();
	}
}
