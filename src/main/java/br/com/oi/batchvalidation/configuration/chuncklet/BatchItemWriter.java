package br.com.oi.batchvalidation.configuration.chuncklet;

import br.com.oi.batchvalidation.models.Batch;
import br.com.oi.batchvalidation.repository.BatchRepository;
import br.com.oi.batchvalidation.utils.CsvFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@Slf4j
public class BatchItemWriter implements ItemWriter<Batch>, StepExecutionListener  {

    @Autowired
    private CsvFileUtils csvFileUtils;

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.csvFileUtils = new CsvFileUtils("savedBatchs", false);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try{
            this.csvFileUtils.closeWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(List<? extends Batch> list) throws Exception {
        List<? extends Batch> savedBatchList = this.batchRepository.saveAll(list);
        savedBatchList.forEach(batch -> {
            try {
                this.csvFileUtils.writer(batch);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
