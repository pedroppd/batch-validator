package br.com.oi.batchvalidation.configuration.chuncklet;

import br.com.oi.batchvalidation.models.dto.BatchDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.*;

import java.util.Iterator;
import java.util.List;

@Slf4j
public class BatchItemReader implements ItemReader<BatchDto>, StepExecutionListener {
    private Iterator<BatchDto> batchInIterator;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step is starting...");
        ExecutionContext ec = stepExecution
                .getJobExecution()
                .getExecutionContext();

        List<BatchDto> batchDtoList = (List<BatchDto>) ec.get("carroInlist");
        this.batchInIterator = batchDtoList.iterator();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step is finishing...");
        return ExitStatus.COMPLETED;
    }

    @Override
    public BatchDto read() {
        log.info("Step is reading...");
        if(this.batchInIterator != null && this.batchInIterator.hasNext()){
            return this.batchInIterator.next();
        }
        return null;
    }
}
