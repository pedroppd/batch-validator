package br.com.oi.batchvalidation.configuration;

import br.com.oi.batchvalidation.configuration.chuncklet.BatchItemProcessor;
import br.com.oi.batchvalidation.configuration.chuncklet.BatchItemWriter;
import br.com.oi.batchvalidation.models.Batch;
import br.com.oi.batchvalidation.configuration.chuncklet.BatchItemReader;
import br.com.oi.batchvalidation.models.BatchValidateTasklet;
import br.com.oi.batchvalidation.models.dto.BatchDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//Classes ItemProcessor, ItemReader, ItemWriter
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("${file.name}")
    private String fileName;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("loteValidation")
                //Responsável por validar os dados de entrada do arquivo
                .start(batchValidateTaskStep())
                .next(batchEnrichmentChunckletStep(batchItemReader(), batchItemProcessor(), batchItemWriter()))
                .build();
    }

    @Bean
    public Step batchValidateTaskStep(){
        //usado para arquivos menores e é feito em memória.
        return stepBuilderFactory.get("loteValidateTaskStep")
                .tasklet(new BatchValidateTasklet(fileName))
                .build();
    }

    @Bean
    public Step batchEnrichmentChunckletStep(
            ItemReader<BatchDto> batchItemReader,
            ItemProcessor<BatchDto, Batch> batchItemProcessor,
            ItemWriter<Batch> batchItemWriter){
        return stepBuilderFactory
                .get("batchEnrichmentChunckletStep")
                .<BatchDto, Batch>chunk(5)
                .reader(batchItemReader)
                .processor(batchItemProcessor)
                .writer(batchItemWriter)
                .build();
    }

    @Bean
    public ItemReader<BatchDto> batchItemReader(){
        return new BatchItemReader();
    }

    @Bean
    public ItemProcessor<BatchDto, Batch> batchItemProcessor(){
        return new BatchItemProcessor();
    }

    @Bean
    public ItemWriter<Batch> batchItemWriter(){
        return new BatchItemWriter();
    }


}
