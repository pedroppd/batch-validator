package br.com.oi.batchvalidation.models;

import br.com.oi.batchvalidation.models.dto.BatchDto;
import br.com.oi.batchvalidation.utils.BatchValidateUtils;
import br.com.oi.batchvalidation.utils.CsvFileUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class BatchValidateTasklet implements Tasklet, StepExecutionListener {

    private List<BatchDto> batchDtoList;

    private String name;

    public BatchValidateTasklet(String name){
        this.name = name;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws IOException {
        CsvFileUtils csvIn = new  CsvFileUtils(this.name, true);

        BatchDto batchDto = csvIn.read();

        while(batchDto != null){
            this.batchDtoList.add(batchDto);
            batchDto = csvIn.read();
        }

        csvIn.closeReader();

        this.batchDtoList = BatchValidateUtils.validate(this.batchDtoList);

        if(this.batchDtoList.isEmpty()){
            log.error("Lista de batchs válidos está vazia");
            throw new RuntimeException("Lista de batchs válidos está vazia");
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.batchDtoList = new ArrayList<>();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext().put("batchInList", this.batchDtoList);
        return ExitStatus.COMPLETED;
    }
}
