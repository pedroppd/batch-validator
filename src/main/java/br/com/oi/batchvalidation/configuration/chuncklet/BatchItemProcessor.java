package br.com.oi.batchvalidation.configuration.chuncklet;

import br.com.oi.batchvalidation.models.Batch;
import br.com.oi.batchvalidation.models.dto.BatchDto;
import br.com.oi.batchvalidation.utils.BatchConverterUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;


@Slf4j
public class BatchItemProcessor implements ItemProcessor<BatchDto, Batch>, StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Process Starter");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Process completed");
        return ExitStatus.COMPLETED;
    }

    @Override
    public Batch process(BatchDto batchDto) throws Exception {
        log.info("Process parsing");
        return BatchConverterUtils.getCarro(batchDto);
    }
}
