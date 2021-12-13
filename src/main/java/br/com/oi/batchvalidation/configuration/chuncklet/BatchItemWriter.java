package br.com.oi.batchvalidation.configuration.chuncklet;

import br.com.oi.batchvalidation.models.Batch;
import br.com.oi.batchvalidation.utils.CsvFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class BatchItemWriter implements ItemWriter<Batch>, StepExecutionListener  {

    @Autowired
    private CsvFileUtils csvFileUtils;

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public void write(List<? extends Batch> list) throws Exception {

    }
}
